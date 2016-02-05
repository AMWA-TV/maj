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
 * $Log: MultiXferItem.java,v $
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
 * Revision 1.1  2007/11/13 22:13:06  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.record.AUID;

/** 
 * <p>Specifies an element of a transfer array that is used when reading one
 * or more channels from an interlaced data stream.</p>
 * 
 * <p><em>Interleaved-essence</em>: An essence format that combines two or more channels 
 * of audio or video data into a single essence stream.</p>
 * 
 * @see tv.amwa.maj.model.EssenceAccess
 *  
 *
 */

public interface MultiXferItem { 

	/**
	 * <p>Returns the data buffer of this item.</p>
	 *
	 * @return Data buffer of this item.
	 */
	public @DataBuffer byte[] getBuffer();
	
	/**
	 * <p>Sets the data buffer of this item.</p>
	 *
	 * @param buffer Data buffer of this item.
	 * 
	 * @throws NullPointerException The given data buffer is <code>null</code>.
	 */
	public void setBuffer(
			@DataBuffer byte[] buffer) 
		throws NullPointerException;

	/**
	 * <p>Returns the length of the data buffer of this item.</p>
	 *
	 * @return Length of the data buffer of this item.
	 * 
	 * @deprecated Java class-based representation of arrays instead, e.g. call 
	 * {@link #getBuffer()}<code>.length</code>.
	 */
	@Deprecated public @UInt32 int getBuflen();

	/**
	 * <p>Provided to set the length of the data buffer of this item but this feature is unnecessary
	 * with Java's class-based representation of arrays. This method does nothing.</p>
	 *
	 * @param buflen Value is ignored.
	 * 
	 * @deprecated Buffer length is set by {@link #setBuffer(byte[])}.
	 */
	@Deprecated public void setBuflen(
			@UInt32 int buflen);

	/**
	 * <p>Returns the type of essence of this data item.</p>
	 *
	 * @return Type of essence represented by this item.
	 */
	public AUID getMediaKind();

	/**
	 * <p>Sets the essence type definition of this data item.</p>
	 *
	 * @param mediaKind Type of essence represented by this item.
	 * 
	 * @throws NullPointerException The given media kind is <code>null</code>.
	 */
	public void setMediaKind(
			AUID mediaKind) 
		throws NullPointerException;

	/**
	 * <p>Returns the number of samples of this data item to transfer.</p>
	 *
	 * @return Number of samples of this data item to transfer.
	 */
	public @UInt32 int getNumSamples();

	/**
	 * <p>Sets the number of samples of this data item to transfer.</p>
	 *
	 * @param numSamples Number of samples of this data item to transfer.
	 * 
	 * @throws IllegalArgumentException The given number of samples is negative.
	 */
	public void setNumSamples(
			@UInt32 int numSamples)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the physical input-output track number of this data item, also known as "physical".</p>
	 *
	 * @return Physical input-output track number of this data item.
	 */
	public @Int16 short getSubTrackNum();

	/**
	 * <p>Sets the physical input-output track number of this data item, also known as "physical".</p>
	 *
	 * @param subTrackNum Physical input-output track number of this data item.
	 */
	public void setSubTrackNum(
			@Int16 short subTrackNum);	
}
