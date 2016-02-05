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
 * $Log: StrongReferenceVector.java,v $
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 *
 */

package tv.amwa.maj.industry;

import java.util.List;
import java.util.Vector;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;

/**
 * <p>Utilities for manipulating {@linkplain MediaEntity media entities} stored in 
 * a {@linkplain TypeDefinitionStrongObjectReference strong reference}
 * {@linkplain TypeDefinitionVariableArray variable array (vector)}. These static methods ensure 
 * a consistent approach to managing elements of a set through append, prepend, get, contains 
 * and remove operations. The methods also ensure that index values are maintained so that
 * the order of the array is preserved when it is persisted through an object relational
 * mapping that does not support automatic index columns.</p>
 * 
 *
 * 
 * @see StrongReferenceSet
 * @see WeakReferenceVector
 * @see TypeDefinitionVariableArray
 * @see TypeDefinitionStrongObjectReference
 */
public class StrongReferenceVector {

	private StrongReferenceVector() { }
	
	@SuppressWarnings("unchecked")
	public final static <T extends MediaEntity> void append(
			List<T> list,
			T item) 
		throws NullPointerException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot append a null value to a list of strong references.");
		
		list.add((T) item.clone());
		item.setPersistentIndex(list.size() - 1);
	}
			
	public final static <T extends MediaEntity> void appendNoClone(
			List<T> list,
			T item) 
		throws NullPointerException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot append a null value to a list of strong references.");
		
		list.add((T) item);
		item.setPersistentIndex(list.size() - 1);
	}

	@SuppressWarnings("unchecked")
	public final static <T extends MediaEntity> void prepend(
			List<T> list,
			T item) 
		throws NullPointerException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot prepend a null value to a list of strong references.");
		
		list.add(0, (T) item.clone());
		item.setPersistentIndex(0);
		
		for ( int u = 1 ; u < list.size() ; u++ )
			list.get(u).setPersistentIndex(u);
	}
	
	public final static <T extends MediaEntity> void prependNoClone(
			List<T> list,
			T item) 
		throws NullPointerException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot prepend a null value to a list of strong references.");
		
		list.add(0, (T) item);
		item.setPersistentIndex(0);
		
		for ( int u = 1 ; u < list.size() ; u++ )
			list.get(u).setPersistentIndex(u);
	}

	@SuppressWarnings("unchecked")
	public final static <T extends MediaEntity> void insert(
			List<T> list,
			int index,
			T item) 
		throws NullPointerException,
			IndexOutOfBoundsException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot insert a null value to a list of strong references.");
		
		try {
			list.add(index, (T) item.clone());
			item.setPersistentIndex(index);
		
			for ( int u = index + 1 ; u < list.size() ; u++ )
				list.get(u).setPersistentIndex(u);
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the current strong reference list.");
		}
	}
	
	public final static <T extends MediaEntity> void insertNoClone(
			List<T> list,
			int index,
			T item) 
		throws NullPointerException,
			IndexOutOfBoundsException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot insert a null value to a list of strong references.");
		
		try {
			list.add(index, (T) item);
			item.setPersistentIndex(index);
		
			for ( int u = index + 1 ; u < list.size() ; u++ )
				list.get(u).setPersistentIndex(u);
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the current strong reference list.");
		}
	}

	@SuppressWarnings("unchecked")
	public final static <T extends MediaEntity> T getAt(
			List<T> list,
			int index) 
		throws NullPointerException,
			IndexOutOfBoundsException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		try {
			return (T) list.get(index).clone();
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the current strong reference list.");
		}
	}
	
	public final static <T extends MediaEntity> boolean contains(
			List<T> list,
			T item) 
		throws NullPointerException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		if (item == null)
			throw new NullPointerException("Cannot check whether a strong reference list contains a null value.");
		
		return list.contains(item);
	}
	
	public final static <T extends MediaEntity> void remove(
			List<T> list,
			int index) 
		throws NullPointerException,
			IndexOutOfBoundsException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		try {
			list.remove(index);
			
			for ( int u = index ; u < list.size() ; u++ )
				list.get(u).setPersistentIndex(u);
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is outside the acceptable range for the current strong reference list.");
		}		
	}
	
	public final static <T extends MediaEntity> boolean remove(
			List<T> list,
			T item)
		throws NullPointerException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");

		if (item == null)
			throw new NullPointerException("Cannot remove a null value from a list of strong references.");
		
		return list.remove(item);
	}
	
	public final static <T extends MediaEntity> List<T> getOptionalList(
			List<T> list) 
		throws PropertyNotPresentException {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		if (list.size() == 0)
			throw new NullPointerException("The optional strong reference list property is not present.");
		
		return new Vector<T>(list);
	}
	
	public final static <T extends MediaEntity> List<T> getRequiredList(
			List<T> list) {
		
		if (list == null)
			throw new NullPointerException("Cannot work with a null-valued list.");
		
		return new Vector<T>(list);
	}

}
