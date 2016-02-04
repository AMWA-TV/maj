package tv.amwa.maj.io.mxf;

import tv.amwa.maj.model.Preface;

/**
 * <p>Represents header metadata in an MXF file that must be present in the mandatory
 * {@linkplain HeaderPartition header partition} of every MXF file. Header metadata may
 * also be present in other partitions, either as an exact repeat (e.g. to provide join-in-progress
 * ability to read the stream) or because the file is grawing and the metadata keeps improving
 * as it goes.</p>
 * 
 * <p>Header metadata consists of a
 * {@linkplain PrimerPack primer pack} and a {@linkplain tv.amwa.maj.model.Preface preface}.
 * The primer pack is used as a means of reducing the number of bytes required to
 * serialize the metadata in an MXF file as a local set.</p>
 * 
 *
 *
 * @see MXFBuilder#readLocalSet(UL, java.nio.ByteBuffer, PrimerPack, java.util.Map, java.util.List)
 */
public interface HeaderMetadata 
	extends Cloneable,
		MXFUnit {

	/**
	 * <p>Returns the primer pack used to encode the header metadata.</p>
	 * 
	 * @return Primer pack used to encode the header metadata.
	 */
	public PrimerPack getPrimerPack();
	
	/**
	 * <p>Returns the preface that contains metadata records of the
	 * MXF file.</p>
	 * 
	 * @return Metadata records of the MXF file.
	 */
	public Preface getPreface();
	
	/**
	 * <p>Create a cloned copy of the header metadata.</p>
	 * 
	 * @return Cloned copy of the header metadata.
	 */
	public HeaderMetadata clone();
	
}
