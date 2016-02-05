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
 * $Log: WeakReferenceVector.java,v $
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
import java.util.List;
import java.util.Vector;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.io.xml.XMLBuilder;

/**
 * <p>Represents a variable array (vector) of weak references and provides a
 * means to resolve them at runtime. The class provides a consistent set of 
 * delegate append, prepend, get, contains and remove methods
 * for weak reference vector type properties.</p>
 * 
 * <p>If the array of references is persisted via an object relational mapping,
 * the methods of this class ensure the order of that array is preserved.</p>
 * 
 *
 *
 * @param <T> Type of the target of all elements of the array (vector).
 * 
 * @see WeakReference
 * @see tv.amwa.maj.meta.TypeDefinitionVariableArray
 * @see WeakReferenceTarget
 * @see WeakReferenceSet
 * @see tv.amwa.maj.meta.TypeDefinitionWeakObjectReference
 * @see JPAGenerator#generateORM(java.util.Collection, String)
 */
public class WeakReferenceVector<T extends WeakReferenceTarget> {

	private List<WeakReference<T>> vector = Collections.synchronizedList(new Vector<WeakReference<T>>());
	private long persistentID = 0l;
	
	public WeakReferenceVector() { }
	
	public void append(
			T item) 
		throws NullPointerException {
		
		if (item == null)
			throw new NullPointerException("Cannot append to a list of weak references with a null value.");
		
		WeakReference<T> weakReference = new WeakReference<T>(item);
		vector.add(weakReference);
		weakReference.setPersistentIndex(vector.size() - 1);
	}
	
	public void append(
			WeakReference<T> reference) 
		throws NullPointerException {
		
		if (reference == null)
			throw new NullPointerException("Cannot append to a list of weak references with a null value.");
		
		vector.add(reference);
		reference.setPersistentIndex(vector.size() - 1);
	}
	
	public void prepend(
			T item) 
		throws NullPointerException {
		
		if (item == null)
			throw new NullPointerException("Cannot prepend to a list of weak references with a null value.");

		WeakReference<T> weakReference = new WeakReference<T>(item);
		vector.add(0, weakReference);
		weakReference.setPersistentIndex(0);
		
		for ( int u = 1 ; u < vector.size() ; u++ )
			vector.get(u).setPersistentIndex(u);
	}
	
	public void insert(
			int index,
			T item) 
		throws NullPointerException,
			IndexOutOfBoundsException {
		
		if (item == null)
			throw new NullPointerException("Cannot insert to a list of weak references with a null value.");
	
		try {
			WeakReference<T> weakReference = new WeakReference<T>(item);
			vector.add(index, weakReference);
			weakReference.setPersistentIndex(index);
			
			for ( int u = index + 1 ; u < vector.size() ; u++ )
				vector.get(u).setPersistentIndex(u);
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is out of bounds for the current list of weak object references.");
		}
	}
	
	public List<T> getRequiredList() {
		
		List<T> resolvedList = new Vector<T>(vector.size());
		for ( WeakReference<T> referenceItem : vector ) 
			resolvedList.add(referenceItem.getTarget());
		return resolvedList;
	}
	
	public List<T> getOptionalList() 
		throws PropertyNotPresentException {
		
		if (vector.size() == 0)
			throw new PropertyNotPresentException("The optional weak reference list property is not present.");
		
		return getRequiredList();
	}
	
	public boolean contains(
			T item) {
		
		for ( WeakReference<T> referenceItem : vector )
			if (referenceItem.getTarget().equals(item.getAUID())) return true;
		
		return false;
	}
	
	public T getAt(
			int index)
		throws IndexOutOfBoundsException {
		
		try {
			return vector.get(index).getTarget();
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is out of bounds for the current list of weak object references.");			
		}
	}
	
	public void removeAt(
			int index) {
		
		try {
			vector.remove(index);
			
			for ( int u = 0 ; u < vector.size() ; u++ )
				vector.get(u).setPersistentIndex(u);
		}
		catch (IndexOutOfBoundsException iobe) {
			throw new IndexOutOfBoundsException("The given index is out of bounds for the current list of weak object references.");			
		}
	}
	
	public boolean remove(
			T essenceKind) 
		throws NullPointerException {

		if (essenceKind == null)
			throw new NullPointerException("Cannot remove a null value from a list of weak references.");
		
		for ( WeakReference<T> weakReference : vector )
			if (weakReference.equals(essenceKind.getAUID())) {
				
				return vector.remove(weakReference);
			}
		
		return false;
	}
	
	public void clear() {
		
		vector.clear();
	}
	
	public int count() {
		
		return vector.size();
	}
	
	public long getPersistentID() {
		
		return persistentID;
	}
	
	public final static void generateWeakReferenceORM(
			Node parent,
			String namespace,
			String prefix) {
		
		Element entity = XMLBuilder.createChild(parent, namespace, prefix, "entity");
		XMLBuilder.setAttribute(entity, namespace, prefix, "class", WeakReferenceVector.class.getCanonicalName());
		XMLBuilder.setAttribute(entity, namespace, prefix, "access", "FIELD");
		
		Element attributes = XMLBuilder.createChild(entity, namespace, prefix, "attributes");
		
		Element persistentID = XMLBuilder.createChild(attributes, namespace, prefix, "id");
		XMLBuilder.setAttribute(persistentID, namespace, prefix, "name", "persistentID");
		
		XMLBuilder.createChild(persistentID, namespace, prefix, "generated-id");
		
		Element persistentIDColumn = XMLBuilder.createChild(persistentID, namespace, prefix, "column");
		XMLBuilder.setAttribute(persistentIDColumn, namespace, prefix, "name", "PersistentID");

		Element vector = XMLBuilder.createChild(attributes, namespace, prefix, "one-to-many");
		XMLBuilder.setAttribute(vector, namespace, prefix, "name", "vector");
		XMLBuilder.setAttribute(vector, namespace, prefix, "targetEntity", 
				WeakReference.class.getCanonicalName());
		
		XMLBuilder.appendElement(vector, namespace, prefix, "order-by", "persistentIndex ASC");
		
		XMLBuilder.createChild(vector, namespace, prefix, "cascade-all");
		
		Element vectorJoinColumn = XMLBuilder.createChild(vector, namespace, prefix, "join-column");
		XMLBuilder.setAttribute(vectorJoinColumn, namespace, prefix, "name", "WeakVectorID");
	}


}
