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
 * $Log: Utilities.java,v $
 * Revision 1.15  2011/10/05 17:14:31  vizigoth
 * Added support for application metadata plugins, package markers and dynamic metadictionary extraction from AAF files.
 *
 * Revision 1.14  2011/07/27 17:19:29  vizigoth
 * Added hexdump debugging utility for looking at the contents of byte buffers.
 *
 * Revision 1.13  2011/02/14 22:33:03  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.12  2011/01/19 11:37:07  vizigoth
 * Added missing comment to getLocalHostID().
 *
 * Revision 1.11  2011/01/04 10:42:32  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.10  2010/12/15 18:50:05  vizigoth
 * Moved character to byte parsing methods into common utility functions.
 *
 * Revision 1.9  2010/02/11 00:00:45  vizigoth
 * Added the ability to determine an ethernet address for use in ID generation.
 *
 * Revision 1.8  2009/12/18 17:55:59  vizigoth
 * Interim check in to help with some training activities. Early support for reading Preface objects from MXF files.
 *
 * Revision 1.7  2009/05/14 16:15:30  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:05:04  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.4  2007/12/12 12:52:07  vizigoth
 * Minor edit to comment.
 *
 * Revision 1.3  2007/12/04 20:16:40  vizigoth
 * Minor comment updates.
 *
 * Revision 1.2  2007/12/04 20:14:03  vizigoth
 * Added and edited documentation.
 *
 * Revision 1.1  2007/11/13 22:14:43  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>Static utilities shared by more than one class in the MAJ API and that may be of use more
 * generally. Included here are methods to: calculate Modified Julian Dates, work out SMPTE timezone codes, 
 * convert timezone offsets, generate sort of unique host identifiers and for the truncation and extension 
 * of byte arrays.</p>
 * 
 *
 */

public final class Utilities {

	/** Hashtable reprsenting table 2 of SMPTE S309M-1999, mapping from millisecond offset from UTC
	 * to the SMPTE timezone code. */
	private static Hashtable<Integer, Byte> timezoneTable;

	/** <p>Class containing static methods only that should not be instantiated.</p> */
	private Utilities() { }
	
	/**
	 * <p>Converts a number of hours, minutes and seconds and a corresponding sign value to an
	 * equivalent offset value from UTC (Universal Coordinated Time), measured in milliseconds.
	 * Timezone offsets available by calling methods of the {@link java.util.Calendar} class are
	 * measured in milliseconds.</p>
	 * 
	 * <p>Any sign value specified with the hours or minutes values are ignored and their magnitudes
	 * taken. Any positive value for the sign value, or zero, is taken to mean a positive offset
	 * from UTC, otherwise the value is considered a negative value offset from UTC. The maximum permitted
	 * offset from UTC is plus or minus 30 hours.</p> 
	 *
	 * @param sign Indicates a positive or negative offset from UTC.
	 * @param hours Hours part of the magnitude of the offset from UTC.
	 * @param minutes Minutes part of the magnitude of the offset from UTC.
	 * @return Offset from UTC represented in milliseconds.
	 * 
	 * @throws IllegalArgumentException The magnitude of one or both of the hours or minutes value is outside 
	 * the acceptable range.
	 * 
	 * @see java.util.Calendar#ZONE_OFFSET
	 * @see java.util.Calendar#DST_OFFSET
	 */
	public final static int hoursAndMinsToMilliseconds(
			int sign, 
			int hours, 
			int minutes)
		throws IllegalArgumentException {
		
		sign = (sign >= 0) ? 1 : -1;
		hours = (hours >= 0) ? hours : -hours;
		minutes = (minutes >= 0) ? minutes : -minutes;
		
		if ((hours > 30) || (minutes > 1800))
			throw new IllegalArgumentException("The magnitude of one or both of the hours or minutes value is outside the acceptable range.");
		return sign * (hours * 3600000 + minutes * 60000); 
	}
	
	// Fill the value table.
	static {
		
		timezoneTable = new Hashtable<Integer, Byte>();
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 0, 0), (byte) 0x00);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 1, 0), (byte) 0x01);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 2, 0), (byte) 0x02);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 3, 0), (byte) 0x03);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 4, 0), (byte) 0x04);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 5, 0), (byte) 0x05);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 6, 0), (byte) 0x06);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 7, 0), (byte) 0x07);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 8, 0), (byte) 0x08);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 9, 0), (byte) 0x09);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 10, 0), (byte) 0x10);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 11, 0), (byte) 0x11);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 12, 0), (byte) 0x12);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 13, 0), (byte) 0x13);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 12, 0), (byte) 0x14);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 11, 0), (byte) 0x15);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 10, 0), (byte) 0x16);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 9, 0), (byte) 0x17);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 8, 0), (byte) 0x18);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 7, 0), (byte) 0x19);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 6, 0), (byte) 0x20);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 5, 0), (byte) 0x21);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 4, 0), (byte) 0x22);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 3, 0), (byte) 0x23);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 2, 0), (byte) 0x24);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 1, 0), (byte) 0x25);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 0, 30), (byte) 0x0a);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 1, 30), (byte) 0x0b);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 2, 30), (byte) 0x0c);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 3, 30), (byte) 0x0d);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 4, 30), (byte) 0x0e);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 5, 30), (byte) 0x0f);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 6, 30), (byte) 0x1a);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 7, 30), (byte) 0x1b);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 8, 30), (byte) 0x1c);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 9, 30), (byte) 0x1d);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 10, 30), (byte) 0x1e);
		timezoneTable.put(hoursAndMinsToMilliseconds(-1, 11, 30), (byte) 0x1f);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 11, 30), (byte) 0x2a);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 10, 30), (byte) 0x2b);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 9, 30), (byte) 0x2c);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 8, 30), (byte) 0x2d);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 7, 30), (byte) 0x2e);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 6, 30), (byte) 0x2f);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 5, 30), (byte) 0x3a);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 4, 30), (byte) 0x3b);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 3, 30), (byte) 0x3c);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 2, 30), (byte) 0x3d);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 1, 30), (byte) 0x3e);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 0, 30), (byte) 0x3f);
		timezoneTable.put(hoursAndMinsToMilliseconds(1, 12, 45), (byte) 0x32);		
	}
	
	/**
	 * <p>Ensures that an array contains exactly the required number of bytes. If it does not, 
	 * it is either padded with 0s to the right to make it up to the required length or truncated
	 * at the required length to make it fit.</p>
	 * 
	 * <p>If the array passed in is <code>null</code>, an array of the required length containing 0's
	 * is created.</p>
	 * 
	 * @param data Array to check the length of.
	 * @param requiredLength Required length of the array.
	 * @return If the length is OK, a cloned copy of the given array. Otherwise, a replacement array 
	 * is created that is a padded or truncated version of the one provided to the required length.
	 * 
	 * @throws NegativeArraySizeException The required array length is negative, which is not
	 * allowed for an array length. 
	 * 
	 * @see tv.amwa.maj.record.PackageID
     * @see tv.amwa.maj.record.AUID
	 */
	public static final byte[] checkBytes(
			byte[] data, 
			int requiredLength) 
		throws NegativeArraySizeException {

		if (data == null) data = new byte[0];
		
		if (data.length != requiredLength) {
			
			byte[] replacement = new byte[requiredLength]; 
			for ( int u = 0 ; u < requiredLength ; u++ ) replacement[u] = (byte) 0;                           	
			                            
			if (data.length < requiredLength) {
				for ( int u = 0 ; u < data.length ; u++ ) 
					replacement[u] = data[u];
			}
			else {
				for ( int u = 0 ; u < requiredLength ; u++ )
					replacement[u] = data[u];
			}
			return replacement;
		}
		
		return data.clone();
	}

    /**
     * <p>Create and seed a secure random number generator. A number of different parameters that 
     * may be different system-to-system are combined with the current time in a mixing 
     * function to make a seed for the random number generator that is fairly difficult to guess.</p>
     * 
     * <p>No warranty is provided with the use of this method. The data used as a seed may be 
     * publicly available, as is this code. This method should not be used as part of any application 
     * for: secure business-to-business transactions; digital rights management; customer relationship 
     * management; healthcare; ecommerce.</p>
     * 
     * @return Random number generator seeded in a reasonably secure way.
     * 
     * @see tv.amwa.maj.record.PackageID
     * @see tv.amwa.maj.record.AUID
     * @see tv.amwa.maj.industry.Forge#randomAUID()
     * @see tv.amwa.maj.industry.Forge#randomUMID()
     */
    public final synchronized static SecureRandom seedRandomMaker() {
   
    	SecureRandom randomMaker = null;
    	
    	String username = System.getProperty("user.name");
    	long dateTime = (new java.util.Date()).getTime();
    	byte[] hostAddress = null;
    	String hostname = null;
    	try {
    		InetAddress hostInetAddress = java.net.InetAddress.getLocalHost();
    		hostAddress = hostInetAddress.getAddress();
    		hostname = hostInetAddress.getCanonicalHostName();
    	}
    	catch (UnknownHostException uhe) {
    		hostAddress = new byte[] {
    				System.getProperty("os.version").getBytes()[0],
    				System.getProperty("os.arch").getBytes()[0],
    				System.getProperty("os.name").getBytes()[0],
    				System.getProperty("java.vm.name").getBytes()[0] };
    		hostname = System.getProperty("user.dir");
    	}
    	
    	MessageDigest md = null;
    	try {
    		md = MessageDigest.getInstance("SHA");
    	}
    	catch (NoSuchAlgorithmException nsae) {
    		try {
    			randomMaker = SecureRandom.getInstance("SHA1PRNG");
    			randomMaker.setSeed(username.getBytes());
    			return randomMaker;
    		}
    		catch (NoSuchAlgorithmException nsae2) {
    			randomMaker = new SecureRandom(username.getBytes());
    			return randomMaker;
    		}
    	}
    	
    	md.update(username.getBytes());
    	
    	for ( int u = 0 ; u < 8 ; u++ ) {
    		md.update((byte) (dateTime & 255));
    		dateTime = dateTime >>> 8;
    	}
    	
    	md.update(hostAddress);
    	md.update(hostname.getBytes());
    	
    	byte[] seed = md.digest();
   
    	try {
    		randomMaker = SecureRandom.getInstance("SHA1PRNG");
    		randomMaker.setSeed(seed);
    	}
    	catch (NoSuchAlgorithmException nsae3) {
    		randomMaker = new SecureRandom(seed);
    	}

    	return randomMaker;
    }
    
    // Thanks to Al Kovalick's presentation on timing and web services for pointing out the 
    // original bug in this routine.

    /**
     * <p>Converts a date represented as day, month and year values into a Modified Julian Date value.
     * <a href="http://en.wikipedia.org/wiki/Modified_Julian_Date#Alternatives">Modified Julian Dates 
     * are described on Wikipedia</a> and the calculation of this method is based on the equations
     * provided on the Wikipedia page.</p>
     *
     * @param day Day part of the date to convert, in the range 1 to 31.
     * @param month Month part of the date to convert, in the range 1 to 12.
     * @param year Year part of the date convert, which must be 1754 A.D. or later to ensure no
     * confusion exists with dates skipped in the UK in 1752, corrected in 1753.
     * @return Date converted to a modified julian date representation.
     * 
     * @throws IllegalArgumentException One or more of the arguments is outside the acceptable range.
     * 
     * @see #compute7ByteTimeStamp(Calendar)
     */
    public final static int dmyToModifiedJulianDate(
    		int day, 
    		int month, 
    		int year) 
    	throws IllegalArgumentException {
    	
    	if ((day < 1) || (day > 31))
    		throw new IllegalArgumentException("Day is outside the acceptable range of 1 to 31.");
    	if ((month < 1) || (month > 12))
    		throw new IllegalArgumentException("Month is outside the acceptable range of 1 to 12.");
    	if (year < 1754)
    		throw new IllegalArgumentException("Year must be later than 1754 A.D..");
    	
    	int a = (int) Math.floor((14 - month) / 12);
    	long y = (long) year + (long) 4800 - (long) a;
    	long m = month + (12 * a) - 3;
    	
    	long julian = day + 
    		(long) ((153 * m + 2) / 5) +
    		(365 * y) +
    		(long) (y / 4) -
    		(long) (y / 100) +
    		(long) (y / 400) -
    		32045;

		int mjd = (int) (julian - 2400000.5);

		return mjd;
    }

    
    /**
     * <p>For a given offset from UTC measured in milliseconds, the timezone code defined in table&nbsp;2 of
     * SMPTE S309M-1999 is returned. If the given value does not match one defined in the table,
     * a value of <code>0x39</code> is returned, the <em>undefined</em> value. The offset value must
     * be accurate.</p>
     *
     * @param timezoneOffset Timezone offset from UTC, measured in milliseconds.
     * @return Timezone code corresponding to the given offset, or <code>0x39</code> if no directly corresponding
     * code can be found for the given offset.
     * 
     * @see tv.amwa.maj.record.PackageID
     */
    public final static byte timezoneCode(
    		int timezoneOffset) {
    	
    	if (!timezoneTable.containsKey(timezoneOffset)) 
    		return (byte) 0x39; // Undefined timezone code.
    	
    	return timezoneTable.get(timezoneOffset);
    }

    /**
     * <p>Generates a byte array of the given size that should be a value unique to the host
     * on which the current Java virtual machine is running. Java does not have an API call to 
     * access the unique hardware address of a system, so bytes compatible with an
     * <a href="http://en.wikipedia.org/wiki/MAC_Address">EUI-48 or EUI-64</a>
     * <em>locally administered</em> value is generated.</p>
     * 
     * <p>The four most significant bytes of the generated host id are set to be the four most 
     * significant bytes of the IP address of the current system. The least significant bits of
     * the value are taken from the most local part of the domain name for the system. This is done
     * with the intention that locally administered systems on separate private networks within 
     * different organisation should generate different ids.</p>
     * 
     * <p>If no local network information is available, other local information about the identification
     * of the machine is used instead. Note that this technique is likely to generate very similar
     * or clashing host ids for systems configured with the same operating system, Java virtual machine
     * and username.</p>
     * 
     * <p>This method is no substitute for administering local system identities directly or using
     * actual MAC addresses. The method should provide some degree of host id uniqueness within 
     * an organisation but should be used with caution if used as part of a public system or 
     * business-to-business applications. A much better approach is to pass the MAC address of
     * a system in to a Java virtual machine as a system property, including a unique virtual machine id
     * if the same system will be running more than one instance of an operating system in parallel.</p>
     *
     * @param size Number of bytes or host id required. Values of&nbsp;6 and&nbsp;8 are recommended as the only
     * values likely to produce reasonably reliable results.
     * @return An array of bytes extracted from some relatively unique properties of the current
     * system. Using this function on a set of systems on a network controlled by one organisation
     * should produce unique results for each different system. 
     * 
     * @throws NegativeArraySizeException Cannot create a unique host ID value with a negative array size.
     * 
     * @see tv.amwa.maj.industry.Forge#setLocalNodeID(byte[])
     * @see tv.amwa.maj.industry.Forge#timebasedAUID()
     * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, tv.amwa.maj.record.InstanceNumberGeneration, tv.amwa.maj.record.MaterialNumberGeneration)
     * @see #getLocalHostID()
     */
    public final static byte[] createLocalHostID(
    		int size) 
    	throws NegativeArraySizeException {
    	
    	if (size < 0)
    		throw new NegativeArraySizeException("Cannot create a unique host ID value with a negative array size.");

    	byte[] hostAddress = null;
    	String hostname = null;
    	try {	
    		InetAddress hostInetAddress = java.net.InetAddress.getLocalHost();
    		hostAddress = hostInetAddress.getAddress();
    		hostname = hostInetAddress.getCanonicalHostName();
    	}
    	catch (UnknownHostException uhe) {
    		hostAddress = new byte[] {
    				System.getProperty("os.version").getBytes()[0],
    				System.getProperty("os.arch").getBytes()[0],
    				System.getProperty("os.name").getBytes()[0],
    				System.getProperty("java.vm.name").getBytes()[0] };
    		hostname = System.getProperty("user.dir");
    	}

    	byte[] hostID = new byte[8];

    	// Try to extract the most local part of the domain name
    	int firstDot = hostname.indexOf('.');
    	if ((firstDot == -1) || (firstDot >= hostname.length() - 4)) {
    		hostID[0] = (byte) 'x';
    		hostID[1] = (byte) 'x';
    		hostID[2] = (byte) 'x';
    		hostID[4] = (byte) 'x';
    	}
    	else {
    		byte[] endOfTheDomain = hostname.substring(firstDot + 1, firstDot + 5).getBytes();
    		System.arraycopy(endOfTheDomain, 0, hostID, 0, 4);
    	}
    	
    	System.arraycopy(hostAddress, hostAddress.length - 4, hostID, 4, 4);
    		
    	// Set the locally administered flag (7th bit of the first byte is 1) and unicast (8th bit of
    	// the first byte is 0).
    	hostID[0] = (byte) ((((int) hostID[0]) & 0xfc) | 2);
    	
    	switch (size) {
    	
    	case 6:
    		byte[] shorter = new byte[6];
    		shorter[0] = hostID[0];
    		System.arraycopy(hostID, 3, shorter, 1, 5);
    		return shorter;
    	case 8:
    		return hostID;
    	default:
    		return checkBytes(hostID, size);
    	}
    }

    /**
     * <p>Generates an array of 6-bytes that is a fairly reliable identifier of the system on
     * which this code is running. If the an Ethernet address of one of the network interfaces
     * of this system is available, this is returned. Otherwise, the {@link #createLocalHostID(int)}
     * method is called to generate a value based on domain name and IP address.</p>
     * 
     * <p>The value returned is designed to be good enough for time-based UUID generation 
     * within a specific facility or well controlled environment. For rigorous control, 
     * a system administrator should use {@link tv.amwa.maj.industry.Forge#setLocalNodeID(byte[])}
     * to inject values known to be totally unique.</p>
     * 
     * @return Fairly good unique identifier for this system.
     * 
     * @see tv.amwa.maj.industry.Forge#setLocalNodeID(byte[])
     * @see tv.amwa.maj.industry.Forge#timebasedAUID()
     * @see tv.amwa.maj.industry.Forge#generatePackageID(tv.amwa.maj.enumeration.MaterialType, tv.amwa.maj.record.InstanceNumberGeneration, tv.amwa.maj.record.MaterialNumberGeneration)
	 * @see #createLocalHostID(int)
     */
    public final static byte[] getLocalHostID() {
    	
    	try {
    		Enumeration<NetworkInterface> netInterfaces = 
    			NetworkInterface.getNetworkInterfaces();
    		
    		byte[] candidateAddress = null;
    		
    		for ( ; netInterfaces.hasMoreElements() ; ) {
    			candidateAddress = netInterfaces.nextElement().getHardwareAddress();
    			if (!suspiciousHardwareAddress(candidateAddress)) break;
    			else candidateAddress = null;
    		}
    		
    		if (candidateAddress != null)
    			return candidateAddress;
    	}
    	catch (SocketException se) { }
    	
    	return createLocalHostID(6);
    }
    
    private final static boolean suspiciousHardwareAddress(
    		byte[] hardwareAddress) {
    	
    	if (hardwareAddress == null) return true;
    	
    	int zeros = 0;
    	for ( byte b : hardwareAddress )
    		if (b == 0) zeros++;
    	
    	return (zeros > 2) ? true : false;
    }
    
	/** Counter used to ensure that two consecutively generated IDs created within the same millisecond 
	 * on the same system have a different value. */
	private static transient int timeStampCounter = 0;
    
    /**
     * <p>Creates a 7 byte time stamp value suitable for use in the creation of unique identifiers. The
     * value generated uses the time stamp accessed through the given {@link java.util.Calendar} value, in 
     * combination with a local counter, to create 7 bytes representing the time stamp.</p>
     * 
     * <p>Making 24 consecutive thread-safe calls to this method with the same calendar value will result 
     * in 24 different byte arrays. Java only provides time resolution down to the nearest millisecond and so
     * multiple calls to this method on a fast system using the current system time would result in
     * the same value unless a local counter is used. However, many modern systems are more than
     * capable of calling this method more than 24 times each millisecond so it is recommended that any
     * identifier generated using this method is combined with the output a random number generator 
     * to achieve a satisfactory degree of uniqueness.</p>
     * 
     * <p>The value returned is found from a clock cycle through the day combined with a 
     * Modified Julian Date (MJD), as follows:</p>
     * 
     * <ul>
     *  <li>bytes 0 to 3 - An unsigned integer in network byte order representing a clock cycle value 
     *  ranging from 0 at the start of a day to 2073599999 at the end.<li>
     *  <li>byte 4 - Top nibble (4 most significant bits) are the units of the MJD and bottom nibble the
     *  tens of the MJD.</li>
     *  <li>byte 5 - Top nibble is the 100s of the MJD, bottom nibble is the 1000s.</li>
     *  <li>byte 6 - Top nibble is the 10000s of the MJD, bottom nibble is the 100000s.</li>
     * </ul>
     * 
     * <p>For example, to generate a 7-byte timestamp for right now, call:</p>
     * 
     * <p><center><code>compute7ByteTimeStamp(Calendar.getInstance());</code></center></p>
     *
     * @param timeStamp Java calendar value to use to generate a time stamp with.
     * @return 7-byte timestamp made from a daily clock cycle and Modified Julian Date computed from
     * the given time stamp.
     * 
     * @throws NullPointerException Cannot create a 7-byte timestamp with a <code>null</code> value.
     * 
     * @see #dmyToModifiedJulianDate(int, int, int)
     * @see tv.amwa.maj.record.PackageID
     */
    public synchronized final static byte[] compute7ByteTimeStamp(
    		Calendar timeStamp) 
    	throws NullPointerException {
    	
    	if (timeStamp == null)
    		throw new NullPointerException("Cannot create a 7-byte timestamp with a null calendar value.");
    	
		byte[] timeStampBits = new byte[7];
		
		timeStampCounter++;
		if (timeStampCounter == Integer.MAX_VALUE) timeStampCounter = 0;
		
		// Generates a count value between 0 and 2073599999. The time stamp counter aims to ensure
		// a degree of uniqueness within the same millisecond.
		int todaysCount = 
			timeStamp.get(Calendar.HOUR_OF_DAY) * (86400000) +
			timeStamp.get(Calendar.MINUTE) * (1440000) +
			timeStamp.get(Calendar.SECOND) * (24000) +
			timeStamp.get(Calendar.MILLISECOND) * 24 +
			timeStampCounter % 24;

		timeStampBits[0] = (byte) ((todaysCount >>> 24) & 255);
		timeStampBits[1] = (byte) ((todaysCount >>> 16) & 255);
		timeStampBits[2] = (byte) ((todaysCount >>> 8) & 255);
		timeStampBits[3] = (byte) (todaysCount & 255);
		
		int year = timeStamp.get(Calendar.YEAR);
		int month = timeStamp.get(Calendar.MONTH);
		int day = timeStamp.get(Calendar.DAY_OF_MONTH);

		int mjd = Utilities.dmyToModifiedJulianDate(day, month + 1, year);
		timeStampBits[4] = (byte) (((mjd % 10) << 4) | ((mjd / 10) % 10));
		timeStampBits[5] = (byte) ((((mjd / 100) % 10) << 4) | ((mjd / 1000) % 10));
		timeStampBits[6] = (byte) ((((mjd / 10000) % 10) << 4) | ((mjd / 100000) % 10));

		return timeStampBits;
    }
    
	/** <p>Hexidecimal character array used for encoding binary data.</p> */
	private final static char[] hexChars = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
	};
	
	private final static byte[] charToByte = new byte[127];
	
	static {
		
		for ( int x = 0 ; x < charToByte.length ; x++ )
			charToByte[x] = 0;
		
		for ( int x = 0 ; x < hexChars.length ; x++ )
			charToByte[hexChars[x]] = (byte) x;
		charToByte['a'] = (byte) 10;
		charToByte['b'] = (byte) 11;
		charToByte['c'] = (byte) 12;
		charToByte['d'] = (byte) 13;
		charToByte['e'] = (byte) 14;
		charToByte['f'] = (byte) 15;
	}
	
	/**
	 * <p>Convert binary data into a sequence of pairs of hexadecimal character values.</p>
	 *
	 * @param bytes Bytes to convert to a hex string.
	 * 
	 * @return Hex string representation of the given bytes.
	 * 
	 * @throws NullPointerException The given array of bytes is <code>null</code>.
	 * 
	 * @see #hexStringToBytes(String)
	 */
	public final static char[] bytesToHexChars(
			byte[] bytes) 
		throws NullPointerException {
		
		if (bytes == null)
			throw new NullPointerException("Cannot convert a null byte array to hex string.");
		
		char[] chars = new char[bytes.length * 2];
		
		int charCounter = 0;
		for ( int x = 0 ; x < bytes.length ; x++ ) {
			chars[charCounter++] = hexChars[(bytes[x] >>> 4) & 0x0f];
			chars[charCounter++] = hexChars[bytes[x] & 0x0f];
		}
		
		return chars;
	}
	
	/**
	 * <p>Convert a string of hex digits to a Java byte array, assuming that each pair of 
	 * hex digits is the value of a byte in the array. The byte array will be halg the length
	 * of the given string.</p>
	 * 
	 * <p>For example, <code>010A10</code> is converted into <code>{&nbsp;(byte)&nbsp;1, (byte)&nbsp;10,
	 * (byte)&nbsp;16&nbsp;}</code>.</p> 
	 * 
	 * @param hexCharacters Hexadecimal characters to convert to a byte array.
	 * @return Byte array created from the given hexadecimal digits.
	 * 
	 * @throws NullPointerException The given string of hex digits is <code>null</code>.
	 * @throws IndexOutOfBoundsException The length of the given string of hexadecimal digits is not a multiple
	 * of&nbsp;2.
	 * 
	 * @see #bytesToHexChars(byte[])
	 */
	public final static byte[] hexStringToBytes(
			String hexCharacters) 
		throws NullPointerException,
			IndexOutOfBoundsException {

		if (hexCharacters == null)
			throw new NullPointerException("The given string of hex digits is null.");
		
		char[] chars = new char[hexCharacters.length()];
		hexCharacters.getChars(0, chars.length, chars, 0);

		byte[] bytes = new byte[(int) (chars.length / 2)];
		
		int charCounter = 0;
		for ( int x = 0 ; x < bytes.length ; x++ ) {
			bytes[x] = (byte) ((charToByte[chars[charCounter++]] << 4) | (charToByte[chars[charCounter++]]));
		}
		
		return bytes;
	}

	/**
	 * <p>Create the Java field name of a property by lowering its first letter. This method
	 * is useful for classes that defining their own object relational mappings. For example,
	 * a property called <em>CreationTime</em> is represented in the MAJ API with a private field
	 * called <em>creationTime</em>.</p>
	 * 
	 * @param camelString String in camel case.
	 * @return Same string but with the first character converted to lower case.
	 */
	public final static String lowerFirstLetter(
			String camelString) {
		
		if (camelString.length() == 0) return camelString;
		
		StringBuffer workingBuffer = new StringBuffer(camelString);
		workingBuffer.setCharAt(0, Character.toLowerCase(camelString.charAt(0)));
		
		return workingBuffer.toString();
	}

	/**
	 * <p>Create a byte value from its representation as two characters, one for the most
	 * significant 4 bits and one for the least significant 4 bits.</p>
	 *
	 * @param mostSignficantCharacter Hexidecimal character representing the 4 most significant bits
	 * of a byte value.
	 * @param leastSignficantCharacter Hexidecimal character representing the 4 least significant bits
	 * of a byte value.
	 * @return Byte representation of the two hexidecimal characters.
	 * 
	 * @throws NumberFormatException One or both of the characters is/are not hexidecimal 
	 * characters.
	 */
	public final static byte twoCharsToByte(
			char mostSignficantCharacter,
			char leastSignficantCharacter) 
		throws NumberFormatException {
		
		return (byte) ((byteFromCharacter(mostSignficantCharacter) << 4) | 
							byteFromCharacter(leastSignficantCharacter));
	}

	/**
	 * <p>Convert a character representing a hexidecimal digit to the 4 least significant bits
	 * of a byte value of equivalent value. This method accepts both upper and lower case
	 * hexidecimal digits.</p>
	 *
	 * @param c Hexidecimal character to convert.
	 * @return Equivalent byte value to the given hexidecimal digit.
	 * 
	 * @throws NumberFormatException The given character is not a hexidecimal digit.
	 */
	public final static byte byteFromCharacter(
			char c) 
		throws NumberFormatException {
		
		switch (c) {
		
		case '0': return 0;
		case '1': return 1;
		case '2': return 2;
		case '3': return 3;
		case '4': return 4;
		case '5': return 5;
		case '6': return 6;
		case '7': return 7;
		case '8': return 8;
		case '9': return 9;
		case 'a':
		case 'A': return 10;
		case 'b':
		case 'B': return 11;
		case 'c':
		case 'C': return 12;
		case 'd':
		case 'D': return 13;
		case 'e':
		case 'E': return 14;
		case 'f':
		case 'F': return 15;
		default:
			throw new NumberFormatException("Unexpected character " + c + " when parsing an AUID.");
	 	}
	}

	/**
	 * <p>Write a hexidecimal dump, with byte offsets and character conversions in the style of
	 * <code>hexdump -C</code>, to the standard output stream. The buffer is dumped from its current
	 * position to its limit. The buffer is rewound to the initial position.</p>
	 * 
	 * @param buffer Buffer to dump to the output stream.
	 * 
	 * @throws NullPointerException Cannot dump a <code>null</code> buffer.
	 */
	public final static void hexDump(
			ByteBuffer buffer) 
		throws NullPointerException {
	
		if (buffer == null)
			throw new NullPointerException("Cannot create a hex dump of a null buffer.");
		
		int positionCache = buffer.position();
		buffer.rewind();
		int count = 0;
		while (buffer.hasRemaining()) {
			StringBuffer nextLine = new StringBuffer(80);
			byte[] lineBytes = new byte[buffer.remaining() > 16 ? 16 : buffer.remaining()];
			buffer.get(lineBytes);
			
			nextLine.append(Integer.toHexString(count));
			for ( int x = nextLine.length() ; x <= 8 ; x++ )
				nextLine.insert(0, '0');
			
			nextLine.append("  ");
			
			for ( int x = 0 ; x < lineBytes.length ; x++ ) {
				nextLine.append(hexChars[(lineBytes[x] >>> 4) & 15]);
				nextLine.append(hexChars[lineBytes[x] & 15]);
				nextLine.append(' ');
			}
			
			nextLine.append("  ");
			
			for ( int x = 0 ; x < lineBytes.length ; x++ ) {
				if ((lineBytes[x] > 0x20) && (lineBytes[x] < 0x80))
					nextLine.append((char) lineBytes[x]);
				else
					nextLine.append('.');
			}
			
			System.out.println(nextLine.toString());
			count += 16;
		}
		
		buffer.position(positionCache);
	}
	
	/**
	 * <p>Create an XML-safe element name from the given name, converting any non-safe characters into 
	 * underscore values "<code>_</code>". For <code>null</code> or empty values, a <code>null</code> is returned
	 * as these are not legal symbols.</p>
	 * 
	 * @param name Name to convert to a symbol.
	 * @return Symbol created from the name that is safe to use as an XML element name.
	 * 
	 * @throws NullPointerException Cannot convert a null name into a safe symbol value.
	 * @throws IllegalArgumentException Cannot convert an empty string into a safe symbol value.
	 * 
	 * @see tv.amwa.maj.meta.MetaDefinition#getSymbol()
	 */
	public final static String makeSymbol(
			String name) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (name == null) 
			throw new NullPointerException("Cannot convert a null name into a safe symbol value.");
		if (name.length() == 0) 
			throw new IllegalArgumentException("Cannot convert an empty string into a safe symbol value.");
		
		char[] symbolChars = new char[name.length()];
		
		symbolChars[0] = (Character.isJavaIdentifierStart(name.charAt(0))) ? name.charAt(0) : '_';
		
		for ( int x = 1 ; x < symbolChars.length ; x++ )
			symbolChars[x] = (Character.isJavaIdentifierPart(name.charAt(x))) ? name.charAt(x) : '_';
 		
		return new String(symbolChars);
	}
}
