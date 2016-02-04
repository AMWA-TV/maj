/*
 * Copyright 2016 Advanced Media Workflow Assocation
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
 * $Log: MetaDefinitionImpl.java,v $
 * Revision 1.5  2011/07/27 17:36:16  vizigoth
 * Added namespace handling to the generation of meta dictionary XML.
 *
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/20 15:52:54  vizigoth
 * Fixed up all meta tests to the point where they all pass.
 *
 * Revision 1.2  2011/01/19 21:37:53  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/11/18 10:49:53  vizigoth
 * Added support for dynamic meta dictionaries and type name mapping for legacy meta dictionary compatibility.
 *
 * Revision 1.2  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:02  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2009/02/24 18:49:22  vizigoth
 * Major refactor to move all XML-specific code out of the implementing classes and drive all IO operations through Java reflection.
 *
 * Revision 1.3  2008/10/15 16:26:15  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.2  2007/12/04 13:04:53  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:13:19  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:23  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.meta.impl;

import java.io.Serializable;

import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.industry.WeakReferenceTarget;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.model.impl.DefinitionType;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/**
 * <p>Implements the definition of a class, type, or property in an AAF file.</p>
 * 
 * <p>Extension of the meta model with external classes is not encouraged. The metamodel can be used
 * to describe extensions in the main AAF object model. Therefore, the registration of classes is 
 * restricted to this package only.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0224, uuid3 = 0x0000,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "MetaDefinition",
		  description = "The MetaDefinition class is an abstract class that defines a class, type, or property in an AAF file.",
		  symbol = "MetaDefinition",
		  namespace = CommonConstants.AAF_XML_NAMESPACE,
		  prefix = CommonConstants.AAF_XML_PREFIX)
public abstract class MetaDefinitionImpl 
	implements 
		MetaDefinition,
		DefinitionType,
		WeakReferenceTarget,
		Serializable,
		CommonConstants,
		Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 613837427379296528L;
	
	/** Specifies the unique identifier for the item being defined. */
	private AUID identification;
	/** Specifies the display name of the item being defined. */
	private String name = null;
	/** Provides an explanation of the use of the item being defined. */
	private String description = null;
	/** Symbol name for this metadefinition. */
	String symbol;
	/** Namespace for this metadefinition. */
	String namespace = null;
	/** Namespace abbreviation for this metadefinition. */
	String prefix = null;
	/** Aliases that may also identify this definition. */
	private String[] aliases;	
	/** AAF-style type names are in use. */
	private boolean aafNamesInUse = false;
	
	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MetaDefinitionIdentification",
			aliases = { "Identification", "AUID" },
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = true,
			pid = 0x0005,
			symbol = "MetaDefinitionIdentification")
	public final AUID getAUID() {

		return identification.clone();
	}
	
	public AUID getIdentification() {
		
		return identification.clone();
	}
	
	public final static AUID initializeMetaDefinitionIdentification() {
		
		return AUIDImpl.randomAUID();
	}
	
	@MediaPropertySetter("MetaDefinitionIdentification")
	public final void setIdentification(
			AUID identification) 
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot set the identity of a meta definition with a null id.");
		
		this.identification = identification.clone();
	}

	@MediaProperty(uuid1 = 0x06010107, uuid2 = (short) 0x1401, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MetaDefinitionDescription",
			aliases = { "Description" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x0007,
			symbol = "MetaDefinitionDescription")
	public final String getDescription() 
		throws PropertyNotPresentException {

		if (description == null)
			throw new PropertyNotPresentException("The optional description property is not present for this meta definition.");
		
		return description;
	}

	@MediaPropertySetter("MetaDefinitionDescription")
	public final void setDescription(
			String description)  {

		this.description = description;
	}

	@MediaProperty(uuid1 = 0x03020401, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "MetaDefinitionName",
			aliases = { "Name" },
			typeName = "UTF16String",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0006,
			symbol = "MetaDefinitionName")
	public final String getName()  {
		
		return (aafNamesInUse) ? nameToAAFName(name) : name;
	}
	
	@MediaPropertySetter("MetaDefinitionName")
	public final void setName(
			String name) 
		throws NullPointerException {

		if (name == null)
			throw new NullPointerException("Cannoe set the name of a meta definition name with a null string value.");
		
		this.name = (aafNamesInUse) ? aafNameToName(name) : name;
	}

	private static int nameCount = 1;
	
	public final static String initializeMetaDefinitionName() {
		
		return "DefaultMetaDefinitionName" + (nameCount++);
	}
	
	/**
	 * <p>Create an XML symbol name from the name of this definition. All non-word characters
	 * are replaced by underbar characters "<code>_</code>".</p>
	 *
	 * @return Symbol name created from this definition's name.
	 */
	String getSymbolName() {
		
		return name.replaceAll("\\W", "_");
	}
	
	public final boolean equals(
			Object o) {
		
		if (o == null) return false;

		if (o instanceof WeakReference<?>) {
			o = ((WeakReference<?>) o).getTarget();
		}
		
		if (!(o instanceof MetaDefinition)) return false;
		return identification.equals(((MetaDefinition) o).getAUID());
	}
	
	public boolean deepEquals(
			Object o) {
		
		return MediaEngine.deepEquals(this, o);
	}
	
	public final int hashCode() {
		
		return identification.hashCode();
	}
	
	public String toString() {
		
		return MediaEngine.toString(this);
	}
	
	public void appendXMLChildren(
			Node parent) { }
	
	public String getComment() {
		
		return null;
	}
	
	public String getSymbol() {
		
		if (symbol == null)
			return name;
		else
			return symbol;
	}
	
	public void setSymbol(
			String symbol) 
		throws NullPointerException {
		
		if (symbol == null)
			throw new NullPointerException("Cannot set a meta definition symbol to null.");
		
		this.symbol = symbol;
	}
	
	public String getWeakTargetReference() {
		
		return getName();
	}
	
	public String getNamespace() {
		
		return "unknown";
	}
	
	public void setNamespace(
			String namespace) {
		
		this.namespace = namespace;
	}
	
	public String getPrefix() {
		
		return "unknown";
	}
	
	public void setPrefix(
			String prefix) {
		
		this.prefix = prefix;
	}
	
	public void setAliases(
			String[] aliases) {
	
		if (aliases == null)
			this.aliases = new String[] { };
		else
			this.aliases = aliases;
	}
	
	public String[] getAliases() {
		
		return aliases;
	}
	
	public boolean getAAFNamesInUse() {
		
		return aafNamesInUse;
	}
	
	public void setAAFNamesInUse(
			boolean aafNamesInUse) {
		
		this.aafNamesInUse = aafNamesInUse;
	}
	
	public String nameToAAFName(
			String name) {
		
		return name;
	}
	
	public String aafNameToName(
			String name) {
		
		return name;
	}
	
	public void appendMetadictXML(
			Node metadict,
			String namespace,
			String prefix) {
		
		XMLBuilder.appendElement(metadict, namespace, prefix, "Symbol", getSymbol());
		XMLBuilder.appendElement(metadict, namespace, prefix, "Name", name);
		XMLBuilder.appendElement(metadict, namespace, prefix, "Identification", identification.toString());
		XMLBuilder.appendElement(metadict, namespace, prefix, "Description", 
				(description != null) ? description : "");
	}
	
	public MetaDefinition clone() {
		
		try {
			return (MetaDefinition) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Should never get here
			throw new InternalError(cnse.getMessage());
		}
	}
}
