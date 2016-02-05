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
 * $Log: Identification.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/11/18 10:44:52  vizigoth
 * Fixed import headings.
 *
 * Revision 1.6  2010/07/14 13:34:38  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.5  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:25  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.ProductVersion;
import tv.amwa.maj.record.TimeStamp;


/**
 * <p>Specifies identity information about the application that created or 
 * modified a file.</p>
 * 
 * <p>If a file was opened for modification by many applications in its
 * lifetime, then there will be multiple identification objects.
 * These are kept in an ordered list, with the first entry being the
 * file creator, and the last entry being the last application to
 * modify the file. The list is stored in the {@linkplain Preface preface}
 * of a file and accessed with {@link Preface#getIdentifications()}.</p>
 * 
 * <p>An identification is useful for technical support when
 * diagnosing problems with AAF files, as it tells which applications and
 * versions of applications have touched the file.   If a file has been 
 * modified by multiple applications, then the {@linkplain #getFileModificationDate() date} and 
 * {@linkplain #getApplicationProductID() product id} properties can be
 * used to tell which changes to the file were made by a particular
 * application.</p>
 * 
 *
 *
 * @see Preface#getLastIdentification()
 */

public interface Identification 
	extends InterchangeObject {

	/**
	 * <p>Returns the application supplier name property of this identification, which specifies the 
	 * name of the company or organization that created the application.</p>
	 * 
	 * @return Application supplier name of this identification.
	 */
	public @AAFString String getApplicationSupplierName();

	/**
	 * <p>Returns the application name of this identification, which specifies the name 
	 * of the application.</p>
	 * 
	 * @return Application name of this identification.
	 */
	public @AAFString String getApplicationName();

	/**
	 * <p>Returns the application version string of this identification, which
	 * specifies the version number of the application in string form.</p>
	 * 
	 * @return Application version string of this identification.
	 */
	public @AAFString String getApplicationVersionString();

	/**
	 * <p>Gets the application version property associated with this
	 * identification, which specifies the version number of the application. This
	 * is an optional property.</p>
	 * 
	 * @return Application version property associated with this identification.
	 * 
	 * @throws PropertyNotPresentException The optional product version property is
	 * not present in this identification.
	 */
	public ProductVersion getApplicationVersion()
		throws PropertyNotPresentException;

	/**
	 * <p>Set the application version property of this identification, which specifies the 
	 * version number of the application. Set this optional property to <code>null</code> to
	 * omit it.</p>
	 * 
	 * @param version Application version to set for this identification.
	 */
	public void setApplicationVersion(
			ProductVersion version);

	/**
	 * <p>Returns the application product id of this identification, which uniquely identifies 
	 * the application.</p>
	 * 
	 * @return Application product id of this identification.
	 */
	public AUID getApplicationProductID();

	/**
	 * <p>Returns the file modification date property of 
	 * this identification. The date-time stamp recorded in this object corresponds to 
	 * the time that this file was created or modified on the occasion that
	 * this identification was added to the file.</p>
	 * 
	 * @return File modification date of this identification.
	 * 
	 * @see Preface#appendIdentification(Identification)
	 */
	public TimeStamp getFileModificationDate();

	/**
	 * <p>Returns the version number of this SDK library.</p>
	 * 
	 * @return Version number of this SDK library.
	 */
	public ProductVersion getRefImplVersion();

	/**
	 * <p>Returns the version number of this SDK library..</p>
	 * 
	 * @return Version number of this SDK library.
	 */

	public ProductVersion getToolkitVersion();
	
	/**
	 * <p>Returns the application platform property of this identification, which 
	 * specifies the toolkit and the platform on which the application is 
	 * running, e.g. "MAJ API". This is an optional property.</p>
	 * 
	 * <p>This information is provided only to allow diagnostic printing of
	 * platform information to be read by humans.  The format of the
	 * strings is not guaranteed to remain the same for a given
	 * platform.</p>
	 * 
	 * @return Platform property of this identification.
	 * 
	 * @throws PropertyNotPresentException The optional platform property
	 * is not present in this identification.
	 */
	public @AAFString String getApplicationPlatform()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the generation identifier of this AAF file, which is
	 * an {@linkplain tv.amwa.maj.record.AUID AUID} that was generated at
	 * the time this identification was created.  If a file was
	 * opened for modification by many applications in its lifetime,
	 * then there will be multiple Identification objects.</p>
	 * 
	 * <p>Note that this is a read-only property automatically generated 
	 * by the implementation.</p>
	 * 
	 * @return Unique generation AUID of this identification.
	 */
	public AUID getGenerationID();
	
	/**
	 * <p>Create a cloned copy of this identification.</p>
	 *
	 * @return Cloned copy of this identification.
	 */
	public Identification clone();
}
