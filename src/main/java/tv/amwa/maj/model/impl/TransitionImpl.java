/* 
 **********************************************************************
 *
 * $Id: TransitionImpl.java,v 1.3 2011/10/07 19:42:21 vizigoth Exp $
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
 * $Log: TransitionImpl.java,v $
 * Revision 1.3  2011/10/07 19:42:21  vizigoth
 * Stop cloning strong references and getProperties method in applicatio object.
 *
 * Revision 1.2  2011/01/19 21:55:59  vizigoth
 * Added property initialization code.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.1  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:04:51  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.1  2007/11/13 22:09:26  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.exception.BadLengthException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.misctype.PositionType;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.OperationGroup;
import tv.amwa.maj.model.Transition;


/** 
 * <p>Implements a means to indicate that the two adjacent {@linkplain tv.amwa.maj.model.Segment Segments} should be overlapped when 
 * they are played and that the overlapped sections should be combined using the specified effect.
 * Transitions are used to specify {@linkplain tv.amwa.maj.model.Sequence sequences} according to the specified rules
 * for a sequence. The {@linkplain tv.amwa.maj.model.OperationGroup operation} that they specify must be appropriate
 * for use in a transition, which means that it has two inputs and a <em>level</em> parameter.</p>
 *
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x1700,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "Transition",
		  description = "The Transition class specifies that the two adjacent Segments should be overlapped when they are played and the overlapped sections should be combined using the specified effect.",
		  symbol = "Transition")
public class TransitionImpl
	extends 
		ComponentImpl
	implements 
		Transition,
		Serializable,
		Cloneable {

	private static final long serialVersionUID = 8175422126688427171L;
	
	private OperationGroup transitionOperation;
	private long cutPoint;
	
	public TransitionImpl() { }

	/**
	 * <p>Creates and initializes a transition, which specifies that the two adjacent 
	 * {@link SegmentImpl segments} should be overlapped when they are played and that the 
	 * overlapped sections should be combined using the specified effect.</p>
	 * 
	 * @param dataDefinition Kind of data represented by the new transition component.
	 * @param length Length of the transition component.
	 * @param cutPoint The point at which a cut would be inserted if the transition
	 * were removed.
	 * @param operationGroup An operation group that specifies the effect to be performed during the
	 * new transition.
	 * 
	 * @throws NullPointerException The data definition and/or operation group arguments
	 * is/are <code>null</code>.
	 * @throws BadLengthException Cannot set the length of a segment with a negative value.
	 */
	public TransitionImpl(
			DataDefinition dataDefinition,
			@LengthType long length,
			OperationGroup operationGroup,
			@PositionType long cutPoint) 
		throws NullPointerException,
			BadLengthException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a transition with a null data definition value.");
		if (operationGroup == null)
			throw new NullPointerException("Cannot create a transition with a null operation group value.");
		
		setComponentDataDefinition(dataDefinition);
		
		setLengthPresent(true);
		setComponentLength(length);
		
		setTransitionOperation(operationGroup);
		setCutPoint(cutPoint);
	}
	
	@MediaProperty(uuid1 = 0x07020103, uuid2 = (short) 0x0106, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "CutPoint",
			typeName = "PositionType",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1802,
			symbol = "CutPoint")
	public long getCutPoint() {

		return cutPoint;
	}

	@MediaPropertySetter("CutPoint")
	public void setCutPoint(
			long cutPoint) {

		this.cutPoint = cutPoint;
	}
	
	public final static long initializeCutPoint() {
		
		return 0l;
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = (short) 0x0205, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "TransitionOperation",
			aliases = { "OperationGroup", "TransitionOperationGroup" },
			typeName = "OperationGroupStrongReference",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x1801,
			symbol = "TransitionOperation")
	public OperationGroup getTransitionOperation() {

		return transitionOperation;
	}

	@MediaPropertySetter("TransitionOperation")
	public void setTransitionOperation(
			tv.amwa.maj.model.OperationGroup transitionOperation)
		throws NullPointerException {

		if (transitionOperation == null)
			throw new NullPointerException("Cannot set the operation group for this transition using a null value.");
		
		this.transitionOperation = transitionOperation;
	}
	
	public final static OperationGroup initializeTransitionOperation() {
		
		return new OperationGroupImpl(
				DataDefinitionImpl.forName("Unknown"), 
				0l, 
				OperationDefinitionImpl.forName("Unknown"));
	}

	public Transition clone() {
		
		return (Transition) super.clone();
	}
}
