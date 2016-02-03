package tv.amwa.maj.io.mxf.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import tv.amwa.maj.io.mxf.LocalTagEntry;
import tv.amwa.maj.io.mxf.MXFConstants;
import tv.amwa.maj.io.mxf.PrimerPack;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.record.AUID;
import tv.amwa.maj.record.impl.AUIDImpl;

public class IndexPrimerPackImpl 
	implements PrimerPack {

	private final static HashMap<Short, AUID> indexTags = 
		new HashMap<Short, AUID>(11);
	private final static HashMap<AUID, Short> reverseIndexTags =
		new HashMap<AUID, Short>(11);
	private final static Set<LocalTagEntry> localTagBatch = 
		new HashSet<LocalTagEntry>(11);
	
	private final static AUID indexEditRateKey = 
		new AUIDImpl(0x05300406, (short) 0x0000, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 });
	private final static AUID indexStartPositionKey =
		new AUIDImpl(0x07020103, (short) 0x010a, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 });
	private final static AUID indexDurationKey = 
		new AUIDImpl(0x07020201, (short) 0x0102, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 });
	private final static AUID editUnitByteCountKey =
		new AUIDImpl(0x04060201, (short) 0x0000, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 });
	private final static AUID indexSIDKey =
		new AUIDImpl(0x01030405, (short) 0x0000, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 });
	private final static AUID bodySIDKey =
		new AUIDImpl(0x01030404, (short) 0x0000, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 });
	private final static AUID sliceCountKey =
		new AUIDImpl(0x04040401, (short) 0x0100, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x04 });
	private final static AUID posTableCountKey =
		new AUIDImpl(0x04040401, (short) 0x0700, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 });
	private final static AUID deltaEntryArrayKey =
		new AUIDImpl(0x04040401, (short) 0x0600, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 });
	private final static AUID indexEntryArrayKey =
		new AUIDImpl(0x04040402, (short) 0x0500, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x05 } );
	private final static AUID extStartOffsetKey =
		new AUIDImpl(0x04060204, (short) 0x0000, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0a } );
	private final static AUID vbeByteCountKey =
		new AUIDImpl(0x04060205, (short) 0x0000, (short) 0x0000,
				new byte[] { 0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, 0x0a } );
	
	static {
		indexTags.put(MXFConstants.InstanceTag, MXFConstants.InstanceUID);
		indexTags.put((short) 0x3f0b, indexEditRateKey);
		indexTags.put((short) 0x3f0c, indexStartPositionKey);
		indexTags.put((short) 0x3f0d, indexDurationKey);
		indexTags.put((short) 0x3f05, editUnitByteCountKey);
		indexTags.put((short) 0x3f06, indexSIDKey);
		indexTags.put((short) 0x3f07, bodySIDKey);
		indexTags.put((short) 0x3f08, sliceCountKey);
		indexTags.put((short) 0x3f0e, posTableCountKey); 
		indexTags.put((short) 0x3f09, deltaEntryArrayKey);
		indexTags.put((short) 0x3f0a, indexEntryArrayKey);
		indexTags.put((short) 0x3f0f, extStartOffsetKey);
		indexTags.put((short) 0x3f10, vbeByteCountKey);
		
		for (Entry<Short, AUID> entry : indexTags.entrySet()) {
			reverseIndexTags.put(entry.getValue(), entry.getKey());
			localTagBatch.add(new LocalTagEntryImpl(entry.getKey(), entry.getValue()));
		}
	}
	
	public void addLocalTagEntry(LocalTagEntry localTagEntry)
			throws NullPointerException {			
	}

	public void addLocalTagEntry(
			short localTag, AUID uid) {
		
	}

	public void clearLocalTagEntries() {
		
	}

	public int countLocalTagEntries() {
		
		return indexTags.size();
	}

	public Set<LocalTagEntry> getLocalTagEntryBatch() {
		return localTagBatch;
	}

	public boolean isLocalTagEntryPresent(
			LocalTagEntry localTagEntry) {
		
		return false;
	}

	public AUID lookupUID(
			short localTag) {

		return indexTags.get(localTag);
	}
	
	public PrimerPack clone() { 
		
		return null;
	}

	public void addLocalTagEntry(
			PropertyDefinition propertyDefinition)
			throws NullPointerException {
		
	}
	
	public String[] getPackOrder() {

		return PrimerPackImpl.packOrder;
	}

	public Short lookupLocalTag(AUID uid) 
		throws NullPointerException {

		return null;
	}

	public Short lookupLocalTag(
			PropertyDefinition propertyDefinition)
		throws NullPointerException {

		if (propertyDefinition == null)
			throw new NullPointerException("Cannot find the index tag for a null property definition.");
		
		return reverseIndexTags.get(propertyDefinition.getAUID());
	}
		
}
