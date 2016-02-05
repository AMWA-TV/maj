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
 * $Log: ParameterConstant.java,v $
 * Revision 1.5  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/12/12 12:29:53  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:41  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access unique identifiers for parameters to 
 * {@linkplain tv.amwa.maj.model.OperationDefinition operations}, such as the video and audio
 * effects defined by the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF edit protocol</a>. 
 * Additional information about each parameter appears in the {@linkplain ParameterDescription
 * parameter description} that annotates each {@linkplain tv.amwa.maj.record.AUID} constant.</p> 
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported parameters.</p>
 * 
 * @see tv.amwa.maj.model.ParameterDefinition
 * @see tv.amwa.maj.model.OperationDefinition
 * @see tv.amwa.maj.constant.ParameterDescription
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterDefinitionWeakReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#ParameterStrongReferenceVector
 * 
 *
 *
 */
public interface ParameterConstant {

	/**
	 * <p>Used to specify the level of mixing between input sources. This parameter is a 
	 * {@link tv.amwa.maj.record.Rational Rational} and must be in the range 0&nbsp;to&nbsp;1.</p>
	 * 
	 * <p>Parameter to operations:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoDissolve video dissolve}</li>
	 *  <li>{@linkplain OperationConstant#SMPTEVideoWipe SMPTE video wipe}</li>
	 *  <li>{@linkplain OperationConstant#MonoAudioDissolve mono audio dissolve}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID Level = new tv.amwa.maj.record.impl.AUIDImpl(
			0xe4962320, (short) 0x2267, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/**
	 * <p>Used to specify a SMPTE Wipe Number as defined in section&nbsp;7.6.33 of SMPTE&nbsp;258M.
	 * This parameter is a value of {@link tv.amwa.maj.integer.Int32} type.
	 * Note that there are many more wipe patterns defined in SMPTE&258M than are commonly used by
	 * video authoring applications. </p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#SMPTEVideoWipe SMPTE video wipe}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Int32")
	public final static AUID SMPTEWipeNumber = new tv.amwa.maj.record.impl.AUIDImpl(
			0xe4962323, (short) 0x2267, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** TODO find and write a description of this
	 * <p>This parameter is a value of {@linkplain tv.amwa.maj.misctype.Bool boolean} type.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#SMPTEVideoWipe SMPTE video wipe}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEReverse = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba0, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/**
	 * <p>Used to specify the ratio of output length to input length for a 
	 * {@linkplain OperationConstant#VideoSpeedControl video speed control operation}. </p>
	 * 
	 * <p>This parameter is a value of {@link tv.amwa.maj.record.Rational} type. The range of the 
	 * parameter is -2<sup>31</sup>&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1, excluding 0. For example, 
	 * a value of 1/2 specifies half normal frame rate and so appears as slow motion. A 
	 * value of -1/1 appears at normal frame rate played in reverse.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoSpeedControl video speed control}</li>
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID SpeedRatio = new tv.amwa.maj.record.impl.AUIDImpl(
			0x72559a80, (short) 0x24d7, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x50, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/**
	 * <p>Used to specify a horizontal position offset for a {@linkplain OperationConstant#VideoPosition video position
	 * operation}.</p>
	 * 
	 * <p>This parameter is a value of {@link tv.amwa.maj.record.Rational} type. The range of the 
	 * parameter is -2<sup>31</sup>&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1. A value of -1 offsets
	 * an image center to the left of the image and a value of 1 offsets the image center to the right
	 * of the image.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoPosition video position}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PositionOffsetX = new tv.amwa.maj.record.impl.AUIDImpl(
			0xc573a510, (short) 0x071a, (short) 0x454f, 
			new byte[] { (byte) 0xb6, 0x17, (byte) 0xad, 0x6a, (byte) 0xe6, (byte) 0x90, 0x54, (byte) 0xc2 });

	/**
	 * <p>Used to specify a vertical position offset for a {@linkplain OperationConstant#VideoPosition video position
	 * operation}.</p>
	 * 
	 * <p>This parameter is a value of {@link tv.amwa.maj.record.Rational} type. The range of the 
	 * parameter is -2<sup>31</sup>&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1. A value of -1 offsets
	 * an image center to the top of the image and a value of 1 offsets the image center to the bottom
	 * of the image.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoPosition video position}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PositionOffsetY = new tv.amwa.maj.record.impl.AUIDImpl(
			0x82e27478, (short) 0x1336, (short) 0x4ea3, 
			new byte[] { (byte) 0xbc, (byte) 0xb9, 0x6b, (byte) 0x8f, 0x17, (byte) 0x86, 0x4c, 0x42 });

	/**
	 * <p>Used to specify the left-hand boundary of a {@linkplain OperationConstant#VideoCrop video crop
	 * operation}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of the the original image coordinate space and represented
	 * by a value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, where
	 * -1 is the left of the image and 1 is the right of the image.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCrop video crop}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID CropLeft = new tv.amwa.maj.record.impl.AUIDImpl(
			0xd47b3377, (short) 0x318c, (short) 0x4657, 
			new byte[] { (byte) 0xa9, (byte) 0xd8, 0x75, (byte) 0x81, 0x1b, 0x6d, (byte) 0xc3, (byte) 0xd1 });

	/**
	 * <p>Used to specify the right-hand boundary of a {@linkplain OperationConstant#VideoCrop video crop
	 * operation}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of the the original image coordinate space and represented
	 * by a value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, where
	 * -1 is the left of the image and 1 is the right of the image.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCrop video crop}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID CropRight = new tv.amwa.maj.record.impl.AUIDImpl(
			0x5ecc9dd5, (short) 0x21c1, (short) 0x462b, 
			new byte[] { (byte) 0x9f, (byte) 0xec, (byte) 0xc2, (byte) 0xbd, (byte) 0x85, (byte) 0xf1, 0x40, 0x33 });

	/**
	 * <p>Used to specify the top boundary of a {@linkplain OperationConstant#VideoCrop video crop
	 * operation}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of the the original image coordinate space and represented
	 * by a value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, where
	 * -1 is the left of the image and 1 is the right of the image.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCrop video crop}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID CropTop = new tv.amwa.maj.record.impl.AUIDImpl(
			0x8170a539, (short) 0x9b55, (short) 0x4051, 
			new byte[] { (byte) 0x9d, 0x4e, 0x46, 0x59, (byte) 0x8d, 0x01, (byte) 0xb9, 0x14 });

	/**
	 * <p>Used to specify the bottom boundary of a {@linkplain OperationConstant#VideoCrop video crop
	 * operation}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of the the original image coordinate space and represented
	 * by a value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, where
	 * -1 is the top of the image and 1 is the bottom of the image.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCrop video crop}</li>
	 * </ul>
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID CropBottom = new tv.amwa.maj.record.impl.AUIDImpl(
			0x154ba82b, (short) 0x990a, (short) 0x4c80, 
			new byte[] { (byte) 0x91, 0x01, 0x30, 0x37, (byte) 0xe2, (byte) 0x88, 0x39, (byte) 0xa1 });

	/**
	 * <p>Used to specify the horizontal scaling factor for an image as part of a 
	 * {@linkplain OperationConstant#VideoScale video scaling operation}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of 
	 * the original image width, with the result centered about 0,0 and represented as a value of 
	 * {@link tv.amwa.maj.record.Rational} type. The range of the value is 0&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1,
	 * with a value of 1 representing the original size.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoScale video scale}</li>
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID ScaleX = new tv.amwa.maj.record.impl.AUIDImpl(
			0x8d568129, (short) 0x847e, (short) 0x11d5, 
			new byte[] { (byte) 0x93, 0x5a, 0x50, (byte) 0xf8, 0x57, (byte) 0xc1, 0x00, 0x00 });

	/**
	 * <p>Used to specify the vertical scaling factor for an image as part of a 
	 * {@linkplain OperationConstant#VideoScale video scaling operation}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of 
	 * the original image height, with the result centered about 0,0 and represented as a value of 
	 * {@link tv.amwa.maj.record.Rational} type. The range of the value is 0&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1,
	 * with a value of 1 representing the original size.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoScale video scale}</li>
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID ScaleY = new tv.amwa.maj.record.impl.AUIDImpl(
			0x8d56812a, (short) 0x847e, (short) 0x11d5, 
			new byte[] { (byte) 0x93, 0x5a, 0x50, (byte) 0xf8, 0x57, (byte) 0xc1, 0x00, 0x00 });

	/**
	 * <p>Used to specify the angle of clockwise rotation for an image as part of a 
	 * {@linkplain OperationConstant#VideoRotate video rotate operaetion}.</p>
	 * 
	 * <p>This parameter is expressed as a fraction of one complete revolution, represented as a
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is 0&nbsp;to&nbsp;1,
	 * with 0 representing no rotation and 1 a full clockwise rotation.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoRotate video rotate}</li>
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID Rotation = new tv.amwa.maj.record.impl.AUIDImpl(
			0x062cfbd8, (short) 0xf4b1, (short) 0x4a50, 
			new byte[] { (byte) 0xb9, 0x44, (byte) 0xf3, (byte) 0x9e, 0x2f, (byte) 0xc7, 0x3c, 0x17 });

	/**
	 * <p>Used to specify the horizontal component of the top left corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the left of the image and 1 representing the right.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinTopLeftX = new tv.amwa.maj.record.impl.AUIDImpl(
			0x72a3b4a2, (short) 0x873d, (short) 0x4733, 
			new byte[] { (byte) 0x90, 0x52, (byte) 0x9f, (byte) 0x83, (byte) 0xa7, 0x06, (byte) 0xca, 0x5b });

	/**
	 * <p>Used to specify the vertical component of the top left corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the top of the image and 1 representing the bottom.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinTopLeftY = new tv.amwa.maj.record.impl.AUIDImpl(
			0x29e4d78f, (short) 0xa502, (short) 0x4ebb, 
			new byte[] { (byte) 0x8c, 0x07, (byte) 0xed, 0x5a, 0x03, 0x20, (byte) 0xc1, (byte) 0xb0 });

	/**
	 * <p>Used to specify the horizontal component of the top right corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the left of the image and 1 representing the right.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinTopRightX = new tv.amwa.maj.record.impl.AUIDImpl(
			0xa95296c0, (short) 0x1ed9, (short) 0x4925, 
			new byte[] { (byte) 0x84, (byte) 0x81, 0x20, (byte) 0x96, (byte) 0xc7, 0x2e, (byte) 0x81, (byte) 0x8d });

	/**
	 * <p>Used to specify the vertical component of the top right corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the top of the image and 1 representing the bottom.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinTopRightY = new tv.amwa.maj.record.impl.AUIDImpl(
			0xce1757ae, (short) 0x7a0b, (short) 0x45d9, 
			new byte[] { (byte) 0xb3, (byte) 0xf3, 0x36, (byte) 0x86, (byte) 0xad, (byte) 0xff, 0x1e, 0x2d });

	/**
	 * <p>Used to specify the horizontal component of the bottom left corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the left of the image and 1 representing the right.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinBottomLeftX = new tv.amwa.maj.record.impl.AUIDImpl(
			0x08b2bc81, (short) 0x9b1b, (short) 0x4c01, 
			new byte[] { (byte) 0xba, 0x73, (byte) 0xbb, (byte) 0xa3, 0x55, 0x4e, (byte) 0xd0, 0x29 });

	/**
	 * <p>Used to specify the vertical component of the bottom left corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the top of the image and 1 representing the bottom.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinBottomLeftY = new tv.amwa.maj.record.impl.AUIDImpl(
			0xc163f2ff, (short) 0xcd83, (short) 0x4655, 
			new byte[] { (byte) 0x82, 0x6e, 0x37, 0x24, (byte) 0xab, 0x7f, (byte) 0xa0, (byte) 0x92 });

	/**
	 * <p>Used to specify the horizontal component of the bottom right corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the left of the image and 1 representing the right.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinBottomRightX = new tv.amwa.maj.record.impl.AUIDImpl(
			0x53bc5884, (short) 0x897f, (short) 0x479e, 
			new byte[] { (byte) 0xb8, 0x33, 0x19, 0x1f, (byte) 0x86, (byte) 0x92, 0x10, 0x0d });

	/**
	 * <p>Used to specify the vertical component of the bottom right corner of a quadrilateral defining
	 * a {@linkplain OperationConstant#VideoCornerPinning video corner pinning operation}.</p>
	 * 
	 * <p>The parameter is expressed as a fraction of the original image size and represented as a 
	 * value of {@link tv.amwa.maj.record.Rational} type. The range of the value is -1&nbsp;to&nbsp;1, with
	 * -1 representing the top of the image and 1 representing the bottom.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoCornerPinning video corner pinning}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID PinBottomRightY = new tv.amwa.maj.record.impl.AUIDImpl(
			0x812fb15b, (short) 0x0b95, (short) 0x4406, 
			new byte[] { (byte) 0x87, (byte) 0x8d, (byte) 0xef, (byte) 0xaa, 0x1c, (byte) 0xff, (byte) 0xc1, 0x29 });

	/**
	 * <p>Used to specify whether the alpha transparency level property of the {@linkplain tv.amwa.maj.model.PictureDescriptor
	 * digital image descriptor} associated with an alpha transparency effect
	 * should be inverted or not. This parameter is represented by a value of {@linkplain tv.amwa.maj.misctype.Bool boolean}
	 * type.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoAlphaWithinVideoKey alpha with video key effect}</li>
	 *  <li>{@linkplain OperationConstant#VideoSeparateAlphaKey separate alpha key effect}
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Boolean")
	public final static AUID AlphaKeyInvertAlpha = new tv.amwa.maj.record.impl.AUIDImpl(
			0xa2667f65, (short) 0x65d8, (short) 0x4abf, 
			new byte[] { (byte) 0xa1, 0x79, 0x0b, (byte) 0x9b, (byte) 0x93, 0x41, 0x39, 0x49 });

	/**
	 * <p>Used to specify the luminance key level for a {@linkplain OperationConstant#VideoLuminanceKey luminance 
	 * key operation}. This parameter is represented by a value of {@linkplain tv.amwa.maj.record.Rational} type in
	 * the range 0&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1, with 0 representing black and 1 representing white.
	 * </p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoLuminanceKey luminance key}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID LumKeyLevel = new tv.amwa.maj.record.impl.AUIDImpl(
			0x21ed5b0f, (short) 0xb7a0, (short) 0x43bc, 
			new byte[] { (byte) 0xb7, 0x79, (byte) 0xc4, 0x7f, (byte) 0x85, (byte) 0xbf, 0x6c, 0x4d });

	/**
	 * <p>Used to specify the luminance key clip for a {@linkplain OperationConstant#VideoLuminanceKey luminance
	 * key operation}. This parameter is represented by a value of {@linkplain tv.amwa.maj.record.Rational} type in
	 * the range 0&nbsp;to&nbsp;1, with 0 representing black and 1 representing white.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#VideoLuminanceKey luminance key}</li>
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID LumKeyClip = new tv.amwa.maj.record.impl.AUIDImpl(
			0xcbd39b25, (short) 0x3ece, (short) 0x441e, 
			new byte[] { (byte) 0xba, 0x2c, (byte) 0xda, 0x47, 0x3a, (byte) 0xb5, (byte) 0xcc, 0x7c });

	/**
	 * <p>Used to specify the amplitude multiplier for an {@linkplain OperationConstant#MonoAudioGain mono
	 * audio gain operation}. This parameter is represented by a value of {@linkplain tv.amwa.maj.record.Rational} 
	 * type in the range 0&nbsp;to&nbsp;2<sup>31</sup>&nbsp;-&nbsp;1, with unity gain&nbsp;1. This multiplier
	 * is scaled linearly.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#MonoAudioGain mono audio gain}</li>
	 * </ul> 
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID Amplitude = new tv.amwa.maj.record.impl.AUIDImpl(
			0xe4962321, (short) 0x2267, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/**
	 * <p>Used to specify the pan value for a left-to-right pan position of mono audio essence on the
	 * output of a {@linkplain OperationConstant#MonoAudioPan mono audio pan}. This parameter is represented
	 * by a value of {@link tv.amwa.maj.record.Rational} type in the range 0&nbsp;to&nbsp;1 with 0 representing
	 * full left, 1/2 representing half left & half right and 1 representing full right.</p>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#MonoAudioPan mono audio pan}</li>
	 * </ul>  
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID Pan = new tv.amwa.maj.record.impl.AUIDImpl(
			0xe4962322, (short) 0x2267, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/**
	 * <p>Used to specify the outgoing level of a {@linkplain OperationConstant#TwoParameterMonoAudioDissolve
	 * two-parameter audio dissolve operation}. This parameter is a value of {@link tv.amwa.maj.record.Rational}
	 * type in the range 0&nbsp;to&nbsp;1. The outgoing level is scaled linearly.</p>
	 * 
	 * <p>The relationship between the {@linkplain #IncomingLevel incoming} and outgoing levels for any
	 * sample&nbsp;P with input samples&nbsp;A and&nbsp;B is:</p>
	 * 
	 * <center>P = (IncomingLevel * B) + (OutgoingLevel * A)</center>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#TwoParameterMonoAudioDissolve two-parameter mono audio dissolve}</li>
	 * </ul>   
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID OutgoingLevel = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9e610007, (short) 0x1be2, (short) 0x41e1, 
			new byte[] { (byte) 0xbb, 0x11, (byte) 0xc9, 0x5d, (byte) 0xe9, (byte) 0x96, 0x4d, 0x03 });

	/**
	 * <p>Used to specify the incoming level of a {@linkplain OperationConstant#TwoParameterMonoAudioDissolve
	 * two-parameter audio dissolve operation}. This parameter is a value of {@link tv.amwa.maj.record.Rational}
	 * type in the range 0&nbsp;to&nbsp;1. The incoming level is scaled linearly.</p>
	 * 
	 * <p>The relationship between the incoming and {@linkplain #OutgoingLevel outgoing} levels for any
	 * sample&nbsp;P with input samples&nbsp;A and&nbsp;B is:</p>
	 * 
	 * <center>P = (IncomingLevel * B) + (OutgoingLevel * A)</center>
	 * 
	 * <p>Parameter to operation:</p>
	 * 
	 * <ul>
	 *  <li>{@linkplain OperationConstant#TwoParameterMonoAudioDissolve two-parameter mono audio dissolve}</li>
	 * </ul>   
	 */
	@ParameterDescription(typeName = "Rational")
	public final static AUID IncomingLevel = new tv.amwa.maj.record.impl.AUIDImpl(
			0x48cea642, (short) 0xa8f9, (short) 0x455b, 
			new byte[] { (byte) 0x82, (byte) 0xb3, (byte) 0x86, (byte) 0xc8, 0x14, (byte) 0xb7, (byte) 0x97, (byte) 0xc7 });

	
	// TODO comments for all the following parameter constants
	
	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTESoft = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba1, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEBorder = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba2, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEPosition = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba3, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEModulator = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba4, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEShadow = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba5, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTETumble = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba6, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTESpotlight = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba7, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEReplicationH = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba8, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTEReplicationV = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894ba9, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "Boolean")
	public final static AUID SMPTECheckerboard = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9c894baa, (short) 0x2277, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	@ParameterDescription(typeName = "UInt32")
	public final static AUID PhaseOffset = new tv.amwa.maj.record.impl.AUIDImpl(
			0x5f1c2560, (short) 0x2415, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4f, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

}
