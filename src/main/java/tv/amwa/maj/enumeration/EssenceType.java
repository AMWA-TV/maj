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
 *
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
