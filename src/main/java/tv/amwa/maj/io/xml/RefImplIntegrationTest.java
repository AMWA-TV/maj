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
