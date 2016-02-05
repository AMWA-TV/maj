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
 * $Log: InterpolationConstant.java,v $
 * Revision 1.6  2011/01/04 10:43:58  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.5  2009/05/14 16:15:24  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.4  2009/03/30 09:05:00  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:52:01  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2007/12/12 12:29:54  vizigoth
 * Added to and edited document comments to a release level.
 *
 * Revision 1.1  2007/11/13 22:12:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.constant;

import tv.amwa.maj.record.AUID;

/** 
 * <p>Implement this interface to access unique identifiers for 
 * the definition of an interpolation function, which specifies the mechanism used to calculate the 
 * values produced by a {@linkplain tv.amwa.maj.model.VaryingValue varying value}. Varying values
 * are used to specify time-varying effect {@linkplain tv.amwa.maj.model.Parameter parameters} over
 * a set of {@linkplain tv.amwa.maj.model.ControlPoint control points}. 
 * The additional metadata provided by the annotation can be used in combination with the identifier
 * to create a {@linkplain tv.amwa.maj.model.InterpolationDefinition interpolation definition}.</p>
 * 
 * <p>See the <a href="package-summary.html#managingDefinitions">description of managing definitions</a> 
 * in the package summary for more details of how to use these constants and dynamically extend the range 
 * of supported interpolation definitions.</p>
 * 
 * @see tv.amwa.maj.constant.InterpolationDescription
 * @see tv.amwa.maj.model.InterpolationDefinition
 * @see tv.amwa.maj.model.VaryingValue
 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#InterpolationDefinitionStrongReferenceSet
 * 
 *
 */
public interface InterpolationConstant {

	@InterpolationDescription( )
	public final static AUID None = new tv.amwa.maj.record.impl.AUIDImpl(
			0x5b6c85a3, (short) 0x0ede, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xa9, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f });

	/**
	 * <p>Interpolation defining a linear relationship between the input (time) and output, where the
	 * output parameter varies in a straight line between two values.</p>
	 */
	@InterpolationDescription(description="Parameter varies in a straight line between two values.")
	public final static AUID Linear = new tv.amwa.maj.record.impl.AUIDImpl(
			0x5b6c85a4, (short) 0x0ede, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xa9, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f } );

	/**
	 * <p>Interpolation producing a constant value on the output whatever the input (time) value is
	 * until the next {@linkplain tv.amwa.maj.model.ControlPoint control point} is reached.</p>
	 */
	@InterpolationDescription( )
	public final static AUID Constant = new tv.amwa.maj.record.impl.AUIDImpl(
			0x5b6c85a5, (short) 0x0ede, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xa9, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f } );

	/**
	 * <p>Interpolation defined over a set of {@linkplain tv.amwa.maj.model.ControlPoint control points}
	 * where the output is computed by the calculation of a B-spline function.</p>
	 */
	@InterpolationDescription( )
	public final static AUID BSpline = new tv.amwa.maj.record.impl.AUIDImpl(
			0x5b6c85a6, (short) 0x0ede, (short) 0x11d3, 
			new byte[] { (byte) 0x80, (byte) 0xa9, 0x00, 0x60, 0x08, 0x14, 0x3e, 0x6f } );

	/**
	 * <p>Interpolation defined over a set of {@linkplain tv.amwa.maj.model.ControlPoint control points} 
	 * where the output is computed by the calculation of a logarithmic function.</p>
	 */
	@InterpolationDescription( )
	public final static AUID Log = new tv.amwa.maj.record.impl.AUIDImpl(
			0x15829ec3, (short) 0x1f24, (short) 0x458a, 
			new byte[] { (byte) 0x96, 0x0d, (byte) 0xc6, 0x5b, (byte) 0xb2, 0x3c, 0x2a, (byte) 0xa1} );

	/**
	 * <p>Interpolation defined over a set of {@linkplain tv.amwa.maj.model.ControlPoint control points} 
	 * where the output is computed by the calculation of mathematical power function.</p>
	 */
	@InterpolationDescription( )
	public final static AUID Power = new tv.amwa.maj.record.impl.AUIDImpl(
			0xc09153f7, (short) 0xbd18, (short) 0x4e5a, 
			new byte[] { (byte) 0xad, 0x09, (byte) 0xcb, (byte) 0xdd, 0x65, 0x4f, (byte) 0xa0, 0x01} );
}
