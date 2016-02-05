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
 * $Log: WAVEDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:34  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/02/08 11:27:18  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:09:00  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataValue;


/**
 * <p>Specifies the description of a file of audio essence formatted according to the RIFF 
 * Waveform Audio File Format (WAVE).</p>
 * 
 * <p>For more information, see the <a href="http://en.wikipedia.org/wiki/WAV">description of 
 * WAVE on Wikipedia</a>.</p>
 * 
 *
 *
 * @see tv.amwa.maj.constant.CodecConstant#WAVE
 * @see SourcePackage#getEssenceDescriptor()
 * @see tv.amwa.maj.constant.ContainerConstant#RIFFWAVE
 */

public interface WAVEDescriptor 
	extends AAFFileDescriptor {

	/**
	 * <p>Returns a copy of the WAVE file information without the
	 * associated sample data from the audio essence described by this 
	 * WAVE descriptor.</p>
	 * 
	 * @return Summary property of this WAVE descriptor.
	 */
	public @DataValue byte[] getWAVESummary();

	/**
	 * <p>Returns the size of the data buffer required for the 
	 * {@link #getWAVESummary()} method.</p>
	 * 
	 * @return Size of the data buffer required for the 
	 * {@link #getWAVESummary()} method.
	 * 
	 * @deprecated Use {@link #getWAVESummary()}<code>.length</code>
	 * instead.
	 */
	@Deprecated public @UInt32 int getWAVESummaryBufferSize();

	/**
	 * <p>Sets the copy of WAVE file information without the sample data 
	 * from the audio essence described by this WAVE descriptor.</p>
	 * 
	 * @param summary Summary property for this WAVE descriptor.
	 *  
	 * @throws NullPointerException The given summary is <code>null</code>.
	 */
	public void setWAVESummary(
			@DataValue byte[] summary) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this WAVE descriptor.</p>
	 *
	 * @return Cloned copy of this WAVE descriptor.
	 */
	public WAVEDescriptor clone();
}
