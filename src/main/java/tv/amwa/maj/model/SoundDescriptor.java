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
 * $Log: SoundDescriptor.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:23  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.ElectroSpatialFormulation;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;

/**
 * <p>Specifies the description of a file {@linkplain SourcePackage source package} that 
 * is associated with audio essence.</p>
 * 
 *
 *
 * @see tv.amwa.maj.constant.DataDefinitionConstant#Sound
 * @see SourcePackage#getEssenceDescriptor()
 */
public interface SoundDescriptor 
	extends AAFFileDescriptor {

	/**
	 * <p>Sets the kind of compression and format of compression
	 * information of this sound essence data. This property is
	 * optional and if the property is not present then the described
	 * audio essence is not compressed. Set to <code>null</code>
	 * to indicate that the described audio essence is not compressed.</p>
	 * 
	 * @param compression Kind of compression and format of the
	 * compression for this sound essence data.
	 */
	public void setSoundCompression(
			AUID compression);

	/**
	 * <p>Returns he kind of compression and format of compression
	 * information of this sound essence data. This property is
	 * optional.</p>
	 * 
	 * @return The kind of compression and format of compression for 
	 * the sound essence data.
	 * 
	 * @throws PropertyNotPresentException The optional property is not present in 
	 * the sound descriptor, indicating that the described sound essence is not
	 * compressed.
	 */
	public AUID getSoundCompression()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of audio channels described by this
	 * sound descriptor.</p>
	 * 
	 * @param channelCount Number of audio channels described
	 * by this sound descriptor.
	 * 
	 * @throws IllegalArgumentException The given channel count is negative.
	 */
	public void setChannelCount(
			@UInt32 int channelCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of audio channels described by this
	 * sound descriptor.</p>
	 * 
	 * @return Number of audio channels described by this sound
	 * descriptor.
	 */
	public @UInt32 int getChannelCount();
	
	/**
	 * <p>Sets the audio sampling rate of audio essence described by this sound
	 * descriptor.</p>
	 * 
	 * @param audioSamplingRate Audio sampling rate of the audio essence described by this
	 * sound descriptor.
	 * 
	 * @throws NullPointerException The given audio sampling rate is <code>null</code>.
	 */
	public void setAudioSampleRate(
			Rational audioSamplingRate) 
		throws NullPointerException;
		
	/**
	 * <p>Returns the audio sampling rate of audio essence described by this sound
	 * descriptor.</p>
	 * 
	 * @return Audio sampling rate of audio essence described by this sound descriptor.
	 */
	public Rational getAudioSampleRate();

	/**
	 * <p>Sets the locked flag for the audio essence described this 
	 * sound descriptor, which indicates whether the number of
	 * samples per frame is locked. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param locked Are the number of samples per frame locked?
	 */
	public void setIsLocked(
			@Bool Boolean locked);

	/**
	 * <p>Returns the locked flag for the audio essence described by this 
	 * sound descriptor, which indicates whether the number of
	 * samples per frame is locked. This property is optional.</p>
	 * 
	 * @return Are the number of samples per frame locked?
	 * 
	 * @throws PropertyNotPresentException The optional locked property is not
	 * present in this sound descriptor.
	 * 
	 * @see #getLocked()
	 */
	public @Bool boolean isLocked()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the locked flag for the audio essence described by this 
	 * sound descriptor, which indicates whether the number of
	 * samples per frame is locked. This property is optional.</p>
	 * 
	 * @return Are the number of samples per frame locked?
	 * 
	 * @throws PropertyNotPresentException The optional locked property is not
	 * present in this sound descriptor.
	 * 
	 * @see #isLocked()
	 */
	public @Bool boolean getLocked()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the electro-spatial formulation of the signal of the audio essence
	 * described by this sound descriptor. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * <p>Possible values for the electo-spatial formulation include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.ElectroSpatialFormulation#MultiChannelMode}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.ElectroSpatialFormulation#TwoChannelMode}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.ElectroSpatialFormulation#SingleChannelMode}</li>
	 * </ul> 
	 * 
	 * @param formulation Electro-spatial formulation of the signal 
	 * described by this sound descriptor.
	 */
	public void setElectrospatialFormulation(
			ElectroSpatialFormulation formulation);

	/**
	 * <p>Returns the electro-spatial formulation of the signal described by this
	 * sound descriptor. This property is optional.</p>
	 * 
	 * <p>Possible values for the electo-spatial formulation include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.ElectroSpatialFormulation#MultiChannelMode}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.ElectroSpatialFormulation#TwoChannelMode}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.ElectroSpatialFormulation#SingleChannelMode}</li>
	 * </ul> 
	 * 
	 * @return Electro-spatial formulation of the signal 
	 * of this sound descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional electro-spatial formulation property is not
	 * present in this sound descriptor.
	 */
	public ElectroSpatialFormulation getElectrospatialFormulation()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the audio reference level of the audio essence described by this sound descriptor. This 
	 * property specifies the number of dBm for 0VU. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param level Audio reference level property of this sound descriptor. 
	 */
	public void setAudioReferenceLevel(
			@Int8 Byte level);

	/**
	 * <p>Returns the audio reference level of this sound descriptor. This 
	 * specifies the number of dBm for 0VU. This property is optional.</p>
	 * 
	 * @return Audio reference level property of this sound descriptor. 
	 * 
	 * @throws PropertyNotPresentException The optional audio reference level property is not
	 * present in this sound descriptor.
	 */
	public @Int8 byte getAudioReferenceLevel()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the dial norm of this sound descriptor, which specifies the gain to be applied 
	 * to normalize perceived loudness of the sound, as defined by ITU-R Recommendation BS.1196 (1dB 
	 * per step). Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param dialNorm Dial norm property of the sound descriptor.
	 */
	public void setDialNorm(
			@Int8 Byte dialNorm);

	/**
	 * <p>Returns the dial norm property of this sound descriptor, which
	 * specifies the gain to be applied to normalize perceived loudness 
	 * of the sound, as defined by ITU-R Recommendation BS.1196 (1dB 
	 * per step). This property is optional.</p>
	 * 
	 * @return Dial norm property of this sound descriptor.
	 * 
	 * @throws PropertyNotPresentException This optional dial norm property is not
	 * present in this sound descriptor.
	 */
	public @Int8 byte getDialNorm()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the number of quantization bits used for the audio essence described
	 * by this sound descriptor.</p>
	 * 
	 * @param bitsCount Number of quantization bits of this sound descriptor.
	 * 
	 * @throws IllegalArgumentException The given quantization bits value is negative.
	 */
	public void setQuantizationBits(
			@UInt32 int bitsCount)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of quantization bits used for the audio essence described
	 * by this sound descriptor.</p>
	 * 
	 * @return Number of quantization bits of this sound descriptor.
	 */
	public @UInt32 int getQuantizationBits();
	
	/**
	 * <p>Create a cloned copy of this sound descriptor.</p>
	 * 
	 * @return Cloned copy of this sound descriptor.
	 */
	public SoundDescriptor clone();
}
