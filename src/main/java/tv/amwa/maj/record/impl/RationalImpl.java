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
 * $Log: RationalImpl.java,v $
 * Revision 1.3  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.5  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.4  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.3  2008/10/15 09:45:54  vizigoth
 * Edited documentation to release standard.
 *
 * Revision 1.2  2008/03/07 08:08:11  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:14:36  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record.impl;

import java.io.Serializable;
import java.text.ParseException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import tv.amwa.maj.integer.Int32;
import tv.amwa.maj.io.xml.XMLSerializable;
import tv.amwa.maj.record.Rational;

/** 
 * <p>Implements a rational number by means of a Java integer numerator ({@link tv.amwa.maj.integer.Int32}) and a 
 * Java integer ({@link tv.amwa.maj.integer.Int32}) denominator.</p>
 *
 * <p>This implementation of the AAF rational number interface extends Java's {@link java.lang.Number}
 * class so that its integer and floating point value can be found, by methods such as {@link #intValue()}
 * and {@link #doubleValue()}. Numerical comparison with other numbers can take place using the
 * {@link #compareToNumber(Number)} method.</p>
 *
 *
 *
 * @see tv.amwa.maj.industry.TypeDefinitions#Rational
 * @see tv.amwa.maj.model.TimelineTrack#getEditRate()
 */
public final class RationalImpl 
	extends java.lang.Number
	implements Rational,
		Serializable,
		XMLSerializable,
		Cloneable,
		Comparable<Rational> {

	/**  */
	private static final long serialVersionUID = -7948422399183603311L;
	
	@Int32 private int numerator;
    @Int32 private int denominator;

    /**
     * <p>Create a rational number from its numerator and denominator.</p>
     * 
	 * @param numerator
	 * @param denominator
	 */
	public RationalImpl(
			@Int32 int numerator, 
			@Int32 int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/**
	 * <p>Convert a string representation of a rational value into a value of this type.
	 * The string representation is expected to take the form of the numerator, followed
	 * by a slash character ('<code>/</code>'), followed by the denominator, e.g. 
	 * "<code>-123/4567</code>".</p>
	 * 
	 * @param rationalAsString String to use to create a rational value.
	 * @return Corresponding rational value.
	 * 
	 * @throws NullPointerException The rational as string argument is <code>null</code>.
	 * @throws PraseException The string is not formatted as a rational number 
	 * representation.
	 * 
	 * @see #toString()
	 */
	public final static RationalImpl parseFactory(
			String rationalAsString)
		throws NullPointerException,
			ParseException {
		
		if (rationalAsString == null)
			throw new NullPointerException("Cannot create a rational value from a null string.");
		
		int slashIndex = rationalAsString.indexOf('/');
		if (slashIndex == -1)
			throw new ParseException("The given string does not contain the required slash ('/') separator character for a rational value.", 0);
		
		String topNumber = rationalAsString.substring(0, slashIndex);
		String bottomNumber = rationalAsString.substring(slashIndex + 1);
		
		int numerator, denominator;
		try {
			numerator = Integer.parseInt(topNumber);
		}
		catch (NumberFormatException nfeNumerator) {
			throw new ParseException("Number format exception thrown when parsing numerator: " + nfeNumerator.getMessage(), 0);
		}
		
		try{
			denominator = Integer.parseInt(bottomNumber);
		}
		catch (NumberFormatException nfeDenominator) {
			throw new ParseException("Number foramt exception thrown when parsing denominator:" + nfeDenominator.getMessage(), 0);
		}
		
		return new RationalImpl(numerator, denominator);	
	}
	
	/**
	 * <p>Create a rational number with a numerator of&nbsp;0 and a denominator of&nbsp;1.</p>
	 *
	 */
	public RationalImpl() {
		numerator = 0;
		denominator = 1;
	}
	
	public final @Int32 int getDenominator() {
		return denominator;
	}

	public final void setDenominator(
			@Int32 int denominator) {
		
		this.denominator = denominator;
	}

	public final @Int32 int getNumerator() {
		return numerator;
	}

	public final void setNumerator(
			@Int32 int numerator) {
		
		this.numerator = numerator;
	}

	public final void setRational(
			@Int32 int numerator, 
			@Int32 int denominator) {

		this.numerator = numerator;
		this.denominator = denominator;
	}

	public final int hashCode() {
		
		return numerator^denominator;
	}

	/** 
	 * <p>Tests to see if this rational value is equal to the given value. Two values
	 * are equals if and only if they are both rational values with the same numerator
	 * and denominator. For a numerical comparison, use 
	 * {@link #compareTo(tv.amwa.maj.record.Rational)}.</p>
	 * 
	 * @return Is the given value equal to this one?
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public final boolean equals(
			Object o) {
		
		if (o == null) return false;
		if (!(o instanceof Rational)) return false;
		
		Rational testRational = 
			(Rational) o;
		
		if (testRational.getDenominator() != denominator) return false;
		if (testRational.getNumerator() != numerator) return false;
		
		return true;
	}

	/**
	 * <p>Returns a string representation of this rational value as the numerator, followed by a 
	 * slash (/), followed by the denominator. For example, "1/4". The format is the same as that
	 * parsed by {@link #parseFactory(String)}.</p>
	 * 
	 * @return This rational value formatted as a string.
	 * 
	 * @see #parseFactory(String)
	 */
	public final String toString() {
		
		return numerator + "/" + denominator;
	}

	/** 
	 * <p>Returns the rational calculated as double value. In the event of a division by
	 * zero, a value of {@link Double#POSITIVE_INFINITY} is returned.</p>
	 * 
	 * @see java.lang.Number#doubleValue()
	 */
	@Override
	public final double doubleValue() {

		return ((double) numerator) / ((double) denominator);
	}

	/** 
	 * <p>Returns the rational calculated as float value. In the event of a division by
	 * zero, a value of {@link Float#POSITIVE_INFINITY} is returned.</p>
	 * 
	 * @see java.lang.Number#floatValue()
	 */
	@Override
	public final float floatValue() {

		return ((float) numerator) / ((float) denominator);
	}

	/** 
	 * <p>Calculates the integer value of the numerator divided by the denominator. If
	 * the magnitude of the numerator is less than the denominator, a value of 
	 * <code>0</code> is returned.</p>
	 * 
	 * @throws ArithmeticException The denominator is zero.
	 * 
	 * @see java.lang.Number#intValue()
	 */
	@Override
	public final int intValue() 
		throws ArithmeticException {
		
		return numerator / denominator;
	}

	/** 
	 * <p>Calculates the long value corresponding to the numerator devided by the 
	 * denominator. If the magnitude of the numerator is less than the denominator, a 
	 * value of <code>0</code> is returned.</p>
	 * 
	 * @throws ArithmeticException The denominator is 0.
	 * 
	 * @see java.lang.Number#longValue()
	 */
	@Override
	public final long longValue() 
		throws ArithmeticException {

		return ((long) numerator) / ((long) denominator);
	}

	/**
	 * <p>Numerically compare this rational with the given rational value.</p>
	 *
	 * @param o Value to compare with this one with.
	 * @return A value of <code>-1</code> if this value is less than the one given, 
	 * <code>1</code> if this value is greater than the one given and <code>0</code> if
	 * they are equal.
	 * 
	 * @throws NullPointerException Argument is null.
	 * 
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public final int compareTo(
			Rational o) 
		throws NullPointerException {

		if (o == null) 
			throw new NullPointerException("The given value to compare with this rational value is null.");
		
		double thisValue = doubleValue();
		double testValue = ((double) o.getNumerator()) / ((double) o.getDenominator());
		
		if (thisValue < testValue) return -1;
		if (thisValue > testValue) return 1;
		
		return 0;
	}

	/**
	 * <p>Numerically compare this value with another number.</p>
	 *
	 * @param n Number to compare with this one.
	 * @return A value of <code>-1</code> if this value is less than the one given, 
	 * <code>1</code> if this value is greater than the one given and <code>0</code> if
	 * they are equal.
	 * 
	 * @throws NullPointerException Argument is null.
	 */
	public final int compareToNumber(Number n) 
		throws NullPointerException {
		
		double thisValue = doubleValue();
		double testValue = n.doubleValue();
		
		if (thisValue < testValue) return -1;
		if (thisValue > testValue) return 1;
		
		return 0;		
	}
	
	public final Rational clone() {
		
		try {
			return (Rational) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// This class implements cloneable and so this should not happen
			cnse.printStackTrace();		
			return null;		
		}
	}

	public void appendXMLChildren(
			Node parent) {

		Document document = parent.getOwnerDocument();
		Text rationalText = document.createTextNode(toString());
		parent.appendChild(rationalText);
	}

	public String getComment() {
		
		return null;
	}
	
	public final static String MYSQL_COLUMN_DEFINITION = 
		"VARCHAR(30) CHARACTER SET ascii COLLATE ascii_general_ci";
	
	public final static String toPersistentForm(
			Rational rational) {
		
		if (rational == null) return null;
		return rational.toString();
	}
	
	public final static Rational fromPersistentForm(
			String rational) {
		
		if (rational == null) return null;
		try {
			return parseFactory(rational);
		}
		catch (ParseException pe) {
			return null;
		}
	}
	
}
