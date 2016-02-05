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
 * $Log: Component.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.3  2008/01/27 11:07:34  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 13:04:51  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:08:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;


import java.util.List;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.LengthType;


/**
 * <p>Specifies an essence element.</p>
 *  
 * <p>The {@link #getComponentLength()} and {@link #setComponentLength(long)} methods  
 * apply to all time-varying media and may apply to {@linkplain Event events} but do not apply to 
 * static media, so length is an optional property. Whether the property is
 * present or omitted is determined by the kind of {@linkplain Track track}
 * that the component is in:</p>
 * 
 * <ul>
 *  <li><strong>{@linkplain TimelineTrack timeline track}</strong> - time-varying
 *  essence where the length property shall be present;</li>
 *  <li><strong>{@linkplain EventTrack event track}</strong> - instantaneous events
 *  shall omit the length property, events marking out a clip shall specify the length
 *  property;</li>
 *  <li><strong>{@linkplain StaticTrack static track}</strong> - static data that
 *  is not related to time and so the length property shall be omitted.</li>
 * </ul>
 * 
 *
 * 
 * @see Track#getTrackSegment()
 * @see Sequence#getComponentObjects()
 * @see tv.amwa.maj.industry.TypeDefinitions#ComponentStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ComponentStrongReferenceVector
 */
public abstract interface Component 
	extends InterchangeObject {

	// TODO can you set the length to a negative value?
	
	/**
	 * <p>Sets the length property of this component, which specifies its 
	 * duration in edit units. This is an optional property, which 
	 * will only be present for time-varying media.</p>
	 * 
	 * @param componentLength The duration in edit units of this component.
	 * 
	 * @throws BadLengthException Cannot set the length to a negative value.
	 */
	public void setComponentLength(
			@LengthType long componentLength) 
		throws BadLengthException;

	/**
	 * <p>Returns the length of this component, which specifies its duration in edit
	 * units. This is an optional property, which will be present for time-varying 
	 * media and may be present for events.</p>
	 * 
	 * @return Length of this component in edit units.
	 * 
	 * @throws BadPropertyException The optional length property is not present 
	 * for this object.
	 */
	public @LengthType long getComponentLength()
	  	throws BadPropertyException;

	/**
	 * <p>Sets the data definition property for the component, which specifies the kind of 
	 * data described by this component.</p>
	 * 
	 * @param dataDefinition Data definition of this component.
	 * 
	 * @throws NullPointerException The given data definition is <code>null</code>.
	 */
	public void setComponentDataDefinition(
			DataDefinition dataDefinition) 
		throws NullPointerException;

	/**
	 * <p>Returns the data definition of the component, which specifies the kind of 
	 * data described by this component.</p>
	 * 
	 * @return Data definition of this component.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReference
	 */
	public DataDefinition getComponentDataDefinition();

	/**
	 * <p>Appends a {@linkplain KLVData KLV Data} to the collection of KLV data of 
	 * this component.</p>
	 * 
	 * @param klvData KLV data value to add to the set of KLV data values of
	 * this component.
	 * 
	 * @throws NullPointerException The given KLV data value is <code>null</code>.
	 */
	public void appendComponentKLVData(
			KLVData klvData) 
		throws NullPointerException;

	/**
	 * <p>Returns the total number of {@linkplain KLVData KLV data} in the collection for this 
	 * component.</p>
	 * 
	 * @return Number of KLV data objects in this collection for this component.
	 * 
	 */
	public @UInt32 int countComponentKLVData();

	/**
	 * <p>Clear the list of {@linkplain KLVData KLV data} for this component, omitting this
	 * optional property.</p>
	 */
	public void clearComponentKLVData();
	
	/**
	 * <p>Returns the list of {@linkplain KLVData KLV data} of this component. This is an
	 * optional property.</p>
	 * 
	 * @return Shallow copy of the list of {@linkplain KLVData KLV data} of this component.
	 * 
	 * @throws PropertyNotPresentException No KLV data items are present for this component.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataStrongReferenceVector
	 */
	public List<? extends KLVData> getComponentKLVData()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes the given {@linkplain KLVData KLV data} from the collection of this component.</p>
	 * 
	 * @param klvData KLV data value to remove from the component.
	 * 
	 * @throws NullPointerException The given KLV data item is <code>null</code>.
	 * @throws PropertyNotPresentException No KLV data items are present for this component.
	 * @throws ObjectNotFoundException The given KLV data object is not in the collection of 
	 * this component.
	 * 
	 */
	public void removeComponentKLVData(
			KLVData klvData) 
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException;

	/**
	 * <p>Append a user comment name/value pair to the collection of user comments of
	 * the component, which specify user comments that are directly classified and set 
	 * up by the operator (for example Bin columns).</p>
	 * 
	 * <p>This method creates a new {@linkplain TaggedValue tagged value}, initializes it with the 
	 * specified comment name/value pair, and appends it to the comment list.</p>
	 * 
	 * @param name The name associated with the new comment to create.
	 * @param value The corresponding value, or description, of the new comment.
	 * 
	 * @throws NullPointerException One or both of the name and/or value values is/are <code>null</code>.
	 */
	public void appendComponentUserComment(
			@AAFString String name,
			@AAFString String value) 
		throws NullPointerException;

	/**
	 * <p>Append a user comment defined by an existing {@linkplain TaggedValue tagged value}
	 * to the collection of user comments of this component, which specify user comments that are 
	 * directly classified and set up by the operator (for example Bin columns).</p>
	 * 
	 * @param userComment User comment to append to the collection of user comments of this component.
	 * 
	 * @throws NullPointerException The given user comment value is <code>null</code>.
	 * 
	 * @see TaggedValueDefinition
	 */
	public void appendComponentUserComment(
			TaggedValue userComment)
		throws NullPointerException;
	
	/**
	 * <p>Returns the number of user comments in the collection of user comments 
	 * of this component, which specify user comments that are directly classified and set 
	 * up by the operator (for example Bin columns).</p>
	 * 
	 * @return Number of user comments of the component.
	 */
	public @UInt32 int countComponentUserComments();
	
	/**
	 * <p>Clears the list of user comments of this components, omitting this optional
	 * property.</p>
	 */
	public void clearComponentUserComments();

	/**
	 * <p>Returns the collection of user comments of the component, which specify user comments 
	 * that are directly classified and set up by the operator (for example Bin columns).
	 * This is an optional property.</p>
	 * 
	 * @return Shallow copy of the collection of user comments for this component.
	 * 
	 * @throws PropertyNotPresentException No user comments are present for this component.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReferenceVector
	 */
	public List<? extends TaggedValue> getComponentUserComments()
		throws PropertyNotPresentException;

	/**
	 * <p>Removes a given user comment from the collection of user comments of this 
	 * component, which specify user comments that are directly classified and set 
	 * up by the operator (for example Bin columns).</p>
	 * 
	 * @param userComment User comment to remove from the collection of user comments of
	 * this component.
	 * 
	 * @throws NullPointerException The given comment is <code>null</code>.
	 * @throws PropertyNotPresentException No user comments are present for this component.
	 * @throws ObjectNotFoundException The comment to be removed cannot be
	 * found in the collection of comments of this component.
	 */
	public void removeComponentUserComment(
			TaggedValue userComment) 
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException;

	/**
	 * <p>Append an attribute name/value pair to the attributes collection of  
	 * of this component, which specify attributes that are under the control of the 
	 * application (for example filter control).</p>
	 * 
	 * <p>This method creates a new {@linkplain TaggedValue tagged value}, initializes it 
	 * with the specified attribute name/value pair, and appends it to the attribute 
	 * list.</p>
	 * 
	 * @param name Name for the new attribute.
	 * @param value Corresponding value for the new attribute.
	 * 
	 * @throws NullPointerException One or both of the given name and/or value method
	 * parameters are <code>null</code>.
	 */
	public void appendComponentAttribute(
			@AAFString String name,
			@AAFString String value) 
		throws NullPointerException;
	
	/**
	 * <p>Append an attribute defined by an existing {@linkplain TaggedValue tagged value}
	 * to the attributes collection of this component, which specify attributes that are under 
	 * the control of the application (for example filter control).</p>
	 * 
	 * @param attribute Tagged value to be appended to the collection of attributes for this
	 * component.
	 * 
	 * @throws NullPointerException The given attribute value is <code>null</code>.
	 * 
	 * @see TaggedValueDefinition
	 */
	public void appendComponentAttribute(
			TaggedValue attribute)
		throws NullPointerException;

	/**
	 * <p>Returns the size of the collection of attributes for the component, which specify 
	 * attributes that are under the control of the application (for example filter control).</p></p>
	 * 
	 * @return Size of the collection of attributes of this component.
	 */
	public @UInt32 int countComponentAttributes();

	/**
	 * <p>Clear all attributes from this component, omitting this optional property.</p>
	 */
	public void clearComponentAttributes();
	
	/**
	 * <p>Returns the collection of attributes of this component, which specify 
	 * attributes that are under the control of the application (for example filter control).
	 * This is an optional property.</p>
	 * 
	 * @return Shallow copy of the collection of attributes of this component.
	 * 
	 * @throws PropertyNotPresentException No attributes are present for this component.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#TaggedValueStrongReferenceVector
	 */
	public List<? extends TaggedValue> getComponentAttributes()
		throws PropertyNotPresentException;

	/**
	 * <p>Remove an attribute from the collection of attributes of this component,
	 * which specify attributes that are under the control of the application (for example filter control).</p>
	 * 
	 * @param attribute Attribute to remove from the collection of attributes of 
	 * the component.
	 * 
	 * @throws NullPointerException The given attribute is <code>null</code>.
	 * @throws PropertyNotPresentException No attributes are present for this component.
	 * @throws ObjectNotFoundException The given tagged value is not currently
	 * contained by the list of attributes of this component.
	 */
	public void removeComponentAttribute(
			TaggedValue attribute) 
		throws NullPointerException,
			PropertyNotPresentException,
			ObjectNotFoundException;

	/**
	 * <p>Determines whether the length property is present for this component. This method allows
	 * a user to prepare a component for a specific kind of {@link tv.amwa.maj.model.Track track}
	 * according the the length rules of tracks. These rules are:</p>
	 * 
	 * <ul>
	 *  <li>If a component is in a {@link tv.amwa.maj.model.TimelineTrack timeline track}
	 *  then it shall have a length property.</li>
	 *  <li>If a component is in a {@link tv.amwa.maj.model.StaticTrack static trackt} then
	 *  it shall not have a length property.</li>
	 *  <li>If a component is in an {@link tv.amwa.maj.model.EventTrack event track} then 
	 *  it may have a length property. In this case, if no length property is specified then the component
	 *  describes an instantaneous {@link tv.amwa.maj.model.Event event} that does not have 
	 *  a duration.</li>
	 * </ul>
	 * 
	 * <p>The default value is that length values are not present, although components that are not 
	 * appropriate for static data override this to be <code>true</code>.</p>
	 *
	 * @return Is the context-dependent length property present for this component?
	 */
	public boolean getLengthPresent();
	
	/**
	 * <p>Sets whether the length property is present for this component. This method allows
	 * a user to prepare a component for a specific kind of {@link tv.amwa.maj.model.Track track}
	 * according the the length rules of tracks. These rules are:</p>
	 * 
	 * <ul>
	 *  <li>If a component is in a {@link tv.amwa.maj.model.TimelineTrack timeline track}
	 *  then it shall have a length property.</li>
	 *  <li>If a component is in a {@link tv.amwa.maj.model.StaticTrack static track} then
	 *  it shall not have a length property.</li>
	 *  <li>If a component is in an {@link tv.amwa.maj.model.EventTrack event track} then 
	 *  it may have a length property. In this case, if no length property is specified then the component
	 *  describes an instantaneous {@link tv.amwa.maj.model.Event event} that does not have 
	 *  a duration.</li>
	 * </ul>
	 * 
	 * <p>The default value is that length values are not present, although components that are not 
	 * appropriate for static data override this to be <code>true</code>. The constructors and 
	 * {@link tv.amwa.maj.model.Track#setTrackSegment(tv.amwa.maj.model.Segment) setSegment()}
	 * methods of {@link tv.amwa.maj.model.TimelineTrack timeline track} and 
	 * {@link tv.amwa.maj.model.StaticTrack static track} will set the presence or absence
	 * of the length property according to the above rules. Therefore, this method is mainly useful to set
	 * whether an {@link tv.amwa.maj.model.Event event} should be considered as instantaneous 
	 * or not.</p>
	 *
	 * @param lengthPresent Is the context-dependent length property present for this component?
	 */
	public void setLengthPresent(
			boolean lengthPresent);
	
	/**
	 * <p>Create a cloned copy of this component.</p>
	 *
	 * @return Cloned copy of this component.
	 */
	public Component clone();
}
