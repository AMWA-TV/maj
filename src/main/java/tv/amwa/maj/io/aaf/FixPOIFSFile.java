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

package tv.amwa.maj.io.aaf;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FixPOIFSFile {

	static final byte[] aafHeaderBytes = new byte[] {
			0x41, 0x41, 0x46, 0x42, 0x0d, 0x00, 0x4f, 0x4d,
			0x06, 0x0e, 0x2b, 0x34, 0x01, 0x01, 0x01, (byte) 0xff
	};

	static final ByteBuffer aafHeaderBytesToWrite = ByteBuffer.wrap(aafHeaderBytes);

	/**
	 * @param args
	 */
	public static void main(String[] args)
		throws Exception {

		RandomAccessFile fixFile = new RandomAccessFile(args[0], "rw");
		FileChannel fixChannel = fixFile.getChannel();

		fixChannel.position(8);
		fixChannel.write(aafHeaderBytesToWrite);
		fixChannel.close();
	}

}
