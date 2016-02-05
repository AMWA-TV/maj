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
 * $Log: MPEGVideoDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.1  2007/11/13 22:08:32  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.ContentScanningType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;

/** 
 * <p>Specifies the description of picture essence that is encoded according to the 
 * MPEG specifications.</p>
 *
 *
 *
 * @see SourcePackage#getEssenceDescriptor()
 */
public interface MPEGVideoDescriptor
	extends CDCIDescriptor {

	/**
	 * <p>Sets whether the described MPEG video essence consists of a single MPEG sequence or 
	 * if there are a number of sequences. Set to <code>true</code> if the sequence
	 * has only one sequence and <code>false</code> if it contains more than one. Set this
	 * optional property to <code>null</code> to omit it.</p>
	 *
	 * @param singleSequence Does the described essence contain only a single sequence?
	 */
	public void setSingleSequence(
			Boolean singleSequence);

	/**
	 * <p>Determines whether the described MPEG video essence consists of a single MPEG sequence or 
	 * if there are a number of sequences. Returns <code>true</code> if the sequence
	 * has only one sequence and <code>false</code> if it contains more than one. This is
	 * an optional property.</p>
	 *
	 * @return Does the described essence contain only a single sequence?
	 * 
	 * @throws PropertyNotPresentException The optional single sequence property is not
	 * present for this MPEG video descriptor.
	 */
	public boolean getSingleSequence()
		throws PropertyNotPresentException;

	/**
	 * <p>Set to <code>true</code> to indicate that the described MPEG video essence always has a 
	 * constant number of B&nbsp;frames throughout or to <code>false</code> to indicate that the 
	 * number of B&nbsp;frames vary. Set this optional property to <code>null</code> to omit
	 * it.</p>
	 *
	 * @param constanBPictureCount Does the described essence to have a constant number of
	 * B&nbsp;frames?
	 */
	public void setConstantBPictureCount(
	    	Boolean constanBPictureCount);

	/**
	 * <p>Returns <code>true</code> if the associated essence always has a constant 
	 * number of B frames throughout, or <code>false</code> if the number of B&nbsp;frames vary. 
	 * This is an optional property.</p>
	 *
	 * @return Does the described essence to have a constant number of B&nbsp;frames?
	 * 
	 * @throws PropertyNotPresentException The optional constant B&nbsp;frames property is
	 * not present in this MPEG video descriptor.
	 */
	public boolean getConstantBPictureCount()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets whether the underlying content of the associated MPEG-coded essence was of 
	 * a known content scanning type. Possible values are {@link ContentScanningType#Progressive Progressive},
	 * {@link ContentScanningType#Interlace Interlace} and {@link ContentScanningType#Mixed Mixed},
	 * or set to {@link ContentScanningType#NotKnown NotKnown} if the value is unknown. Set this
	 * optional property to <code>null</code> to omit it.</p>
	 *
	 * @param codedContentScanning Content scanning type of data represented by the underlying content
	 * of the associated MPEG-coded essence. 
	 */
	public void setCodedContentScanning(
			ContentScanningType codedContentScanning);

	/**
	 * <p>Returns whether the underlying content of the descrived MPEG-coded essence was of 
	 * a known content scanning type. Possible values are 
	 * {@link tv.amwa.maj.enumeration.ContentScanningType#Progressive Progressive},
	 * {@link tv.amwa.maj.enumeration.ContentScanningType#Interlace Interlace} and 
	 * {@link tv.amwa.maj.enumeration.ContentScanningType#Mixed Mixed},
	 * or returns {@link tv.amwa.maj.enumeration.ContentScanningType#NotKnown NotKnown} if the 
	 * value is unknown. This is an optional property.</p>
	 *
	 * @return Content scanning type of data represented by the underlying content of the 
	 * described MPEG-coded essence. 
	 * 
	 * @throws PropertyNotPresentException The optional coded content type property is not
	 * present in this MPEG video descriptor.
	 */
	public ContentScanningType getCodedContentScanning()
		throws PropertyNotPresentException;

	/**
	 * <p>Set to <code>true</code> if the low delay mode was used in the sequence 
	 * represented by the described MPEG video essence and <code>false</code> otherwise. 
	 * Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param lowDelay Was the low delay mode used in the sequence?
	 */
	public void setLowDelay(
			Boolean lowDelay);

	/**
	 * <p>Returns <code>true</code> if the low delay mode was used in the sequence 
	 * represented by the described MPEG-video essence and <code>false</code> otherwise.
	 * This is an optional property.</p>
	 *
	 * @return Was the low delay mode used in the sequence?
	 * @throws PropertyNotPresentException The optional low delay property is not
	 * present in this MPEG video descriptor.
	 */
	public boolean getLowDelay()
		throws PropertyNotPresentException;

	/**
	 * <p>Set to <code>true</code> to indicate that "ClosedGop" is set in all GOP headers of
	 * the described MPEG video essence, as per ISO/IEC&nbsp;13818-1 IBP descriptor, otherwise 
	 * <code>false</code>. Set this optional to <code>null</code> to omit it.</p>
	 *
	 * @param closedGOP Is "ClosedGOP" set in all the headers of the described MPEG video essence.?
	 */
	public void setClosedGOP (
			Boolean closedGOP);

	/**
	 * <p>Returns <true>code</true> if "ClosedGop" is set in all GOP headers of the
	 * described MPEG video essence, as per ISO/IEC&nbsp;13818-1 IBP descriptor, otherwise 
	 * <code>false</code>. This is an optional property.</p>
	 *
	 * @return Is "ClosedGOP" set in all the headers of the described MPEG video descriptor?
	 * 
	 * @throws PropertyNotPresentException The optional closed GOP property is not
	 * present in this MPEG video descriptor.
	 */
	public boolean getClosedGOP()
		throws PropertyNotPresentException;

	/**
	 * <p>Set to <code>true</code> to indicate that every GOP in the described MPEG video sequence
	 * is constructed in the same way, as per ISO/IEC&nbsp;13818-1 IBP descriptor, otherwise 
	 * <code>false</code>. Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param identicalGOP Is every GOP in the described MPEG-video sequence constructed in the 
	 * same way?
	 */
	public void setIdenticalGOP(
	    	Boolean identicalGOP);

	/**
	 * <p>Returns <code>true</code> if every GOP in the described MPEG video sequence
	 * is constructed in the same way, as per ISO/IEC13818-1 IBP descriptor, otherwise 
	 * <code>false</code>.</p>
	 *
	 * @return Is every GOP in the associated sequence constructed in the 
	 * same way?
	 * 
	 * @throws PropertyNotPresentException The optional identical GOP property is
	 * not present in this MPEG video descriptor.
	 */
	public boolean getIdenticalGOP()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum occurring spacing between I&nbsp;frames in the described
	 * MPEG video essence, as per ISO/IEC&nbsp;13818-1 IBP descriptor. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 *
	 * @param maxGOP Maximum occurring spacing between I&nbsp;frames of the described
	 * MPEG video essence.
	 * 
	 * @throws IllegalArgumentException The given max GOP value is negative.
	 */
	public void setMaxGOP(
			@UInt16 Short maxGOP)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum occurring spacing between I&nbsp;frames in the described
	 * MPEG video essence, as per an ISO/IEC&nbsp;13818-1 IBP descriptor. This is an optional
	 * property.</p>
	 *
	 * @return Maximum occurring spacing between I&nbsp;frames in the described MPEG video
	 * essence.
	 * 
	 * @throws PropertyNotPresentException The optional max GOP property is not
	 * present in this MPEG video descriptor.
	 */
	public short getMaxGOP()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum number of B&nbsp;pictures between P&nbsp;frames or I&nbsp;frames in the
	 * described MPEG video essence, equivalent to ISO/IEC&nbsp;13818-2 annex&nbsp;D (M-1). Set this optional 
	 * property to <code>null</code> to omit it.</p>
	 *
	 * @param maxBPictureCount Maximum number of B&nbsp;pictures between P&nbsp;frames or I&nbsp;frames
	 * in the described MPEG video essence.
	 * @throws IllegalArgumentException The given maximum B picture count is negative.
	 */
	public void setMaxBPictureCount(
			@UInt16 Short maxBPictureCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum number of B&nbsp;pictures between P&nbsp;frames or I&nbsp;frames in the
	 * described MPEG video essence, equivalent to ISO/IEC&nbsp;13818-2 annex&nbsp;D (M-1). This is an optional
	 * property.</p>
	 * 
	 * @return Maximum number of B&nbsp;pictures between P&nbsp;frames or I&nbsp;frames
	 * in the described MPEG video essence.
	 * 
	 * @throws PropertyNotPresentException The optional maximum B&nbsp;picture count property
	 * is not present in this MPEG video descriptor.
	 */
	public short getMaxBPictureCount ()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the maximum bit rate of the MPEG elementary stream of the described MPEG video 
	 * essence, measured in bits per second. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 *
	 * @param bitRate Maximum bit rate of the MPEG elementary stream of the described MPEG video 
	 * essence.
	 * 
	 * @throws IllegalArgumentException The given bit rate is negative.
	 */
	public void setBitRate(
			@UInt32 Integer bitRate)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the maximum bit rate of the MPEG elementary stream of the described MPEG video
	 * essence, measured in bits per second.</p>
	 *
	 * @return Maximum bit rate of the MPEG elementary stream of the described MPEG video 
	 * essence.
	 * 
	 * @throws PropertyNotPresentException The optional bit rate property is not
	 * present in this MPEG video descriptor.
	 */
	public @UInt32 int getBitRate()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the MPEG-2 video profile and level of the described MPEG video essence. For the main
	 * profile and main level, set to <code>0x48</code>. For 4:2:2 profile at main level,
	 * set to <code>0x85</code>. Set this optional property to <code>null</code> to omit it.</p>
	 *
	 * @param profileAndLevel MPEG-2 video profile and level of the described MPEG video essence.
	 */
	public void setProfileAndLevel (
			@UInt8 Byte profileAndLevel);

	/**
	 * <p>Returns the MPEG-2 video profile and level of the described MPEG video essence. For the main
	 * profile and main level, the value returned is <code>0x48</code>. For 4:2:2 profile at 
	 * main level, the value returned is <code>0x85</code>. This is an optional property.</p>
	 *
	 * @return MPEG-2 video profile and level of the described MPEG video essence.
	 * 
	 * @throws PropertyNotPresentException The optional profile and level property is not
	 * present in this MPEG video descriptor.
	 */
	public @UInt8 byte getProfileAndLevel()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this MPEG video descriptor.</p>
	 *
	 * @return Cloned copy of this MPEG video descriptor.
	 */
	public MPEGVideoDescriptor clone();
}
