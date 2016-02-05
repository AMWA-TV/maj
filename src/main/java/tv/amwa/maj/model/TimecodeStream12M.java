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
 * $Log: TimecodeStream12M.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.Bool;


/**
 * <p>Specifies a stream of timecode data in the SMPTE&nbsp;12M format.</p>
 * 
 *
 *
 */

public interface TimecodeStream12M 
	extends TimecodeStream {


	/** 
	 * <p>Default value for the include sync property, which is {@value #INCLUDESYNC_DEFAULT}.</p> 
	 * 
	 * @see #getIncludeSync() 
	 * @see #setIncludeSync(Boolean)
	 */
	public static final boolean INCLUDESYNC_DEFAULT = false; 
	
	// Decided to support the include sync property here, even though it is
	// not supported in the com-api version.
	
	/**
	 * <p>Sets <code>true</code> if  synchronization data is included in this 
	 * timecode stream and <code>false</code> if the synchronization data has a 
	 * fixed value and is omitted from the timecode stream.</p>
	 * 
	 * <p>The default value for this optional property is {@value #INCLUDESYNC_DEFAULT}.
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param includeSync Is synchronization data included in the timecode
	 * stream?
	 * 
	 * @see #INCLUDESYNC_DEFAULT
	 */
	public void setIncludeSync(
			@Bool Boolean includeSync);
	
	/**
	 * <p>Returns <code>true</code> if the synchronization data is included in the 
	 * timecode stream; otherwise <code>false</code> if the synchronization data has a 
	 * fixed value and is omitted from the timecode stream.</p>
	 * 
	 * <p>If this optional property is omitted, the default value of 
	 * {@value #INCLUDESYNC_DEFAULT} is returned.</p>
	 * 
	 * @return Is synchronization data included in the timecode
	 * stream?
	 * 
	 * @see #INCLUDESYNC_DEFAULT
	 */
	public @Bool boolean getIncludeSync();
	
	/**
	 * <p>Create a cloned copy of this timecode stream 12M.</p>
	 *
	 * @return Cloned copy of this timecode stream 12M.
	 */
	public TimecodeStream12M clone();
}
