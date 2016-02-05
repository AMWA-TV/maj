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
 * $Log: WAVEDescriptorImpl.java,v $
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
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
 * Revision 1.1  2007/11/13 22:09:44  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.WAVEDescriptor;

/** 
 * <p>Implements the description of a file of audio essence formatted according to the RIFF 
 * Waveform Audio File Format (WAVE).</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2c00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "WAVEDescriptor",
		  description = "The WAVEDescriptor class specifies that a File SourcePackage is associated with audio essence formatted according to the RIFF Waveform Audio File Format (WAVE).",
		  symbol = "WAVEDescriptor")
public class WAVEDescriptorImpl
	extends 
		AAFFileDescriptorImpl
	implements 
		WAVEDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 3439854186002522302L;

	private byte[] waveSummary;
	
	public WAVEDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new WAVE descriptor, which specifies that a file 
	 * {@link SourcePackageImpl source package} is associated with audio essence formatted according 
	 * to the RIFF Waveform Audio File Format (WAVE).</p>
	 *
	 * @param containerFormat Container mechanism used to store the essence.
	 * @param summary A copy of the WAVE file information without the sample data.
	 * 
	 * @throws NullPointerException One or both of the arguments is/are <code>null</code> 
	 * and both are required.
	 */
	public WAVEDescriptorImpl(
			ContainerDefinition containerFormat,
			@DataBuffer byte[] summary)
		throws NullPointerException {
		
		if (summary == null)
			throw new NullPointerException("Cannot create a new WAVE descriptor with a null summary value");

		setContainerFormat(containerFormat);
		setWAVESummary(summary);
	}

	@MediaProperty(uuid1 = 0x03030302, uuid2 = (short) 0x0100, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "WAVESummary",
			aliases = { "Summary", "WAVEDescriptorSummary" },
			typeName = "DataValue", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3801,
			symbol = "WAVESummary")
	public byte[] getWAVESummary() {

		return waveSummary.clone();
	}

	@MediaPropertySetter("WAVESummary")
	public void setWAVESummary(
			byte[] waveSummary)
		throws NullPointerException {

		this.waveSummary = waveSummary.clone();
	}

	public final static byte[] initializeWAVESummary() {
		
		return new byte[0];
	}
	
	public int getWAVESummaryBufferSize() {

		return waveSummary.length;
	}

	public WAVEDescriptor clone() {
		
		return (WAVEDescriptor) super.clone();
	}
}
