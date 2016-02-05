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
 * $Log: AuxBitsModeType.java,v $
 * Revision 1.7  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:14:05  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;

/** 
 * <p>Represents the 3&nbsp;bit auxiliary bits mode of audio essence stored in the AES/EBU
 * audio file format and described by a {@linkplain tv.amwa.maj.model.AES3PCMDescriptor AES3 PCM 
 * descriptor}. Typically, this file format can use either:</p>
 * 
 * <ul>
 *  <li>20&nbsp;bits per audio sample and 4&nbsp;bits of optional auxiliary data, or</li>
 *  <li>24&nbsp;bits per audio sample and no bits of auxiliary data.</li>
 * </ul>
 * 
 * <p>The values defined here are as specified in section&nbsp;4 of
 * <a href="http://www.ebu.ch/CMSimages/en/tec_doc_t3250-2004_tcm6-12767.pdf">EBU tech. 3250</a>.</p> 
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#AuxBitsModeType
 * @see tv.amwa.maj.model.AES3PCMDescriptor#getAuxBitsMode()
 * @see tv.amwa.maj.enumeration.UserDataModeType
 *
 *
 *
 */
public enum AuxBitsModeType 
	implements MediaEnumerationValue {
	
	/**
	 * <p>Maximum audio sample word length is 20&nbsp;bits (default). Use of auxiliary sample bits not defined.</p>
	 */
	NotDefined(0),
	/**
	 * <p>Maximum audio sample word length is 24&nbsp;bits and auxiliary sample bits are used for main audio 
	 * sample data.</p>
	 */
	MainAudioSampleData(1),
	/**
	 * <p>Maximum audio sample word length is 20&nbsp;bits and auxiliary sample bits carry a single 
	 * coordination signal.</p>
	 */
	SingleCoordinationSignal(2),
	/**
	 * <p>The size of sample and audio sections is user-defined.</p>
	 */
	UserDefined(3),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved0(4),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved1(5),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved2(6),
	/**
	 * <p>Reserved for future use and should not be used.</p>
	 */
	Reserved3(7);

	private int value;
	
	private AuxBitsModeType(int value) {
		
		this.value = value;
	}

	public long value() {

		return (long) value;
	}

    public String symbol() { 
    	
    	return "AuxBitsMode_" + name(); 
    }
}
