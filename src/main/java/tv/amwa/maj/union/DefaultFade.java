/* 
 **********************************************************************
 *
 * $Id: DefaultFade.java,v 1.3 2011/02/14 22:32:59 vizigoth Exp $
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
 * $Log: DefaultFade.java,v $
 * Revision 1.3  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/21 11:46:38  vizigoth
 * Created tests for default fade as it is used by CompositionPackage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/01/27 11:11:18  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.3  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:55  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.record.Rational;

/**
 * <p>Specifies the optional default audio fade properties of a 
 * {@linkplain tv.amwa.maj.model.CompositionPackage composition package}, which are either all present or all 
 * omitted. Default audio fade properties are applied to all {@linkplain tv.amwa.maj.model.SourceClip
 * source clips} in a {@linkplain tv.amwa.maj.model.CompositionPackage composition} that do not specify
 * their own {@linkplain Fade audio fade} properties. A default fade applies to both an audio fade-in and
 * fade-out.</p>
 * 
 * <p>Original C name: <code>aafDefaultFade_t</code></p>
 * 
 * @see tv.amwa.maj.model.CompositionPackage
 * @see tv.amwa.maj.model.SourceClip
 * @see Fade
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 * 
 */

public interface DefaultFade { 

	/**
	 * <p>Returns the edit units used to specify the default fade length.</p>
	 *
	 * @return Edit units used to specify the fade length.
	 */
	public Rational getFadeEditUnit();

	/**
	 * <p>Sets the edit units used to specify the default fade length</p>
	 *
	 * @param fadeEditUnit Edit units used to specify the fade length.
	 *
	 * @throws NullPointerException The given edit units for the default fade length are <code>null</code>.	 
	 */
	public void setFadeEditUnit(
			Rational fadeEditUnit)
		throws NullPointerException;

	/**
	 * <p>Returns the length of this default audio fade, measured in this fade's edit units.</p>
	 *
	 * @return Length of the default audio fade.
	 * 
	 * @see #getFadeEditUnit()
	 */
	public @LengthType long getFadeLength();

	/**
	 * <p>Sets the length of this default audio fade, measured in this fade's edit unit.</p>
	 *
	 * @param fadeLength Length of the default audio fade.
	 * 
	 * @throws BadLengthException Length of the default fade is negative.
	 * 
	 * @see #getFadeEditUnit()
	 * @see #setFadeEditUnit(Rational)
	 */
	public void setFadeLength(
			@LengthType long fadeLength)
		throws BadLengthException;

	/**
	 * <p>Returns the type of this default audio fade.</p>
	 *
	 * @return Type of this default audio fade.
	 */
	public FadeType getFadeType();

	/**
	 * <p>Sets the type of this default audio fade.</p>
	 *
	 * @param fadeType Type of this default audio fade.
	 * 
	 * @throws NullPointerException The given fade type for the default fade is <code>null</code>.
	 */
	public void setFadeType(
			FadeType fadeType)
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this default fade value.</p>
	 * 
	 * @return Cloned copy of this default fade value.
	 */
	public DefaultFade clone();
}
