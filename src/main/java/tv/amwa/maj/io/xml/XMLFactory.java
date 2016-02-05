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
 * $Log: XMLFactory.java,v $
 * Revision 1.5  2011/07/27 17:07:21  vizigoth
 * Additions and alterations coincidence with editing the Reg-XML document part 1.
 *
 * Revision 1.4  2011/02/14 22:32:58  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.3  2011/01/04 10:40:23  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.2  2010/11/08 16:42:15  vizigoth
 * Tidied up imports.
 *
 * Revision 1.1  2010/06/16 14:56:41  vizigoth
 * Towards better Reg XML support for complete documents ... still work in progress.
 *
 * Revision 1.4  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:07  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/15 14:16:08  vizigoth
 * Documentation improved to an early release level.
 *
 * Revision 1.1  2007/11/13 22:14:54  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.io.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import tv.amwa.maj.model.Preface;
import tv.amwa.maj.model.impl.IdentificationImpl;

// TODO comments
// TODO tests

/**
 * <p>Provides a means to read and write XML files from the local filing system.</p>
 * 
 * <p>THE DOCUMENTATION FOR THIS CLASS IS INCOMPLETE.</p>
 * 
 *
 */
public class XMLFactory {
	
	public final static Preface readPreface(
			String inputFileName) 
		throws FileNotFoundException,
			IOException, 
			NullPointerException, 
			SAXException {
		
		if (inputFileName == null)
			throw new NullPointerException("The given AAF XML file reference is null.");
		
		File aafxFile = new File(inputFileName);
		aafxFile = aafxFile.getAbsoluteFile();
		
		if (!(aafxFile.exists()))
			throw new FileNotFoundException("The given AAF XML file does not exist.");
		
		if (!(aafxFile.isFile()))
			throw new FileNotFoundException("The given AAF XML file name is not a file.");
		
		if (!(aafxFile.canRead()))
			throw new IOException("Insufficient privileges to read the given AAF XML file.");
		
		Map<String, InputStream> streams = XMLBuilder.parseDocTypeToStreams(aafxFile);
		
		return (Preface) XMLBuilder.createFromXML(
				new InputSource(new FileInputStream(aafxFile)),
				streams);
	}
	
	public final static void writePreface(
			Preface preface,
			String outputFileName) 
		throws NullPointerException,
			IOException {
		
		if (outputFileName == null)
			throw new NullPointerException("Cannot write to a file specified as null.");
		
		File aafxFile = new File(outputFileName);
		aafxFile = aafxFile.getAbsoluteFile();
		
//		if (aafxFile.exists())
//			throw new IOException("Cannot overwrite the given file as it already exists.");
	
		overwriteFile(aafxFile, preface);
	}
	
	public final static void overwriteFile(
			File aafxFile,
			Preface preface) 
		throws NullPointerException,
			IOException {
	
		if (aafxFile == null)
			throw new NullPointerException("Cannot write to a file specified as null.");
		if (preface == null)
			throw new NullPointerException("Cannot write to an AAF XML file with a null header.");

		if (!(aafxFile.getParentFile().canWrite()))
			throw new IOException("Cannot write to the directory of the given file.");
	
		if ((aafxFile.exists()) && (!aafxFile.canWrite()))
			throw new IOException("Insufficient privileges to write to the given file.");
		
		preface.updateDictionaries();
		preface.updateEssenceContainers();
		// TODO deal with updating descriptive schemes
		AAFElement documentRoot = new AAFElement(preface);
		String asXML = XMLBuilder.toXML(documentRoot, aafxFile);
		
		FileWriter fileWriter = new FileWriter(aafxFile);
		fileWriter.write(asXML);
		fileWriter.close();
		
		XMLBuilder.writeStreams(documentRoot.getDocument(), aafxFile);
	}
	
	public final static void main(String args[]) {
		
		if (args.length == 0) {
			System.out.println("Usage: java tv.amwa.maj.io.xml.XMLFactory <reg_xml_file>");
			System.exit(1);
		}

		try {
			Preface header = readPreface(args[0]);
			header.appendIdentification(new IdentificationImpl(
					IdentificationImpl.APICompanyName,
					IdentificationImpl.APIProductName,
					IdentificationImpl.APIProductVersionString,
					IdentificationImpl.APIProductID));
			System.out.println(header.toString());
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
