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
 * $Log: RGBAComponentTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.5  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.4  2009/03/30 09:17:55  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/15 09:46:53  vizigoth
 * Improvements alongside documentation improvements.
 *
 * Revision 1.2  2008/01/14 21:16:58  vizigoth
 * Minor change due to refactoring of element names in RGBAComponentKind.
 *
 * Revision 1.1  2007/11/13 22:16:37  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:31  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import tv.amwa.maj.enumeration.RGBAComponentKind;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.RGBAComponent;
import tv.amwa.maj.record.impl.RGBAComponentImpl;


// TODO check and enhance tests

public class RGBAComponentTest {

	RGBAComponentImpl rgbaNullAndEmpty = null;
	RGBAComponentImpl rgba8BarBlues = null;
	
	final static String expectedXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"<aaf:RGBAComponent xmlns:aaf=\"http://www.smpte-ra.org/schemas/2001-2/2007/aaf\">\n" +
			"  <aaf:Code>CompBlue</aaf:Code>\n" +
			"  <aaf:ComponentSize>8</aaf:ComponentSize>\n" +
			"</aaf:RGBAComponent>\n";
	
	@Before
	public void setUp() {
		
		rgbaNullAndEmpty = new RGBAComponentImpl(RGBAComponentKind.Null, (byte) 0);
		rgba8BarBlues = new RGBAComponentImpl(RGBAComponentKind.Blue, (byte) 8);
	}
	
	@After
	public void tearDown() {
		rgbaNullAndEmpty = null;
		rgba8BarBlues = null;
	}
	
	@Test
	public final void testHashCode() {

		assertFalse(rgbaNullAndEmpty.hashCode() == 0);
		assertTrue(rgbaNullAndEmpty.hashCode() != rgba8BarBlues.hashCode());
	}

	@Test
	public final void testRGBAComponentRGBAComponentKindByte() {

		assertEquals((byte) 8, rgba8BarBlues.getComponentSize());
		assertEquals(RGBAComponentKind.Blue, rgba8BarBlues.getCode());
	}
	
	@Test(expected=NullPointerException.class)
	public final void testRGBAComponentRGBAComponentKindByteNull() {
		
		new RGBAComponentImpl(null, (byte) 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public final void testRGBAComponentRGBAComponentKindByteNegative() {
		
		new RGBAComponentImpl(RGBAComponentKind.None, (byte) -3);
	}
	
	@Test
	public final void testEqualsObject() {

		RGBAComponent aliensEqual = new RGBAComponentImpl(RGBAComponentKind.Blue, (byte) 8);
		
		assertFalse(aliensEqual.equals(null));
		assertTrue(aliensEqual.equals(aliensEqual));
		assertTrue(aliensEqual.equals(rgba8BarBlues));
		assertTrue(rgba8BarBlues.equals(aliensEqual));
	}

	@Ignore @Test // FIXME make this test pass on Windows
	public final void testToString() {
	
		assertEquals(expectedXML, rgba8BarBlues.toString());
	}

	@Test
	public final void testClone() {

		RGBAComponent cloned = rgba8BarBlues.clone();
		assertFalse(cloned == rgba8BarBlues);
		assertEquals(cloned, rgba8BarBlues);
		assertEquals(rgba8BarBlues, cloned);
		
		cloned.setComponentSize((byte) 4);
		assertEquals((byte) 4, cloned.getComponentSize());
		assertEquals((byte) 8, rgba8BarBlues.getComponentSize());
	}

	@Ignore @Test // FIXME make this test pass on Windows
	public final void testAppendXMLChildren() {

		assertEquals(expectedXML, XMLBuilder.toXMLNonMetadata(rgba8BarBlues));
	}

}
