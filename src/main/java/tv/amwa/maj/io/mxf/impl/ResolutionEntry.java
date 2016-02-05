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

package tv.amwa.maj.io.mxf.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.impl.TypeDefinitionObjectReferenceImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionSetImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionVariableArrayImpl;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

public class ResolutionEntry {

	private PropertyDefinition property;
	private MetadataObject target;
	private PropertyValue unresolvedValue;

	public ResolutionEntry(
			PropertyDefinition property,
			MetadataObject target,
			PropertyValue unresolvedValue) {

		// TODO check for null values

		this.property = property;
		this.target = target;
		this.unresolvedValue = unresolvedValue;
	}

	public boolean resolve(
			Map<AUIDImpl, MetadataObject> referenceTable) {

//		if (property.getName().equals("PackageTracks"))
//			System.out.println("Resolving package track objects.");
//		System.out.println(property.getName());

		boolean resolutionComplete = true;
		PropertyValue resolvedReference;

		switch (property.getTypeDefinition().getTypeCategory()) {

		case WeakObjRef:
			resolvedReference = resolveReference(unresolvedValue, referenceTable);

			if (resolvedReference != null) {
				property.setPropertyValue(target, resolvedReference);
				return true;
			}

			return true;

		case StrongObjRef:
			resolvedReference = resolveReference(unresolvedValue, referenceTable);

			if (resolvedReference != null) {
				try {
					Field injectionPoint = findInjectionPoint(property);
					injectionPoint.setAccessible(true);
					injectionPoint.set(target, resolvedReference.getValue());
				}
				catch (Exception e) {
					System.err.println("Could not inject a value into property " + property.getName() + ".");
					property.setPropertyValue(target, resolvedReference);
				}
				return true;
			}

			System.err.println("Cannot resolve strong reference for property " + property.getName() + ".");
			return false;

		case Set:
			Set<PropertyValue> unresolvedValues =
				((TypeDefinitionSetImpl) unresolvedValue.getType()).getElements(unresolvedValue);
			Set<PropertyValue> resolvedValues = new HashSet<PropertyValue>(unresolvedValues.size());
			Set<Object> injectionSet = new HashSet<Object>(unresolvedValues.size());

			for ( PropertyValue element : unresolvedValues ) {

				resolvedReference = resolveReference(element, referenceTable);

				if (resolvedReference != null) {
					resolvedValues.add(resolvedReference);
					injectionSet.add(resolvedReference.getValue());
					continue;
				}

				System.err.println("Unable to resolve a set reference for property " + property.getName() + ".");
				resolutionComplete = false;
			}

			try {
				Field injectionPoint = findInjectionPoint(property);
				injectionPoint.setAccessible(true);
				injectionPoint.set(target, injectionSet);
			}
			catch (Exception e) {
				System.err.println("Could not inject a value into property " + property.getName() + ".");
				property.setPropertyValue(target, property.getTypeDefinition().createValue(resolvedValues));
			}

			return resolutionComplete;

		case VariableArray:
			List<PropertyValue> unresolvedListValues =
				((TypeDefinitionVariableArrayImpl) unresolvedValue.getType()).getElements(unresolvedValue);
			List<PropertyValue> resolvedListValues = new Vector<PropertyValue>(unresolvedListValues.size());
			List<Object> injectionList = new Vector<Object>(unresolvedListValues.size());

			for ( PropertyValue element : unresolvedListValues ) {

				resolvedReference = resolveReference(element, referenceTable);

				if (resolvedReference != null) {
					resolvedListValues.add(resolvedReference);
					injectionList.add(resolvedReference.getValue());
					continue;
				}

				System.err.println("Unable to resolve a variable array reference for property " + property.getName() + ".");
				resolutionComplete = false;
			}

			try {
				Field injectionPoint = findInjectionPoint(property);
				injectionPoint.setAccessible(true);
				injectionPoint.set(target, injectionList);
			}
			catch (Exception e) {
				System.err.println("Could not inject a value into property " + property.getName() + ".");
				property.setPropertyValue(
					target,
					property.getTypeDefinition().createValue(resolvedListValues));
			}
			return resolutionComplete;

		default:
			return true;
		}
	}

	private final static PropertyValue resolveReference(
			PropertyValue unresolvedReference,
			Map<AUIDImpl, MetadataObject> referenceTable) {

		if (unresolvedReference instanceof
				TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) {

			TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue unresolvedProperty =
				(TypeDefinitionObjectReferenceImpl.UnresolvedReferenceValue) unresolvedReference;
			AUID instanceID = unresolvedProperty.getValue();

			MetadataObject referencedBaseValue = referenceTable.get(instanceID);

			if (referencedBaseValue == null) {
				try {
					Class<?> baseClassImplementation =
						((TypeDefinitionObjectReferenceImpl) unresolvedProperty.getType()).getObjectType().getJavaImplementation();
					Method forIdentification = baseClassImplementation.getMethod("forIdentification", tv.amwa.maj.record.AUID.class);
					referencedBaseValue = (MetadataObject) forIdentification.invoke(null, instanceID);
				} catch (Exception e) {
					return null;
				}
			}

			if (referencedBaseValue == null) return null;

			PropertyValue referencedValue = unresolvedReference.getType().createValue(referencedBaseValue);

			return referencedValue;
		}

		return null;
	}

	private final static Field findInjectionPoint(
			PropertyDefinition property) {

		try {
			ClassDefinition memberOf = property.getMemberOf();
			Class<?> memberClass = memberOf.getJavaImplementation();
			return memberClass.getDeclaredField(
					Character.toLowerCase(property.getName().charAt(0)) + property.getName().substring(1));
		}
		catch (NoSuchFieldException nsfe) {
			return null;
		}
	}
}
