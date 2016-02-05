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
 * $Log: RP224ToJava.java,v $
 * Revision 1.1  2011/07/27 17:18:53  vizigoth
 * New utility used to generate RP224.java interface.
 *
 *
 */

package tv.amwa.maj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Convert a CSV export of the RP224 SMPTE Labels Registry spreadsheet to a Java interface.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.constant.RP224
 */

public class RP224ToJava {

	final static String ofInterest = ",([^,]*|\"[^\"]*\")";
	final static String notOfInterest = ofInterest; //",[^,]*|\"[^\"]*\"";
	
	final static Pattern leafPattern =
			Pattern.compile("Leaf" + ofInterest + ofInterest +  // ID
					notOfInterest +
					ofInterest + // Name
					ofInterest + // Description
					notOfInterest + notOfInterest + ",");
	final static Pattern idPattern =
			Pattern.compile("([0-9a-fA-F]{2})\\.([0-9a-fA-F]{2})\\.([0-9a-fA-F]{2})\\.([0-9a-fA-F]{2})\\." +
					"([0-9a-fA-F]{2})\\.([0-9a-fA-F]{2})\\.([0-9a-fA-F]{2})\\.([0-9a-fA-F]{2})");
	/**
	 * @param args
	 */
	public static void main(
			String[] args) {
		
		if (args.length != 1) {
			System.err.println("RP224 spreadsheet expected as input.");
			System.exit(1);
		}

		BufferedReader reader = null;
		try {
			File rp224File = new File(args[0]);
			rp224File = rp224File.getAbsoluteFile();
			
			if (!rp224File.exists())
				throw new IOException("The given rp224 file does not exist.");
			if (!rp224File.canRead())
				throw new IOException("The given rp224 file cannot be read.");
			if (!rp224File.isFile())
				throw new IOException("The given rp224 file is not a regular file.");
			
			reader = new BufferedReader(new FileReader(rp224File));
		
			System.out.println("package tv.amwa.maj.constant;\n");
			
			System.out.println("import tv.amwa.maj.record.AUID;");
			System.out.println("import tv.amwa.maj.industry.Forge;\n");
			
			System.out.println("public interface RP224 {\n");
			
			for ( String currentLine = reader.readLine() ;
					currentLine != null;
					currentLine = reader.readLine() ) {
				
				Matcher matcher = leafPattern.matcher(currentLine);
				if (matcher.matches()) {
					
					if (matcher.group(3).length() == 0) continue;
					
					System.out.println("    /**");
					System.out.println("     * <p>" + matcher.group(4) + ".</p>");
					System.out.println("     */");
					
					System.out.println("    public final static AUID " + toJavaName(matcher.group(4)) + " = Forge.makeAUID(");
					
					Matcher id1Matcher = idPattern.matcher(matcher.group(1));
					id1Matcher.matches();
					Matcher id2Matcher = idPattern.matcher(matcher.group(2));
					id2Matcher.matches();
					
					System.out.println("            0x" + id2Matcher.group(1) + id2Matcher.group(2) + id2Matcher.group(3) + id2Matcher.group(4) + 
							", (short) 0x" + id2Matcher.group(5) + id2Matcher.group(6) + 
							", (short) 0x" + id2Matcher.group(7) + id2Matcher.group(8) + ",");
					System.out.print("            new byte[] { ");
					for ( int x = 1 ; x < 8 ; x++ )
						System.out.print("0x" + id1Matcher.group(x) + ", ");
					System.out.println("0x" + id1Matcher.group(8) + " } );\n");
				}

			}

			System.out.println("}\n");
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		finally {
			if (reader != null) 
				try { reader.close(); } catch (Exception e) { }
		}
	}

	public final static String toJavaName(
			String smpteName) {
		
		String javaName = smpteName.replace("  ", "_");
		javaName = javaName.replace("-", "");
		javaName = javaName.replace(".", "");
		javaName = javaName.replace("@", "at");
		javaName = javaName.replace('/', '_');
		javaName = javaName.replace(' ', '_');
		
		return javaName;
	}
}
