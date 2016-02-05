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
 * $Log: VBIWrappingType.java,v $
 * Revision 1.1  2009/02/06 17:01:31  vizigoth
 * Conversion of C headers to fields and stubs.
 *
 * 
 */
 
package tv.amwa.maj.io.mxf;

/**
 * <p>VBI wrapping type enumeration, as per SMPTE-436M.</p>
 * 
 *
 * 
 * @see VBILine
 * @see VBISource
 */
public enum VBIWrappingType {

	/** Interlaced or PsF frame. */
	VBIFrame, // = 1
	/** Field 1 of an interlaced picture. */
	VBIField1, // = 2
	/** Field 2 of an interlaced picture. */
	VBIField2, // = 3
	/** Progressive frame. */
	VBIProgressive // = 4
}
