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
 * $Log: Fade.java,v $
 * Revision 1.2  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2008/01/14 20:52:23  vizigoth
 * Changed terminology for interfaces so that they all specify something.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union;

import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.LengthType;


/**
 * <p>Specifies a value that describes an audio fade operation
 * associated with a {@linkplain tv.amwa.maj.model.SourceClip source clip} in terms of its fade in and out
 * lengths and types. This interface was introduced as part of the port of the MAJ API to collect together 
 * return values from {@link tv.amwa.maj.model.SourceClip#getFade()}.</p>
 * 
 * <p>Properties for a fade in operation are optional. Either both values for a fade in length and
 * type are present or both are omitted. Similarly for fade out parameters.</p> 
 * 
 * @see tv.amwa.maj.enumeration.FadeType
 * @see tv.amwa.maj.model.SourceClip
 * 
 *
 *
 */

public interface Fade {

	/**
	 * <p>Sets the fade in length of the fade. This is an optional property that can be omitted 
	 * by calling {@link #setFadeInType(FadeType) setFadeInType(null)}.</p>
	 * 
	 * @param fadeInLength Fade in length of the fade.
	 * 
	 * @throws BadLengthException Cannot set the fade in length to a negative value.
	 */
	public void setFadeInLength(
			@LengthType long fadeInLength)
		throws BadLengthException;
	
	/**
	 * <p>Returns the fade in length of the fade. This is an optional property.</p>
	 * 
	 * @return Fade in length of the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property is not present in the fade.
	 */
	public @LengthType long getFadeInLength()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the fade in type of the fade. Set to <code>null</code> to omit both this optional
	 * property and the associated fade in length property.</p>
	 * 
	 * @param fadeInType Fade in type of the fade.
	 */
	public void setFadeInType(
			FadeType fadeInType);
	
	/**
	 * <p>Returns the fade in type of the fade. This is an optional property.</p>
	 * 
	 * @return Fade in type of the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property
	 * is not present in the fade.
	 */
	public FadeType getFadeInType()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns <code>true</code> if details of a fade in are
	 * present in the fade; otherwise <code>false</code>.</p>
	 * 
	 * @return Are details of a fade in present in the fade? 
	 */
	public boolean isFadeInPresent();
	
	/**
	 * <p>Sets the fade out length of the fade. This is an optional property that can be omitted 
	 * by calling {@link #setFadeOutType(FadeType) setFadeOutType(null)}.</p>
	 * 
	 * @param fadeOutLength Fade out length of the fade.
	 * 
	 * @throws BadLengthException Cannot set the fade out length to a negative value.
	 */
	public void setFadeOutLength(
			@LengthType long fadeOutLength)
		throws BadLengthException;
	
	/**
	 * <p>Returns the fade out length of the fade. This is an optional property.</p>
	 * 
	 * @return Fade out length of the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property
	 * is not present in the fade.
	 */
	public @LengthType long getFadeOutLength()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the fade out type of the fade. Set to <code>null</code> to omit both this optional
	 * property and the associated fade out length property.</p>
	 * 
	 * @param fadeOutType Fade out type of the fade.
	 */
	public void setFadeOutType(
			FadeType fadeOutType);
	
	/**
	 * <p>Returns the fade out type of the fade. This is an optional property.</p>
	 * 
	 * @return Fade out type for the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property
	 * is not present in the fade.
	 */
	public FadeType getFadeOutType()
		throws PropertyNotPresentException;
		
	/**
	 * <p>Returns <code>true</code> if details of a fade out
	 * are present in the fade; otherwise <code>false</code>.</p>
	 * 
	 * @return Are details of the fade out present in the fade?
	 */
	public boolean isFadeOutPresent();
}
