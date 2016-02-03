/* 
 **********************************************************************
 *
 * $Id: HiddenClass.java,v 1.3 2011/01/13 17:44:26 vizigoth Exp $
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
 * $Log: HiddenClass.java,v $
 * Revision 1.3  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.2  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2010/03/19 09:50:37  vizigoth
 * New annotation for classes that are not SMPTE registered but are useful for Java.
 *
 */

package tv.amwa.maj.industry;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tv.amwa.maj.meta.TypeDefinitionObjectReference;

/**
 * <p>Labels a class introduced into a Java class hierarchy for convenience, between {@linkplain MediaClass media
 * classes}, but not part of a registered hierarchy. Such a class contains shared methods for its children but
 * does not have any {@linkplain MediaProperty media properties} exposed.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see TypeDefinitionObjectReference
 * @see Warehouse#lookForClass(Class)
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HiddenClass {

}
