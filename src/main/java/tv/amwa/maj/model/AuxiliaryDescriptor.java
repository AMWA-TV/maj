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
 * $Log: AuxiliaryDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:31  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/01/27 11:07:20  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:09  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.AAFString;

/**
 * <p>Specifies the description of an auxiliary file essence source. The type
 * of data in the essence is given by a mime type. See the 
 * <a href="http://www.iana.org/assignments/media-types/index.html">IANA register of media
 * types</a>.</p>
 * 
 *
 * 
 * @see SourcePackage#getEssenceDescriptor()
 */
public interface AuxiliaryDescriptor
	extends PhysicalDescriptor {

	/**
	 * <p>Sets the registered mime type of the data in the auxiliary file as per 
	 * <a href="http://www.faqs.org/rfcs/rfc2046.html">RFC 2046</a>
	 * and <a href="http://www.faqs.org/rfcs/rfc2048.html">RFC 2048</a>.</p>
	 * 
	 * @param mimeType Specifies the registered mimetype of the data in 
	 * the auxiliary file.
	 * 
	 * @throws NullPointerException The given mime type is <code>null</code>.
	 */
	public void setMIMEType(
			@AAFString String mimeType) 
		throws NullPointerException;

	/** 
	 * <p>Gets the registered mime type of the data in the auxiliary file as per 
	 * <a href="http://www.faqs.org/rfcs/rfc2046.html">RFC 2046</a> (MIME media types) and 
	 * <a href="http://www.faqs.org/rfcs/rfc2048.html">RFC 2048</a> (MIME registration prodcedures).</p>
	 * 
	 * @return The registered mimetype of the data in 
	 * the auxiliary file.
	 */
	public @AAFString String getMIMEType();

	/**
	 * <p>Sets the registered character set used by the internal and external representation of the data as per 
	 * <a href="http://www.faqs.org/rfcs/rfc2048.html">RFC 2048</a>.
	 * See the <a href="http://www.iana.org/assignments/character-sets">
	 * IANA register of character sets</a>. Example: <code>"ISO-8859-1"</code>.
	 * Set to <code>null</code> to omit this optional property.</p>
	 * 
	 * @param charSet Specifies the registered character set used by the internal and 
	 * external representation of the data.
	 */	
	public void setCharSet(
			@AAFString String charSet);
	
	/**
	 * <p>Gets the registered character set used by the internal and external representation of the data as per <a 
	 * href="href="http://www.faqs.org/rfcs/rfc2048.html">RFC 2048</a>.
	 * See the <a href="http://www.iana.org/assignments/character-sets">
	 * IANA register of character sets</a>. Example: 
	 * <code>"ISO-8859-1"</code>. This is an optional property.</p>
	 * 
	 * @return The registered character set used by the internal and 
	 * external representation of the data.
	 * 
	 * @throws PropertyNotPresentException The optional character set is not present
	 * for this auxiliary descriptor.
	 */
	public @AAFString String getCharSet()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this auxiliary descriptor.</p>
	 * 
	 * @return Cloned copy of this auxiliary descriptor.
	 */
	public AuxiliaryDescriptor clone();
}
