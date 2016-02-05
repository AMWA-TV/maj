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
 * $Log: WeakReference.java,v $
 * Revision 1.10  2011/10/05 17:14:25  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.9  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.8  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.7  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2010/04/13 10:11:03  vizigoth
 * Added a TODO on improving exception handling.
 *
 * Revision 1.5  2010/04/13 07:22:08  vizigoth
 * Using Long rather than long for persistent IDs and setting to null as a default.
 *
 * Revision 1.4  2010/03/19 10:01:52  vizigoth
 * Added support for lazy evaluation of weak references when read from a stream.
 *
 * Revision 1.3  2010/03/01 15:17:22  vizigoth
 * Added a generic table for weak reference resolution. Helps with auto generated weak reference targets.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 *
 */

package tv.amwa.maj.industry;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.TypeDefinitionWeakObjectReference;
import tv.amwa.maj.model.DefinitionObject;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

/**
 * <p>Represents and provides a means to resolve a weak references that are stored 
 * as a field of an implementing class. Weak references can be safely persisted 
 * through object relational mapping and will resolve themselves at runtime as required.</p>
 * 
 *
 *
 * @param <T> Type of the target of the weak reference.
 * 
 * @see WeakReferenceTarget
 * @see TypeDefinitionWeakObjectReference
 * @see WeakReferenceSet
 * @see WeakReferenceVector
 */
public class WeakReference<T extends WeakReferenceTarget> 
	implements MediaEntity,
		Cloneable {

	private static Map<AUID, WeakReferenceTarget> genericTable = 
		Collections.synchronizedMap(new HashMap<AUID, WeakReferenceTarget>());
	
	private AUID identifier;
	private String canonicalTypeName;
	private long persistentID = 0l;
	@SuppressWarnings("unused")
	private int persistentIndex = 0;
	private T cachedValue = null;
	
	public WeakReference() { }
	
	public WeakReference(
			T target) {
		
		setTarget(target);
	}
	
	public WeakReference(
			Class<T> targetType,
			AUID targetIdentifier) {
		
		this.canonicalTypeName = targetType.getCanonicalName();
		this.identifier = targetIdentifier.clone();
	}
	
	@SuppressWarnings("unchecked")
	public T getTarget() { // TODO improve exception handling
		
		if (cachedValue != null)
			return cachedValue;
		
		if (canonicalTypeName.equals("tv.amwa.maj.meta.impl.TypeDefinitionImpl")) {
			cachedValue = (T) Warehouse.lookForType(identifier);
			if (cachedValue != null) return cachedValue;
		}
		ClassDefinition targetType = Warehouse.lookForClass(canonicalTypeName);
		try {
			Method staticResolver = targetType.getJavaImplementation().getMethod(
					"forAUID", AUID.class);
			cachedValue = (T) staticResolver.invoke(null, identifier);
			if (cachedValue != null) return cachedValue;
		}
		catch (Exception e) {
			// Method is not found ... try the generic resolver
		}
		
		try {
			return (T) genericTable.get(identifier);
		}
		catch (ClassCastException cce) {
			return null;
		}
	}
	
	public void setTarget(
			T target) {
		
		this.identifier = target.getAUID();
		canonicalTypeName = target.getClass().getCanonicalName();
		cachedValue = target;
	}
	
	public Long getPersistentID() {

		return persistentID;
	}
	
	public void setPersistentIndex(
			int index) {

		this.persistentIndex = index;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (o instanceof AUID)
			return this.identifier.equals(o);
		
		if (o instanceof WeakReferenceTarget)
			return this.identifier.equals(((WeakReferenceTarget) o).getAUID());
		
		if (o instanceof WeakReference)
			return getTarget().equals(((WeakReference<T>) o).getTarget());
		
		return false;
	}
	
	public int hashCode() {
		
		return identifier.hashCode();
	}
	
	public String toString() {
		
		return getTarget().toString();
	}
	
	@SuppressWarnings("unchecked")
	public WeakReference<T> clone() {
		
		try {
			return (WeakReference<T>) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Should never get here
			throw new InternalError(cnse.getMessage());
		}
	}
	
	public final static void registerTarget(
			WeakReferenceTarget target) 
		throws NullPointerException {
		
		if (target == null)
			throw new NullPointerException("Cannot register a weak reference target using a null value.");
		
		try {
			genericTable.put(target.getAUID(), target);
			if (target instanceof DefinitionObject)
				Warehouse.register((DefinitionObject) target);
		}
		catch (NullPointerException npe) { /* Assume still initializing. */ }
	}
	
	public final static void forgetTarget(
			WeakReferenceTarget target) {
		
		if (target == null) return;
		
		genericTable.remove(target.getAUID());
	}
	
	public final static void generateWeakReferenceORM(
			Node parent,
			String namespace,
			String prefix) {
		
		Element entity = XMLBuilder.createChild(parent, namespace, prefix, "entity");
		XMLBuilder.setAttribute(entity, namespace, prefix, "class", WeakReference.class.getCanonicalName());
		XMLBuilder.setAttribute(entity, namespace, prefix, "access", "FIELD");
		
		Element attributes = XMLBuilder.createChild(entity, namespace, prefix, "attributes");
		
		Element identifier = XMLBuilder.createChild(attributes, namespace, prefix, "embeddable");
		XMLBuilder.setAttribute(identifier, namespace, prefix, "name", "identifier");
		
		Element identifierOverride = XMLBuilder.createChild(identifier, namespace, prefix, "attribute-override");
		XMLBuilder.setAttribute(identifierOverride, namespace, prefix, "name", "auidValue");
		
		Element identifierColumn = XMLBuilder.createChild(identifierOverride, namespace, prefix, "column");
		XMLBuilder.setAttribute(identifierColumn, namespace, prefix, "name", "Identifier");
		XMLBuilder.setAttribute(identifierColumn, namespace, prefix, "nullable", "false");
		
		Element canonicalTypeName = XMLBuilder.createChild(attributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(canonicalTypeName, namespace, prefix, "name", "canonicalTypeName");
		
		Element typeNameColumn = XMLBuilder.createChild(canonicalTypeName, namespace, prefix, "column");
		XMLBuilder.setAttribute(typeNameColumn, namespace, prefix, "name", "TypeName");
		XMLBuilder.setAttribute(typeNameColumn, namespace, prefix, "nullable", "false");
		
		Element persistentID = XMLBuilder.createChild(attributes, namespace, prefix, "id");
		XMLBuilder.setAttribute(persistentID, namespace, prefix, "name", "persistentID");
		
		XMLBuilder.createChild(persistentID, namespace, prefix, "generated-id");
		
		Element persistentIDColumn = XMLBuilder.createChild(persistentID, namespace, prefix, "column");
		XMLBuilder.setAttribute(persistentIDColumn, namespace, prefix, "name", "PersistentID");

		Element persitentIndex = XMLBuilder.createChild(attributes, namespace, prefix, "basic");
		XMLBuilder.setAttribute(persitentIndex, namespace, prefix, "name", "persistentIndex");
		
		Element indexColumn = XMLBuilder.createChild(persitentIndex, namespace, prefix, "column");
		XMLBuilder.setAttribute(indexColumn, namespace, prefix, "name", "PersistentIndex");
		XMLBuilder.setAttribute(indexColumn, namespace, prefix, "nullable", "false");
		
		Element transientCache = XMLBuilder.createChild(attributes, namespace, prefix, "transient");
		XMLBuilder.setAttribute(transientCache, namespace, prefix, "name", "cachedValue");
	}
	
	public String getIdentifierString() {
		
		return AUIDImpl.toPersistentForm(identifier);
	}
	
	public void setIdentifierString(
			String identifier) {
		
		this.identifier = AUIDImpl.fromPersistentForm(identifier);
	}
	
	public AUID getIdentifier() {
		
		return identifier.clone();
	}
}
