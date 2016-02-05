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
 * $Log: MultiResultItem.java,v $
 * Revision 1.2  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.3  2008/01/10 17:19:47  vizigoth
 * Minor comment improvement.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:13:01  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.integer.UInt32;

/** 
 * <p>Specifies an element of an array containing multiple result values from an operation
 * to read one or more channels from an interleaved data stream.</p>
 * 
 * @see tv.amwa.maj.model.EssenceMultiAccess
 * 
 *
 */

public interface MultiResultItem { 

	/**
	 * <p>Returns the total number of bytes transferred.</p>
	 *
	 * @return Total number of bytes transferred.
	 */
	public @UInt32 int getBytesXfered();

	/**
	 * <p>Sets the total number of bytes transferred.</p>
	 *
	 * @param bytesXfered Total number of bytes transferred.
	 * 
	 * @throws IllegalArgumentException The given number of bytes transferred is negative.
	 */
	public void setBytesXfered(
			@UInt32 int bytesXfered)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of samples transferred.</p>
	 *
	 * @return Number of samples transferred.
	 */
	public @UInt32 int getSamplesXfered();

	/**
	 * <p>Sets the number of samples transferred.</p>
	 *
	 * @param samplesXfered Number of samples transferred.
	 * 
	 * @throws IllegalArgumentException The given number of samples transferred is negative.
	 */
	public void setSamplesXfered(
			@UInt32 int samplesXfered)
		throws IllegalArgumentException;
}
