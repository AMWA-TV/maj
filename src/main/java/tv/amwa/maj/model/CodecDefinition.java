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
 * $Log: CodecDefinition.java,v $
 * Revision 1.4  2011/07/27 17:30:18  vizigoth
 * Fixed imports to clear warnings.
 *
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
 *
 * Revision 1.2  2011/01/13 17:44:26  vizigoth
 * Major refactor of the industrial area and improved front-end documentation.
 *
 * Revision 1.1  2011/01/04 10:39:02  vizigoth
 * Refactor all package names to simpler forms more consistent with typical Java usage.
 *
 * Revision 1.7  2009/05/14 16:15:13  vizigoth
 * Major refactor to remove dependency on JPA and introduce better interface and implementation separation. Removed all setPropertiesFromInterface and castFromInterface methods.
 *
 * Revision 1.6  2009/03/30 09:04:50  vizigoth
 * Refactor to use SMPTE harmonized names and add early KLV file support.
 *
 * Revision 1.5  2008/02/08 12:44:28  vizigoth
 * Comment linking fix.
 *
 * Revision 1.4  2008/01/27 11:07:28  vizigoth
 * Edited comments to a release standard.
 *
 * Revision 1.3  2007/12/04 13:04:50  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.2  2007/12/04 09:27:32  vizigoth
 * Minor formating changes.
 *
 * Revision 1.1  2007/11/13 22:08:37  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model;

import java.util.List;

import tv.amwa.maj.exception.DuplicateEssenceKindException;
import tv.amwa.maj.exception.ObjectNotFoundException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.misctype.Bool;

/** 
 * <p>Specifies the definition of an essence codec. A codec is a program or system capable of performing 
 * encoding and decoding on a digital data stream, which is often the case for the data streams represented 
 * by {@linkplain tv.amwa.maj.model.EssenceData essence data} in the MAJ API.</p>
 * 
 *
 * 
 * @see tv.amwa.maj.constant.CodecConstant
 * @see tv.amwa.maj.constant.CodecFlavour
 * @see tv.amwa.maj.industry.Warehouse#lookup(Class, String)
 * @see Dictionary#getCodecDefinitions()
 * @see AAFFileDescriptor#getCodec()
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionWeakReference
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReference
 * @see tv.amwa.maj.industry.TypeDefinitions#CodecDefinitionStrongReferenceSet
 */

public interface CodecDefinition 
	extends DefinitionObject {

	/**
	 * <p>Returns <code>true</code> if the given codec supports transfers to essence
	 * of the given essence kind.</p>
	 * 
	 * @param essenceKind The essence kind to test.
	 * @return Is this essence kind supported by the codec?
	 * 
	 * @throws NullPointerException The given essence kind is <code>null</code>.
	 * 
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public @Bool boolean isEssenceKindSupported(
			DataDefinition essenceKind) 
		throws NullPointerException;

	/** 
	 * <p>Appends the given essence kind to the list of those supported by the codec, which 
	 * specify the {@linkplain DataDefinition data definitions} of the 
	 * essence formats that the defined codec processes. The content of the list is dependent upon 
	 * the specified format for the codec, not 
	 * on whether an implementation supports all parts of the format.</p>
	 * 
	 * @param essenceKind The essence kind to append.
	 * 
	 * @throws NullPointerException The given essence kind is <code>null</code>.
	 * @throws DuplicateEssenceKindException The given essence kind is already
	 * contained.
	 * 
	 * @see #prependEssenceKind(DataDefinition)
	 * @see #insertEssenceKind(int, DataDefinition)
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public void appendEssenceKind(
			DataDefinition essenceKind) 
		throws NullPointerException,
			DuplicateEssenceKindException;

	/**
	 * <p>Prepends the given essence kind to the list of those supported by the codec, which 
	 * specify the {@linkplain DataDefinition data definitions} of the 
	 * essence formats that the defined codec processes. The content of the list is dependent upon 
	 * the specified format for the codec, not 
	 * on whether an implementation supports all parts of the format.</p>
	 * 
	 * @param essenceKind The essence kind to prepend.
	 * 
	 * @throws NullPointerException The given essence kind is <code>null</code>.
	 * @throws DuplicateEssenceKindException The given essence kind is already
	 * contained.
	 * 
	 * @see #appendEssenceKind(DataDefinition)
	 * @see #insertEssenceKind(int, DataDefinition)
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public void prependEssenceKind(
			DataDefinition essenceKind)
		throws NullPointerException,
			DuplicateEssenceKindException;
	
	/**
	 * <p>Inserts the given essence kind to the list of those supported by the codec at the given
	 * index. Essence kinds 
	 * specify the {@linkplain DataDefinition data definitions} of the 
	 * essence formats that the defined codec processes. The content of the list is dependent upon 
	 * the specified format for the codec, not 
	 * on whether an implementation supports all parts of the format.</p>
	 * 
	 * @param index Index in the list where the essence kind is to be inserted.
	 * @param essenceKind The essence kind to insert.
	 * 
	 * @throws NullPointerException The given essence kind is <code>null</code>.
	 * @throws DuplicateEssenceKindException The given essence kind is already
	 * contained.
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the 
	 * current list of supported essence kinds.
	 * 
	 * @see #appendEssenceKind(DataDefinition)
	 * @see #prependEssenceKind(DataDefinition)
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public void insertEssenceKind(
			int index,
			DataDefinition essenceKind)
		throws NullPointerException,
			DuplicateEssenceKindException,
			IndexOutOfBoundsException;
	
	/**
	 * <p>Returns the essence kind at the given index in the list of supported essence
	 * kinds of this codec.</p>
	 * 
	 * @param index Index of the essence kind to retrieve.
	 * @return Data definition defining the essence kind at the given index.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the 
	 * current list of supported essence kinds.
	 * 
	 * @see #getEssenceKinds()
	 */
	public DataDefinition getEssenceKindAt(
			int index) 
		throws IndexOutOfBoundsException;
	
	/**
	 * <p>Clears the list of essence kinds for this codec definition. Note that the list 
	 * of essence kinds is a required property and so a call to this method should be 
	 * followed by a call to add another essence kind.</p>
	 */
	public void clearEssenceKinds();
	
	/** 
	 * <p>Removes the given essence kind from the list of those supported
	 * by the codec, which specify the {@linkplain DataDefinition data definitions} of the 
	 * essence formats that the defined codec processes.</p>
	 * 
	 * @param essenceKind The essence kind to remove.
	 * 
	 * @throws NullPointerException Argument is null.
	 * @throws ObjectNotFoundException The given kind of essence is not already 
	 * contained.
	 * @throws IllegalArgumentException Cannot remove an item from the set if it
	 * will become empty.
	 * 
	 * @see #removeEssenceKindAt(int)
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 */
	public void removeEssenceKind(
			DataDefinition essenceKind) 
		throws NullPointerException,
			 ObjectNotFoundException,
			 IllegalArgumentException;
	
	/**
	 * <p>Removes the essence kind at the given index in the list of essence kinds
	 * for this codec definition.</p>
	 * 
	 * @param index Index of the essence kind to remove.
	 * 
	 * @throws IndexOutOfBoundsException The given index is outside the acceptable range for the 
	 * current list of supported essence kinds.
	 * 
	 * @see #removeEssenceKind(DataDefinition)
	 */
	public void removeEssenceKindAt(
			int index)
		throws IndexOutOfBoundsException;
	
	/** 
	 * <p>Returns the number of supported essence kinds of this codec
	 * definition, which specify the {@linkplain DataDefinition data definitions} of the 
	 * essence formats that the defined codec processes.</p>
	 * 
	 * @return The number of supported essence kinds.
	 */
	public @UInt32 int countEssenceKinds();

	/** 
	 * <p>Returns a set of the essence kinds for the defined codec, which 
	 * specify the {@linkplain DataDefinition data definitions} of the 
	 * essence formats that the defined codec processes.</p>
	 * 
	 * @return Shallow copy of a set of the essence kinds that the defined codec processes.
	 * 
	 * @see #getEssenceKindAt(int)
	 * @see tv.amwa.maj.constant.DataDefinitionConstant
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceSet
	 * @see tv.amwa.maj.industry.TypeDefinitions#DataDefinitionWeakReferenceVector
	 */
	public List<? extends DataDefinition> getEssenceKinds();
	
	/**
	 * <p>Find out whether the optional codec flavours property is present
	 * for the defined codec.  Flavours are
	 * used when a single codec can support multiple formats.  An
	 * example would be a codec which would accept a "resolution id"
	 * for a particular manufacturer and set up all of the parameters.
	 * When a new resolution id is released, then a new codec plugin
	 * would give users the ability to use the new resolutions without
	 * upgrading the application.</p>
	 * 
	 * @return Are there any flavours?
	 */
	// public @Bool boolean areThereFlavours();
	
	/** 
	 * <p>Returns the file descriptor class object associated with the
	 * defined codec, which specifies the {@linkplain ClassDefinition class definition } of the sub-class 
	 * of {@linkplain AAFFileDescriptor file descriptor} that identifies the essence format that the defined 
	 * codec processes.</p>
	 * 
	 * @return The class definition of the sub-class 
	 * of {@link AAFFileDescriptor FileDescriptor} that identifies the essence format that the defined
	 * codec processes.
	 */
	public ClassDefinition getFileDescriptorClass();

	/**
	 * <p>Sets the file descriptor class associated with this codec, which specifies
	 * the {@linkplain ClassDefinition class definition} of the sub-class of 
	 * {@link AAFFileDescriptor FileDescriptor} that identifies the essence format
	 * that the defined codec processes.</p>
	 * 
	 * @param fileClass Specifies the class definition of the sub-class 
	 * of {@link AAFFileDescriptor} that identifies the essence format that the defined
	 * codec processes.
	 * 
	 * @throws NullPointerException The given class definition for a sub-class of file descriptor 
	 * is <code>null</code>.
	 * @throws IllegalArgumentException The given class definition is not for a sub-class of
	 * {@link AAFFileDescriptor FileDescriptor}.
	 * 
	 * @see tv.amwa.maj.constant.CodecConstant
	 */
	public void setFileDescriptorClass(
			ClassDefinition fileClass) 
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Returns a list of supported codec flavours.<p>
	 * 
	 * <p>Flavours are used when a single codec can support multiple
	 * formats.  An example would be a codec which would accept a 
	 * "resolution ID" for a particular manufacturer and set up all of
	 * the parameters.  When a new resolution ID is released, then a
	 * new codec plugin would give users the ability to use the new
	 * resolutions without upgrading the application.</p>
	 * 
	 * @return List of supported codec flavours.
	 * 
	 * @see tv.amwa.maj.constant.CodecFlavour
	 */
	// public Set<AUID> getCodecFlavours();
	
	// TODO codec flavour support removed ... this is not in the latest meta dictionary
	
	/**
	 * <p>Create a cloned copy of this codec definition.</p>
	 *
	 * @return Cloned copy of this codec definition.
	 */
	public CodecDefinition clone();
}
