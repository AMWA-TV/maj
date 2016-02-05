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

package tv.amwa.maj.industry;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tv.amwa.maj.io.aaf.AAFStream;
import tv.amwa.maj.io.mxf.impl.MXFStream;
import tv.amwa.maj.io.xml.XMLStream;

/**
 * <p>Default implementation of a {@linkplain StreamResolver stream resolver} that resolves URIs for
 * streams to {@linkplain Stream stream} access to local files.</p>
 *
 * <p>The local file resolver can handle the following types of local streams:</p>
 *
 * <ul>
 *  <li>{@linkplain tv.amwa.maj.io.aaf.AAFStream wrapped inside AAF structured storage files};</li>
 *  <li>{@linkplain tv.amwa.maj.io.mxf.MXFStream wrapped as body streams inside MXF files};</li>
 *  <li>{@linkplain tv.amwa.maj.io.xml.XMLStream referenced raw essence streams from XML files};</li>
 *  <li>{@linkplain FileStream raw essence streams as unwrapped files};</li>
 *  <li>{@linkplain MemoryResidentStream memory resident streams}.</li>
 * </ul>
 *
 * <p>This implementation is fairly dumb, returns the first location that it finds and
 * pays little attention to accept criteria, except as noted in
 * {@link #resolveStream(URI, String)}.</p>
 *
 *
 *
 * @see MediaEngine#getStreamResolver()
 * @see MediaEngine#changeStreamResolver(StreamResolver)
 * @see tv.amwa.maj.meta.TypeDefinitionStream
 * @see tv.amwa.maj.model.EssenceData#getEssenceStream()
 */
public class LocalFileResolver
	implements
		StreamResolver {

	private Map<URI, List<URI>> mappings;
	private Map<URI, Stream> streamCache;

	public LocalFileResolver() {

		mappings = Collections.synchronizedMap(new HashMap<URI, List<URI>>());
		streamCache = Collections.synchronizedMap(new HashMap<URI, Stream>());
	}

	public URI makeSpecific(
			URI streamReference,
			String accept)
		throws NullPointerException,
			IllegalArgumentException {

		if (streamReference == null)
			throw new NullPointerException("Cannot make a null stream reference specific.");

		if (mappings.containsKey(streamReference))
			return mappings.get(streamReference).get(0);

		return null;
	}

	public void registerMapping(
			URI canonicalForm,
			URI location)
		throws NullPointerException,
			IllegalArgumentException {

		if (canonicalForm == null)
			throw new NullPointerException("Cannot register a null canonical URI reference.");
		if (location == null)
			throw new NullPointerException("Cannot register a null location URI reference.");

		// TODO test the specificity of the canonical URI or generality of the location URI

		List<URI> locationList = null;
		if (mappings.containsKey(canonicalForm)) {
			locationList = mappings.get(canonicalForm);
		}
		else {
			locationList = Collections.synchronizedList(new ArrayList<URI>());
			mappings.put(canonicalForm, locationList);
		}

		locationList.add(location);
	}

	public boolean removeLocation(
			URI identifier)
		throws NullPointerException {

		if (identifier == null)
			throw new NullPointerException("Cannot remove a null location.");

		if (mappings.containsKey(identifier)) {
			streamCache.remove(identifier);
			mappings.remove(identifier);
			return true;
		}

		boolean removedFromValues = false;
		for ( List<URI> uriList : mappings.values() ) {
			if (uriList.contains(identifier)) {
				uriList.remove(identifier);
				removedFromValues = true;
			}
		}

		return removedFromValues;
	}

	public Stream resolveStream(
			URI streamReference)
		throws NullPointerException {

		if (streamReference == null)
			throw new NullPointerException("Cannot resolve a stream with a null reference.");

		if (streamCache.containsKey(streamReference)) {
			return streamCache.get(streamReference).clone();
		}

		if (streamReference.getQuery().contains("type=ss")) {
			AAFStream stream = new AAFStream(streamReference);
			streamCache.put(streamReference, stream);
			return stream;
		}

		if (streamReference.getQuery().contains("type=klv")) {
			MXFStream stream = new MXFStream(streamReference);
			streamCache.put(streamReference, stream);
			return stream;
		}

		if (streamReference.getQuery().contains("type=xml")) {
			XMLStream stream = new XMLStream(streamReference);
			streamCache.put(streamReference, stream);
			return stream;
		}

		return new FileStream(streamReference);
	}

	public Stream resolveStream(
			URI streamReference,
			String accept)
		throws NullPointerException,
			IllegalArgumentException {

		if (streamReference == null)
			throw new NullPointerException("Cannot resolve a stream from a null reference.");
		if (accept == null)
			throw new NullPointerException("Cannot process null accept criteria.");
		if (!accept.equals("*/*"))
			throw new IllegalArgumentException("The local file resolver only accepts */* as accept criteria.");

		return resolveStream(streamReference);
	}

	/**
	 * <p>Clear the internal stream cache of this local file resolver of all file references.
	 * This may be useful if the resolver is producing unexpected results or after a file system
	 * outage. Memory resident streams are not cleared as this could cause them to be lost
	 * all together.</p>
	 */
	public void clearStreamCacheFiles() {

		for ( URI uri : streamCache.keySet() )
			if (!(streamCache.get(uri) instanceof MemoryResidentStream))
				streamCache.remove(uri);
	}

	public void cacheStream(
			Stream stream)
		throws NullPointerException,
			IllegalArgumentException {

		if (stream == null)
			throw new NullPointerException("Cannot cache a stream location from a null stream value.");

		streamCache.put(stream.getStreamURI(), stream);
	}
}
