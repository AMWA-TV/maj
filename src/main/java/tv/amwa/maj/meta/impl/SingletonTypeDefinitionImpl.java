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
 * $Log: SingletonTypeDefinitionImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/03/19 16:13:53  vizigoth
 * Added methods for writing bytes and calculating lengths.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 */

package tv.amwa.maj.meta.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import tv.amwa.maj.industry.HiddenClass;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.PropertyValue;
import tv.amwa.maj.meta.PropertyDefinition;

/**
 * <p>Provides property value set and get methods for reflectively setting and getting values from
 * {@linkplain tv.amwa.maj.industry.MetadataObject metadata objects}.</p>
 * 
 * <p>This class provides no public methods.</p>
 * 
 *
 *
 */
@HiddenClass
public abstract class SingletonTypeDefinitionImpl 
	extends TypeDefinitionImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6038568307527042949L;

	public static class SingletonMethodBag
		extends MethodBag {
		
		private Method setter;
		
		public SingletonMethodBag(
				Method getter,
				Method[] candidateMethods,
				String propertyName) {
			
			super(getter, candidateMethods, propertyName);
			
			for ( Method setterFinder : candidateMethods ) {
				MediaPropertySetter setterAnnotation = setterFinder.getAnnotation(MediaPropertySetter.class);
				
				if (setterAnnotation != null) {
				
					if (setterAnnotation.value().equals(propertyName)) {
						this.setter = setterFinder;
						return;
					}
				}
			}
		}
		
		public void set(
				MetadataObject mdObject,
				Object value) 
			throws IllegalArgumentException, 
				IllegalAccessException, 
				InvocationTargetException {
		
			// FIXME problem setting blobs, second time around only!
			if (setter == null) {
				System.err.println("*** Found a null setter: " + getPropertyName());
				return;
			}
			// Avoid trying to set a primitive value with a null
			if ((setter.getParameterTypes()[0].isPrimitive()) && (value == null)) return;
			setter.invoke(mdObject, value);
		}
		
		public String getSetterName() {
			
			if (setter == null) return null;
			else return setter.getName();
		}
	}

	@Override
	public void setPropertyValue(
			MetadataObject metadataObject,
			PropertyDefinition property, 
			PropertyValue value) 
		throws IllegalArgumentException, 
			IllegalAccessException, 
			InvocationTargetException {
		
		SingletonMethodBag methods = (SingletonMethodBag) ((PropertyDefinitionImpl) property).getMethodBag();
		methods.set(metadataObject, value.getValue());
	}

	@Override
	MethodBag makeMethodBag(
			Method getter,
			Method[] candidateMethods,
			String propertyName) {
		
		return new SingletonMethodBag(getter, candidateMethods, propertyName);
	}
}
