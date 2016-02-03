/* 
 **********************************************************************
 *
 * $Id: OperationalPatternDescription.java,v 1.2 2008/01/23 14:22:47 vizigoth Exp $
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
 * $Log: OperationalPatternDescription.java,v $
 * Revision 1.2  2008/01/23 14:22:47  vizigoth
 * Fixed name of OperationalProtocolConstant to OperationalPatternConstant.
 *
 * Revision 1.1  2008/01/23 14:20:49  vizigoth
 * Added operational pattern constants and descriptions.
 *
 */

package tv.amwa.maj.constant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Labels an {@linkplain tv.amwa.maj.record.AUID AUID} that is a unqiue identifier for an
 * operational pattern. An operational pattern constraints levels of file complexity.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see OperationalPatternConstant
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OperationalPatternDescription {

	/**
	 * <p>A brief description of the operational pattern.</p>
	 */
	String description() default "";
	
	/**
	 * <p>A list of alternative names that can be used as aliases to describe the operational pattern.</p>
	 */
	String[] aliases() default { };
	

}
