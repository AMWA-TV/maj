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
 * $Log: OperationConstant.java,v $
 * Revision 1.7  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/08 11:29:31  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:42  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;
 
import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access unique identifiers for operations, such as the video and audio
 * effects defined by the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF edit protocol</a>. 
 * Additional information about each operation appears in the {@linkplain OperationDescription
 * operation description} that annotates each {@linkplain tv.amwa.maj.record.AUID} constant.</p> 
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported operations.</p>
 * 
 * @see tv.amwa.maj.constant.OperationDescription
 * @see tv.amwa.maj.model.OperationDefinition
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#OperationDefinitionWeakReferenceVector
 * 
 *
 *
 */
public interface OperationConstant {

	/** 
	 * <p>A dissolve between overlapping video clips shall be specified using the video dissolve effect.</p>
	 * 
	 * <p>The video dissolve effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#Level Level} - optional; default is a {@linkplain tv.amwa.maj.model.VaryingValue 
	 *  varying value} object with two {@linkplain tv.amwa.maj.model.ControlPoint control points}: 
	 *  value&nbsp;0 at time&nbsp;0, and value&nbsp;1 at time&nbsp;1.</li>
	 * </ul>
	 * 
	 */
	@OperationDescription(description = "Video Dissolve",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 2,
			parametersDefined = { "Level" },
			aliases = { "Video Dissolve" } )
	public final static AUID VideoDissolve = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0c3bea40, (short) 0xfc05, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x29, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** 
	 * <p>Video wipes are simple two-source visual effects as defined by SMPTE&nbsp;258M.</p>
	 * 
	 * <p>Video wipe effects that are defined within SMPTE&nbsp;258M shall be specified using the 
	 * SMPTE Video Wipe effect.</p>
	 * 
	 * <p>The SMPTE Video Wipe effect shall only be used by an 
	 * {@linkplain tv.amwa.maj.model.OperationGroup operation group} within a 
	 * {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</o>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#SMPTEWipeNumber SMPTEWipeNumber} - required; SMPTE Wipe Number as defined 
	 *  in section&nbsp;7.6.33 of SMPTE&nbsp;258M.</li>
	 *  <li>{@link ParameterConstant#SMPTEReverse SMPTEReverse} - optional, default is <code>false</code>.</li>
	 *  <li>{@link ParameterConstant#Level Level} - optional; default is a {@linkplain tv.amwa.maj.model.VaryingValue 
	 *  varying value} object with two {@linkplain tv.amwa.maj.model.ControlPoint control points}: 
	 *  value&nbsp;0 at time&nbsp;0, and value&nbsp;1 at time&nbsp;1.</li>
	 * </ul>
	 */
	@OperationDescription(description = "SMPTE Video Wipe",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 2,
			parametersDefined = { "SMPTEWipeNumber", "SMPTEReverse", "Level"},
			aliases = { "SMPTE Video Wipe" })
	public final static AUID SMPTEVideoWipe = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0c3bea44, (short) 0xfc05, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x29, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** 
	 * <p>A video speed control effect, describing alterations to the playback speed of a clip 
	 * including forwards and backwards directions, shall be specified using a video speed control 
	 * effect.</p>
	 * 
	 * <p>The video speed control effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#SpeedRatio SpeedRatio} - required; defines the ratio of output length to input
	 *  length.</li>
	 * </ul>
	 * 
	 */
	@OperationDescription(description = "Video Speed Control",
			dataDefinition = "Picture",
			isTimeWarp = true,
			numberInputs = 1,
			bypass = 1,
			parametersDefined = { "SpeedRatio" },
			aliases = { "Video Speed Control" })
	public final static AUID VideoSpeedControl = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9d2ea890, (short) 0x0968, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x38, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** 
	 * <p>A video frame repeat effect, describing a clip that has a single essence frame as their 
	 * original source, shall be specified using a video repeat effect.</p>
	 * 
	 * <p>The video repeat effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>This operation has no parameters.</p> 
	 */
	@OperationDescription(description = "Video Repeat",
			dataDefinition = "Picture",
			isTimeWarp = true,
			numberInputs = 1,
			bypass = 1,
			aliases = "Video Repeat")
	public final static AUID VideoRepeat = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9d2ea891, (short) 0x0968, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x38, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** 
	 * <p>A video flip effect changes the vertical orientation of the image.</p>
	 * 
	 * <p>The video flip effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>This operation has no parameters.</p> 
	 */
	@OperationDescription(description = "Video Flip",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			bypass = 1,
			aliases = "Video Flip")
	public final static AUID Flip = new tv.amwa.maj.record.impl.AUIDImpl(
			0xf1db0f32, (short) 0x8d64, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xdf, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/** 
	 * <p>A video flop effect changes the horizontal orientation of the image.</p>
	 * 
	 * <p>The video flop effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>This operation has no parameters.</p> 
	 */
	@OperationDescription(description = "Video Flop",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			bypass = 1,
			aliases = { "Video Flop" })
	public final static AUID Flop = new tv.amwa.maj.record.impl.AUIDImpl(
			0xf1db0f34, (short) 0x8d64, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xdf, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });


	/** 
	 * <p>A video flip-flop effect changes the vertical and horizontal orientation of the image, 
	 * equivalent to a 180&nbsp;degree rotation of the image.</p>
	 * 
	 * <p>The video flip-flop effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>This operation has no parameters.</p> 
	 */
	@OperationDescription(description = "Video Flip Flop",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			bypass = 1,
			aliases = { "Video Flip Flop" } )
	public final static AUID FlipFlop = new tv.amwa.maj.record.impl.AUIDImpl(
			0xf1db0f33, (short) 0x8d64, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xdf, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/** 
	 * <p>An image move shall be specified using the video position effect.</p>
	 * 
	 * <p>The video position effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#PositionOffsetX PositionOffsetX} - optional; default value&nbsp;0.</li>
	 *  <li>{@link ParameterConstant#PositionOffsetY PositionOffsetY} - optional; default value&nbsp;0.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Video Position",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			parametersDefined = { "PositionOffsetX", "PositionOffsetY" },
			aliases = "Video Position")
	public final static AUID VideoPosition = new tv.amwa.maj.record.impl.AUIDImpl(
			0x86f5711e, (short) 0xee72, (short) 0x450c, 
			new byte[] { (byte) 0xa1, 0x18, 0x17, (byte) 0xcf, 0x3b, 0x17, 0x5d, (byte) 0xff });


	/** 
	 * <p>Crop operation to remove unwanted image information.</p>
	 * 
	 * <p>The video crop effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Crop parameters are specified for the left, right, top and bottom. These values are 
	 * expressed as a fraction of the original image coordinate space. The operation parameters are:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#CropLeft CropLeft} - optional; default is -1.</li>
	 *  <li>{@link ParameterConstant#CropRight CropRight} - optional; default is 1.</li>
	 *  <li>{@link ParameterConstant#CropTop CropTop} - optional; default is -1.</li>
	 *  <li>{@link ParameterConstant#CropBottom CropBottom} - optional; default is 1.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Video Crop",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			parametersDefined = { "CropLeft", "CropRight", "CropTop", "CropBottom" },
			aliases = { "Video Crop" })
	public final static AUID VideoCrop = new tv.amwa.maj.record.impl.AUIDImpl(
			0xf5826680, (short) 0x26c5, (short) 0x4149, 
			new byte[] { (byte) 0x85, 0x54, 0x43, (byte) 0xd3, (byte) 0xc7, (byte) 0xa3, (byte) 0xbc, 0x09 });


	/** 
	 * <p>Image scaling shall be specified using the video scale effect.</p>
	 * 
	 * <p>The video scale effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>The required width and height of the image can be represented as fractions of 
	 * the original image width and height with the result centered about 0,0. The parameters of the operation
	 * are:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#ScaleX ScaleX} - optional; default is 1.</li>
	 *  <li>{@link ParameterConstant#ScaleY ScaleY} - optional; default is 1.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Video Scale",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			parametersDefined = { "ScaleX", "ScaleY" },
			aliases = { "Video Scale" } )
	public final static AUID VideoScale = new tv.amwa.maj.record.impl.AUIDImpl(
			0x2e0a119d, (short) 0xe6f7, (short) 0x4bee, 
			new byte[] { (byte) 0xb5, (byte) 0xdc, 0x6d, (byte) 0xd4, 0x29, (byte) 0x88, 0x68, 0x7e });


	/** 
	 * <p>Image rotation shall be specified using the video rotate effect.</p>
	 * 
	 * <p>The video rotate effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>The rotation of the image about its center is represented by a single parameter scaled 
	 * 0&nbsp;to&nbsp;1 for one complete clockwise rotation. The parameter of the operation is:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#Rotation Rotation} - required.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Video Rotate",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			parametersDefined = { "Rotation" },
			aliases = { "Video Rotate" })
	public final static AUID VideoRotate = new tv.amwa.maj.record.impl.AUIDImpl(
			0xf2ca330d, (short) 0x8d45, (short) 0x4db4, 
			new byte[] { (byte) 0xb1, (byte) 0xb5, 0x13, 0x6a, (byte) 0xb0, 0x55, 0x58, 0x6f });


	/** 
	 * <p>Image corner pinning shall be specified using the video corner pinning effect.</p>
	 * 
	 * <p>The video corner pinning effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Image corner pinning uses four pairs of parameters which represent where the corners of the 
	 * original image have moved to as a fraction of the original image size. These parameters are:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#PinTopLeftX PinTopLeftX} - optional; default is -1.</li>
	 *  <li>{@link ParameterConstant#PinTopLeftY PinTopLeftY} - optional; default is -1.</li>
	 *  <li>{@link ParameterConstant#PinTopRightX PinTopRightX} - optional; default is 1.</li>
	 *  <li>{@link ParameterConstant#PinTopRightY PinTopRightY} - optional; default is -1.</li>
	 *  <li>{@link ParameterConstant#PinBottomLeftX PinBottomLeftX} - optional; default is -1.</li>
	 *  <li>{@link ParameterConstant#PinBottomLeftY PinBottomLeftY} - optional; default is 1.</li>
	 *  <li>{@link ParameterConstant#PinBottomRightX PinBottomRightX} - optional; default is 1.</li>
	 *  <li>{@link ParameterConstant#PinBottomRightY PinBottomRightY} - optional; default is 1.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Video Corner Pinning",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1,
			parametersDefined = { "PinTopLeftX", "PinTopLeftY", "PinTopRightX", "PinTopRightY",
									"PinBottomLeftX", "PinBottomLeftY", "PinBottomRightX", "PinBottomRightY" },
			aliases = { "Video Corner Pinning" } )
	public final static AUID VideoCornerPinning = new tv.amwa.maj.record.impl.AUIDImpl(
			0x21d5c51a, (short) 0x8acb, (short) 0x46d5, 
			new byte[] { (byte) 0x93, (byte) 0x92, 0x5c, (byte) 0xae, 0x64, 0x0c, (byte) 0x88, 0x36 });


	/** 
	 * <p>An alpha key effect 
	 * in which the alpha channel is within the foreground video essence shall be specified using the 
	 * alpha with video key effect. Alpha transparency provides a mechanism for titles and travelling mattes.</p>
	 * 
	 * <p>The alpha channel shall be contained within a channel of the foreground video essence, using 
	 * either RGBA or YUVA. The essence descriptor shall be an {@linkplain tv.amwa.maj.model.RGBADescriptor 
	 * RGBA descriptor} object (or a sub-class) for RGBA, or a {@linkplain tv.amwa.maj.model.CDCIDescriptor
	 * CDCI descriptor} object (or a sub-class) for Color Difference Component Images. The alpha channel shall be 
	 * indicated by the essence descriptor.</p>
	 * 
	 * <p>This alpha with video key effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>The {@linkplain tv.amwa.maj.model.PictureDescriptor#getAlphaTransparency() digital image descriptor alpha transparency
	 * property} shall be present and specify whether the minimum alpha value or the maximum alpha value 
	 * indicates complete transparency. The 
	 * "{@linkplain ParameterConstant#AlphaKeyInvertAlpha alpha key invert alpha parameter}" of this 
	 * operation indicates whether the transparency indicated by the 
	 * alpha transparency property should be inverted. This parameter is defined as:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#AlphaKeyInvertAlpha AlphaKeyInvertAlpha} - optional; default <code>false</code>.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Alpha With Video Key effect",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 2,
			bypass = 1,
			parametersDefined = { "AlphaKeyInvertAlpha" },
			aliases = { "Alpha With Video Key effect" })
	public final static AUID VideoAlphaWithinVideoKey = new tv.amwa.maj.record.impl.AUIDImpl(
			0x14db900e, (short) 0xd537, (short) 0x49f6, 
			new byte[] { (byte) 0x88, (byte) 0x9b, 0x01, 0x25, 0x68, (byte) 0xfc, (byte) 0xc2, 0x34 });


	/** 
	 * <p>An alpha key effect in 
	 * which the alpha channel essence is separate from the video foreground and background essence shall be 
	 * specified using the separate-alpha key effect. Alpha Transparency provides a mechanism for titles 
	 * and travelling mattes.</p>
	 * 
	 * <p>The alpha channel shall be contained within a channel of separate essence, using either RGBA or 
	 * YUVA. The essence descriptor shall be a {@linkplain tv.amwa.maj.model.RGBADescriptor RGBA descriptor} 
	 * object (or a sub-class) for RGBA, or a {@linkplain tv.amwa.maj.model.CDCIDescriptor CDCI descriptor} object 
	 * (or a sub-class) for Color Difference Component Images. Where the alpha channel is indicated by 
	 * the essence descriptor, that channel shall be used as the alpha channel. Where the alpha channel is 
	 * not indicated by the essence descriptor, the luminance value of the essence shall be used as the 
	 * alpha channel.</p>
	 * 
	 * <p>The separate-alpha key effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>The {@linkplain tv.amwa.maj.model.PictureDescriptor#getAlphaTransparency() digital image descriptor alpha transparency
	 * property} shall be present and specify whether the minimum alpha value or the maximum alpha value 
	 * indicates complete transparency. The 
	 * "{@linkplain ParameterConstant#AlphaKeyInvertAlpha alpha key invert alpha parameter}" of this 
	 * operation indicates whether the transparency indicated by the 
	 * alpha transparency property should be inverted. This parameter is defined as:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#AlphaKeyInvertAlpha AlphaKeyInvertAlpha} - optional; default <code>false</code>.</li>
	 * </ul>
	 */
	// TODO check whether the number of inputs is 2 or 3 ... section 12.14.1 is not clear
	@OperationDescription(description = "Separate Alpha Key effect",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 3,
			bypass = 1,
			parametersDefined = { "AlphaKeyInvertAlpha" },
			aliases = { "Separate Alpha Key effect" } )
	public final static AUID VideoSeparateAlphaKey = new tv.amwa.maj.record.impl.AUIDImpl(
			0xe599cb0f, (short) 0xba5f, (short) 0x4192, 
			new byte[] { (byte) 0x93, 0x56, 0x51, (byte) 0xeb, 0x19, (byte) 0xc0, (byte) 0x85, (byte) 0x89 });


	/** 
	 * <p>A luminance key effect where the luminance is determined from the Y or the RGB channels.</p>
	 * 
	 * <p>The luminance key effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#LumKeyLevel LumKeyLevel} - required.</li>
	 *  <li>{@link ParameterConstant#LumKeyClip LumKeyClip} - required.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Luminance Key",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 2,
			bypass = 1,
			parametersDefined = { "LumKeyLevel", "LumKeyClip" },
			aliases = { "Luminance Key" })
	public final static AUID VideoLuminanceKey = new tv.amwa.maj.record.impl.AUIDImpl(
			0x38ff7903, (short) 0x69e5, (short) 0x476b, 
			new byte[] { (byte) 0xbe, 0x5a, (byte) 0xea, (byte) 0xfc, 0x20, 0x00, (byte) 0xf0, 0x11 });


	/** 
	 * <p>A chroma key effect shall be specified using the chroma key effect.</p>
	 * 
	 * <p>The chroma key effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Note that this operation has no specified parameters. However, an exporting applicaiton may define
	 * and use its own parameters.</p> 
	 */
	@OperationDescription(description = "Chroma Key",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 2,
			bypass = 1,
			aliases = { "Chroma Key" })
	public final static AUID VideoChromaKey = new tv.amwa.maj.record.impl.AUIDImpl(
			0x30a315c2, (short) 0x71e5, (short) 0x4e82, 
			new byte[] { (byte) 0xa4, (byte) 0xef, 0x05, 0x13, (byte) 0xee, 0x05, 0x6b, 0x65 });


	/** 
	 * <p>An audio gain effect, specifying how the level of an audio signal is amplified or attenuated 
	 * over time, shall be specified using the mono audio gain effect.</p>
	 * 
	 * <p>The mono audio gain effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#Amplitude Amplitude} - required.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Mono Audio Gain",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 1,
			bypass = 1,
			parametersDefined = { "Amplitude" },
			aliases = "Mono Audio Gain")
	public final static AUID MonoAudioGain = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9d2ea894, (short) 0x0968, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x38, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>An audio pan effect, specifying the left/right balance of mono audio sources in a stereo sound 
	 * field, shall be specified using the mono audio pan effect.</p>
	 * 
	 * <p>A mono audio pan effect shall only be applied to audio tracks that refer to mono essence.</p>
	 * 
	 * <p>Audio pan effects shall not be nested. The top-level pan value shall be applied.</p>
	 * 
	 * <p>The mono audio pan effect shall only be used by an {@linkplain tv.amwa.maj.model.OperationGroup 
	 * operation group} not contained within a {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#Pan Pan} - required.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Mono Audio Pan",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 1,
			bypass = 1,
			parametersDefined = { "Pan" },
			aliases = { "Mono Audio Pan" })
	public final static AUID MonoAudioPan = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9d2ea893, (short) 0x0968, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x38, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>A dissolve between overlapping audio clips, in which the fade out of the outgoing "A" segment 
	 * is the mirror image in time of the fade in of the incoming "B" segment, shall be specified using 
	 * the single-parameter mono audio dissolve effect.<p>
	 * 
	 * <p>The single-parameter mono audio dissolve effect shall only be used by an 
	 * {@linkplain tv.amwa.maj.model.OperationGroup operation group} within a 
	 * {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#Level Level} - optional; default is a {@linkplain tv.amwa.maj.model.VaryingValue 
	 *  varying value} object with two {@linkplain tv.amwa.maj.model.ControlPoint control points}: 
	 *  value&nbsp;0 at time&nbsp;0, and value&nbsp;1 at time&nbsp;1. The level is scaled linearly.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Single-Parameter Mono Audio Dissolve",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 2,
			parametersDefined = { "Level" },
			aliases = { "Mono Audio Dissolve" })
	public final static AUID MonoAudioDissolve = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0c3bea41, (short) 0xfc05, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x29, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>A dissolve between overlapping audio clips, in which the levels of the incoming 'B' segment
	 * and outgoing 'A' segment are independently defined, shall be specified using the two-parameter mono
	 * audio dissolve effect.</p>
	 * 
	 * <p>Note that two-parameter audio dissolve effects are commonly used by digital audio workstations.</p>
	 * 
	 * <p>The two-parameter mono audio dissolve effect shall only be used by an 
	 * {@linkplain tv.amwa.maj.model.OperationGroup operation group} within a 
	 * {@linkplain tv.amwa.maj.model.Transition transition} object.</p>
	 * 
	 * <p>Operation parameters:</p>
	 * 
	 * <ul>
	 *  <li>{@link ParameterConstant#OutgoingLevel OutgoingLevel} - optional; default is a {@linkplain tv.amwa.maj.model.VaryingValue 
	 *  varying value} object with two {@linkplain tv.amwa.maj.model.ControlPoint control points}: 
	 *  value&nbsp;0 at time&nbsp;0, and value&nbsp;1 at time&nbsp;1.</li>
	 *  <li>{@link ParameterConstant#IncomingLevel IncomingLevel} - optional; default is a {@linkplain tv.amwa.maj.model.VaryingValue 
	 *  varying value} object with two {@linkplain tv.amwa.maj.model.ControlPoint control points}: 
	 *  value&nbsp;0 at time&nbsp;0, and value&nbsp;1 at time&nbsp;1.</li>
	 * </ul>
	 */
	@OperationDescription(description = "Two-Parameter Mono Audio Dissolve",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 2,
			parametersDefined = { "OutgoingLevel", "IncomingLevel" },
			aliases = { "Two-Parameter Mono Audio Dissolve" })
	public final static AUID TwoParameterMonoAudioDissolve = new tv.amwa.maj.record.impl.AUIDImpl(
			0x2311bd90, (short) 0xb5da, (short) 0x4285, 
			new byte[] { (byte) 0xaa, 0x3a, (byte) 0x85, 0x52, (byte) 0x84, (byte) 0x87, 0x79, (byte) 0xb3 });


	/** 
	 * <p>Specifies an unknown operation.</p>
	 */
	@OperationDescription(description = "Unknown operation",
			dataDefinition = "Unknown",
			isTimeWarp = false,
			numberInputs = 0)
	public final static AUID Unknown = new tv.amwa.maj.record.impl.AUIDImpl(
			0x1575e350, (short) 0xfca3, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x2a, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** 
	 * <p>Legacy operation used to fade video to black.</p>
	 * 
	 * @deprecated Use effects defined in the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF
	 * edit protocol</a> instead.
	 */
	@OperationDescription(description = "Video Fade To Black",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1)
	@Deprecated
	public final static AUID VideoFadeToBlack = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0c3bea43, (short) 0xfc05, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x29, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>Legacy picture with matte operation.</p>
	 * 
	 * @deprecated Use effects defined in the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF
	 * edit protocol</a> instead.
	 */
	@OperationDescription(description = "Picture With Matte",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1)
	@Deprecated
	public final static AUID PictureWithMate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0a3c75e0, (short) 0xfd82, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x2b, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });

	/** 
	 * <p>Legacy operation used to convert a video frame to a mask.</p>
	 * 
	 * @deprecated Use effects defined in the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF
	 * edit protocol</a> instead.
	 */
	@OperationDescription(description = "Video Frame To Mask",
			dataDefinition = "Picture",
			isTimeWarp = false,
			numberInputs = 1)
	@Deprecated
	public final static AUID VideoFrameToMask = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9d2ea892, (short) 0x0968, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x38, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>Legacy stereo audio dissolve operation.</p>
	 * 
	 * @deprecated Use effects defined in the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF
	 * edit protocol</a> instead.
	 */
	@OperationDescription(description = "Stereo Audio Dissolve",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 1)
	@Deprecated
	public final static AUID StereoAudioDissolve = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0c3bea42, (short) 0xfc05, (short) 0x11d2, 
			new byte[] { (byte) 0x8a, 0x29, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>Legacy stereo audio gain operation.</p>
	 * 
	 * @deprecated Use effects defined in the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF
	 * edit protocol</a> instead.
	 */
	@OperationDescription(description = "Stereo Audio Gain",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 1)
	@Deprecated
	public final static AUID StereoAudioGain = new tv.amwa.maj.record.impl.AUIDImpl(
			0x9d2ea895, (short) 0x0968, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x38, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });


	/** 
	 * <p>Legacy mono audio mix down operation.</p>
	 * 
	 * @deprecated Use effects defined in the <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF
	 * edit protocol</a> instead.
	 */
	@OperationDescription(description = "Mono Audio Mixdown",
			dataDefinition = "Sound",
			isTimeWarp = false,
			numberInputs = 1)
	@Deprecated
	public final static AUID MonoAudioMixdown = new tv.amwa.maj.record.impl.AUIDImpl(
			0x8d896ad0, (short) 0x2261, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x4c, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });
}
