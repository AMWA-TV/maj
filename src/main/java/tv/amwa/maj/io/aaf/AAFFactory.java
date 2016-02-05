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
 * $Log: AAFFactory.java,v $
 * Revision 1.10  2011/02/14 22:33:04  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.9  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.8  2010/11/08 16:48:17  vizigoth
 * Tidied up imports.
 *
 * Revision 1.7  2010/06/16 15:39:59  vizigoth
 * Minor fixes after testing AAF file output.
 *
 * Revision 1.6  2010/06/16 09:36:21  vizigoth
 * First version that writes readable AAF files.
 *
 * Revision 1.5  2010/06/15 19:04:10  vizigoth
 * Further improvements allowing generated AAF file to be read in part.
 *
 * Revision 1.4  2010/06/14 17:12:16  vizigoth
 * Significant progress towards writing valid AAF files with MAJ.
 *
 * Revision 1.3  2010/05/19 12:58:28  vizigoth
 * Capability to write an AAF file that MAJ can read.
 *
 * Revision 1.2  2010/04/15 10:53:21  vizigoth
 * Found out that I need to close the file input stream ... POI doesn't do it for me.
 *
 * Revision 1.1  2010/03/19 10:10:11  vizigoth
 * Added comment headers, tidied up and added a factory with a readPreface method.
 *
 */

package tv.amwa.maj.io.aaf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.poi.hpsf.ClassID;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import tv.amwa.maj.extensions.avid.AvidFactory;
import tv.amwa.maj.model.Preface;

/**
 * <p>Read and write AAF structured storage binary files to and from MAJ {@linkplain Preface preface}
 * instances. Wherever possible, the methods of this class deal with understanding or constructing 
 * any necessary metadata dictionaries or dictionaries.</p>
 * 
 * <p>Note that MAJ uses an extended version of the Apache POIFS library. MAJ can read big and little endian
 * byte order files but only writes back in big endian.</p>
 * 
 *
 *
 */

public class AAFFactory 
	implements AAFConstants {

	/**
	 * <p>Read a complete preface from an AAF file, including all the content storage objects. This method does 
	 * not read any essence data apart from embedded streams.</p>
	 * 
	 * @param filename AAF file to read a preface object from.
	 * @return Preface object contained in the AAF file.
	 * 
	 * @throws IOException An exception was thrown when trying to read the given AAF file.
	 * 
	 * @see #writePreface(Preface, String)
	 */
	public final static Preface readPreface(
			String filename) 
		throws IOException {
		
		POIFSReader r = new AAFReader();
    
		AAFReaderListener eventReader = AAFBuilder.makeAAFEventReader();
		r.registerListener(eventReader);
		
		InputStream fis = null;
		try {
			File inputFile = new File(filename);
			eventReader.setFilePath(inputFile);
			fis = new FileInputStream(inputFile);

			r.read(fis);
    
			eventReader.resolveEntries();

			return eventReader.getPreface();
		}
		finally {
			if (fis != null)
				fis.close();
		}

	}

	/**
	 * <p>Write an AAF file with the given filename that is constructed from the given {@linkplain Preface preface}.<p>
	 * 
	 * <p>Note that this version of MAJ only supports writing metadata-only files.</p>
	 * 
	 * @param preface Preface to use to construct an AAF file from.
	 * @param outputFilename File path specification for the file to write the preface to.
	 * 
	 * @throws IOException An error occurred when writing the AAF file.
	 * 
	 * @see #readPreface(String)
	 */
	public final static void writePreface(
			Preface preface,
			String outputFilename) 
		throws IOException {
		
	    POIFSFileSystem outputFileSystem = new POIFSFileSystem();
	    
	    DirectoryEntry rootDir = outputFileSystem.getRoot();

	    preface.updateDictionaries();
//	    System.out.println(preface);
	    
	    AAFWriterListener aafWriter = AAFBuilder.makeAAFWriterListener();
	    DirectoryEntry metaDictionaryDir = rootDir.createDirectory(META_DICTIONARY_DIRNAME);
	    DirectoryEntry prefaceDir = rootDir.createDirectory(PREFACE_DIRNAME);
	    
	    AAFBuilder.generateAAFStructure(prefaceDir, aafWriter, preface);
	    AAFBuilder.generateMetaDictionary(metaDictionaryDir, aafWriter, preface);
	    rootDir.createDocument(PROPERTIES_STREAMNAME, 70, aafWriter);
	    rootDir.createDocument(REFERENCED_PROPERTIES_STREAMNAME, aafWriter.getReferencedPropertiesSize(), aafWriter);

//	    rootDir.setStorageClsid(
//	    		(outputFileSystem.getBigBlockSize() == 4096) ?
//	    				new AAFClassID(AAFSignatureSSBin4K) :
//	    					new AAFClassID(AAFSignatureSSBinary));

	    rootDir.setStorageClsid(new ClassID(rootEntryClassID, 0));
	    
	    FileOutputStream fos = null;
	    try {
	    	fos = new FileOutputStream(outputFilename);
	    	outputFileSystem.writeFilesystem(fos);
	    }
	    finally {
	    	if (fos != null)
	    		try { fos.close(); } catch (Exception e) { }
	    }
	    
	    // AAF puts a signature in bytes 8-23 of the file
	    // POIFS cannot write this
	    
	    RandomAccessFile fixFile = null;
	    FileChannel fixChannel = null;
		try {
			fixFile = new RandomAccessFile(outputFilename, "rw");
			fixChannel = fixFile.getChannel();

			fixChannel.position(8);
			if (outputFileSystem.getBigBlockSize() == 4096)
				fixChannel.write(ByteBuffer.wrap(AAFSignatureSSBin4KBytes));
			else
				fixChannel.write(ByteBuffer.wrap(AAFSignatureSSBinaryBytes));
		}
		finally {
			if (fixChannel != null) 
				fixChannel.close();
		}
	}
	
	public final static void main(
			String args[]) {
		
		if (args.length == 0) {
			System.out.println("Usage: java tv.amwa.maj.io.aaf.AAFFactory <aaf_file>");
			System.exit(1);
		}
		
		AvidFactory.registerAvidExtensions();
		
		String filename = args[0];
		try {
			Preface preface = null;
//			for ( int x = 0 ; x < 100 ; x++ ) {
				long startTime = System.currentTimeMillis();
				preface = readPreface(filename);
				long endTime = System.currentTimeMillis();

				System.out.println("INFO: Reading AAF file took " + (endTime - startTime) + "ms.");
//			}
			System.out.println(preface.toString());
		
//			AvidFactory.registerAvidExtensions();
//			writePreface(preface, args[1]);
		}
		catch (IOException ioe) {
			System.err.println("Error reading file " + filename + ": " + ioe.getMessage());
			ioe.printStackTrace();
			System.exit(1);
		}
	}
}
