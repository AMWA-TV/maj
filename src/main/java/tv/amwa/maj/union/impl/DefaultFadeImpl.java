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
 * $Log: DefaultFadeImpl.java,v $
 * Revision 1.2  2011/01/21 11:46:38  vizigoth
 * Created tests for default fade as it is used by CompositionPackage.
 *
 * Revision 1.1  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:35  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/27 11:12:29  vizigoth
 * Fixed to match interface improvements.
 *
 * Revision 1.1  2008/01/14 20:17:38  vizigoth
 * Edited comments to a release standard and implemented 4 core object methods. Also, moved DefaultFade into this package.
 *
 * Revision 1.1  2007/11/13 22:14:36  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.union.impl;

import java.io.Serializable;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.model.SourceClip;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;
import tv.amwa.maj.union.DefaultFade;


/*
 * It was my intention to embed the default fade type as an embedded rational. However, it 
 * is not possible to do this as only Basic, Column, Lob, Temporal and Enumerated types can
 * be portably used as fields within Embeddable objects. 
 */

/** 
 * <p>Implementation of a default fade to be applied to
 * {@linkplain tv.amwa.maj.model.SourceClip source clips} in a 
 * {@linkplain tv.amwa.maj.model.CompositionPackage composition package} that do not specify their own
 * fade properties. The structure consists of the fade length, edit units to which 
 * the fade is specified and the {@linkplain tv.amwa.maj.enumeration.FadeType fade type}.</p>
 * 
 * <p>It is useful to manage default fade properties together as they are either all present or all
 * omitted from a {@linkplain tv.amwa.maj.model.CompositionPackage} (conditional rule 1).</p>
 * 
 * @see tv.amwa.maj.model.CompositionPackage#getDefaultFade()
 *
 *
 */
public class DefaultFadeImpl 
	implements 
		tv.amwa.maj.union.DefaultFade,
		XMLSerializable,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = -3254222018572655370L;
	
	/**
	 * <p>Default length of the audio fade-in and fade-out to be applied 
	 * to all audio {@link SourceClip source clips} that do not specify their own audio 
	 * fade properties.</p>
	 */
	@LengthType private long fadeLength;
	/** 
	 * <p>Specifies the default type of audio fade.</p>
	 */
	private FadeType fadeType;    
	/** 
	 * <p>Specifies the edit units in which the default fade length is specified.</p>
	 */
	private Rational fadeEditUnit;

	/** 
	 * <p>Create a default fade description.</p>
	 * 
	 * @param fadeLength Specifies the default length of the audio fade-in and fade-out.
	 * @param fadeType Specifies the default type of audio fade.
	 * @param fadeEditUnit Specifies the edit units in which the default fade length is specified.
	 * 
	 * @throws NullPointerException One or both of the given fade type and/or fade edit unit values
	 * is <code>null</code>.
	 * @throws BadLengthException The default fade length is negative.
	 */
	
    public DefaultFadeImpl(
    		@LengthType long fadeLength, 
    		FadeType fadeType, 
    		Rational fadeEditUnit) 
    	throws NullPointerException,
    		BadLengthException {

    	if (fadeEditUnit == null)
    		throw new NullPointerException("Cannot create a default fade with a null value for fade edit unit.");
    	
    	if (fadeType == null)
    		throw new NullPointerException("Cannot create a default fade with a null value for fade type.");
 
		setFadeLength(fadeLength);
		setFadeType(fadeType);

		setFadeEditUnit(fadeEditUnit);
    }

    /**
     * <p>Create a default fade value with its parameters initialised to basic values. These
     * are a fade length of <code>0</code>, a fade type of {@linkplain tv.amwa.maj.enumeration.FadeType#None} 
     * and a fade edit unit of <code>0/1</code>.</p>
     *
     */
    public DefaultFadeImpl() {
    	fadeLength = 0l;
    	fadeType = FadeType.None;
    	setFadeEditUnit(new RationalImpl(0, 1));
    }
    
	public Rational getFadeEditUnit() {
		
		return fadeEditUnit.clone();
	}
	
	public void setFadeEditUnit(
			tv.amwa.maj.record.Rational fadeEditUnit) 
		throws NullPointerException {
		
		if (fadeEditUnit == null)
			throw new NullPointerException("Cannot set the fade edit unit of a default fade using a null value.");
		
		this.fadeEditUnit = new RationalImpl(fadeEditUnit.getNumerator(), fadeEditUnit.getDenominator());
	}

	public @LengthType long getFadeLength() {
		return fadeLength;
	}

	public void setFadeLength(
			@LengthType long fadeLength) 
		throws BadLengthException {
		
		if (fadeLength < 0)
			throw new BadLengthException("Cannot set the length of a default fade to a negative value.");
		
		this.fadeLength = fadeLength;
	}

	public FadeType getFadeType() {
		
		return fadeType;
	}

	public void setFadeType(
			FadeType fadeType) 
		throws NullPointerException {

		if (fadeType == null)
			throw new NullPointerException("Cannot set the fade type of a default fade with a null value.");
		
		this.fadeType = fadeType;
	}

	@Override
	public boolean equals(Object o) {
		
		if (o == null) return false;
		if (!(o instanceof tv.amwa.maj.union.DefaultFade)) return false;
		
		tv.amwa.maj.union.DefaultFade testFade =
			(tv.amwa.maj.union.DefaultFade) o;
		
		if (testFade.getFadeLength() != fadeLength) return false;
		if (testFade.getFadeType() != fadeType) return false;
		if (!(fadeEditUnit.equals(testFade.getFadeEditUnit()))) return false;
		
		return true;
	}

	@Override
	public int hashCode() {
		
		return (new Long(fadeLength)).hashCode() ^
			~fadeEditUnit.hashCode() ^
			fadeType.hashCode();
	}

	
	static final String DEFAULTFADE_TAG = "DefaultFade";
	static final String DEFAULTFADELENGTH_TAG = "DefaultFadeLength";
	static final String DEFAULTFADEEDITUNIT_TAG = "DefaultFadeEditUnit";
	static final String DEFAULTFADETYPE_TAG = "DefaultFadeType";
	
	public void appendXMLChildren(
			Node parent) {

		XMLBuilder.appendComment(parent, "The DefaultFade tag is not valid AAF XML.");
		
		Element referenceElement = XMLBuilder.createChild(parent, "http://www.amwa.tv/projects/maj",
				"maj", DEFAULTFADE_TAG);

		XMLBuilder.appendElement(referenceElement, CommonConstants.AAF_XML_NAMESPACE,
				CommonConstants.AAF_XML_PREFIX, DEFAULTFADELENGTH_TAG, fadeLength);
		XMLBuilder.appendElement(referenceElement, CommonConstants.AAF_XML_NAMESPACE,
				CommonConstants.AAF_XML_PREFIX, DEFAULTFADEEDITUNIT_TAG, fadeEditUnit.toString());
		XMLBuilder.appendElement(referenceElement, CommonConstants.AAF_XML_NAMESPACE,
				CommonConstants.AAF_XML_PREFIX, DEFAULTFADETYPE_TAG, fadeType.symbol());
	}
	
	public String getComment() {
		
		 return null;
	}
	
	/** 
	 * <p>Creates a pseudo-XML representation of this default fade value. The format is loosely
	 * based on the XML element representing a composition package but is not itself defined by an
	 * XML schema or DTD. For example:</p>
	 * 
	 * <pre>
	 * &lt;DefaultFade&gt;
	 *   &lt;DefaultFadeLength&gt;1000&lt;/DefaultFadeLength&gt;
	 *   &lt;DefaultFadeType&gt;FadeLinearAmp&lt;/DefFadeType&gt;
	 *   &lt;DefaultFadeEditUnit&gt;25/1&lt;/DefFadeEditUnit&gt;
	 * &lt;/DefaultFade&gt;
	 * </pre>
	 * 
	 * @return XML representation of this default fade value.
	 */
	@Override
	public String toString() {
		
		return XMLBuilder.toXMLNonMetadata(this);
	}
	
	@Override
	public DefaultFade clone() {
		
		try {
			return (DefaultFade) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new InternalError("Clone is supported. Should never get here.");
		}
	}
}
