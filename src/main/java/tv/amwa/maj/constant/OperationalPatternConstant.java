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
 * $Log: OperationalPatternConstant.java,v $
 * Revision 1.5  2011/07/27 12:20:00  vizigoth
 * Added RP224 SMPTE label registry leaf-node constants.
 *
 * Revision 1.4  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.1  2008/01/23 14:22:46  vizigoth
 * Fixed name of OperationalProtocolConstant to OperationalPatternConstant.
 *
 * Revision 1.1  2008/01/23 14:20:49  vizigoth
 * Added operational pattern constants and descriptions.
 *
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/**
 * <p>Implement this interface to access unique identifiers for operational patterns or AAF protocols, such as 
 * as the AAF edit protocol. An operational pattern constraints levels of file complexity.</p>
 * 
 *
 *
 * @see OperationalPatternDescription
 * @see tv.amwa.maj.model.Preface#getOperationalPattern()
 */
public interface OperationalPatternConstant {

	/**
	 * <p>File complexity is constrained to that specified by the 
	 * <a href="http://www.amwa.tv/html/specs/aafeditprotocol.pdf">AAF edit protocol</a>.</p>
	 */
	@OperationalPatternDescription(
			description = "Operational Pattern for the AAF Edit Protocol",
			aliases = "OPDef_EditProtocol")
	public final static AUID EditProtocol = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d011201, (short) 0x0100, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x05 });
	
	/**
	 * <p>File complexity is not constrained by an optional pattern.</p>
	 */ 
	@OperationalPatternDescription(
			description = "Unconstrained by an Operational Pattern",
			aliases = "OPDef_Unconstrained")
	public final static AUID Unconstrained = new tv.amwa.maj.record.impl.AUIDImpl(
			0x0d011201, (short) 0x0200, (short) 0x0000,
			new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x04, 0x01, 0x01, 0x09 });
}
