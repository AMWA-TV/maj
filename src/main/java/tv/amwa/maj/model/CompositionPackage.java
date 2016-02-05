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
 * $Log: CompositionPackage.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.1  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:21  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:35  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.union.DefaultFade;


/**
 * <p>Specifies a material object that describes how to combine content data elements 
 * into a sequence, how to modify content data elements, and how to synchronize content
 * data elements.</p>
 * 
 *
 * 
 */

public interface CompositionPackage 
	extends Package {

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.union.DefaultFade default fade} for this 
	 * composition package, which specifies the default fade to be applied to all audio 
	 * {@linkplain SourceClip source clips} that do not specify their own audio fade properties.  
	 * The default fade properties are optional.</p>
	 * 
	 * @return Default fade for the composition.
	 * 
	 * @throws PropertyNotPresentException The optional default fade properties are
	 * not present in this composition package.
	 * 
	 * @see #getDefaultFadeEditUnit()
	 * @see #getDefaultFadeLength()
	 * @see #getDefaultFadeType()
	 */
	public DefaultFade getDefaultFade()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the default fade properties of this composition package, which specifies the default 
	 * fade to be applied to all audio {@linkplain SourceClip source clips} that do not specify their 
	 * own audio fade properties. The default fade properties are optional and can be omitted 
	 * by calling this method with <code>null</code> for either the fade type or fade edit unit
	 * properties.</p>
	 * 
	 * @param fadeLength Default fade length for the fade.
	 * @param fadeType Default fade type for the fade.
	 * @param fadeEditUnit Default fade edit unit for the fade.
	 * 
	 * @throws BadLengthException Fade length is negative.
	 */
	public void setDefaultFade(
			@LengthType long fadeLength,
			FadeType fadeType,
			Rational fadeEditUnit) 
		throws BadLengthException;
	
	/**
	 * <p>Sets the default fade properties for this composition package, which specifies the default 
	 * fade to be applied to all audio {@linkplain SourceClip source clips} that do not specify their 
	 * own audio fade properties. The default fade properties are optional and can be omitted 
	 * by calling this method with <code>null</code>.</p>
	 * 
	 * @param defaultFade Default fade properties for this composition package.
	 */
	public void setDefaultFade(
			DefaultFade defaultFade);

	/**
	 * <p>Returns the edit units used to specify the default fade length of this composition package.</p>
	 *
	 * @return Edit units used to specify the fade length.
	 * 
	 * @see #getDefaultFade()
	 */
	public Rational getDefaultFadeEditUnit()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the length of this default audio fade of this composition package, measured in the 
	 * fade's {@linkplain #getDefaultFadeEditUnit() edit units}.</p>
	 *
	 * @return Length of the default audio fade.
	 * 
	 * @see #getDefaultFadeEditUnit()
	 * @see #getDefaultFade()
	 */
	public @LengthType long getDefaultFadeLength()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the type of the default audio fade for this composition package.</p>
	 *
	 * @return Type of this default audio fade.
	 * 
	 * @see #getDefaultFade()
	 */
	public FadeType getDefaultFadeType()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Set the {@linkplain tv.amwa.maj.record.PackageID package id} of a rendering of this composition 
	 * package, which specifies a mixdown of the composition. The start of the mixdown 
	 * and the start of the composition are assumed to be co-timed. Set the rendering to
	 * <code>null</code> to omit this optional property.</p>
	 * 
	 * @param packageID Package id for a rendering of this composition package.
	 */
	public void setCompositionRendering(
			PackageID packageID);

	/**
	 * <p>Returns the {@linkplain tv.amwa.maj.record.PackageID package id} of a rendering of this composition
	 * package, which specifies a mixdown of the composition. The start of the mixdown 
	 * and the start of the composition are assumed to be co-timed.</p>
	 * 
	 * @return package id of a rendering of the composition.
	 * 
	 * @throws PropertyNotPresentException The optional rendering property is not
	 * present in this composition package. 
	 */
	public PackageID getCompositionRendering() 
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this composition package.</p>
	 *
	 * @return Cloned copy of this composition package.
	 */
	public CompositionPackage clone();

}
