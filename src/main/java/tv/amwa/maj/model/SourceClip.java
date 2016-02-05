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
 * $Log: SourceClip.java,v $
 * Revision 1.5  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.4  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/19 11:28:36  vizigoth
 * Referenced method name in comment fixed.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.6  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.2  2008/02/08 11:27:19  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:43  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.FadeType;
import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.exception.PackageNotFoundException;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;


/**
 * <p>Specifies a representation of essence and identifies the source of the essence.
 * This interface uses the {@linkplain tv.amwa.maj.union.SourceReferenceValue source reference values} to
 * manage the properties of a source clip.</p>
 * 
 * <p>Source clips can by explicit, in context or indicating that the {@linkplain Package package} in which the
 * clip is contained is the original source. For more
 * information, see the {@link tv.amwa.maj.industry.Forge#originalSource()} and
 * {@link tv.amwa.maj.industry.Forge#inContextReference(int, Long)} methods.</p> 
 * 
 *
 *
 * @see tv.amwa.maj.union.SourceReferenceValue
 */

public interface SourceClip 
	extends SourceReferenceSegment {

	/**
	 * <p>This method returns the optional fade information for this
	 * source clip.  This function only applies to audio source clips.
	 * Length units are specified by the containing {@linkplain Track package 
	 * track's} edit rate.</p>
	 * 
	 * @return Fade information for this source clip.
	 * 
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see #getFadeInLength()
	 * @see #getFadeInType()
	 * @see #getFadeOutLength()
	 * @see #getFadeOutType()
	 */
	@Deprecated public tv.amwa.maj.union.Fade getFade();

	
	/**
	 * <p>Returns the fade in length of the fade. This is an optional property.</p>
	 * 
	 * @return Fade in length of the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property is not present in the fade.
	 * 
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see #getFade()
	 * @see tv.amwa.maj.union.Fade
	 */
	@Deprecated public @LengthType long getFadeInLength()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Returns the fade in type of the fade. This is an optional property.</p>
	 * 
	 * @return Fade in type of the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property
	 * is not present in the fade.
	 * 
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see #getFade()
	 * @see tv.amwa.maj.union.Fade
	 */
	@Deprecated public FadeType getFadeInType()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the fade out length of the fade. This is an optional property.</p>
	 * 
	 * @return Fade out length of the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property
	 * is not present in the fade.
	 * 
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see #getFade()
	 * @see tv.amwa.maj.union.Fade
	 */
	@Deprecated public @LengthType long getFadeOutLength()
		throws PropertyNotPresentException;
	
	
	/**
	 * <p>Returns the fade out type of the fade. This is an optional property.</p>
	 * 
	 * @return Fade out type for the fade.
	 * 
	 * @throws PropertyNotPresentException This optional property
	 * is not present in the fade.
	 * 
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see #getFade()
	 * @see tv.amwa.maj.union.Fade
	 */
	@Deprecated public FadeType getFadeOutType()
		throws PropertyNotPresentException;
	
	/** 
	 * <p>Returns the {@linkplain Package package} that this source clip references.</p>
	 * 
	 * @return Package that the source clip references.
	 * 
	 * @throws PackageNotFoundException The source reference of this source clip
	 * could not be resolved to a package.
	 */
	public Package resolveRef()
		throws PackageNotFoundException;

	/**
	 * <p>The method returns the {@linkplain tv.amwa.maj.union.SourceReferenceValue source reference} of this
	 * source clip. The source reference includes the start position property of this 
	 * source clip.</p>
	 * 
	 * @return Source reference of this source clip.
	 * 
	 * @see tv.amwa.maj.union.SourceReferenceValue#isContextual()
	 * @see tv.amwa.maj.union.SourceReferenceValue#isOriginalSource()
	 * @see tv.amwa.maj.union.SourceReferenceValue#getStartPosition()
	 */
	public tv.amwa.maj.union.SourceReferenceValue getSourceReference();
	
	/**
	 * <p>Sets the optional fade properties of this source clip.  The fade 
	 * properties only apply to a source clip of {@linkplain DataDefinition data
	 * definition} (or convertible to a data definition) of type sound.  All 
	 * arguments should be specified.  Length units are specified by the 
	 * containing {@linkplain Track track's} edit rate.</p>
	 * 
	 * @param fadeInLength Fade in length for the source clip.
	 * @param fadeInType Fade in type for the source clip.
	 * @param fadeOutLength Fade out length for the source clip.
	 * @param fadeOutType Fade out type for the source clip.
	 * 
	 * @throws BadLengthException One or both of the length parameters of the fade is/are negative.
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see Component#getComponentDataDefinition()
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Sound
	 * @see #setFade(tv.amwa.maj.union.Fade)
	 */
	@Deprecated public void setFade(
			@LengthType long fadeInLength,
			FadeType fadeInType,
			@LengthType long fadeOutLength,
			FadeType fadeOutType)
		throws BadLengthException;
	
	/**
	 * <p>Sets the optional fade properties of this source clip from a
	 * {@linkplain tv.amwa.maj.union.Fade set of fade properties}.  The fade properties 
	 * only apply to a source clip of {@linkplain DataDefinition data definition} (or convertible 
	 * to a data definition) of type sound.  Length units are specified by the 
	 * containing {@linkplain Track track's} edit rate.</p>
	 * 
	 * @param fade Parameters of a fade for this source clip.
	 * 
	 * @throws NullPointerException The given set of fade properties is <code>null</code>.
	 * 
	 * @deprecated The fade properties of a source clip are deprecated in the
	 * AAF specification. Use a {@linkplain Transition transition} with an appropriate
	 * effect instead.
	 * 
	 * @see Component#getComponentDataDefinition()
	 * @see tv.amwa.maj.constant.DataDefinitionConstant#Sound
	 * @see #setFade(long, FadeType, long, FadeType)
	 */
	@Deprecated public void setFade(
			tv.amwa.maj.union.Fade fade)
		throws NullPointerException;

	/**
	 * <p>Sets the {@linkplain tv.amwa.maj.union.SourceReferenceValue source reference} of this source clip.
	 * The source reference includes the start time property of this source clip.</p>
	 * 
	 * @param sourceReference Source reference of this source clip.
	 * 
	 * @throws NullPointerException The given source reference is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.industry.Forge#originalSource()
	 * @see tv.amwa.maj.industry.Forge#inContextReference(int)
	 * @see tv.amwa.maj.industry.Forge#inContextReference(int, Long)
	 * @see tv.amwa.maj.industry.Forge#makeReference(tv.amwa.maj.record.PackageID, int, Long)
	 */
	public void setSourceReference(
			tv.amwa.maj.union.SourceReferenceValue sourceReference) 
		throws NullPointerException;

	/**
	 * <p>Returns the position offset from the origin of the referenced {@linkplain tv.amwa.maj.model.Package package's} 
	 * {@linkplain tv.amwa.maj.model.Track track} in edit units determined by the 
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip's} 
	 * context. If the source package identifier of this value is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id}, then the start position shall 
	 * also be set to&nbsp;0.</p>
	 *
	 * @return Position offset from the origin of the referenced content.
	 * 
	 * @throws PropertyNotPresentException The optional start time property is not present,
	 * indicating that the reference is in the context of a {@linkplain tv.amwa.maj.model.StaticTrack static track}
	 * rather than a {@linkplain tv.amwa.maj.model.TimelineTrack timeline track} or 
	 * {@linkplain tv.amwa.maj.model.EventTrack event track}.
	 */
	public @PositionType long getStartPosition()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Sets the position offset from the origin of the referenced {@linkplain tv.amwa.maj.model.Package package's} 
	 * {@linkplain tv.amwa.maj.model.Track track} in edit units determined by the 
	 * {@linkplain tv.amwa.maj.model.SourceClip source clip's} 
	 * context. If the source package identifier of this value is the 
	 * {@linkplain tv.amwa.maj.industry.Forge#zeroPackageID() zero package id}, then the start position shall 
	 * also be set to&nbsp;0. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param setStartPosition Position offset from the origin of the referenced content.
	 */
	public void setStartPosition(
			@PositionType Long setStartPosition);
	
	/**
	 * <p>Create a cloned copy of this source clip.</p>
	 *
	 * @return Cloned copy of this source clip.
	 */
	public SourceClip clone();
}
