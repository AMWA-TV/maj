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
 * $Log: EdgecodeValueTest.java,v $
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
 * Revision 1.2  2008/01/18 16:07:11  vizigoth
 * Changed header property of an edgecode value to optional.
 *
 * Revision 1.1  2007/11/13 22:16:39  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:31  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import tv.amwa.maj.enumeration.EdgeType;
import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.EdgeCodeValue;
import tv.amwa.maj.record.impl.EdgeCodeValueImpl;


public class EdgecodeValueTest {

	private static final EdgeCodeValue edgecodeDefault = new EdgeCodeValueImpl();
	private static final EdgeCodeValue edgecode = 
		new EdgeCodeValueImpl(321l, FilmType.Ft35MM, EdgeType.Keycode);
	private static final byte[] edgecodeHeader = 
				new byte[] { (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd' ,
							 (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h' };
	
	static {
		edgecode.setEdgeCodeHeader(edgecodeHeader);
	}
	
	@Test public void createDefaultEdgecode() {
		
		assertEquals(0l, edgecodeDefault.getEdgeCodeStart());
		assertEquals(FilmType.FtNull, edgecodeDefault.getEdgeCodeFilmFormat());
		assertEquals(EdgeType.Null, edgecodeDefault.getEdgeCodeFormat());

		try {
			edgecodeDefault.getEdgeCodeHeader();
			fail("Header property should not be omitted in a default value.");
		}
		catch (PropertyNotPresentException pnpe) { }
	}

	@Test public void createFromValues() {
		
		assertEquals(321l, edgecode.getEdgeCodeStart());
		assertEquals(FilmType.Ft35MM, edgecode.getEdgeCodeFilmFormat());
		assertEquals(EdgeType.Keycode, edgecode.getEdgeCodeFormat());
		assertTrue(
				Arrays.equals(
						new byte[] { (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
								 (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h' },
						edgecode.getEdgeCodeHeader()));
	}

	@Test public void enumResetTest() {
		
		@SuppressWarnings("unused")
		FilmType filmType = edgecode.getEdgeCodeFilmFormat();
		filmType = FilmType.Ft16MM;
		
		assertEquals(FilmType.Ft35MM, edgecode.getEdgeCodeFilmFormat());
	}
	
	@Test public void externalHeaderModification() {
		
		byte[] header = edgecode.getEdgeCodeHeader();
		header[2] = (byte) 'x';
		
		assertTrue(
				Arrays.equals(
						new byte[] { (byte) 'a', (byte) 'b', (byte) 'c', (byte) 'd',
								 (byte) 'e', (byte) 'f', (byte) 'g', (byte) 'h' },
						edgecode.getEdgeCodeHeader()));
	}

	@Test public void setAndGetStringHeader() {
		
		assertTrue(edgecode.getEdgeCodeHeaderAsString().equals("abcdefgh"));
		
		EdgeCodeValue testEdgecode = edgecode.clone();
		
		testEdgecode.setEdgeCodeHeader("DrWho732");
		assertTrue(testEdgecode.getEdgeCodeHeaderAsString().equals("DrWho732"));
		assertTrue(
				Arrays.equals(
						new byte[] { (byte) 'D', (byte) 'r', (byte) 'W', (byte) 'h',
									 (byte) 'o', (byte) '7', (byte) '3', (byte) '2' },
						testEdgecode.getEdgeCodeHeader()));
	}

	@Test public void edgecodeCloneEquality() {
		
		EdgeCodeValue cloned = edgecode.clone();
		
		assertFalse(edgecode == cloned);
		assertTrue(edgecode.equals(cloned));
		assertTrue(cloned.equals(edgecode));
	}
	
	@Test public void edgecodeCloneIndependent() {
		
		EdgeCodeValue cloned = edgecode.clone();
		
		assertEquals("abcdefgh", edgecode.getEdgeCodeHeaderAsString());
		cloned.setEdgeCodeHeader("CloneTest");
		assertEquals("abcdefgh", edgecode.getEdgeCodeHeaderAsString());		
	}

	@Test public void edgecodeEquals() {
		
		assertFalse(edgecode.equals(null));
		assertFalse(edgecode.equals(new Object()));
		
		assertTrue(edgecode.equals(edgecode));
		assertFalse(edgecode.equals(edgecodeDefault));
		assertFalse(edgecodeDefault.equals(edgecode));
	}

	@Test(expected=PropertyNotPresentException.class)
	public void createWithNullHeader() {
		
		EdgeCodeValueImpl testEdgecode = new EdgeCodeValueImpl(0l, FilmType.FtNull, EdgeType.Null);
		testEdgecode.getEdgeCodeHeader();
	}

	@Test(expected=NullPointerException.class)
	public void createWithNullEdgeType() {
		
		@SuppressWarnings("unused")
		EdgeCodeValueImpl testEdgecode = new EdgeCodeValueImpl(0l, FilmType.FtNull, null);
	}

	@Test(expected=NullPointerException.class)
	public void createWithNullFilmKind() {
		
		@SuppressWarnings("unused")
		EdgeCodeValueImpl testEdgecode = new EdgeCodeValueImpl(0l, null, EdgeType.Null);
	}

	public void setHeaderWithNullArray() {
		
		EdgeCodeValue testEdgecode = edgecode.clone();
		testEdgecode.setEdgeCodeHeader((byte[]) null);
	}

	@Test(expected=PropertyNotPresentException.class)
	public void setHeaderWithNullString() {
		
		EdgeCodeValue testEdgecode = edgecode.clone();
		testEdgecode.setEdgeCodeHeader((String) null);
		testEdgecode.getEdgeCodeHeader();
	}

	@Test(expected=NullPointerException.class) 
	public void setFilmKindWithNull() {
		
		EdgeCodeValue testEdgecodeValue = edgecode.clone();
		testEdgecodeValue.setEdgeCodeFilmFormat(null);
	}

	@Test(expected=NullPointerException.class)
	public void setCodeFormatWithNull() {
		
		EdgeCodeValue testEdgecodeValue = edgecode.clone();
		testEdgecodeValue.setEdgeCodeFormat(null);
	}
	
	@Ignore @Test public void toXML() { // FIXME make this test pass on windows
		
		String asXML = XMLBuilder.toXMLNonMetadata((XMLSerializable) edgecode);
		// System.out.println(asXML);
		assertTrue(asXML.contains("<aaf:EdgecodeValue xmlns:aaf=\"http://www.smpte-ra.org/schemas/2001-2/2007/aaf\">\n" +
				"  <aaf:EdgeCodeStart>321</aaf:EdgeCodeStart>\n" +
				"  <aaf:FilmKind>Ft35MM</aaf:FilmKind>\n" +
				"  <aaf:CodeFormat>EtKeycode</aaf:CodeFormat>\n" +
				"  <!--Header as text: 'abcdefgh'-->\n" +
				"  <aaf:EdgeCodeHeader>6162636465666768</aaf:EdgeCodeHeader>\n" +
				"</aaf:EdgecodeValue>"));
	}
}
