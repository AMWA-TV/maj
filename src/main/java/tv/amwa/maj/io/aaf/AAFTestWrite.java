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
