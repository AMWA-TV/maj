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
 * $Log: HTMLClipImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:13  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.HTMLClip;
import tv.amwa.maj.record.PackageID;


/** 
 * <p>Implements a reference to HTML text essence.</p>
 *
 *
 *
 */
@MediaClass(uuid1 =  0x0D010101, uuid2 = 0x0101, uuid3 = 0x1300,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "HTMLClip",
		  description = "Reference to HTML text essence.",
		  symbol = "HTMLClip")
public class HTMLClipImpl
	extends 
		TextClipImpl
	implements 
		HTMLClip,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -4233608050787321967L;
	
	private String beginAnchor = null;
	private String endAnchor = null;
	
	public HTMLClipImpl() { }

	/**
	 * <p>Creates and initializes a new HTML clip, which represents an HTML document 
	 * and identifies the source of the document. An HTML clip must have a data definition of 
	 * {@link DataDefinitionImpl#Auxiliary} and is static and so has no length.</p>
	 * 
	 * @param sourceID Reference to the package containing the HTML essence.
	 * @param sourceTrackID Reference to the track in the package containing the HTML clip.
	 * 
	 * @throws NullPointerException The source ID argument is <code>null</code>.
	 * @throws IllegalArgumentException The track id value must be non-negative.
	 */
	public HTMLClipImpl(
			PackageID sourceID,
			@UInt32 int sourceTrackID)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (sourceID == null)
			throw new NullPointerException("Cannot create an HTML clip with a null source id.");
		if (sourceTrackID < 0)
			throw new IllegalArgumentException("Cannot create an HTML clip with a negative track id reference.");
		
		setComponentDataDefinition(DataDefinitionImpl.forIdentification(DataDefinitionImpl.Auxiliary));
		setLengthPresent(false);
		setSourcePackageID(sourceID);
		setSourceTrackID(sourceTrackID);
	}
	
	@MediaProperty(uuid1 = 0x05300601, uuid2 = 0x0100, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x02},
			     definedName = "BeginAnchor",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x1401,
			     symbol = "BeginAnchor")	     
	public String getBeginAnchor()
			throws PropertyNotPresentException {

		if (beginAnchor == null)
			throw new PropertyNotPresentException("The optional begin anchor property is not present in this HTML clip.");
		
		return beginAnchor;
	}

	@MediaPropertySetter("BeginAnchor")
	public void setBeginAnchor(
			String beginAnchor) {

		this.beginAnchor = beginAnchor;
	}

	@MediaProperty(uuid1 = 0x05300602, uuid2 = 0x0100, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x02},
			     definedName = "EndAnchor",
			     typeName = "UTF16String",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x1402,
			     symbol = "EndAnchor")
	public String getEndAnchor()
			throws PropertyNotPresentException {
	
		if (endAnchor == null)
			throw new PropertyNotPresentException("The optional end anchor property is not present in this HTML clip.");

		return endAnchor;
	}

	@MediaPropertySetter("EndAnchor")
	public void setEndAnchor(
			String endAnchor) {

		this.endAnchor = endAnchor;
	}

	@Override
	public HTMLClip clone() {
		
		return (HTMLClip) super.clone();
	}
}
