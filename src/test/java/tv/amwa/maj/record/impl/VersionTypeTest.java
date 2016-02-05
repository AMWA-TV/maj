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
 * $Log: VersionTypeTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.4  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.3  2009/03/30 09:17:55  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/10/15 09:46:53  vizigoth
 * Improvements alongside documentation improvements.
 *
 * Revision 1.1  2007/11/13 22:16:38  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:31  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Test;

import tv.amwa.maj.record.VersionType;
import tv.amwa.maj.record.impl.VersionTypeImpl;


public class VersionTypeTest {

	public final static VersionTypeImpl version = 
		new VersionTypeImpl((byte) 152, (byte) 87);
	public final static VersionTypeImpl zero = new VersionTypeImpl();
	public final static VersionTypeImpl maxVersion = 
		new VersionTypeImpl((byte) 127, (byte) 127);
	
	public static String expectedXML = 
		"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		"<Version xmlns=\"http://www.aafassociation.org/aafx/v1.1/20070305\" xmlns:aaf=\"http://www.aafassociation.org/aafx/v1.1/20070305\">152.87</Version>\n";
	
	@Test public void createVersionWithValues() {
		
		assertEquals((byte) 152, version.getMajor());
		assertEquals((byte) 87, version.getMinor());
	}

	@Test public void createVersionDefault() {
		
		assertEquals((byte) 0, zero.getMajor());
		assertEquals((byte) 0, zero.getMinor());
	}
	
	@Test 
	public void versionNumberToString152dot87() {

		assertEquals("-104.87", version.toString());
	}
	
	@Test
	public void versionNumberToStringZero() {

		assertEquals("0.0", zero.toString());
	}
	
	@Test
	public void versionNumberToStringMax() {

		assertEquals("127.127", maxVersion.toString());
	}

	@Test public void versionNumberEquals() {
		
		assertEquals(false, version.equals(null));
		assertEquals(false, version.equals(new Object()));
		
		assertEquals(true, version.equals(version));
		assertEquals(true, version.equals(version.clone()));
		assertEquals(false, version.equals(zero));
		assertEquals(false, zero.equals(version));
	}

	@Test public void compareVersionsLess() {
		
		assertEquals(-1, version.compareTo(zero));
		assertEquals(-1, zero.compareTo(maxVersion));
		assertEquals(-1, 
				version.compareTo(new VersionTypeImpl(
						version.getMajor(),
						(byte) (version.getMinor() + 1))));
	}
	
	@Test public void compareVersionsSame() {
		
		assertEquals(0, zero.compareTo(zero));
		assertEquals(0, version.compareTo(version));
		assertEquals(0, maxVersion.compareTo(maxVersion));
		assertEquals(0, 
				version.compareTo(new VersionTypeImpl(
						version.getMajor(), 
						version.getMinor())));
	}
	
	@Test public void compareVersionsGreater() {
		
		assertEquals(1,
				version.compareTo(new VersionTypeImpl(
						version.getMajor(),
						(byte) (version.getMinor() - 1))));
		assertEquals(1, zero.compareTo(version));
		assertEquals(1, maxVersion.compareTo(zero));
		assertEquals(1, maxVersion.compareTo(version));
	}
	
	@Test public void versionNumberHashcode() {
		
		int zeroHashCode = zero.hashCode();
		int versionHashCode = version.hashCode();
		int maxVersionHashCode = maxVersion.hashCode();
		
		assertFalse((zeroHashCode == versionHashCode));
		assertFalse((zeroHashCode == maxVersionHashCode));
		assertFalse((versionHashCode == maxVersionHashCode));
	}

	@Test(expected=NullPointerException.class)
	public void compareToNull() {
		
		zero.compareTo(null);
	}

	@Test public void cloneVersionNumber() {
		
		VersionType cloned = version.clone();
		
		assertFalse(cloned == version);
		assertTrue(cloned.equals(version));
		assertTrue(version.equals(cloned));
		
		assertEquals(0, version.compareTo(cloned));
		assertEquals(0, cloned.compareTo(version));
	}

	@Test public void cloneVersionTypeExternal() {
		
		VersionType cloned = version.clone();
		assertEquals((byte) 152, version.getMajor());
		
		cloned.setMajor((byte) 255);
		assertEquals((byte) 152, version.getMajor());
		assertEquals((byte) 255, cloned.getMajor());
	}
	
	// Version numbers no formatted as 7.8 ... no need for XML
	/* @Test public void appendXMLChildren() {
		
		assertEquals(expectedXML, XMLBuilder.toXMLNonMetadata(version));
	} */
	
	@Test
	public void testParseVersion() 
		throws NullPointerException, ParseException {
		
		VersionTypeImpl parsed = VersionTypeImpl.parseFactory("9.8");
		
		assertEquals((byte) 9, parsed.getMajor());
		assertEquals((byte) 8, parsed.getMinor());
	}
	
	@Test(expected=ParseException.class)
	public void testParseVersionNoDot() 
		throws NullPointerException, ParseException {
		
		VersionTypeImpl.parseFactory("9-8");
	}
	
	@Test(expected=ParseException.class)
	public void testParseVersionNaN() 
		throws NullPointerException, ParseException {
		
		VersionTypeImpl.parseFactory("1.Short");
	}
	
	@Test(expected=ParseException.class)
	public void testParseVersionRange() 
		throws NullPointerException, ParseException {
		
		VersionTypeImpl.parseFactory("256.0");
	}

	@Test(expected=NullPointerException.class)
	public void testParseVersionNull() 
		throws NullPointerException, ParseException {
		
		VersionTypeImpl.parseFactory(null);
	}

}
