/* 
 **********************************************************************
 *
 * $Id: BadPropertyException.java,v 1.5 2011/02/14 22:32:59 vizigoth Exp $
 *
 * The contents of this file are subject to the AAF SDK Public
 * Source License Agreement (the "License"); You may not use this file
 * except in compliance with the License.  The License is available in
 * AAFSDKPSL.TXT, or you may obtain a copy of the License from the AAF
 * Association or its successor.
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.  See
 * the License for the specific language governing rights and 
 * limitations under the License.
 *
 * The Original Code of this file is Copyright 2007, Licensor of the
 * AAF Association.
 *
 * The Initial Developer of the Original Code of this file and the 
 * Licensor of the AAF Association is Richard Cartwright.
 * All rights reserved.
 *
 * Contributors and Additional Licensors of the AAF Association:
 * Avid Technology, Metaglue Corporation, British Broadcasting Corporation
 *
 **********************************************************************
 */

/*
 * $Log: BadPropertyException.java,v $
 * Revision 1.5  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/03/30 09:04:59  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.2  2007/11/27 20:38:16  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:11:22  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.exception;

/** 
 * <p>Thrown when an optional property should be omitted in the current context. In the AAF
 * specification, the cases when this can occur are labelled as <em>Conditional Rules</em>. This
 * is different from a {@link PropertyNotPresentException} which is thrown when an attempt is
 * made to read an optional property that is currently omitted.</p>
 * 
 * <p>For example, according to conditional rule&nbsp;1 of the {@linkplain tv.amwa.maj.model.Event 
 * event class}, its position property shall only be present when it is in an 
 * {@linkplain tv.amwa.maj.model.EventTrack event track}. Any attempt to set or get this 
 * property in another context should result in this exception being thrown.</p>
 * 
 * <p>Equivalent C result code: <code>AAFRESULT_BAD_PROP 0x801200CB</code></p>
 * 
 * @see tv.amwa.maj.model.Event
 * @see tv.amwa.maj.model.EventTrack
 * @see tv.amwa.maj.model.Component
 * @see tv.amwa.maj.model.Sequence
 * @see tv.amwa.maj.model.AAFFileDescriptor
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class BadPropertyException 
	extends RuntimeException 
	implements MAJException {

	/** <p></p> */
	private static final long serialVersionUID = 443709493640658988L;

	/**
	 * <p>Create a new bad property exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public BadPropertyException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new bad property exception with no message.</p>
	 */
	public BadPropertyException() {
		super();
	}
}
