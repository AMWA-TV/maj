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
 * $Log: AAFReader.java,v $
 * Revision 1.4  2011/07/27 12:27:34  vizigoth
 * Removed unreferenced variables warning messages.
 *
 * Revision 1.3  2010/03/19 10:10:11  vizigoth
 * Added comment headers, tidied up and added a factory with a readPreface method.
 *
 */

package tv.amwa.maj.io.aaf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.POIFSDocument;
import org.apache.poi.poifs.filesystem.POIFSDocumentPath;
import org.apache.poi.poifs.property.DirectoryProperty;
import org.apache.poi.poifs.property.Property;
import org.apache.poi.poifs.property.PropertyTable;
import org.apache.poi.poifs.storage.BlockAllocationTableReader;
import org.apache.poi.poifs.storage.BlockList;
import org.apache.poi.poifs.storage.HeaderBlockReader;
import org.apache.poi.poifs.storage.RawDataBlockList;
import org.apache.poi.poifs.storage.SmallBlockTableReader;

public class AAFReader 
	extends POIFSReader {
	
	private AAFReaderListener readerListener = null;
	private boolean registryClosed = false;
	
    /**
     * Register an AAFReaderListener for all documents
     *
     * @param listener the listener to be registered
     *
     * @exception NullPointerException if listener is null
     * @exception IllegalStateException if read() has already been
     *                                  called
     */

	@Override
	public void registerListener(final POIFSReaderListener listener)
    {
        if (listener == null)
        {
            throw new NullPointerException();
        }
        if (registryClosed)
        {
            throw new IllegalStateException();
        }
        if (listener instanceof AAFReaderListener)
        	this.readerListener = (AAFReaderListener) listener;
        else
        	super.registerListener(listener);
    }
	
    /**
     * Read from an InputStream and process the documents we get
     *
     * @param stream the InputStream from which to read the data
     *
     * @exception IOException on errors reading, or on invalid data
     */

    public void read(
    		final InputStream stream)
        throws IOException
    {
    	if (readerListener == null) {
    		super.read(stream);
    		return;
    	}
    	
        registryClosed = true;

        // read the header block from the stream
        HeaderBlockReader header_block_reader = new HeaderBlockReader(stream);

        // read the rest of the stream into blocks
        RawDataBlockList  data_blocks         = new RawDataBlockList(stream, header_block_reader.getBigBlockSize());

        // set up the block allocation table (necessary for the
        // data_blocks to be manageable
        new BlockAllocationTableReader(header_block_reader.getBigBlockSize(),
        							   header_block_reader.getBATCount(),
                                       header_block_reader.getBATArray(),
                                       header_block_reader.getXBATCount(),
                                       header_block_reader.getXBATIndex(),
                                       data_blocks);

        // get property table from the document
        PropertyTable properties =
            new PropertyTable(header_block_reader.getBigBlockSize(),
            			 	  header_block_reader.getPropertyStart(),
                              data_blocks);

        // process documents
        processProperties(
        		SmallBlockTableReader.getSmallDocumentBlocks(
        				header_block_reader.getBigBlockSize(), data_blocks, properties.getRoot(), header_block_reader.getSBATStart()), 
        		data_blocks, 
        		properties.getRoot().getChildren(), 
        		new POIFSDocumentPath());
    }
	
	private void processProperties(
			final BlockList small_blocks,
			final BlockList big_blocks,
			final Iterator<Property> properties,
			final POIFSDocumentPath path)
		throws IOException {
		
		while (properties.hasNext()) {
			Property property = properties.next();
			String   name     = property.getName();

			if (property.isDirectory()) {
				
				POIFSDocumentPath new_path = new POIFSDocumentPath(path,
						new String[] { name });

				readerListener.processPOIFSReaderEvent(
							new AAFReaderEvent(null, path, property));
				
				processProperties(
						small_blocks, big_blocks,
						(( DirectoryProperty ) property).getChildren(), new_path);
				
				readerListener.processDirectoryEnd();
			}
			else {
				int startBlock = property.getStartBlock();

				if (readerListener != null) {
					int size = property.getSize();
					POIFSDocument document = null;

					if (property.shouldUseSmallBlocks()) {
						document =
							new POIFSDocument(name, small_blocks
									.fetchBlocks(startBlock, -1), size);
					}
					else {
						document =
							new POIFSDocument(name, big_blocks
									.fetchBlocks(startBlock, -1), size);
					}

					readerListener.processPOIFSReaderEvent(
								new AAFReaderEvent(
										new DocumentInputStream(document), path,
										property));
				}
				else {

					// consume the document's data and discard it
					if (property.shouldUseSmallBlocks()) {
						small_blocks.fetchBlocks(startBlock, -1);
					}
					else {
						big_blocks.fetchBlocks(startBlock, -1);
					}
				}
			}
		}
	}

}
