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
 * $Log: OperationDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.8  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.3  2008/02/08 11:27:25  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:38  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model;

import java.util.List;
import java.util.Set;

import tv.amwa.maj.exception.ObjectAlreadyAttachedException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;


/**
 * <p>Specifies the definition of an operation that is performed on an 
 * array of {@linkplain Segment segments}. Operation definitions specify
 * which {@linkplain tv.amwa.maj.model.Parameter parameters} are possible for an operation, while an
 * {@linkplain OperationGroup operation group} specifies specific parameters 
 * and input segments for a particular operation invocation.</p>
 * 
 *
 *
 * @see tv.amwa.maj.constant.OperationConstant
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see Dictionary#getOperationDefinitions()
 * @see Transition#getTransitionOperation()
 * @see OperationGroup#getOperationDefinition()
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReferenceVector
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReferenceSet
 */
public interface OperationDefinition 
	extends DefinitionObject {
	
	/** 
	 * <p>Default value for the is time warp property, which is {@value #ISTIMEWARP_DEFAULT}.</p>
	 * 
	 * @see #isTimeWarp()
	 * @see #setIsTimeWarp(Boolean)
	 */
	public final static boolean ISTIMEWARP_DEFAULT = false;

	/**
	 * <p>Returns the kind of data that is 
	 * produced by the defined operation.</p>
	 * 
	 * @return Kind of data produced by the defined operation.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReference
	 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
	 */
	public DataDefinition getOperationDataDefinition();

	/**
	 * <p>Sets the kind of data that is 
	 * produced by the defined operation.</p>
	 * 
	 * @param operationDataDefinition Kind of data produced by the defined operation.
	 * 
	 * @throws NullPointerException The given data definition is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReference
	 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
	 */
	public void setOperationDataDefinition(
			DataDefinition operationDataDefinition) 
		throws NullPointerException;

	/**
	 * <p>Returns <code>true</code> if the length of an {@linkplain OperationGroup operation group}
	 * is different from the lengths of the input segments, for example a 
	 * slow motion effect.</p>
	 *  
	 * <p>If this optional property is not present, its default value of {@value #ISTIMEWARP_DEFAULT}
	 * is returned.</p>
	 *  
	 * @return Does this operation change the total length of segments
	 * in the associated operation group?
	 * 
	 * @see #ISTIMEWARP_DEFAULT
	 * @see #getIsTimeWarp()
	 */
	public @Bool boolean isTimeWarp();

	/**
	 * <p>Returns <code>true</code> if the length of an {@linkplain OperationGroup operation group}
	 * is different from the lengths of the input segments, for example a 
	 * slow motion effect.</p>
	 *  
	 * <p>If this optional property is not present, its default value of {@value #ISTIMEWARP_DEFAULT}
	 * is returned.</p>
	 *  
	 * @return Does this operation change the total length of segments
	 * in the associated operation group?
	 * 
	 * @see #ISTIMEWARP_DEFAULT
	 * @see #isTimeWarp()
	 */
	public @Bool boolean getIsTimeWarp();

	/**
	 * <p>Set to <code>true</code> to indicate that the the length of an 
	 * operation group is different from the lengths of the input segments, 
	 * for example a slow motion effect.</p>
	 *  
	 * <p>The default value for this optional property is {@value #ISTIMEWARP_DEFAULT}. To 
	 * omit this optional property, call this method with <code>null</code>.</p>
	 *  
	 * @param isTimeWarp Does this operation change the total length of segments
	 * in the associated operation group?
	 * 
	 * @see #ISTIMEWARP_DEFAULT
	 */
	public void setIsTimeWarp(
			@Bool Boolean isTimeWarp);

	/** 
	 * <p>Prepend an operation definition to the degrade-to
	 * list of operation definitions of the defined operation, which specify simpler 
	 * operations that an application can substitute for the defined operation if it cannot 
	 * process it.  Use this function to add an operation definition to
	 * be scanned first when searching for a replacement, to be considered as a more
	 * desirable alternate operation.</p>
	 * 
	 * @param operationDefinition Degrade-to operation definition to add.
	 * 
	 * @throws NullPointerException The given degrade-to operation definition is <code>null</code>.
	 */
	public void prependDegradeToOperation(
			OperationDefinition operationDefinition) 
		throws NullPointerException;

	/**
	 * <p>Append an operation definition to the degrade-to
	 * list of operation definitions of the defined operation, which specify simpler 
	 * operations that an application can substitute for the defined operation if it cannot 
	 * process it. Use this function to add an operation definition to
	 * be scanned last when searching for a replacement, to be considered as a less
	 * desirable alternate operation.</p>
	 * 
	 * @param operationDefinition Degrade-to operation to append. 
	 * @throws NullPointerException The given degrade-to operation definition is <code>null</code>.
	 */
	public void appendDegradeToOperation(
			OperationDefinition operationDefinition) 
		throws NullPointerException;

	/**
	 * <p>Insert an operation definition into the degrade-to list of
	 * definitions of the defined operation at the given index. The degrade-to
	 * list of operation definitions specify simpler operations that an application 
	 * can substitute for the defined operation if it cannot process it.
	 * Operation definitions already existing at the given and higher indices will
	 * be moved up to the next higher index to accommodate.</p>
	 * 
	 * @param index 0-based index at which the operation definition is to be inserted.
	 * @param operationDefinition Degrade-to operation definition to insert at the
	 * specified index.
	 * 
	 * @throws NullPointerException The given operation definition is <code>null</code>.
	 * @throws IndexOutOfBoundsException The index is outside the acceptable range for
	 * the current degrade-to list.
	 */
	public void insertDegradeToOperationAt(
			@UInt32 int index,
			OperationDefinition operationDefinition) 
		throws NullPointerException,
			IndexOutOfBoundsException;

	/**
	 * <p>Removes the indexed operation definition from the degrade-to 
	 * list of operation definitions of the defined operation, which specify simpler 
	 * operations that an application can substitute for the defined operation if it cannot 
	 * process it.  Operation definitions already existing at
	 * indices higher than the given index will be moved down to the
	 * next lower index to accommodate.</p>
	 * 
	 * @param index 0-based index of operation definition to remove from the 
	 * degrade-to list of the defined operation
	 * @throws IndexOutOfBoundsException The index is outside the range acceptable range for
	 * the current degrade-to list.
	 */
	public void removeDegradeToOperationAt(
			@UInt32 int index) 
		throws IndexOutOfBoundsException;

	/**
	 * <p>Returns the list of degrade-to operation definitions of the defined
	 * operation, which specify simpler 
	 * operations that an application can substitute for the defined operation if it cannot 
	 * process it. The definitions are ordered from the most desirable to the least desirable 
	 * alternative.</p>
	 * 
	 * @return Shallow copy of the list of degrade-to operation definitions of the defined
	 * operation.
	 * 
	 * @throws PropertyNotPresentException No degrade to operations are present for this
	 * operation definition.
	 */
	public List<? extends OperationDefinition> getDegradeToOperations()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the number of degrade-to operations in this operation 
	 * definition, which specify simpler 
	 * operations that an application can substitute for the defined operation if it cannot 
	 * process it.</p>
	 * 
	 * @return Number of degrade-to operations listed in this operation 
	 * definition.
	 */
	public @UInt32 int countDegradeToOperations();

	/**
	 * <p>Clears the list degrade-to operations of this operation definition, omitting
	 * this optional property.</p>
	 */
	public void clearDegradeToOperations();
	
	/**
	 * <p>Returns an {@linkplain tv.amwa.maj.record.AUID AUID} identifying the 
	 * category of the defined operation, which specifies the kind of operation, such as video effect, 
	 * audio effect, or 3D&nbsp;operation. This is an optional property.</p>
	 * 
	 * @return Identifier for the category of the defined operation.
	 * 
	 * @throws PropertyNotPresentException The optional operation category property is not present
	 * for the defined operation.
	 * 
	 * @see tv.amwa.maj.constant.OperationCategoryType
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationCategoryType
	 */
	public AUID getOperationCategory() 
		throws PropertyNotPresentException;

	/**
	 * <p>Sets an {@linkplain tv.amwa.maj.record.AUID AUID} identifying the 
	 * category of the defined operation, which specifies the kind of operation, such as video effect, 
	 * audio effect, or 3D&nbsp;operation. Set this optional property to <code>null</code> to omit
	 * it.</p>
	 * 
	 * @param category AUID indicating the category of the defined operation.
	 * 
	 * @see tv.amwa.maj.constant.OperationCategoryType
	 * @see tv.amwa.maj.industry.TypeDefinitions#OperationCategoryType
	 */
	public void setOperationCategory(
			AUID category);

	/** 
	 * <p>Returns the number of input media segments of the defined operation
	 * definition. A value of&nbsp;-1 indicates that the effect can have any number 
	 * of input segments.</p>
	 * 
	 * @return Number of input media segments for the defined operation.
	 */
	public @Int32 int getOperationInputCount();

	/**
	 * <p>Sets the number of input media segments for the defined operation. A value 
	 * of&nbsp;-1 indicates that the effect can have any number 
	 * of input segments.</p>
	 * 
	 * @param operationInputCount Number of input media segments of the defined
	 * operation.
	 */
	public void setOperationInputCount(
			@Int32 int operationInputCount);

	/**
	 * <p>Gets the bypass media segment index, which specifies the array index (1-based) 
	 * of the input segment which is the primary input. This
	 * value allows the client application to pick one of the inputs
	 * (foreground, background, etc.) to stand in for the effect if it is not available 
	 * and none of the degrade-to effects are available. This is an optional property.</p>
	 * 
	 * <p>Note that the methods of the C-based <a href="http://aaf.sourceforge.net/">AAF reference implementation</a>
	 * use 0-based indexing.</p>
	 * 
	 * @return 1-based index of the bypass media segment for the defined operation.
	 * 
	 * @throws PropertyNotPresentException The optional bypass property is
	 * not present in the defined operation.
	 */
	public @UInt32 int getBypass()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the bypass media segment index, which specifies the array index (1-based) 
	 * of the input segment which is the primary input. This value
	 * allows the client application to pick one of the inputs
	 * (foreground, background, etc.) to stand in for the
	 * effect if it is not available, and none of the degrade to
	 * effects are available. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * @param bypass Index of bypass media segment for the defined operation.
	 */
	public void setBypass(
			@UInt32 Integer bypass);

	/**
	 * <p>Add a {@linkplain ParameterDefinition parameter definition} to 
	 * the unordered collection of parameter definitions of the defined operation,
	 * which specify the {@linkplain Parameter parameters} that can be used as controls 
	 * for the operation.</p>
	 * 
	 * @param parameterDefinition Parameter definition object to add.
	 * 
	 * @throws NullPointerException The given parameter definition is <code>null</code>.
	 * @throws ObjectAlreadyAttachedException Parameter definition is already
	 * present within this operation definition.
	 * 
	 * @see OperationGroup#addParameter(Parameter)
	 */
	public void addParameterDefinition(
			ParameterDefinition parameterDefinition) 
		throws NullPointerException,
			ObjectAlreadyAttachedException;

	/**
	 * <p>Returns the collection of {@linkplain ParameterDefinition parameter definitions}
	 * of the defined operation, which specify the {@linkplain Parameter parameters} that 
	 * can be used as controls for the operation.</p>
	 * 
	 * @return Shallow copy of the collection of {@linkplain ParameterDefinition parameter 
	 * definitions} of the defined operation.
	 * 
	 * @throws PropertyNotPresentException No parameter definitions are present for the
	 * defined operation.
	 * 
	 * @see OperationGroup#addParameter(Parameter)
	 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReferenceSet
	 */
	public Set<? extends ParameterDefinition> getOperationParametersDefined()
		throws PropertyNotPresentException; 

	/**
	 * <p>Return the number of {@linkplain ParameterDefinition parameter definitions}
	 * of the defined operation, which specify the {@linkplain Parameter parameters} that 
	 * can be used as controls for the operation.</p>
	 * 
	 * @return Number of parameter definitions of the defined operation.
	 */
	public @UInt32 int countOperationParametersDefined();

	/**
	 * <p>Looks up and returns the {@linkplain ParameterDefinition parameter definition}
	 * corresponding to the given identifier from the collection of defined parameters
	 * of the defined operation.</p>
	 * 
	 * @param parameterDefinition Identity of the parameter definition to look up.
	 * @return Matching parameter definition with the given identifier.
	 * 
	 * @throws NullPointerException The given identifier for a parameter definition is 
	 * <code>null</code>.
	 * @throws ObjectNotFoundException The given identifier does not correspond
	 * to a parameter definition of the defined operation.
	 */
	public ParameterDefinition lookupOperationParameter(
			AUID parameterDefinition) 
		throws NullPointerException,
			ObjectNotFoundException;
	
	/**
	 * <p>Create a cloned copy of this operation definition.</p>
	 *
	 * @return Cloned copy of this operation definition.
	 */
	public OperationDefinition clone();
}

