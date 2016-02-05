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
 * $Log: GPITrigger.java,v $
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/27 11:07:23  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:29  vizigoth
 * Public release of MAJ API.
 */

// TODO find out how the trigger is actually specified ... KLV data?

package tv.amwa.maj.model;

import tv.amwa.maj.misctype.Bool;

/**
 * <p>Specifies a trigger action that should be taken when the GPI trigger is reached.</p>
 * 
 * <p>A <em>GPI trigger</em> is a one way communication protocol in which the controller tells or 
 * "triggers" a video mixer or title generator to perform a previously programmed function 
 * such as a transition, effect, or title.</p>
 * 
 *
 * 
 */

public interface GPITrigger 
	extends Event {

	/**
	 * <p>Returns the active state of this GPI trigger, which is <code>true</code> if the event 
	 * is "on".</p>
	 * 
	 * @return Active state of this trigger.
	 */
	public @Bool boolean getActiveState();
	
	/**
	 * <p>Set to <code>true</code> to turn the trigger "on" or 
	 * <code>false</code> to turn the trigger off.</p>
	 * 
	 * @param activeState Active state to set for the trigger.
	 */
	public void setActiveState(
			@Bool boolean activeState);
	
	/**
	 * <p>Create a cloned copy of this GPI trigger.</p>
	 *
	 * @return Cloned copy of this GPI trigger.
	 */
	public GPITrigger clone();
}

