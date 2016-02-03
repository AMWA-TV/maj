/* 
 **********************************************************************
 *
 * $Id: EssenceType.java,v 1.5 2011/02/14 22:32:58 vizigoth Exp $
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
 * $Log: EssenceType.java,v $
 * Revision 1.5  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:52  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

/** 
 * <p>Specifies the kind of {@linkplain tv.amwa.maj.model.EssenceData essence data} in terms 
 * of its relationship with time.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public enum EssenceType {

	/**
	 * <p>Continuous data such as audio essence and video essence.</p>
	 * 
	 * @see tv.amwa.maj.model.TimelineTrack
	 */
	Timeline,
	/**
	 * <p>Data that is not time-oriented, for example a static image.</p>
	 * 
	 * @see tv.amwa.maj.model.StaticTrack
	 */
	Static,
	/**
	 * <p>Data that is time-oriented but discontinuous or lumpy, such as descriptive
	 * metadata.</p>
	 * 
	 * @see tv.amwa.maj.model.EventTrack
	 */
	Event
}
