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
 * $Log: FadeImpl.java,v $
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:15:39  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.model.impl.SourceClipImpl;

/** 
 * <p>Implementation of a fade that used to collect together the audio fade properties of 
 * {@linkplain tv.amwa.maj.model.SourceClip source clip} segments into a single
 * method argument and return value. These properties describe the length and type of audio fade 
 * ins and outs for a segment.</p>
 * 
 * <p>The <code>fadeInLength</code>, <code>fadeInType</code>, <code>fadeOutLength</code> and 
 * <code>fadeOutType</code> properties of 
 * {@linkplain tv.amwa.maj.model.SourceClip source clip} are deprecated in the AAF object
 * specification v1.1. The preferred way of specifying an audio fade is to use a 
 * {@linkplain tv.amwa.maj.model.Transition transition} with an appropriate effect.</p>
 * 
 * <p>As this class is used as a convenient means to package up related values as arguments methods of 
 * other persistent classes, it has no persistent representation of its own.</p>
 * 
 * @see tv.amwa.maj.model.SourceClip#setFade(tv.amwa.maj.union.Fade)
 * @see tv.amwa.maj.model.SourceClip#getFade()
 * @see tv.amwa.maj.enumeration.FadeType
 *
 *
 */

public class FadeImpl
	implements tv.amwa.maj.union.Fade,
		Serializable,
		Cloneable {
	
	/** <p></p> */
	private static final long serialVersionUID = 465034093344901022L;
	
	private long fadeInLength = 0l;
	private FadeType fadeInType = FadeType.None;
	private boolean fadeInPresent = false;
	private long fadeOutLength = 0l;
	private FadeType fadeOutType = FadeType.None;
	private boolean fadeOutPresent = false;

	/**
	 * <p>Create and initialize a fade description, which describe a fade
	 * associated with a {@link SourceClipImpl source clip}. The class is initialized with no
	 * fade in or fade out present and fade lengths set to 0.</p>
	 *
	 */
	public FadeImpl() {  }
	
	/**
	 * <p>Creates a new fade value using the given parameters.</p>
	 * 
	 * <p>Fade in and out lengths must be non-negative values, otherwise an {@linkplain tv.amwa.maj.exception.BadLengthException
	 * bad length exception} will be thrown.</p>
	 * 
	 * <p>If fade in or out types are set to <code>null</code> then the fade in and out properties will be
	 * set to not present respectively. For all non-<code>null</code> values for fade in and out types, the 
	 * properties will be set to present.</p>
	 *
	 * @param fadeInLength Fade in length for this new fade.
	 * @param fadeInType Fade in type for this new fade.
	 * @param fadeOutLength Fade out length for this new fade.
	 * @param fadeOutType Fade out type for this new fade.
	 * 
	 * @throws BadLengthException One or both of the given length values are negative.
	 */
	public FadeImpl(
			@LengthType long fadeInLength,
			FadeType fadeInType,
			@LengthType long fadeOutLength,
			FadeType fadeOutType) 
		throws BadLengthException {
		
		setFadeInLength(fadeInLength);
		setFadeInType(fadeInType);
		setFadeOutLength(fadeOutLength);
		setFadeOutType(fadeOutType);
	}

	/**
	 * <p>Set the properties of this fade value from the given object that implements the 
	 * {@linkplain tv.amwa.maj.union.Fade fade interface}.</p>
	 *
	 * @param castFrom Object implementing the fade interface to cast a value from.
	 */
	public final void setPropertiesFromInterface(
			tv.amwa.maj.union.Fade castFrom) {
		
		try {
			setFadeInLength(castFrom.getFadeInLength());
			setFadeInType(castFrom.getFadeInType());
		}
		catch (PropertyNotPresentException pnpe) {
			setFadeInType(null);
		}
		catch (BadLengthException ble) {
			throw new IllegalArgumentException("Fade.setPropertiesFromInterface(): " + ble);
		}
		
		try {
			setFadeOutLength(castFrom.getFadeOutLength());
			setFadeOutType(castFrom.getFadeOutType());
		}
		catch (PropertyNotPresentException pnpe) {
			setFadeOutType(null);
		}
		catch (BadLengthException ble) {
			throw new IllegalArgumentException("Fade.setPropertiesFromInterface(): " + ble);
		}
	}
	
	public @LengthType long getFadeInLength()
			throws PropertyNotPresentException {

		if (fadeInPresent == false)
			throw new PropertyNotPresentException("The optional fade in property is not present in this fade value.");
		
		return fadeInLength;
	}

	public FadeType getFadeInType()
			throws PropertyNotPresentException {

		if (fadeInPresent == false)
			throw new PropertyNotPresentException("The optional fade in property is not present in this fade value.");
		
		return fadeInType;
	}

	public @LengthType long getFadeOutLength()
			throws PropertyNotPresentException {

		if (fadeOutPresent == false)
			throw new PropertyNotPresentException("The optional fade in property is not present in this fade value.");
		
		return fadeOutLength;
	}

	public FadeType getFadeOutType()
			throws PropertyNotPresentException {

		if (fadeOutPresent == false)
			throw new PropertyNotPresentException("The optional fade in property is not present in this fade value.");
				
		return fadeOutType;
	}

	public boolean isFadeInPresent() {

		return fadeInPresent;
	}

	public boolean isFadeOutPresent() {

		return fadeOutPresent;
	}

	public void setFadeInLength(
			@LengthType long fadeInLength) 
		throws BadLengthException {

		if (fadeInLength < 0)
			throw new BadLengthException("Cannot set the fade in length to a negative value.");
		
		fadeInPresent = true;			
		this.fadeInLength = fadeInLength;
	}

	public void setFadeInType(
			FadeType fadeInType) {

		if (fadeInType == null)
			fadeInPresent = false;
		else {
			this.fadeInType = fadeInType;
			fadeInPresent = true;
		}
	}

	public void setFadeOutLength(
			@LengthType long fadeOutLength)
		throws BadLengthException {
		
		if (fadeOutLength < 0)
			throw new BadLengthException("Cannot set the fade out length to a negative value.");
		
		fadeOutPresent = true;
		this.fadeOutLength = fadeOutLength;
	}

	public void setFadeOutType(
			FadeType fadeOutType) {

		if (fadeOutType == null)
			fadeOutPresent = false;
		else {
			this.fadeOutType = fadeOutType;
			fadeOutPresent = true;
		}
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof tv.amwa.maj.union.Fade)) return false;
		
		tv.amwa.maj.union.Fade testFade =
			(tv.amwa.maj.union.Fade) o;

		if (fadeInPresent == false)
			return !(testFade.isFadeInPresent());
		if (fadeOutPresent == false)
			return !(testFade.isFadeOutPresent());
		
		try {
			if (fadeInLength != testFade.getFadeInLength()) return false;
			if (fadeInType != testFade.getFadeInType()) return false;
		}
		catch (PropertyNotPresentException pnpe) {
			// fadeInPresent == true here, so return false as testFade.isFadeInPresent == false
			return false;
		}
		
		try {
			if (fadeOutLength != testFade.getFadeOutLength()) return false;
			if (fadeOutType != testFade.getFadeOutType()) return false;
		}
		catch (PropertyNotPresentException pnpe) {
			// fadeOutPresent == true here, so return false as testFade.isFadeOutPresent == false
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		
		return Long.valueOf(fadeInLength).hashCode() ^ Long.valueOf(fadeOutLength).hashCode() ^
			fadeInType.hashCode() ^ fadeOutType.hashCode() ^ Boolean.valueOf(fadeInPresent).hashCode() ^
			(Boolean.valueOf(fadeOutPresent).hashCode() << 16);
	}

	@Override
	public FadeImpl clone() {
		
		try {
			return (FadeImpl) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			cnse.printStackTrace();
			return null;
		}
	}
	
	static final String FADEINLENGTH_TAG = "FadeInLength";
	static final String FADEINTYPE_TAG = "FadeInType";
	static final String FADEOUTLENGTH_TAG = "FadeOutLength";
	static final String FADEOUTTYPE_TAG = "FadeOutType";
	
	/**
	 * <p>Creates a pseudo-XML representation of this fade value, which is loosely based on the AAF XML
	 * generated for a {@linkplain tv.amwa.maj.model.impl.SourceClipImpl source clip}. No XML schema or DTD
	 * is defined. For example:</p>
	 * 
	 * <pre>
	 * &lt;Fade&gt;
	 *   &lt;FadeInLength&gt;34&lt;/FadeInLength&gt;
	 *   &lt;FadeInType&gt;LinearAmp&lt;/FadeInType&gt;
	 * &lt;/Fade&gt;
	 * </pre>
	 * 
	 * @return Pseudo-XML representation of this fade value.
	 * 
	 * @see tv.amwa.maj.model.impl.SourceClipImpl#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		if (fadeInPresent == true) {
			sb.append("  <" + FADEINLENGTH_TAG + ">" + fadeInLength + "</" + FADEINLENGTH_TAG + ">\n");
			sb.append("  <" + FADEINTYPE_TAG + ">" + fadeInType.name() + "</" + FADEINTYPE_TAG + ">\n");
		}
		
		if (fadeOutPresent == true) {
			sb.append("  <" + FADEOUTLENGTH_TAG + ">" + fadeOutLength + "</" + FADEOUTLENGTH_TAG + ">\n");
			sb.append("  <" + FADEOUTTYPE_TAG + ">" + fadeOutType.name() + "</" + FADEOUTTYPE_TAG + ">\n");
		}
		
		if (sb.length() == 0) return "<Fade />";
		
		sb.insert(0, "<Fade>\n");
		sb.append("</Fade>");
		
		return sb.toString();
	}
}
