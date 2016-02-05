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
 * $Log: DataEssenceDescriptorImpl.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import org.w3c.dom.Node;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.model.ContainerDefinition;
import tv.amwa.maj.model.DataEssenceDescriptor;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


/** 
 * <p>Implements the description of a file of data essence and 
 * identifies the data coding of that essence. Data essence includes that specified in MXF
 * mappings for MPEG (SMPTE&nbsp;381M), DV (SMPTE&nbsp;383M), D10 and D11 (SMPTE&nbsp;386M).
 * Data essence often refers to time-varying data, such as subtitles (closed captions).</p>
 *
 *
 *
 */
@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x4300,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "DataEssenceDescriptor",
		  description = "Specifies that a file source package is associated with data essence.",
		  symbol = "DataEssenceDescriptor")
public class DataEssenceDescriptorImpl
	extends AAFFileDescriptorImpl
	implements DataEssenceDescriptor,
		Serializable,
		XMLSerializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 739654857146965078L;
	
	private AUID dataEssenceCoding = null;
	
	public DataEssenceDescriptorImpl() { }
	
	/**
	 * <p>Creates and initializes a new data essence descriptor, which specifies that a 
	 * {@link SourcePackageImpl file source package} is associated with data essence.</p>
	 *
	 * @param containerFormat Container format of the associated data essence.
	 * 
	 * @throws NullPointerException The container format argument is <code>null</code>.
	 */
	public DataEssenceDescriptorImpl(
			ContainerDefinition containerFormat) 
		throws NullPointerException {
		
		setContainerFormat(containerFormat);
	}
	
	@MediaProperty(uuid1 = 0x04030302, uuid2 = 0x0000, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x03},
			     definedName = "DataEssenceCoding",
			     typeName = "AUID",
			     optional = true,
			     uniqueIdentifier = false,
			     pid = 0x3E01,
			     symbol = "DataEssenceCoding")
	public AUID getDataEssenceCoding()
			throws PropertyNotPresentException {

		if (dataEssenceCoding == null)
			throw new PropertyNotPresentException("The optional data essence coding property is not present in this data essence descriptor.");
		
		return dataEssenceCoding.clone();
	}

	@MediaPropertySetter("DataEssenceCoding")
	public void setDataEssenceCoding(
			AUID dataEssenceCoding) {

		if (dataEssenceCoding == null)
			this.dataEssenceCoding = null;
		else
			this.dataEssenceCoding = dataEssenceCoding.clone();
	}

	@Override
	public DataEssenceDescriptor clone() {
		
		return (DataEssenceDescriptor) super.clone();
	}
	
	// TODO : What was going on here? "Add in default values for essence length and sample rate"
//	@Override
//	public void appendXMLChildren(
//			Node parent) {
//		
//		XMLBuilder.appendComment(parent, "Default values for essence length and sample rate included, even though static essence is described.");
//		XMLBuilder.appendElement(parent, CommonConstants.AAF_XML_NAMESPACE, 
//				CommonConstants.AAF_XML_PREFIX, "EssenceLength", "0");
//		XMLBuilder.appendElement(parent, CommonConstants.AAF_XML_NAMESPACE, 
//				CommonConstants.AAF_XML_PREFIX, "SampleRate", "0/0");
//	}
	
	public String getDataEssenceCodingString() {
		
		return AUIDImpl.toPersistentForm(dataEssenceCoding);
	}
	
	public void setDataEssenceCodingString(
			String dataEssenceCoding) {
		
		this.dataEssenceCoding = AUIDImpl.fromPersistentForm(dataEssenceCoding);
	}
}
