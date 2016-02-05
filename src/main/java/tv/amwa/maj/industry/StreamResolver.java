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

/**
 * <p>Specifies a means for an application to resolve a URI reference to a
 * {@linkplain Stream stream} to a means to read and write the data in that
 * stream. The URI may be a globally unique canonical identifier for the
 * stream or a very specific location, such as the location of an MXF file
 * on a local disk and the body stream identifier for the internal data.</p>
 *
 * <p>The methods of this interface are statically implemented by the
 * {@linkplain MediaEngine media engine} to provide a per-virtual-machine
 * stream resolution strategy that is used by default for all properties of
 * {@linkplain tv.amwa.maj.meta.TypeDefinitionStream stream type}. The strategy
 * can be replaced by changing the stream resolver used by the media engine
 * by calling {@link MediaEngine#changeStreamResolver(StreamResolver)}.</p>
 *
 * <p>The MAJ API has a default {@linkplain LocalFileResolver local file resolver}
 * that is used to provide access to streams that are currently accessible as local
 * files. These are often the same MXF or AAF files that wrap the stream data with
 * its associated metadata.</p>
 *
 *
 *
 * @see Stream
 * @see MemoryResidentStream
 * @see LocalFileResolver
 * @see tv.amwa.maj.model.EssenceData#getEssenceStream()
 *
 */
public interface StreamResolver {

	/**
	 * <p>Resolve the given URI to a {@linkplain Stream stream} that can be used to access
	 * the data at that URI according to resolver policy. The URI may be location and
	 * file-type specific, or it may be a canonical identifier for which a specific location
	 * has already been {@linkplain #registerMapping(URI, URI) registered}.
	 * If a canonical identifier, the resolver should use a local policy to determine which
	 * location to return as a stream.</p>
	 *
	 * @param streamReference Identifier for the stream to resolve.
	 * @return Stream that can be used to read and write data, or <code>null</code> if
	 * the given URI cannot be resolved.
	 *
	 * @throws NullPointerException Cannot resolve a <code>null</code> URI.
	 *
	 * @see #resolveStream(URI)
	 * @see MediaEngine#resolveStream(URI)
	 * @see java.net.URI#create(String)
	 */
	public Stream resolveStream(
			URI streamReference)
		throws NullPointerException;

	/**
	 * <p>Resolve the given URI to a {@linkplain Stream stream} that can be used to access
	 * the data at the URI in a form that matches the given HTTP-like accept criteria. The accept criteria
	 * provide a list of MIME types and quality parameters that indicate types of data
	 * acceptable to the caller, in order of preference. The resolver may choose to use the
	 * accept criteria when selecting a stream to access, according to local resolver policy.
	 * The format of an HTTP accept criteria is defined to be the same as an HTTP accept header,
	 * which can be found in
	 * <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html">section 14 of
	 * HTTP&nbsp;1.1</a>.</p>
	 *
	 * <p>The calling client should look at the URI of the stream returned using
	 * {@linkplain Stream#getStreamURI()} to see if their accept request has been
	 * observed. Note that two calls to this method with the same identifier may
	 * resolve to different streams according to the policy of the local resolved,
	 * for example to handle network routing failures.</p>
	 *
	 * @param streamReference Identifier for the stream to resolve.
	 * @param accept Hint for the resolver as to the acceptable kinds of stream for the
	 * calling client. An accept header must be provided and should be set to "<code>*&#x2f;*</code>"
	 * to get the same behaviour as for {@link #resolveStream(URI)}.
	 * @return Stream that can be used to read and write data, or <code>null</code> if
	 * the given URI cannot be resolved.
	 *
	 * @see NullPointerException Cannot resolve a <code>null</code> URI and/or deal with a <code>null</code>
	 * accept header.
	 * @see IllegalArgumentException The given accept header is not valid.
	 *
	 * @see #resolveStream(URI)
	 * @see #makeSpecific(URI, String)
	 * @see MediaEngine#resolveStream(URI, String)
	 * @see java.net.URI#create(String)
	 */
	public Stream resolveStream(
			URI streamReference,
			String accept)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Make a canonical URI local and file-type specific using the
	 * {@linkplain #registerMapping(URI, URI) registered mappings} of this resolver and
	 * the HTTP-like accept criteria provided. This method is similar to {@link #resolveStream(URI, String)}
	 * except that it does not carry out the final step of resolving the provided URI to a stream.
	 * Instead, it provides the specific URI that would be used to provide access to the stream.</p>
	 *
	 * <p>URIs that are already specific should be passed straight through.</p>
	 *
	 * @param streamReference Identifier for the stream to resolve.
	 * @param accept Hint for the resolver as to the acceptable kinds of stream for the
	 * calling client.
	 * @return Location and file-type specific URI for accessing the stream.
	 *
	 * @see NullPointerException Cannot resolve a <code>null</code> URI and/or deal with a <code>null</code>
	 * accept header.
	 * @see IllegalArgumentException The given accept header is not valid.
	 *
	 * @see #resolveStream(URI, String)
	 * @see MediaEngine#makeSpecific(URI, String)
	 * @see java.net.URI#create(String)
	 */
	public URI makeSpecific(
			URI streamReference,
			String accept)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Register a mapping between the canonical form of a URI for a stream and
	 * a location and file-type specific form. This mapping may be used by future
	 * resolutions from identifier to streams made by this resolver.</p>
	 *
	 * @param canonicalForm Canonical representation of an identifier for a stream.
	 * @param location One possible location and file-type specific identifier for
	 * the stream.
	 *
	 * @throws NullPointerException One or both is the arguments is/are <code>null</code>.
	 * @throws IllegalArgumentException The given canonical identifier is location
	 * specific and/or the given location identifier is not specific enough.
	 *
	 * @see #resolveStream(URI)
	 * @see #resolveStream(URI, String)
	 * @see #makeSpecific(URI, String)
	 * @see MediaEngine#registerMapping(URI, URI)
	 * @see java.net.URI#create(String)
	 */
	public void registerMapping(
			URI canonicalForm,
			URI location)
		throws NullPointerException,
			IllegalArgumentException;

	/**
	 * <p>Remove a canonical identifier or a specific location identifier as a possible
	 * stream resolution source. If the location is canonical, all location-specific
	 * identifiers for that identifier may also be removed.</p>
	 *
	 * @param identifier Identifier to remove from this resolved.
	 * @return Has the identifier been successfully removed? Note that <code>false</code> is
	 * returned if the identifier is not known to this resolver.
	 *
	 * @throws NullPointerException Cannot remove a <code>null</code> identifier.
	 *
	 * @see MediaEngine#removeLocation(URI)
	 */
	public boolean removeLocation(
			URI identifier)
		throws NullPointerException;

	/**
	 * <p>Caches the lookup of a location-specific URI to a stream. This saves the resolver
	 * from having to construct a new stream for the URI from scratch. The location of the
	 * stream is found with a call to {@link Stream#getStreamURI()}.</p>
	 *
	 * @param stream Actual stream to cache.
	 *
	 * @throws NullPointerException Cannot cache a <code>null</code> stream.
	 */
	public void cacheStream(
			Stream stream)
		throws NullPointerException,
			IllegalArgumentException;
}
