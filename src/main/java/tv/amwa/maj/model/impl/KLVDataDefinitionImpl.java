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
 * $Log: KLVDataDefinitionImpl.java,v $
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/06/18 16:48:08  vizigoth
 * Fixed naming issue that caused issues with the media engine setting values.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2008/01/27 11:14:41  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.1  2007/11/13 22:09:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.TypeDefinitions;
import tv.amwa.maj.industry.WeakReference;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.model.KLVDataDefinition;
import tv.amwa.maj.record.AUID;

/** 
 * <p>Implements a representation of the documentation for the 
 * {@linkplain tv.amwa.maj.model.KLVData KLV data} objects 
 * used in a file.</p>
 *
 *
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#KLVDataDefinitionStrongReferenceSet
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x4d00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "KLVDataDefinition",
		  description = "The KLVDataDefinition class documents the KLVData objects used in the file.",
		  symbol = "KLVDataDefinition")
public class KLVDataDefinitionImpl
	extends 
		DefinitionObjectImpl
	implements
		KLVDataDefinition,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 8558817010222950307L;
	
	private WeakReference<TypeDefinition> kLVDataType = null;
	
	public KLVDataDefinitionImpl() { }

	/**
	 * <p>Creates and initializes a new KLV data definition, which documents 
	 * the {@link KLVDataImpl} objects used in the current persistent unit.</p>
	 *
	 * @param identification Unique identifier for the KLV data definition.
	 * @param name Display name for the KLV data definition.
	 * 
	 * @throws NullPointerException One or both of the arguments is <code>null</code>.
	 */
	public KLVDataDefinitionImpl(
			AUID identification,
			@AAFString String name)
		throws NullPointerException {
		
		if (identification == null)
			throw new NullPointerException("Cannot create a KLV data definition with a null id.");
		
		setName(name);
		setAUID(identification);
	}
	
	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0109, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x07},
			definedName = "KLVDataType",
			typeName = "TypeDefinitionWeakReference",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x4D12,
			symbol = "KLVDataType")
	public TypeDefinition getKLVDataType() {

		if (kLVDataType == null)
			return TypeDefinitions.UInt8Array;
		else
			return kLVDataType.getTarget();
	}

	@MediaPropertySetter("KLVDataType")
	public void setKLVDataType(
			TypeDefinition typeDefinition) {

		if (typeDefinition == null)
			this.kLVDataType = null;
		else
			kLVDataType = new WeakReference<TypeDefinition>(typeDefinition);
	}

	public KLVDataDefinition clone() {
		
		return (KLVDataDefinition) super.clone();
	}
}
