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
 * $Log: BadTypeException.java,v $
 * Revision 1.4  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.3  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2007/11/27 20:37:44  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:20  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: All type definitions, AAFFactory

/** 
 * <p>Thrown when a method call on an object in the meta dictionary passes in the
 * wrong type of {@linkplain tv.amwa.maj.industry.PropertyValue property value} as a 
 * parameter.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_BAD_TYPE 0x801200CC</code></p>
 *
 * @see tv.amwa.maj.meta.TypeDefinitionFixedArray
 * @see tv.amwa.maj.meta.TypeDefinitionRecord
 * @see tv.amwa.maj.meta.TypeDefinitionSet
 * @see tv.amwa.maj.meta.TypeDefinitionStream
 * @see tv.amwa.maj.meta.TypeDefinitionString
 * @see tv.amwa.maj.meta.TypeDefinitionVariableArray
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class BadTypeException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 5736718965504949293L;

	/**
	 * <p>Create a new bad type exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */

	public BadTypeException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new bad type exception with no message.</p>
	 */
	public BadTypeException() {
		super();
	}
}
