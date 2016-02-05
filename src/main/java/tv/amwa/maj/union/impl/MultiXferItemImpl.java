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
 * $Log: MultiXferItemImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:48  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;
import java.util.Arrays;

import tv.amwa.maj.integer.Int16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.DataBuffer;
import tv.amwa.maj.record.AUID;

/**
 * <p>Implementation of an element of a transfer array that is used when reading or writing one
 * or more channels from an interleaved data stream.</p>
 * 
 * <p><em>Interleaved-essence</em>: An essence format that combines two or more channels 
 * of audio or video data into a single essence stream.</p>
 * 
 * @see tv.amwa.maj.union.impl.MultiXferItemImpl
 * @see tv.amwa.maj.model.EssenceMultiAccess
 * @see tv.amwa.maj.model.EssenceMultiAccess#readMultiSamples(tv.amwa.maj.union.MultiXferItem[])
 * @see tv.amwa.maj.model.EssenceMultiAccess#writeMultiSamples(tv.amwa.maj.union.MultiXferItem[])
 * 
 *
 */
public class MultiXferItemImpl 
	implements tv.amwa.maj.union.MultiXferItem,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 738803789994372035L;
	
	/** <p>The essence type definition, also known as "essenceDef".</p> */
	private AUID mediaKind;
	/** <p>The physical input-output channel number, also known as "physical".</p> */ 
	@Int16 private short subTrackNum;
	/** <p>The number of samples to transfer.</p> */
	@UInt32 private int numSamples;
	/** <p>The buffer of un-interleaved stream data.</p> */
	@DataBuffer private byte[] buffer;

    /**
     * <p>Create a new multiple-transfer item.</p>
     * 
	 * @param mediaKind The essence type definition, also known as "essenceDef".
	 * @param subTrackNum The physical input-output channel number, also known as "physical".
	 * @param numSamples The number of samples to transfer.
	 * @param buffer The buffer of un-interleaved stream data.
	 * 
	 * @throws NullPointerException One or both of the given media kind or data buffer is <code>null</code>.
	 * @throws IllegalArgumentException The given number of samples is negative.
	 */
	public MultiXferItemImpl(
			AUID mediaKind, 
			@Int16 short subTrackNum, 
			@UInt32 int numSamples, 
			byte[] buffer) 
		throws NullPointerException,
			IllegalArgumentException {
		
		setMediaKind(mediaKind);
		setBuffer(buffer);
		setNumSamples(numSamples);
		setSubTrackNum(subTrackNum);
	}

	public @DataBuffer byte[] getBuffer(){ 
		
		return buffer;
	}

	public void setBuffer(
			@DataBuffer byte[] buffer) 
		throws NullPointerException {

		if (buffer == null)
			throw new NullPointerException("The given data buffer for this multi transfer item is null.");
		
		this.buffer = buffer;
	}

	@Deprecated
	public @UInt32 int getBuflen() {
		return buffer.length;
	}


	@Deprecated
	public void setBuflen(
			@UInt32 int buflen) { }


	public AUID getMediaKind() {
	
		return mediaKind;
	}

	public void setMediaKind(
			tv.amwa.maj.record.AUID mediaKind) 
		throws NullPointerException {
		
		if (mediaKind == null)
			throw new NullPointerException("The given media kind for this multi-transfer item is null.");
		
		this.mediaKind = mediaKind.clone();
	}

	public @UInt32 int getNumSamples() {
		return numSamples;
	}

	public void setNumSamples(
			@UInt32 int numSamples) 
		throws IllegalArgumentException {
		
		if (numSamples < 0)
			throw new IllegalArgumentException("The given number of samples for this multi-transfer item is negative.");
		
		this.numSamples = numSamples;
	}

	public @Int16 short getSubTrackNum() {
		return subTrackNum;
	}

	public void setSubTrackNum(
			@Int16 short subTrackNum) {
		
		this.subTrackNum = subTrackNum;
	}

	@Override
	public boolean equals(
			Object o) {

		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof tv.amwa.maj.union.MultiXferItem)) return false;
		
		tv.amwa.maj.union.MultiXferItem testItem = (tv.amwa.maj.union.MultiXferItem) o;
		
		if (testItem.getNumSamples() != numSamples) return false;
		if (testItem.getSubTrackNum() != subTrackNum) return false;
		if (!(mediaKind.equals(testItem.getMediaKind()))) return false;
		if (buffer.length != testItem.getBuffer().length) return false;
		
		return Arrays.equals(buffer, testItem.getBuffer());
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of the metadata part of this multi-transfer
	 * item. No XML schema or DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;MultiTransferItem bufferLength="65536"&gt;
	 *   &lt;MediaKind&gt;urn:x-ul:060e2b34.0401.0101.01030202.01000000&lt;/MediaKind&gt;
	 *   &lt;NumSamples&gt;42&lt;/NumSamples&gt;
	 *   &lt;SubTrackNum&gt;7&lt;/SubTrackNum&gt;
	 * &lt;/MultiTransferItem&gt;
	 * </pre>
	 * 
	 * @return XML representation of the metadata of this multi-transfer item.
	 */
	@Override
	public String toString() {

		return "<MultiTransferItem bufferLength=\"" + buffer.length + "\">\n" +
			"  <MediaKind>" + mediaKind.toString() + "</MediaKind>\n" +
			"  <NumSamples>" + numSamples + "</NumSamples>\n" +
			"  <SubTrackNum>" + subTrackNum + "</SubTrackNum>\n" +
			"</MultiTransferItem>";
	}

	@Override
	public MultiXferItemImpl clone() 
		throws CloneNotSupportedException {

		return (MultiXferItemImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return numSamples ^ ~subTrackNum ^ mediaKind.hashCode() ^ Arrays.hashCode(buffer);
	}
}
