/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: ContainerConstant.java,v $
 * Revision 1.6  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2007/12/12 12:29:55  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:36  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/** 
 * <p>Implement this interface to access unique identifiers for mechanisms used to store essence
 * in files. Many of these containers are specified by labels in the SMPTE labels registry 
 * (<a href="http://www.smpte-ra.org/mdd/RP224.6%20TP%2020041220.xls">SMPTE RP224.6</a>). 
 * {@linkplain tv.amwa.maj.model.AAFFileDescriptor File descriptors} use {@linkplain 
 * tv.amwa.maj.model.ContainerDefinition container definitions} to specify the how data
 * is contained within a file.</p>
 * 
 * <p>Many of the constants defined here represent ways that the material exchange format (MXF)
 * generic container, as defined in SMPTE&nbsp;379M, is used to represent essence according to 
 * different encodings of audio and/or video data. An MXF file consists of a sequence of content 
 * packages and data can be packed into these packages according to two main strategies:</p>
 * 
 * TODO fix this in the stylesheet
 * 
 * <dl> 
 *  <dt>frame wrapping</dt>
 *  <dd>Each content package is the duration of a single video frame, consisting of any
 *  picture, sound and data elements associated with the frame.</dd>
 *  <dt>clip wrapping</dt>
 *  <dd>Each content package contains all picture and sound data for an entire clip.</dd>
 * </dl>
 * 
 * <p>All the constants in this package are annotated with additional metadata using the 
 * {@linkplain ContainerDescription container description} annotation.</p>
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported containers.</p>
 * 
 * @see tv.amwa.maj.model.AAFFileDescriptor
 * @see tv.amwa.maj.model.ContainerDefinition
 * @see ContainerDescription
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#ContainerDefinitionStrongReferenceSet
 *
 *
 *
 */

public interface ContainerConstant {

	/**
	 * <p>Essence is contained externally in a location given by {@linkplain tv.amwa.maj.model.Locator locators}
	 * in the {@linkplain tv.amwa.maj.model.AAFFileDescriptor file descriptor}.</p> 
	 */
	@ContainerDescription(description="External Container")
	public final static AUID External = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4313b572, (short) 0xd8ba, (short) 0x11d2, 
			new byte[] { (byte) 0x80, (byte) 0x9b, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/**
	 * <p>An Open Media Framework file. The format is a platform-independent file format intended for 
	 * transfer of digital media between different software applications.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID OMF = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4b1c1a46, (short) 0x03f2, (short) 0x11d4, 
			new byte[] { (byte) 0x80, (byte) 0xfb, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/**
	 * <p>An Advanced Authoring Format file. This is is a professional file interchange format designed 
	 * for the video post production and authoring environments.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID AAF = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4313b571, (short) 0xd8ba, (short) 0x11d2, 
			new byte[] { (byte) 0x80, (byte) 0x9b, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/**
	 * <p>An Advanced Authoring Format file encoded using 
	 * <a href="http://www.aafassociation.org/html/specs/aafcontainerspec-v1.0.1.pdf">Microsoft's Structured
	 * Storage</a> format.</p>
	 * 
	 * @see #AAF
	 */
	@ContainerDescription(description="")
	public final static AUID AAFMSS = new tv.amwa.maj.record.impl.AUIDImpl(
			0x42464141, (short) 0x000d, (short) 0x4d4f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, (byte) 0xff });

	/**
	 * <p>An Advanced Authoring Format file stored using KLV data encoded as defined in SMPTE&336M. As KLV
	 * coding is used for the Material Exchange Format files and the MXF and AAF data models are compatible, 
	 * AAF KLV files are also MXF files.</p>
	 * 
	 * @see #AAF
	 */
	@ContainerDescription(description="")
	public final static AUID AAFKLV = new tv.amwa.maj.record.impl.AUIDImpl(
			0x4b464141, (short) 0x000d, (short) 0x4d4f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, (byte) 0xff });

	/**
	 * <p>An Advanced Authoring Format file stored using the so called Registered data XML format, an XML encoding
	 * of the classes of the AAF specification. This format is supported by the MAJ API using the 
	 * <a href="../io/xml/package-summary.html">IO XML package</a>.</p>
	 * 
	 * @see #AAF
	 */
	@ContainerDescription(description="")
	public final static AUID AAFXML = new tv.amwa.maj.record.impl.AUIDImpl(
			0x58464141, (short) 0x000d, (short) 0x4d4f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, (byte) 0xff });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 50&nbsp;Mbps, represented according to the template in SMPTE&nbsp;386M.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_DefinedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 50&nbsp;Mbps, represented according to an extended template.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_ExtendedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 50&nbsp;Mbps, containing only picture data.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_PictureOnly = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x017f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 50&nbsp;Mbps, represented according to the template in SMPTE&nbsp;386M.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_50Mbps_DefinedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0201, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 50&nbsp;Mbps, represented according to an extended template.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_50Mbps_ExtendedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0202, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 50&nbsp;Mbps, containing only picture data.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_50Mbps_PictureOnly = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x027f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 40&nbsp;Mbps, represented according to the template in SMPTE&nbsp;386M.
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_40Mbps_DefinedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0301, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 40&nbsp;Mbps, represented according to an extended template.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_40Mbps_ExtendedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0302, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 40&nbsp;Mbps, containing only picture data.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_40Mbps_PictureOnly = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x037f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 40&nbsp;Mbps, represented according to the template in SMPTE&nbsp;386M.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_40Mbps_DefinedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0401, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 40&nbsp;Mbps, represented according to an extended template.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_40Mbps_ExtendedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0402, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 40&nbsp;Mbps, containing only picture data.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_40Mbps_PictureOnly = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x047f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 30&nbsp;Mbps, represented according to the template in SMPTE&nbsp;386M.
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_30Mbps_DefinedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0501, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 30&nbsp;Mbps, represented according to an extended template.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_30Mbps_ExtendedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0502, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 625 lines, 50&nbsp;Hz and 
	 * streamed at 30&nbsp;Mbps, containing only picture data.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_625x50I_30Mbps_PictureOnly = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x057f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 30&nbsp;Mbps, represented according to the template in SMPTE&nbsp;386M.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_30Mbps_DefinedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0601, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 30&nbsp;Mbps, represented according to an extended template.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_30Mbps_ExtendedTemplate = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x0602, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for SMPTE-defined D-10 essence data defined at 525 lines, 59.94&nbsp;Hz and 
	 * streamed at 30&nbsp;Mbps, containing only picture data.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_SMPTE_D10_525x5994I_30Mbps_PictureOnly = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0201, (short) 0x067f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for a DV stream compliant with IEC 61834-2 for 525 line video at 59.94&nbsp;Hz,
	 * streamed at 25&nbsp;Mbps and wrapped into frame-length content packages.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_IECDV_525x5994I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with IEC 61834-2 for 525 line video at 59.94&nbsp;Hz,
	 * streamed at 25&nbsp;Mbps and wrapped by clips into content packages.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_IECDV_525x5994I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with IEC 61834-2 for 625 line video at 50&nbsp;Hz,
	 * streamed at 25&nbsp;Mbps and wrapped into frame-length content packages.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_IECDV_625x50I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0201, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with IEC 61834-2 for 625 line video at 50&nbsp;Hz,
	 * streamed at 25&nbsp;Mbps and wrapped by clips into content packages.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_IECDV_625x50I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0202, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with IEC 61834-2; appropriate for transport over
	 * a serial data transfer interface as defined in SMPTE&nbsp;322M; for 525 line video at 59.94&nbsp;Hz;
	 * streamed at 25&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_IECDV_525x5994I_25Mbps_SMPTE322M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0301, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with IEC 61834-2; appropriate for transport over
	 * a serial data transfer interface as defined in SMPTE&nbsp;322M; for 525 line video at 59.94&nbsp;Hz;
	 * streamed at 25&Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_IECDV_525x5994I_25Mbps_SMPTE322M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0302, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with IEC 61834-2; appropriate for transport over
	 * a serial data transfer interface as defined in SMPTE&nbsp;322M; for 625 line video at 50&nbsp;Hz;
	 * streamed at 25&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_IECDV_625x50I_25Mbps_SMPTE322M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0401, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with IEC 61834-2; appropriate for transport over
	 * a serial data transfer interface as defined in SMPTE&nbsp;322M; for 625 line video at60&nbsp;Hz;
	 * streamed at 25&nbsp;Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_IECDV_625x50I_25Mbps_SMPTE322M = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x0402, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with IEC 61834-2 from an undefined source,
	 * streamed at 25&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_IECDV_UndefinedSource_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x3f01, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with IEC 61834-2 from an undefined source,
	 * streamed at 25&nbsp;Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_IECDV_UndefinedSource_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x3f02, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 525 line 
	 * video at 59.94&nbsp;Hz, streamed at a rate of 25&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_525x5994I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x4001, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 525 line 
	 * video at 59.94&nbsp;Hz, streamed at a rate of 25&nbsp;Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_525x5994I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x4002, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 625 line 
	 * video at 50&nbsp;Hz, streamed at a rate of 25&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_625x50I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x4101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 625 line 
	 * video at 50&nbsp;Hz, streamed at a rate of 25&nbsp;Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_625x50I_25Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x4102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 525 line
	 * video at 59.94&nbsp;Hz, streamed at 50&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_525x5994I_50Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x5001, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M  for 525 line
	 * video at 59.94&nbsp;Hz, streamed at 50&nbsp;Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_525x5994I_50Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x5002, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 625 line
	 * video at 50&nbsp;Hz, streamed at 50&nbsp;Mbps and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_625x50I_50Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x5101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream compliant with SMPTE&nbsp;314M for 625 line
	 * video at 50&nbsp;Hz, streamed at 50&nbsp;Mbps and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_625x50I_50Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x5102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 1080 line 
	 * video at 59.94&nbsp;Hz; interlaced; streamed at a rate of 100&nbsp;Mbps; wrapped into frame 
	 * length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_1080x5994I_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6001, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 1080 line 
	 * video at 59.94&nbsp;Hz; interlaced; streamed at a rate of 100&nbsp;Mbps; wrapped 
	 * by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_1080x5994I_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6002, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 1080 line 
	 * video at 50&nbsp;Hz; interlaced; streamed at a rate of 100&nbsp;Mbps; wrapped into frame 
	 * length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_1080x50I_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; 1080 line 
	 * video at 50&nbsp;Hz; interlaced; streamed at a rate of 100&nbsp;Mbps and wrapped 
	 * by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_1080x50I_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 720 line
	 * video at 59.94&nbsp;Hz; progressive; streamed at a rate of 100&nbsp;Mbps; wrapped
	 * into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_720x5994P_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6201, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 720 line
	 * video at 59.94&nbsp;Hz; progressive; streamed at a rate of 100&nbsp;Mbps; wrapped
	 * by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_720x5994P_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6202, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 720 line
	 * video at 50&nbsp;Hz; progressive; streamed at a rate of 100&nbsp;Mbps; wrapped
	 * into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_720x50P_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6301, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream: compliant with SMPTE&nbsp;370M; for 720 line
	 * video at 50&nbsp;Hz; progressive; streamed at a rate of 100&nbsp;Mbps; wrapped
	 * by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_720x50P_100Mbps = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x6302, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream from an undefined source and wrapped into frame-length
	 * content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_DVbased_UndefinedSource = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x7f01, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for a DV stream from an undefined source and wrapped into by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_DVbased_UndefinedSource = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0202, (short) 0x7f02, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/** TODO
	 * <p>MXF generic container for ...</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_MPEGES_VideoStream0_SID = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0204, (short) 0x6001, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });


	/** TODO
	 * <p>MXF generic container for ... </p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_CustomClosedGOPwrapped_MPEGES_VideoStream1_SID = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0204, (short) 0x6107, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x02 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 525 video lines at 59.94&nbsp;Hz, with
	 * a 4:2:2 chroma pattern, interlaced, a width of 720 and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994I_720_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 525 video lines at 59.94&nbsp;Hz, with
	 * a 4:2:2 chroma pattern, interlaced, a width of 720 and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994I_720_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 525 video lines at 59.94&nbsp;Hz, with
	 * a 4:2:2 chroma pattern, interlaced, a width of 720 and wrapped into line-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994I_720_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0103, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 625 video lines at 50&nbsp;Hz, with
	 * a 4:2:2 chroma pattern, interlaced, a width of 720 and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_Uncompressed_625x50I_720_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0105, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 625 video lines at 50&nbsp;Hz, with
	 * a 4:2:2 chroma pattern, interlaced, a width of 720 and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50I_720_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0106, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 625 video lines at 50&nbsp;Hz, with
	 * a 4:2:2 chroma pattern, interlaced, a width of 720 and wrapped into line-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Linewrapped_Uncompressed_625x50I_720_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0107, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 525 line video at 59.94&nbsp;Hz, with a 
	 * 4:2:2 chroma pattern, progressive, a width of 960 and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_Uncompressed_525x5994P_960_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x0119, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 525 line video at 59.94&nbsp;Hz, with a 
	 * 4:2:2 chroma pattern, progressive, a width of 960 and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_Uncompressed_525x5994P_960_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x011a, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 525 line video at 59.94&nbsp;Hz, with a 
	 * 4:2:2 chroma pattern, progressive, a width of 960 and wrapped into line-by-line content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Linewrapped_Uncompressed_525x5994P_960_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x011b, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 625 line video at 50&nbsp;Hz, with a 
	 * 4:2:2 chroma pattern, progressive, a width of 960 and wrapped into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_Uncompressed_625x50P_960_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x011d, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 625 line video at 50&nbsp;Hz, with a 
	 * 4:2:2 chroma pattern, progressive, a width of 960 and wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_Uncompressed_625x50P_960_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x011e, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for uncompressed essence, consisting of 625 line video at 50&nbsp;Hz, with a 
	 * 4:2:2 chroma pattern, progressive, a width of 960 and wrapped into line-by-line content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Linewrapped_Uncompressed_625x50P_960_422 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0205, (short) 0x011f, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for broadcast wave audio data as specified by SMPTE&nbsp;382M and wrapped
	 * into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_Broadcast_Wave_audio_data = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0206, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for broadcast wave audio data as specified by SMPTE&nbsp;382M and wrapped
	 * by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_Broadcast_Wave_audio_data = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0206, (short) 0x0200, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for AES3 audio data as specified by SMPTE&nbsp;382M and wrapped
	 * into frame-length content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_AES3_audio_data = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0206, (short) 0x0300, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for AES3 audio data as specified by SMPTE&nbsp;382M and wrapped
	 * by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_AES3_audio_data = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0206, (short) 0x0400, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for audio data coded with the a-law 
	 * algorithm as specified in SMPTE&nbsp;388M, wrapped into frame-length content packages.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_Alaw_Audio = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x020a, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x03 });

	/**
	 * <p>MXF generic container for audio data coded with the a-law 
	 * algorithm as specified in SMPTE&nbsp;388M, wrapped by clips into content packages.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_Alaw_Audio = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x020a, (short) 0x0200, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x03 });

	/**
	 * <p>MXF generic container for audio data coded with the a-law 
	 * algorithm as specified in SMPTE&nbsp;388M, wrapped into content packages in a
	 * custom way.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Customwrapped_Alaw_Audio = new tv.amwa.maj.record.impl.AUIDImpl(
	0x0d010301, (short) 0x020a, (short) 0x0300, new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x03 });

	/**
	 * <p>MXF generic container for advanced video coding byte streams represented according to 
	 * SMPTE RP&nbsp;2008-2007 <em>Material Exchange Format&nbsp;- Mapping AVC Streams into the MXF 
	 * Generic Container</em>, wrapped by clips into content packages.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_AVCbytestream_VideoStream0_SID = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0210, (short) 0x6002, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x0a });

	/**
	 * <p>MXF generic container for Avid's DNxHD coded data which is undergoing standardisation in SMPTE and
	 * to become known as VC-3, wrapped into frame-length content packages. See the 
	 * <a href="http://en.wikipedia.org/wiki/DNxHD_codec">related description of the DNxHD codec
	 * on Wikipedia</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_VC3 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0211, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x0a });


	/**
	 * <p>MXF generic container for Avid's DNxHD coded data which is undergoing standardisation in SMPTE and
	 * to become known as VC-3, wrapped by clips into content packages. See the 
	 * <a href="http://en.wikipedia.org/wiki/DNxHD_codec">related description of the DNxHD codec
	 * on Wikipedia</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_VC3 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0211, (short) 0x0200, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x0a });


	/**
	 * <p>MXF generic container for VC-1 coded data, coded according to SMPTE&nbsp;421M and wrapped 
	 * into frame-length content packages. See the <a href="http://en.wikipedia.org/wiki/VC1">description of 
	 * VC-1 on Wikipedia</a>.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Framewrapped_VC1 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0212, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x0a });

	/**
	 * <p>MXF generic container for VC-1 coded data, coded according to SMPTE&nbsp;421M and wrapped 
	 * by clips into content packages. See the <a href="http://en.wikipedia.org/wiki/VC1">description of 
	 * VC-1 on Wikipedia</a>.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Clipwrapped_VC1 = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x0212, (short) 0x0200, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x0a });

	/**
	 * <p>MXF generic container for essence containing multiple different mappings to different
	 * types of stored data.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Generic_Essence_Multiple_Mappings = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d010301, (short) 0x027f, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x03 });

	/**
	 * <p>A resource interchange format file (RIFF) containing chunks of {@linkplain CodecConstant#WAVE WAVE} 
	 * audio data. See the <a href="http://en.wikipedia.org/wiki/RIFF">description of RIFF on Wikipedia</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID RIFFWAVE = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d011301, (short) 0x0101, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x06 });

	/**
	 * <p>The JPEG file interchange format, the container specified to hold {@linkplain CodecConstant#JPEG JPEG-coded}
	 * image data. See the <a href="http://en.wikipedia.org/wiki/JFIF">description of JFIF on Wikipedia</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID JFIF = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d011301, (short) 0x0102, (short) 0x0200, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x07 });

	/**
	 * <p>The audio interchange file format (AIFF) or its equivalent compressed variant known as 
	 * AIFC. AIFF is a beg-endian analogue of RIFF. See the <a href="http://en.wikipedia.org/wiki/AIFF">
	 * description of AIFF and AIFC on Wikipedia</a>.</p> 
	 */
	@ContainerDescription(description="")
	public final static AUID AIFFAIFC = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d011301, (short) 0x0104, (short) 0x0100, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x06 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 10-bit
	 * quantisation for 1080 line progressive video at 220&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_220X_1080p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0101, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 8-bit
	 * quantisation for 1080 line progressive video at 145&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_145_1080p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0102, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 8-bit
	 * quantisation for 1080 line progressive video at 220&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_220_1080p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0103, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNxHD&nbsp;36 file format for offline editing of film and 
	 * HD sources. See the <a href="http://www.avid.com/onlineSupport/supportcontent.asp?contentID=10672">
	 * description of DNxHD&nbsp;36 from AVID</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_36_1080p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0104, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 10-bit
	 * quantisation for 1080 line interlaced video at 220&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_220X_1080i = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0201, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 8-bit
	 * quantisation for 1080 line interlaced video at 145&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_145_1080i = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0202, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 8-bit
	 * quantisation for 1080 line interlaced video at 220&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_220_1080i = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0203, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 10-bit
	 * quantisation for 720 line progressive video at 220&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_220X_720p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0301, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 8-bit
	 * quantisation for 720 line progressive video at 220&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_220_720p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0302, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });

	/**
	 * <p>MXF generic container for Avid's DNX high-definition video encoding, using 8-bit
	 * quantisation for 720 line progressive video at 145&nbsp;Mbps. See the 
	 * <a href="http://www.avid.com/dnxhd/index.asp">description of DNxHD from Avid</a>.</p>
	 */
	@ContainerDescription(description="")
	public final static AUID MXFGC_Avid_DNX_145_720p = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0e040301, (short) 0x0206, (short) 0x0303, 
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x01 });
}
