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
 * $Log: SourceReferenceValueImpl.java,v $
 * Revision 1.2  2011/01/21 10:41:24  vizigoth
 * Completed source refernce value tests.
 *
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/02/08 11:38:05  vizigoth
 * Comment linking fix.
 *
 * Revision 1.4  2008/02/08 11:33:47  vizigoth
 * Consistent referal to zero rather than null/nil mob id and isOriginal/Contextual methods added to the interface.
 *
 * Revision 1.3  2008/01/14 20:48:00  vizigoth
 * Change of null/nil mob is to be called the zero mob id.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:32  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.PackageIDType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.union.SourceReferenceValue;


// TODO tests

/** 
 * <p>Implementation of a source reference value that targets an item of source material,
 * defined using a {@linkplain tv.amwa.maj.record.PackageID package id}, 
 * source {@linkplain tv.amwa.maj.misctype.TrackID track id} and start time. This reference can be used 
 * as an argument to methods or as a return value. The class is used to ensure the constraint that if 
 * the source id is the {@linkplain PackageIDImpl#getZeroPackageID() zero package id} then the source track id 
 * is&nbsp;0 and the start time is&nbsp;0 .</p>
 * 
 * <p>The interpretation of a source reference value depends on the context where it is used. The value of 
 * a source reference can be explicit or relative to its context. Source references are often used 
 * to build a chain of references that can be used to trace the original source of some material.</p>
 * 
 * <p>Three different kinds of reference can be identified from the value of the source id property of a 
 * source reference. These are defined as:</p>
 * 
 * <ol>
 *  <li>Source package id property is present and has a non-nil value. An explicit reference to a {@linkplain tv.amwa.maj.model.Package package} 
 *  by its {@linkplain PackageIDImpl unique package id} and {@linkplain tv.amwa.maj.misctype.TrackID track id}. An optional 
 *  offset starting frame may also be provided. Use the {@linkplain #SourceReferenceValue(PackageIDImpl, int, Long) public 
 *  constructor} of this class to create this kind of source reference.</li>
 *  <li>Source package id property is present and is the {@linkplain tv.amwa.maj.record.impl.PackageIDImpl#getZeroPackageID() zero package id}. This
 *  specifies that the {@linkplain tv.amwa.maj.model.SourcePackage source package} containing the source reference represents 
 *  the original source material. Such references are the final link in source reference chains. To manufacture such
 *  a reference, call the {@link #originalSource()} static method.</li>
 *  <li>The source package id property is not present. This specifies that the reference is to another 
 *  {@linkplain tv.amwa.maj.model.Track track} within the same {@linkplain tv.amwa.maj.model.Package package}, the package owning 
 *  the source reference itself. To manufacture such a reference, call one of the {@link #inContextReference(int)} or 
 *  {@link #inContextReference(int, Long)} methods.</li>
 * </ol>
 * 
 * <p>As this class is used as a convenience argument to the methods of other persistent classes,
 * it has no persistent representation of its own.</p>
 * 
 * @see tv.amwa.maj.model.SourceClip
 * @see tv.amwa.maj.model.SourceReferenceSegment
 * @see tv.amwa.maj.record.PackageID
 *
 *
 */

public class SourceReferenceValueImpl 
	implements tv.amwa.maj.union.SourceReferenceValue,
		Serializable,
		XMLSerializable,
		Cloneable {

	private static final long serialVersionUID = -1301646346927365235L;
	
	/** 
	 * <p>Identifies the package being referenced. </p>
	 */
	private PackageID sourcePackageID = null;
	/** 
	 * <p>Specifies the track id of a track within the specified 
	 * package. </p> 
	 */
	@TrackID private int sourceTrackID;
	/** 
	 * <p>Specifies the offset from the origin of the referenced package's
	 * track in edit units determined by the SourceClip object's context.</p> 
	 */
	@PositionType private Long startPosition;


    /**
     * <p>Create a new source reference value that contains a source package id, source track id and
     * start position. If the given source package id is the {@linkplain PackageIDImpl#getZeroPackageID() zero package id}, 
     * values for source track id and start position are set to zero to comply with source reference constraints.</p>
     * 
	 * @param sourcePackageID Identifies the {@linkplain tv.amwa.maj.model.Package package} that is the target
	 * of the new reference. If the property is set to the {@linkplain PackageIDImpl#getZeroPackageID() zero package id}, 
	 * it means that the package owning the source reference describes the original source. If this optional property 
	 * is omitted by setting its value to <code>null</code>, the given {@linkplain tv.amwa.maj.model.Track track} 
	 * refers to another track within the same package as the reference.
	 * @param sourceTrackID Specifies the track id of a track within the specified 
	 * package. If the source package id is the {@linkplain PackageIDImpl#getZeroPackageID() zero package id}, then the source 
	 * track id shall have a 0&nbsp;value.
	 * @param startPosition Specifies the position offset from the origin of the referenced package's
	 * track in edit units determined by the associated 
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip's} context. If the source package id 
	 * is the {@linkplain PackageIDImpl#getZeroPackageID() zero package id}, then the start position shall have a 0&nbsp;value. 
	 * Set the value to <code>null</code> to omit this optional property in the case that the
	 * new reference is to {@linkplain tv.amwa.maj.model.StaticTrack static material}.
	 * 
	 * @throws IllegalArgumentException The source track id cannot be set to a negative value.
	 * 
	 * @see tv.amwa.maj.record.PackageID#isZero()
	 * @see tv.amwa.maj.record.impl.PackageIDImpl#getZeroPackageID()
	 */
	public SourceReferenceValueImpl(
			@PackageIDType PackageID sourcePackageID, 
			@TrackID int sourceTrackID, 
			@PositionType Long startPosition) 
		throws IllegalArgumentException {
		
		setStartPosition(startPosition);
		setSourceTrackID(sourceTrackID);

		setSourcePackageID(sourcePackageID); // This must be last so that the zero constraint is applied correctly
	}

	/**
	 * <p>Manufactures a new source reference that indicates the reference is to essence
	 * that is the original source. The source package id will be the {@linkplain tv.amwa.maj.record.impl.PackageIDImpl#getZeroPackageID() 
	 * zero package id}, the source track id will be zero and so will the start position.</p>
	 *
	 * @return Manufactured source reference to original source.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#isOriginalSource()
	 * @see tv.amwa.maj.record.PackageID#isZero()
	 */
	public static final SourceReferenceValueImpl originalSource() {
		
		return new SourceReferenceValueImpl(
				PackageIDImpl.getZeroPackageID(),
				0,
				0l);
	}
	
	/**
	 * <p>Manufactures a new source reference to static material in track within the same context
	 * as the package in which the reference is specified. This method version omits the start
	 * position property&nbsp;- use {@link #inContextReference(int, Long)} to set a start
	 * position value for timeline- or event-based material.</p>
	 *
	 * @param sourceTrackID Track to reference within the same package that this reference is defined in.
	 * @return Manufactured contextual source reference.
	 * 
	 * @throws IllegalArgumentException The given source track id is a negative value.
	 * 
	 * @see tv.amwa.maj.model.StaticTrack
	 * @see tv.amwa.maj.union.SourceReferenceValue#isContextual()
	 */ 
	public static final SourceReferenceValueImpl inContextReference(
			@TrackID int sourceTrackID) 
		throws IllegalArgumentException {
		
		return new SourceReferenceValueImpl(
				null,
				sourceTrackID,
				null);
	}
	
	/**
	 * <p>Manufactures a new source reference to timeline- or event-based material in 
	 * a track within the same context as the package in which the reference is specified. Use the
	 * {@link #inContextReference(int)} to create a reference to static material.</p>
	 *
	 * @param sourceTrackID Track to reference within the same package that this reference is defined in.
	 * @param startPosition Specifies the offset from the origin of the referenced package's track in edit 
	 * units determined by the associated source clip object's context.
	 * 
	 * @return Manufactured contextual source reference.
	 * 
	 * @throws IllegalArgumentException The source track id cannot be set to a negative value.
	 * 
	 * @see tv.amwa.maj.model.TimelineTrack
	 * @see tv.amwa.maj.model.EventTrack
	 * @see tv.amwa.maj.union.SourceReferenceValue#isContextual()
	 */
	public static final SourceReferenceValueImpl inContextReference(
			@TrackID int sourceTrackID,
			@PositionType Long startPosition) 
		throws IllegalArgumentException {
		
		return new SourceReferenceValueImpl(
				null,
				sourceTrackID,
				startPosition);
	}
	
	public @PackageIDType PackageID getSourcePackageID() 
		throws PropertyNotPresentException {
		
		if (sourcePackageID == null) 
			throw new PropertyNotPresentException("The optional source ID property is not present in this source reference value, indicating that this is an in content reference.");
		
		return sourcePackageID.clone();
	}

	public void setSourcePackageID(
			@PackageIDType tv.amwa.maj.record.PackageID sourcePackageID) {
		
		if (sourcePackageID == null) {
			this.sourcePackageID = null;
			return;
		}
		
		this.sourcePackageID = sourcePackageID.clone();

		applyZeroConstraint();
	}

	public @TrackID int getSourceTrackID() {
		
		return sourceTrackID;
	}

	public void setSourceTrackID(
			@TrackID int sourceTrackID) 
		throws IllegalArgumentException {

		if (sourceTrackID < 0)
			throw new IllegalArgumentException("Cannot set a source track id value to a negative value.");
		
		if (sourcePackageID != null) {
			if ((sourcePackageID.isZero()) && (sourceTrackID != 0))
				throw new IllegalArgumentException("Cannot set a non-zero value for source track id for this source reference when the source id is the zero package id.");
		}
		
		this.sourceTrackID = sourceTrackID;
		
		applyZeroConstraint();
	}

	public @PositionType long getStartPosition() 
		throws PropertyNotPresentException {
		
		if (startPosition == null)
			throw new PropertyNotPresentException("The optional start time property is not present in this source reference value.");
		
		return startPosition;
	}

	public void setStartPosition(
			@PositionType Long startPosition) 
		throws IllegalArgumentException {

		if ((sourcePackageID != null) && (startPosition != null)) {
			if ((sourcePackageID.isZero()) && (startPosition != 0))
				throw new IllegalArgumentException("Cannot set a non-zero value for start time for this source reference value when the source id is the zero package id.");
		}
		
		this.startPosition = startPosition;

	}

	/** 
	 * <p>Check that if the source id is the zero package id, so are the source track id and the start time.
	 * If this is not the case, the source track id is set to&nbsp;0 and start time to&nbsp;0.</p>
	 */
	private void applyZeroConstraint() {
		
		if (sourcePackageID == null) return;
		
		if (sourcePackageID.isZero()) {
			this.sourceTrackID = 0;
			if (startPosition != null)
				this.startPosition = 0l;
		}
	}

	public boolean isOriginalSource() {
		
		if (sourcePackageID == null) return false;
		
		return sourcePackageID.isZero();
	}
	
	public boolean isContextual() {
		
		return (sourcePackageID == null);
	}
	
	@Override
	public boolean equals(Object o) {

		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof tv.amwa.maj.union.SourceReferenceValue)) return false;
		
		tv.amwa.maj.union.SourceReferenceValue testValue = 
			(tv.amwa.maj.union.SourceReferenceValue) o;
		
		try {
			if (!(testValue.getSourcePackageID().equals(sourcePackageID))) return false;
		}
		catch (PropertyNotPresentException pnpe) {
			if (sourcePackageID != null) return false;
		}
		
		try {
			if (!(Long.valueOf(testValue.getStartPosition()).equals(startPosition))) return false;
		} catch (PropertyNotPresentException e) {
			if (startPosition != null) return false;
		}
		
		if (sourceTrackID != testValue.getSourceTrackID()) return false;
		
		return true;
	}

	/**
	 * <p>Create a pseudo-XML representation of this source reference, similar to that produced
	 * for a {@linkplain tv.amwa.maj.model.SourceClip source clip}. No XML schema or
	 * DTD defines this element. For example, an original source reference would create the
	 * following:</p>
	 * 
	 * <pre>
	 * &lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;
	 * &lt;!--The SourceReferenceValue tag is not valid AAF XML.--&gt;
	 * &lt;SourceReferenceValue&gt;
	 *   &lt;SourceID&gt;urn:x-umid:000000000000000000000000-00-000000-00000000000000000000000000000000&lt;/SourceID&gt;
	 *   &lt;SourceTrackID&gt;0&lt;/SourceTrackID&gt;
	 *   &lt;StartTime&gt;0&lt;/StartTime&gt;
	 * &lt;/SourceReferenceValue&gt;
	 * </pre>
	 * 
	 * @return An XML representation of this source reference.
	 */
	@Override
	public String toString() {

		return XMLBuilder.toXMLNonMetadata(this);
	}

	@Override
	public int hashCode() {

		if (sourcePackageID == null)
			return sourceTrackID;
		if (sourcePackageID.isZero()) 
			return Integer.MAX_VALUE;
		else
			return sourcePackageID.hashCode() ^ 
				((startPosition == null) ? 0 : startPosition.hashCode()) ^ 
				sourceTrackID;
	}

	@Override
	public SourceReferenceValue clone() {

		try {
			return (SourceReferenceValue) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace();
			return null;
		}
	}
	
	static final String SOURCEREFERENCEVALUE_TAG = "SourceReferenceValue";
	static final String SOURCEPACKAGEID_TAG = "SourcePackageID";
	static final String SOURCETRACK_ID = "SourceTrackID";
	static final String STARTPOSITION_TAG = "StartPosition";
	
	public void appendXMLChildren(
			Node parent) {

		XMLBuilder.appendComment(parent, "The SourceReferenceValue tag is not valid AAF XML.");
		
		Element referenceElement = XMLBuilder.createChild(parent, "http://www.amwa.tv/projects/maj",
				"maj", SOURCEREFERENCEVALUE_TAG);

		if (sourcePackageID != null)
			XMLBuilder.appendElement(referenceElement, CommonConstants.AAF_XML_NAMESPACE,
					CommonConstants.AAF_XML_PREFIX, SOURCEPACKAGEID_TAG, sourcePackageID.toString());
		XMLBuilder.appendElement(referenceElement, CommonConstants.AAF_XML_NAMESPACE,
				CommonConstants.AAF_XML_PREFIX, SOURCETRACK_ID, sourceTrackID);
		if (startPosition != null)
			XMLBuilder.appendElement(referenceElement, CommonConstants.AAF_XML_NAMESPACE,
					CommonConstants.AAF_XML_PREFIX, STARTPOSITION_TAG, startPosition);
	}

	public String getComment() {
		
		return null;
	}
}
