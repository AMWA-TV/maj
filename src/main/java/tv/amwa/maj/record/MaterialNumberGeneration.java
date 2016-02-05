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
 * $Log: MaterialNumberGeneration.java,v $
 * Revision 1.3  2011/07/27 17:23:19  vizigoth
 * Removed import dependency on classes of the implementation package.
 *
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.1  2011/01/05 13:09:06  vizigoth
 * Created new forge for making record and union type values.
 *
 * Revision 1.1  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.3  2009/03/30 09:05:03  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2008/03/07 08:08:10  vizigoth
 * Edited comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:14:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.record;


/**
 * <p>Describes the method used to generate the material number part of a UMID, which form the last 16&nbsp;bytes
 * of a UMID value. The techniques represented by this enumeration are defined in appendix&nbsp;A of SMPTE&nbsp;330M.
 * The kind of material number generation can be specified when creating a UMID with the 
 * {@linkplain tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
 * UMID factory methods}.</p>
 *
 * <p>To find the corresponding byte value of the material number generation type, call
 * {@link #getMethodCode()}. The static method {@link #generationMethodFromCode(byte)} converts the
 * byte value code into a value of this enumeration.</p>
 * 
 * <p>The material number generation method is encoded within a UMID value. Call {@link PackageID#getMaterialNumberGenerator()}
 * to find out the kind of instance number generation in use.</p>
 * 
 * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, InstanceNumberGeneration, MaterialNumberGeneration)
 * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, AUID)
 * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, InstanceNumberGeneration, MaterialNumberGeneration, byte[])
 * 
 *
 *
 */
public enum MaterialNumberGeneration {

	/** 
	 * <p>Material number generation method is not defined. In this case, the MAJ API generates a 
	 * completely random UMID from 128&nbsp; bits of random data.</p.
	 */
	NotDefined((byte) 0x00),
	/** 
	 * <p>SMPTE material number generation method that uses the system clock, current date, a 
	 * a 2-byte random number and a 6&nbsp;byte system identifier.</p>
	 * 
	 * @see #Masked
	 */
	SMPTE((byte) 0x01),
	/** 
	 * <p>SMPTE material number generation methods that uses a given UUID/UL value to define 
	 * the material number.</p>
	 */
	UUID_UL((byte) 0x02),
	/** 
	 * <p>Masked SMPTE material number generation method based on the {@linkplain #SMPTE SMPTE method},
	 * obscuring the details of data using a MD5 hashing function to hide secure information.</p>
	 * 
	 * @see #SMPTE
	 */
	Masked((byte) 0x03),
	/** 
	 * <p>Material number generation method specified by the IEEE&nbsp;1394 network method, including
	 * the current date, time and an 8&nbsp;byte system identifier.</p>
	 */
	IEEE1394((byte) 0x04);

	/** Representation of internal byte value. */
	private byte method;
	
	/**
	 * <p>Create an instance of the enumeration as and when required.</p>
	 *
	 * @param method Byte code value representing the material number generation type.
	 */
	private MaterialNumberGeneration(byte method) {
		
		this.method = method;
	}

	/**
	 * <p>Returns the numeric code for the material number generation type, as defined in table&nbsp;3 
	 * of SMPTE&nbsp;330M.</p>
	 *
	 * @return Byte value associated with material number generation type.
	 */
	public byte getMethodCode() {
		
		return method;
	}

	/**
	 * <p>Converts a numeric code for a material number generation type into a value of this enumeration, 
	 * as defined in table&nbsp;3 of SMPTE&nbsp;330M.</p>
	 *
	 * @param code Code to convert to an enumeration value.
	 * @return Enumeration value corresponding to the code, or <code>null</code> if no corresponding
	 * enumeration value could be found.
	 */
	
	public final static MaterialNumberGeneration generationMethodFromCode(
			byte code) {
		
		for ( MaterialNumberGeneration generation : MaterialNumberGeneration.values() ) 
			if (generation.getMethodCode() == code) return generation;
			
		return null;
	}
	
}
