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
 * $Log: PackageIDTest.java,v $
 * Revision 1.2  2011/01/20 17:40:50  vizigoth
 * Fixed up all record tests to the point where they all pass.
 *
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.2  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.1  2009/03/30 09:17:55  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2007/11/13 22:16:35  vizigoth
 * Public release of MAJ API.
 *
 * Revision 1.2  2007/11/13 21:22:31  vizigoth
 * Added AMWA license to all Java files.
 *
 */

package tv.amwa.maj.record.impl;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;

import org.junit.Test;

import tv.amwa.maj.enumeration.MaterialType;
import tv.amwa.maj.exception.GenerationMethodNotSupportedException;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.InstanceNumberGeneration;
import tv.amwa.maj.record.MaterialNumberGeneration;
import tv.amwa.maj.record.PackageID;
import tv.amwa.maj.record.impl.AUIDImpl;
import tv.amwa.maj.record.impl.PackageIDImpl;
import tv.amwa.maj.util.Utilities;

public class PackageIDTest {

	private final static byte[] forwardBytes;
	private final static byte[] backwardBytes;
	
	// Altered to match byte-swapping behaviour of reference implementation
	private final static String forwardUmidString = 
		"urn:smpte:umid:01020304.05060708.090a0b0c.0d0e0f10.11121314.15161718.191a1b1c.1d1e1f20";
	private final static String backwardUmidString = 
		"urn:smpte:umid:fffefdfc.fbfaf9f8.f7f6f5f4.f3f2f1f0.efeeedec.ebeae9e8.e7e6e5e4.e3e2e1e0";
	
	private final static String forwardUmidPersist =
		"11121314-1516-1718-191A-1B1C1D1E1F20-0E0F10-0D-0102030405060708090A0B0C";
	private final static String backwardUmidPersist = 
		"EFEEEDEC-EBEA-E9E8-E7E6-E5E4E3E2E1E0-F2F1F0-F3-FFFEFDFCFBFAF9F8F7F6F5F4";
	
	private final static PackageIDImpl forwardUmid;
	private final static PackageIDImpl backwardUmid;
	
	// Number of random UMIDs to generate to test uniqueness of random generation
	private final static int randomSampleTestSize = 1000;
	
	static {
		
		forwardBytes = new byte[32];
		for ( int u = 0 ; u < 32 ; u++) forwardBytes[u] = (byte) (u + 1);
		
		backwardBytes = new byte[32];
		for ( int u = 0 ; u < 32 ; u++) backwardBytes[u] = (byte) (-1 - u);

		forwardUmid = new PackageIDImpl(forwardBytes);
		backwardUmid = new PackageIDImpl(backwardBytes);
	}

	@Test public void packageIDToString() {
		
		assertEquals(
				forwardUmidString,
				forwardUmid.toString());
		assertEquals(
				backwardUmidString,
				backwardUmid.toString());
		@SuppressWarnings("unused")
		PackageIDImpl madeInAFactory = null;
		try {
			madeInAFactory = PackageIDImpl.umidFactory(
					MaterialType.NotIdentified, 
					MaterialNumberGeneration.SMPTE,
					InstanceNumberGeneration.CopyAndPseudoRandom16Bit,
					Utilities.createLocalHostID(6));
		}
		catch (GenerationMethodNotSupportedException gmnse) { }
	}

	@Test public void packageIDEquals() {
		
		assertFalse(forwardUmid.equals(null));
		assertFalse(forwardUmid.equals(new Object()));
		assertTrue(forwardUmid.equals(forwardUmid));
		assertFalse(forwardUmid.equals(backwardUmid));
		assertFalse(backwardUmid.equals(forwardUmid));
		
		assertTrue(forwardUmid.equals(forwardUmid.clone()));
	}

	@Test public void clonePackageID() {
		
		PackageID cloned = forwardUmid.clone();
		
		assertFalse(cloned == forwardUmid);
		assertTrue(cloned.equals(forwardUmid));
		assertTrue(forwardUmid.equals(cloned));
		
		cloned.setLength((byte) 0xfe);
		assertEquals((byte) 0x0d, forwardUmid.getLength());
	}

	@Test public void createNoParameters() {
		
		PackageIDImpl[] testPackageIDs = new PackageIDImpl[randomSampleTestSize];
		
		for ( int u = 0 ; u < randomSampleTestSize ; u++) 
			testPackageIDs[u] = new PackageIDImpl();	

		for ( int x = 0 ; x < randomSampleTestSize ; x++ )
			for ( int y = 0 ; y < randomSampleTestSize ; y++ ) {
				if (x == y) continue;
				assertFalse(testPackageIDs[x] == testPackageIDs[y]);
			}
	}

	@Test public void packageIDHashCode() {
		
		assertFalse(forwardUmid.hashCode() == backwardUmid.hashCode());
		assertTrue(forwardUmid.hashCode() == forwardUmid.clone().hashCode());
	}

	public void createWithBasicArgs() {
		
		AUIDImpl testAuid = new AUIDImpl(0x61626364, (short) 0x6566, (short) 0x6768, 
				new byte[] { 0x69, 0x6a, 0x6b, 0x6c,
							 0x6d, 0x6e, 0x6f, 0x60 });
		PackageIDImpl testPackageID = 
			new PackageIDImpl(PackageIDImpl.getBaseUniversalLabel(), (byte) 0x13, (byte) 0x20, (byte) 0x30, (byte) 0x40, testAuid);
		
		assertTrue(Arrays.equals(PackageIDImpl.getBaseUniversalLabel(), testPackageID.getUniversalLabel()));

		assertEquals((byte) 0x13, testPackageID.getLength());
		assertEquals((byte) 0x20, testPackageID.getInstanceHigh());
		assertEquals((byte) 0x30, testPackageID.getInstanceMid());
		assertEquals((byte) 0x40, testPackageID.getInstanceLow());
		
		assertTrue(testAuid.equals(testPackageID.getMaterial()));
		
		assertEquals(
				"urn:x-umid:060a2b340101010101010f00-13-203040-6162636465666768696a6b6c6d6e6f60",
				testPackageID.toString());
	}

	@Test(expected=NullPointerException.class)
	public void createWithNullLabel() {
		
		@SuppressWarnings("unused")
		PackageIDImpl testPackageID = new PackageIDImpl(null, (byte) 0x13, (byte) 0, (byte) 0, (byte) 0, new AUIDImpl());
	}

	@Test(expected=NullPointerException.class)
	public void createWithNullAuid() {
		
		@SuppressWarnings("unused")
		PackageIDImpl testPackageID = 
			new PackageIDImpl(PackageIDImpl.getBaseUniversalLabel(), (byte) 0x13, (byte) 0, (byte) 0, (byte) 0, null);
	}

	@Test(expected=NullPointerException.class)
	public void createWithNullByteArray() {
		
		@SuppressWarnings("unused")
		PackageIDImpl testPackageID = new PackageIDImpl(null);
	}

	@Test public void createWithShortArray() {
		
		byte[] aBitShort = new byte[30];
		System.arraycopy(forwardBytes, 0, aBitShort, 0, 30);

		PackageIDImpl testPackageID = new PackageIDImpl(aBitShort);
		assertEquals((byte) 0, testPackageID.getPackageIDValue()[30]);
		assertEquals((byte) 0, testPackageID.getPackageIDValue()[31]);
		assertEquals(
				"urn:smpte:umid:01020304.05060708.090a0b0c.0d0e0f10.11121314.15161718.191a1b1c.1d1e0000",
				testPackageID.toString());
	}

	@Test public void createWithLongArray() {
		
		byte[] aBitLong = new byte[34];
		System.arraycopy(forwardBytes, 0, aBitLong, 2, 32);
		aBitLong[0] = (byte) 0xfa;
		aBitLong[1] = (byte) 0xfb;
		
		PackageIDImpl testPackageID = new PackageIDImpl(aBitLong);
		assertEquals((byte) 0xfa, testPackageID.getPackageIDValue()[0]);
		assertEquals((byte) 0xfb, testPackageID.getPackageIDValue()[1]);
		assertEquals((byte) 0x01, testPackageID.getPackageIDValue()[2]);
		assertEquals(
				"urn:smpte:umid:fafb0102.03040506.0708090a.0b0c0d0e.0f101112.13141516.1718191a.1b1c1d1e",
				testPackageID.toString());
	}

	@Test public void createWithEmptyArray() {
		
		PackageIDImpl testPackageID = new PackageIDImpl(new byte[0]);
		assertTrue(testPackageID.isZero());
	}
	
	
	@Test public void setAndGetLabel() {
		
		PackageID testPackageID = forwardUmid.clone();
		testPackageID.setUniversalLabel(PackageIDImpl.getBaseUniversalLabel());
		assertTrue(Arrays.equals(PackageIDImpl.getBaseUniversalLabel(), testPackageID.getUniversalLabel()));
	}

	@Test public void setAndGetLength() {
		PackageID testPackageID = forwardUmid.clone();
		testPackageID.setLength((byte) 0xab);
		assertEquals((byte) 0xab, testPackageID.getLength());
	}

	@Test public void setAndGetInstance() {
		
		PackageID testPackageID = forwardUmid.clone();

		testPackageID.setInstanceHigh((byte) 0xab);
		testPackageID.setInstanceMid((byte) 0xac);
		testPackageID.setInstanceLow((byte) 0xad);
		
		assertEquals((byte) 0xab, testPackageID.getInstanceHigh());
		assertEquals((byte) 0xac, testPackageID.getInstanceMid());
		assertEquals((byte) 0xad, testPackageID.getInstanceLow());
	}

	@Test public void setAndGetMaterial() {
		
		byte[] fakedAuidBytes = new byte[16];
		System.arraycopy(backwardBytes, 8, fakedAuidBytes, 0, 16);
		AUIDImpl testAuid = new AUIDImpl(fakedAuidBytes);
		
		PackageID testPackageID = forwardUmid.clone();
		testPackageID.setMaterial(testAuid);
		assertTrue(testAuid.equals(testPackageID.getMaterial()));
		assertEquals(
				"urn:smpte:umid:01020304.05060708.090a0b0c.0d0e0f10.f7f6f5f4.f3f2f1f0.efeeedec.ebeae9e8",
				testPackageID.toString());
	}

	@Test public void setAndGetBytes() {
		
		byte[] fakedUpBytes = new byte[32];
		System.arraycopy(backwardBytes, 16, fakedUpBytes, 0, 16);
		System.arraycopy(forwardBytes, 0, fakedUpBytes, 16, 16);
		
		PackageIDImpl testPackageID = (PackageIDImpl) forwardUmid.clone();
		testPackageID.setPackageIDValue(fakedUpBytes);
		
		assertTrue(Arrays.equals(fakedUpBytes, testPackageID.getPackageIDValue()));
		assertEquals(
				"urn:smpte:umid:efeeedec.ebeae9e8.e7e6e5e4.e3e2e1e0.01020304.05060708.090a0b0c.0d0e0f10",
				testPackageID.toString());
	}
	
	@Test(expected=NullPointerException.class)
	public void setMaterialWithNull() {
		
		PackageID testPackageID = forwardUmid.clone();
		testPackageID.setMaterial(null);
	}

	@Test(expected=NullPointerException.class)
	public void setLabelWithNull() {
		
		PackageID testPackageID = forwardUmid.clone();
		testPackageID.setUniversalLabel(null);
	}

	@Test(expected=NullPointerException.class) 
	public void setValueWithNull() {
		
		PackageIDImpl testPackageID = (PackageIDImpl) forwardUmid.clone();
		testPackageID.setPackageIDValue(null);
	}

	@Test public void smpteGeneration() {

		byte[] hostID = Utilities.createLocalHostID(6);
		AUID[] materialNumbers = new AUID[100];
		for ( int x = 0 ; x < 100 ; x++ )
			materialNumbers[x] = 
				PackageIDImpl.materialGenerationSMPTE(hostID);

		for ( int x = 0 ; x < 100 ; x++) 
			for ( int y = x + 1 ; y < 100 ; y++)
				assertFalse(materialNumbers[x].equals(materialNumbers[y]));
		
	}

	@Test public void maskedGeneration() {
		
		byte[] hostID = Utilities.createLocalHostID(6);
		AUID[] materialNumbers = new AUID[100];
		for ( int x = 0 ; x < 100 ; x++ )
			materialNumbers[x] = 
				PackageIDImpl.materialGenerationMasked(hostID);

		for ( int x = 0 ; x < 100 ; x++) 
			for ( int y = x + 1 ; y < 100 ; y++)
				assertFalse(materialNumbers[x].equals(materialNumbers[y]));	
	}

	@Test 
	public void ieeeGeneration() 
		throws InterruptedException {
		
		byte[] hostID = Utilities.createLocalHostID(8);
		AUID[] materialNumbers = new AUID[1000];
		for ( int x = 0 ; x < 1000 ; x++ ) {
			materialNumbers[x] = 
				PackageIDImpl.materialGenerationIEEE1394(hostID);
			Thread.sleep(0, 50);
		}
		
		int clashCount = 0;
		for ( int x = 0 ; x < 1000 ; x++) {
			for ( int y = x + 1 ; y < 1000 ; y++) {
				
				assertNotNull(materialNumbers[y]);
				
				if (materialNumbers[x].equals(materialNumbers[y])) {
				
					System.err.println("Classhing material numbers at indexes " + x + " and " + y + ": " + 
							materialNumbers[x].toString());
					for ( int z = x - 1 ; z < y + 2 ; z++ )
						System.err.println("    " + z + ": " + materialNumbers[z].toString());
					clashCount++;
				}
			}
		}
		
		assertEquals("Identifier clashes detected.", 0, clashCount);
	}

	@Test public void umidStringRoundTrip() 
		throws NullPointerException, ParseException {
		
		PackageIDImpl decendingPackageID = new PackageIDImpl(backwardBytes);
		String decendingString = decendingPackageID.toString();
		PackageIDImpl parsedPackageID = PackageIDImpl.parseFactory(decendingString);
		assertEquals(decendingPackageID, parsedPackageID);
	}

	@Test
	public void oldStyleUMIDParse() 
		throws NullPointerException, ParseException {
		
		PackageID oldStyleDescending = 
			PackageIDImpl.parseFactory("urn:x-umid:fffefdfcfbfaf9f8f7f6f5f4-f3-f2f1f0-efeeedecebeae9e8e7e6e5e4e3e2e1e0");
		
		assertTrue(oldStyleDescending.equals(backwardUmid));
	}
	
	@Test public void toXML() {
		
		String asXML = XMLBuilder.toXMLNonMetadata(new PackageIDImpl(backwardBytes));
		assertTrue(asXML.contains(backwardUmidString));
	}
	
	@Test 
	public void generateMaskedUMID() {
		
		PackageIDImpl.umidFactory(
				MaterialType.NotIdentified, 
				MaterialNumberGeneration.Masked, 
				InstanceNumberGeneration.LocalRegistration, 
				new byte[] { 0, 1, 2, 3, 4, 5, 6, 7 } );
	}
	
	@Test
	public void testToPersistentFormForward() {
		
		assertEquals(forwardUmidPersist, PackageIDImpl.toPersistentForm(forwardUmid));
	}
	
	@Test
	public void testToPersistentFormBackward() {
		
		assertEquals(backwardUmidPersist, PackageIDImpl.toPersistentForm(backwardUmid));
	}
	
	@Test
	public void testFromPersistentFormForward() {
		
		System.out.println(forwardUmidPersist.length());
		assertTrue(forwardUmid.equals(PackageIDImpl.fromPersistentForm(forwardUmidPersist)));
	}
	
	@Test
	public void testFromPersistentFormBackward() {
		
		assertTrue(backwardUmid.equals(PackageIDImpl.fromPersistentForm(backwardUmidPersist)));
	}
	
	@Test
	public void testToPersistentFormZero() {
		
		assertEquals("00000000-0000-0000-0000-000000000000-000000-00-000000000000000000000000",
				PackageIDImpl.toPersistentForm(PackageIDImpl.getZeroPackageID()));
	}
	
	@Test
	public void testFromPersistentFormZero() {
		
		assertTrue(PackageIDImpl.getZeroPackageID().equals(
				PackageIDImpl.fromPersistentForm(
						"00000000-0000-0000-0000-000000000000-000000-00-000000000000000000000000")));
	}
}
