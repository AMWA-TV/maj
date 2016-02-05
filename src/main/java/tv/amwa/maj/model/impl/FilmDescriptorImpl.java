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
 * $Log: FilmDescriptorImpl.java,v $
 * Revision 1.2  2011/02/14 22:32:49  vizigoth
 * First commit after major sourceforge outage.
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
 * Revision 1.1  2007/11/13 22:09:40  vizigoth
 * Public release of MAJ API.
 */

package tv.amwa.maj.model.impl;

import java.io.Serializable;

import tv.amwa.maj.enumeration.FilmType;
import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.model.FilmDescriptor;
import tv.amwa.maj.record.Rational;
import tv.amwa.maj.record.impl.RationalImpl;


/** 
 * <p>Implements the description of film media. A {@linkplain tv.amwa.maj.model.SourcePackage}
 * described by a film descriptor is known as a <em>film source package</em>.</p>
 * 
 *
 *
 */

@MediaClass(uuid1 = 0x0d010101, uuid2 = 0x0101, uuid3 = 0x2d00,
		  uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x02, 0x06, 0x01, 0x01},
		  definedName = "FilmDescriptor",
		  description = "The FilmDescriptor class describes film media.",
		  symbol = "FilmDescriptor")
public class FilmDescriptorImpl
	extends 
		EssenceDescriptorImpl
	implements 
		FilmDescriptor,
		Serializable,
		Cloneable {

	/** <p></p> */
	private static final long serialVersionUID = 6661560948065288922L;

	private FilmType filmFormat = null;
	private Integer frameRate = null;
	private Byte perforationsPerFrame = null;
	private Rational filmAspectRatio = null; // TODO check how this gets into the database when null.
	private String filmStockManufacturer = null;
	private String filmStockKind = null;
	private String filmFormatName = null;
	private String filmBatchNumber = null;
	 
	/**
	 * <p>Creates and initializes a film descriptor that describes film media.</p>
	 */
	public FilmDescriptorImpl() { }
	
	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0203, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FilmAspectRatio",
			typeName = "Rational",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3904,
			symbol = "FilmAspectRatio")
	public Rational getFilmAspectRatio() 
		throws PropertyNotPresentException {

		if (filmAspectRatio == null)
			throw new PropertyNotPresentException("The optional film aspect ratio property is not present for this film descriptor.");
		
		return filmAspectRatio.clone();
	}

	@MediaPropertySetter("FilmAspectRatio")
	public void setFilmAspectRatio(
			Rational aspectRatio) {

		if (aspectRatio == null)
			this.filmAspectRatio = null;
		else
			this.filmAspectRatio = aspectRatio.clone();
	}

	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0108, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FilmFormat",
			typeName = "FilmType",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3901,
			symbol = "FilmFormat")
	public FilmType getFilmFormat() 
		throws PropertyNotPresentException {

		if (filmFormat == null)
			throw new PropertyNotPresentException("The optional film format property is not present for this film descriptor.");
		
		return filmFormat;
	}

	@MediaPropertySetter("FilmFormat")
	public void setFilmFormat(
			FilmType filmFormat) {

		this.filmFormat = filmFormat;
	}

	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0106, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FilmStockManufacturer",
			aliases = { "Manufacturer", "FilmDescriptorManufacturer" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3905,
			symbol = "FilmStockManufacturer")
	public String getFilmStockManufacturer() 
		throws PropertyNotPresentException {

		if (filmStockManufacturer == null)
			throw new PropertyNotPresentException("The optional manufacturer property is not present for this film descriptor.");
		
		return filmStockManufacturer;
	}

	@MediaPropertySetter("FilmStockManufacturer")
	public void setFilmStockManufacturer(
			String filmStockManufacturer) {

		this.filmStockManufacturer = filmStockManufacturer;
	}

	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0105, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FilmStockKind",
			aliases = { "Model", "FilmDescriptorModel" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3906,
			symbol = "FilmStockKind")
	public String getFilmStockKind() 
		throws PropertyNotPresentException {

		if (filmStockKind == null)
			throw new PropertyNotPresentException("The optional film stock kind property is not present for this film descriptor.");
		
		return filmStockKind;
	}

	@MediaPropertySetter("FilmStockKind")
	public void setFilmStockKind(
			String filmStockKind)  {

		this.filmStockKind = filmStockKind;
	}

	@MediaProperty(uuid1 = 0x04010802, uuid2 = (short) 0x0300, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FrameRate",
			typeName = "UInt32",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3902,
			symbol = "FrameRate")
	public int getFrameRate() 
		throws PropertyNotPresentException {

		if (frameRate == null)
			throw new PropertyNotPresentException("The optional frame rate property is not present in this film descriptor.");
		
		return frameRate;
	}

	@MediaPropertySetter("FrameRate")
	public void setFrameRate(
			Integer rate)
		throws IllegalArgumentException {

		if (rate == null) {
			this.frameRate = null;
			return;
		}
		
		if (rate < 0)
			throw new IllegalArgumentException("Cannot set the frame rate of this film descriptor to a negative value.");
		
		this.frameRate = rate;
	}
	
	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0103, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "PerforationsPerFrame",
			typeName = "UInt8",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3903,
			symbol = "PerforationsPerFrame")
	public byte getPerfortionsPerFrame()
			throws PropertyNotPresentException {

		if (perforationsPerFrame == null)
			throw new PropertyNotPresentException("The optional perforations per frame property is not present for this film descriptor.");

		return perforationsPerFrame;
	}

	@MediaPropertySetter("PerforationsPerFrame")
	public void setPerforationsPerFrame(
			Byte perforationsPerFrame) 
		throws IllegalArgumentException {

		if (perforationsPerFrame == null) {
			this.perforationsPerFrame = null;
			return;
		}
		
		if (perforationsPerFrame < 0)
			throw new IllegalArgumentException("Cannot set the number of perforations per frame to a negative value for this film descriptor.");
		
		this.perforationsPerFrame = perforationsPerFrame;
	}

	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0107, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FilmBatchNumber",
			typeName = "UTF16String", 
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3908,
			symbol = "FilmBatchNumber")
	public String getFilmBatchNumber()
			throws PropertyNotPresentException {

		if (filmBatchNumber == null)
			throw new PropertyNotPresentException("The optional film batch number is not present for this film descriptor.");
		
		return filmBatchNumber;
	}

	@MediaPropertySetter("FilmBatchNumber")
	public void setFilmBatchNumber(
			String filmBatchNumber) {

		this.filmBatchNumber = filmBatchNumber;
	}

	@MediaProperty(uuid1 = 0x04100103, uuid2 = (short) 0x0104, uuid3 = (short) 0x0100,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x02},
			definedName = "FilmFormatName",
			aliases = { "FilmGaugeFormat" },
			typeName = "UTF16String",
			optional = true,
			uniqueIdentifier = false,
			pid = 0x3907,
			symbol = "FilmFormatName")
	public String getFilmFormatName()
			throws PropertyNotPresentException {

		if (filmFormatName == null)
			throw new PropertyNotPresentException("The optional film gauge format property is not present for this film descriptor.");
		
		return filmFormatName;
	}

	@MediaPropertySetter("FilmFormatName")
	public void setFilmFormatName(
			String filmFormatName) {

		this.filmFormatName = filmFormatName;
	}

	public FilmDescriptor clone() {
		
		return (FilmDescriptor) super.clone();
	}
	
	public String getFilmAspectRatioString() {
		
		return RationalImpl.toPersistentForm(filmAspectRatio);
	}
	
	public void setFilmAspectRatioString(
			String filmAspectRatio) {
		
		this.filmAspectRatio = RationalImpl.fromPersistentForm(filmAspectRatio);
	}
}
