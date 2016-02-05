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
 * $Log: IllegalPropertyException.java,v $
 * Revision 1.5  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:38:13  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

import tv.amwa.maj.industry.MetadataObject;

// References: InterchangeObject

/** 
 * <p>Thrown when a given {@linkplain tv.amwa.maj.meta.PropertyDefinition property definition}
 * is illegal within the context of a {@linkplain MetadataObject metadata object}.</p>
 * 
 * <p>No equivalent C result code.</p>
 * 
 * @see tv.amwa.maj.meta.PropertyDefinition
 *
 *
 *
 */
public class IllegalPropertyException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -1369581867197955774L;

	/**
	 * <p>Create a new illegal property exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public IllegalPropertyException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new illegal property exception with no message.</p>
	 */
	public IllegalPropertyException() {
		super();
	}	
}
