/* 
 **********************************************************************
 *
 * $Id: AAFReaderListener.java,v 1.6 2011/10/05 17:14:30 vizigoth Exp $
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
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: AAFReaderListener.java,v $
 * Revision 1.6  2011/10/05 17:14:30  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.5  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2010/11/08 16:47:52  vizigoth
 * Added setFilePath method.
 *
 * Revision 1.3  2010/03/19 10:10:11  vizigoth
 * Added comment headers, tidied up and added a factory with a readPreface method.
 *
 */

package tv.amwa.maj.io.aaf;

import java.io.File;
import java.io.IOException;

import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;

import tv.amwa.maj.meta.ExtensionScheme;
import tv.amwa.maj.model.Preface;

// TODO I need a comment
public interface AAFReaderListener 
	extends POIFSReaderListener {

    /**
     * <p>Process an event generated while reading an AAF file.</p>
     *
     * @param event Event generated while reading an AAF file.
     */

    public void processPOIFSReaderEvent(
    		AAFReaderEvent event);	
    
    /**
     * <p>Deal with any tidying up at the end of processing a directory 
     * read from an AAF file.</p>
     * 
     */
    public void processDirectoryEnd();
    
	/**
	 * <p>Resolve any references left unresolved after processing an AAF file.</p>
	 */
	public void resolveEntries();

	/**
	 * <p>Return the preface read while reading an AAF file, also known as the 
	 * header. If no preface was found, this method returns <code>null</code>.</p>
	 * 
	 * @return Preface read from a recently processed AAF file.
	 */
	public Preface getPreface();
	
	/**
	 * <p>Returns the extension scheme created from unknown properties, types and classes read from
	 * the AAF file. These extensions are used to create 
	 * {@linkplain tv.amwa.maj.model.ApplicationPluginObject application plugin objects} from any
	 * properties that use these extensions. XML serialization uses the prefix <em>this</em>,
	 * which is why the extension scheme is known as <em>this extension scheme</em>.</p>
	 * 
	 * @return Extension scheme created from unknown {@linkplain MetaDefinition meta definitions} 
	 * found in the file.
	 * 
	 * @see tv.amwa.maj.industry.Warehouse#register(ExtensionScheme)
	 */
	public ExtensionScheme getThisExtensionScheme();
	
	public void setFilePath(
			File filePath)
		throws NullPointerException,
			IOException;
}
