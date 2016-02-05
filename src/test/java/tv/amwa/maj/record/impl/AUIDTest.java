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
 * $Log: AUIDTest.java,v $
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
 * Revision 1.1  2007/11/13 22:16:34  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:31  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;


public class AUIDTest {

	private final static AUIDImpl zeroAuid = new AUIDImpl(0, (short) 0, (short) 0,
			new byte[] {(byte) 0, (byte) 0, (byte) 0, (byte) 0,
		                (byte) 0, (byte) 0, (byte) 0, (byte) 0 });
	private final static String zeroString = "urn:uuid:00000000-0000-0000-0000-000000000000";
	private final static String persistZero = "00000000-0000-0000-0000-000000000000";

	private final static AUIDImpl auidValue1 = new AUIDImpl(1, (short) 2, (short) 3,
			new byte[] { (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
		                 (byte) 8, (byte) 9, (byte) 10, (byte) 11 });
	private final static String stringValue1 = "urn:smpte:ul:060e2b34.08090a0b.00000001.00020003";
	private final static String persistForm1 = "00000001-0002-0003-060E-2B3408090A0B";
	// MAJ has become very previous about universal labels, ensuring they start with the correct 4 bytes
	// This is necessary as the AAF reference implementation sometimes emits them unswapped in XML documents

	private final static AUIDImpl auidMax = new AUIDImpl(-1, (short) -1, (short) -1,
			new byte[] { (byte) -1, (byte) -1, (byte) -1, (byte) -1,
		                  (byte) -1, (byte) -1, (byte) -1, (byte) -1 });
	private final static String stringMax = "urn:uuid:ffffffff-ffff-ffff-ffff-ffffffffffff";
	private final static String stringMaxMixed = "urn:uuid:ffFffFFF-ffff-FFFF-fFfF-fFfFfFfFfFfF";
	private final static String persistMax = "FFFFFFFF-FFFF-FFFF-FFFF-FFFFFFFFFFFF";

	@Test public void auidEquals() {
		assertEquals(zeroAuid.equals(zeroAuid), true);
		assertEquals(auidValue1.equals(auidValue1), true);
		assertEquals(auidMax.equals(auidMax), true);
		assertEquals(auidMax.equals(new Object()), false);
		assertEquals(zeroAuid.equals(auidValue1), false);
		assertEquals(zeroAuid.equals(null), false);

		AUIDImpl testAuid = new AUIDImpl(1, (short) 2, (short) 3,
				new byte[] { (byte) 0x06, (byte) 0x0e, (byte) 0x2b, (byte) 0x34,
                             (byte) 8, (byte) 9, (byte) 10, (byte) 11 });
		assertEquals(auidValue1.equals(testAuid), true);
	}

	@Test public void auidToStringValue1() {
		String result = auidValue1.toString();
		assertEquals(result, stringValue1);
	}

	@Test public void auidToStringMax() {

		@SuppressWarnings("unused")
		AUIDImpl auidMax2 = new AUIDImpl(-1, (short) -1, (short) -1,
				new byte[] { (byte) -1, (byte) -1, (byte) -1, (byte) -1,
			                  (byte) -1, (byte) -1, (byte) -1, (byte) -1 });

		String result = auidMax.toString();
		assertEquals(result, stringMax);
	}

	@Test public void auidToStringZero() {
		String result = zeroAuid.toString();
		assertEquals(result, zeroString);
	}

	@Test public void stringToAUIDValue1() {
		AUIDImpl testAuid = AUIDImpl.parseFactory(stringValue1);
		assertTrue(auidValue1.equals(testAuid));
	}

	@Test
	public void stringToAUIDValue1Old() {
		AUIDImpl testAuid = AUIDImpl.parseFactory("urn:x-ul:060e2b34.0809.0a0b.00000001.00020003");
		assertEquals(testAuid, auidValue1);
	}

	@Test public void stringToAUIDMax() {
		AUIDImpl testAuid = AUIDImpl.parseFactory(stringMax);
		assertEquals(testAuid, auidMax);
	}

	@Test public void stringToAUIDNil() {
		AUIDImpl testAuid = AUIDImpl.parseFactory(zeroString);
		assertEquals(testAuid, zeroAuid);
	}

	@Test public void stringToAUIDMixed() {
		AUIDImpl testAuid = AUIDImpl.parseFactory(stringMaxMixed);
		assertEquals(testAuid, auidMax);
	}

	@Test public void stringToAUIDURN() {
		AUIDImpl testAuid = AUIDImpl.parseFactory(stringValue1);
		assertEquals(auidValue1, testAuid);
	}

	/*
	 * This test checks that if the part of AUID URN before the value itself is
	 * provided, a NumberFormatException rather than a StringIndexOutOfBoundsException
	 * is thrown.
	 */
	@Test(expected=NumberFormatException.class)
	public void stringToAUIDURNOnly() {
		@SuppressWarnings("unused")
		AUIDImpl testAuid = AUIDImpl.parseFactory("urn:uuid:");
	}

	@Test(expected=NullPointerException.class)
	public void stringToAUIDNull() {
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(null);
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDEmpty() {
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory("");
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDTooShort() {
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(stringValue1.substring(0, stringValue1.length() - 1));
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDTooLong() {
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(stringValue1 + "a");
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDNonHexCharacter() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(4, 'g');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDBadSeparator1() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(8, '_');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDBadSeparator2() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(13, '_');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDBadSeparator3() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(18, '_');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDMinusInWrongPlace1() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(1, '-');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDMinusInWrongPlace2() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(9, '-');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDMinusInWrongPlace3() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(14, '-');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test(expected=NumberFormatException.class)
	public void stringToAUIDMinusInWrongPlace4() {
		StringBuffer sb = new StringBuffer(stringValue1);
		sb.setCharAt(19, '-');
		@SuppressWarnings("unused")
		AUIDImpl testAUID = AUIDImpl.parseFactory(sb.toString());
	}

	@Test public void nilAUIDTest() {

		assertEquals(zeroAuid.isNil(), true);
		assertEquals(auidMax.isNil(), false);
		assertEquals(auidValue1.isNil(), false);
	}

	@Test public void auidConstructorLargeArray() {
		AUIDImpl testAuid = new AUIDImpl(1, (short) 2, (short) 3,
				new byte[] {(byte) 64, (byte) 65, (byte) 66, (byte) 67,
						(byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72});
		byte[] truncated = testAuid.getData4();
		assertEquals(truncated.length, 8);

		for ( int u = 0 ; u < 8 ; u++ )
			assertEquals(truncated[u], (byte) (64 + u));
	}

	@Test public void auidConstructorSmallArray() {
		AUIDImpl testAuid = new AUIDImpl(1, (short) 2, (short) 3,
				new byte[] {(byte) 64, (byte) 65, (byte) 66, (byte) 67,
						(byte) 68, (byte) 69, (byte) 70});
		byte[] truncated = testAuid.getData4();
		assertEquals(truncated.length, 8);

		for ( int u = 0 ; u < 7 ; u++ )
			assertEquals(truncated[u], (byte) (64 + u));
		assertEquals(truncated[7], (byte) 0);

	}

	@Test public void auidConstructorEmptyArray() {
		AUIDImpl testAuid = new AUIDImpl(0, (short) 0, (short) 0, new byte[0]);

		byte[] created = testAuid.getData4();
		assertEquals(created.length, 8);

		for ( int u = 0 ; u < 8 ; u++ )
			assertEquals(created[u], (byte) 0);
	}

	@Test(expected=NullPointerException.class)
	public void auidConstructorNullArray()
		throws NullPointerException {
		@SuppressWarnings("unused")
		AUIDImpl testAuid = new AUIDImpl(0, (short) 0, (short) 0, null);
	}

	@Test public void auidConstructorRandom() {

		AUIDImpl testAuid1 = new AUIDImpl();
		AUIDImpl testAuid2 = new AUIDImpl();
		// System.out.println(testAuid1.toString());

		assertEquals(testAuid1.isNil(), false);
		assertEquals(testAuid2.isNil(), false);
		// The next test relies on the fact that statisicaly it is very unlikely that the
		// two values are equal ... however, there is always a very slight chance!
		assertFalse(testAuid1.equals(testAuid2));
	}

	@Test
	public final void auidRandomConstructurLayout() {

		checkByteLayout(new AUIDImpl(), 4);
	}

	@Test
	public final void auidRandomLayout() {

		checkByteLayout(AUIDImpl.randomAUID(), 4);
	}

	void checkByteLayout(
			AUIDImpl testAuid, int version) {

		// Check that the version and layout fields of the AUID are set correctly
		short data3 = testAuid.getData3();
		assertEquals(version, (data3 >>> 12));

		checkVariantLayout(testAuid);
	}

	void checkVariantLayout(
			AUIDImpl testAuid) {

		byte[] data4 = testAuid.getData4();
		assertEquals(128, (data4[0] & (128+64)));
	}

	@Test public void setAndGetData2() {

		AUIDImpl testAuid = new AUIDImpl();
		testAuid.setData2((short) 0x0123);
		assertEquals((short) 0x0123, testAuid.getData2());

		testAuid.setData2((short) 0xabcd);
		assertEquals((short) 0xabcd, testAuid.getData2());

		testAuid.setData2((short) 0xffff);
		assertEquals((short) 0xffff, testAuid.getData2());

		testAuid.setData2((short) 0xcd01);
		assertEquals((short) 0xcd01, testAuid.getData2());

		testAuid.setData2((short) 0x01cd);
		assertEquals((short) 0x01cd, testAuid.getData2());
	}


	@Test public void setAndGetData3() {

		AUIDImpl testAuid = new AUIDImpl();
		testAuid.setData3((short) 0x0123);
		assertEquals((short) 0x0123, testAuid.getData3());

		testAuid.setData3((short) 0xabcd);
		assertEquals((short) 0xabcd, testAuid.getData3());

		testAuid.setData3((short) 0xffff);
		assertEquals((short) 0xffff, testAuid.getData3());

		testAuid.setData3((short) 0xcd01);
		assertEquals((short) 0xcd01, testAuid.getData3());

		testAuid.setData3((short) 0x01cd);
		assertEquals((short) 0x01cd, testAuid.getData3());

	}

	@Test public void setAndGetData1() {

		AUIDImpl testAuid = new AUIDImpl();
		testAuid.setData1(0x01234567);
		assertEquals(0x01234567, testAuid.getData1());

		testAuid.setData1(0x00abcdef);
		assertEquals(0x00abcdef, testAuid.getData1());

		testAuid.setData1(0xffffffff);
		assertEquals(0xffffffff, testAuid.getData1());

		testAuid.setData1(0xabcd0123);
		assertEquals(0xabcd0123, testAuid.getData1());
	}

	@Test public void byteStorage() {

		AUIDImpl testAuid = new AUIDImpl();

		testAuid.setData1(0xab01cd02);
		testAuid.setData2((short) 0xef03);
		testAuid.setData3((short) 0x04ff);

		byte[] auidBytes = testAuid.getAUIDValue();
		assertEquals((byte) 0xab, auidBytes[0]);
		assertEquals((byte) 0x01, auidBytes[1]);
		assertEquals((byte) 0xcd, auidBytes[2]);
		assertEquals((byte) 0x02, auidBytes[3]);
		assertEquals((byte) 0xef, auidBytes[4]);
		assertEquals((byte) 0x03, auidBytes[5]);
		assertEquals((byte) 0x04, auidBytes[6]);
		assertEquals((byte) 0xff, auidBytes[7]);
	}

	@Test public void externalModificationAllBytes() {

		byte[] testExternal = auidValue1.getAUIDValue();

		testExternal[3] = (byte) 0xfe;
		assertEquals((byte) 1, auidValue1.getAUIDValue()[3]);
		assertEquals(1, auidValue1.getData1());
	}

	@Test public void externalModificationData4() {

		byte[] data4External = auidValue1.getData4();

		data4External[0] = (byte) 0xfe;
		assertEquals((byte) 0x06, auidValue1.getData4()[0]);
		assertEquals((byte) 0x06, auidValue1.getAUIDValue()[8]);
	}

	@Test public void auidHashCode() {

		assertFalse(auidValue1.hashCode() == auidMax.hashCode());

		AUID testAuid = auidValue1.clone();
		assertTrue(auidValue1.hashCode() == testAuid.hashCode());
	}

	@Test public void cloneAuid() {

		AUID cloned = auidValue1.clone();

		assertTrue(cloned.equals(auidValue1));
		assertTrue(auidValue1.equals(cloned));
		assertFalse(cloned == auidValue1);

		assertEquals(1, auidValue1.getData1());
		cloned.setData1(4567);
		assertEquals(1, auidValue1.getData1());
	}

	@Test public void isUniversalLabelTrue() {

		assertTrue(auidValue1.isUniversalLabel());
	}

	@Test public void isUniversalLabelFalse() {

		assertFalse(auidMax.isUniversalLabel());
		assertFalse(zeroAuid.isUniversalLabel());
	}

	@Test public void auidStringRoundTrip() {

		AUIDImpl auid = new AUIDImpl();
		AUIDImpl testAUID = AUIDImpl.parseFactory(auid.toString());
		assertEquals(auid, testAUID);
	}

	@Test public void toXML() {

		String asXML = XMLBuilder.toXMLNonMetadata(auidValue1);
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
			"urn:smpte:ul:060e2b34.08090a0b.00000001.00020003";
		assertEquals(expected, asXML);
	}

	@Test public void testFastAUIDGeneration() {

		int testComparisons = 100;

		AUIDImpl[] inSuccession = new AUIDImpl[testComparisons];

		for ( int x = 0 ; x < testComparisons ; x++) {
			inSuccession[x] = AUIDImpl.timebasedAUID();
		}

		testComparisons--;

		for ( int x = 0 ; x < testComparisons ; x++) {
			assertFalse(inSuccession[x].equals(inSuccession[x + 1]));
		}
	}

	@Test public void testNameBasedGeneration() {

		AUIDImpl nameBased = AUIDImpl.namebasedAUID("Richard Rogers".getBytes());
		assertEquals(AUIDImpl.parseFactory("urn:uuid:41844487-f0c8-3afd-942f-adccd917f59c"), nameBased);
	}

	@Test public void testNameBasedLayout() {

		AUIDImpl nameBased = AUIDImpl.namebasedAUID("Richard Rogers".getBytes());

		checkByteLayout(nameBased, 3);
	}

	@Test public void testTimeBasedLayout() {

		AUIDImpl timeBased = AUIDImpl.timebasedAUID();

		checkByteLayout(timeBased, 1);
	}

	@Test
	public final void testCompareAUIDsEqual() {

		assertEquals(0, auidValue1.compareTo(auidValue1));
	}

	@Test
	public final void testCompareAUIDsLess() {

		assertEquals(-1, zeroAuid.compareTo(auidValue1));
	}


	@Test
	public final void testCompareAUIDsGreater() {

		assertEquals(1, auidValue1.compareTo(zeroAuid));
	}

	@Test
	public final void testToPersistentFormZero() {

		assertEquals(persistZero, AUIDImpl.toPersistentForm(zeroAuid));
	}

	@Test
	public final void testToPersistentFormMax() {

		assertEquals(persistMax, AUIDImpl.toPersistentForm(auidMax));
	}

	@Test
	public final void testToPersistentFormValue1() {

		assertEquals(persistForm1, AUIDImpl.toPersistentForm(auidValue1));
	}

	@Test
	public final void testFromPersistentFormZero() {

		assertTrue(zeroAuid.equals(AUIDImpl.fromPersistentForm(persistZero)));
	}

	@Test
	public final void testFromPersistentFormValue1() {

		assertTrue(auidValue1.equals(AUIDImpl.fromPersistentForm(persistForm1)));
	}

	@Test
	public final void testFromPersistentFormMax() {

		assertTrue(auidMax.equals(AUIDImpl.fromPersistentForm(persistMax)));
	}
}
