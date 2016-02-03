/* 
 **********************************************************************
 *
 * $Id: InterpolationDefinition.java,v 1.3 2011/02/14 22:32:49 vizigoth Exp $
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
 * $Log: InterpolationDefinition.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:03  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.3  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.2  2008/01/27 11:07:40  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:27  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;


/**
 * <p>Specifies a definition for the mechanism used to calculate the values produced by a
 * {@linkplain VaryingValue varying value} using the specified {@linkplain ControlPoint
 * control points}.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 * @see tv.amwa.maj.constant.InterpolationConstant
 * @see Dictionary#getInterpolationDefinitions()
 */

public interface InterpolationDefinition 
	extends DefinitionObject {

	/**
	 * <p>Create a cloned copy of this interpolation definition.</p>
	 *
	 * @return Cloned copy of this interpolation definition.
	 */
	public InterpolationDefinition clone();
}

