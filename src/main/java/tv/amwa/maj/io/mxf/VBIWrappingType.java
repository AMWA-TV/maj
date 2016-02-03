/* 
 **********************************************************************
 *
 * $Id: VBIWrappingType.java,v 1.1 2009/02/06 17:01:31 vizigoth Exp $
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
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
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
