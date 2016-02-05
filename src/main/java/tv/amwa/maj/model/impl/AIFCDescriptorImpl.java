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
 * $Log: AIFCDescriptorImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:09:04  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.DataValue;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.model.AIFCDescriptor;


/** 
 * <p>Implements the description of a file of audio essence formatted according to the Audio Interchange 
 * File Format with Compression (AIFC).</p>
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2600,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "AIFCDescriptor",
		  description = "The AIFCDescriptor class specifies that a File SourcePackage is associated with audio essence formatted according to the Audio Interchange File Format with Compression (AIFC).",
		  symbol = "AIFCDescriptor")
public class AIFCDescriptorImpl
	extends AAFFileDescriptorImpl
	implements AIFCDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -2028438976071340542L;

	private byte[] aifcSummary = null;
	
	public  AIFCDescriptorImpl() { }

	/**
	 * <p>Creates and initializes a new AIFC descriptor with the required summary data.</p>
	 *
	 * @param containerFormat Container mechanism used to store the essence.
	 * @param sampleRate Sample rate of the essence.
	 * @param length Duration of the essence measured in sample units.
	 * @param summary Copy of the descriptive information in the associated AIFC audio data value.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws BadLengthException The given length of the described material is negative.
	 */
	public AIFCDescriptorImpl(
			tv.amwa.maj.model.ContainerDefinition containerFormat,
			tv.amwa.maj.record.Rational sampleRate,
			@LengthType long length,
			@DataValue byte[] summary)
		throws NullPointerException,
			BadLengthException {
		
		if (sampleRate == null)
			throw new NullPointerException("Cannot create a new AIFC file descriptor using a null sample rate value.");
		if (summary == null)
			throw new NullPointerException("Cannot create a new AIFC file descriptor using a null summary value.");
		
		setContainerFormat(containerFormat);
		setDescribesTimeVaryingEssence(sampleRate, length);
		setAIFCSummary(summary);
	}
	
	@MediaProperty(uuid1 = 0x03030302, uuid2 = (short) 0x0200, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "AIFCSummary",
			aliases = { "Summary", "AIFCDescriptorSummary" },
			typeName = "DataValue", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x3101,
			symbol = "AIFCSummary")
	public byte[] getAIFCSummary() {
		
		return aifcSummary.clone();
	}

	public int getSummaryBufferSize() {

		return aifcSummary.length;
	}
	
	@MediaPropertySetter("AIFCSummary")
	public void setAIFCSummary(
			byte[] aifcSummary)
		throws NullPointerException {

		if (aifcSummary == null)
			throw new NullPointerException("Cannot set the summary of an AIFC file descriptor using a null value.");
		
		this.aifcSummary = aifcSummary.clone();
		// TODO consider reading the summary and extracting sample rate and length?
	}

	public final static byte[] initializeAIFCSummary() {
		
		return new byte[0];
	}
	
	public AIFCDescriptor clone() {
		
		return (AIFCDescriptor) super.clone();
	}
}
