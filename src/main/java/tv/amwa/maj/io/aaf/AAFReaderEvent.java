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
 * $Log: AAFReaderEvent.java,v $
 * Revision 1.2  2010/03/19 10:10:11  vizigoth
 * Added comment headers, tidied up and added a factory with a readPreface method.
 *
 */

package tv.amwa.maj.io.aaf;

import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSDocumentPath;
import org.apache.poi.poifs.property.Property;

public class AAFReaderEvent {

	private DocumentInputStream stream;
	private POIFSDocumentPath path;
	private Property property;
	
	AAFReaderEvent(
			final DocumentInputStream stream,
            final POIFSDocumentPath path,
            final Property property) {
		
        this.stream = stream;
        this.path = path;
        this.property = property;
    }
    
    /**
     * @return the DocumentInputStream, freshly opened
     */

    public DocumentInputStream getStream()
    {
        return stream;
    }

    /**
     * @return the document's path
     */

    public POIFSDocumentPath getPath()
    {
        return path;
    }

    /**
     * @return the document's name
     */

    public String getName()
    {
        return property.getName();
    }
    
    /**
     * @return the property causing this event
     */
    public Property getProperty() {
    	
    	return property;
    }
 }
