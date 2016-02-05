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
 * $Log: EssenceMultiAccess.java,v $
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.4  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.3  2008/10/16 16:51:53  vizigoth
 * First early release 0.1.
 *
 * Revision 1.2  2008/01/27 11:07:21  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.1  2007/11/13 22:08:36  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

// TODO Really confused definition of this and EssenceAccess? Which way round should it go?
// which extends which?

import tv.amwa.maj.enumeration.CompressEnable;
import tv.amwa.maj.exception.InvalidParameterException;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.union.MultiCreateItem;
import tv.amwa.maj.union.MultiResultItem;
import tv.amwa.maj.union.MultiXferItem;


/**
 * <p>Specifies streaming access over over multiple channels of 
 * {@linkplain EssenceData essence data}.  This interface deals with essence data
 * which is in an uncompressed form, and handles compression or decompression of 
 * the data if required.</p>
 * 
 * <p>You should call the {@link MaterialPackage#createMultiEssence(AUID, MultiCreateItem[], CompressEnable, Locator, AUID) 
 * createMultiEssence()} 
 * or {@link MaterialPackage#openMultiEssence(int, tv.amwa.maj.enumeration.CriteriaType, tv.amwa.maj.enumeration.MediaOpenMode, tv.amwa.maj.enumeration.CompressEnable) openMultiEssence()}
 * methods of {@linkplain MaterialPackage material package} in order to get an implementation of 
 * an essence multi-channel access, as no public create or open method in
 * is specified in this interface.</p>
 * 
 * <p>Note that the current version of the MAJ API has no built-in codec support, large 
 * essence is not supported and the methods of this interface do nothing.</p>
 * 
 *
 *
 * @see EssenceAccess
 * @see EssenceData
 * @see MaterialPackage
 */
public interface EssenceMultiAccess {

	/** 
	 * <p>Writes multiple channels worth of sample data into the essence from
	 * an array of items representing interleaved data streams in the natural order 
	 * for the codec. See {@link tv.amwa.maj.union.MultiXferItem} for details of the fields of each 
	 * item.</p>
	 * 
	 * <p>The array returned contains details of how many bytes and samples were
	 * transferred by the operation corresponding to each input item of the array
	 * given as a parameter. See {@link tv.amwa.maj.union.MultiResultItem} for more details.</p> 
	 * 
	 * @param xferArray An array of transfer parameters.  All fields of items in
	 * this array must be set up before performing the transfer. 
	 * @return An array containing the details of how many bytes and samples were
	 * transferred for each item of the given array.
	 * 
	 * @throws NullPointerException The given transfer array is <code>null</code> or one or more of 
	 * its elements is <code>null</code>.
	 * @throws InvalidParameterException One or more of the parameters defined
	 * by an item of the given array is invalid.
	 */
	public MultiResultItem[] writeMultiSamples(
			MultiXferItem[] xferArray) 
		throws NullPointerException,
			InvalidParameterException;

	/**
	 * <p>Reads multiple channels of data from an interleaved data stream.
	 * The given input array contains items that define which channel of data 
	 * should be read. These items are modified by the operation by their data 
	 * buffers being filled with data from the essence according to the parameters 
	 * they specify. See {@link tv.amwa.maj.union.MultiXferItem} for more details.</p>
	 * 
	 * <p>The array returned contains details of how many bytes and samples were
	 * transferred by the operation corresponding to each input item of the array
	 * given as a parameter. See {@link tv.amwa.maj.union.MultiResultItem} for more details.</p> 
	 * 
	 * @param xferArray Array of transfer items to read data into.
	 * @return An array containing the details of how many bytes and samples were
	 * transferred array by the operation for each item of the given array.
	 * 
	 * @throws NullPointerException The given transfer array is <code>null</code> or one or more of 
	 * its elements is <code>null</code>.
	 * @throws InvalidParameterException One or more of the parameters defined
	 * by an item of the given array is invalid.
	 */
	public MultiResultItem[] readMultiSamples(
			MultiXferItem[] xferArray) 
		throws NullPointerException,
			InvalidParameterException;

}
