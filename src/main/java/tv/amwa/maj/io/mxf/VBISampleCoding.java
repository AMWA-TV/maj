/* 
 **********************************************************************
 *
 * $Id: VBISampleCoding.java,v 1.1 2009/02/06 17:01:31 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Matt Beard, Metaglue Corporation
 *
 **********************************************************************
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
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
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
