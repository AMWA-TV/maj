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
 * $Log: ColorSpace.java,v $
 * Revision 1.4  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/01/08 17:01:49  vizigoth
 * Edited Javadoc comments to release standard and removed unused enumerations.
 *
 * Revision 1.1  2007/11/13 22:13:46  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.enumeration;

import tv.amwa.maj.industry.MediaEnumerationValue;
import tv.amwa.maj.integer.Int64;

/** 
 * <p>Specifies a kind of color space, such as "RGB" or "YCrCb". For more information, see the
 * <a href="http://en.wikipedia.org/wiki/Color_space#Generic_color_models>Wikipedia description
 * of generic color models</a>.</p>
 * 
 * <p>Original C name: <code>aafColorSpace_e</code></p>
 * 
 *
 */

public enum ColorSpace 
	implements MediaEnumerationValue {

	/**
	 * <p>Uses additive color mixing to describe what kind of light needs to be emitted to produce a 
	 * given color.</p>
	 */
    RGB (0, "RGB"),
    /**
     * <p>Used in PAL television representing a luminance value with two 
     * chrominance values. Similar to {@link #YIQ} except it is rotated through 33&nbsp;degrees.</p>
     */
    YUV (1, "YUV"), 
    /**
     * <p>Formerly used in NTSC television broadcasts representing a luminance value with two 
     * chrominance values, corresponding approximately to the amounts of blue and red in the color.</p>
     */
    YIQ (2, "YIQ"), 
    /**
     * <p>Hue, saturation and intensity with the lightness of a pure color equal to the lightness of a medium
     * gray.</p> 
     */
    HSI (3, "HSI"), 
    /**
     * <p>Hue, saturation and value with the lightness of a pure color equal to the brightness of white.</p> 
     */
    HSV (4, "HSV"), 
    /**
     * <p>Family of color spaces used in video and digital photography with Y representing the luma component, 
     * Cr the the color difference red component and Cb the color different blue component. YCrCb provides
     * an efficient means for the representation and transmission of RGB color data.</p>
     */
    YCrCb (5, "YCrCb"), 
    YDrDb (6, "YDrDb"), // TODO find out what this is!
    /**
     * <p>Uses subtractive color mixing as it describes what kind 
     * of inks need to be applied so the light reflected from the substrate and through the inks 
     * to produce a given color.</p>
     */
    CMYK (7, "CMYK"), 
    ;

    private final int value;
    private final String spaceName;

    ColorSpace (int value, String spaceName) { 
    	this.value = value; 
    	this.spaceName = spaceName;
    }

    @Int64 public long value() { return (long) value; }
    
    /** 
     * <p>Returns the name of this color space, for example "RGB".</p>
     * 
     * @return Colour space name.
     */
    public String colorSpaceName() { 
    	
    	return spaceName; 
    }

    public String symbol() { return name(); }
}
