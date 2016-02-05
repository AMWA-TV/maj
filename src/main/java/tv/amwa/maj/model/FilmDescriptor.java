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
 * $Log: FilmDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.8  2010/07/14 13:34:38  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.3  2008/02/08 11:27:26  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.2  2008/01/27 11:07:32  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:53  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt8;
import tv.amwa.maj.misctype.AAFString;
import tv.amwa.maj.record.Rational;

/**
 * <p>Specifies the description of film media. A {@linkplain SourcePackage}
 * described by a film descriptor is known as a <em>film source package</em>.</p>
 * 
 *
 * 
 * @see SourcePackage#getEssenceDescriptor()
 */

public interface FilmDescriptor 
	extends EssenceDescriptor {

	/**
	 * <p>Sets the film stock manufacturer property of the described film, which is 
	 * a string to display to end users indicating the manufacturer of the film stock. Set
	 * this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param filmStockManufacturer Film manufacturer of the described film.
	 */
	public void setFilmStockManufacturer(
			@AAFString String filmStockManufacturer);

	/**
	 * <p>Returns the film stock manufacturer of the described film, which is 
	 * a string to display to end users indicating the manufacturer of the film stock. This
	 * is an optional property.</p>
	 * 
	 * @return Film manufacturer of this film descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional film manufacturer property is not
	 * present for this film descriptor.
	 */
	public @AAFString String getFilmStockManufacturer()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the kind of film stock of the described film, which is a string to display to 
	 * end users indicating the manufacturer's brand designation for the film, such as "5247". Set
	 * this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param filmStockKind Kind of film stock of the described film.
	 */
	public void setFilmStockKind(
			@AAFString String filmStockKind);

	/**
	 * <p>Returns the kind of film stock of the described film, which is a string to display to 
	 * end users indicating the manufacturer's brand designation for the film, such as "5247". This is
	 * an optional property.</p>
	 * 
	 * @return Kind of film stock of the described film.
	 * 
	 * @throws PropertyNotPresentException The optional film model property is
	 * not present for this film descriptor.
	 */
	public @AAFString String getFilmStockKind()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the film format of the described film, according to its 
	 * {@linkplain tv.amwa.maj.enumeration.FilmType film type}. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * <p>Typical values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.FilmType#Ft35MM}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.FilmType#Ft16MM}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.FilmType#Ft65MM}</li>
	 * </ul>
	 * 
	 * @param filmFormat Film format of the described film.
	 */
	public void setFilmFormat(
			FilmType filmFormat);

	/** 
	 * <p>Returns the film format of the described film, according to its 
	 * {@linkplain tv.amwa.maj.enumeration.FilmType film type}.</p>
	 * 
	 * <p>Typical values include:</p>
	 * 
	 * <ul>
	 *  <li>{@link tv.amwa.maj.enumeration.FilmType#Ft35MM}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.FilmType#Ft16MM}</li>
	 *  <li>{@link tv.amwa.maj.enumeration.FilmType#Ft65MM}</li>
	 * </ul>
	 * 
	 * @return Film format of the described film.
	 * 
	 * @throws PropertyNotPresentException The optional film format property is not
	 * present for this film descriptor.
	 */
	public FilmType getFilmFormat()
		throws PropertyNotPresentException;

	/**
	 * <p>Set the frame rate of the described film, which specifies the frame rate 
	 * of the film measured in frames per second. Set this optional property to <code>null</code>
	 * to omit it.</p>
	 * 
	 * @param rate Frame rate of the described film.
	 * 
	 * @throws IllegalArgumentException The given frame rate is negative.
	 */
	public void setFrameRate(
			@UInt32 Integer rate)
		throws IllegalArgumentException;

	/**
	 * <p>Returns the frame rate of the described film, which specifies the frame rate 
	 * of the film measured in frames per second. This is an optional property.</p>
	 * 
	 * @return Frame rate of the described film.
	 * 
	 * @throws PropertyNotPresentException The optional frame rate property is not
	 * present for this film descriptor.
	 */
	public @UInt32 int getFrameRate()
		throws PropertyNotPresentException;

	/**
	 * <p>Set the number of perforations per frame on the film stock of the described
	 * film. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param perforationsPerFrame Perforations per frame of the described film.
	 */
	public void setPerforationsPerFrame(
			@UInt8 Byte perforationsPerFrame);

	/**
	 * <p>Returns the number of perforations per frame on the film stock of the described
	 * film. This is an optional property.</p>
	 *  
	 * @return Number of perforations per frame of the described film.
	 * 
	 * @throws PropertyNotPresentException The optional perforations per
	 * frame property is not present for this film descriptor.
	 */
	public @UInt8 byte getPerfortionsPerFrame()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the image aspect ratio of the described film, which specifies the ratio 
	 * between the horizontal size of the frame image and the vertical size of the frame 
	 * image. Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param aspectRatio Image aspect ratio of the described film.
	 */
	public void setFilmAspectRatio(
			Rational aspectRatio);

	/**
	 * <p>Returns the image aspect ratio of the described film, which specifies the ratio 
	 * between the horizontal size of the frame image and the vertical size of the frame 
	 * image. This is an optional property.</p>
	 * 
	 * @return Image aspect ratio of the described film.
	 * 
	 * @throws PropertyNotPresentException The optional image aspect ratio
	 * for this film descriptor is not present.
	 */
	public Rational getFilmAspectRatio()
		throws PropertyNotPresentException;

	/**
	 * <p>Sets the film gauge format of the described film. Set this optional property
	 * to <code>null</code> to omit it.</p>
	 *
	 * @param filmFormatName Film gauge format of the described film.
	 */
	public void setFilmFormatName(
			String filmFormatName);

	/**
	 * <p>Returns the film gauge format of the described film. This is an optional
	 * property.</p>
	 *
	 * @return Film gauge format of the described film.
	 * 
	 * @throws PropertyNotPresentException This optional film gauge property
	 * is not present for this film descriptor.
	 */
	public String getFilmFormatName()
		throws PropertyNotPresentException;
	
	// TODO AAF specification says batch number of the "tape" - suggest this is fixed!
	
	/**
	 * <p>Sets the film batch number for the described film. Set this optional
	 * property to <code>null</code> to omit it.</p>
	 * 
	 * @param filmBatchNumber Film batch number of the described film.
	 */
	public void setFilmBatchNumber(
			String filmBatchNumber);

	/**
	 * <p>Returns the film batch number for this film descriptor. This is an optional
	 * property.</p>
	 *
	 * @return Film batch number for the described film.
	 * 
	 * @throws PropertyNotPresentException The optional film batch number is
	 * not present for this film descriptor.
	 */
	public String getFilmBatchNumber()
		throws PropertyNotPresentException;
	
	/**
	 * <p>Create a cloned copy of this film descriptor.</p>
	 *
	 * @return Cloned copy of this film descriptor.
	 */
	public FilmDescriptor clone();
}
