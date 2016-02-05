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
 * $Log: EssenceAccess.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/01/27 11:07:33  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.2  2007/12/04 09:29:56  vizigoth
 * Minor formating changes.
 *
 * Revision 1.1  2007/11/13 22:09:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.BadSampleOffsetException;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.exception.SingleChannelOpException;
import tv.amwa.maj.exception.StreamFullException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.misctype.CodecID;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.Rational;

// TODO Documentation for this as its relevance to the MAJ API is understood.
// TODO AAF.idl documentation is the wrong way around ... description of multi is on single channel etc.

/** 
 * <p>Specifies streaming access over a single channel of
 * essence data.  This interface deals with essence data which is in an 
 * uncompressed form, and handles compression or decompression of the 
 * data if required.</p>
 * 
 * <p>You should call the 
 * {@link MaterialPackage#createEssence(int, DataDefinition, AUID, Rational, Rational, tv.amwa.maj.enumeration.CompressEnable, Locator, AUID)
 * createEssence()} 
 * or 
 * {@link MaterialPackage#openEssence(int, tv.amwa.maj.enumeration.CriteriaType, tv.amwa.maj.enumeration.MediaOpenMode, tv.amwa.maj.enumeration.CompressEnable)
 * openEssence()} methods on {@linkplain MaterialPackage material package} in order to get an implementation of essence access, 
 * as no public constructor or other open method is specified by this interface.</p>
 * 
 * <p>Note that the current version of the MAJ API has no built-in codec support, large 
 * essence is not supported and the methods of this interface do nothing.</p>
 * 
 *
 *
 * @see EssenceFormat
 * @see EssenceData
 * @see MaterialPackage
 */

public interface EssenceAccess 
	extends EssenceMultiAccess {

	/**
	 * <p>Sets which flavour of the codec for the essence is to be used.</p>
	 * 
	 * @param flavour The codec flavour to be used.
	 * 
	 * @throws NullPointerException The given codec flavour is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.CodecFlavour
	 */
	public void setEssenceCodecFlavour(
			AUID flavour) 
		throws NullPointerException;

	/**
	 * <p>Writes data to the essence stream.</p>
	 * 
	 * <p>A single video frame is one sample.</p>
	 * 
	 * @param nSamples Write this many samples.
	 * @param buffer Buffer to write data from.
	 * @return The number of samples actually written.
	 * 
	 * @throws NullPointerException The given buffer is <code>null</code>.
	 * @throws SingleChannelOpException Tried to write to an interleaved 
	 * stream.
	 */
	public @UInt32 int writeSamples(
			@UInt32 int nSamples,
			@DataBuffer byte[] buffer) 
		throws NullPointerException,
			SingleChannelOpException;

	/**
	 * <p>Returns the size in bytes of the largest sample for a given
	 * essence type.</p>
	 * 
	 * <p>For uncompressed data, or the output of the software codec, the
	 * sample size is likely to be a constant value.</p>
	 * 
	 * <p>The essence type parameter exists to support codecs with multiple
	 * interleaved essence types.</p>
	 * 
	 * @param essenceType The essence type.
	 * @return The largest sample size for the given essence type.
	 * 
	 * @throws NullPointerException The given essence type is <code>null</code>
	 */
	public @LengthType long getLargestSampleSize(
			DataDefinition essenceType) 
		throws NullPointerException;

	/**
	 * <p>Returns the size in bytes of the given sample for a given essence
	 * type. For uncompressed data, or the output of the software codec, the
	 * sample size is likely be a constant value.</p>
	 * 
	 * <p>The essence type parameter exists to support codecs with multiple
	 * interleaved essence types.</p>
	 * 
	 * @param dataDef The essence type.
	 * @param frameNumber The 0-based index of a sample frame number.
	 * @return Size of the sample frame at the given index for the given type of data.
	 * 
	 * @throws NullPointerException The given essence type is <code>null</code>.
	 * @throws BadSampleOffsetException The supplied sample offset is outside
	 * the acceptable range for the essence.
	 */
	public @LengthType long getIndexedSampleSize(
			DataDefinition dataDef,
			@PositionType long frameNumber) 
		throws NullPointerException,
			BadSampleOffsetException;

	/**
	 * <p>Sets a series of format objects which will be used to describe the 
	 * in-memory format. This is the format expected on writes and produced 
	 * on reads. On writes, the data will be written in this format, except
	 * where a software codec may be used.  On reads, the data will be
	 * translated to this format.</p>
	 * 
	 * <p>The order of the parameters does matter, as transformations will
	 * be applied in that order to get from the on-disk format to the
	 * in-memory format.</p>
	 * 
	 * @param op An ordered list iterator over transforms to apply to the 
	 * essence if required.
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public void setTransformParameters(
			List<? extends EssenceFormat> op) 
		throws NullPointerException; 

	/**
	 * <p>Returns the number of samples of the given essence type on the
	 * essence stream. A video sample is one frame.</p>
	 * 
	 * @param essenceType Type of essence to count.
	 * @return Number of samples that are present for the given essence type.
	 * 
	 * @throws NullPointerException The given essence type is <code>null</code>.
	 */
	public @LengthType long countSamples(
			DataDefinition essenceType) 
		throws NullPointerException;

	/**
	 * <p>Read a given number of samples from an opened essence stream.
	 * This call will only return a single channel of essence from an 
	 * interleaved stream. A signle video sample is a frame.</p>
	 * 
	 * @param nSamples Read this many samples.
	 * @return Buffer containing samples.
	 * 
	 * @throws EndOfDataException Hit the end of the essence (like&nbsp;EOF)
	 * while reading.
	 */
	public @DataBuffer byte[] readSamples(
			@UInt32 int nSamples) 
		throws EndOfDataException;

	
	/** 
	 * <p>Returns the number of samples actually read by the last call
	 * to {@link #readSamples(int)}.</p>
	 * 
	 * @return Number of samples actually read. Will return&nbsp;0 if 
	 * {@link #readSamples(int)} has not previously been called or
	 * 0&nbsp;samples were read.
	 */
	public @UInt32 int getSamplesActuallyRead();

	/**
	 * <p>The seek function for essence. Useful only on reading as
	 * you cannot seek around while writing essence.</p>
	 * 
	 * <p>An audio frame is one sample across all channels.</p>
	 * 
	 * @param sampleFrameNum A 0-based offset in units of the sample rate 
	 * to seek to.
	 * 
	 * @throws BadSampleOffsetException Hit the end of the essence (like&nbsp;EOF) 
	 * while reading.
	 */
	public void seek(
			@PositionType long sampleFrameNum) 
		throws BadSampleOffsetException;

	/**
	 * <p>Given an AAFEssenceFormat, read the essence parameters inside
	 * and set the values from the file format.</p>
	 * 
	 * @param opsTemplate An {@link EssenceFormat} with parameter codes set 
	 * but no values.
	 * @return Another {@link EssenceFormat} with values set.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws InvalidParameterException Matching parameter not found.
	 */
	public EssenceFormat getFileFormat(
			EssenceFormat opsTemplate) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Returns a list of all file format parameters supported by
	 * the current codec.</p>
	 * 
	 * @return List of file format parameters.
	 */
	public EssenceFormat getFileFormatParametersList();

	/**
	 * <p>Returns an empty {@link EssenceFormat} object.  This is the factory
	 * method for EssenceFormat.</p>
	 * 
	 * @return An empty essence format object.
	 */
	public EssenceFormat getEmptyFileFormat();

	/**
	 * <p>Given an {@link EssenceFormat}, read the essence parameters inside
	 * and change the file format.</p>
	 * 
	 * @param ops An {@link EssenceFormat} with one or more parameter/value 
	 * pairs.
	 * @throws NullPointerException Argument is null.
	 */
	public void putFileFormat(
			EssenceFormat ops) throws NullPointerException;

	
	/**
	 * <p>Returns the friendly name of the codec expanded for human
	 * consumption. No other call uses this name, so it may be fully
	 * descriptive, especially of limitations.</p>
	 * 
	 * @return Human-readable name and optional description of codec.
	 */
	public @AAFString String getCodecName();

	/**
	 * <p>Returns the identifier of the codec being used to handle the specified 
	 * essence. This will be required in order to be able to send private data to the
	 * codec.</p>
	 * 
	 * @return The current codec used for accessing the essence.
	 * 
	 * @see CodecDefinition
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public @CodecID AUID getCodecID();

	/**
	 * <p>Handle any format related writing at the end of the essence and adjust 
	 * {@linkplain Package package} lengths as required.  Must be called before releasing a write essence
	 * access.</p>
	 * 
	 * @throws StreamFullException The stream has insufficient capacity to complete the
	 * write operation.
	 */
	public void completeWrite() 
		throws StreamFullException;

}
