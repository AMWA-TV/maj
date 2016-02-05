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
 * $Log: CodecDefinitionImpl.java,v $
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.3  2011/01/18 09:13:55  vizigoth
 * Fixes after writing Warehouse unit tests.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/03/19 09:40:57  vizigoth
 * Added support for lazy weak reference resolutions through forAUID method.
 *
 * Revision 1.2  2010/01/25 13:23:32  vizigoth
 * Workaround to avoid troublesome name lookup for AAFFileDescriptor class.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.3  2008/01/27 11:14:43  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.2  2007/12/04 13:04:49  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import tv.amwa.maj.constant.CodecConstant;
import tv.amwa.maj.constant.CodecDescription;
import tv.amwa.maj.exception.DuplicateEssenceKindException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyContains;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyRemove;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceVector;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.AAFFileDescriptor;
import tv.amwa.maj.model.CodecDefinition;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.record.AUID;


/** 
 * <p>Implements the definition of an essence codec. A codec is a program or system capable of performing 
 * encoding and decoding on a digital data stream, which is often the case for the data streams represented 
 * by {@linkplain tv.amwa.maj.model.EssenceData essence data} in the MAJ API.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReferenceSet
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1f00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "CodecDefinition",
		  description = "The CodecDefinition specifies an essence codec.",
		  symbol = "CodecDefinition")
public class CodecDefinitionImpl
	extends DefinitionObjectImpl
	implements CodecDefinition,
			Serializable,
			CodecConstant,
			Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -715077536381810314L;
	
	private WeakReference<ClassDefinition> fileDescriptorClass;
	private WeakReferenceVector<DataDefinition> codecDataDefinitions =
		new WeakReferenceVector<DataDefinition>();
	// @SuppressWarnings("unused")
	// private Set<AUID> codecFlavours = null;

	private static final Map<String, CodecDefinition> definitionsByName =
		new HashMap<String, CodecDefinition>(20); 
	private static final Map<AUID, CodecDefinition> definitionsById =
		new HashMap<AUID, CodecDefinition>(10);
	
	static {
		registerCodecsFromClass(CodecConstant.class);
	}
	
	public final static int registerCodecsFromClass(
			Class<?> classWithCodecs) 
		throws NullPointerException {
		
		if (classWithCodecs == null)
			throw new NullPointerException("Cannot register parameter definitions using a null class.");
		
		int registered = 0;
		Field[] fields = classWithCodecs.getFields();
		for ( Field field : fields ) {
				
			int modifiers = field.getModifiers();
			if ((!Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) continue;
				
			try {
				Object value = field.get(null);
				if (!(value instanceof tv.amwa.maj.record.AUID)) continue;

				if (!(field.isAnnotationPresent(CodecDescription.class))) continue;
				
				CodecDescription codecMetadata = 
					field.getAnnotation(CodecDescription.class);	

				Set<tv.amwa.maj.model.DataDefinition> dataDefinitions = 
					new HashSet<tv.amwa.maj.model.DataDefinition>(2);
				for ( String dataDefName : codecMetadata.dataDefinitions() )
					dataDefinitions.add(DataDefinitionImpl.forName(dataDefName));
				
				ClassDefinition fileDescriptorClass;
				if (codecMetadata.fileDescriptorClass().equals(""))
					fileDescriptorClass = Warehouse.lookForClass(AAFFileDescriptorImpl.class);
				else
					fileDescriptorClass = Warehouse.lookForClass(
							codecMetadata.fileDescriptorClass());
				
				CodecDefinitionImpl definedCodec = new CodecDefinitionImpl(
						(tv.amwa.maj.record.AUID) value,
						field.getName(),
						fileDescriptorClass,
						dataDefinitions);
			
				if (codecMetadata.description().length() > 0)
					definedCodec.setDescription(codecMetadata.description());
		
				// System.out.println(definedCodec.toString());
				
				registerCodecDefinition(definedCodec);
			
				for ( String alias : codecMetadata.aliases() )
					definitionsByName.put(alias, definedCodec);
				
				registered++;
			}
			catch (IllegalAccessException iae) { /* Codec definition was not meant to be. */ }
		}

		return registered;
	}
	
	public final static boolean registerCodecDefinition(
			CodecDefinition definedCodec) 
		throws NullPointerException {
		
		if (definedCodec == null)
			throw new NullPointerException("Cannot register a new codec definition using a null value.");
		
		boolean alreadyContained = definitionsById.containsKey(definedCodec.getAUID());
		
		definitionsByName.put(definedCodec.getName(), definedCodec);
		definitionsByName.put("CodecDef_" + definedCodec.getName(), definedCodec);
		definitionsById.put(definedCodec.getAUID(), definedCodec);		

		return alreadyContained;
	}
	
	public final static CodecDefinition forName(
			String codecName) 
		throws NullPointerException {
		
		if (codecName == null)
			throw new NullPointerException("Cannot retrieve a codec definition with a null name.");
		
		return definitionsByName.get(codecName);
	}
	
	public final static CodecDefinition forIdentification(
			AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot retrieve a codec definition with a null identification.");
		
		return definitionsById.get(identification);
	}
	
	public final static CodecDefinition forAUID(
			AUID identification)
		throws NullPointerException {
		
		return forIdentification(identification);
	}

	public final static Collection<String> inventory() {
		
		SortedSet<String> inventory = new TreeSet<String>();
		for ( AUID definitionID : definitionsById.keySet()) {
			inventory.add(definitionsById.get(definitionID).getName());
		}
		
		return inventory;
	}
	
	public final static int count() {
		
		return definitionsById.size();
	}
	
	/** Default constructor is not public to avoid unset required fields. */
	public CodecDefinitionImpl() { }
	
	/**
	 * <p>Create and initialize all the fields of the codec definition object, which 
	 * specifies an essence codec. The set of data definitions for essence kinds processed 
	 * by the codec must contain at least one item, otherwise an {@link IllegalArgumentException}
	 * will be thrown.</p>
	 * 
	 * @param identification Unique identifier for new codec definition.
	 * @param name Display name for the new codec definition.
	 * @param fileDescriptorClass Class definition of the sub-class of file descriptor that identifies
	 * the essence format that this codec processes.
	 * @param dataDefinitions Data definitions of the essence formats that the new codec processes.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code>.
	 * @throws IllegalArgumentException Cannot crate a codec definition with and empty 
	 * set of data definitions.
	 */
	public CodecDefinitionImpl(
			tv.amwa.maj.record.AUID identification,
			@AAFString String name,
			tv.amwa.maj.meta.ClassDefinition fileDescriptorClass,
			Set<tv.amwa.maj.model.DataDefinition> dataDefinitions)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a codec definition with a null identification.");
		if (name == null)
			throw new NullPointerException("Cannot create a codec definition with a null name.");
		if (fileDescriptorClass == null)
			throw new NullPointerException("Cannot create a codec definition with a null file descriptor class.");
		if (dataDefinitions == null)
			throw new NullPointerException("Cannot create a codec definition with a null set of data definitions.");
		
		if (dataDefinitions.size() == 0)
			throw new IllegalArgumentException("Cannot create a codec definition with an empty set of data definitions.");
		
		setAUID(identification);
		setName(name);
		setFileDescriptorClass(fileDescriptorClass);
		
		for ( tv.amwa.maj.model.DataDefinition definition : dataDefinitions )
			if (definition != null) 
				this.codecDataDefinitions.append(definition);

		// this.codecFlavours = Collections.synchronizedSet(new HashSet<AUID>());
	}
	
	@MediaListAppend("CodecDataDefinitions")
	public void appendEssenceKind(
			DataDefinition essenceKind)
		throws NullPointerException,
			DuplicateEssenceKindException {

		if (essenceKind == null)
			throw new NullPointerException("Cannot add a null essence kind.");
		
		if (codecDataDefinitions.contains(essenceKind))
			throw new DuplicateEssenceKindException("The given essence kind is already represented in this codec definition.");

		codecDataDefinitions.append(essenceKind);
	}

	@MediaPropertyCount("CodecDataDefinitions")
	public int countEssenceKinds() {

		return codecDataDefinitions.count();
	}
	
	/** 
	 * <p>Returns <code>null</code> in this implementation.</p>
	 * 
	 * @see tv.amwa.maj.model.CodecDefinition#getCodecFlavours()
	 */
	public Set<AUID> getCodecFlavours() {

		return null;
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0301, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "CodecDataDefinitions",
			aliases = { "DataDefinitions", "CodecDefinitionDataDefinitions" },
			typeName = "DataDefinitionWeakReferenceVector",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x2302,
			symbol = "CodecDataDefinitions")
	public List<DataDefinition> getEssenceKinds() {

		return codecDataDefinitions.getRequiredList();
	}
	
	public final static List<DataDefinition> initializeCodecDataDefinitions() {
		
		List<DataDefinition> initialDefinitions = new ArrayList<DataDefinition>(1);
		initialDefinitions.add(DataDefinitionImpl.forName("Unknown"));
		return initialDefinitions;
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0107, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FileDescriptorClass",
			typeName = "ClassDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x2301,
			symbol = "FileDescriptorClass")
	public ClassDefinition getFileDescriptorClass() {

		return fileDescriptorClass.getTarget();
	}

	@MediaPropertySetter("FileDescriptorClass")
	public void setFileDescriptorClass(
			ClassDefinition fileClass)
		throws NullPointerException,
			IllegalArgumentException {

		if (fileClass == null)
			throw new NullPointerException("Cannot set the file descriptor class for this codec definition using a null value.");
		
		if (!AAFFileDescriptor.class.isAssignableFrom(fileClass.getJavaImplementation()))
			throw new IllegalArgumentException("The given file descriptor class " + fileClass.getName() + " for this codec definition is not a sub-class of file descriptor.");
		
		this.fileDescriptorClass = new WeakReference<ClassDefinition>(fileClass);
	}
	
	public final static ClassDefinition initializeFileDescriptorClass() {
		
		return Warehouse.lookForClass("SoundDescriptor");
	}

	@MediaPropertyContains("CodecDataDefinitions")
	public boolean isEssenceKindSupported(
			tv.amwa.maj.model.DataDefinition essenceKind)
		throws NullPointerException {

		return codecDataDefinitions.contains(essenceKind);
	}

	@MediaPropertyRemove("CodecDataDefinitions")
	public void removeEssenceKind(
			DataDefinition essenceKind)
		throws NullPointerException,
			ObjectNotFoundException,
			IllegalArgumentException {

		if (essenceKind == null)
			throw new NullPointerException("Cannot remove an essence kind from the codec's data definitions using a null value.");
		if (codecDataDefinitions.count() == 1) 
			throw new IllegalArgumentException("Cannot remove an essence kind as the set of data definitions cannot be empty.");
		
		if (!(codecDataDefinitions.contains(essenceKind)))
			throw new ObjectNotFoundException("Cannot remove the essence kind from the data definitions as it is not known.");
		
		codecDataDefinitions.remove(essenceKind);
	}
	
	@MediaPropertyClear("CodecDataDefinitions")
	public void clearEssenceKinds() {
		
		codecDataDefinitions.clear();
	}
	
	public CodecDefinitionImpl clone()  {
		
		return (CodecDefinitionImpl) super.clone();

	}
	
	@Override
	public String getWeakTargetReference() {
		
		if (getName().startsWith("CodecDef_")) 
			return getName();
		else
			return "CodecDef_" + getName();
	}

	@MediaListGetAt("CodecDataDefinitions")
	public DataDefinition getEssenceKindAt(
			int index)
		throws IndexOutOfBoundsException {

		return codecDataDefinitions.getAt(index);
	}

	@MediaListInsertAt("CodecDataDefinitions")
	public void insertEssenceKind(
			int index, 
			DataDefinition essenceKind)
		throws NullPointerException, 
			DuplicateEssenceKindException,
			IndexOutOfBoundsException {
		
		if (essenceKind == null)
			throw new NullPointerException("Cannot add a null essence kind.");
		
		if (codecDataDefinitions.contains(essenceKind))
			throw new DuplicateEssenceKindException("The given essence kind is already represented in this codec definition.");

		codecDataDefinitions.insert(index, essenceKind);
	}

	@MediaListPrepend("CodecDataDefinitions")
	public void prependEssenceKind(
			DataDefinition essenceKind)
		throws NullPointerException, 
			DuplicateEssenceKindException {

		if (essenceKind == null)
			throw new NullPointerException("Cannot add a null essence kind.");
		
		if (codecDataDefinitions.contains(essenceKind))
			throw new DuplicateEssenceKindException("The given essence kind is already represented in this codec definition.");

		codecDataDefinitions.prepend(essenceKind);
		
	}

	@MediaListRemoveAt("CodecDataDefinitions")
	public void removeEssenceKindAt(
			int index) 
		throws IndexOutOfBoundsException {
		
		codecDataDefinitions.removeAt(index);
	} 
	
}
