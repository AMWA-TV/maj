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
 * $Log: RGBAComponentImpl.java,v $
 * Revision 1.3  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/01/19 14:38:35  vizigoth
 * Moved parsing of record type from byte buffers done in a non standard way to creatFromBuffer factory methods in the implementation itself.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/10/15 09:45:54  vizigoth
 * Edited documentation to release standard.
 *
 * Revision 1.3  2008/01/15 12:27:31  vizigoth
 * Updated due to refactoring of element names in the RGBAComponentKind enumeration.
 *
 * Revision 1.2  2008/01/14 20:45:24  vizigoth
 * Updated reference to RGBAComponent enumeration ... change of CompNull to Null.
 *
 * Revision 1.1  2007/11/13 22:14:38  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.RGBAComponentKind;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.io.xml.LocalHandler;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.record.RGBAComponent;

/**
 * <p>Implements an element of an array representing the order and size of the component 
 * values within a pixel value as part of an {@linkplain tv.amwa.maj.misctype.RGBALayout RGBA Layout}. 
 * The RGB layout type type is a fixed-size 8&nbsp;element array, where each element consists of an 
 * RGBA component value. Each RGBA component has with the following fields:</p>
 * 
 * <ul>
 * <li><code>code</code> - {@linkplain tv.amwa.maj.enumeration.RGBAComponentKind RGBA component kind enumerated value} 
 * specifying the component kind.</li>
 * <li><code>size</code> - Java byte specifying the number of bits.</li>
 * </ul>
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#RGBAComponent
 * @see tv.amwa.maj.model.RGBADescriptor
 * @see tv.amwa.maj.enumeration.RGBAComponentKind
 * @see tv.amwa.maj.misctype.RGBALayout
 *
 *
 *
 */
public final class RGBAComponentImpl
	implements RGBAComponent,
		Serializable,
		XMLSerializable,
		Cloneable,
		CommonConstants {

	/**  */
	private static final long serialVersionUID = -1997649975507845926L;
	
	/** Enumerated value specifying component kind. */
	private RGBAComponentKind code;
	/** Size of the component in bits. */
	@UInt8 private byte componentSize;
	
	private long persistentId;

	/**
	 * <p>Create a new RGBA component with a {@link RGBAComponentKind#Null} code and
	 * a size of <code>0</code>.</p>
	 * 
	 * <p>This constructor is public to allow the 
	 * {@link TypeDefinitionRecord#getObject(tv.amwa.maj.model.PropertyValue, Class)}
	 * method to create new instances of objects from record property values.</p>
	 */
	public RGBAComponentImpl() { 
		code = RGBAComponentKind.Null;
		componentSize = (byte) 0;
	}
	
    /**
     * <p>Create an element of an RGBAComponent array.</p>
	 * 
	 * @param code Code for the component.
	 * @param size Size of the component in bits.
	 * 
	 * @throws NullPointerException The code argument is <code>null</code>.
	 * @throws IllegalArgumentException The size argument is negative.
	 */
	public RGBAComponentImpl(
			RGBAComponentKind code, 
			@UInt8 byte size) 
		throws NullPointerException,
			IllegalArgumentException {
		
		setComponentSize(size);
		setCode(code);
	}

	public final RGBAComponentKind getCode() {
		return code;
	}

	public final void setCode(RGBAComponentKind code) 
		throws NullPointerException {

		if (code == null)
			throw new NullPointerException("Cannot set the component kind of this RGBA component with a null value.");
		
		this.code = code;
	}

	public final @UInt8 byte getComponentSize() {
		return componentSize;
	}

	public final void setComponentSize(
			@UInt8 byte size) 
		throws IllegalArgumentException {
		
		if (size < 0)
			throw new IllegalArgumentException("Cannot set the size value of an RGBA component to a negative value.");
		this.componentSize = size;
	}

	// TODO work out how to replace this.
	/* @TableGenerator(name = "RGBAComponentIdGeneration",
			table = "GeneratorTable",
			pkColumnName = "PrimaryKeyColumn",
			valueColumnName = "ValueColumn",
			pkColumnValue = "RGBAComponentId",
			allocationSize = 8)
			
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "RGBAComponentIdGeneration") */
	protected final long getId() { return persistentId; }
	protected final void setId(long id) { this.persistentId = id; }

	public final boolean equals(
			Object o) {
	
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof RGBAComponent)) return false;
		
		RGBAComponent testComponent = 
			(RGBAComponent) o;
		
		if (testComponent.getComponentSize() != componentSize) return false;
		if (testComponent.getCode() != code) return false;
		
		return true;
	}
	
	/**
	 * <p>Creates a pseudo-XML string representation of this RGBA component value. The representation 
	 * consists of a <code>RGBAComponent</code> element with <code>Code</code> and <code>Size</code>
	 * sub-elements. For example, an 8-bit red component is represented as follows:</p>
	 * 
	 * <pre>
	 *   &lt;RGBAComponent&gt;
	 *     &lt;Code&gt;CompRed&lt;/Code&gt;
	 *     &lt;ComponentSize&gt;8&lt;/ComponentSize&gt;
	 *   &lt;/RGBAComponent&gt;
	 * </pre>
	 * 
	 * @return Pseudo-XML representation of this RGBA component value.
	 * 
	 * @see #parseFactory(String)
	 * @see XMLHandler
	 */
	public final String toString() {

		return XMLBuilder.toXMLNonMetadata(this);
	}

	private final static Pattern codePattern = 
		Pattern.compile("<\\w*\\:?Code\\>(\\w+)\\<\\/\\w*\\:?Code\\>");
	private final static Pattern componentSizePattern =
		Pattern.compile("<\\w*\\:?ComponentSize\\>(\\d{1,3})\\<\\/\\w*\\:?ComponentSize\\>");
	
	/**
	 * <p>Create a new value of this RGBA component type by parsing the given pseudo-XML
	 * representation. This method will create an instance from the result of calling the 
	 * {@link #toString()} method. The pseudo-XML format is illustrated below:</p>
	 * 
	 * <pre>
	 *   &lt;RGBAComponent&gt;
	 *     &lt;Code&gt;CompRed&lt;/Code&gt;
	 *     &lt;ComponentSize&gt;8&lt;/ComponentSize&gt;
	 *   &lt;/RGBAComponent&gt;
	 * </pre>
	 * 
	 * <p>If any tags are missing, default values are used and the method returns successfully.
	 * This method ignores any namespace prefix found in an element name.</p>
	 * 
	 * @param componentString Pseudo-XML string to convert into a RGBA component instance.
	 * @return Newly created RGBA component instance.
	 * 
	 * @throws NullPointerException The given psuedo-XML representation is <code>null</code>.
	 * @throws ParseException The component size could not be parsed into a byte value or the 
	 * given component code was not recognized as an element of the 
	 * {@linkplain RGBAComponentKind RGBA component kind enumeration}.
	 * 
	 * @see #toString()
	 */
	public final static RGBAComponentImpl parseFactory(
			String componentString) 
		throws NullPointerException,
			ParseException {

		if (componentString == null)
			throw new NullPointerException("Cannot create a RGBA component value from a null value.");
		
		try {
			Matcher matcher = codePattern.matcher(componentString);
			
			RGBAComponentKind code = RGBAComponentKind.Null;
			if (matcher.find()) {
				String enumToken = matcher.group(1);
				if (enumToken.startsWith("Comp"))
					enumToken = enumToken.substring(4);
				code = RGBAComponentKind.valueOf(enumToken);
			}
			
			matcher = componentSizePattern.matcher(componentString);
			
			byte componentSize = (byte) 0;
			if (matcher.find())
				componentSize = Byte.parseByte(matcher.group(1));
			
			return new RGBAComponentImpl(code, componentSize);
		}
		catch (NumberFormatException nfe) {
			throw new ParseException("Unable to parse the given RGBA component size value into a byte value.", 0);
		}
		catch (IllegalArgumentException iae) {
			throw new ParseException("Unable to parse the given RGBA component kind value into a known enumeration value of RGBA component kind.", 0);
		}
	}
	
	public final RGBAComponent clone() {

		try {
			return (RGBAComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			// Should not happen as this class implements cloneable
			e.printStackTrace();
			return null;
		}
	}

	public int hashCode() {

		return code.hashCode() ^ (componentSize << 16);
	}

	public final static String RGBACOMPONENT_TAG = "RGBAComponent";
	final static String CODE_TAG = "Code";
	final static String COMPONENTSIZE_TAG = "ComponentSize";
	
	public void appendXMLChildren(
			Node parent) {
		
		Node rgbaComponentElement;
		
		if (parent instanceof DocumentFragment)
			rgbaComponentElement = 
				XMLBuilder.createChild(parent, AAF_XML_NAMESPACE, AAF_XML_PREFIX, RGBACOMPONENT_TAG);
		else
			rgbaComponentElement = parent;

		XMLBuilder.appendElement(rgbaComponentElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, CODE_TAG, "Comp" + code.name());
		XMLBuilder.appendElement(rgbaComponentElement, AAF_XML_NAMESPACE, 
				AAF_XML_PREFIX, COMPONENTSIZE_TAG, componentSize);
	}

	/**
	 * <p>XML parser event handler for converting RGBA component elements into RGBA component 
	 * values.</p>
	 * 
	 *
	 * 
	 * @see RGBAComponentImpl#toString()
	 */
	public static class XMLHandler
		extends LocalHandler {

		public final static String elementName = RGBACOMPONENT_TAG;
		public final static String[] dataElementNames = new String[] {
			CODE_TAG, COMPONENTSIZE_TAG
		};
		
		@Override
		protected Object createResult() throws Exception {

			String codeName = getElementData(CODE_TAG);
			if (codeName.startsWith("Comp"))
				codeName = codeName.substring(4);
			
			return new RGBAComponentImpl(
					RGBAComponentKind.valueOf(codeName),
					Byte.parseByte(getElementData(COMPONENTSIZE_TAG)));
		}
	}

	public String getComment() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public final static String MYSQL_COLUMN_DEFINITION = 
		"VARCHAR(255) CHARACTER SET ascii COLLATE ascii_general_ci";
	
	public final static String toPersistentForm(
			RGBAComponent rgbaComponent) {
		
		if (rgbaComponent == null) return null;
		return rgbaComponent.toString();
	}
	
	public final static RGBAComponent fromPersistentForm(
			String rgbaComponent) {
		
		if (rgbaComponent == null) return null;
		try {
			return parseFactory(rgbaComponent);
		}
		catch (ParseException pe) {
			return null;
		}
	}
}
