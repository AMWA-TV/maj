package tv.amwa.maj.example;
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
 * $Log: AMWADemoClass.java,v $
 * Revision 1.1  2011/02/14 22:30:55  vizigoth
 * Adding new example package.
 *
 * Revision 1.9  2011/01/13 17:44:31  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.8  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2010/11/18 10:42:29  vizigoth
 * Fixed to use new makeByName method in MediaEngine.
 *
 * Revision 1.6  2010/07/14 13:34:35  seanhowes
 * Clean up of test that are out of sync (@Ignore) and added mavenisation
 *
 * Revision 1.5  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/05/14 16:15:39  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:08  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.1  2007/11/15 14:39:45  vizigoth
 * Created simple demo for AMWA meetings, London Nov 2007.
 *
 * Revision 1.1  2007/11/13 22:15:52  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.3  2007/11/13 21:22:30  vizigoth
 * Added AMWA license to all Java files.
 *
 */


import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.model.*;


/**
 * <p>My first example of creating some AAF classes from scratch. This example is the main example used in 
 * the MAJ overview.</p>
 * 
 * <p>The example shows that at any point it is possible to interrogate the value of an object
 * using the <code>toString()</code> method. This is also the representation returned when using 
 * a debugging tool.</p>
 * 
 *
 *
 */
public class AMWADemoClass 
	implements tv.amwa.maj.constant.CommonConstants {

	public static void main(String[] args) throws Exception {

		MediaEngine.initializeAAF(); // Required to initialize AAF specified classes

	    MaterialPackage amwaPackage = Forge.makeByName(
	    		AAF_XML_NAMESPACE, "MaterialPackage",
	    		"PackageID", Forge.randomUMID(), // Randomly generated
	    		"Name", "AMWADemoPackage",
	    		"PackageLastModified", Forge.now(),
	    		"CreationTime", Forge.now());

	    Sequence amwaVideoSequence = Forge.makeByName(
	    		AAF_XML_NAMESPACE, "Sequence",
	    		"ComponentDataDefinition", "Picture");

	    amwaVideoSequence.appendComponentObject(
	    		Forge.make(
	    				SourceClip.class,
	    				"ComponentDataDefinition", "Picture",
	    				"ComponentLength", 60l,
	    				"SourcePackageID", "urn:smpte:umid:060c2b34.02051101.01001000.13000000.11ee08d4.040311d4.8e3d0090.27dfca7c",
	    				"SourceTrackID", 1,
	    				"StartPosition", 10l));

	    TimelineTrack amwaVideoTrack = Forge.make(
	    		TimelineTrack.class,
	    		"TrackID", 1,
	    		"TrackSegment", amwaVideoSequence,
	    		"EditRate", "25/1",
	    		"Origin", 0l);

	    amwaVideoTrack.setTrackName("AMWA VIDEO TRACK");

	    amwaPackage.appendPackageTrack(amwaVideoTrack);
	    amwaPackage.appendPackageUserComment("company", "portability 4 media");

	    System.out.println(amwaPackage.toString());
	}
}
