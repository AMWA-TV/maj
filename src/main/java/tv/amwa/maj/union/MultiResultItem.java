/* 
 **********************************************************************
 *
 * $Id: MultiResultItem.java,v 1.2 2011/02/14 22:32:59 vizigoth Exp $
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
 * $Log: MultiResultItem.java,v $
 * Revision 1.2  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.3  2008/01/10 17:19:47  vizigoth
 * Minor comment improvement.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:13:01  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.integer.UInt32;

/** 
 * <p>Specifies an element of an array containing multiple result values from an operation
 * to read one or more channels from an interleaved data stream.</p>
 * 
 * @see tv.amwa.maj.model.EssenceMultiAccess
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */

public interface MultiResultItem { 

	/**
	 * <p>Returns the total number of bytes transferred.</p>
	 *
	 * @return Total number of bytes transferred.
	 */
	public @UInt32 int getBytesXfered();

	/**
	 * <p>Sets the total number of bytes transferred.</p>
	 *
	 * @param bytesXfered Total number of bytes transferred.
	 * 
	 * @throws IllegalArgumentException The given number of bytes transferred is negative.
	 */
	public void setBytesXfered(
			@UInt32 int bytesXfered)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the number of samples transferred.</p>
	 *
	 * @return Number of samples transferred.
	 */
	public @UInt32 int getSamplesXfered();

	/**
	 * <p>Sets the number of samples transferred.</p>
	 *
	 * @param samplesXfered Number of samples transferred.
	 * 
	 * @throws IllegalArgumentException The given number of samples transferred is negative.
	 */
	public void setSamplesXfered(
			@UInt32 int samplesXfered)
		throws IllegalArgumentException;
}
