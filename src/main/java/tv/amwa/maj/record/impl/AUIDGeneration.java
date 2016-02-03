/* 
 **********************************************************************
 *
 * $Id: AUIDGeneration.java,v 1.3 2011/02/14 22:33:03 vizigoth Exp $
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
 * $Log: AUIDGeneration.java,v $
 * Revision 1.3  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/03/07 08:08:12  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:14:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;


/**
 * <p>Enumeration representing the different generation methods for AUIDs. These versions are
 * the ones specified in <a href="http://www.faqs.org/rfcs/rfc4122.html" alt="RFC 4122">rfc&nbsp;4122</a>
 * that are supported by the MAJ API.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 * @see AUIDImpl#auidFactory(AUIDGeneration, byte[])
 * @see java.util.UUID
 */
public enum AUIDGeneration {

	/**
	 * <p>A time-based method for UUID generation consistent with section&nbsp;4.2 of rfc&nbsp;4122. 
	 * The resulting UUID consists of the current date and time, as well as an identifier for the host
	 * generating the identifier. Same as {@link #Timebased}.</p>
	 * 
	 * @see AUID#timebasedAUID()
	 * @see AUID#timebasedAUID(byte[])
	 */
	IETF_Type1,
	/**
	 * <p>A time-based method for UUID generation consistent with section&nbsp;4.2 of rfc&nbsp;4122.
	 * The resulting UUID consists of the current date and time, as well as an identifier for the host
	 * generating the identifier. Same as {@link #IETF_Type1}.</p>
	 * 
	 * @see AUID#timebasedAUID()
	 * @see AUID#timebasedAUID(byte[])
	 */
	Timebased,
	/**
	 * <p>A name-based method for UUID generation consistent with section&nbsp;4.3 of rfc&nbsp;4122.
	 * Same as {@link #Namebased}.</p>
	 * 
	 * @see AUID#namebasedAUID(byte[])
	 */
	IETF_Type3,
	/**
	 * <p>A name-based method for UUID generation consistent with section&nbsp;4.3 of rfc&nbsp;4122.
	 * Same as {@link #IETF_Type3}.</p>
	 * 
	 * @see AUID#namebasedAUID(byte[])
	 */
	Namebased,
	/**
	 * <p>An UUID generated from truly random or pseudo-random numbers consistent with section&nbsp;4.4
	 * of rfc&nbsp;4122. Same as {@link #Random}.</p>
	 * 
	 * @see AUID#randomAUID()
	 */
	IETF_Type4,
	/**
	 * <p>An UUID generated from truly random or pseudo-random numbers consistent with section&nbsp;4.4
	 * of rfc&nbsp;4122. Same as {@link #IETF_Type4}.</p>
	 * 
	 * @see AUID#randomAUID()
	 */
	Random;
	
}
