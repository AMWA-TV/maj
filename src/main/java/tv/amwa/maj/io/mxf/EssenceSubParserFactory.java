/* 
 **********************************************************************
 *
 * $Id: EssenceSubParserFactory.java,v 1.2 2010/01/19 14:44:23 vizigoth Exp $
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
 * $Log: EssenceSubParserFactory.java,v $
 * Revision 1.2  2010/01/19 14:44:23  vizigoth
 * Major refactor to create a cleaner OO class structure and separate interface from implementation. Interim check in - work in progress.
 *
 * Revision 1.1  2009/02/10 08:55:30  vizigoth
 * Decision that best implementation is through an Interface.
 *
 *
 */

 package tv.amwa.maj.io.mxf;

/**
 * <p>Specifies a class that can create a new {@linkplain EssenceSubParser essence sub-parser} 
 * instance.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 * @see EssenceSubParser
 * @see EssenceParser
 */
public interface EssenceSubParserFactory {

	//! Build a new sub-parser of the appropriate type

	//virtual EssenceSubParserPtr NewParser(void) const = 0;

//	public EssenceSubParser createParser();
	
}
