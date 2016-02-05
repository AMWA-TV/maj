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
 * $Log: AIFCDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:31  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/01/27 11:07:36  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataValue;

/**
 * <p>Specifies the description of a file of audio essence formatted according to the Audio Interchange 
 * File Format with Compression (AIFC).<p>
 * 
 * <p>The audio interchange file format with compression, where audio data is stored in chunks that
 * are compressed according to various defined codecs. For more information, see the description of
 * <a href="http://en.wikipedia.org/wiki/AIFF">AIFF on Wikipedia</a>. 
 * 
 *
 * 
 * @see tv.amwa.maj.constant.CodecConstant#AIFC
 * @see SourcePackage#getEssenceDescriptor()
 */
public interface AIFCDescriptor 
	extends AAFFileDescriptor {
	
	/** 
	 * <p>Gets a copy of the descriptive information in the associated 
	 * AIFC Audio Data value.</p>
	 * 
	 * @return A copy of the descriptive information in the associated AIFC audio 
	 * data value.
	 */
	public @DataValue byte[] getAIFCSummary();
	
	/** 
	 * <p>Returns the size of the buffer returned by the {@link #getAIFCSummary()} 
	 * method.</p>
	 * 
	 * @return Size of the summary buffer.
	 * 
	 * @deprecated Use {@link #getAIFCSummary()}<code>.length</code> instead.
	 */
	@Deprecated
	public @UInt32 int getSummaryBufferSize();
	
	/**
	 * <p>Sets the AIFC file information, the descriptive information in the associated
	 * AIFC audio data value.</p>
	 * 
	 * @param summary A copy of the descriptive information in the associated 
	 * AIFC Audio Data value.
	 * 
	 * @throws NullPointerException The given summary buffer is <code>null</code>.
	 */
	public void setAIFCSummary(
			@DataValue byte[] summary) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this AIFC descriptor.</p>
	 * 
	 * @return Cloned copy of this AIFC descriptor.
	 */
	public AIFCDescriptor clone();
}
