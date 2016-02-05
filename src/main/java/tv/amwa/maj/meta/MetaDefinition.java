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
 * $Log: MetaDefinition.java,v $
 * Revision 1.10  2011/10/05 17:14:29  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.9  2011/07/27 17:10:00  vizigoth
 * Made the generation of meta dictionary XML namespace aware.
 *
 * Revision 1.8  2011/02/14 22:32:50  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.7  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/11/18 10:45:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.6  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/02/06 17:03:20  vizigoth
 * Added new super-interface "MetadataObject".
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/29 18:11:22  vizigoth
 * Updated documentation for newly added classes to 1.1.2 and associated fixes.
 *
 * Revision 1.1  2007/11/13 22:08:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.meta;

import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;


/**
 * <p>Specifies the definition of a class, type, or property in an AAF file.</p>
 * 
 *
 *
 */

public interface MetaDefinition 
	extends MetadataObject,
		WeakReferenceTarget {

	/**
	 * <p>Returns the identification for this meta definition.</p>
	 * 
	 * @return Identification for this meta definition.
	 */
	public AUID getAUID();

	/**
	 * <p>Sets the name of this meta definition.</p>
	 * 
	 * @param name Name to set for this meta definition.
	 * 
	 * @throws NullPointerException The given meta definition name is <code>null</code>.
	 * 
	 * @see #getAAFNamesInUse()
	 * @see #setAAFNamesInUse(boolean)
	 */
	public void setName(
			@AAFString String name) 
		throws NullPointerException;

	/**
	 * <p>Returns the name of this meta definition.</p>
	 * 
	 * @return Name of the meta definition.
	 * 
	 * @see #getAAFNamesInUse()
	 * @see #setAAFNamesInUse(boolean)
	 */
	public @AAFString String getName();

	/**
	 * <p>Sets the description of this meta definition. Set this optional property to
	 * <code>null</code> to omit it.</p>
	 * 
	 * @param description Description of this meta definition.
	 */
	public void setDescription(
			@AAFString String description);

	/**
	 * <p>Returns the description of this meta definition. This is an optional
	 * property.</p>
	 * 
	 * @return Description of this meta definition.
	 * 
	 * @throws PropertyNotPresentException The optional description property is not
	 * present in this meta definition.
	 */
	public @AAFString String getDescription()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the namespace in which the subject of this meta definition is defined.</p>
	 * 
	 * @return Namespace in which the subject of this meta definition is defined.
	 * 
	 * @see MediaProperty#namespace()
	 * @see MediaClass#namespace()
	 * @see ExtensionScheme#getSchemeURI()
	 */
	public String getNamespace();
	
	/**
	 * <p>Sets the namespace in which the subject of this meta definition is defined.</p>
	 * 
	 * @param namespace Namespace in which the subject of this meta definition is defined.
	 * 
	 * @see MediaProperty#namespace()
	 * @see MediaClass#namespace()
	 */
	public void setNamespace(
			String namespace);
	
	/**
	 * <p>Returns alternative names for the subject of this meta definition. These
	 * names are alternative names to the {@linkplain #getName() defined name} for
	 * the definition.</p>
	 * 
	 * @return Alternative names for the subject defined by this meta definition.
	 * 
	 * @see MediaProperty#aliases()
	 * @see MediaClass#aliases()
	 */
	public String[] getAliases();
	
	/**
	 * <p>Sets alternative names for the subject of this meta definition. These
	 * names are alternative names to the {@linkplain #getName() defined name} for
	 * the subject of this definition.</p>
	 * 
	 * @param aliases Alternative names for the subject defined by this meta definition.
	 * 
	 * @see MediaProperty#aliases()
	 * @see MediaClass#aliases()
	 */
//	public void setAliases(
//			String[] aliases);
	
	/**
	 * <p>Returns the namespace prefix to use when serializing a value of the subject of this meta
	 * definition to XML.</p>
	 * 
	 * @return Namespace prefix to use when serializing a value of the subject of this meta
	 * definition to XML.
	 * 
	 * @see MediaProperty#prefix()
	 * @see MediaClass#prefix()
	 */
	public String getPrefix();
	
	/**
	 * <p>Sets the namespace prefix to use when serializing a value of the subject of this meta
	 * definition to XML.</p>
	 * 
	 * @return Namespace prefix to use when serializing a value of the subject of this meta
	 * definition to XML.
	 * 
	 * @see MediaProperty#prefix()
	 * @see MediaClass#prefix()
	 */
	public void setPrefix(
			String prefix);

	/**
	 * <p>Returns the symbol used when serializing a value of the subject of this meta
	 * definition, such as its XML element or attribute name.</p>
	 * 
	 * @return Symbol used when serializing a value of the subject of this meta
	 * definition.
	 * 
	 * @see MediaProperty#symbol()
	 * @see MediaClass#symbol()
	 * @see tv.amwa.maj.util.Utilities#makeSymbol(String)
	 */
	public String getSymbol();
	
	/**
	 * <p>Sets the symbol used when serializing a value of the subject of this meta
	 * definition, such as its XML element or attribute name.</p>
	 * 
	 * @return Symbol used when serializing a value of the subject of this meta
	 * definition.
	 * 
	 * @throws NullPointerException Cannot set the symbol of a meta definition
	 * to <code>null</code>.
	 * 
	 * @see MediaProperty#symbol()
	 * @see MediaClass#symbol()
	 */
	public void setSymbol(
			String symbol)
		throws NullPointerException;
	
	
	/**
	 * <p>Are AAF-style names are in use for this meta definition? The default is <code>false</code> that
	 * is to use pass through names that match those defined in the meta dictionary. AAF names
	 * have slightly different values, for example:</p>
	 * 
	 * <ul>
	 *  <li><code>aafInt32</code> instead of <code>Int32</code>;</li>
	 *  <li><code>kAAFTypeID_LocatorStrongReference</code> instead of <code>LocatorStrongReference</code>.</li>
	 * </ul>
	 * 
	 * <p>These names are useful when writing meta dictionaries into AAF files. The internal mappings inside
	 * the class deal with mapping to and from the different names so that @link #getName()} and 
	 * {@link #setName(String)} always match.</p>
	 * 
	 * @return Are AAF-style names in use?
	 * 
	 * @see #setAAFNamesInUse(boolean)
	 * @see #getName()
	 * @see #setName(String)
	 */
	public boolean getAAFNamesInUse();
	
	/**
	 * <p>Set whether AAF-style names are in use for this meta definition. The default is <code>false</code> that
	 * is to use pass through names that match those defined in the meta dictionary. AAF names
	 * have slightly different values, for example:</p>
	 * 
	 * <ul>
	 *  <li><code>aafInt32</code> instead of <code>Int32</code>;</li>
	 *  <li><code>kAAFTypeID_LocatorStrongReference</code> instead of <code>LocatorStrongReference</code>.</li>
	 * </ul>
	 * 
	 * <p>These names are useful when writing meta dictionaries into AAF files. The internal mappings inside
	 * the class deal with mapping to and from the different names so that {@link #getName()} and 
	 * {@link #setName(String)} always match.</p>
	 * 
	 * @param aafNames Set whether AAF-style names are in use.
	 * 
	 * @see #getAAFNamesInUse()
	 * @see #getName()
	 * @see #setName(String)
	 */
	public void setAAFNamesInUse(
			boolean aafNames);
	
	/**
	 * <p>Determines whether this meta definition is equal to the given meta
	 * definition by comparing identifiers. Identities are only compared if the
	 * given object is not <code>null</code> and implements this interface.</p>
	 * 
	 * @param o Object to check equality against.
	 * 
	 * @return Is this meta definition is equal to the given meta
	 * definition by identification?
	 * 
	 * @see #deepEquals(Object)
	 * @see Object#equals(Object)
	 */
	public boolean equals(
			Object o);
	
	/**
	 * <p>Determines whether this definition is equal to the given object by comparing each 
	 * property. The {@link #equals(Object)} method only checks that the identities of two
	 * definitions are equal, relying on the uniqueness ensured by identity registration.
	 * This method is provided for debugging and validation purposes.</p>
	 *
	 * @param o Object to compare to this meta definition.
	 * 
	 * @return Is the given object a meta definition with all of its properties equal
	 * to this meta definition?
	 * 
	 * @see #equals(Object)
	 */
	public boolean deepEquals(
			Object o);
	
	/**
	 * <p>Calculates a hash code value for this meta definition, which is calculated
	 * from its identity.</p>
	 * 
	 * @return Hash code value for this meta definition
	 * 
	 * @see Object#hashCode()
	 */
	public int hashCode();
	
	/**
	 * <p>Creates a string representation of this meta definition. In the MAJ API, the
	 * string representation is an XML representation that should validate against
	 * SMPTE Reg-XML (SMPTE 2001).</p>
	 * 
	 * @return String representation of this meta definition.
	 * 
	 * @see MediaEngine#toString()
	 * @see Object#toString()
	 */
	public String toString();
	
	/**
	 * <p>Append an XML representation of this meta definition to the given XML node.</p>
	 * 
	 * @param metadict Meta dictionary node to append a description of this node to.
	 * 
	 * @see CommonConstants#AAF_METADICT_NAMESPACE
	 */
	public void appendMetadictXML(
			Node metadict,
			String namesapce,
			String prefix);
	
	
	/**
	 * <p>Create a cloned copy of this meta definition.</p>
	 * 
	 * @return Cloned copy of this meta definition.
	 */
	public MetaDefinition clone();
}

