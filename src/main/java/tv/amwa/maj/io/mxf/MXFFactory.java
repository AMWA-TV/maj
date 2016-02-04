package tv.amwa.maj.io.mxf;

import java.io.IOException;

import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.io.mxf.impl.DeltaEntryImpl;
import tv.amwa.maj.io.mxf.impl.HeaderOpenIncompletePartitionPackImpl;
import tv.amwa.maj.io.mxf.impl.IndexTableSegmentImpl;
import tv.amwa.maj.io.mxf.impl.MXFFileImpl;
import tv.amwa.maj.meta.impl.TypeDefinitionRecordImpl;

/**
 * <p>Factory for reading and creating {@linkplain MXFFile MXF files}.</p>
 * 
 * <p>To read an MXF file, start by calling {@link #readPartitions(String)} and use
 * the {@link MXFFile} returned to access the data in the file.</p>
 * 
 * <p>The ability to dump a description of what an MXF file contains is provided
 * by the {@link #dumpFile(String)} static method. Alternatively, run this class as a
 * {@linkplain #main(String[]) Java application}.</p>
 * 
 *
 *
 */
public class MXFFactory {

	static {
		MediaEngine.initializeAAF();
		Warehouse.registerTypes(tv.amwa.maj.io.mxf.TypeDefinitions.class, MXFConstants.RP210_NAMESPACE, MXFConstants.RP210_PREFIX);
		Warehouse.lookForClass(IndexTableSegmentImpl.class);
		Warehouse.lookForClass(HeaderOpenIncompletePartitionPackImpl.class);
		
		TypeDefinitionRecordImpl.registerInterfaceMapping(DeltaEntry.class, DeltaEntryImpl.class);
	}
	
	public final static boolean logging = true;
	
	/**
	 * <p>Read and return access to an MXF file with the given file name, building the partition tables
	 * in the process. If an error occurs when reading the file, access to the file is still returned for
	 * reasons of application-specific diagnosis.</p>
	 * 
	 * @param fileName Name of an MXF file to read.
	 * @return Access to an MXF file, with its partitions table already built.
	 * 
	 * @throws NullPointerException Cannot open an MXF file using a <code>null</code> file name.
	 */
	public final static MXFFile readPartitions(
			String fileName) 
		throws NullPointerException {
		
		if (fileName == null)
			throw new NullPointerException("Cannot open a file using a null filename.");
		
		MXFFile mxfFile = new MXFFileImpl();
		
		try {
			if (mxfFile.open(fileName, false)) {
				System.out.println("MXF file " + fileName + " opened successfully. Run-in size is " + mxfFile.getRunInSize() + ".");
			}
			else {
				System.err.println("Could not open file " + fileName + ".");
				return null;
			}
			mxfFile.readRunIn();
			boolean partitionsRead = mxfFile.buildPartitionsTable();
			// System.out.println("Read " + mxfFile.countPartitions() + " partitions from the file.");
		}
		catch (Exception e) {
			System.err.println(e.getClass().getName() + " when reading file \'" + fileName + "\': " + e.getMessage());
			e.printStackTrace();
		}
		
		return mxfFile;
	}
	
	// TODO enhance this with a set of switches that configure what the dump should contain
	// TODO add the ability to dump details of index tables
	// TODO add the ability to dump details of body partitions and their essence containers
	/**
	 * <p>Create a textual dump of some of the information in the MXF file specified by file name. 
	 * The dump includes:</p>
	 * 
	 * <ul>
	 *  <li>Details of the {@linkplain HeaderPartition header partition} pack;</li>
	 *  <li>Details of all {@linkplain BodyPartition body partitions} in the file;<li>
	 *  <li>Details of the {@linkplain FooterPartition footer partition} if present;</li>
	 *  <li>A dump of the mandatory {@linkplain HeaderMetadata header metadata} from the 
	 *  header partition, including the
	 *  {@linkplain PrimerPack primer pack} and {@linkplain tv.amwa.maj.model.Preface preface};</li>
	 *  <li>If present, a dump of any {@linkplain HeaderMetadata header metadata} from the
	 *  footer partition, including the
	 *  {@linkplain PrimerPack primer pack} and {@linkplain tv.amwa.maj.model.Preface preface};</li>
	 *  <li>If present, a dump of the random index pack at the end of the file.</li>
	 * </ul>
	 * 
	 * @param mxfFileName Name of the MXF file to produce dump information for.
	 * @return String containing the dump information requested.
	 * 
	 * @throws NullPointerException Cannot dump an MXF file from a <code>null</code> file name.
	 * @throws IOException Could not open the referenced MXF file for reading.
	 */
	public final static String dumpFile(
			String mxfFileName) 
		throws NullPointerException,
			IOException {
		
		StringBuffer dumpBuffer = new StringBuffer(65536);
		MXFFile mxfFile = readPartitions(mxfFileName);
		if (!mxfFile.isOpen()) 
			throw new IOException("Cannot dump file " + mxfFileName + " as the file could not be opened.");
		dumpBuffer.append("MXF file " + mxfFileName + " openned and contains " + mxfFile.countPartitions() + ".\n\n");
		
		dumpBuffer.append("************************\n");
		dumpBuffer.append("* Header partition pack:\n");
		dumpBuffer.append(mxfFile.getHeaderPartition().toString());
		dumpBuffer.append("\n\n");
		
		for ( int x = 1 ; x < mxfFile.countPartitions() ; x++ ) {
			dumpBuffer.append("Partition " + x);
			Partition partition = mxfFile.getPartitionAt(x);
			if (partition instanceof FooterPartition)
				dumpBuffer.append(" footer partition:\n");
			else
				dumpBuffer.append(" body partition:\n");
			dumpBuffer.append(partition.toString());
			dumpBuffer.append("\n\n");
		}
		
		dumpBuffer.append("**********************************************************************\n");
		dumpBuffer.append("* Header metadata from the header partition - primer pack and preface:\n");
		
		HeaderMetadata fromTheHeader = mxfFile.getHeaderPartition().readHeaderMetadata();
		dumpBuffer.append(fromTheHeader.getPrimerPack().toString());
		dumpBuffer.append("\n");
		dumpBuffer.append(fromTheHeader.getPreface().toString());
		dumpBuffer.append("\n\n");
		
		dumpBuffer.append("**********************************************************************\n");
		dumpBuffer.append("* Essence elements in the header and body partitions:\n");

		for ( int x = 0 ; x < mxfFile.countPartitions() ; x++ ) {
			Partition partition = mxfFile.getPartitionAt(x);
			if (partition instanceof FooterPartition) break;

			dumpBuffer.append("Partition " + x);
			if (partition instanceof HeaderPartition)
				dumpBuffer.append(" header partition:\n");
			else
				dumpBuffer.append(" body partition:\n");
			EssencePartition containerPartition = (EssencePartition) partition;
			
			for ( EssenceElement element = containerPartition.readEssenceElement() ; 
					element != null ; 
					element = containerPartition.readEssenceElement() ) {
				
				dumpBuffer.append(element.toString());
				dumpBuffer.append("\n");				
			}
		}
		
		if (mxfFile.getFooterPartition() == null) {
			
			dumpBuffer.append("**********************************\n");
			dumpBuffer.append("* No footer partition in the file.\n\n");
		}
		else if (mxfFile.getFooterPartition().readHeaderMetadata() == null) {
			dumpBuffer.append("***********************************************\n");
			dumpBuffer.append("* No header metadata found in this file footer.\n\n");
		}
		else {
			HeaderMetadata fromTheFooter = null;
			fromTheFooter = mxfFile.getFooterPartition().readHeaderMetadata();
			dumpBuffer.append("*********************************************************************\n");
			dumpBuffer.append("* Header metadata from the footer parition - primer pack and preface:\n");
			dumpBuffer.append(fromTheFooter.getPrimerPack().toString());
			dumpBuffer.append("\n");
			dumpBuffer.append(fromTheFooter.getPreface().toString());
			dumpBuffer.append("\n\n");
		}
	
		RandomIndexPack rip = mxfFile.getRandomIndexPack();
		if (rip == null) {
			dumpBuffer.append("***********************************\n");
			dumpBuffer.append("* No random index pack in the file.\n");
		}
		else {
			dumpBuffer.append("********************\n");
			dumpBuffer.append("* Random index pack:");
			dumpBuffer.append(rip.toString());
		}
		
		mxfFile.close();
		
		return dumpBuffer.toString();
	}
			
	/**
	 * <p>Create an empty MXF file that is to be built up from scratch. The file is not
	 * open at this point and must be opened using {@link MXFFile#open(String, boolean)}
	 * before writing any data.</p>
	 * 
	 * @return Empty MXF file.
	 * 
	 * @see MXFFile#open(String, boolean)
	 */
	public final static MXFFile emptyFile() {
		
		MXFFile emptyFile = new MXFFileImpl();
		return emptyFile;
	}
	
	/**
	 * <p>Create a new {@linkplain DeltaEntry delta entry} for an {@linkplain IndexTableSegment index
	 * table segment} using fully specified component values.</p>
	 * 
	 * @param posTableIndex If and how the element is subject to temporal reordering. See 
	 * {@link DeltaEntry#setPosTableIndex(byte)}.
	 * @param slice Slice number for the slice containing this delta entry.
	 * @param elementDelta Offset measured in bytes from the start of the start of the indexed 
	 * element described by this delta entry and the start of the current slice.
	 * 
	 * @return Newly created delta entry.
	 * 
	 * @throws IllegalArgumentException One or more of the given values is out of range.
	 * 
	 * @see IndexTableSegment#setDeltaEntryArray(DeltaEntry[])
	 */
	public final static DeltaEntry makeDeltaEntry(
			byte posTableIndex,
			byte slice,
			int elementDelta)
		throws IllegalArgumentException {
		
		return new DeltaEntryImpl(posTableIndex, slice, elementDelta);
	}
	
	/**
	 * <p>Create a new {@linkplain DeltaEntry delta entry} for an {@linkplain IndexTableSegment index
	 * table segment} using the element delta and default values.</p>
	 * 
	 * @param elementDelta Offset measured in bytes from the start of the start of the indexed 
	 * element described by this delta entry and the start of the current slice.
	 * 
	 * @return Newly created delta entry.
	 * 
	 * @throws IllegalArgumentException The value of an element delta offset cannot be negative.
	 * 
	 * @see IndexTableSegment#setDeltaEntryArray(DeltaEntry[])
	 * @see IndexTableSegment#setDeltaEntries(int[])
	 */
	public final static DeltaEntry makeDeltaEntry(
			int elementDelta) 
		throws IllegalArgumentException {
		
		return new DeltaEntryImpl(elementDelta);
	}
	
	/**
	 * <p>Dump a description of the contents of the MXF file with the given filename to the system out 
	 * stream. The output is as described for {@link #dumpFile(String)}.</p>
	 * 
	 * @param args Name of the MXF file to dump.
	 * 
	 * @throws Exception Exception thrown during the dump process.
	 * 
	 * @see #dumpFile(String)
	 */
	public final static void main(
			String args[]) 
		throws Exception {
		
		if (args.length < 1) {
			System.err.println("Please provide the name of an MXF file to analyse.");
			System.exit(1);
		}
		
		String fileName = args[0];
		System.out.println(dumpFile(fileName));	
	}
}
