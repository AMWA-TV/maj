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

 // Example of MAJ for AMWA training course

package tv.amwa.maj.example;

import tv.amwa.maj.constant.OperationConstant;
import tv.amwa.maj.constant.ParameterConstant;
import tv.amwa.maj.constant.TransferCharacteristicType;
import tv.amwa.maj.constant.UsageType;
import tv.amwa.maj.enumeration.AlphaTransparencyType;
import tv.amwa.maj.enumeration.ColorSitingType;
import tv.amwa.maj.enumeration.FieldNumber;
import tv.amwa.maj.enumeration.LayoutType;
import tv.amwa.maj.enumeration.SignalStandardType;
import tv.amwa.maj.enumeration.TapeFormatType;
import tv.amwa.maj.enumeration.VideoSignalType;
import tv.amwa.maj.extensions.avid.AvidFactory;
import tv.amwa.maj.extensions.avid.CDCIDescriptor;
import tv.amwa.maj.extensions.avid.TapeDescriptor;
import tv.amwa.maj.extensions.avid.WAVEPCMDescriptor;
import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.aaf.AAFFactory;
import tv.amwa.maj.io.xml.XMLFactory;
import tv.amwa.maj.model.AAFFileDescriptor;
import tv.amwa.maj.model.CompositionPackage;
import tv.amwa.maj.model.ConstantValue;
import tv.amwa.maj.model.ContentStorage;
import tv.amwa.maj.model.DataDefinition;
import tv.amwa.maj.model.Locator;
import tv.amwa.maj.model.MaterialPackage;
import tv.amwa.maj.model.MultipleDescriptor;
import tv.amwa.maj.model.NetworkLocator;
import tv.amwa.maj.model.OperationDefinition;
import tv.amwa.maj.model.OperationGroup;
import tv.amwa.maj.model.Package;
import tv.amwa.maj.model.Parameter;
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.model.Sequence;
import tv.amwa.maj.model.SourceClip;
import tv.amwa.maj.model.SourcePackage;
import tv.amwa.maj.model.TimelineTrack;
import tv.amwa.maj.model.Track;
import tv.amwa.maj.model.Transition;


/**
 * <p>Example of a class that creates some instances of AAF classes and writes them either to an
 * XML or AAF structured storage file. To run the class, try:</p>
 *
 * <pre>
 * java tv.amwa.maj.example.CompositionExample &lt;filename(.aaf|.isr|.xml)&gt;
 * </pre>
 *
 * <p>The type of file to save is determined from the extension, where <code>.isr</code> and <code>.aaf</code> both
 * produce a structured storage file.</p>
 *
 * <p>Note that you need the MAJ API on your classpath, either as a jar file or the compiled
 * classes. To write a structured storage file, you must also have the
 * <a href="http://poi.apache.org/poifs/index.html">POIFS library from
 * Apache</a> on the classpath.</p>
 *
 *
 *
 */
public class CompositionExample {

	/**
	 * <p>Create some AAF classes representing a package hierarchy, from top level composition
	 * down to top-level file packages, with a tape descriptor at the end of the source-reference
	 * chain. Write these packages to an XML or AAF structured storage file.</p>
	 *
	 * @param args The filename to write the file to relative to the present working directory.
	 */
	public static final void main(
			String args[]) {

		if (args.length == 0) {
			System.err.println(
					"Example composition generator: java tv.amwa.maj.example.CompositionExample <filename(.aaf/.isr/.xml)>");
			System.exit(1);
		}

		MediaEngine.initializeAAF(); // Load all classes into the virtual machine for dealing with AAF/.ISR data model

		try {
			Preface preface = makeExamplePreface();

			if ((args[0].endsWith(".aaf")) || (args[0].endsWith(".isr"))) { // Structured storage file requested
				AvidFactory.registerAvidExtensions(); // Line will be removed on final release of MAJ
				AAFFactory.writePreface(preface, args[0]);
			}
			else { // Else assume an XML file is required
				AvidFactory.registerAvidExtensions(); // Line will be removed on final release of MAJ
				XMLFactory.writePreface(preface, args[0]);
			}
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * <p>Create all the AAF objects required to represent the example top level composition.</p>
	 */
	public final static Preface makeExamplePreface()
		throws Exception {

		// Start at the top ... a top-level composition
		CompositionPackage rootComposition = Forge.make(CompositionPackage.class,
				"PackageName", "RootPackage",
				"PackageID", Forge.dCinemaUMID(),
				"PackageUsage", UsageType.TopLevel);

		int sourceVideoTrackID = 2;
		int sourceAudioTrackID = 3;
		int sourceEssenceLength = 5 * 25; // Five seconds of material at 25 fps

		TapeDescriptor tapeDescription = Forge.make(TapeDescriptor.class);
		tapeDescription.setSignalType(VideoSignalType.PALSignal);
		tapeDescription.setTapeBatchNumber("PL/1234/12");
		tapeDescription.setTapeManufacturer("Sony");
		tapeDescription.setTapeFormulation("Digibeta");
		tapeDescription.setTapeFormat(TapeFormatType.BetacamFormat);
		tapeDescription.setTapeCapacity(30);

		SourcePackage sourceTape = Forge.make(SourcePackage.class,
				"PackageName", "SourceTape",
				"PackageID", Forge.randomUMID(),
				"PackageTracks", new Track[] {
						makeTimelineTrack("Picture", null, 0, 0l, 1, 30 * 60 * 60 * 25), // 30 minute tape
						makeTimelineTrack("Sound", null, 0, 0l, 2, 30 * 60 * 60 * 25) },
				"EssenceDescription", tapeDescription);

		// To be made from the following source file - external essence so no essence data
		SourcePackage sourceFile = Forge.makeAAF("SourceMob", // Can use AAF names with the factory
				"PackageName", "SourceFile",
				"PackageID", Forge.randomUMID(),
				"PackageTracks", new Track[] {
						makeTimelineTrack("Picture", sourceTape, 1, 36000l * 25l, sourceVideoTrackID, 125l),
						makeTimelineTrack("Sound", sourceTape, 2, 36000l * 25l, sourceAudioTrackID, 125l) },
				"EssenceDescription", Forge.make(MultipleDescriptor.class,
						"FileDescriptors", new AAFFileDescriptor[] {
							    makeIMX50VideoDescriptor(sourceEssenceLength, sourceVideoTrackID),
							    makeWAVEPCMDescriptor(sourceEssenceLength, sourceAudioTrackID) },
						"Locators", new Locator[] {
								Forge.make(NetworkLocator.class, "URL", "file://library/media/imx50-example.mxf") }) );

		MaterialPackage masterPackage = Forge.make(MaterialPackage.class,
				"PackageName", "MasterMaterial",
				"PackageID", Forge.randomUMID(),
				"PackageTracks", new Track[] {
						makeTimelineTrack("Picture", sourceFile, sourceVideoTrackID, 0l, sourceVideoTrackID, 125l),
						makeTimelineTrack("Sound", sourceFile, sourceAudioTrackID, 0l, sourceAudioTrackID, 125) });

		Sequence videoSequence = Forge.make(Sequence.class, "ComponentDataDefinition", "Picture");
		Sequence audioSequence = Forge.make(Sequence.class, "ComponentDataDefinition", "Sound");

		OperationGroup videoDissolve = Forge.make(OperationGroup.class,
				"ComponentDataDefinition", "Picture",
				"ComponentLength", 12);
		videoDissolve.setOperationDefinition(
				Warehouse.lookup(OperationDefinition.class, OperationConstant.VideoDissolve));
		Parameter parameter = Forge.make(ConstantValue.class,
				"ParameterDefinitionReference", ParameterConstant.Level,
				"Value", Forge.makeRational(1, 2));
		videoDissolve.addParameter(parameter);

		Transition videoTranstion = Forge.make(Transition.class,
				"ComponentDataDefinition", "Picture",
				"ComponentLength", 12,
				"TransitionOperation", videoDissolve);

		OperationGroup audioDissolve = Forge.make(OperationGroup.class,
				"ComponentDataDefinition", "Sound",
				"ComponentLength", 12,
				"Operation", "MonoAudioDissolve",
				"Parameters", new Parameter[] {
						Forge.make(ConstantValue.class,
								"ParameterDefinitionReference", ParameterConstant.Level,
								"Value", Forge.makeRational(1, 2)) });

		Transition audioTransition = Forge.make(Transition.class,
				"ComponentDataDefinition", "Sound",
				"ComponentLength", 12,
				"TransitionOperation", audioDissolve);

		videoSequence.appendComponentObject(makeSourceClip("Picture", masterPackage, sourceVideoTrackID, 0, 50));
		videoSequence.appendComponentObject(videoTranstion);
		videoSequence.appendComponentObject(makeSourceClip("Picture", masterPackage, sourceVideoTrackID, 75, 50));

		audioSequence.appendComponentObject(makeSourceClip("Sound", masterPackage, sourceAudioTrackID, 0, 50));
		audioSequence.appendComponentObject(audioTransition);
		audioSequence.appendComponentObject(makeSourceClip("Sound", masterPackage, sourceAudioTrackID, 75, 50));

		rootComposition.appendNewTimelineTrack(
				Forge.makeRational(25, 1), videoSequence, sourceVideoTrackID, "VideoTrack", 0);
		rootComposition.appendNewTimelineTrack(
				Forge.makeRational(25, 1), audioSequence, sourceAudioTrackID, "AudioTrack", 0);

		return Forge.make(Preface.class,
				"ContentStorageObject", Forge.make(ContentStorage.class,
						"Packages", new Package[] {
							sourceTape, sourceFile, rootComposition, masterPackage }));
	}

	/**
	 * <p>Example utility method to tidy up making an IMX50 picture descriptor.</p>
	 */
	public final static CDCIDescriptor makeIMX50VideoDescriptor(
			long essenceLength,
			int linkedTrackID) {

		return Forge.make(CDCIDescriptor.class,
				"SampleRate", "25/1",
				"ContainerFormat", "MXFGC_Framewrapped_SMPTE_D10_625x50I_50Mbps_DefinedTemplate",
				"EssenceLength", essenceLength,
				"PictureCompression", "urn:smpte:ul:060e2b34.04010101.04010202.01020101",
				"FrameLayout", LayoutType.SeparateFields,
				"VideoLineMap", new int[] { 7, 320 },
				"ImageAspectRatio", Forge.makeRational(16, 9),
				"AlphaTransparency", AlphaTransparencyType.MinValueTransparent,
				"ImageAlignmentFactor", 0,
				"TransferCharacteristic", TransferCharacteristicType.ITU470_PAL,
				"ImageStartOffset", 0,
				"ImageEndOffset", 0,
				"FieldDominance", FieldNumber.One,
				"DisplayF2Offset", 0,
				"StoredF2Offset", 0,
				"SignalStandard", SignalStandardType.ITU601,
				"DisplayHeight", 288,
				"DisplayWidth", 720,
				"DisplayXOffset", 0,
				"DisplayYOffset", 16,
				"SampledHeight", 304,
				"SampledWidth", 720,
				"SampledXOffset", 0,
				"SampledYOffset", 0,
				"StoredHeight", 304,
				"StoredWidth", 720,
				"AlphaSampleDepth", 0,
				"BlackRefLevel", 16,
				"ColorRange", 225,
				"ColorSiting", ColorSitingType.Rec601,
				"ComponentDepth", 8,
				"HorizontalSubsampling", 2,
				"PaddingBits", 0,
				"ReversedByteOrder", false,
				"VerticalSubsampling", 1,
				"WhiteRefLevel", 235,
				"LinkedTrackID", linkedTrackID);
	}

	/**
	 * <p>Example utility method to tidy up the making of a WAVE PCM descriptor.</p>
	 */
	public final static WAVEPCMDescriptor makeWAVEPCMDescriptor(
			long essenceLength,
			int linledTrackID) {

		return Forge.make(WAVEPCMDescriptor.class,
				"SampleRate", "25/1",
				"ContainerFormat", "MXFGC_Clipwrapped_Broadcast_Wave_audio_data",
				"EssenceLength", essenceLength,
				"AudioSampleRate", Forge.makeRational(48000, 1),
				"QuantizationBits", 16,
				"ChannelCount", 1,
				"Locked", true,
				"AverageBytesPerSecond", 96000,
				"BlockAlign", 2,
				"ChannelAssignment", "urn:uuid:4b7093c0-c8d2-4f9a-aadc-c1a8d556d3e3",
				"LinkedTrackID", 3);
	}

	/**
	 * <p>Example utility method to speed up making a timeline track.</p>
	 */
	public final static TimelineTrack makeTimelineTrack(
			String trackType,
			Package sourceChainReference,
			int sourceTrackID,
			long startPosition,
			int localTrackID,
			long componentLength) {

		SourceClip clipReference =
			makeSourceClip(trackType, sourceChainReference, sourceTrackID, startPosition, componentLength);

		return Forge.makeAAF("TimelineMobSlot", // This examples uses AAF names
				"SlotID", localTrackID,
				"SlotName", "Slot" + localTrackID,
				"TimelineMobSlotEditRate", "25/1",
				"MobSlotSegment", clipReference);
	}

	/**
	 * <p>Example utility method useful to speed up the making of source clips.</p>
	 */
	public final static SourceClip makeSourceClip(
			String trackType,
			Package sourcePackage,
			int sourceTrackID,
			long startPosition,
			long componentLength) {

		if (sourcePackage != null) {
			return Forge.make(SourceClip.class,
					"ComponentDataDefinition", Warehouse.lookup(DataDefinition.class, trackType),
					"ComponentLength", componentLength,
					"StartPosition", 0l,
					"SourceTrackID", sourceTrackID,
					"SourcePackageID", sourcePackage.getPackageID(),
					"StartPosition", startPosition);
		}
		else { // If no source package is provided, make an original source reference
			SourceClip clipReference = Forge.make(SourceClip.class);
			clipReference.setComponentDataDefinition(Warehouse.lookup(DataDefinition.class, trackType));
			clipReference.setComponentLength(componentLength);
			clipReference.setSourceReference(Forge.originalSource());
			return clipReference;
		}
	}
}
