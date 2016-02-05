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
 * $Log: MultipleDescriptorImpl.java,v $
 * Revision 1.3  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.3  2008/10/16 01:14:25  vizigoth
 * Documentation improved to an early release level. Still with Javadoc warnings.
 *
 * Revision 1.2  2007/12/04 13:04:49  vizigoth
 * Removed safe collections and proxy handling. The first is not required due to ? extends in interface definitions and the second is not a solution to the shared resources problem.
 *
 * Revision 1.1  2007/11/13 22:09:14  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import tv.amwa.maj.constant.ContainerConstant;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaListAppend;
import tv.amwa.maj.industry.MediaListGetAt;
import tv.amwa.maj.industry.MediaListInsertAt;
import tv.amwa.maj.industry.MediaListPrepend;
import tv.amwa.maj.industry.MediaListRemoveAt;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertyClear;
import tv.amwa.maj.industry.MediaPropertyCount;
import tv.amwa.maj.industry.StrongReferenceVector;
import tv.amwa.maj.misctype.LengthType;
import tv.amwa.maj.model.AAFFileDescriptor;
import tv.amwa.maj.model.DataEssenceDescriptor;
import tv.amwa.maj.model.MultipleDescriptor;
import tv.amwa.maj.record.Rational;


/** 
 * <p>Implements the description of content data associated with a single file 
 * {@linkplain tv.amwa.maj.model.SourcePackage source package} that contains multiple tracks 
 * of essence. Each file descriptor within a multiple descriptor should
 * set the linked track property that links it to the source track that it describes.</p>
 *
 *
 *
 */

@MediaClass(uuid1 = 0x0D010101, uuid2 = 0x0101, uuid3 = 0x4400,
		  uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "MultipleDescriptor",
		  description = "Describes the format of the content data associated with a file source package containing multiple tracks of essence.",
		  symbol = "MultipleDescriptor")
public class MultipleDescriptorImpl
	extends 
		AAFFileDescriptorImpl
	implements 
		MultipleDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = -1248555207364210775L;

	private List<AAFFileDescriptor> fileDescriptors = 
		Collections.synchronizedList(new Vector<AAFFileDescriptor>());

	/** 
	 * <p>Creates and initializes a new multiple descriptor for static essence, which describes 
	 * the format of the content data associated with a {@link SourcePackageImpl file source package} 
	 * containing multiple tracks of essence. File descriptors for each of the tracks
	 * of data can then be added using the 
	 * {@link #appendFileDescriptor(tv.amwa.maj.model.AAFFileDescriptor) append,}
	 * {@link #insertFileDescriptorAt(int, tv.amwa.maj.model.AAFFileDescriptor) insert} and
	 * {@link #prependLocator(tv.amwa.maj.model.Locator) prepend} methods.</p>
	 */
	public MultipleDescriptorImpl() {

		setDescribesStaticEssence();
	}

	/**
	 * <p>Creates and initializes a new multiple descriptor for time varying essence, which describes 
	 * the format of the content data associated with a {@link SourcePackageImpl file source package} 
	 * containing multiple tracks of essence. File descriptors for each of the tracks
	 * of data can then be added using the 
	 * {@link #appendFileDescriptor(tv.amwa.maj.model.AAFFileDescriptor) append,}
	 * {@link #insertFileDescriptorAt(int, tv.amwa.maj.model.AAFFileDescriptor) insert} and
	 * {@link #prependLocator(tv.amwa.maj.model.Locator) prepend} methods.</p>
	 *
	 * @param sampleRate Sample rate for the content represented by this multiple descriptor.
	 * @param length Length of content represented by this multiple descriptor.
	 * 
	 * @throws NullPointerException The sample rate argument is <code>null</code>.
	 * @throws IllegalArgumentException The length of the multiple descriptor cannot be a 
	 * negative value.
	 */
	public MultipleDescriptorImpl(
			Rational sampleRate, 
			@LengthType long length) 
		throws NullPointerException,
			IllegalArgumentException {
		
		if (sampleRate == null)
			throw new NullPointerException("Cannot set the sample rate for a multiple descriptor using a null rational value.");
		if (length < 0l)
			throw new IllegalArgumentException("Cannot set the length of a multiple descriptor using a negative value.");
		
		setDescribesTimeVaryingEssence(sampleRate, length);
	}

	@MediaProperty(uuid1 = 0x06010104, uuid2 = 0x060B, uuid3 = 0x0000,
			     uuid4 = {0x06, 0x0E, 0x2B, 0x34, 0x01, 0x01, 0x01, 0x04},
			     definedName = "FileDescriptors",
			     typeName = "FileDescriptorStrongReferenceVector",
			     optional = false,
			     uniqueIdentifier = false,
			     pid = 0x3F01,
			     symbol = "FileDescriptors")
	public List<AAFFileDescriptor> getFileDescriptors() {

		return StrongReferenceVector.getRequiredList(fileDescriptors);
	}	
	
	public final static List<AAFFileDescriptor> initializeFileDescriptors() {
		
		List<AAFFileDescriptor> initialDescriptors = new ArrayList<AAFFileDescriptor>(1);
		initialDescriptors.add(new DataEssenceDescriptorImpl(ContainerDefinitionImpl.forName("External")));
		return initialDescriptors;
	}
	
	@MediaListAppend("FileDescriptors")
	public void appendFileDescriptor(
			AAFFileDescriptor fileDescriptor)
		throws NullPointerException {

		if (fileDescriptor == null)	
			throw new NullPointerException("Cannot append a null file descriptor to the list of file descriptors of this multiple descriptor.");
		removeInitialDescriptor();
		
		StrongReferenceVector.append(fileDescriptors, fileDescriptor);
	}

	void removeInitialDescriptor() {
		
		if (fileDescriptors.size() != 1) return;
		AAFFileDescriptor isItTheDefaultDescriptor = fileDescriptors.get(0);
		if (!(isItTheDefaultDescriptor instanceof DataEssenceDescriptor)) return;
		if (!(isItTheDefaultDescriptor.getContainerFormat().getAUID().equals(ContainerConstant.External))) return;
		clearFileDescriptors();
	}
	
	@MediaPropertyCount("FileDescriptors")
	public int countFileDescriptors() {

		return fileDescriptors.size();
	}

	@MediaListGetAt("FileDescriptors")
	public AAFFileDescriptor getFileDescriptorAt(
			int index)
		throws IndexOutOfBoundsException {

		return StrongReferenceVector.getAt(fileDescriptors, index);
	}

	@MediaListInsertAt("FileDescriptors")
	public void insertFileDescriptorAt(
			int index,
			AAFFileDescriptor fileDescriptor)
		throws NullPointerException,
			IndexOutOfBoundsException {

		if (fileDescriptor == null)
			throw new NullPointerException("Cannot insert a null file descriptor into the list of file descriptors of this multiple descriptor.");
		removeInitialDescriptor();
		
		StrongReferenceVector.insert(fileDescriptors, index, fileDescriptor);
	}

	@MediaListPrepend("FileDescriptors")
	public void prependFileDescriptor(
			AAFFileDescriptor fileDescriptor)
		throws NullPointerException {

		if (fileDescriptor == null)
			throw new NullPointerException("Cannot prepend a null file descriptor to the list of file descriptors of this multiple descriptor.");
		removeInitialDescriptor();
		
		StrongReferenceVector.prepend(fileDescriptors, fileDescriptor);
	}

	@MediaListRemoveAt("FileDescriptors")
	public void removeFileDescriptorAt(
			int index)
		throws IndexOutOfBoundsException {

		StrongReferenceVector.remove(fileDescriptors, index);
	}
	
	@MediaPropertyClear("FileDescriptors")
	public void clearFileDescriptors() {
		
		fileDescriptors = Collections.synchronizedList(new Vector<AAFFileDescriptor>());
	}
	
	public MultipleDescriptor clone() {
		
		return (MultipleDescriptor) super.clone();
	}
}
