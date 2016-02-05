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
 * $Log: StrongReferenceSet.java,v $
 * Revision 1.3  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 *
 */

package tv.amwa.maj.industry;

import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;

/**
 * <p>Utilities for manipulating {@linkplain MediaEntity media entities} stored in 
 * a {@linkplain TypeDefinitionStrongObjectReference strong reference}
 * {@linkplain TypeDefinitionSet set}. These static methods ensure 
 * a consistent approach to managing elements of a set through add, remove and contains 
 * operations.</p>
 * 
 *
 * 
 * @see StrongReferenceVector
 * @see WeakReferenceSet
 * @see TypeDefinitionSet
 * @see TypeDefinitionStrongObjectReference
 */
public class StrongReferenceSet {

	// TODO is MediaEntity too strong?
	
	private StrongReferenceSet() { }
	
	@SuppressWarnings("unchecked")
	public final static <T extends MediaEntity> void add(
			Set<T> set,
			T item) 
		throws NullPointerException {
		
		if (set == null)
			throw new NullPointerException("Cannot work with a null valued set.");
		
		if (item == null)
			throw new NullPointerException("Cannot add a null value to a set.");
		
		set.add((T) item.clone());
	}

	public final static <T extends MediaEntity> boolean contains(
			Set<T> set,
			T item)
		throws NullPointerException {
		
		if (set == null)
			throw new NullPointerException("Cannot work with a null valued set.");

		if (item == null)
			throw new NullPointerException("Cannot check whether an item is contained in a set using a null value.");
		
		return set.contains(item);
	}
	
	public final static <T extends MediaEntity> boolean remove(
			Set<T> set,
			T item)
		throws NullPointerException {
		
		if (set == null)
			throw new NullPointerException("Cannot work with a null valued set.");

		if (item == null)
			throw new NullPointerException("Cannot remove an item from a set using a null value.");
		
		return set.remove(item);
	}
	
	public final static <T extends MediaEntity> Set<T> getOptionalSet(
			Set<T> set) 
		throws PropertyNotPresentException {
		
		if (set == null)
			throw new NullPointerException("Cannot work with a null valued set.");

		if (set.size() == 0)
			throw new PropertyNotPresentException("The optional strong reference set property is not present.");
		
		return new HashSet<T>(set);
	}

	public final static <T extends MediaEntity> Set<T> getRequiredSet(
			Set<T> set) {
		
		if (set == null)
			throw new NullPointerException("Cannot work with a null valued set.");
		
		return new HashSet<T>(set);
	}
}
