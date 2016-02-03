/* 
 **********************************************************************
 *
 * $Id: GenerationMethodNotSupportedException.java,v 1.7 2011/02/14 22:32:59 vizigoth Exp $
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
 * $Log: GenerationMethodNotSupportedException.java,v $
 * Revision 1.7  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.6  2011/01/05 13:10:58  vizigoth
 * Created new forge for making record and union type values. Requires moving related types into exposed package for documentation.
 *
 * Revision 1.5  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:37:46  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:29  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// References: PackageID

/** 
 * <p>Thrown when the generation of a {@linkplain tv.amwa.maj.record.PackageID package id} is requested 
 * with a kind of {@linkplain tv.amwa.maj.record.MaterialNumberGeneration generation method}
 * that is not supported.</p>
 *
 * <p>No equivalent C result code.</p>
 *
 * @see tv.amwa.maj.record.MaterialNumberGeneration
 * @see tv.amwa.maj.record.PackageID
 * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, tv.amwa.maj.record.InstanceNumberGeneration, tv.amwa.maj.record.MaterialNumberGeneration)
 * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, tv.amwa.maj.record.InstanceNumberGeneration, tv.amwa.maj.record.MaterialNumberGeneration, tv.amwa.maj.record.AUID)
 * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, tv.amwa.maj.record.InstanceNumberGeneration, tv.amwa.maj.record.MaterialNumberGeneration, byte[])
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class GenerationMethodNotSupportedException 
	extends RuntimeException 
	implements MAJException, 
		NewForMAJ {

	/**  */
	private static final long serialVersionUID = 186664482130246237L;

	/**
	 * <p>Create a new generation method not supported exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */

	public GenerationMethodNotSupportedException(
			String msg) {
		super(msg);
	}
	
	/**
	 * <p>Create a new generation method not supported exception with no message.</p>
	 */
	public GenerationMethodNotSupportedException() {
		super();
	}
}
