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
 * $Log: TIFFDescriptor.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:12  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/02/28 12:50:32  vizigoth
 * Minor comment edits and fixes.
 *
 * Revision 1.2  2008/02/08 11:27:22  vizigoth
 * Edited comments to a release standard and minor comment fixes.
 *
 * Revision 1.1  2007/11/13 22:08:23  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.misctype.DataValue;
import tv.amwa.maj.misctype.JPEGTableIDType;


/**
 * <p>Specifies the description of a file of
 * video essence formatted according to the Tagged Image File Format (TIFF) specification.</p>
 * 
 * <p>For more information, see the <a href="http://en.wikipedia.org/wiki/TIFF">description of
 * TIFF on Wikipedia</a>.</p>
 * 
 *
 *
 * @see SourcePackage#getEssenceDescriptor()
 */

public interface TIFFDescriptor 
	extends AAFFileDescriptor {

	/** 
	 * <p>Default value for the leading lines property, which is
	 * {@value #LEADINGLINES_DEFAULT}.</p> 
	 * 
	 * @see #getLeadingLines()
	 * @see #setLeadingLines(Integer)
	 */
	public final static int LEADINGLINES_DEFAULT = 0;
	
	/** 
	 * <p>Default value for the trailing lines property, which is
	 * {@value #TRAILINGLINES_DEFAULT}.</p> 
	 * 
	 * @see #getTrailingLines()
	 * @see #setTrailingLines(Integer)
	 */
	public final static int TRAILINGLINES_DEFAULT = 0;
	
	/**
	 * <p>Sets whether the TIFF data described by this TIFF descriptor 
	 * has the same number of rows per strip throughout. Set to 
	 * <code>true</code> if it does; otherwise <code>false</code>.</p>
	 * 
	 * @param isUniform Does the TIFF data described by this TIFF descriptor
	 * have the same number of rows per strip throughout?
	 */
	public void setIsUniform(
			@Bool boolean isUniform);

	/**
	 * <p>Returns <code>true</code> if the TIFF data described by this
	 * TIFF descriptor has the same number of rows per strip throughout; 
	 * otherwise <code>false</code>.</p>
	 * 
	 * @return Does the TIFF data represented by the TIFF descriptor
	 * have the same number of rows per strip throughout?
	 */
	public @Bool boolean getIsUniform();

	/**
	 * <p>Sets whether essence data is stored in contiguous
	 * bytes for the TIFF data described by this TIFF descriptor. Set to
	 * <code>true</code> if the data is contiguous; otherwise 
	 * <code>false</code>.</p>
	 * 
	 * @param isContiguous Is the TIFF data represented by the TIFF descriptor 
	 * stored in contiguous bytes?
	 */
	public void setIsContiguous(
			@Bool boolean isContiguous);

	/**
	 * <p>Returns <code>true</code> if essence data is stored in contiguous
	 * bytes for the TIFF data described by this TIFF descriptor; otherwise
	 * <code>false</false>.</p>
	 * 
	 * @return Is the TIFF data represented by the TIFF descriptor stored in
	 * contiguous bytes?
	 */
	public @Bool boolean getIsContiguous();

	/**
	 * <p>Sets the number of leading lines of this TIFF descriptor, which
	 * specifies the number of leading lines to be thrown 
	 * away when viewing the TIFF data.</p>
	 * 
	 * <p>The optional property has a default value of {@value #LEADINGLINES_DEFAULT}. 
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param leadingLines Number of leading lines of this TIFF descriptor.
	 * 
	 * @see #LEADINGLINES_DEFAULT
	 * @see #setTrailingLines(Integer)
	 */
	public void setLeadingLines(
			@Int32 Integer leadingLines);

	/**
	 * <p>Returns the number of leading lines of the TIFF descriptor, which
	 * specifies the number of leading lines to be thrown 
	 * away when viewing the TIFF data.</p>
	 * 
	 * <p>If this optional property is omitted, its default value of 
	 * {@value #LEADINGLINES_DEFAULT} will be returned.</p>
	 * 
	 * @return Number of leading lines of the TIFF descriptor.
	 * 
	 * @see #LEADINGLINES_DEFAULT
	 * @see #getTrailingLines()
	 */
	public @Int32 int getLeadingLines();

	/**
	 * <p>Sets the number of trailing lines of this TIFF descriptor, which
	 * specifies the number of trailing lines to be thrown away when 
	 * viewing the TIFF data.</p>
	 * 
	 * <p>This optional property has a default value of {@value #TRAILINGLINES_DEFAULT}. 
	 * Set this optional property to <code>null</code> to omit it.</p>
	 * 
	 * @param trailingLines Trailing lines of this TIFF descriptor.
	 * 
	 * @see #TRAILINGLINES_DEFAULT
	 * @see #setLeadingLines(Integer)
	 */
	public void setTrailingLines(
			@Int32 Integer trailingLines);

	/**
	 * <p>Returns the number of trailing lines of this TIFF descriptor, which
	 * specifies the number of trailing lines to be thrown away when 
	 * viewing the TIFF data.</p>
	 * 
	 * <p>If this optional property is omitted, its default value of 
	 * {@value #TRAILINGLINES_DEFAULT} will be returned.</p>
	 * 
	 * @return Trailing lines of this TIFF descriptor.
	 * 
	 * @see #TRAILINGLINES_DEFAULT
	 * @see #getTrailingLines()
	 */
	public @Int32 int getTrailingLines();

	/**
	 * <p>Sets the JPEG table code property of this TIFF descriptor, which specifies
	 * the registered JPEG table code. Set this optional property to <code>null</code>
	 * to omit it and indicate that the described image data uses a table that is not registered.</p>
	 * 
	 * @param jpegTableId JPEG table code property of tis TIFF descriptor.
	 */
	public void setJPEGTableID(
			@JPEGTableIDType Integer jpegTableId);

	/**
	 * <p>Returns the JPEG table code property of this TIFF descriptor, which specifies
	 * the registered JPEG table code. This is an optional property that, when omitted,
	 * indicate that the described image data uses a table that is not registered.</p>
	 * 
	 * @return JPEG table code property of this TIFF descriptor.
	 * 
	 * @throws PropertyNotPresentException The optional JPEG table id property is
	 * not present in this TIFF descriptor.
	 */
	public @JPEGTableIDType int getJPEGTableID()
		throws PropertyNotPresentException;

	/**
	 * <p>Returns the copy of the TIFF IFD file information of this TIFF 
	 * descriptor, without the sample data.</p>
	 * 
	 * @return Copy of the TIFF IFD file information of the TIFF 
	 * descriptor.
	 */
	public @DataValue byte[] getTIFFSummary();

	/**
	 * <p>Returns the size of the buffer required by the {@link #getTIFFSummary()}
	 * method.</p>
	 * 
	 * @return Size of the buffer required by the {@link #getTIFFSummary()}
	 * method.
	 * 
	 * @deprecated Use {@link #getTIFFSummary()}<code>.length</code> instead.
	 */
	@Deprecated public @UInt32 int getSummaryBufferSize();

	/**
	 * <p>Sets the copy of the TIFF IFD file information of this TIFF descriptor, 
	 * without the sample data.</p>
	 * 
	 * @param tiffSummary Sets the TIFF IFD file information of this TIFF 
	 * descriptor.
	 * @throws NullPointerException The given TIFF IFD data is <code>null</code>.
	 */
	public void setTIFFSummary(
			@DataValue byte[] tiffSummary) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this TIFF descriptor.</p>
	 *
	 * @return Cloned copy of this TIFF descriptor.
	 */
	public TIFFDescriptor clone();
}
