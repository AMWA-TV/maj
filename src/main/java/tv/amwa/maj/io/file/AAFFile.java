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
 * $Log: AAFFile.java,v $
 * Revision 1.4  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:39  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:08  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2007/11/13 22:17:24  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:32  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.io.file;

import java.io.Serializable;

import tv.amwa.maj.enumeration.FileRev;
import tv.amwa.maj.exception.AlreadyOpenException;
import tv.amwa.maj.exception.InsufficientSpaceException;
import tv.amwa.maj.exception.NoPrefaceException;
import tv.amwa.maj.exception.NotOpenException;
import tv.amwa.maj.exception.NotWriteableException;
import tv.amwa.maj.exception.WrongOpenModeException;
// import tv.amwa.maj.meta.AAFClass;
import tv.amwa.maj.model.impl.DictionaryImpl;
import tv.amwa.maj.model.impl.PrefaceImpl;



/** TODO implemenetation, head scratching, comments and tests
 * <p></p>
 *
 *
 *
 */

//@AAFClass(uuid1 = 0x9346ACD3, uuid2 = 0x2713, uuid3 = 0x11d2,
//		  uuid4 = { (byte) 0x80, 0x35, 0x00, 0x60, 0x08, 0x14, 0x3E, 0x6F },
//		  definedName = "File",
//		  description = "The File class reprsents an AAF file.",
//		  symbol = "File") // Not an AAF class
// @Entity
public class AAFFile
		implements
			tv.amwa.maj.model.AAFFile,
			Serializable {

	/** <p></p> */
	private static final long serialVersionUID = 1308031985331787898L;

	// TODO private fields here

	/** Default constructor is not public to avoid unset required fields. */
	protected AAFFile() {
	}

	// TODO public constructor here

	/**
	 * <p>Cast a {@link tv.amwa.maj.model.AAFFile AAFFile} 
	 * value from the generic interface to this implementation of
	 * the interface. If the given value is not a native implementation, a copy will be
	 * instanciated using get and set methods.</p>
	 *
	 * @param alien A potentially alien implementation of an instance of the AAFFile 
	 * interface.
	 * @return Instance of this class that is equal to the given value and that is 
	 * an instance of this concrete implementation.
	 *
	 * @throws NullPointerException Argument is null.
	 */
	public final static AAFFile castFromInterface(
			tv.amwa.maj.model.AAFFile alien)
			throws NullPointerException {

		if (alien == null)
			throw new NullPointerException(
					"Cannot cast to AAFFile from a null value.");

		if (alien instanceof AAFFile)
			return (AAFFile) alien;

		AAFFile castValue = new AAFFile();
		// TODO Complete cast

		return castValue;
	}

	/**
	 * <p>Creates and initilizes an AAF file from a Java file descriptor. This method makes no
	 * attempt to check the validity of the given file descriptor or whether the file it specifies
	 * exists and can be accessed.</p>
	 *
	 * @param aafFile File descriptor for an AAF file.
	 * @return Manufactured AAF file.
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public AAFFile(
			java.io.FileDescriptor aafFile)
		throws NullPointerException {
		
		// TODO constructor, and lots of other thinking about this class!
	}
	
	/** 
	 * @see tv.amwa.maj.model.AAFFile#close()
	 */
	public void close()
			throws NotOpenException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.AAFFile#getDictionary()
	 */
	public DictionaryImpl getDictionary() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.AAFFile#getPreface()
	 */
	public PrefaceImpl getPreface()
			throws NotOpenException,
				NoPrefaceException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.AAFFile#getRevision()
	 */
	public FileRev getRevision()
			throws NotOpenException {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * @see tv.amwa.maj.model.AAFFile#open()
	 */
	public void open()
			throws AlreadyOpenException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.AAFFile#save()
	 */
	public void save()
			throws NotOpenException,
				WrongOpenModeException,
				InsufficientSpaceException {
		// TODO Auto-generated method stub

	}

	/** 
	 * @see tv.amwa.maj.model.AAFFile#saveCopyAs(tv.amwa.maj.model.AAFFile)
	 */
	public void saveCopyAs(
			tv.amwa.maj.model.AAFFile destFile)
			throws NotOpenException,
				NotWriteableException {
		// TODO Auto-generated method stub

	}
}
