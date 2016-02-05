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
 *
 *
 * @see EssenceSubParser
 * @see EssenceParser
 */
public interface EssenceSubParserFactory {

	//! Build a new sub-parser of the appropriate type

	//virtual EssenceSubParserPtr NewParser(void) const = 0;

//	public EssenceSubParser createParser();
	
}
