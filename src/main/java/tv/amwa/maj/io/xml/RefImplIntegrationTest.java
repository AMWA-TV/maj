package tv.amwa.maj.io.xml;

import tv.amwa.maj.io.aaf.AAFFactory;
import tv.amwa.maj.model.Preface;

public class RefImplIntegrationTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
		throws Exception {
		// TODO Auto-generated method stub
		Preface preface = AAFFactory.readPreface(args[0]);
		
//		System.out.println(preface.getDictionaries().toString());
//		File outputFile = new File(args[1]);
		
		AAFElement documentRoot = new AAFElement(preface);
		String asXML = XMLBuilder.toXMLNonMetadata(documentRoot);
		
		System.out.println(asXML.substring(0, 10000));
//		
//		FileWriter fileWriter = new FileWriter(outputFile);
//		fileWriter.write(asXML);
//		fileWriter.close();
//		
//		XMLBuilder.writeStreams(documentRoot.getDocument(), outputFile);

	}

}
