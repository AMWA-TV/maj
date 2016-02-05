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
 * $Log: OperationDefinitionImpl.java,v $
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
 * Revision 1.2  2010/03/19 09:40:57  vizigoth
 * Added support for lazy weak reference resolutions through forAUID method.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2007/12/04 13:04:48  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:45  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import tv.amwa.maj.constant.OperationConstant;
import tv.amwa.maj.constant.OperationDescription;
import tv.amwa.maj.exception.ObjectAlreadyAttachedException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceSet;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.industry.WeakReferenceVector;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.OperationDefinition;
import tv.amwa.maj.model.ParameterDefinition;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/** 
 * <p>Implements the definition of an operation that is performed on an 
 * array of {@linkplain tv.amwa.maj.model.Segment segments}. Operation definitions specify
 * which parameters are possible on an operation, while an
 * {@linkplain tv.amwa.maj.model.OperationGroup operation group} specifies specific parameters 
 * and input segments for a particular operation invocation.</p>
 * 
 * <p>THE COMMENTS FOR THIS CLASS ARE INCOMPLETE.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReferenceVector
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1c00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "OperationDefinition",
		  description = "The OperationDefinition class identifies an operation that is performed on an array of Segments.",
		  symbol = "OperationDefinition")
public class OperationDefinitionImpl
	extends 
		DefinitionObjectImpl
	implements 
		OperationDefinition,
		Serializable,
		OperationConstant,
		Cloneable,
		WeakReferenceTarget {

	/** <p></p> */
	private static final long serialVersionUID = -658385965548109802L;
	
	private WeakReference<DataDefinition> operationDataDefinition;
	private Boolean isTimeWarp = null;
	private WeakReferenceVector<tv.amwa.maj.model.OperationDefinition> degradeTo =
		new WeakReferenceVector<tv.amwa.maj.model.OperationDefinition>();
	private AUID operationCategory = null;
	private int operationInputCount;
	private Integer bypass = null;
	private WeakReferenceSet<ParameterDefinition> operationParametersDefined =
		new WeakReferenceSet<ParameterDefinition>(); // Empty set for optional

	private static final Map<String, OperationDefinition> definitionsByName =
		new HashMap<String, OperationDefinition>(100);
	private static final Map<AUID, OperationDefinition> definitionsById =
		new HashMap<AUID, OperationDefinition>(30);
	
	static {
		registerOperationsFromClass(OperationConstant.class);
	}
	
	// Order is important for degrade to
	
	public final static int registerOperationsFromClass(
			Class<?> classWithOperations) 
		throws NullPointerException {
		
		if (classWithOperations == null)
			throw new NullPointerException("Cannot register operation definitions using a null class.");
		
		int registered = 0;
		Field[] fields = classWithOperations.getFields();
		for ( Field field : fields ) {
				
			int modifiers = field.getModifiers();
			if ((!Modifier.isPublic(modifiers)) && (!Modifier.isStatic(modifiers))) continue;
				
			try {
				Object value = field.get(null);
				if (!(value instanceof tv.amwa.maj.record.AUID)) continue;

				if (!(field.isAnnotationPresent(OperationDescription.class))) continue;
				
				OperationDescription operationMetadata = 
					field.getAnnotation(OperationDescription.class);	

				OperationDefinitionImpl definedOperation = new OperationDefinitionImpl(
						(tv.amwa.maj.record.AUID) value,
						field.getName(),
						DataDefinitionImpl.forName(operationMetadata.dataDefinition()),
						operationMetadata.numberInputs());
				
				if (operationMetadata.description().length() > 0)
					definedOperation.setDescription(operationMetadata.description());

				if (operationMetadata.operationCategory().length() > 0)
					definedOperation.setOperationCategory(
							AUIDImpl.parseFactory(operationMetadata.operationCategory()));
				
				if (operationMetadata.bypass() != Integer.MIN_VALUE)
					definedOperation.setBypass(operationMetadata.bypass());
				else
					definedOperation.setBypass(null);
				
				for ( String parameterName : operationMetadata.parametersDefined() ) {
					
					ParameterDefinition parameter = ParameterDefinitionImpl.forName(parameterName);
					if (parameter != null)
						definedOperation.addParameterDefinition(parameter);
					else
						System.err.println("Could not find parameter " + parameterName + " for operation " + definedOperation.getName() + ".");
				}
		
				for ( String operationName : operationMetadata.degradeTo() ) {
					
					OperationDefinition operation = OperationDefinitionImpl.forName(operationName);
					if (operation != null)
						definedOperation.appendDegradeToOperation(operation);
					else
						System.err.println("Could not find degrade to operation " + operationName + " for operation " + definedOperation.getName() + ".");
				}
				
				definedOperation.setIsTimeWarp(operationMetadata.isTimeWarp());
				
				// System.out.println(definedOperation.toString());
				
				registerOperationDefinition(definedOperation);
			
				for ( String alias : operationMetadata.aliases() )
					definitionsByName.put(alias, definedOperation);
				
				registered++;
			}
			catch (IllegalAccessException iae) { /* Parameter definition was not meant to be. */ }
		}

		return registered;
	}
	
	public final static boolean registerOperationDefinition(
			OperationDefinition definedOperation) 
		throws NullPointerException {
		
		if (definedOperation == null)
			throw new NullPointerException("Cannot register a new operation definition using a null value.");
		
		boolean alreadyContained = definitionsById.containsKey(definedOperation.getAUID());
		
		definitionsByName.put(definedOperation.getName(), definedOperation);
		definitionsByName.put("OperationDef_" + definedOperation.getName(), definedOperation);
		definitionsById.put(definedOperation.getAUID(), definedOperation);		

		return alreadyContained;
	}
	
	public final static OperationDefinition forName(
			String operationName) 
		throws NullPointerException {
		
		if (operationName == null)
			throw new NullPointerException("Cannot retrieve a operation definition with a null name.");
		
		return definitionsByName.get(operationName);
	}
	
	public final static OperationDefinition forIdentification(
			tv.amwa.maj.record.AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot retrieve a operation definition with a null identification.");
		
		return definitionsById.get(identification);
	}
	
	public final static OperationDefinition forAUID(
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
	
	public OperationDefinitionImpl() { }

	/**
	 * <p>Creates and initializes a new operation definition, which identifies an operation 
	 * that is performed on an array of {@link SegmentImpl segments}.</p>
	 *
	 * @param identifier Unique identifier for the operation definition.
	 * @param name Display name of the operation definition.
	 * @param dataDefinition The kind of data that is produced by the operation.
	 * @param numInputs Number of input segments. A value of <code>-1</code> indicates that the
	 * effect can have any number of input segments.
	 * 
	 * @throws NullPointerException One or more of the arguments is <code>null</code> and they
	 * are all required properties.
	 */
	public OperationDefinitionImpl(
			AUID identifier,
			@AAFString String name,
			DataDefinition dataDefinition,
			int numInputs)
		throws NullPointerException {
		
		if (identifier == null)
			throw new NullPointerException("Cannot create an operation definition with a null id.");
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create an operation definition with a null data definition.");
		
		setAUID(identifier);
		setName(name);
		setOperationDataDefinition(dataDefinition);
		setOperationInputCount(numInputs);
	}

	@MediaSetAdd("OperationParametersDefined")
	public void addParameterDefinition(
			ParameterDefinition parameterDefinition)
		throws NullPointerException,
			ObjectAlreadyAttachedException {

		if (parameterDefinition == null)
			throw new NullPointerException("Cannot add a null parameter definition to this operation definition.");
		
		if (operationParametersDefined.contains(parameterDefinition))
			throw new ObjectAlreadyAttachedException("The given parameter definition is already attached to this operation definition.");
		
		operationParametersDefined.add(parameterDefinition);
	}

	@MediaListAppend("DegradeTo")
	public void appendDegradeToOperation(
			OperationDefinition operationDefinition)
		throws NullPointerException {

		if (operationDefinition == null)
			throw new NullPointerException("Cannot append a null operation to the list of degrade-to operations.");
		
		degradeTo.append(operationDefinition);
	}

	@MediaPropertyCount("DegradeTo")
	public int countDegradeToOperations() {

		return degradeTo.count();
	}
	
	@MediaPropertyClear("DegradeTo")
	public void clearDegradeToOperations() {
		
		degradeTo = new WeakReferenceVector<tv.amwa.maj.model.OperationDefinition>();
	}

	@MediaPropertyCount("OperationParametersDefined")
	public int countOperationParametersDefined() {

		return operationParametersDefined.count();
	}
	
	@MediaPropertyClear("OperationParametersDefined")
	public void clearOperationParametersDefined() {
		
		operationParametersDefined.clear();
	}

	// TODO conflict between spec and reference implementation about whether this 
	//      property is a 1-based index? Revise comment below.
	
	/** 
	 * <p>This is a 1-based index. A value of <code>0</code> indicates this optional property
	 * is not set.</p>
	 * 
	 * @see tv.amwa.maj.model.OperationDefinition#getBypass()
	 */
	@MediaProperty(uuid1 = 0x05300505, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "Bypass",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1E08,
			symbol = "Bypass")
	public int getBypass() 
		throws PropertyNotPresentException {

		if (bypass == null)
			throw new PropertyNotPresentException("The optional bypass property is not present in this operation definition.");
		
		return bypass;
	}

	@MediaPropertySetter("Bypass")
	public void setBypass(
			Integer bypass) {

		if ((bypass != null) && (bypass < 0))
			throw new IllegalArgumentException("Cannot set the bypass value for this operation definition to a negative value.");
		
		this.bypass = bypass;
	}
	
	@MediaProperty(uuid1 = 0x0530050a, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "OperationCategory",
			typeName = "OperationCategoryType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1E06,
			symbol = "OperationCategory")		
	public AUID getOperationCategory() 
		throws PropertyNotPresentException {

		if (operationCategory == null)
			throw new PropertyNotPresentException("The optional operation category is not set for this operation definition.");
		
		return operationCategory.clone();
	}

	@MediaPropertySetter("OperationCategory")
	public void setOperationCategory(
			AUID operationCategory) {

		if (operationCategory == null) {
			this.operationCategory = null;
			return;
		}
		
		this.operationCategory = operationCategory.clone();
	}

	@MediaProperty(uuid1 = 0x05300509, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "OperationDataDefinition", 
			aliases = { "DataDefinition", "OperationDefinitionDataDefinition" },
			typeName = "DataDefinitionWeakReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1E01,
			symbol = "OperationDataDefinition")
	public DataDefinition getOperationDataDefinition() {

		return operationDataDefinition.getTarget();
	}

	@MediaPropertySetter("OperationDataDefinition")
	public void setOperationDataDefinition(
			DataDefinition operationDdataDefinition)
		throws NullPointerException {

		if (operationDdataDefinition == null)
			throw new NullPointerException("Cannot set the data definition of the operation definition with a null value.");
		
		this.operationDataDefinition = new WeakReference<DataDefinition>(operationDdataDefinition);
	}
	
	public final static DataDefinition initializeOperationDataDefinition() {
		
		return DataDefinitionImpl.forName("Unknown");
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0401, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "DegradeTo",
			typeName = "OperationDefinitionWeakReferenceVector",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1E03,
			symbol = "DegradeTo")
	public List<OperationDefinition> getDegradeToOperations() 
		throws PropertyNotPresentException {
		
		if (degradeTo.count() == 0)
			throw new PropertyNotPresentException("No degrade to operations are present for this operation definition.");

		return degradeTo.getOptionalList();
	}

	@MediaProperty(uuid1 = 0x05300504, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "OperationInputCount",
			aliases = { "NumberInputs" },
			typeName = "Int32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1E07,
			symbol = "OperationInputCount")
	public int getOperationInputCount() {

		return operationInputCount;
	}

	@MediaPropertySetter("OperationInputCount")
	public void setOperationInputCount(
			int operationInputCount) {

		this.operationInputCount = operationInputCount;
	}
	
	public final static int initializeOperationInputCount() {
		
		return 0;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0302, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "OperationParametersDefined",
			aliases = { "ParametersDefined", "ParameterDefinitions" },
			typeName = "ParameterDefinitionWeakReferenceSet",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1E09,
			symbol = "OperationParametersDefined")
	public Set<ParameterDefinition> getOperationParametersDefined() 
		throws PropertyNotPresentException {
		
		if (operationParametersDefined.count() == 0)
			throw new PropertyNotPresentException("No parameter definitions are present in this operation definition.");

		return operationParametersDefined.getOptionalSet();
	}

	@MediaListInsertAt("DegradeTo")
	public void insertDegradeToOperationAt(
			int index,
			tv.amwa.maj.model.OperationDefinition operationDefinition)
		throws NullPointerException,
			IndexOutOfBoundsException {

		if (operationDefinition == null)
			throw new NullPointerException("Cannot insert a null operation into the list of operation definitions.");
		
		try {
			degradeTo.insert(index, operationDefinition);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is out of bounds for the current list of degrade operations.");
		}

	}
	
	public boolean isTimeWarp() {

		return getIsTimeWarp();
	}

	@MediaProperty(uuid1 = 0x05300503, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "IsTimeWarp",
			typeName = "Boolean",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1E02,
			symbol = "IsTimeWarp")	
	public boolean getIsTimeWarp() {
		
		if (isTimeWarp == null)
			return ISTIMEWARP_DEFAULT;
		else
			return isTimeWarp;
	}

	@MediaPropertySetter("IsTimeWarp")
	public void setIsTimeWarp(
			Boolean isTimeWarp) {
		
		this.isTimeWarp = isTimeWarp;
	}

	public ParameterDefinition lookupOperationParameter(
			AUID parameterDefinition)
		throws NullPointerException,
			ObjectNotFoundException {

		if (parameterDefinition == null)
			throw new NullPointerException("Cannot look for a parameter definition for an operation with a null value.");

		for ( ParameterDefinition definition : operationParametersDefined.getRequiredSet() )
			if (definition.getAUID().equals(parameterDefinition)) return definition;
		
		throw new ObjectNotFoundException("A parameter definition with the given id could not be found in this operation definition.");
	}

	@MediaListPrepend("DegradeTo")
	public void prependDegradeToOperation(
			tv.amwa.maj.model.OperationDefinition operationDefinition)
		throws NullPointerException {
		
		if (operationDefinition == null)
			throw new NullPointerException("Cannot prepend a null operation definition to the list of degrade to operations.");

		degradeTo.prepend(operationDefinition);
	}

	@MediaListRemoveAt("DegradeTo")
	public void removeDegradeToOperationAt(
			int index)
		throws IndexOutOfBoundsException {

		try {
			degradeTo.removeAt(index);
		}
		catch (IndexOutOfBoundsException ioobe) {
			throw new IndexOutOfBoundsException("The given index is outside the range of acceptable indexes for the current list of degrade to operations.");
		}
	}

	public OperationDefinition clone() {
		
		return (OperationDefinition) super.clone();
	}
	
	public String getOperationCategoryString() {
		
		return AUIDImpl.toPersistentForm(operationCategory);
	}
	
	public void setOperationCategoryString(
			String operationCategory) {
		
		this.operationCategory = AUIDImpl.fromPersistentForm(operationCategory);
	}
}
