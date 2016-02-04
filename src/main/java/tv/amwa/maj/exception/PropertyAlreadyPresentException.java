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
 * $Log: PropertyAlreadyPresentException.java,v $
 * Revision 1.5  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/02/08 11:39:35  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2007/11/27 20:38:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:16  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: InterchangeObject

/** 
 * <p>Thrown when an attempt is made to create an optional property for an 
 * {@linkplain tv.amwa.maj.model.InterchangeObject interchange object} when 
 * a property with the same definition already exists for that object.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_PROP_ALREADY_PRESENT 0x801200DE</code></p>
 *
 *
 *
 */
public class PropertyAlreadyPresentException 
	extends Exception 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 4009718466519310226L;

	/**
	 * <p>Create a new property already present exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public PropertyAlreadyPresentException(
			String msg) {
		
		super(msg);
	}
	
	/**
	 * <p>Create a new property already present exception with no message.</p>
	 */	
	public PropertyAlreadyPresentException() {
		super();
	}
}
