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
 * $Log: DataDefinitionConstant.java,v $
 * Revision 1.5  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/12/12 12:29:55  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access unique identifiers for data definitions that specify the
 * kind of data that can be stored in a {@linkplain tv.amwa.maj.model.Component component}.
 * Additional information about each data definition appears in the {@linkplain DataDefinitionDescription
 * data definition description} that annotates each {@linkplain tv.amwa.maj.record.AUID} constant.</p> 
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported data definitions.</p>
 * 
 * @see tv.amwa.maj.constant.DataDefinitionDescription
 * @see tv.amwa.maj.model.DataDefinition
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionStrongReferenceSet
 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceSet
 * 
 *
 *
 */
public interface DataDefinitionConstant {

	/** 
	 * <p>Specifies a stream of essence that contains image data.</p>
	 */
	@DataDefinitionDescription(description = "Specifies a stream of essence that contains image data.")
	public final static AUID Picture = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030202, (short) 0x0100, (short) 0x0000, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/** 
	 * <p>Specifies a stream of essence that contains image data.</p> 
	 * 
	 * @deprecated Use {@link #Picture} instead.
	 */
	@DataDefinitionDescription(description = "Specifies a stream of essence that contains image data.",
			aliases = { "DDEF_Picture" })
	@Deprecated
	public final static AUID LegacyPicture = new tv.amwa.maj.record.impl.AUIDImpl(
			0x6f3c8ce1, (short) 0x6cef, (short) 0x11d2, 
			new byte[] { (byte) 0x80, 0x7d, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/** 
	 * <p>Specifies a stream of essence that contains an image of alpha values.</p> 
	 */
	@DataDefinitionDescription(description = "Specifies a stream of essence that contains an image of alpha values.",
			aliases = { "DDEF_Matte" })
	public final static AUID Matte = new tv.amwa.maj.record.impl.AUIDImpl(
			0x05cba731, (short) 0x1daa, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xad, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/** 
	 * <p>Specifies a stream of essence that contains image data and a matte.</p>
	 */
	@DataDefinitionDescription(description = "Specifies a stream of essence that contains image data and a matte.",
			aliases = { "DDEF_PictureWithMatte" })
	public final static AUID PictureWithMatte = new tv.amwa.maj.record.impl.AUIDImpl(
			0x05cba732, (short) 0x1daa, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xad, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });
	
	/** 
	 * <p>Specifies a stream of essence that contains a single channel of sound.</p>
	 */
	@DataDefinitionDescription(description = "Specifies a stream of essence that contains a single channel of sound.")
	public final static AUID Sound = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030202, (short) 0x0200, (short) 0x0000, 
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01});
	
	/** 
	 * <p>Specifies a stream of essence that contains a single channel of sound.</p>
	 * 
	 * @deprecated Use {@link #Sound} instead.
	 */
	@DataDefinitionDescription(description = "Specifies a stream of essence that contains a single channel of sound.",
			aliases = { "DDEF_Sound" })
	@Deprecated
	public final static AUID LegacySound = new tv.amwa.maj.record.impl.AUIDImpl(
			0x78e1ebe1, (short) 0x6cef, (short) 0x11d2, 
			new byte[] { (byte) 0x80, 0x7d, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f});
	
	/** 
	 * <p>Specifies a stream of tape timecode values.</p> 
	 */
	@DataDefinitionDescription(description = "Specifies a stream of tape timecode values.",
			aliases = { "DDEF_Timecode" })
	public final static AUID Timecode = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030201, (short) 0x0100, (short) 0x0000, 
			new byte[] {0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01});


	/** 
	 * <p>Specifies a stream of tape timecode values.</p>
	 * 
	 *  @deprecated Use {@link #Timecode} instead.
	 */
	@DataDefinitionDescription(description = "Specifies a stream of tape timecode values.")
	@Deprecated
	public final static AUID LegacyTimecode = new tv.amwa.maj.record.impl.AUIDImpl(
			0x7f275e81, (short) 0x77e5, (short) 0x11d2, 
			new byte[] { (byte) 0x80, 0x7f, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });
	
	/** 
	 * <p>Specifies a stream of film edge code values.</p>
	 */
	@DataDefinitionDescription(description = "Specifies a stream of film edge code values.",
			aliases = { "DDEF_Edgecode" })
	public final static AUID Edgecode = new tv.amwa.maj.record.impl.AUIDImpl(
			0xd2bb2af0, (short) 0xd234, (short) 0x11d2, 
			new byte[] { (byte) 0x89, (byte) 0xee, 0x00, 0x60, (byte) 0x97, 0x11, 0x62, 0x12 });

	/** 
	 * <p>Specifies descriptive metadata.</p>
	 */
	@DataDefinitionDescription(description = "Specifies descriptive metadata.")
	public final static AUID DescriptiveMetadata = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030201, (short) 0x1000, (short) 0x0000, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/** 
	 * <p>Specifies auxiliary data.</p> 
	 */
	@DataDefinitionDescription(description = "Specifies auxiliary data.")
	public final static AUID Auxiliary = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030203, (short) 0x0100, (short) 0x0000, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x05});

	/** 
	 * <p>Specifies data of an unknown kind.</p>
	 */
	@DataDefinitionDescription(description = "Specifies data of an unknown kind.")
	public final static AUID Unknown = new tv.amwa.maj.record.impl.AUIDImpl(
			0x851419d0, (short) 0x2e4f, (short) 0x11d3, 
			new byte[] { (byte) 0x8a, 0x5b, 0x00, 0x50, 0x04, 0x0e, (byte) 0xf7, (byte) 0xd2 });
	
	/**
	 * <p>Specifies data essence.</p>
	 */
	@DataDefinitionDescription(description = "Specifies data essence.")
	public final static AUID DataEssence = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030202, (short) 0x0300, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
	
	/**
	 * <p>Specifies parsed text essence. (Trial definition.)</p>
	 */	
	@DataDefinitionDescription(description = "Specifies parsed text essence.")	
	public final static AUID ParsedText = new tv.amwa.maj.record.impl.AUIDImpl(
			0x01030203, (short) 0x0200, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x07 });
}
