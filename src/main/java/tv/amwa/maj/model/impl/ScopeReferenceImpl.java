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
 * $Log: ScopeReferenceImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:09:22  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.ScopeReference;

/** 
 * <p>Implements a reference to a section in the specified {@linkplain tv.amwa.maj.model.Track track}
 * or {@linkplain tv.amwa.maj.model.NestedScope nested scope} track. Scope references are specified 
 * in terms of a relative track offset, and the number of scopes to skip 
 * outward.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0d00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "ScopeReference",
		  description = "The ScopeReference class refers to a section in the specified Track or NestedScope slot.",
		  symbol = "ScopeReference")
public class ScopeReferenceImpl
	extends 
		SegmentImpl
	implements
		ScopeReference,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -7722714463453700733L;

	private int relativeScope;
	private int relativeTrack;
	
	/** Default constructor is not public to avoid unset required fields. */
	public ScopeReferenceImpl() { }

	/**
	 * <p>Creates and initializes a new scope reference object, which refers to a section 
	 * in the specified {@linkplain tv.amwa.maj.model.Track track} or 
	 * {@link tv.amwa.maj.model.NestedScope nested scope} slot.</p>
	 * 
	 * @param dataDefinition Data definition for the referenced slot and for this component.
	 * @param relativeScope Number of nested scopes to pass to find the nested scope slot or
	 * package owning the slot. 
	 * @param relativeSlot Number of slots to look backwards from the slot 
	 * containing the scope reference to pass to find the slot referenced.
	 * 
	 * @throws NullPointerException Data definition is <code>null</code>.
	 * @throws IllegalArgumentException The relative scope and or relative slot arguments are
	 * negative.
	 */
	public ScopeReferenceImpl(
			tv.amwa.maj.model.DataDefinition dataDefinition,
			@UInt32 int relativeScope,
			@UInt32 int relativeSlot)
		throws NullPointerException,
			IllegalArgumentException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a scope reference from a null data definition.");
		
		setComponentDataDefinition(dataDefinition);
		setRelativeScope(relativeScope);
		setRelativeTrack(relativeSlot);
	}
	
	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "RelativeScope",
			typeName = "UInt32", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0E01,
			symbol = "RelativeScope")
	public int getRelativeScope() {

		return relativeScope;
	}
	
	/**
	 * <p>Sets the relative scope for this scope reference.</p>
	 *
	 * @param relativeScope Relative scope for this scope reference.
	 * 
	 * @throws IllegalArgumentException Relative scope values for scope references must be 
	 * greater than of equal to <code>0</code>.
	 */
	@MediaPropertySetter("RelativeScope")
	public void setRelativeScope(
			int relativeScope) 
		throws IllegalArgumentException {
		
		if (relativeScope < 0) 
			throw new IllegalArgumentException("Relative scope values for scope references must be greater than of equal to 0.");
		
		this.relativeScope = relativeScope;
	}
	
	public final static int initializeRelativeScope() {
		
		return 0;
	}
	
	@MediaProperty(uuid1 = 0x06010103, uuid2 = (short) 0x0400, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "RelativeTrack",
			aliases = { "RelativeSlot" },
			typeName = "UInt32", 
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0E02,
			symbol = "RelativeTrack")
	public int getRelativeTrack() {

		return relativeTrack;
	}
 
	/**
	 * <p>Sets the relative track property of this scope reference.</p>
	 *
	 * @param relativeTrack Relative slot of this scope reference.
	 * 
	 * @throws IllegalArgumentException Relative slot values for scope references must be greater than of equal to 0.
	 */
	@MediaPropertySetter("RelativeTrack")
	public void setRelativeTrack(
			int relativeTrack) 
		throws IllegalArgumentException {
		
		if (relativeTrack < 0)
			throw new IllegalArgumentException("Relative slot values for scope references must be greater than of equal to 0.");

		this.relativeTrack = relativeTrack;
	}
	
	public final static int initializeRelativeTrack() {
		
		return 0;
	}
	
	public ScopeReference clone() {
		
		return (ScopeReference) super.clone();
	}
}
