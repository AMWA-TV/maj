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
 * $Log: WeakReferenceTarget.java,v $
 * Revision 1.5  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.3  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 */

package tv.amwa.maj.industry;

import tv.amwa.maj.record.AUID;

/**
 * <p>Specifies that a {@linkplain tv.amwa.maj.industry.MetadataObject metadata object} can be the target of a
 * {@linkplain WeakReference weak reference}. Any object implementing 
 * this interface may be the target of more than one weak reference and is not owned by the source of the reference.</p>
 * 
 * <p>The value returned by the {@link #getWeakTargetReference()} method 
 * is used by IO operations such as XML input and output. The unique identification for the target
 * of the reference returned by {@link #getAUID()} is used for creating and resolving
 * {@link WeakReference weak references}.</p> 
 * 
 * <p>In MAJ, resolution of references can be normally be achieved by using methods in the 
 * <a href="../industry/package-summary.html">industry package</a>. For example, 
 * {@link tv.amwa.maj.industry.Warehouse#lookForType(String)} and
 * {@link tv.amwa.maj.industry.Warehouse#lookup(Class, String)}.</p>
 * 
 *
 * 
 * @see WeakReference
 * @see WeakReference#getTarget()
 * @see tv.amwa.maj.meta.TypeDefinitionWeakObjectReference
 * @see tv.amwa.maj.industry.Warehouse#lookForType(String)
 * @see tv.amwa.maj.industry.Warehouse#lookForClass(String)
 */

public interface WeakReferenceTarget {

	/**
	 * <p>Provides a name that can be used to make a weak object reference to an
	 * instance of the implementing class.</p>
	 * 
	 * @return A name to use to make weak reference to this object.  
	 */
	public String getWeakTargetReference();
	
	/**
	 * <p>Returns the unique identifier of the target of the reference.</p>
	 * 
	 * @return Unique identifier of the target of the reference.
	 */
	public AUID getAUID();
}
