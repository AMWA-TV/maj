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
 * $Log: InvalidDataDefinitionException.java,v $
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:45  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:10  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: EssenceGroup, MaterialPackage, Package, NestedScope, Segment, Selector, Sequence, SourcePackage

/** 
 * <p>Thrown when an inappropriate {@linkplain tv.amwa.maj.model.DataDefinition data definition} is 
 * used within the context of a method call.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_INVALID_DATADEF 0x801200D0</code></p>
 *
 * @see tv.amwa.maj.model.DataDefinition
 * @see tv.amwa.maj.constant.DataDefinitionConstant
 *
 *
 *
 */
public class InvalidDataDefinitionException 
	extends IllegalArgumentException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = -8885928788224132108L;

	/**
	 * <p>Create a new invalid data definition exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public InvalidDataDefinitionException(
			String msg) {

		super(msg);
	}

	/**
	 * <p>Create a new invalid data definition exception with no message.</p>
	 */	
	public InvalidDataDefinitionException() {
		super();
	}
}
