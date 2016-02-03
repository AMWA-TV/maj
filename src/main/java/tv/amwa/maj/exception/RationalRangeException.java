/* 
 **********************************************************************
 *
 * $Id: RationalRangeException.java,v 1.5 2011/02/14 22:32:59 vizigoth Exp $
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
 * $Log: RationalRangeException.java,v $
 * Revision 1.5  2011/02/14 22:32:59  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.4  2011/01/04 10:41:20  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:34  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2007/11/27 20:38:00  vizigoth
 * Edited javadoc comments to release standard.
 *
 * Revision 1.1  2007/11/13 22:10:24  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.exception;

// Created this new for when a rational strays outside a defined range.
// References: ControlPoint and AAFFactory

/** 
 * <p>Thrown when a {@linkplain tv.amwa.maj.record.Rational rational} value is
 * outside of the acceptable range in a given context.</p>
 * 
 * <p>No equivalent C result code exists.</p>
 * 
 * @see tv.amwa.maj.record.Rational
 * @see tv.amwa.maj.model.ControlPoint#setControlPointTime(tv.amwa.maj.record.Rational)
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public class RationalRangeException 
	extends IllegalArgumentException 
	implements MAJException,
		NewForMAJ {

	/** <p></p> */
	private static final long serialVersionUID = 2776774493097762395L;

	/**
	 * <p>Create a new rational range exception with the given descriptive message.</p>
	 * 
	 * @param msg Message describing the exception.
	 */
	public RationalRangeException(
			String msg) {
		
		super(msg);
	}

	/**
	 * <p>Create a new rational range exception with no message.</p>
	 */	
	public RationalRangeException() {
		super();
	}
}
