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
 * $Log: AAFFileDescriptor.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/02/28 12:50:31  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.3  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2008/01/27 11:07:36  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:09:01  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.BadPropertyException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.TrackID;
import tv.amwa.maj.record.Rational;


/**
 * <p>Specifies a file descriptor that describes an essence source that is directly 
 * manipulated by an AAF application.</p>
 * 
 * <p>A {@linkplain SourcePackage source package} that contains a file descriptor is also
 * known as an MXF <em>file package</em> or a <em>file source package</em>.</p>
 * 
 *
 * 
 * @see EssenceDescriptor
 * @see SourcePackage#getEssenceDescriptor()
 * @see MultipleDescriptor#getFileDescriptors()
 * @see tv.amwa.maj.industry.TypeDefinitions#FileDescriptorStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#FileDescriptorStrongReferenceVector
 */

public abstract interface AAFFileDescriptor 
	extends EssenceDescriptor {

	/**
	 * <p>Sets the length of the essence in sample units. This is an optional property that should
	 * only be set when the encoder knows the value. </p>
	 * 
	 * <p>Note that the sample rate is defined in MXF in terms of the number of addressable units, so 
	 * for PCM audio this is one unit per audio sample and for MPEG audio this is one unit per frame.</p>
	 * 
	 * @param length Length of the essence in samples.
	 * 
	 * @throws BadPropertyException The length property is not present as this file 
	 * descriptor describes static essence.
	 * @throws BadLengthException The length of the described material is negative.
	 */
	public void setEssenceLength(
			@LengthType Long length)
		throws BadPropertyException,
			BadLengthException;
	
	/**
	 * <p>Returns the length of the essence in sample units. Set this optional property to <code>null</code> to 
	 * omit it.</p>
	 * 
	 * <p>Note that the sample rate is defined in MXF in terms of the number of addressable units, so 
	 * for PCM audio this is one unit per audio sample and for MPEG audio this is one unit per frame.</p>
	 * 
	 * @return Length of essence in samples.
	 * 
	 * @throws BadPropertyException The length property is not present as this file 
	 * descriptor describes static essence.
	 * @throws PropertyNotPresentException The optional essence length property is not present for this file descriptor.
	 */
	public @LengthType long getEssenceLength()
		throws BadPropertyException,
			PropertyNotPresentException;

	/**
	 * <p>Set the {@linkplain CodecDefinition codec} of the described file, which
	 * identifies the mechanism used to compress and uncompress samples of
	 * essence or used to convert samples of essence from one format to another.</p>
	 * 
	 * @param codecDef Codec that was used for the file.
	 * 
	 * @throws NullPointerException The given codec is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public void setCodec(
			CodecDefinition codecDef);

	/**
	 * <p>Returns the {@linkplain CodecDefinition codec} of the described file, which
	 * identifies the mechanism used to compress and uncompress samples of
	 * essence or used to convert samples of essence from one format to another.</p>
	 * 
	 * @return Codec used to make the file.
	 * 
	 * @throws PropertyNotPresentException The optional codec definition property is not present for 
	 * this file descriptor.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionWeakReference
	 */
	public CodecDefinition getCodec()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets sample rate of the essence.</p>
	 * 
	 * <p>The MXF specification further defines this to be the rate of the addressable units in the stream.
	 * For PCM audio, this is the same as the audio sampling rate, for MPEG audio this is the same as the
	 * audio frame rate (e.g. AAC audio has 1024 samples per frame, so the rate is 48000/1024).</p> 
	 * 
	 * @param rate Sample rate of the essence.
	 * 
	 * @throws NullPointerException The given sample rate is <code>null</code>.
	 * @throws BadPropertyException The sample rate property is not present as this file 
	 * descriptor describes static essence.
	 */
	public void setSampleRate(
			Rational rate) 
		throws NullPointerException,
			BadPropertyException;

	/**
	 * <p>Returns the sample rate of the essence.</p>
	 * 
	 * <p>The MXF specification further defines this to be the rate of the addressable units in the stream.
	 * For PCM audio, this is the same as the audio sampling rate. For MPEG audio, this is the same as the
	 * audio frame rate (e.g. AAC-encoding of 48kHz audio has 1024 samples per frame, so the rate is 48000/1024).</p> 
	 * 
	 * @return Sample rate of the essence.
	 * 
	 * @throws BadPropertyException The sample rate property is not present as this file 
	 * descriptor describes static essence.
	 */
	public Rational getSampleRate()
		throws BadPropertyException;

	/**
	 * <p>Specifies the {@linkplain ContainerDefinition file format} of the described file, which
	 * identifies the container mechanism used to store the essence.</p>
	 * 
	 * <p>The container format is defined as an optional property but version&nbsp;1.1 of 
	 * the AAF object specification requires it to be specified for each file. The property
	 * can be omitted by setting its value to <code>null</code> for compatibility with
	 * older versions of the specification.</p>
	 * 
	 * @param format File format to set for this file descriptor.
	 * 
	 * @see tv.amwa.maj.constant.ContainerConstant
	 */
	public void setContainerFormat(
			ContainerDefinition format);

	/**
	 * <p>Returns the {@linkplain ContainerDefinition file format} of the described file, which
	 * identifies the container mechanism used to store the essence.</p>
	 * 
	 * <p>The container format is defined as an optional property but version&nbsp;1.1 of 
	 * the AAF object specification requires it to be specified for each file. However,
	 * file descriptors created with tools supporting earlier versions of the AAF
	 * specification may omit the property, in which case a {@link PropertyNotPresentException}
	 * is thrown.</p>
	 * 
	 * @return File format of the described file.
	 * 
	 * @throws PropertyNotPresentException The (rarely) optional container format property
	 * is not present in this file descriptor.
	 * 
	 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionWeakReference
	 */
	public ContainerDefinition getContainerFormat()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Set the linked track id of this file descriptor, which specifies which 
	 * {@linkplain Track track} of the associated {@linkplain SourcePackage source package} 
	 * this descriptor describes when the source package is described by a {@linkplain MultipleDescriptor 
	 * multiple descriptor}. In this case, this file descriptor is one of the collection of file descriptors
	 * that are part of a multiple descriptor, as returned by {@link MultipleDescriptor#getFileDescriptors()}. If this
	 * file descriptor is not part of a multiple descriptor collection, set this optional property to 
	 * <code>null</code> to omit it.</p>
	 *
	 * @param linkedTrackID Linked track id of this file descriptor.
	 * 
	 * @throws IllegalArgumentException The linked track id is negative.
	 */
	public void setLinkedTrackID(
			@TrackID Integer linkedTrackID)
		throws IllegalArgumentException;
	
	/**
	 * <p>Returns the linked track id of this file descriptor, which specifies which 
	 * {@linkplain Track track} of the associated {@linkplain SourcePackage source package} 
	 * this descriptor describes when the source package is described by a {@linkplain MultipleDescriptor 
	 * multiple descriptor}. This is an optional property.</p>
	 *
	 * @return Linked track id of this file descriptor. 
	 * 
	 * @throws PropertyNotPresentException The optional linked track id property is not
	 * present in this file descriptor.
	 */
	public @TrackID int getLinkedTrackID()
		throws PropertyNotPresentException;
	
	/** 
	 * <p>Create a cloned copy of this file descriptor.</p>
	 * 
	 * @return Cloned copy of this file descriptor.
	 */
	public AAFFileDescriptor clone();
}

