package tv.amwa.maj.io.mxf.impl;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

import tv.amwa.maj.constant.RP224;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.industry.MediaClass;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.industry.MediaProperty;
import tv.amwa.maj.industry.MediaPropertySetter;
import tv.amwa.maj.industry.MediaSetAdd;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.integer.UInt16;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.integer.UInt64;
import tv.amwa.maj.io.mxf.FixedLengthPack;
import tv.amwa.maj.io.mxf.HeaderOpenIncompletePartitionPack;
import tv.amwa.maj.io.mxf.MXFBuilder;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.Padded;
import tv.amwa.maj.io.mxf.PartitionPack;
import tv.amwa.maj.io.mxf.UL;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

@MediaClass(uuid1 = 0x0d010201, uuid2 = 0x0100, uuid3 = 0x0000,
		uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x02, 0x05, 0x01, 0x01 },
		definedName = "PartitionPack",
		description = "A partition pack describes a single partition within an MXF file.",
		namespace = MXFConstants.RP210_NAMESPACE,
		prefix = MXFConstants.RP210_PREFIX,
		symbol = "PartitionPack")
public abstract class PartitionPackImpl 
	implements 
		MetadataObject,
		FixedLengthPack,
		PartitionPack,
		Cloneable,
		Padded {
	
	
	private short majorVersion = MAJORVERSION_DEFAULT;
	private short minorVersion = MINORVERSION_DEFAULT;
	private int kagSize = 1;
	private long thisPartition;
	private long previousPartition = PREVIOUSPARTITION_DEFAULT;
	private long footerPartition = FOOTERPARTITION_DEFAULT;
	private long headerByteCount = HEADERBYTECOUNT_DEFAULT;
	private long indexByteCount = INDEXBYTECOUNT_DEFAULT;
	private int indexSID = INDEXSID_DEFAULT;
	private long bodyOffset;
	private int bodySID = BODYSID_DEFAULT;
	private AUID operationalPattern;
	private Set<AUID> essenceContainers = new HashSet<AUID>();
	
	private long paddingBytes = 0l;
	
	public final static String[] packOrder = {
		"Major Version", "Minor Version", "KAGSize", "ThisPartition",
		"PreviousPartition", "FooterPartition", "HeaderByteCount",
		"IndexByteCount", "IndexSID", "BodyOffset", "BodySID",
		"Operational Pattern", "EssenceContainers"
	};

	static {
		initializePackClasses();
	}
	
	public final static void initializePackClasses() {
		Warehouse.lookForClass(HeaderClosedCompletePartitionPackImpl.class);
		Warehouse.lookForClass(HeaderClosedIncompletePartitionPackImpl.class);
		Warehouse.lookForClass(HeaderOpenCompletePartitionPackImpl.class);
		Warehouse.lookForClass(HeaderOpenIncompletePartitionPack.class);
		Warehouse.lookForClass(BodyClosedCompletePartitionPackImpl.class);
		Warehouse.lookForClass(BodyClosedIncompletePartitionPackImpl.class);
		Warehouse.lookForClass(BodyOpenCompletePartitionPackImpl.class);
		Warehouse.lookForClass(BodyOpenIncompletePartitionPackImpl.class);
		Warehouse.lookForClass(FooterClosedCompletePartitionPackImpl.class);
		Warehouse.lookForClass(FooterClosedIncompletePartitionPackImpl.class);	
	}

	public PartitionPackImpl() { }
	
	public PartitionPackImpl(
			@UInt32 int kagSize,
			@UInt64 long thisPartition,
			@UInt64 long bodyOffset,
			tv.amwa.maj.record.AUID operationalPattern) {
		
		this.kagSize = kagSize;
		this.thisPartition = thisPartition;
		this.bodyOffset = bodyOffset;
		this.operationalPattern = operationalPattern.clone();
	}
	
	@MediaProperty(uuid1 = 0x03010201, uuid2 = 0x0600, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "Major Version",
			typeName = "UInt16",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "MajorVersion")
	public @UInt16 short getMajorVersion() {
		return majorVersion;
	}
	
	@MediaPropertySetter("Major Version")
	public void setMajorVersion(
			@UInt16 short majorVersion) {
		this.majorVersion = majorVersion;
	}
	
	@MediaProperty(uuid1 = 0x03010201, uuid2 = 0x0700, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "Minor Version",
			typeName = "UInt16",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "MinorVersion")	
	public @UInt16 short getMinorVersion() {
		return minorVersion;
	}
	
	@MediaPropertySetter("Minor Version")
	public void setMinorVersion(
			@UInt16 short minorVersion) {
		this.minorVersion = minorVersion;
	}

	@MediaProperty(uuid1 = 0x03010201, uuid2 = 0x0900, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "KAGSize",
			aliases = { "KAG Size" }, // RP210 has a space
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "KAGSize")	
	public @UInt32 int getKagSize() {
		
		return kagSize;
	}
	
	@MediaPropertySetter("KAGSize")
	public void setKagSize(
			@UInt32 int kagSize) {
		
		this.kagSize = kagSize;
	}
	
	@MediaProperty(uuid1 = 0x06101003, uuid2 = 0x0100, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "ThisPartition",
			aliases = { "Current Number in Sequence" },
			typeName = "UInt64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "ThisPartition")		
	public @UInt64 long getThisPartition() {
		
		return thisPartition;
	}
	
	@MediaPropertySetter("ThisPartition")
	public void setThisPartition(
			@UInt64 long thisPartition) {
		
		this.thisPartition = thisPartition;
	}
	
	@MediaProperty(uuid1 = 0x06101002, uuid2 = 0x0100, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "PreviousPartition",
			aliases = { "Previous Number in Sequence" },
			typeName = "UInt64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "PreviousPartition")		
	public @UInt64 long getPreviousPartition() {
		
		return previousPartition;
	}
	
	@MediaPropertySetter("PreviousPartition")
	public void setPreviousPartition(
			@UInt64 long previousPartition) {
		
		this.previousPartition = previousPartition;
	}
	
	@MediaProperty(uuid1 = 0x06101005, uuid2 = 0x0100, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "FooterPartition",
			aliases = { "Last Number in Sequence" },
			typeName = "UInt64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "FooterPartition")		
	public @UInt64 long getFooterPartition() {
		
		return footerPartition;
	}
	
	@MediaPropertySetter("FooterPartition")
	public void setFooterPartition(
			@UInt64 long footerPartition) {
		
		this.footerPartition = footerPartition;
	}
	
	@MediaProperty(uuid1 = 0x04060901, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "HeaderByteCount",
			aliases = { "Header Byte Count" },
			typeName = "UInt64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "HeaderByteCount")			
	public @UInt64 long getHeaderByteCount() {
		
		return headerByteCount;
	}
	
	@MediaPropertySetter("HeaderByteCount")
	public void setHeaderByteCount(
			@UInt64 long headerByteCount) {
		
		this.headerByteCount = headerByteCount;
	}
	
	@MediaProperty(uuid1 = 0x04060902, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "IndexByteCount",
			aliases = { "Index Byte Count" },
			typeName = "UInt64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "IndexByteCount")			
	public @UInt64 long getIndexByteCount() {
		
		return indexByteCount;
	}
	
	@MediaPropertySetter("IndexByteCount")
	public void setIndexByteCount(
			@UInt64 long indexByteCount) {
		
		this.indexByteCount = indexByteCount;
	}
	
	@MediaProperty(uuid1 = 0x01030405, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "IndexSID",
			aliases = { "Index Stream ID" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "IndexSID")			
	public @UInt32 int getIndexSID() {
		
		return indexSID;
	}
	
	@MediaPropertySetter("IndexSID")
	public void setIndexSID(
			int indexSID) {
		
		this.indexSID = indexSID;
	}

	@MediaProperty(uuid1 = 0x06080102, uuid2 = 0x0103, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "BodyOffset",
			aliases = { "Stream Position Indicator" },
			typeName = "UInt64",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "BodyOffset")			
	public @UInt64 long getBodyOffset() {
		
		return bodyOffset;
	}
	
	@MediaPropertySetter("BodyOffset")
	public void setBodyOffset(
			@UInt64 long bodyOffset) {
		
		this.bodyOffset = bodyOffset;
	}
	
	@MediaProperty(uuid1 = 0x01030404, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 },
			definedName = "BodySID",
			aliases = { "Essence Stream ID" },
			typeName = "UInt32",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "BodySID")			
	public @UInt32 int getBodySID() {
		
		return bodySID;
	}
	
	@MediaPropertySetter("BodySID")
	public void setBodySID(
			@UInt32 int bodySID) {
		
		this.bodySID = bodySID;
	}
	
	// UL copied from Preface.OperationalPattern
	@MediaProperty(uuid1 = 0x01020203, uuid2 = 0x0000, uuid3 = 0x0000,
			uuid4 = { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 },
			definedName = "Operational Pattern",
			typeName = "AUID",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "OperationalPattern")			
	public AUID getOperationalPattern() {
		
		return operationalPattern;
	}
	
	@MediaPropertySetter("Operational Pattern")
	public void setOperationalPattern(
			AUID operationalPattern) {
		
		this.operationalPattern = operationalPattern;
	}
	
	// UL copied from Preface.EssenceContainers
	@MediaProperty(uuid1 = 0x01020210, uuid2 = (short) 0x0201, uuid3 = (short) 0x0000,
			uuid4 = {0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05},
			definedName = "EssenceContainers",
			typeName = "AUIDSet",
			optional = false,
			uniqueIdentifier = false,
			pid = 0x0000,
			symbol = "EssenceContainers")			
	public Set<AUID> getEssenceContainers() {
		return essenceContainers;
	}
	
	@MediaPropertySetter("EssenceContainers")
	public void setEssenceContainers(
			Set<AUID> essenceContainers) {
		
		this.essenceContainers = essenceContainers;
	}
	
	@MediaSetAdd("EssenceContainer")
	public void addEssenceContainer(
			AUID essenceContainerID) 
		throws NullPointerException {
		
		if (essenceContainerID == null)
			throw new NullPointerException("Cannot add a null essence container to the set of essence containers for this partition pack.");
		
		essenceContainers.add(essenceContainerID);
	}
	
	public String[] getPackOrder() {
		
		return packOrder;
	}
	
	public long getPaddingFillSize() {
		
		return paddingBytes;
	}
	
	public int getEncodedSize() {
		
		return 88 + essenceContainers.size() * 16;
	}
	
	public void setPaddingFillSize(
			long paddingFillSize)
		throws IllegalArgumentException {
		
		// TODO consider if this should be a length greater than 16 bytes and a BER length.
		if (paddingFillSize < 0)
			throw new IllegalArgumentException("Number of padding bytes must be greater than 0.");
		
		this.paddingBytes = paddingFillSize;
	}
	
	public String toString() {
		
		return MediaEngine.toString(this);
	}
	
	public PartitionPack clone() {
		
		try {
			return (PartitionPack) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			// Supports cloneable so should never happen
			throw new InternalError(cnse.getMessage());
		}
	}

	public final static PartitionPack readPartitionPack(
			MXFFileImpl material) throws EndOfDataException {
		
		UL key = material.readKey();
		long length = material.readBER();
		ByteBuffer buffer = material.read((int) length);
		buffer.rewind();
		
		return
			(PartitionPack) MXFBuilder.readFixedLengthPack((AUIDImpl) key, buffer);
	}
	
	public final static short initializeMajorVersion() {
		
		return MAJORVERSION_DEFAULT;
	}
	
	public final static short initializeMinorVersion() {
		
		return MINORVERSION_DEFAULT;
	}

	public final static int initializeKAGSize() {
		
		return 1;
	}

	public final static long initializeThisPartition() {
		
		return 0;
	}
	
	public final static long initializePreviousPartition() {
		
		return PREVIOUSPARTITION_DEFAULT;
	}

	public final static long initializeFooterPartition() {
		
		return FOOTERPARTITION_DEFAULT;
	}
	
	public final static long initializeHeaderByteCount() {
		
		return HEADERBYTECOUNT_DEFAULT;
	}
	
	public final static long initializeIndexByteCount() {
		
		return INDEXBYTECOUNT_DEFAULT;
	}

	public final static int initializeIndexSID() {
		
		return 0;
	}

	public final static long initializeBodyOffset() {

		return 0l;
	}
	
	public final static int initializeBodySID() {
		
		return BODYSID_DEFAULT;
	}

	public final static AUID initializeOperationalPattern() {
		
		return RP224.MXF_OP1a_SingleItem_SinglePackage_UniTrack_Stream_Internal;
	}

	public final static Set<AUID> initializeEssenceContainers() {
		
		return new HashSet<AUID>();
	}
}
