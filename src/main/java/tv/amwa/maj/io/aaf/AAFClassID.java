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
 * $Log: AAFClassID.java,v $
 * Revision 1.4  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2010/06/14 17:12:16  vizigoth
 * Significant progress towards writing valid AAF files with MAJ.
 *
 * Revision 1.2  2010/05/19 12:58:28  vizigoth
 * Capability to write an AAF file that MAJ can read.
 *
 */

package tv.amwa.maj.io.aaf;

import org.apache.poi.hpsf.ClassID;

import tv.amwa.maj.meta.MetaDefinition;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.util.Utilities;

/**
 * <p>Class identifier to use as storage class identifiers on <em>folders</em> representing
 * AAF classes within structured storage files. <p>
 * 
 *
 *
 */
public class AAFClassID 
	extends ClassID {

	/**
	 * <p>Create a storage class identifier from a MAJ class definition identifier.</p> 
	 * 
	 * @param aafClassID Identifier for an AAF class.
	 * 
	 * @throws NullPointerException Cannot set the value of the class identifier using  
	 * a <code>null</code> value.
	 * 
	 * @see MetaDefinition#getAUID()
	 * @see #AAFClassID(byte[])
	 */
	public AAFClassID(
			AUID aafClassID) 
		throws NullPointerException {
		
		bytes = aafClassID.getAUIDValue();
	}
	
	/**
	 * <p>Create a storage class identifier from an array of bytes already in the correct
	 * order to be written into the file.</p>
	 * 
	 * @param aafClassIDBytes Identifier for an AAF class.
	 * 
	 * @throws NullPointerException annot set the value of the class identifier using  
	 * a <code>null</code> value.
	 * 
	 * @see #AAFClassID(AUID)
	 */
	public AAFClassID(
			byte[] aafClassIDBytes) 
		throws NullPointerException {
		
		bytes = Utilities.checkBytes(aafClassIDBytes, 16);
	}
	
}
