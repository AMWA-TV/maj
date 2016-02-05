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

import org.apache.poi.poifs.filesystem.POIFSWriterEvent;
import org.apache.poi.poifs.filesystem.POIFSWriterListener;

public class AAFTestWrite {

	static class ReaderListener
		implements POIFSWriterListener {

		public void processPOIFSWriterEvent(
				POIFSWriterEvent event) {

			System.err.println(event.getPath());
		}

	}

	static ReaderListener myListener = new ReaderListener();

	/**
	 * @param args
	 */
//	public static void main(
//			String[] args) {
//
//		POIFSFileSystem poifs = new POIFSFileSystem();
//
//		DirectoryEntry prefaceDir = poifs.createDirectory("Preface-XYZ");
//		// prefaceDir.setStorageClsid();
//
//		prefaceDir.createDocument("properties", 1024, myListener);
//
//		prefaceDir.createDirectory("ContentStorage-YYYY");
//
//
//	}

}
