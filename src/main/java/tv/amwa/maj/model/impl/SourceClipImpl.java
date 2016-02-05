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
 * $Log: SourceClipImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/05/19 12:59:18  vizigoth
 * Fixed an AAF reading bug where start position was not getting set.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.7  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.6  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.5  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.4  2008/01/15 12:33:55  vizigoth
 * Updated due to refactoring of element names in FadeType enumeration.
 *
 * Revision 1.3  2008/01/14 21:13:54  vizigoth
 * Update due to refactoring of element names in the FadeType enumeration.
 *
 * Revision 1.2  2007/12/13 11:31:51  vizigoth
 * Removed MediaCriteria interface and replaced with CriteriaType enumeration.
 *
 * Revision 1.1  2007/11/13 22:09:16  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.union.SourceReferenceValue;
import tv.amwa.maj.union.impl.FadeImpl;
import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.PackageNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.extensions.quantel.QConstants;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.Stream;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Package;
import tv.amwa.maj.model.SourceClip;

/** 
 * <p>Implements a representation of essence and identifies the source of the essence.
 * This interface uses the {@linkplain tv.amwa.maj.union.SourceReferenceValue source reference values} to
 * manage the properties of a source clip.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#SourceClipStrongReference
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1100,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "SourceClip",
		  description = "The SourceClip class represents the essence and identifies the source of the essence.",
		  symbol = "SourceClip")
public class SourceClipImpl
	extends 
		SourceReferenceSegmentImpl
	implements 
		SourceClip,
		tv.amwa.maj.extensions.quantel.QSourceClip,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -5642395327782333462L;

	private Long startPosition = null;
	private long fadeInLength = 0l;
	private FadeType fadeInType = FadeType.None;
	private boolean fadeInPresent = false;
	private long fadeOutLength = 0l;
	private FadeType fadeOutType = FadeType.None;
	private boolean fadeOutPresent = false;
	
	public SourceClipImpl() { }

	/**
	 * <p>Creates and initializes a new source clip, which represents an item of essence and identifies 
	 * the source of the essence.</p>
	 *
	 * @param dataDefinition Kind of data represented by this component.
	 * @param length Length of the source clip component.
	 * @param sourceReference Reference to the source clip.
	 * 
	 * @throws NullPointerException The data definition and/or source reference arguments
	 * are <code>null</code>.
	 * @throws BadLengthException The length of the component must be non-negative.
	 */
	public SourceClipImpl(
			DataDefinition dataDefinition,
			long length,
			SourceReferenceValue sourceReference)
		throws NullPointerException,
			BadLengthException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new source clip with a null data definition.");
		if (sourceReference == null)
			throw new NullPointerException("Cannot create a new source clip with a null source reference.");
		
		setComponentDataDefinition(dataDefinition);
		setLengthPresent(true);
		setComponentLength(length);
		setSourceReference(sourceReference);
	}

	public SourceReferenceValue getSourceReference() {

		try {
			return new tv.amwa.maj.union.impl.SourceReferenceValueImpl(
					getSourcePackageID(),
					getSourceTrackID(),
					startPosition);
		}
		catch (PropertyNotPresentException pnpe) {
			// Source ID is not present ... this is a reference to a track in this package.

			return tv.amwa.maj.union.impl.SourceReferenceValueImpl.inContextReference(getSourceTrackID(), startPosition);
		}
	}

	public void setSourceReference(
			SourceReferenceValue sourceReference)
		throws NullPointerException {

		if (sourceReference == null)
			throw new NullPointerException("Cannot set the source reference of this clip with a null source reference value.");
		
		try {
			setSourcePackageID(sourceReference.getSourcePackageID());
		} 
		catch (PropertyNotPresentException e) {
			setSourcePackageID(null);
		}
		
		setSourceTrackID(sourceReference.getSourceTrackID());

		try {
			setStartPosition(sourceReference.getStartPosition());
		} 
		catch (PropertyNotPresentException e) {
			setStartPosition(null);
		}
	}

	public Package resolveRef()
			throws PackageNotFoundException {
		// TODO link this to resolveReference() and fix
		throw new PackageNotFoundException("The MAJ API does not currently provide a built-in package reference resolution facility.");
	}

	@Deprecated public void setFade(
			long fadeInLength,
			FadeType fadeInType,
			long fadeOutLength,
			FadeType fadeOutType) 
		throws BadLengthException {

		setFade(new FadeImpl(fadeInLength, fadeInType, fadeOutLength, fadeOutType));
	}

	@Deprecated 
	public FadeImpl getFade() { 
	
		try {
			return new FadeImpl(
					fadeInLength,
					(fadeInPresent ? fadeInType : null),
					fadeOutLength,
					(fadeOutPresent ? fadeOutType : null));
		}
		catch (BadLengthException ble) {
			ble.printStackTrace();
			/* Should not happen so print stack trace. */
			return null;
		}
	}

	@Deprecated public void setFade(
			tv.amwa.maj.union.Fade fade) 
		throws NullPointerException {

		if (fade == null)
			throw new NullPointerException("Cannot set the fade value of this source clip using a null pointer.");
		
		try {
			this.fadeInLength = fade.getFadeInLength();
			this.fadeInType = fade.getFadeInType();
			this.fadeInPresent = true;
		}
		catch (PropertyNotPresentException pnpe) {
			this.fadeInPresent = false;
		}
		
		try {
			this.fadeOutLength = fade.getFadeOutLength();
			this.fadeOutType = fade.getFadeOutType();
			this.fadeOutPresent = true;
		}
		catch (PropertyNotPresentException pnpe) {
			this.fadeOutPresent = false;		
		}
	}

	@MediaProperty(uuid1 = 0x07020201, uuid2 = (short) 0x0105, uuid3 = (short) 0x0200,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FadeInLength",
			typeName = "LengthType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1202,
			symbol = "FadeInLength")
	@Deprecated public long getFadeInLength() 
		throws PropertyNotPresentException {
		
		if (fadeInPresent == false)
			throw new PropertyNotPresentException("The fade in property is not present in this source clip.");
		
		return fadeInLength;
	}
	
	@Deprecated
	@MediaPropertySetter("FadeInLength")
	public void setFadeInLength(
			Long fadeInLength) {
		
		if (fadeInLength == null) {
			this.fadeInPresent = false;
			return;
		}
		
		this.fadeInLength = fadeInLength;
		
		if (fadeInType == null) fadeInType = FadeType.None;
		this.fadeInPresent = true;
	}
	
	@MediaProperty(uuid1 = 0x05300501, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "FadeInType",
			typeName = "FadeType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1203,
			symbol = "FadeInType")
	@Deprecated public FadeType getFadeInType() 
		throws PropertyNotPresentException {
		
		if (fadeInPresent == false)
			throw new PropertyNotPresentException("The fade in property is not present in this source clip.");

		return fadeInType;
	}
	
	@Deprecated
	@MediaPropertySetter("FadeInType") 
	public void setFadeInType(
			FadeType fadeInType) {
		
		if (fadeInType == null) {
			this.fadeInPresent = false;
			this.fadeInType = null;
			return;
		}
		
		this.fadeInType = fadeInType;
		this.fadeInPresent = true;
	}

	@MediaProperty(uuid1 = 0x07020201, uuid2 = (short) 0x0105, uuid3 = (short) 0x0300,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FadeOutLength",
			typeName = "LengthType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1204,
			symbol = "FadeOutLength")
	@Deprecated public long getFadeOutLength() 
		throws PropertyNotPresentException {
		
		if (fadeOutPresent == false)
			throw new PropertyNotPresentException("The fade out property is not present out this source clip.");
		
		return fadeOutLength;
	}
	
	@Deprecated
	@MediaPropertySetter("FadeOutLength")
	public void setFadeOutLength(
			Long fadeOutLength) {
		
		if (fadeOutLength == null) {
			this.fadeOutPresent = false;
			return;
		}
		
		this.fadeOutLength = fadeOutLength;
		if (this.fadeOutType == null) this.fadeOutType = FadeType.None;
		this.fadeOutPresent = true;
	}
	
	@MediaProperty(uuid1 = 0x05300502, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "FadeOutType",
			typeName = "FadeType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1205,
			symbol = "FadeOutType")
	@Deprecated public FadeType getFadeOutType() 
		throws PropertyNotPresentException {
		
		if (fadeOutPresent == false)
			throw new PropertyNotPresentException("The fade out property is not present out this source clip.");

		return fadeOutType;
	}
	
	@Deprecated
	@MediaPropertySetter("FadeOutType")
	public void setFadeOutType(
			FadeType fadeOutType) {
		
		if (fadeOutType == null) {
			this.fadeOutPresent = false;
			this.fadeOutType = null;
			return;
		}
		
		this.fadeOutType = fadeOutType;
		this.fadeOutPresent = true;
	}
	
	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0104, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "StartPosition",
			aliases = { "StartTime" },
			typeName = "PositionType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x1201,
			symbol = "StartPosition")
	public long getStartPosition() 
		throws PropertyNotPresentException {
		
		if (startPosition == null)
			throw new PropertyNotPresentException("The context-sensitive optional start time property is not present in this source clip.");
		
		return startPosition;
	}

	@MediaPropertySetter("StartPosition")
	public void setStartPosition(
			Long startPosition) {
		
		if (startPosition == null)
			this.startPosition = null;
		
		try {
			if (getSourcePackageID().equals(PackageIDImpl.getZeroPackageID()))
				this.startPosition = 0l;
			else
				this.startPosition = startPosition;
		}
		catch (PropertyNotPresentException pnpe) {
			this.startPosition = startPosition;
		}
	}
	
	// Begin - Quantel extensions
	
	private Integer rushChannelMask = null;
	private Stream rushBlob = null;
	
    @MediaProperty(uuid1 = 0x11b2c317, uuid2 = (short) 0x8928, uuid3 = (short) 0x49a2,
        uuid4 = { (byte) 0xaf, (byte) 0x65, (byte) 0x66, (byte) 0x61, (byte) 0x50, (byte) 0xd4, (byte) 0x42, (byte) 0xa3 },
        definedName = "Rush channel mask",
        symbol = "Rush_channel_mask",
        aliases = { "Rush_channel_mask" },
        typeName = "UInt32",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public @UInt32 int getRushChannelMask()
		throws PropertyNotPresentException {
		
		if (rushChannelMask == null)
			throw new PropertyNotPresentException("The optional rush channel mask property is not present for this Quantel source clip.");
		
		return rushChannelMask;
	}
	
	@MediaPropertySetter("Rush channel mask")
	public void setRushChannelMask(
			@UInt32 Integer rushChannelMask)
		throws IllegalArgumentException {
		
		if (rushChannelMask == null) { this.rushChannelMask = null; return; }
		
		if (rushChannelMask < 0)
			throw new IllegalArgumentException("The rush channel mask property cannot be negative.");
		
		this.rushChannelMask = rushChannelMask;
	}
	
    @MediaProperty(uuid1 = 0x072ceb98, uuid2 = (short) 0x1a33, uuid3 = (short) 0x40ad,
        uuid4 = { (byte) 0x9e, (byte) 0xe7, (byte) 0x1f, (byte) 0x80, (byte) 0xa4, (byte) 0x7c, (byte) 0x38, (byte) 0x67 },
        definedName = "Rush blob",
        symbol = "Rush_blob",
        aliases = { "Rush_blob" },
        typeName = "Stream",
        optional = true,
        uniqueIdentifier = false,
        pid = 0,
        prefix = QConstants.XML_PREFIX,
        namespace = QConstants.XML_NAMESPACE)
	public Stream getRushBlob()
		throws PropertyNotPresentException {
		
		if (rushBlob == null)
			throw new PropertyNotPresentException("The optional rush blob property is not present for this Quantel source clip.");
		
		return rushBlob;
	}
	
	@MediaPropertySetter("Rush blob")
	public void setRushBlob(
			Stream rushBlob) {
		
		this.rushBlob = rushBlob;
	}
	
	// End - Quantel extensions
	
	/**
	 * <p>Resolve the reference to a package represented by this source clip. If the reference
	 * cannot be resolved, a {@link PackageNotFoundException} will be thrown.</p>
	 * 
	 * <p>The resolution of references is dependent on the execution context in which this
	 * software is running:</p>
	 * 
	 * <ul>
	 *  <li>Standalone Java application - A cache of all known packages will be kept statically
	 *  within the package object.</li>
	 *  <li>Java SE application with a persistance unit - Package resolved by searching using
	 *  an entity manager provided by a local entity manager factory.</li>
	 *  <li>Java EE container - Package resolved by searching using an injected entity 
	 *  manager.</li>
	 * </ul>
	 *
	 * @return Package referenced by this source clip.
	 */
	Package resolveReference() 
		throws PackageNotFoundException {
		
		// TODO need to consider standalone vs. J2SE with persistence vs. J2EE with container
		return null;
	}

	public SourceClip clone() {
		
		return (SourceClip) super.clone();
	}
}
