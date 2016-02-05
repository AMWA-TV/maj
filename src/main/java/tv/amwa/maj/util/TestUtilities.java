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

package tv.amwa.maj.util;

import java.lang.reflect.Method;
import java.util.Arrays;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.record.AUID;

public class TestUtilities {

	public static Method propertyGetMethod(
			Class<? extends MetadataObject> mediaClass,
			String propertyName) {

		for ( Method method : mediaClass.getMethods() ) {
			MediaProperty property = method.getAnnotation(MediaProperty.class);
			if ((property != null) && (property.definedName().equals(propertyName)))
				return method;
		}
		return null;
	}

	public static Method propertyGetMethod(
			Class<? extends MetadataObject> mediaClass,
			AUID propertyID) {

		for ( Method method : mediaClass.getMethods() ) {
			MediaProperty property = method.getAnnotation(MediaProperty.class);
			if (property != null) {
				if (property.uuid1() != propertyID.getData1()) continue;
				if (property.uuid2() != propertyID.getData2()) continue;
				if (property.uuid3() != propertyID.getData3()) continue;
				if (!Arrays.equals(property.uuid4(), propertyID.getData4())) continue;
				return method;
			}
		}
		return null;
	}

	public static MediaProperty propertyAnnotation(
			Class<? extends MetadataObject> mediaClass,
			String propertyName) {

		for ( Method method : mediaClass.getMethods() ) {
			MediaProperty property = method.getAnnotation(MediaProperty.class);
			if ((property != null) && (property.definedName().equals(propertyName)))
				return property;
		}
		return null;
	}

	public static MediaProperty propertyAnnotation(
			Class<? extends MetadataObject> mediaClass,
			AUID propertyID) {

		for ( Method method : mediaClass.getMethods() ) {
			MediaProperty property = method.getAnnotation(MediaProperty.class);
			if (property != null) {
				if (property.uuid1() != propertyID.getData1()) continue;
				if (property.uuid2() != propertyID.getData2()) continue;
				if (property.uuid3() != propertyID.getData3()) continue;
				if (!Arrays.equals(property.uuid4(), propertyID.getData4())) continue;
				return property;
			}
		}
		return null;
	}

	public final static boolean objectEqualityTest(
			Object o1,
			Object o2) {

		if ((o1 instanceof ByteOrder) && (o2 instanceof Short)) {
			return (((ByteOrder) o1).getAAFByteOrderCode() == (Short) o2);
		}

		if ((o1.getClass().isArray()) && (o2.getClass().isArray())) {
			Class<?> elementType1 = o1.getClass().getComponentType();
			Class<?> elementType2 = o2.getClass().getComponentType();

			if ((elementType1.isPrimitive()) && (elementType2.isPrimitive())) {
				if (!elementType1.equals(elementType2)) return false;

				if (elementType1.equals(Byte.TYPE))
					return Arrays.equals((byte[]) o1, (byte[]) o2);
				if (elementType1.equals(Short.TYPE))
					return Arrays.equals((short[]) o1, (short[]) o2);
				if (elementType1.equals(Integer.TYPE))
					return Arrays.equals((int[]) o1, (int[]) o2);
				if (elementType1.equals(Long.TYPE))
					return Arrays.equals((long[]) o1, (long[]) o2);
				if (elementType1.equals(Boolean.TYPE))
					return Arrays.equals((boolean[]) o1, (boolean[]) o2);
				if (elementType1.equals(Float.TYPE))
					return Arrays.equals((float[]) o1, (float[]) o2);
				if (elementType1.equals(Double.TYPE))
					return Arrays.equals((double[]) o1, (double[]) o2);
				if (elementType1.equals(Character.TYPE))
					return Arrays.equals((char[]) o1, (char[]) o2);
			}

			if ((!elementType1.isPrimitive()) || (!elementType2.isPrimitive())) {

				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
		}

		return o1.equals(o2);
	}
}
