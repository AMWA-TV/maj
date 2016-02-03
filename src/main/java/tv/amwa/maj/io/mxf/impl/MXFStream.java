package tv.amwa.maj.io.mxf.impl;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;

import tv.amwa.maj.enumeration.ByteOrder;
import tv.amwa.maj.exception.EndOfDataException;
import tv.amwa.maj.industry.Stream;

public class MXFStream 
	implements 
		Stream, 
		Cloneable {

	public MXFStream(
			URI streamReference) {
		
		// TODO
	}
	
	public void close() {
		// TODO Auto-generated method stub

	}

	public long getLength() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getLimit() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getPosition() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public URI getStreamURI() {
		// TODO Auto-generated method stub
		return null;
	}

	public ByteBuffer read(int noOfBytes) 
		throws EndOfDataException,
			IOException, 
			IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLimit(long limit) 
		throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	public void setPosition(long position) 
		throws IllegalArgumentException,
			IOException {
		// TODO Auto-generated method stub

	}

	public int write(ByteBuffer buffer)
		throws EndOfDataException, 
			IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public MXFStream clone() {
		
		try {
			return (MXFStream) super.clone();
		}
		catch (CloneNotSupportedException cnse) {
			throw new InternalError("Cloning of MXF streams is supported.");
		}
	}

	public ByteOrder getByteOrder() {

		return ByteOrder.Big;
	}
}
