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
 * $Log: VersionType.java,v $
 * Revision 1.6  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.5  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.4  2010/06/18 09:37:16  vizigoth
 * Version was wrongly based on UInt8 instead of Int8. Fixed to match metadictionary.
 *
 * Revision 1.3  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2007/12/14 15:01:49  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:13:07  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record;

import tv.amwa.maj.integer.Int8;

/**
 * <p>Specifies a 2-byte version number, consisting of a major and minor component.</p>
 * 
 * <p>To make values of this type, use the following methods from the 
 * {@linkplain tv.amwa.maj.industry.Forge MAJ forge}:</p>
 * 
 * <ul>
 *  <li>From its component parts: 
 *  {@link tv.amwa.maj.industry.Forge#makeVersion(byte, byte)};</li>
 *  <li>A zero value: {@link tv.amwa.maj.industry.Forge#zeroVersion()};</li>
 *  <li>From a string representation as generated by {@link #toString()}: 
 *  {@link tv.amwa.maj.industry.Forge#parseVersion(String)}.</li>
 * </ul>  
 * 
 * @see tv.amwa.maj.industry.TypeDefinitions#VersionType
 * @see ProductVersion
 * 
 *
*/
public interface VersionType
	extends Comparable<VersionType> { 

	/**
	 * <p>Returns the major component of the version number.</p>
	 *
	 * @return Major component of the version number.
	 */
	public @Int8 byte getMajor();

	/**
	 * <p>Sets the major component of the version number.</p>
	 *
	 * @param major Major component of the version number.</p>
	 */
	public void setMajor(
			@Int8 byte major);

	/**
	 * <p>Returns the minor component of the version number.</p>
	 *
	 * @return Minor component of the version number.
	 */
	public @Int8 byte getMinor();

	/**
	 * <p>Sets the minor component of the version number.</p>
	 *
	 * @param minor Minor component of the version number.
	 */
	public void setMinor(
			@Int8 byte minor);
	
	/**
	 * <p>Compare this version number to the given version number.</p>
	 *
	 * @param o Version number to compare with this one.
	 * @return A value of <code>-1<code> if this version number is less than the given one, 
	 * <code>1</code> if this version is greater and <code>0</code> if the two version
	 * numbers and equal.
	 * 
	 * @throws NullPointerException The given value is <code>null</code>.
	 */
	public int compareTo(
			VersionType o) 
		throws NullPointerException;
	
	/**
	 * <p>Create a cloned copy of this version value.</p>
	 *  
	 * @return Cloned copy of this version value.
	 */
	public VersionType clone();
	
	/** 
	 * <p>Formats the version number as a string representation. This format is:</p>
	 * 
	 * <p><center>&lt;<em>major</em>&gt;<code>.</code>&lt;<em>minor</em>&gt;</center></p>
	 * 
	 * <p>Note that "<code>2.10</code>" is a later version than "<code>2.9</code>".</p>
	 * 
	 * <p>To convert the representation created by this method back into a version type
	 * value, use {@link tv.amwa.maj.industry.Forge#parseVersion(String)}.</p>
	 * 
	 * @see tv.amwa.maj.industry.Forge#parseVersion(String)
	 */
	public String toString();
}
