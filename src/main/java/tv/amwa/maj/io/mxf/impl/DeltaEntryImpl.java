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
 * $Log: DeltaEntryImpl.java,v $
 * Revision 1.4  2011/07/28 18:51:18  vizigoth
 * Changes to better support creating index tables with delta entries.
 *
 * Revision 1.3  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/01/21 20:51:31  vizigoth
 * Updates to index table support to the point where index table data can be read from MXF files and stream offsets can be calculated.
 *
 * Revision 1.1  2010/01/19 14:44:24  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 *
 */

package tv.amwa.maj.io.mxf.impl;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

import java.text.ParseException;

import tv.amwa.maj.integer.Int8;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt32Array;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.mxf.DeltaEntry;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;

/**
 * <p>Defines a byte offset value along an incrementing timeline as part of a delta entry
 * array in an {@linkplain IndexTableSegmentImpl index table segment}.</p>
 * 
 *
 * 
 */
public class DeltaEntryImpl 
	implements
	 	DeltaEntry,
		Cloneable, 
		Serializable,
		XMLSerializable {
	
	private static final long serialVersionUID = -1341008782583700631L;
	
	private @Int8 byte posTableIndex = POSTABLEINDEX_DEFAULT;
	private @UInt8 byte slice = SLICE_DEFAULT;
	private @UInt32 int elementDelta;
	
	static {
		TypeDefinitionRecordImpl.registerInterfaceMapping(DeltaEntry.class, DeltaEntryImpl.class);
	}
	
	public DeltaEntryImpl() { }
	
	public DeltaEntryImpl(
			@UInt32 int elementDelta)
		throws IllegalArgumentException {
		
		setElementDelta(elementDelta);
	}
	
	public DeltaEntryImpl(
			@Int8 byte posTableIndex,
			@UInt8 byte slice,
			@UInt32 int elementDelta)
		throws IllegalArgumentException {
		
		setPosTableIndex(posTableIndex);
		setSlice(slice);
		setElementDelta(elementDelta);
	}
	
	public final static DeltaEntry[] makeDeltaEntryArray(
			@UInt32Array int[] elementDeltas) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (elementDeltas == null)
			throw new NullPointerException("Cannot make an array of delta entries from a null value.");
		
		for ( int elementDelta : elementDeltas )
			if (elementDelta < 0) throw new IllegalArgumentException("Cannot set an element delta value to a negative value.");
		
		DeltaEntry[] deltaEntries = new DeltaEntry[elementDeltas.length];
		
		for ( int x = 0 ; x < elementDeltas.length ; x++ )
			deltaEntries[x] = new DeltaEntryImpl(elementDeltas[x]);
		
		return deltaEntries;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public @Int8 byte getPosTableIndex() {
		
		return posTableIndex;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public @UInt8 void setPosTableIndex(
			@UInt8 byte posTableIndex) 
		throws IllegalArgumentException {
		
		if (posTableIndex < -1)
			throw new IllegalArgumentException("A position table index cannot be less than -1.");
		
		this.posTableIndex = posTableIndex;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public @UInt8 byte getSlice() {
		
		return slice;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public void setSlice(
			@UInt8 byte slice)
		throws IllegalArgumentException {
		
		if (slice < 0)
			throw new IllegalArgumentException("Cannot set the value of the slice number to a negative value.");
		
		this.slice = slice;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public @UInt32 int getElementDelta() {
		
		return elementDelta;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public void setElementDelta(
			@UInt32 int elementDelta) 
		throws IllegalArgumentException {
		
		if (elementDelta < 0)
			throw new IllegalArgumentException("Cannot set the delta from the element delta offset to a negative value.");
		
		this.elementDelta = elementDelta;
	}
	
	public boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (this == o) return true;
		if (!(o instanceof DeltaEntry)) return false;
		
		DeltaEntry testEntry = (DeltaEntry) o;
		
		if (testEntry.getElementDelta() != elementDelta) return false;
		if (testEntry.getPosTableIndex() != posTableIndex) return false;
		if (testEntry.getSlice() != slice) return false;
		
		return true;
	}
	
	/**
	 * <p>Create a cloned copy of this DeltaEntryImpl.</p>
	 *
	 * @return Cloned copy of this DeltaEntryImpl.
	 */
	public DeltaEntry clone() {
		
		try {
			return (DeltaEntry) super.clone();
		} 
		catch (CloneNotSupportedException cnse) {
			// Implements cloneable, so lot likely
			throw new InternalError(cnse.getMessage());
		}
	}
	
	public String toString() {
		
		return XMLBuilder.toXMLNonMetadata(this);
	}
	
	public int hashCode() {
		
		return (posTableIndex << 24) ^ (slice << 16) ^ elementDelta;
	}
	
	public final static String DELTAENTRY_TAG = "DeltaEntry";
	final static String POSTABLEINDEX_TAG = "PosTableIndex";
	final static String SLICE_TAG = "Slice";
	final static String ELEMENTDELTA_TAG = "ElementDelta";
	
	public void appendXMLChildren(
			Node parent) {
		
		Node deltaEntryElement;
		
		if (parent instanceof DocumentFragment)
			deltaEntryElement = 
				XMLBuilder.createChild(parent, MXFConstants.RP210_NAMESPACE, 
						MXFConstants.RP210_PREFIX, DELTAENTRY_TAG);
		else
			deltaEntryElement = parent;

		XMLBuilder.appendElement(deltaEntryElement, MXFConstants.RP210_NAMESPACE, 
				MXFConstants.RP210_PREFIX, POSTABLEINDEX_TAG, posTableIndex);
		XMLBuilder.appendElement(deltaEntryElement, MXFConstants.RP210_NAMESPACE, 
				MXFConstants.RP210_PREFIX, SLICE_TAG, slice);
		XMLBuilder.appendElement(deltaEntryElement, MXFConstants.RP210_NAMESPACE, 
				MXFConstants.RP210_PREFIX, ELEMENTDELTA_TAG, elementDelta);
	}

	private final static Pattern posTableIndexPattern = 
		Pattern.compile("<\\w*\\:?" + POSTABLEINDEX_TAG + "\\>(-?\\d+|(-?0x[0-9a-fA-F]+))\\<\\/\\w*\\:?" + POSTABLEINDEX_TAG + "\\>");
	private final static Pattern slicePattern = 
		Pattern.compile("<\\w*\\:?" + SLICE_TAG + "\\>(\\d+|(0x[0-9a-fA-F]+))\\<\\/\\w*\\:?" + SLICE_TAG + "\\>");
	private final static Pattern elementDeltaPattern =
		Pattern.compile("<\\w*\\:?" + ELEMENTDELTA_TAG + "\\>(\\d+|(0x[0-9a-fA-F]+))\\<\\/\\w*\\:?" + ELEMENTDELTA_TAG + "\\>");
	
	public final static DeltaEntry parseFactory(
			String deltaEntryString) 
		throws NullPointerException,
			ParseException {
		
		if (deltaEntryString == null)
			throw new NullPointerException("Cannot create a delta entry from a null value.");
		
		Matcher matcher = null;
		
		byte posTableIndex = POSTABLEINDEX_DEFAULT;
		byte slice = SLICE_DEFAULT;
		int elementDelta = 0;
		
		try {
			matcher = posTableIndexPattern.matcher(deltaEntryString);
			if (matcher.find()) {
				String posTableIndexString = matcher.group(1);
				boolean posTableNegative = (posTableIndexString.startsWith("-")) ? true : false;
				if (posTableNegative) posTableIndexString = posTableIndexString.substring(1);
				
				if (posTableIndexString.startsWith("0x")) 
					posTableIndex = Byte.parseByte(posTableIndexString.substring(2), 16);
				else
					posTableIndex = Byte.parseByte(posTableIndexString);
				
				if (posTableNegative) posTableIndex = (byte) -posTableIndex;
			}
			
			matcher = slicePattern.matcher(deltaEntryString);
			if (matcher.find()) {
				String sliceString = matcher.group(1);
				if (sliceString.startsWith("0x"))
					slice = Byte.parseByte(sliceString.substring(2), 16);
				else
					slice = Byte.parseByte(sliceString);
			}
			
			matcher = elementDeltaPattern.matcher(deltaEntryString);
			if (!matcher.find())
				throw new ParseException("A delta entry must have an element delta value specified.", 0);
			
			String elementDeltaString = matcher.group(1);
			if (elementDeltaString.startsWith("0x"))
				elementDelta = Integer.parseInt(elementDeltaString, 16);
			else 
				elementDelta = Integer.parseInt(elementDeltaString);
			
			return new DeltaEntryImpl(posTableIndex, slice, elementDelta);
		}
		catch (Exception e) {
			throw new ParseException(e.getClass().getName() + " thrown when parsing a delta entry: " + e.getMessage(), 0);
		}
	}

	// TODO XMLHandler - is this needed in this case?
	
	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}

}
