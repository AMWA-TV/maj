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
 * $Log: MultiResultItemImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:51  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.integer.UInt32;

// TODO test and should these really by UInt32s rather then 64s!

/** 
 * <p>Implementation of an element of an array containing multiple result values from an operation
 * to read or write one or more channels from an interleaved data stream.</p>
 * 
 * <p><em>Interleaved-essence</em>: An essence format that combines two or more channels 
 * of audio or video data into a single essence stream.</p>
 * 
 * @see tv.amwa.maj.model.EssenceMultiAccess
 * @see tv.amwa.maj.model.EssenceMultiAccess#readMultiSamples(tv.amwa.maj.record.MultiXferItem[])
 * @see tv.amwa.maj.model.EssenceMultiAccess#writeMultiSamples(tv.amwa.maj.record.MultiXferItem[])
 *
 *
 */
public class MultiResultItemImpl 
	implements tv.amwa.maj.union.MultiResultItem,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 2045068789673524361L;

	/** <p>The total number of bytes transferred.</p> */
	@UInt32 private int bytesXfered;
	/** <p>The total number of samples transferred.</p> */
	@UInt32 private int samplesXfered;

    /**
     * <p>Create a multi-result value containing the number of bytes and number
     * of samples transferred for a particular interleaved stream.</p>
     * 
	 * @param bytesXfered The total number of bytes transferred.
	 * @param samplesXfered The total number of samples transferred.
	 * 
	 * @throws IllegalArgumentException One or both of the given bytes or samples 
	 * transferred values is negative.
	 */
	public MultiResultItemImpl(
			@UInt32 int bytesXfered, 
			@UInt32 int samplesXfered) 
		throws IllegalArgumentException {
		
		setBytesXfered(bytesXfered);
		setSamplesXfered(samplesXfered);
	}

	public @UInt32 int getBytesXfered() {
		
		return bytesXfered;
	}

	public void setBytesXfered(
			@UInt32 int bytesXfered) 
		throws IllegalArgumentException {
		
		if (bytesXfered < 0) 
			throw new IllegalArgumentException("The given bytes transferred value for this multi-transfer item is negative.");
		
		this.bytesXfered = bytesXfered;
	}

	public @UInt32 int getSamplesXfered() {
		
		return samplesXfered;
	}

	public void setSamplesXfered(
			@UInt32 int samplesXfered) 
		throws IllegalArgumentException {
		
		if (samplesXfered < 0)
			throw new IllegalArgumentException("The given samples transferred valeu for this multi-transfer item is negative.");
		
		this.samplesXfered = samplesXfered;
	}

	@Override
	public boolean equals(
			Object o) {
	
		if (o == null) return false;
		if (o == this) return true;
		
		if (!(o instanceof tv.amwa.maj.union.MultiResultItem)) return false;
		
		tv.amwa.maj.union.MultiResultItem testItem = (tv.amwa.maj.union.MultiResultItem) o;
		
		if (testItem.getBytesXfered() != bytesXfered) return false;
		if (testItem.getSamplesXfered() != samplesXfered) return false;
		
		return true;
	}
	
	/**
	 * <p>Creates a pseudo-XML representation of this multi-result item. No XML schema or
	 * DTD defines this element. For example:</p>
	 * 
	 * <pre>
	 * &lt;MultiResultItem bytesTransferred="65536" samplesTransferred="42"/&gt;
	 * </pre>
	 * 
	 * @return XML representation of this multi-result item.
	 */
	@Override
	public String toString() {

		return "<MultiResultItem bytesTransferred=\"" + bytesXfered + 
			"\" samplesTransferred=\"" + samplesXfered + "\"/>";
	}

	@Override
	public MultiResultItemImpl clone() 
		throws CloneNotSupportedException {

		return (MultiResultItemImpl) super.clone();
	}

	@Override
	public int hashCode() {

		return bytesXfered ^ ~samplesXfered;
	}
}
