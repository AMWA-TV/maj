/* 
 **********************************************************************
 *
 * $Id: EmitXMLClassIDAs.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
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
 * $Log: EmitXMLClassIDAs.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/12/18 17:56:00  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * 
 */

package tv.amwa.maj.industry;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.model.InterchangeObject;

/**
 * <p>Specifies how the class identifier for the type of an object should be included with that
 * object when it is serialized to XML.</p>
 * 
 * <p>Normally, an <code>ObjectClass</code> element is emitted as a child element
 * for every {@linkplain InterchangeObject interchange object}. In certain cases, such 
 * as when the object has not been defined in a namespace that is not derived from the AAF
 * namespace, it is desirable to emit the identifier as an attribute or
 * to suppress it altogether.</p>
 * 
 * <p>How the object's class identifier is to be emitted is controlled by the setting the 
 * {@linkplain MediaClass#emitXMLClassID() emitXMLClassID} property of the {@linkplain MediaClass
 * media class} annotation. If the optional annotation property is omitted from a class,
 * the default is {@link #Parent}.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see MediaClass#emitXMLClassID()
 * @see MediaEngine#getClassDefinition(MetadataObject)
 * @see MetaDefinition#getAUID()
 */
public enum EmitXMLClassIDAs {

	/**
	 * <p>Emit the identifier of the class as an child element of its serialization to XML
	 * called <code>ObjectClass</code>. This is the default approach and results in XML of 
	 * the form:</p>
	 * 
	 * <pre>
	 *   &lt;NetworkLocator&gt;
	 *     &lt;ObjectClass&gt;urn:smpte:ul:060e2b34.02060101.0d010101.01013200&lt;/ObjectClass&gt;
	 *     &lt;URL&gt;http://www.portability4media.com/demo&lt;/URL&gt;
	 *   &lt;/NetworkLocator&gt;
	 * </pre>
	 */
	Element,
	/**
	 * <p>Emit the identifier of the class as an attribute of the element. The attribute is 
	 * named <code>uid</code>. This results in XML of the form:</p>
	 * 
	 * <pre>
	 *   &lt;NetworkLocator uid="urn:smpte:ul:060e2b34.02060101.0d010101.01013200"&gt;
	 *     &lt;URL&gt;http://www.portability4media.com/demo&lt;/URL&gt;
	 *   &lt;/NetworkLocator&gt;
	 * </pre>
	 */
	Attribute,
	/**
	 * <p>Do not emit the identifier of the class in any form. In this case, it is assumed that
	 * an XML schema is providing sufficient identification. This results in XML of the form:</p>
	 * 
	 * <pre>
	 *   &lt;NetworkLocator&gt;
	 *     &lt;URL&gt;http://www.portability4media.com/demo&lt;/URL&gt;
	 *   &lt;/NetworkLocator&gt;
	 * </pre>
	 * 
	 * @see CommonConstants#ObjectClassID
	 */
	Suppressed,
	/**
	 * <p>Emit the same class identification for this element as for its parent in the
	 * class hierarchy. The root of the type hierarchy will default to {@link #Element}
	 * if this method is set.</p>
	 * 
	 * @see ClassDefinition#getParent()
	 */
	Parent;
	
}
