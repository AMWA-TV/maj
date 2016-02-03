/* 
 **********************************************************************
 *
 * $Id: XMLSerializable.java,v 1.6 2011/01/04 10:40:23 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: XMLSerializable.java,v $
 * Revision 1.6  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2010/06/16 14:56:41  vizigoth
 * Towards better Reg XML support for complete documents ... still work in progress.
 *
 * Revision 1.4  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/15 14:16:08  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.1  2007/11/13 22:14:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.io.xml;

import org.w3c.dom.Node;

/** 
 * <p>Specifies a class that can add comments and extra element to its current value to nodes 
 * of an XML DOM tree. All {@linkplain tv.amwa.maj.industry.MetadataObject metadata objects} can
 * serialize themselves as XML using their {@linkplain tv.amwa.maj.meta.ClassDefinition#getSymbol()
 * symbol names}. If this interface is implemented, a descriptive comment can be added and additional
 * child nodes created.</p>
 * 
 * @see XMLFactory
 * @see XMLBuilder#toXML(tv.amwa.maj.industry.MetadataObject)
 * @see tv.amwa.maj.meta.ClassDefinition#getSymbol()
 * @see tv.amwa.maj.meta.PropertyDefinition#getSymbol()
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface XMLSerializable {
	
	/**
	 * <p>Append extra child elements to the given parent node to serialize the value of an object
	 * to an XML fragment. Methods of the {@link XMLBuilder} class are provided to help with this
	 * process.</p>
	 *
	 * @param parent XML parent element to append child nodes to.
	 */
	public void appendXMLChildren(
			Node parent);
	
	/**
	 * <p>Include a comment when serializing a value to XML. The comment will be inserted as the
	 * first child node of the element representing the class.</p>
	 * 
	 * @return Comment to add as the first child node of the element representing this class.
	 */
	public String getComment();
}
