/* 
 **********************************************************************
 *
 * $Id: WeakReferenceSet.java,v 1.6 2011/07/27 12:25:44 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: WeakReferenceSet.java,v $
 * Revision 1.6  2011/07/27 12:25:44  vizigoth
 * Fixed import warning messages.
 *
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/03/19 10:02:11  vizigoth
 * Added support for lazy evaluation of weak references when read from a stream.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 *
 */

package tv.amwa.maj.industry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.exception.PropertyNotPresentException;

/**
 * <p>Represents a set of weak references and provides a means to resolve
 * them at runtime. The class provides a consistent set of delegate add, contains and remove methods
 * for weak reference set type properties.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 * @param <T> Type of the target of all the references in the set.
 * 
 * @see WeakReference
 * @see WeakReferenceTarget
 * @see WeakReferenceVector
 * @see tv.amwa.maj.meta.TypeDefinitionSet
 * @see tv.amwa.maj.meta.TypeDefinitionWeakObjectReference
 * @see JPAGenerator#generateORM(java.util.Collection, String)
 */
public class WeakReferenceSet<T extends WeakReferenceTarget> {

	private Set<WeakReference<T>> set = 
		Collections.synchronizedSet(new HashSet<WeakReference<T>>());
	@SuppressWarnings("unused")
	private long persistentID = 0l;
	
	public WeakReferenceSet() { }
	
	public void add(
			T item)
		throws NullPointerException {
		
		if (item == null)
			throw new NullPointerException("Cannot add a null item to a set of weak references.");
		
		set.add(new WeakReference<T>(item));
	}
	
	public void add(
			WeakReference<T> reference) 
		throws NullPointerException {
		
		if (reference == null)
			throw new NullPointerException("Cannot add a null item to a set of weak references.");
		
		set.add(reference);
	}
	
	public boolean contains(
			T item) 
		throws NullPointerException {
		
		if (item == null)
			throw new NullPointerException("Cannot check for containment of an item in a set using a null value.");
		
		return set.contains(item);
	}
	
	public boolean remove(
			T item) 
		throws NullPointerException {
		
		if (item == null)
			throw new NullPointerException("Cannot remove an item from a set using a null value.");
		
		return set.remove(item);
	}
	
	public void clear() {
		
		set.clear();
	}
	
	public int count() {
		
		return set.size();
	}
	
	public Set<T> getOptionalSet() 
		throws PropertyNotPresentException {
		
		if (set.size() == 0)
			throw new PropertyNotPresentException("The optional weak reference set property is not present.");
		
		return getRequiredSet();
	}
	
	public Set<T> getRequiredSet() {
		
		Set<T> unpackedList = new HashSet<T>(set.size());
		for ( WeakReference<T> weakReference : set )
			unpackedList.add(weakReference.getTarget());
		
		return unpackedList;		
	}
}
