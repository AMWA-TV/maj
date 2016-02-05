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
 * $Log: AAFBuilder.java,v $
 * Revision 1.25  2011/11/04 09:54:16  vizigoth
 * Updates to enable writing AAF files from objects with AMP metadata.
 *
 * Revision 1.24  2011/10/05 17:14:30  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.23  2011/07/27 12:27:34  vizigoth
 * Removed unreferenced variables warning messages.
 *
 * Revision 1.22  2011/02/14 22:33:04  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.21  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.20  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.19  2010/12/15 19:03:12  vizigoth
 * Removed unecessary TODO.
 *
 * Revision 1.18  2010/11/18 10:49:05  vizigoth
 * Improved AAF writing support to the point where MAJ AAF can be read by Media Composer (version 4 tested).
 *
 * Revision 1.17  2010/11/08 16:49:14  vizigoth
 * Added support for dealing with streams.
 *
 * Revision 1.16  2010/06/18 16:54:55  vizigoth
 * Added AAF magic number fix up to the main method, which is used for testing only.
 *
 * Revision 1.15  2010/06/16 15:39:59  vizigoth
 * Minor fixes after testing AAF file output.
 *
 * Revision 1.14  2010/06/16 09:36:21  vizigoth
 * First version that writes readable AAF files.
 *
 * Revision 1.13  2010/06/15 19:04:10  vizigoth
 * Further improvements allowing generated AAF file to be read in part.
 *
 * Revision 1.12  2010/06/14 17:12:16  vizigoth
 * Significant progress towards writing valid AAF files with MAJ.
 *
 * Revision 1.11  2010/05/20 18:51:06  vizigoth
 * Fixed a bug where a strong reference to an unrecognised object caused the directory events to be unmatched.
 *
 * Revision 1.10  2010/05/19 22:23:34  vizigoth
 * Adding Avid extensions.
 *
 * Revision 1.9  2010/05/19 12:58:28  vizigoth
 * Capability to write an AAF file that MAJ can read.
 *
 * Revision 1.8  2010/05/14 18:29:05  vizigoth
 * First version to output something AAF-like!
 *
 * Revision 1.7  2010/04/16 15:22:40  vizigoth
 * Interim checkin. Most structure in place for writing AAF files, just index tables to finish.
 *
 * Revision 1.6  2010/04/15 16:33:59  vizigoth
 * Interim checkin of code for writing AAF files.
 *
 * Revision 1.5  2010/04/13 11:34:12  vizigoth
 * Fix to use File.separator and be properly cross-platform.
 *
 * Revision 1.4  2010/04/13 10:12:01  vizigoth
 * Removed some debug output to make code run more cleanly.
 *
 * Revision 1.3  2010/04/13 07:27:25  vizigoth
 * First version to successfully read an Avid-generated AAF file.
 *
 * Revision 1.2  2010/03/19 10:10:11  vizigoth
 * Added comment headers, tidied up and added a factory with a readPreface method.
 *
 */

package tv.amwa.maj.io.aaf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.poi.hpsf.ClassID;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.DocumentOutputStream;
import org.apache.poi.poifs.filesystem.POIFSDocumentPath;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.poifs.filesystem.POIFSWriterEvent;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.TypeCategory;
import tv.amwa.maj.exception.BadParameterException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.IllegalPropertyValueException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.avid.AvidFactory;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MemoryResidentStream;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.meta.MetaDictionary;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.meta.impl.ClassDefinitionImpl;
import tv.amwa.maj.meta.impl.ExtensionSchemeImpl;
import tv.amwa.maj.meta.impl.PropertyDefinitionImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionObjectReferenceImpl;
import tv.amwa.maj.model.ApplicationPluginObject;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.Parameter;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.model.impl.ApplicationPluginObjectImpl;
import tv.amwa.maj.model.impl.PrefaceImpl;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.util.Utilities;

public class AAFBuilder 
	implements AAFConstants {

	static Map<Byte, String> structuredTypes = new HashMap<Byte, String>();
	
	static final char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		
	public final static AUID MobRefClassID = 
		new AUIDImpl(0x6619f8e0, (short) 0xfe77, (short) 0x11d3,
				 new byte[] { (byte) 0xa0, (byte) 0x84, 0x00, 0x60, (byte) 0x94, (byte) 0xeb, 0x75, (byte) 0xcb });
	
	static Map<String, String> directoryNameTable = new HashMap<String, String>(directoryNameAliases.length / 2);
	
	static {
		
		structuredTypes.put((byte) 0x82, "SF_DATA                                  ");
		structuredTypes.put((byte) 0xD2, "SF_DATA_VECTOR                           ");
		structuredTypes.put((byte) 0xDA, "SF_DATA_SET                              ");
		structuredTypes.put((byte) 0x42, "SF_DATA_STREAM                           ");
		structuredTypes.put((byte) 0x22, "SF_STRONG_OBJECT_REFERENCE               ");
		structuredTypes.put((byte) 0x32, "SF_STRONG_OBJECT_REFERENCE_VECTOR        ");
		structuredTypes.put((byte) 0x3A, "SF_STRONG_OBJECT_REFERENCE_SET           ");
		structuredTypes.put((byte) 0x02, "SF_WEAK_OBJECT_REFERENCE                 ");
		structuredTypes.put((byte) 0x12, "SF_WEAK_OBJECT_REFERENCE_VECTOR          ");
		structuredTypes.put((byte) 0x1A, "SF_WEAK_OBJECT_REFERENCE_SET             ");
		structuredTypes.put((byte) 0x03, "SF_WEAK_OBJECT_REFERENCE_STORED_OBJECT_ID");
		structuredTypes.put((byte) 0x86, "SF_UNIQUE_OBJECT_ID                      ");
		structuredTypes.put((byte) 0x40, "SF_OPAQUE_STREAM                         ");
		
		MediaEngine.initializeAAF();
		
		int tableSize = directoryNameAliases.length / 2;
		for ( int x = 0 ; x < tableSize ; x++ )
			directoryNameTable.put(directoryNameAliases[2*x + 1], directoryNameAliases[2*x]);
	}
	
	public final static AAFReaderListener makeAAFEventReader() {
		
		return new LocalAAFEventReader();
	}
	
	private static class LocalAAFEventReader 
		implements AAFReaderListener {
		
		ClassDefinition likelyParent = null;
		List<ClassDefinition> classStack = new ArrayList<ClassDefinition>();
		Map<String, MetadataObject> pathMap = new HashMap<String, MetadataObject>();
		Map<String, ByteBuffer> indexMap = new HashMap<String, ByteBuffer>();
		List<ResolutionEntry> resolutions = new ArrayList<ResolutionEntry>();
		Map<Short, PropertyDefinition> extensionPropertyMap = new HashMap<Short, PropertyDefinition>();
		Map<String, Stream> streamMap = new HashMap<String, Stream>();
		String canonicalFilePath = null;
		boolean seenMetaDictionary = false;
		boolean seenPreface = false;
		boolean metaDictionaryResolved = false;
		
		Preface resolvedPreface = null;
		ExtensionScheme thisScheme = null;
		
		public LocalAAFEventReader() {
			
			thisScheme = new ExtensionSchemeImpl();
			thisScheme.setSchemeID(Forge.randomAUID());
			thisScheme.setSchemeURI(thisScheme.getSchemeID().toString());
			thisScheme.setPreferredPrefix("this");
			thisScheme.setExtensionDescription("Deafult AAF extension scheme.");
			Warehouse.register(thisScheme);
		}
		
		public void processPOIFSReaderEvent(
				POIFSReaderEvent event) { }
		
	    public void processPOIFSReaderEvent(
	    		AAFReaderEvent event) {
	    	
	    	// Deal with need to extension meta definitions to be resolved first
	    	if ((!metaDictionaryResolved) && (event.getPath().length() > 0)) {
	    		POIFSDocumentPath eventPath = event.getPath();
	    		if (eventPath.getComponent(0).equals("MetaDictionary-1"))
	    			seenMetaDictionary = true;
	    		if (eventPath.getComponent(0).equals("Header-2"))
	    			seenPreface = true;
	    		if (seenMetaDictionary && seenPreface) {
	    			resolveMetaDictionary();
	    			resolutions.clear();
	    			pathMap.clear();
	    			indexMap.clear();
	    			metaDictionaryResolved = true;
	    		}
	    	}
	    	
	    	try {
				if (event.getProperty().isDirectory()) {
					AUID classID = new AUIDImpl(event.getProperty().getStorageClsid().getBytes());
					
//					for ( int x = 0 ; x < 16 ; x++)
//						System.err.print(event.getProperty().getStorageClsid().getBytes()[x] + " ");
//					System.err.println();
					
					ClassDefinition theClass = ClassDefinitionImpl.forAUID(classID);
					if (theClass != null) {
						classStack.add(0, theClass);
						likelyParent = theClass;
					}
					else {
						System.err.println("UNRECOGNISED CLASS " + classID.toString());
						classStack.add(0, null);
						if (classStack.size() > 1) 
							likelyParent = classStack.get(1);
						else
							likelyParent = null;
					}
					
					return;
				}
				
//				DocumentInputStream fromProperties = event.getStream();	
//				// TODO the available method is not recommended ... look for an alternative
//				byte[] data = new byte[fromProperties.available()];
//				fromProperties.read(data);
//				
//				ByteBuffer buffer = ByteBuffer.wrap(data);
//				
				if (event.getName().equals(REFERENCED_PROPERTIES_STREAMNAME)) {
//						System.out.println("FOUND REFERENCED PROPERTIES!!!!");
//						
//						while (buffer.hasRemaining())
//							System.out.print(Integer.toHexString(buffer.get()) + " ");
//						System.out.println();
						
//					DocumentInputStream fromProperties = event.getStream();	
//					// TODO the available method is not recommended ... look for an alternative
//					byte[] data = new byte[fromProperties.available()];
//					fromProperties.read(data);
//					
//					ByteBuffer buffer = ByteBuffer.wrap(data);
//					buffer.position(buffer.limit());
					return;
				}
				
				if (event.getName().endsWith(PROPERTIES_STREAMNAME)) {
					
					DocumentInputStream fromProperties = event.getStream();	
					// TODO the available method is not recommended ... look for an alternative
					byte[] data = new byte[fromProperties.available()];
					fromProperties.read(data);
					
					ByteBuffer buffer = ByteBuffer.wrap(data);

					MetadataObject mdObject = decodeProperties(buffer, event.getPath().toString());
					
					if (mdObject == null) return;
					
					pathMap.put(event.getPath().toString(), mdObject);					
					
					if (mdObject instanceof Preface)
						resolvedPreface = (Preface) mdObject;
					
					return;
				}

				if (event.getName().endsWith("index")) {
				
					DocumentInputStream fromProperties = event.getStream();	
					// TODO the available method is not recommended ... look for an alternative
					byte[] data = new byte[fromProperties.available()];
					fromProperties.read(data);
					
					ByteBuffer buffer = ByteBuffer.wrap(data);
					buffer.order(ByteOrder.LITTLE_ENDIAN); // FIXME this looks dodgy! What about big endian files?
					indexMap.put(event.getPath().toString() + File.separator + event.getName(), buffer);
					
					return;
				}
				
				// Assume stream data has been found
				DocumentInputStream fromProperties = event.getStream();	
				// TODO the available method is not recommended ... look for an alternative
				byte[] data = new byte[fromProperties.available()];
				fromProperties.read(data);
				
				ByteBuffer buffer = ByteBuffer.wrap(data);
//				System.out.println("Adding a stream with path : " + event.getPath().toString() + File.separator + event.getName());
				streamMap.put(event.getPath().toString() + File.separator + event.getName(), 
						new MemoryResidentStream(buffer));
				
				
			} catch (NullPointerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	    public void processDirectoryEnd() {
	    	
			classStack.remove(0);
			if (classStack.size() > 0)
				likelyParent = classStack.get(0);
			else
				likelyParent = null;
	    }
	    
		private MetadataObject decodeProperties(
				ByteBuffer buffer,
				String eventPath) {

//			if (eventPath.endsWith("6101")) {
//			buffer.mark();
//			for ( int x = 0 ; x < 16 ; x++ )
//				if (buffer.hasRemaining()) System.err.print(buffer.get() + " ");
//			System.err.println();
//			buffer.reset();
//			}
			
			byte code = buffer.get();
			if (code == 0x4c) // L for little
				buffer.order(ByteOrder.LITTLE_ENDIAN);
			else
				buffer.order(ByteOrder.BIG_ENDIAN);
			
			@SuppressWarnings("unused")
			byte formatVersion = buffer.get();
			int count = (int) buffer.getShort();

			if (likelyParent == null) {
				if (!eventPath.equals(File.separator))
					System.err.println("Cannot decode null class definition for path " + eventPath + ".");
				return null;
			}

			// System.out.println("Properties: count = " + count + " for class " + likelyParent.getName());
			
			MetadataObject mdObject = likelyParent.createInstance();
			if (!(Warehouse.lookForClass(mdObject.getClass()).getAUID().equals(likelyParent.getAUID()))) {
				// Extension class - Add metadata plugin object
				ApplicationPluginObject plugin = new ApplicationPluginObjectImpl();
				plugin.setApplicationPluginInstanceID(Forge.randomAUID());
				plugin.setBaseClass(Warehouse.lookForClass(mdObject.getClass()));
				plugin.setObjectClass(likelyParent);
				plugin.setApplicationScheme(Warehouse.lookupExtensionScheme(likelyParent.getNamespace()));

				if (mdObject instanceof InterchangeObject)
					((InterchangeObject) mdObject).addApplicationPlugin(plugin);
			}
			
			int[] lengths = new int[count];
			PropertyDefinition[] properties = new PropertyDefinition[count];
			
			for ( int x = 0 ; x < count ; x++ ) {
				
				short propertyID = buffer.getShort();
				
				// System.out.print(pad(propertyID, 4) + " ");
				// System.out.print(structuredTypes.get(buffer.get()) + " ");
				
				@SuppressWarnings("unused")
				short storedForm = buffer.getShort();
				lengths[x] = (int) buffer.getShort();
				
				// System.out.print(pad(lengths[x], 4));
				
				PropertyDefinition property = null;
				try {
					if (propertyID > 0)
						property = likelyParent.lookupPropertyDefinition(propertyID);
					else
						property = extensionPropertyMap.get(propertyID);
					properties[x] = property;
					
//					System.out.println(property.getMemberOf().getName() + "." + property.getName() + " " + 
//							property.getTypeDefinition().getName() + " length " + lengths[x] + 
//							" form " + structuredTypes.get((byte) storedForm));
				}
				catch (BadParameterException bpe) {
					System.err.println("Encountered a non-existent property with code " + propertyID + 
							" for class " + likelyParent.getName() + ".");
				}
			}
			
			List<ResolutionEntry> localResolutions = new Vector<ResolutionEntry>();
			
			for ( int x = 0 ; x < count ; x++ ) {

				buffer.limit(buffer.position() + lengths[x]);
//				System.err.println(properties[x].getMemberOf().getName() + "." + properties[x].getName() + " " +
//						"position = " + buffer.position() + " remaining = " + buffer.remaining() + " required = " + lengths[x]);
				
				try {
					TypeDefinition propertyType;
					if (properties[x] != null) 
						propertyType = properties[x].getTypeDefinition();
					else {
						// TODO does this ever happen ... should it be removed?
						System.err.println("Unexpetedly encountered a null property for class " + likelyParent.getName() +
								" index " + x + ".");
						continue;
					}

					if (propertyType == null) {
						System.err.println("Found an unknown type definition for property " + properties[x].getMemberOf().getName() +
								"." + properties[x].getName() + ".");
						continue;
					}
					
					switch (propertyType.getTypeCategory()) {
					
					case WeakObjRef:
//						buffer.mark();
//						System.out.println(properties[x].getName() + 
//								" tag " + Integer.toHexString(properties[x].getLocalIdentification()) + ": ");
//						for ( int y = 0 ; y < lengths[x] ; y++ ) {
//							byte nextByte = buffer.get();
//							System.out.print(hexChar[(nextByte >>> 4) & 15]);
//							System.out.print(hexChar[nextByte & 15]);
//							System.out.print(' ');
//						}
//						System.out.println();
//						buffer.reset();
						
						PropertyValue weakReferenceValue = propertyType.createFromBytes(buffer);
						localResolutions.add(new ResolutionEntry(properties[x], mdObject, 
								(AUID) weakReferenceValue.getValue()));
						break;
					case VariableArray:
						TypeDefinition arrayElementType = ((TypeDefinitionVariableArray) propertyType).getType();
						
						switch (arrayElementType.getTypeCategory()) {
						
						case StrongObjRef:
						case WeakObjRef:
							String referenceName = TypeDefinitions.UTF16String.createFromBytes(buffer).getValue().toString();
							// System.out.println("Array with ref name " + referenceName);
							localResolutions.add(new ResolutionEntry(properties[x], mdObject, eventPath + 
									File.separator + referenceName));
							break;
						case Character:
							Collection<String> stringElements = bufferToStringList(buffer);
							// System.out.println(lengths[x] + ": " + stringElements.toString());
							PropertyValue stringListValue = propertyType.createValue(stringElements);
							properties[x].setPropertyValue(mdObject, stringListValue);							
							break;
						default:
//							System.out.println("About to process variable array value " + properties[x].getMemberOf().getName() + "." +
//									properties[x].getName() + ":" + buffer.toString());
//							int preservePosition = buffer.position();
//							while (buffer.hasRemaining()) {
//								System.out.print(Integer.toHexString(buffer.get()) + ", ");
//							}
//							buffer.position(preservePosition);
//							System.out.println();

							List<PropertyValue> arrayValues = new Vector<PropertyValue>();
							while (buffer.hasRemaining()) 
								arrayValues.add(arrayElementType.createFromBytes(buffer));

							PropertyValue listValue = propertyType.createValue(arrayValues);
							properties[x].setPropertyValue(mdObject, listValue);
							break;						
						}
						break;

					case Set:
						TypeDefinition setElementType = ((TypeDefinitionSet) propertyType).getElementType();
						
						switch (setElementType.getTypeCategory()) {
						
						case StrongObjRef:
						case WeakObjRef:
							String referenceName = TypeDefinitions.UTF16String.createFromBytes(buffer).getValue().toString();
							localResolutions.add(new ResolutionEntry(properties[x], mdObject, eventPath + 
									File.separator + referenceName));
							break;
						case Character:
							Collection<String> stringElements = bufferToStringList(buffer);
							PropertyValue stringListValue = propertyType.createValue(stringElements);
							properties[x].setPropertyValue(mdObject, stringListValue);							
							break;							
						default:
							Set<PropertyValue> arrayValues = new HashSet<PropertyValue>();
							while (buffer.hasRemaining()) 
								arrayValues.add(setElementType.createFromBytes(buffer));

							PropertyValue listValue = propertyType.createValue(arrayValues);
							properties[x].setPropertyValue(mdObject, listValue);
							break;						
						}
						break;
					
					case StrongObjRef:
						String referenceName = TypeDefinitions.UTF16String.createFromBytes(buffer).getValue().toString();
						localResolutions.add(new ResolutionEntry(properties[x], mdObject, eventPath + 
								File.separator + referenceName));
						break;
					case Stream:
//						System.out.println("About to process stream value " + properties[x].getMemberOf().getName() + "." +
//								properties[x].getName() + ":" + buffer.toString());
//						int preservePosition = buffer.position();
//						while (buffer.hasRemaining()) {
//							System.out.print(Integer.toHexString(buffer.get()) + ", ");
//						}
//						buffer.position(preservePosition);
//						System.out.println();
						if (!buffer.hasRemaining()) {
							System.err.println("Found a stream with no data at path " + eventPath + ".");
							break;
						}
						
						tv.amwa.maj.enumeration.ByteOrder streamByteOrder = 
							(buffer.get() == (byte) 'l') ? 
									tv.amwa.maj.enumeration.ByteOrder.Little : tv.amwa.maj.enumeration.ByteOrder.Big;
						String streamReferenceName = 
							eventPath + File.separator + TypeDefinitions.UTF16String.createFromBytes(buffer).getValue().toString();
							
						resolutions.add(new ResolutionEntry(properties[x], mdObject, streamReferenceName, streamByteOrder));

//						System.err.println("Found a stream with path " + streamReferenceName);
						break;
					case Indirect:
//						System.out.println("About to process indirect value " + properties[x].getMemberOf().getName() + "." +
//								properties[x].getName() + ":" + buffer.toString());
//						int preservePosition = buffer.position();
//						while (buffer.hasRemaining()) {
//							System.out.print(Integer.toHexString(buffer.get()) + ", ");
//						}
//						buffer.position(preservePosition);
//						System.out.println();
						
						byte localCode = buffer.get(); // read past '4c' that starts it all
						ByteOrder preserveOrder = buffer.order();
						if (localCode == 0x4c) // L for little
							buffer.order(ByteOrder.LITTLE_ENDIAN);
						else
							buffer.order(ByteOrder.BIG_ENDIAN);
	
						PropertyValue indirectValue = TypeDefinitions.Indirect.createFromBytes(buffer);
//						System.out.println("Indirect value for property " + properties[x].getName() + " is " +
//								((PropertyValue) indirectValue.getValue()).getValue().toString() + ".");
						buffer.order(preserveOrder);
						properties[x].setPropertyValue(mdObject, indirectValue);
						break;
					default:
//						if (propertyType.getTypeCategory() == TypeCategory.ExtEnum) {
//							System.out.println("About to process ext enum value " + properties[x].getMemberOf().getName() + "." +
//									properties[x].getName() + ":" + buffer.toString());
//						
//							int preservePosition = buffer.position();
//							while (buffer.hasRemaining()) {
//								System.out.print(Integer.toHexString(buffer.get()) + ", ");
//							}
//							buffer.position(preservePosition);
//							System.out.println();
//						}
						
						PropertyValue theValue = propertyType.createFromBytes(buffer);
						properties[x].setPropertyValue(mdObject, theValue);
						break;
					}
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (EndOfDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally {
					buffer.position(buffer.limit());
				}
			}
			
			if (mdObject instanceof MetaDictionary) return null;
			
			if (mdObject instanceof MetaDefinition) {
//				if (!MediaEngine.isBaseline((MetaDefinition) mdObject))
//					System.err.println("Found an extension " + likelyParent.getName() + " with name " + 
//							((MetaDefinition) mdObject).getName() + ".");
//				System.out.println(mdObject.toString());

				if (mdObject instanceof PropertyDefinition) {
					PropertyDefinition propertyDefinition = (PropertyDefinition) mdObject;
					if (!Warehouse.isKnownProperty(propertyDefinition)) {
						
						propertyDefinition.setNamespace(thisScheme.getSchemeURI());
						propertyDefinition.setPrefix(thisScheme.getPreferredPrefix());
						propertyDefinition.setSymbol(Utilities.makeSymbol(propertyDefinition.getName()));
						Warehouse.register(propertyDefinition);
						thisScheme.addMetaDefinition(propertyDefinition);
					}
					addExtensionProperty((PropertyDefinition) mdObject);
				}
				
				if (mdObject instanceof ClassDefinition) {
					ClassDefinition classDefinition = (ClassDefinition) mdObject;
					
					if (Warehouse.lookForClass(classDefinition.getAUID()) == null) {
						classDefinition.setNamespace(thisScheme.getSchemeURI());
						classDefinition.setPrefix(thisScheme.getPreferredPrefix());
						Warehouse.register(classDefinition);
						thisScheme.addMetaDefinition(classDefinition);
					}
				}
				
				if (mdObject instanceof TypeDefinition) {
					TypeDefinition typeDefinition = (TypeDefinition) mdObject;
					if (Warehouse.lookForType(typeDefinition.getAUID()) == null) {
						Warehouse.register(typeDefinition, thisScheme.getSchemeURI(), thisScheme.getPreferredPrefix());
						thisScheme.addMetaDefinition(typeDefinition);
					}
				}
			}
			else {
				if (mdObject instanceof WeakReferenceTarget)
					WeakReference.registerTarget((WeakReferenceTarget) mdObject);
			}

			// System.out.println(mdObject.toString());
			resolutions.addAll(localResolutions);
			return mdObject;
		}

		private void addExtensionProperty(
				PropertyDefinition property) {
			
			short propertyPid = property.getLocalIdentification();
			
			if (propertyPid < 1) {
				
				if (Warehouse.isKnownProperty(property))
					extensionPropertyMap.put(propertyPid, ClassDefinitionImpl.globalPropertyIDLookup(property.getAUID()));
			}
		}
		
		@SuppressWarnings("unused")
		private static String pad(int i, int space) {
			
			StringBuffer buffer = new StringBuffer(Integer.toHexString(i));

			while (buffer.length() < space) buffer.insert(0, '0');
			
			return buffer.toString();
		}
		
		private final static List<String> bufferToStringList(
				ByteBuffer buffer) {
			
			List<String> stringElements = new Vector<String>();
			StringBuffer stringElement = new StringBuffer();
			while(buffer.hasRemaining()) {
				if (buffer.remaining() == 1) break;
				char nextChar = buffer.getChar();
				if (nextChar == '\u0000') {
					stringElements.add(stringElement.toString());
					stringElement = new StringBuffer();
				}
				else
					stringElement.append(nextChar);
			}
			
			return stringElements;
		}
		
		public void resolveEntries() {
			
			// TODO check if all index entries are Little Endian or file dependent
			if (resolvedPreface != null) {
				ByteOrder order = (resolvedPreface.getByteOrder() == tv.amwa.maj.enumeration.ByteOrder.Big) ?
					ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
				
				for ( ByteBuffer indexBuffer : indexMap.values() )
					indexBuffer.order(order);
			}
				
			for ( ResolutionEntry resolvable : resolutions )
				resolvable.resolve(pathMap, indexMap, streamMap);
		}
		
		public void resolveMetaDictionary() {
			
			for ( String path : pathMap.keySet() ) {
				MetadataObject item = pathMap.get(path);
				if ((item instanceof MetadataObject) &&
						(!(thisScheme.containsMetaDefinition((MetaDefinition) item))) ) continue;
				
				if (item instanceof PropertyDefinition) {
					PropertyDefinition propertyDefinition = (PropertyDefinition) item;
					int lastSlash = path.lastIndexOf(File.separatorChar);
					if (lastSlash < 0) continue;
					MetadataObject memberClassItem = pathMap.get(path.substring(0, lastSlash));
					if ((memberClassItem == null) || (!(memberClassItem instanceof ClassDefinition))) continue;
					ClassDefinition memberClass = Warehouse.lookForClass(((ClassDefinition) memberClassItem).getAUID());
					if (memberClass == null)
						memberClass = (ClassDefinition) memberClassItem;
					if (thisScheme.containsMetaDefinition(memberClass))
						((ClassDefinitionImpl) memberClass).addPropertyDefinition(propertyDefinition);
					
					try {
						propertyDefinition.getMemberOf();
					}
					catch (PropertyNotPresentException pnpe) {
						
						propertyDefinition.setMemberOf(memberClass);
					}
					continue;
				}
				
				if (item instanceof ClassDefinition) {
					ClassDefinition classDefinition = (ClassDefinition) item;
					try {
						classDefinition.getParent();
						continue;
					}
					catch (NullPointerException npe) { }
					
					for ( ResolutionEntry resolution : resolutions ) {
						if ((classDefinition.equals(resolution.getTarget())) &&
								(resolution.getProperty().getAUID().equals(CommonConstants.ParentClassID))) {
							resolution.resolve(pathMap, indexMap, streamMap);
							classDefinition.setJavaImplementation(classDefinition.getParent().getJavaImplementation());
							// System.out.println(classDefinition.toString());
						}
					}
					
				}
			}
		}
		
		public Preface getPreface() {
			
			return resolvedPreface;
		}
		
		public ExtensionScheme getThisExtensionScheme() {
			
			return thisScheme;
		}
		
		public void setFilePath(
				File filePath)
			throws NullPointerException,
				IOException {
			
			if (filePath == null)
				throw new NullPointerException("Cannot set the file path using a null value.");
			
			this.canonicalFilePath = filePath.getCanonicalPath();
		}
	}
	
	public final static AAFWriterListener makeAAFWriterListener() {
		
		return new LocalAAFWriterListener();
	}
	
	private static class LocalAAFWriterListener
		implements AAFWriterListener {

		private Map<String, SortedMap<PropertyDefinition, PropertyValue>> pathPropertyMap = 
			new HashMap<String, SortedMap<PropertyDefinition, PropertyValue>>();
		private int refPropertyIndexCount = 0;
		private Map<TypeDefinitionWeakObjectReference, Integer> refPropertyMap = 
			new HashMap<TypeDefinitionWeakObjectReference, Integer>();
		private Map<String, PropertyValue> indexMap = 
			new HashMap<String, PropertyValue>();
		private String interchangeObjectPath = "";
		private Map<AUID, Short> localIDTable =
			new HashMap<AUID, Short>();
		private Map<String, Stream> streamMap =
			new HashMap<String, Stream>();
		
		LocalAAFWriterListener() { }
		
		public void processPOIFSWriterEvent(
				POIFSWriterEvent event) {
			
			if (event.getName().equals(PROPERTIES_STREAMNAME)) {
				encodeProperties(event);
				return;
			}
			
			if (event.getName().equals(REFERENCED_PROPERTIES_STREAMNAME)) {
				encodeReferencedProperties(event);
				return;
			}
			
			if (event.getName().endsWith("index")) {
				encodeIndex(event);
				return;
			}
			
			String fullEventPath = event.getPath().toString() + File.separator + event.getName();
			if (streamMap.containsKey(fullEventPath)) {
				encodeStream(event, streamMap.get(fullEventPath));
				return;
			}
			
			System.err.println("Unhandled writer event " + fullEventPath + ".");
		}
		
		private void encodeProperties(
				POIFSWriterEvent event) {
			
			DocumentOutputStream dos = event.getStream();

			if (event.getPath().toString().equals(File.separator)) {
				writeRootProperties(dos);
				return;
			}
			
			SortedMap<PropertyDefinition, PropertyValue> propertyMap = 
				pathPropertyMap.get(event.getPath().toString());
			
			if (propertyMap == null) {
				// Can happen legitimately for all optional objects
				System.err.println("Could not find information for " + event.getPath().toString() + File.separator + "properties.");
				// Should write an empty property maps
				
				try {
					dos.write((byte) 'B');
					dos.write(32);
					dos.write(0);
					dos.write(0);
				}
				catch (IOException ioe) { }
				finally {
					if (dos != null)
						dos.close();
				}
				return;
			}
			
//			if ( propertyMap.size() == 0)
//				System.out.println("Found an empty property map.");				

			String propertyName = null;
			// PropertyDefinition currentProperty = null;
			
			TypeDefinition propertyType  = null;
			String referenceName = null;
			PropertyValue value = null;
			
			try {
				ByteBuffer buffer = ByteBuffer.allocate(event.getLimit());
				buffer.order(ByteOrder.BIG_ENDIAN);
				buffer.put((byte) 'B');
				buffer.put((byte) 32);
				buffer.putShort((short) propertyMap.size());

				for ( PropertyDefinition property : propertyMap.keySet() ) {
					
					registerLocalID(property);
					buffer.putShort(getLocalID(property.getAUID()));

					propertyType = property.getTypeDefinition();
					referenceName = null;
					value = null;
					
					if (property.getAUID().equals(AAFConstants.ParametersID)) {
						buffer.putShort(SF_STRONG_OBJECT_REFERENCE_SET);
						referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
						buffer.putShort((short) (referenceName.length() * 2 + 2));
						continue;
					}

					switch (propertyType.getTypeCategory()) {

					case StrongObjRef:
						buffer.putShort(SF_STRONG_OBJECT_REFERENCE);
						referenceName = makeAAFPathPartName(property, getLocalID(property.getAUID()));
						buffer.putShort((short) (referenceName.length() * 2 + 2));
						break;
					case WeakObjRef:
						buffer.putShort(SF_WEAK_OBJECT_REFERENCE);
						buffer.putShort((short) 21);
						break;
					case VariableArray:
						TypeDefinition arrayRefType = ((TypeDefinitionVariableArray) propertyType).getType();

						switch (arrayRefType.getTypeCategory()) {

						case StrongObjRef:
							buffer.putShort(SF_STRONG_OBJECT_REFERENCE_VECTOR);
							referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
							buffer.putShort((short) (referenceName.length() * 2 + 2));
							break;
						case WeakObjRef:
							buffer.putShort(SF_WEAK_OBJECT_REFERENCE_VECTOR);
							referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
							buffer.putShort((short) (referenceName.length() * 2 + 2));
							break;
						case Character:
							buffer.putShort(SF_DATA);
							value = propertyMap.get(property);
							int stringArrayLength = 0;
							List<PropertyValue> stringElements = TypeDefinitions.UTF16StringArray.getElements(value);
							
							for ( PropertyValue stringElement : stringElements )
								stringArrayLength += ((String) stringElement.getValue()).length() * 2 + 2;
							buffer.putShort((short) stringArrayLength); 
							break;
						default:
							buffer.putShort(SF_DATA);
							value = propertyMap.get(property);
							int arrayLength = ((TypeDefinitionVariableArray) propertyType).getCount(value);
							
							if (arrayLength == 0) {
								buffer.putShort((short) 0);
								break;
							}
								
							PropertyValue exampleValue = ((TypeDefinitionVariableArray) propertyType).getElementValue(value, 0);
							buffer.putShort((short) (arrayLength * arrayRefType.lengthAsBytes(exampleValue)));
							break;
						}
						break;
					case Set:
						TypeDefinition setRefType = ((TypeDefinitionSet) propertyType).getElementType();

						switch (setRefType.getTypeCategory()) {

						case StrongObjRef:
							buffer.putShort(SF_STRONG_OBJECT_REFERENCE_SET);
							referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
							buffer.putShort((short) (referenceName.length() * 2 + 2));
							break;
						case WeakObjRef:
							buffer.putShort(SF_WEAK_OBJECT_REFERENCE_SET);
							referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
							buffer.putShort((short) (referenceName.length() * 2 + 2));
							break;
						case Character:
							buffer.putShort(SF_DATA);
							value = propertyMap.get(property);
							int stringArrayLength = 0;
							List<PropertyValue> stringElements = TypeDefinitions.UTF16StringArray.getElements(value);
							
							for ( PropertyValue stringElement : stringElements )
								stringArrayLength += ((String) stringElement.getValue()).length() * 2 + 2;
							buffer.putShort((short) stringArrayLength); 
							break;
						default:
							buffer.putShort(SF_DATA);
							value = propertyMap.get(property);
							int setLength = ((TypeDefinitionSet) propertyType).getCount(value);
							if (setLength == 0) {
								buffer.putShort((short) 0);
								break;
							}
							
							PropertyValue exampleValue = null;
							for ( PropertyValue candidateValue : ((TypeDefinitionSet) propertyType).getElements(value)) {
								exampleValue = candidateValue;
								break;
							}
							buffer.putShort((short) (setLength * setRefType.lengthAsBytes(exampleValue)));
							break;
						}
						break;
					case Indirect:
						buffer.putShort(SF_DATA);
						value = propertyMap.get(property);
						buffer.putShort((short) (value.getType().lengthAsBytes(value) + 1));
						break;
					case Stream:
						buffer.putShort(SF_DATA_STREAM);
						referenceName = makeAAFPathPartName(property, getLocalID(property.getAUID()));
						buffer.putShort((short) (referenceName.length() * 2 + 2 + 1));
						break;
					default:
						buffer.putShort(SF_DATA);
						
						// Special case ... byte order is a two byte code
						if (property.getAUID().equals(ByteOrderPropertyID)) {
							buffer.putShort((short) 2);
							break;
						}
						
						value = propertyMap.get(property);
						
						buffer.putShort((short) value.getType().lengthAsBytes(value));
						break;
					}
				}

//				buffer.limit(buffer.position());
//				buffer.rewind();
//
//				System.out.println(event.getPath().toString() + "/properties:");
//				while (buffer.hasRemaining())
//					System.out.print(Integer.toHexString(buffer.get()) + " ");
//				System.out.println();
//
//				buffer.limit(event.getLimit());
				buffer.mark();

				for ( PropertyDefinition property : propertyMap.keySet() ) {

					propertyName = property.getMemberOf().getName() + "." + property.getName();
					// currentProperty = property;
					
					propertyType = property.getTypeDefinition();
					referenceName = null;
					value = null;
					TypeDefinitionWeakObjectReference weakType = null;
					
					if (property.getAUID().equals(AAFConstants.ParametersID)) {
						referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
//						System.out.println("Writing set with reference name " + referenceName);
						value = TypeDefinitions.UTF16String.createValue(referenceName);
						TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(value, buffer);						
						continue;
					}
					
					switch (propertyType.getTypeCategory()) {

					case StrongObjRef:
						referenceName = makeAAFPathPartName(property, getLocalID(property.getAUID()));
						value = TypeDefinitions.UTF16String.createValue(referenceName);
						TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(value, buffer);
						break;
					case WeakObjRef:
						weakType = (TypeDefinitionWeakObjectReference) propertyType;
						buffer.putShort(refPropertyMap.get(weakType).shortValue());
						buffer.putShort(weakType.getObjectType().getUniqueIdentifierProperty().getLocalIdentification());
						buffer.put((byte) 16);
						value = propertyMap.get(property);
						
						AUID theReference = null;
						if (value instanceof TypeDefinitionObjectReferenceImpl.ObjectReferenceValue) {
							theReference = 
								((TypeDefinitionObjectReferenceImpl.ObjectReferenceValue) value).getLocalReference();
						}
						else {
							theReference = 
								((TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) value).getValue();
						}
						
						buffer.putInt(theReference.getData1());
						buffer.putShort(theReference.getData2());
						buffer.putShort(theReference.getData3());
						buffer.put(theReference.getData4());
						
//						System.out.println("*** WEAK REFERENCE ***");
						break;
					case VariableArray:
						TypeDefinition arrayRefType = ((TypeDefinitionVariableArray) propertyType).getType();

						switch (arrayRefType.getTypeCategory()) {

						case StrongObjRef:
						case WeakObjRef:
							referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
							value = TypeDefinitions.UTF16String.createValue(referenceName);
							TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(value, buffer);
							break;
						case Character:
							// Assuming UTF16String
							value = propertyMap.get(property);
							
							for ( PropertyValue elementValue : ((TypeDefinitionVariableArray) propertyType).getElements(value)) {
								TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(elementValue, buffer);
								// buffer.putShort((short) 0);
							}
							break;
						default:
							value = propertyMap.get(property);
							
							for ( PropertyValue elementValue : ((TypeDefinitionVariableArray) propertyType).getElements(value))
								arrayRefType.writeAsStructuredStorageBytes(elementValue, buffer);
							break;
						}
						break;
					case Set:
						TypeDefinition setRefType = ((TypeDefinitionSet) propertyType).getElementType();

						switch (setRefType.getTypeCategory()) {

						case StrongObjRef:
						case WeakObjRef:
							referenceName = makeAAFPathPartReference(property, getLocalID(property.getAUID()));
//							System.out.println("Writing set with reference name " + referenceName);
							value = TypeDefinitions.UTF16String.createValue(referenceName);
							TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(value, buffer);
							break;
						default:
							value = propertyMap.get(property);
							
							for ( PropertyValue elementValue : ((TypeDefinitionSet) propertyType).getElements(value)) 
								setRefType.writeAsStructuredStorageBytes(elementValue, buffer);
							break;
						}
						break;
					case Indirect:
						buffer.put((byte) 'B');
						value = propertyMap.get(property);
						value.getType().writeAsStructuredStorageBytes(value, buffer);
						break;
					case Stream:
						value = propertyMap.get(property);
						Stream stream = (Stream) value.getValue();
						if (stream.getByteOrder() == tv.amwa.maj.enumeration.ByteOrder.Little)
							buffer.put((byte) 'l');
						else
							buffer.put((byte) 'B');
						referenceName = makeAAFPathPartName(property, getLocalID(property.getAUID()));
						value = TypeDefinitions.UTF16String.createValue(referenceName);
						TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(value, buffer);
						break;
					default:
						value = propertyMap.get(property);
						
						if (property.getAUID().equals(ByteOrderPropertyID)) {
							buffer.putShort((short) 0x4d4d); // Everything MAJ emits is big byte order
							break;
						}
						
						if (property.getAUID().equals(LocalIdentificationProperty)) {
							PropertyValue idPropertyValue = null;
							for ( PropertyDefinition findIDProperty : propertyMap.keySet() )
								if (findIDProperty.getAUID().equals(MetaDefinitionIDProperty)) {
									idPropertyValue = propertyMap.get(findIDProperty);
									break;
								}
							AUID propertyID = (AUID) idPropertyValue.getValue();
							registerLocalID(propertyID, (Short) value.getValue());
							buffer.putShort((short) getLocalID(propertyID));
							break;
						}
						else 
							value.getType().writeAsStructuredStorageBytes(value, buffer);
						break;
					}

//					if ( propertyType.getTypeCategory() == TypeCategory.Indirect ) {
//						buffer.limit(buffer.position());
//						buffer.reset();
//
//						System.out.print(property.getName() + ":");
//						while (buffer.hasRemaining())
//							System.out.print(Integer.toHexString(buffer.get()) + " ");
//						System.out.println();
//					}
					
					buffer.mark();
					buffer.limit(event.getLimit());

				} // for loop property

//				System.out.println(buffer.toString() + " limit " + event.getLimit());
				
				dos.write(buffer.array());
			} // Try
			catch (InsufficientSpaceException ise) {
				System.err.println("Run out of space in buffer when writing " + event.getPath().toString() + "/properties : " + event.getLimit());
				System.err.println("Property is: " + propertyName);
				if (value != null)
					System.err.println(value.getValue().toString());
			} catch (IOException ioe) {
				System.err.println("IO exception thrown when trying to write structured storage property values for path " +
						event.getPath().toString() + ": " + ioe.getMessage());
				ioe.printStackTrace();
			}
			finally {
//				System.out.println("Closing dos.");
				if (dos != null)
					dos.close();
			}
			
		}

		private void encodeReferencedProperties(
				POIFSWriterEvent event) {
			
			DocumentOutputStream dos = event.getStream();
					
			try {
				ByteBuffer buffer = ByteBuffer.allocate(event.getLimit());
				buffer.order(ByteOrder.BIG_ENDIAN);
				buffer.put((byte) 'B');
				buffer.putShort((short) refPropertyMap.size());
				buffer.putInt((getReferencedPropertiesSize() - 7) / 2);
			
				for ( int x = 0 ; x < refPropertyMap.size() ; x++ ) {
					for ( TypeDefinitionWeakObjectReference weakType : refPropertyMap.keySet() ) {
						if (refPropertyMap.get(weakType) != x) continue;
						
						AUID[] targetSet = weakType.getTargetSet();
						ClassDefinition parentType = null;
						for ( AUID target : targetSet ) {
							if (target.equals(RootPrefaceProperty)) {
								buffer.putShort((short) 2);
								parentType = Warehouse.lookForClass(Preface.class);
								continue;
							}
							if (target.equals(RootMetaDictionaryProperty)) {
								buffer.putShort((short) 1);
								parentType = Warehouse.lookForClass(MetaDictionary.class);
								continue;
							}
							
							try {
								// TODO probably should not have gone so low
								if (parentType == null) {
									System.err.println("++*: Target is " + target + " for weak type " + weakType.getName());
								}
								PropertyDefinition nextInLine = parentType.lookupPropertyDefinition(target);
								buffer.putShort(nextInLine.getLocalIdentification());

								if (nextInLine.getTypeDefinition().getTypeCategory() == TypeCategory.StrongObjRef) 
									parentType = 
										((TypeDefinitionStrongObjectReference) nextInLine.getTypeDefinition()).getObjectType();
							}
							catch (BadParameterException bpe) {
								System.err.println("Error making reference properties for target property " + target.toString() + ".");
							}					
						}
						buffer.putShort((short) 0);
					}
				}
				
//				System.out.println("WRITING REFERENCED PROPERTIES");
//				buffer.rewind();
//				while (buffer.hasRemaining())
//					System.out.print(Integer.toHexString(buffer.get()) + " ");
//				System.out.println();
				
				buffer.rewind();
				dos.write(buffer.array());
			}
			catch (IOException ioe) {
				System.err.println("IO exception thrown when trying to write refistered properties: " + ioe.getMessage());
				ioe.printStackTrace();
			}
			finally {
				if (dos != null)
					dos.close();
			}
			
			// TODO
		}
		
		private final static void writeRootProperties(
				DocumentOutputStream dos) {
			
			try {
				ByteBuffer buffer = ByteBuffer.allocate(70);
				buffer.order(ByteOrder.BIG_ENDIAN);
				
				buffer.put((byte) 'B'); // Byte order
				buffer.put((byte) 32); // Version
				buffer.putShort((short) 2); // Property count
				
				buffer.putShort((short) 1); // MetaDictionary-1
				buffer.putShort(SF_STRONG_OBJECT_REFERENCE); // Object ref type
				buffer.putShort((short) (META_DICTIONARY_DIRNAME.length() * 2 + 2));
				
				buffer.putShort((short) 2); // Preface-2
				buffer.putShort(SF_STRONG_OBJECT_REFERENCE);
				buffer.putShort((short) (PREFACE_DIRNAME.length() * 2 + 2));
				
				PropertyValue stringToWrite = TypeDefinitions.UTF16String.createValue(META_DICTIONARY_DIRNAME);
				TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(stringToWrite, buffer);
				
				stringToWrite = TypeDefinitions.UTF16String.createValue(PREFACE_DIRNAME);
				TypeDefinitions.UTF16String.writeAsStructuredStorageBytes(stringToWrite, buffer);
				
				buffer.rewind();
				dos.write(buffer.array());
			}
			catch (InsufficientSpaceException ise) {
				System.err.println("Insufficient space to write the root properties strucutre.");
				ise.printStackTrace();
			}
			catch (IOException ioe) {
				System.err.println("IO exception thrown when writing the root properties: " + ioe.getMessage());
				ioe.printStackTrace();
			}
			finally {
				if (dos != null)
					dos.close();
			}
		}
		
		private void encodeIndex(
				POIFSWriterEvent event) {
			
			DocumentOutputStream dos = event.getStream();
			
			int count = 0;
			
			try {
				ByteBuffer buffer = ByteBuffer.allocate(event.getLimit());
				buffer.order(ByteOrder.BIG_ENDIAN);

				PropertyValue value = indexMap.get(event.getPath().toString() + File.separator + event.getName());
				
				if ((value.getType().getTypeCategory() == TypeCategory.VariableArray) && 
						(!(event.getName().startsWith("Parameters-b03")))) {
				
					TypeDefinitionVariableArray variableArrayType = (TypeDefinitionVariableArray) value.getType();
					TypeDefinition arrayRefType = variableArrayType.getType();
					
					switch (arrayRefType.getTypeCategory()) {
					
					case StrongObjRef:
//						System.out.println("*** STRONG REFERENCE VECTOR INDEX ***");
						count = variableArrayType.getCount(value);
						buffer.putInt(count);
						buffer.putInt(count);
						buffer.putInt(-1);
						for ( int x = 0 ; x < count ; x++ )
							buffer.putInt(x);
						break;
					case WeakObjRef:
//						System.out.println("*** WEAK REFERENCE VECTOR INDEX ***");
						
						count = variableArrayType.getCount(value);
						buffer.putInt(count);
						buffer.putShort(refPropertyMap.get(arrayRefType).shortValue());
						
						// TODO brittle for empty arrays
						List<PropertyValue> elements = variableArrayType.getElements(value);
						PropertyValue sampleValue = elements.get(0);
						
						ClassDefinition referencedValueClass = 
							((TypeDefinitionWeakObjectReference) arrayRefType).getObjectType();
						byte identificationSize = 0;
						PropertyDefinition uniqueProperty = null;
						if (referencedValueClass.isUniquelyIdentified()) {
							uniqueProperty = referencedValueClass.getUniqueIdentifierProperty();
							buffer.putShort(uniqueProperty.getLocalIdentification());
							PropertyValue sampleIDValue = uniqueProperty.getPropertyValue((MetadataObject) sampleValue.getValue());
							identificationSize = (byte) uniqueProperty.getTypeDefinition().lengthAsBytes(sampleIDValue);
						}
						else {
							System.err.println("Warning: When writing AAF, found an element of a weak reference vector that does not have an identifier: Type " + 
									referencedValueClass.getName());
							buffer.putShort((short) 0);
							identificationSize = 0;
						}
						buffer.put(identificationSize);
						
						for ( PropertyValue elementValue : elements ) {
							try {
								PropertyValue identityValue = 
									uniqueProperty.getPropertyValue((MetadataObject) elementValue.getValue());
								identityValue.getType().writeAsStructuredStorageBytes(identityValue, buffer);
							}
							catch (Exception e) {
								for ( int y = 0 ; y < identificationSize ; y++ )
									buffer.put((byte) 0);
							}
						}

						break;
					default:
						break;
					}
				}
				else {
					// Deal with the special case of parameters being encoded as a strong referenced set
					TypeDefinition setRefType = 
						(value.getType().getTypeCategory() == TypeCategory.VariableArray) ?
								((TypeDefinitionVariableArray) value.getType()).getType() :
									((TypeDefinitionSet) value.getType()).getElementType();
					
					switch (setRefType.getTypeCategory()) {
					
					case StrongObjRef:
//						System.out.println("*** STRONG REFERENCE SET INDEX ***");
						boolean isInterchange = interchangeObjectPath.equals(event.getPath().toString());
						
						count = (value.getType().getTypeCategory() == TypeCategory.VariableArray) ?
								((TypeDefinitionVariableArray) value.getType()).getCount(value) :
									((TypeDefinitionSet) value.getType()).getCount(value);
						
						if (isInterchange) count--;
								
						buffer.putInt(count);
						buffer.putInt(count); // first free
						buffer.putInt(-1); // last free
						
						// TODO Brittle for empty sets
						Collection<PropertyValue> elements = 
							(value.getType().getTypeCategory() == TypeCategory.VariableArray) ?
								((TypeDefinitionVariableArray) value.getType()).getElements(value) :
									((TypeDefinitionSet) value.getType()).getElements(value);
								
						if (isInterchange) {
							PropertyValue toRemove = null;
							for ( PropertyValue element : elements ) {
								if (((PropertyDefinition) element.getValue()).getAUID().equals(ApplicationPluginsID)) {
									toRemove = element;
									break;
								}
							}
							if (toRemove != null)
								elements.remove(toRemove);
						}
						
						PropertyValue sampleValue = null;
						for ( PropertyValue findMeASample : elements ) {
							sampleValue = findMeASample;
							break;
						}
						
						ClassDefinition referencedValueClass = 
							((TypeDefinitionStrongObjectReference) setRefType).getObjectType();
						byte identificationSize = 0;
						PropertyDefinition uniqueProperty = null;
						if (referencedValueClass.isUniquelyIdentified()) {
							uniqueProperty = referencedValueClass.getUniqueIdentifierProperty();
							buffer.putShort(uniqueProperty.getLocalIdentification()); // TODO what about dynamic properties
							if (sampleValue != null) {
								PropertyValue sampleIDValue = 
									uniqueProperty.getPropertyValue((MetadataObject) sampleValue.getValue());
								identificationSize = (byte) uniqueProperty.getTypeDefinition().lengthAsBytes(sampleIDValue);
							}
							else {
								if (uniqueProperty.getTypeDefinition().equals(TypeDefinitions.PackageIDType))
									identificationSize = 32;
								else
									identificationSize = 16;
							}
						}
						else if (sampleValue.getValue() instanceof Parameter) {
							buffer.putShort((short) 0x1b01); 
							identificationSize = 16;
						}
						else {
							System.err.println("Warning: When writing AAF, found an element of a strong reference set that does not have an identifier: Type " + 
									referencedValueClass.getName());
							buffer.putShort((short) 0);
							identificationSize = 0;
						}
						buffer.put(identificationSize);
						
						int x = 0;
						for ( PropertyValue elementValue : elements ) {
							buffer.putInt(x);
							buffer.putInt(1); // reference count fixed
							try {
								PropertyValue identityValue = null;
								if (elementValue.getValue() instanceof Parameter) {
									identityValue = TypeDefinitions.AUID.createValue(
											((Parameter) elementValue.getValue()).getParameterDefinitionReference());
								}
								else {
									identityValue = 
										uniqueProperty.getPropertyValue((MetadataObject) elementValue.getValue());
								}
								identityValue.getType().writeAsStructuredStorageBytes(identityValue, buffer);
							}
							catch (Exception e) { 
								for ( int y = 0 ; y < identificationSize ; y++)
									buffer.put((byte) 0);
							}
							x++;
						}
						break;
					case WeakObjRef:
//						System.out.println("*** WEAK REFERENCE SET INDEX ***");
						TypeDefinitionSet setType = (TypeDefinitionSet) value.getType();
						
						count = setType.getCount(value);
						buffer.putInt(count);
						buffer.putShort(refPropertyMap.get(setRefType).shortValue());
						
						Set<PropertyValue> weakElements = setType.getElements(value);
						PropertyValue weakSampleValue = null;
						for ( PropertyValue findASampleValue : weakElements ) {
							weakSampleValue = findASampleValue;
							break;
						}
						
						ClassDefinition weakReferencedValueClass = 
							((TypeDefinitionWeakObjectReference) setRefType).getObjectType();
						byte weakIdentificationSize = 0;
						PropertyDefinition weakUniqueProperty = null;
						if (weakReferencedValueClass.isUniquelyIdentified()) {
							weakUniqueProperty = weakReferencedValueClass.getUniqueIdentifierProperty();
							buffer.putShort(weakUniqueProperty.getLocalIdentification());
							PropertyValue sampleIDValue = 
								weakUniqueProperty.getPropertyValue((MetadataObject) weakSampleValue.getValue());
							if (weakSampleValue != null)
								weakIdentificationSize = 
									(byte) weakUniqueProperty.getTypeDefinition().lengthAsBytes(sampleIDValue); 
						}
						else {
							System.err.println("Warning: When writing AAF, found an element of a weak reference vector that does not have an identifier: Type " + 
									weakReferencedValueClass.getName());
							buffer.putShort((short) 0);
							weakIdentificationSize = 0;
						}
						buffer.put(weakIdentificationSize);
						
						for ( PropertyValue elementValue : weakElements ) {
							try {
								PropertyValue identityValue = 
									weakUniqueProperty.getPropertyValue((MetadataObject) elementValue.getValue());
								identityValue.getType().writeAsStructuredStorageBytes(identityValue, buffer);
							}
							catch (Exception e) {
								for ( int y = 0 ; y < weakIdentificationSize ; y++ )
									buffer.put((byte) 0);
							}
						}
						buffer.rewind();
						
//						System.out.println("*** WRITING INDEX ***");
//						while (buffer.hasRemaining())
//							System.out.print(Integer.toHexString(buffer.get()) + " ");
//						System.out.println();
			
						break;
					default:
						break;
					}	
				}
							
				buffer.rewind();
				dos.write(buffer.array());
			}
			catch (IOException ioe) {
				System.err.println("IO excetion thrown when writing index with path " + event.getPath().toString() + ": " +
						ioe.getMessage());
				ioe.printStackTrace();
			}
			finally {
				if (dos != null)
					dos.close();
			}
		}
		
		private void encodeStream(
				POIFSWriterEvent event,
				Stream stream) {
		
			DocumentOutputStream dos = event.getStream();
					
			try {
				stream.setPosition(0l);
				for ( long x = STREAM_CHUNK_SIZE ; x < stream.getLength() ; x += STREAM_CHUNK_SIZE )
					dos.write(stream.read(STREAM_CHUNK_SIZE).array());
				dos.write(stream.read((int) (stream.getLength()-stream.getPosition())).array());
			}
			catch (IOException ioe) {
				System.err.println("IO excetion thrown when writing stream with path " + event.getPath().toString() + ": " +
						ioe.getMessage());
				ioe.printStackTrace();
			}
			finally {
				if (dos != null)
					dos.close();
			}
		}
		
		public void addProperty(
				String path,
				PropertyDefinition propertyDefinition,
				PropertyValue propertyValue) 
			throws NullPointerException,
				IllegalPropertyValueException {
			
			if (path == null)
				throw new NullPointerException("Cannot add a property for AAF writing using a null path.");
			if (propertyDefinition == null)
				throw new NullPointerException("Cannot add a property for AAF writing using a null property definition.");
			if (propertyValue == null)
				throw new NullPointerException("Cannot add a property for AAF writing using a null property value.");
		
			// TODO check this
//			if (!propertyDefinition.getTypeDefinition().equals(propertyValue.getType()))
//				throw new IllegalPropertyValueException("The given property value does not match the type of the property definition.");
			
			if (!pathPropertyMap.containsKey(path)) {
				SortedMap<PropertyDefinition, PropertyValue> createdMap = 
					new TreeMap<PropertyDefinition, PropertyValue>();
				pathPropertyMap.put(path, createdMap);
			}
			
			SortedMap<PropertyDefinition, PropertyValue> mapToAddTo =
				pathPropertyMap.get(path);
			mapToAddTo.put(propertyDefinition, propertyValue);
		}

//		public void addPropertyMap(
//				String path,
//				SortedMap<PropertyDefinition, PropertyValue> propertyMap)
//			throws NullPointerException {
//
//			if (path == null)
//				throw new NullPointerException("Cannot add a property map to an AAF writer using a null path.");
//			if (propertyMap == null)
//				throw new NullPointerException("Cannot add a property map to an AAF writier using a null property map.");
//				
//			pathPropertyMap.put(path, propertyMap);
//		}
		
		synchronized public int registerWeakType(
				TypeDefinitionWeakObjectReference weakType) {
			
			if (weakType == null)
				throw new NullPointerException("Cannot register a weak type with an AAF writer using a null type definition.");
			
			if (weakType.equals(TypeDefinitions.ExtensionSchemeWeakReference))
				return 0;
			
			if (!refPropertyMap.containsKey(weakType)) {
				refPropertyMap.put(weakType, refPropertyIndexCount);
				refPropertyIndexCount++;
			}
			
			return refPropertyMap.get(weakType);
		}

		public int getReferencedPropertiesSize() {

			int regPropSize = 7;
			
			for ( TypeDefinitionWeakObjectReference weakType : refPropertyMap.keySet() ) {
				regPropSize += weakType.getTargetSet().length * 2 + 2;
			}
			
			return regPropSize;
		}
		
		public void addIndexValue(
				String indexPath,
				PropertyValue value)
			throws NullPointerException {
			
			if (indexPath == null)
				throw new NullPointerException("Cannot add a new index value using a null index path.");
			if (value == null)
				throw new NullPointerException("Cannot add a new index value using a null value.");
			
			indexMap.put(indexPath, value);
		}
		
		private static short nextDynamicID = -1;
		
		public void registerLocalID(
				PropertyDefinition property) {
			
			registerLocalID(property.getAUID(), property.getLocalIdentification());
		}
		
		public synchronized void registerLocalID(
				AUID propertyID,
				short localID) {
			
			if (localIDTable.containsKey(propertyID))
					return;
			
			if (localID <= 0) 
					localID = nextDynamicID--;
			
//			System.err.println("Registering property " + propertyID + " with ID " + localID);
			localIDTable.put(propertyID, localID);
		}
		
		public short getLocalID(
				AUID propertyID) {
			
			return localIDTable.get(propertyID);
		}

		public void registerStreamDocument(
				DocumentEntry streamDocument,
				Stream stream) 
			throws NullPointerException {
	
			if (streamDocument == null)
				System.err.println("Cannot register a stream to write with a null stream document.");
			if (stream == null)
				System.err.println("Cannot register a stream to write with a null stream.");
			
			String fullStreamPath = makePathForDirectory(streamDocument.getParent()) + File.separator + streamDocument.getName();
			streamMap.put(fullStreamPath, stream);
		}

		public void setInterchangePath(
				String path) 
			throws NullPointerException {

			if (path == null)
				throw new NullPointerException("Cannot set the interchange object definition path using a null value.");
			
			interchangeObjectPath = path;
		}
	}
	
	private final static StringBuffer shortenName(
			PropertyDefinition property,
			short localID,
			int mangledLength) 
		throws NullPointerException {
		
		if (property == null)
			throw new NullPointerException("Cannot shorten to the AAF structured storage name of a null property definition.");
		
		// TODO consider a lookup table for performance
		StringBuffer pathPartName = new StringBuffer(32);
		
		String propertyName = property.getName();
		if ((MAP_SS_DIRECTORY_NAMES) && (directoryNameTable.containsKey(propertyName))) {
			propertyName = directoryNameTable.get(propertyName);
		}
		int propertyNameLength = propertyName.length();
		
		String localIDinHex = Integer.toHexString(localID);
		int localIDinHexLength = localIDinHex.length();
		if (localIDinHexLength > 4) {
			localIDinHex = localIDinHex.substring(localIDinHexLength - 4, localIDinHexLength);
			localIDinHexLength = 4;
		}
		
		int squeezedLength = mangledLength - localIDinHexLength - 2;
		if (propertyNameLength > squeezedLength) {
			int halfLength = squeezedLength / 2;
			pathPartName.append(propertyName.substring(0, halfLength));
			pathPartName.append('-');
			pathPartName.append(propertyName.substring(propertyNameLength - halfLength + 1));
		}
		else {
			pathPartName.append(propertyName);
		}
		
		pathPartName.append('-');
		pathPartName.append(localIDinHex);
		
		return pathPartName;

	}
	
	public final static String makeAAFPathPartName(
			PropertyDefinition property) 
		throws NullPointerException {
		
		return shortenName(property, property.getLocalIdentification(), 32).toString();
	}
	
	public final static String makeAAFPathPartName(
			PropertyDefinition property,
			short localID)
		throws NullPointerException {
		
		return shortenName(property, localID, 32).toString();
	}
			
	
	public final static String makeAAFPathPartName(
			PropertyDefinition property,
			short localID,
			int index) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (index < 0)
			throw new IllegalArgumentException("Cannot create a shortenned name with a negative index value.");
		
		StringBuffer pathPartName = shortenName(property, localID, 22);
		
		pathPartName.append('{');
		pathPartName.append(Integer.toHexString(index));
		pathPartName.append('}');
		
		return pathPartName.toString();
	}
	
	public final static String makeAAFPathPartReference(
			PropertyDefinition property,
			short localID)
		throws NullPointerException {
		
		return shortenName(property, localID, 22).toString();
	}
	
	public final static void generateAAFStructure(
			DirectoryEntry classDir,
			AAFWriterListener aafWriter,
			MetadataObject rootObject) 
		throws IOException {
			
		ClassDefinition rootClass = MediaEngine.getClassDefinition(rootObject);
		
//		if (rootClass instanceof PropertyDefinition)
//			aafWriter.registerLocalID((PropertyDefinition) rootClass);
		
		String path = makePathForDirectory(classDir);
		classDir.setStorageClsid(new AAFClassID(rootClass.getAUID()));
		
//		System.out.println(path + " " + classDir.getStorageClsid());
//		System.out.println(path + File.separator + "properties");
		
		Set<PropertyDefinition> properties = rootClass.getAllPropertyDefinitions();
		if (rootObject instanceof MetaDefinition)
			((MetaDefinition) rootObject).setAAFNamesInUse(true);
		SortedMap<? extends PropertyDefinition, ? extends PropertyValue> valueMap = 
			rootClass.getProperties(rootObject);
		if (rootObject instanceof MetaDefinition)
			((MetaDefinition) rootObject).setAAFNamesInUse(false);
		
		int propertyCount = 0;
		int propertiesSize = 0;
		for ( PropertyDefinition property : properties) {
			
			// No need to process optional not present values
			if (property.getIsOptional()) {
				if (!valueMap.containsKey(property))
					continue;
			}
			
			// No need to write object class property
			if (property.getAUID().equals(ObjectClassID))
				continue;
			
			// Not interested in member of properties in AAF serialization
			if (property.getAUID().equals(MemberOfID))
				continue;
					
			// Check to see if we don't have a value for everything else
			if (!valueMap.containsKey(property)) {
				System.err.println("Found a required property not in the value map for " + rootClass.getName() + "." +
						property.getName() + ".");
				System.err.println(rootObject.toString());
				continue;
			}
			
			TypeDefinition propertyType = property.getTypeDefinition();
			PropertyValue value = valueMap.get(property);
			
			if (propertyType.equals(TypeDefinitions.ApplicationPluginObjectStrongReferenceSet)) {

				Set<PropertyValue> setElements = ((TypeDefinitionSet) propertyType).getElements(value);
				
				if (setElements.size() == 1) {
					// Possibly an extension class
					PropertyValue theElement = null;
					for ( PropertyValue item : setElements ) theElement = item;
					ApplicationPluginObject plugin = (ApplicationPluginObject) theElement.getValue();
					if (!(plugin.getObjectClass().getAUID().equals(AAFConstants.ApplicationPluginObjectID))) {
						classDir.setStorageClsid(new AAFClassID(plugin.getObjectClass().getAUID()));
					}
				}
				
				for ( PropertyValue item : setElements ) {
					// TODO unnecessarily defensive?
					if (!(item.getValue() instanceof ApplicationPluginObject)) continue;
					
					ApplicationPluginObject plugin = (ApplicationPluginObject) item.getValue();
					if (plugin == null) continue;
					
					Set<AUID> extensionPropertyIDs = plugin.getExtensionPropertyIDs();
					for ( AUID extensionPropertyID : extensionPropertyIDs ) {
						PropertyDefinition extensionPropertyDefinition = Warehouse.lookForProperty(extensionPropertyID);
						PropertyValue extensionPropertyValue = plugin.getExtensionProperty(extensionPropertyID);
						
						if ((extensionPropertyDefinition != null) && (extensionPropertyValue != null)) {
							propertyCount++;
							aafWriter.registerLocalID(extensionPropertyDefinition);
							aafWriter.addProperty(path, extensionPropertyDefinition, extensionPropertyValue);
							propertiesSize += processPropertyValue(extensionPropertyDefinition, extensionPropertyValue, classDir, aafWriter, path);
						}
					}
				}
				continue;
			}
			
			aafWriter.registerLocalID(property);
			
			aafWriter.addProperty(path, property, value);
			propertyCount++;
			
			propertiesSize += processPropertyValue(property, value, classDir, aafWriter, path);
		}
		
		propertiesSize += 4 + 6 * propertyCount;
		classDir.createDocument(PROPERTIES_STREAMNAME, propertiesSize, aafWriter);		
	}
	
	final static int processPropertyValue(
			PropertyDefinition property,
			PropertyValue value,			
			DirectoryEntry classDir,
			AAFWriterListener aafWriter,
			String path) 
		throws IOException {
		
		int propertySize = 0;
		TypeDefinition propertyType = value.getType();
		String dirName = null;
				
		if (property.getAUID().equals(AAFConstants.ParametersID)) {
			
//			TypeDefinition setRefType = ((TypeDefinitionVariableArray) propertyType).getType();

			List<PropertyValue> setElements = ((TypeDefinitionVariableArray) propertyType).getElements(value);

			dirName = makeAAFPathPartReference(property, aafWriter.getLocalID(property.getAUID()));
			propertySize += dirName.length() * 2 + 2;
			
			int elementCount = 0;

			byte identificationSize = 16;
			
			classDir.createDocument(dirName + " index", 15 + ((8 + identificationSize) * setElements.size()), aafWriter); 
			aafWriter.addIndexValue(path + File.separator + dirName + " index", value);
			
			for ( PropertyValue setElement : setElements) {

				DirectoryEntry newSetDir = 
					classDir.createDirectory(dirName + "{" + Integer.toHexString(elementCount) + "}");
				generateAAFStructure(newSetDir, aafWriter, (MetadataObject) setElement.getValue());

				elementCount++;
			}
			
			return propertySize;
		}
		
		switch (propertyType.getTypeCategory()) {
		
		case WeakObjRef:
			propertySize += 21;
			aafWriter.registerWeakType((TypeDefinitionWeakObjectReference) propertyType);
			break;
		case StrongObjRef:
			MetadataObject propertyValue = (MetadataObject) value.getValue();
			dirName = makeAAFPathPartName(property, aafWriter.getLocalID(property.getAUID()));
			propertySize += dirName.length() * 2 + 2;

			DirectoryEntry newDir = classDir.createDirectory(dirName);
			generateAAFStructure(newDir, aafWriter, propertyValue);
			break;
			
		case Set:				
			TypeDefinition setRefType = ((TypeDefinitionSet) propertyType).getElementType();

			Set<PropertyValue> setElements = ((TypeDefinitionSet) propertyType).getElements(value);
			
			if (property.getAUID().equals(PropertiesID)) {
				PropertyValue toRemove = null;
				for ( PropertyValue element : setElements ) {
					if (((PropertyDefinition) element.getValue()).getAUID().equals(ApplicationPluginsID)) {
						toRemove = element;
						break;
					}
				}
				
				if (toRemove != null) {
					setElements.remove(toRemove);
					aafWriter.setInterchangePath(path);
				}
			}
			
			if ((setRefType.getTypeCategory() != TypeCategory.StrongObjRef) &&
					(setRefType.getTypeCategory() != TypeCategory.WeakObjRef)) {
				
				if (setElements.size() == 0) break;
				
				PropertyValue sampleValue = null;
				for ( PropertyValue findMeASample : setElements ) {
					sampleValue = findMeASample;
					break;
				}
				
				propertySize += setElements.size() * setRefType.lengthAsBytes(sampleValue);
				break;
			}

			dirName = makeAAFPathPartReference(property, aafWriter.getLocalID(property.getAUID()));
			propertySize += dirName.length() * 2 + 2;
			
			int elementCount = 0;

			if (setRefType.getTypeCategory() == TypeCategory.StrongObjRef) {
				
				PropertyValue sampleValue = null;
				for ( PropertyValue findMeASample : setElements ) {
					sampleValue = findMeASample;
					break;
				}
				
				ClassDefinition referencedValueClass = 
					((TypeDefinitionStrongObjectReference) setRefType).getObjectType();
				byte identificationSize = 0;
				PropertyDefinition uniqueProperty = null;
				if ((setElements.size() > 0) && (referencedValueClass.isUniquelyIdentified())) {
					uniqueProperty = referencedValueClass.getUniqueIdentifierProperty();
					PropertyValue sampleIDValue = uniqueProperty.getPropertyValue((MetadataObject) sampleValue.getValue());
					identificationSize = (byte) uniqueProperty.getTypeDefinition().lengthAsBytes(sampleIDValue);
				}
				else {
					identificationSize = 0;
				}

				classDir.createDocument(dirName + " index", 15 + ((8 + identificationSize) * setElements.size()), aafWriter); 
				aafWriter.addIndexValue(path + File.separator + dirName + " index", value);
			}
			
			if (setRefType.getTypeCategory() == TypeCategory.WeakObjRef) {
				classDir.createDocument(dirName + " index", 9 + 16 * setElements.size(), aafWriter);
				aafWriter.addIndexValue(path + File.separator + dirName + " index", value);
			}
			
			for ( PropertyValue setElement : setElements) {

				switch (setRefType.getTypeCategory()) {

				case StrongObjRef:					
					DirectoryEntry newSetDir = 
						classDir.createDirectory(dirName + "{" + Integer.toHexString(elementCount) + "}");
					generateAAFStructure(newSetDir, aafWriter, (MetadataObject) setElement.getValue());
					break;
				case WeakObjRef:
					aafWriter.registerWeakType((TypeDefinitionWeakObjectReference) setRefType);
					break;
				default:
					break;
				}
				elementCount++;
			}
			
			break;
			
		case VariableArray:				
			TypeDefinition arrayRefType = ((TypeDefinitionVariableArray) propertyType).getType();
			
			List<PropertyValue> listElements = ((TypeDefinitionVariableArray) propertyType).getElements(value);
			
//			if ((arrayRefType.getTypeCategory() != TypeCategory.StrongObjRef) &&
//					(arrayRefType.getTypeCategory() != TypeCategory.WeakObjRef)) {
//				
//				if (listElements.size() == 0) return;
//				
//				propertiesSize += listElements.size() * arrayRefType.lengthAsBytes(listElements.get(0));
//				break;
//			}
			
			if (arrayRefType.getTypeCategory() == TypeCategory.StrongObjRef) {
				dirName = makeAAFPathPartReference(property, aafWriter.getLocalID(property.getAUID()));
				propertySize += dirName.length() * 2 + 2;
				classDir.createDocument(dirName + " index", 12 + 4 * listElements.size(), aafWriter); // TODO size
				aafWriter.addIndexValue(path + File.separator + dirName + " index", value);
			}
			if (arrayRefType.getTypeCategory() == TypeCategory.WeakObjRef) {
				dirName = makeAAFPathPartReference(property, aafWriter.getLocalID(property.getAUID()));
				propertySize += dirName.length() * 2 + 2;
				classDir.createDocument(dirName + " index", 9 + 16 * listElements.size(), aafWriter);
				aafWriter.addIndexValue(path + File.separator + dirName + " index", value);
			}
			
			for ( int x = 0 ; x < listElements.size() ; x++ ) {
				
				PropertyValue listElement = listElements.get(x);
			
				switch (arrayRefType.getTypeCategory()) {

				case StrongObjRef:
					DirectoryEntry newArrayDir = 
						classDir.createDirectory(dirName + "{" + Integer.toHexString(x) + "}");
					generateAAFStructure(newArrayDir, aafWriter, (MetadataObject) listElement.getValue());
					break;
				case WeakObjRef:
					aafWriter.registerWeakType((TypeDefinitionWeakObjectReference) arrayRefType);
					break;
				case Character:
					// TODO Assuming UTF16String
					propertySize += ((String) listElement.getValue()).length() * 2 + 2;					
					break;
				default:
					propertySize += listElement.getType().lengthAsBytes(listElement);
					break;
				} // switch
			} // for
			break;
			
		case Indirect:
			propertySize += value.getType().lengthAsBytes(value) + 1; // for the mystery 4c
			break;
			
		case Stream:
			dirName = makeAAFPathPartName(property, aafWriter.getLocalID(property.getAUID()));
			Stream stream = (Stream) value.getValue();
			DocumentEntry streamDocument = classDir.createDocument(dirName, (int) stream.getLength(), aafWriter);
			
			aafWriter.registerStreamDocument(streamDocument, stream);
			
			propertySize += dirName.length() * 2 + 2 + 1;
			break;
		
		default:
			if (property.getAUID().equals(ByteOrderPropertyID))
				propertySize += 2;
			else
				propertySize += value.getType().lengthAsBytes(value);
			break;
		}
		
		return propertySize;
	}
	
	public final static void generateMetaDictionary(
			DirectoryEntry metaDir,
			AAFWriterListener aafWriter,
			Preface preface) 
		throws IOException {
		
		PropertyDefinitionImpl.initalizePropertyNameMap();
		ClassDefinitionImpl.initalizeClassNameMap();
		TypeDefinitionImpl.initalizeTypeNameMap();
		MetaDictionary metaDictionary = Forge.make(MetaDictionary.class);
		
		metaDictionary.makeDynamic(preface);
				
		generateAAFStructure(metaDir, aafWriter, metaDictionary);
	}
	
	private final static String makePathForDirectory(
			DirectoryEntry dir) {
		
		StringBuffer fullPath = new StringBuffer(256);
		fullPath.append(File.separator);
		fullPath.append(dir.getName());
		
		DirectoryEntry parentDir = dir.getParent();
		while (!parentDir.getName().equals("Root Entry")) {
			fullPath.insert(0, parentDir.getName());
			fullPath.insert(0, File.separator);
			parentDir = parentDir.getParent();
		}
		
		return fullPath.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
		throws IOException {

		if (args.length != 2)
			System.exit(1);
		
		final String inputFilename = args[0];
		final String outputFilename = args[1];
		
		POIFSReader r = new AAFReader();
	   
	    LocalAAFEventReader eventReader = new LocalAAFEventReader();
	    r.registerListener(eventReader);
	    
	    AvidFactory.registerAvidExtensions();
	    
	    FileInputStream fis = null;
	    
	    long startTime = System.nanoTime();
	    try {
	    	fis = new FileInputStream(inputFilename);
		    r.read(new FileInputStream(inputFilename));
		    eventReader.resolveEntries();
	    }
	    finally {
	    	if (fis != null)
	    		try { fis.close(); } catch (Exception e) { }
	    }
	    long endTime = System.nanoTime();
	    
	    System.out.println("AAF file read in " + (endTime - startTime) + "ns.");

	    ((PrefaceImpl) eventReader.getPreface()).setByteOrder(tv.amwa.maj.enumeration.ByteOrder.Big);
	    System.out.println(eventReader.getPreface().getByteOrder());

	    POIFSFileSystem outputFileSystem = new POIFSFileSystem();
	    
	    DirectoryEntry rootDir = outputFileSystem.getRoot();

	    AAFWriterListener aafWriter = makeAAFWriterListener();
	    DirectoryEntry metaDictionaryDir = rootDir.createDirectory(META_DICTIONARY_DIRNAME);
	    DirectoryEntry prefaceDir = rootDir.createDirectory(PREFACE_DIRNAME);
	    
	    generateAAFStructure(prefaceDir, aafWriter, eventReader.getPreface());
	    generateMetaDictionary(metaDictionaryDir, aafWriter, eventReader.getPreface());
	    rootDir.createDocument(PROPERTIES_STREAMNAME, 70, aafWriter);
	    rootDir.createDocument(REFERENCED_PROPERTIES_STREAMNAME, aafWriter.getReferencedPropertiesSize(), aafWriter);

//	    rootDir.setStorageClsid(
//	    		(outputFileSystem.getBigBlockSize() == 4096) ?
//	    				new AAFClassID(AAFSignatureSSBin4K) :
//	    					new AAFClassID(AAFSignatureSSBinary));

	    rootDir.setStorageClsid(new ClassID(rootEntryClassID, 0));
	    
	    FileOutputStream fos = null;
	    try {
	    	fos = new FileOutputStream(outputFilename);
	    	outputFileSystem.writeFilesystem(fos);
	    }
	    finally {
	    	if (fos != null)
	    		try { fos.close(); } catch (Exception e) { }
	    }
	    
	    // AAF puts a signature in bytes 8-23 of the file
	    // POIFS cannot write this
	    
	    RandomAccessFile fixFile = null;
	    FileChannel fixChannel = null;
		try {
			fixFile = new RandomAccessFile(outputFilename, "rw");
			fixChannel = fixFile.getChannel();

			fixChannel.position(8);
			if (outputFileSystem.getBigBlockSize() == 4096)
				fixChannel.write(ByteBuffer.wrap(AAFSignatureSSBin4KBytes));
			else
				fixChannel.write(ByteBuffer.wrap(AAFSignatureSSBinaryBytes));
		}
		finally {
			if (fixChannel != null) 
				fixChannel.close();
		}
	}
	
}
