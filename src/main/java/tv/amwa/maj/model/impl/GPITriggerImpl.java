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
 * $Log: GPITriggerImpl.java,v $
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
 * Revision 1.1  2007/11/13 22:09:37  vizigoth
 * Public release of MAJ API.
 *
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.misctype.Bool;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.GPITrigger;


/** 
 * <p>Implements a trigger action that should be taken when the GPI trigger is reached.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x0700,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "GPITrigger",
		  description = "The GPITrigger class specifies a trigger action that should be taken when the GPITrigger is reached.",
		  symbol = "GPITrigger")
public class GPITriggerImpl
	extends 
		EventImpl
	implements 
		GPITrigger,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 8895295398617490756L;

	private boolean activeState;
	
	public GPITriggerImpl() { }

	/** 
	 * <p>Creates and initializes a new GPI trigger event, which specifies a 
	 * trigger action that should be taken when the GPITrigger is reached. If this event 
	 * is located in an {@link EventTrackImpl event track}, its position must also be 
	 * set using {@link EventImpl#setEventPosition(long)}.</p>
	 *
	 * @param dataDefinition Kind of data represented by this component.
	 * @param activeState Is the event turned on?
	 * 
	 * @throws NullPointerException Data definition argument is <code>null</code>.
	 */
	public GPITriggerImpl(
			DataDefinition dataDefinition,
			@Bool boolean activeState)
		throws NullPointerException {
		
		if (dataDefinition == null)
			throw new NullPointerException("Cannot create a new GPI trigger with a null data definition.");
		
		setComponentDataDefinition(dataDefinition);
		setActiveState(activeState);
	}

	@MediaProperty(uuid1 = 0x05300401, uuid2 = (short) 0x0000, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x01},
			definedName = "ActiveState",
			typeName = "Boolean",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0801,
			symbol = "ActiveState")
	public boolean getActiveState() {

		return activeState;
	}

	@MediaPropertySetter("ActiveState")
	public void setActiveState(
			boolean activeState) {

		this.activeState = activeState;
	}

	public final static boolean initializeActiveState() {
		
		return false;
	}
	
	public GPITrigger clone() {
		
		return (GPITrigger) super.clone();
	}
}
