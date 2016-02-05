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
 * $Log: VBISampleCoding.java,v $
 * Revision 1.1  2009/02/06 17:01:31  vizigoth
 * Conversion of C headers to fields and stubs.
 *
 * 
 */

package tv.amwa.maj.io.mxf;

/**
 * <p>Sample coding enumeration, as per SMPTE-436M.</p>
 * 
 *
 * 
 * @see VBILine
 * @see VBISource
 */

public enum VBISampleCoding {

	/** Luma only, 1&nbsp;bit per sample. */
	Y1Bit, // = 1,						
	/** Chroma only, 1&nbsp;bit per sample. */ 
	C1Bit, // = 2,						
	/** Luma and chroma, 1&nbsp;bit per sample. */
	YC1Bit, // = 3,						
	/** Luma only, 8&nbsp;bits per sample. */
	Y8Bit, // = 4,						
	/** Chroma only, 8&nbsp;bits per sample. */
	C8Bit, // = 5,						
	/** Luma and chroma, 8&nbsp;bits per sample. */
	YC8Bit, // = 6,						
	/** Luma only, &nbsp;bits per sample. */
	Y10Bit, // = 7,						
	/** Chroma only, 10&nbsp;bits per sample. */
	C10Bit, // = 8,						
	/** Luma and chroma, 10&nbsp;bits per sample. */
	YC10Bit, // = 9						

}
