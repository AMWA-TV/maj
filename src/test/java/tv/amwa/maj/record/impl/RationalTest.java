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
 * $Log: RationalTest.java,v $
 * Revision 1.1  2011/01/20 11:15:09  vizigoth
 * Change of package name from embeddable to record.impl.
 *
 * Revision 1.3  2009/12/18 17:56:02  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.2  2009/03/30 09:17:55  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
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

import java.text.ParseException;

import org.junit.Test;

import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.record.impl.RationalImpl;



public class RationalTest {

	private static final RationalImpl zero = new RationalImpl();
	private static final RationalImpl broken = new RationalImpl(34, 0);
	private static final RationalImpl maxBigValue = new RationalImpl(Integer.MAX_VALUE, 1);
	private static final RationalImpl tinyValue = new RationalImpl(1, Integer.MAX_VALUE);
	private static final RationalImpl minBigValue = new RationalImpl(Integer.MAX_VALUE, -1);
	private static final RationalImpl rational = new RationalImpl(72, 4593);
			
	@Test public void createEmptyParameters() {
		
		assertEquals(0, zero.getNumerator());
		assertEquals(1, zero.getDenominator());
	}

	@Test public void createRational() {
		
		assertEquals(72, rational.getNumerator());
		assertEquals(4593, rational.getDenominator());
	}

	@Test
	public void findDoubleValue() {
		
		assertEquals(Double.POSITIVE_INFINITY, broken.doubleValue(), 0.000001);
	}

	@Test
	public void findFloatValue() {
		
		assertEquals(Float.POSITIVE_INFINITY, broken.floatValue(), 0.000001);
	}

	@Test(expected=ArithmeticException.class) 
	public void findIntegerValue() {

		System.out.println(broken.intValue());
	}

	@Test(expected=ArithmeticException.class) 
	public void findLongValue() {
		
		System.out.println(broken.longValue());
	}

	@Test public void rationalToString() {
		
		assertEquals("0/1", zero.toString());
		assertEquals("34/0", broken.toString());
		assertEquals("72/4593", rational.toString());
		assertEquals(Integer.toString(Integer.MAX_VALUE) + "/1", maxBigValue.toString());
	}

	@Test public void compareRationals() {
		
		assertEquals(0, zero.compareTo(zero));
		assertEquals(0, zero.compareTo(new RationalImpl(0, 2)));
		
		assertEquals(-1, zero.compareTo(rational));
		assertEquals(1, rational.compareTo(zero));
		
		assertEquals(-1, minBigValue.compareTo(maxBigValue));
		assertEquals(1, maxBigValue.compareTo(minBigValue));
		
		assertEquals(-1, zero.compareTo(tinyValue));
		assertEquals(1, tinyValue.compareTo(zero));
	}

	@Test(expected=NullPointerException.class)
	public void compareToNull() {
		
		zero.compareTo(null);
	}

	@Test public void rationalEquals() {
		
		assertFalse(zero.equals(null));
		assertFalse(zero.equals(new Object()));
		assertTrue(zero.equals(zero));
		assertFalse(zero.equals(new RationalImpl(0, 2)));
		assertFalse(zero.equals(rational));
		assertFalse(rational.equals(zero));	
	}

	@Test public void rationalHashCode() {
		
		assertFalse(zero.hashCode() == rational.hashCode());
		assertTrue(tinyValue.hashCode() == tinyValue.hashCode());
		assertFalse(minBigValue.hashCode() == maxBigValue.hashCode());
	}

	@Test public void rationalClone() {
		
		RationalImpl cloned = (RationalImpl) tinyValue.clone();
		assertTrue(cloned.equals(tinyValue));
		assertTrue(tinyValue.equals(cloned));
		
		assertEquals(0, cloned.compareTo(tinyValue));
		assertFalse(cloned == tinyValue);
	}	

	@Test public void rationalToDouble() {
		
		assertEquals(0.015676028739386023, rational.doubleValue(), 0.0000001);
		assertEquals(2.147483647e9, maxBigValue.doubleValue(), 0.0000001);
		assertEquals(-2.147483647e9, minBigValue.doubleValue(), 0.0000001);
		assertEquals(4.656612875245797e-10, tinyValue.doubleValue(), 0.0000001);
	}

	@Test public void rationalToFloat() {
		
		assertEquals((float) 0.015676029, rational.floatValue(), 0.0000001);
		assertEquals((float) 2.14748365e9, maxBigValue.floatValue(), 0.0000001);
		assertEquals((float) -2.14748365e9, minBigValue.floatValue(), 0.0000001);
		assertEquals((float) 4.65661288e-10, tinyValue.floatValue(), 0.0000001);
	}

	@Test public void rationalToInt() {
		
		assertEquals(0, zero.intValue());
		assertEquals(0, rational.intValue());
		assertEquals(Integer.MAX_VALUE, maxBigValue.intValue());
		assertEquals(0, tinyValue.intValue());
		assertEquals(Integer.MIN_VALUE, minBigValue.intValue() - 1);
	}

	@Test public void rationalToLong() {
		
		assertEquals(0l, zero.longValue());
		assertEquals(0l, rational.longValue());
		assertEquals((long) Integer.MAX_VALUE, maxBigValue.longValue());
		assertEquals(0l, tinyValue.longValue());
		assertEquals((long) Integer.MIN_VALUE, minBigValue.longValue() - 1);
	}

	@Test public void compareToNumber() {
		
		assertEquals(0, zero.compareToNumber(0));
		assertEquals(1, maxBigValue.compareToNumber(1234));
		assertEquals(-1, minBigValue.compareToNumber(1234.567));
	}
	
	@Test public void toXML() {
		
		String asXML = XMLBuilder.toXMLNonMetadata(rational);
		assertTrue(asXML.contains("72/4593"));
	}
	
	@Test
	public void testParseRational() 
		throws NullPointerException, ParseException {
		
		RationalImpl parsed = RationalImpl.parseFactory("-123/45678");
		
		assertEquals(-123, parsed.getNumerator());
		assertEquals(45678, parsed.getDenominator());
	}
	
	// TODO more tests of exceptional cases for parse rational
}