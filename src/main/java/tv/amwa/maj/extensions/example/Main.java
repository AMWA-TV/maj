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

package tv.amwa.maj.extensions.example;

import tv.amwa.maj.industry.Forge;

/**
 * <p>Test of the example extension.</p>
 *
 *
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
						"Name", "Richard Rogers",
						"DOB", Forge.makeDate((byte) 11, (byte) 10, (short) 2003)));
		System.out.println(description.toString());
	}

}
