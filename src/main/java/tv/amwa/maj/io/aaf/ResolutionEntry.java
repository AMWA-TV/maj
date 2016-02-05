/*
 * Copyright 2016 Richard Cartwright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * $Log: ResolutionEntry.java,v $
 * Revision 1.12  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.11  2011/10/05 17:14:30  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.10  2011/07/27 12:27:34  vizigoth
 * Removed unreferenced variables warning messages.
 *
 * Revision 1.9  2011/02/14 22:33:04  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.8  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/05/19 12:58:28  vizigoth
 * Capability to write an AAF file that MAJ can read.
 *
 * Revision 1.6  2010/05/14 18:29:05  vizigoth
 * First version to output something AAF-like!
 *
 * Revision 1.5  2010/04/16 15:21:52  vizigoth
 * Interim checkin while developing AAF file writing of index tables.
 *
 * Revision 1.4  2010/03/19 10:10:11  vizigoth
 * Added comment headers, tidied up and added a factory with a readPreface method.
 *
 */

package tv.amwa.maj.io.aaf;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.industry.MemoryResidentStream;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceSet;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.industry.WeakReferenceVector;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

public class ResolutionEntry {

	private PropertyDefinition property;
	private MetadataObject target;
	private Object reference;
	private ByteOrder byteOrder = null;

	public ResolutionEntry(
			PropertyDefinition property,
			MetadataObject target,
			String path) {
		
		this.property = property;
		this.target = target;
		this.reference = path;
	}

	public ResolutionEntry(
			PropertyDefinition property,
			MetadataObject target,
			String path,
			ByteOrder byteOrder) {
		
		this.property = property;
		this.target = target;
		this.reference = path;
		this.byteOrder = byteOrder;
	}	
	
	public ResolutionEntry(
			PropertyDefinition property,
			MetadataObject target,
			AUID reference) {
		
		this.property = property;
		this.target = target;
		this.reference = reference;	
	}
	
	public MetadataObject getTarget() {
		
		return target;
	}
	
	public PropertyDefinition getProperty() {
		
		return property;
	}
	
	@SuppressWarnings("unchecked")
	public void resolve(
			Map<String, MetadataObject> pathMap,
			Map<String, ByteBuffer> indexMap,
			Map<String, Stream> streamMap) {
				
		TypeDefinition propertyType = property.getTypeDefinition();
		
		if (property.getAUID().equals(AAFConstants.ParametersID)) {
			handleParametersSpecialCase(pathMap, indexMap, propertyType);
			return;
		}
		
		switch (propertyType.getTypeCategory()) {
		
		case WeakObjRef:
			AUID referenceID = null;
			if (reference instanceof AUID)
				referenceID = (AUID) reference;
			else {
				System.err.println("Unexpected " + reference.getClass().getName() + " reference value for weak reference " +
						"property " + property.getMemberOf().getName() + "." + property.getName() + ".");
				break;
			}
			
			Class<?> baseClassImplementation = 
				((TypeDefinitionWeakObjectReference) propertyType).getObjectType().getJavaImplementation();
			
			WeakReference<? extends WeakReferenceTarget> weakReference =
				new WeakReference(baseClassImplementation, referenceID);
			try {
				Field injectionPoint = findInjectionPoint(property);
				injectionPoint.setAccessible(true);
				injectionPoint.set(target, weakReference);
			}
			catch (Exception e) {
				System.err.println("Could not inject a value into property " + property.getName() + ".");
				property.setPropertyValue(target, propertyType.createValue(weakReference.getTarget()));
			}
			break;
		
		case StrongObjRef:
			MetadataObject referenceTarget = null;
			if (reference instanceof String)
				referenceTarget = pathMap.get(reference);
			if (referenceTarget == null) {
				System.err.println("Unable to resolve reference " + reference + " for property " + 
						property.getMemberOf().getName() + "." + property.getName());
				break;
			}
			
			try {
				Field injectionPoint = findInjectionPoint(property);
				injectionPoint.setAccessible(true);
				injectionPoint.set(target, referenceTarget);
			}
			catch (Exception e) {
				System.err.println("Unable to use injection for property " + property.getName() + ".");
//				if (!(target instanceof ApplicationObject))
					property.setPropertyValue(target, propertyType.createValue(referenceTarget));
			}
			break;
			
		case VariableArray:
			
			TypeDefinition arrayReferencedType = ((TypeDefinitionVariableArray) propertyType).getType();
			String arrayCollectionPath = (String) reference;
			
			switch (arrayReferencedType.getTypeCategory()) {
			
			case StrongObjRef:
				ByteBuffer arrayIndexBuffer = indexMap.get(arrayCollectionPath + " index");

//				System.out.println("*** READING VARIABLE ARRAY INDEX ***");
//				arrayIndexBuffer.rewind();
//				while (arrayIndexBuffer.hasRemaining())
//					System.out.print(Integer.toHexString(arrayIndexBuffer.get()) + " ");
//				System.out.println();
				arrayIndexBuffer.rewind();
				
				int entryCount = arrayIndexBuffer.getInt();
				@SuppressWarnings("unused")
				int firstFree = arrayIndexBuffer.getInt();
				@SuppressWarnings("unused")
				int lastFree = arrayIndexBuffer.getInt();
				
//				System.out.println("*** STRONG REFERENCE VECTOR: entryCount " + entryCount + " firstFree " + firstFree 
//						+ " lastFree " + lastFree);
				
				List<MetadataObject> elements = new Vector<MetadataObject>(entryCount);
				for ( int x = 0 ; x < entryCount ; x++ ) {
//					String arrayElementPath = arrayCollectionPath + "{" + Integer.toHexString(x) + "}";
					String arrayElementPath = arrayCollectionPath + "{" + Integer.toHexString(arrayIndexBuffer.getInt()) + "}";
//					System.out.println(x + ": " + arrayElementPath);
					elements.add(pathMap.get(arrayElementPath));
					
				}
				
				try {
					Field injectionPoint = findInjectionPoint(property);
					injectionPoint.setAccessible(true);
					injectionPoint.set(target, elements);
				}
				catch (Exception e) {
					System.err.println("Unable to use injection for property " + property.getName() + ".");
//					if (!(target instanceof ApplicationObject))
						property.setPropertyValue(
								target, 
								propertyType.createValue(elements));
				}

//				System.out.println(target.toString());
				break;
				
			case WeakObjRef:
//				System.out.println("Looking for index " + arrayCollectionPath + " index: " + 
//						indexMap.containsKey(arrayCollectionPath + " index"));
				ByteBuffer weakArrayIndexBuffer = indexMap.get(arrayCollectionPath + " index");
				int weakEntryCount = weakArrayIndexBuffer.getInt();
				@SuppressWarnings("unused")
				short referencedPropertyTag = weakArrayIndexBuffer.getShort();
				@SuppressWarnings("unused")
				short identificationPid = weakArrayIndexBuffer.getShort();
				@SuppressWarnings("unused")
				byte identificationSize = weakArrayIndexBuffer.get();
//				System.out.println("*** WEAK REFERENCE VECTOR: Array entry count = " + weakEntryCount + 
//						", indentificationPid = " + identificationPid + ", " +
//						"identificationSize = " + identificationSize);
				WeakReferenceVector<WeakReferenceTarget> weakArray = new WeakReferenceVector<WeakReferenceTarget>();
				for ( int x = 0 ; x < weakEntryCount ; x++ ) {
					try {
						AUID reference = AUIDImpl.createFromBuffer(weakArrayIndexBuffer);
//						System.out.println("Weak reference element " + x + ": " + reference.toString());
						weakArray.append(new WeakReference(
								((TypeDefinitionWeakObjectReference) 
										arrayReferencedType).getObjectType().getJavaImplementation(),
										reference));
					}
					catch (EndOfDataException eode) {
						System.err.println("Error reading data from a weak reference vector.");
					}
				}
				
				try {
					Field injectionPoint = findInjectionPoint(property);
					injectionPoint.setAccessible(true);
					injectionPoint.set(target, weakArray);
				}
				catch (Exception e) {
					System.err.println("Unable to use injection for property " + property.getName() + ".");
					property.setPropertyValue(
							target, 
							propertyType.createValue(weakArray));
				}

				// System.out.println(target.toString());
				break;
				
			default:
				// Other kinds of variable array values are embedded in the main properties.
				break;
			}
			break;
			
		case Set:
			
			TypeDefinition setReferencedType = ((TypeDefinitionSet) propertyType).getElementType();
			String setCollectionPath = (String) reference;
			
			switch (setReferencedType.getTypeCategory()) {
			
			case StrongObjRef:
				ByteBuffer setIndexBuffer = indexMap.get(setCollectionPath + " index");
				int entryCount = setIndexBuffer.getInt();
				@SuppressWarnings("unused")
				int firstFreeKey = setIndexBuffer.getInt();
				@SuppressWarnings("unused")
				int lastFreeKey = setIndexBuffer.getInt();
				@SuppressWarnings("unused")
				short identificationPid = setIndexBuffer.getShort();
				byte identificationSize = setIndexBuffer.get();
				
//				System.out.println("*** STRONG REFERENCE SET: entryCount " + entryCount + " firstFree " + firstFreeKey
//						+ " lastFree " + lastFreeKey + " identificationPid " + Integer.toHexString(identificationPid) +
//						" identificationSize " + identificationSize);
				
				Set<MetadataObject> setElements = new HashSet<MetadataObject>(entryCount);
				for ( int x = 0 ; x < entryCount ; x++ ) {
					int localKey = setIndexBuffer.getInt();
					@SuppressWarnings("unused")
					int referenceCount = setIndexBuffer.getInt();
					byte[] identification = new byte[identificationSize];
					setIndexBuffer.get(identification);
					String setElementPath = setCollectionPath + "{" + Integer.toHexString(localKey) + "}";
					setElements.add(pathMap.get(setElementPath));
//					System.out.println("localKey " + localKey + " referenceCount " + referenceCount + ": " +
//							setElementPath);

//					for ( int y = 0 ; y < identification.length ; y++ )
//						System.out.print(Integer.toHexString(identification[y]) + " ");
//					System.out.println();
				}
				
				try {
					Field injectionPoint = findInjectionPoint(property);
					injectionPoint.setAccessible(true);
					injectionPoint.set(target, setElements);
				}
				catch (Exception e) {
					System.err.println("Unable to use injection for property " + property.getName() + ".");
					property.setPropertyValue(
						target, 
						propertyType.createValue(setElements));
				}

				// System.out.println(target.toString());
				break;
				
			case WeakObjRef:
//				System.out.println("Looking for index " + setCollectionPath + " index: " + 
//						indexMap.containsKey(setCollectionPath + " index"));
				ByteBuffer weakArrayIndexBuffer = indexMap.get(setCollectionPath + " index");
				
//				System.out.println("*** READING INDEX ***");
//				weakArrayIndexBuffer.rewind();
//				while (weakArrayIndexBuffer.hasRemaining())
//					System.out.print(Integer.toHexString(weakArrayIndexBuffer.get()) + " ");
//				System.out.println();
//				weakArrayIndexBuffer.rewind();

				int weakEntryCount = weakArrayIndexBuffer.getInt();
				@SuppressWarnings("unused")
				short referencedPropertyTag = weakArrayIndexBuffer.getShort();
				@SuppressWarnings("unused")
				short weakIdentificationPid = weakArrayIndexBuffer.getShort();
				@SuppressWarnings("unused")
				byte weakIdentificationSize = weakArrayIndexBuffer.get();
//				System.out.println("*** WEAK REFERENCE SET: Set entry count = " + weakEntryCount + 
//						" referencedPropertyTag " + referencedPropertyTag +
//						" indentificationPid " + weakIdentificationPid +
//						" identificationSize " + weakIdentificationSize);
				WeakReferenceSet<WeakReferenceTarget> weakSet = new WeakReferenceSet<WeakReferenceTarget>();
				for ( int x = 0 ; x < weakEntryCount ; x++ ) {
					try {
						AUID reference = AUIDImpl.createFromBuffer(weakArrayIndexBuffer);
//						System.out.println("Weak reference element " + x + ": " + reference.toString());
						weakSet.add(new WeakReference(
								((TypeDefinitionWeakObjectReference) 
										setReferencedType).getObjectType().getJavaImplementation(),
										reference));
					}
					catch (EndOfDataException eode) {
						System.err.println("Error reading data from a weak reference vector.");
					}
				}
				
				try {
					Field injectionPoint = findInjectionPoint(property);
					injectionPoint.setAccessible(true);
					injectionPoint.set(target, weakSet);
				}
				catch (Exception e) {
					System.err.println("Unable to use injection for property " + property.getName() + ".");
					property.setPropertyValue(
						target, 
						propertyType.createValue(weakSet));
				}

//				System.out.println(target.toString());
				break;
				
			default:
				// Other kinds of set value are embedded in the main properties.
				break;
			}
			break;
		
		case Stream:
//			System.out.println("Resolving stream " + reference.toString() + " for property " + property.getMemberOf().getName() + "." +
//					property.getName() + ".");

			Stream referencedStream = null;
			if (reference instanceof String)
				referencedStream = streamMap.get(reference);
			if (referencedStream instanceof MemoryResidentStream) {
				((MemoryResidentStream) referencedStream).setByteOrder(byteOrder);
			}
			if (referencedStream == null) {
				System.err.println("Unable to resolve stream reference " + reference + " for property " + 
						property.getMemberOf().getName() + "." + property.getName());
				break;
			}
			
			try {
//				System.out.println("***: Resolving a stream. Hooray! Length is " + referencedStream.getLength());
				Field injectionPoint = findInjectionPoint(property);
				injectionPoint.setAccessible(true);
				injectionPoint.set(target, referencedStream);
			}
			catch (Exception e) {
				System.err.println("Unable to use injection for property " + property.getName() + ".");
//				if (!(target instanceof ApplicationObject))
					property.setPropertyValue(target, propertyType.createValue(referencedStream));
			}
			break;

		default:
			break;
		}
	}
	
	
	private void handleParametersSpecialCase(
			Map<String, MetadataObject> pathMap,
			Map<String, ByteBuffer> indexMap, 
			TypeDefinition propertyType) {
		
//		TypeDefinition setReferencedType = ((TypeDefinitionVariableArray) propertyType).getType();
		String setCollectionPath = (String) reference;

		ByteBuffer setIndexBuffer = indexMap.get(setCollectionPath + " index");
		int entryCount = setIndexBuffer.getInt();
		@SuppressWarnings("unused")
		int firstFreeKey = setIndexBuffer.getInt();
		@SuppressWarnings("unused")
		int lastFreeKey = setIndexBuffer.getInt();
		@SuppressWarnings("unused")
		short identificationPid = setIndexBuffer.getShort();
		byte identificationSize = setIndexBuffer.get();
			
//			System.out.println("*** STRONG REFERENCE SET: entryCount " + entryCount + " firstFree " + firstFreeKey
//					+ " lastFree " + lastFreeKey + " identificationPid " + Integer.toHexString(identificationPid) +
//					" identificationSize " + identificationSize);
			
		List<MetadataObject> setElements = new Vector<MetadataObject>(entryCount);
		for ( int x = 0 ; x < entryCount ; x++ ) {
			int localKey = setIndexBuffer.getInt();
			@SuppressWarnings("unused")
			int referenceCount = setIndexBuffer.getInt();
			byte[] identification = new byte[identificationSize];
			setIndexBuffer.get(identification);
			String setElementPath = setCollectionPath + "{" + Integer.toHexString(localKey) + "}";
			setElements.add(pathMap.get(setElementPath));
//				System.out.println("localKey " + localKey + " referenceCount " + referenceCount + ": " +
//						setElementPath);

//				for ( int y = 0 ; y < identification.length ; y++ )
//					System.out.print(Integer.toHexString(identification[y]) + " ");
//				System.out.println();
		}
			
		try {
			Field injectionPoint = findInjectionPoint(property);
			injectionPoint.setAccessible(true);
			injectionPoint.set(target, setElements);
		}
		catch (Exception e) {
			System.err.println("Could not inject a value into property " + property.getName() + ".");
			property.setPropertyValue(
				target, 
				propertyType.createValue(setElements));
		}

			// System.out.println(target.toString());
	}

	private final static Field findInjectionPoint(
			PropertyDefinition property) {
		
		try {
			ClassDefinition memberOf = property.getMemberOf();
			Class<?> memberClass = memberOf.getJavaImplementation();
			return memberClass.getDeclaredField(
					Character.toLowerCase(property.getName().charAt(0)) + property.getName().substring(1));
		}
		catch (NoSuchFieldException nsfe) {
			return null;
		}
	}
}
