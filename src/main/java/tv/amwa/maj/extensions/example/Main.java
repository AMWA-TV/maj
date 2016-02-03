package tv.amwa.maj.extensions.example;

import tv.amwa.maj.industry.Forge;

/**
 * <p>Test of the example extension.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 */
public class Main {

	/**
	 * <p>Tests the example factory by making a simple description and a person.</p>
	 * 
	 * @param args Ignored.
	 */
	public static void main(String[] args) {
		
		EgFactory.initialize();
		SimpleDescription description = EgFactory.make("SimpleDescription",
				"Title", "Test Description",
				"Identifier", "123/456/789/000",
				"DateAccepted", "2011-01-13",
				"Creator", EgFactory.make("Person", 
						"Name", "Richard Cartwright",
						"DOB", Forge.makeDate((byte) 11, (byte) 10, (short) 2003)));
		System.out.println(description.toString());
	}

}
