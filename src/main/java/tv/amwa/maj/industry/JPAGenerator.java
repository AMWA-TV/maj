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

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import tv.amwa.maj.constant.CommonConstants;
import tv.amwa.maj.extensions.avid.AvidFactory;
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.meta.ClassDefinition;
import tv.amwa.maj.meta.PropertyDefinition;
import tv.amwa.maj.meta.TypeDefinition;
import tv.amwa.maj.meta.TypeDefinitionFixedArray;
import tv.amwa.maj.meta.TypeDefinitionRecord;
import tv.amwa.maj.meta.TypeDefinitionSet;
import tv.amwa.maj.meta.TypeDefinitionStrongObjectReference;
import tv.amwa.maj.meta.TypeDefinitionVariableArray;
import tv.amwa.maj.model.InterchangeObject;
import tv.amwa.maj.model.impl.AAFFileDescriptorImpl;
import tv.amwa.maj.model.impl.AES3PCMDescriptorImpl;
import tv.amwa.maj.model.impl.AIFCDescriptorImpl;
import tv.amwa.maj.model.impl.AuxiliaryDescriptorImpl;
import tv.amwa.maj.model.impl.BWFImportDescriptorImpl;
import tv.amwa.maj.model.impl.CDCIDescriptorImpl;
import tv.amwa.maj.model.impl.CodecDefinitionImpl;
import tv.amwa.maj.model.impl.CommentMarkerImpl;
import tv.amwa.maj.model.impl.ComponentImpl;
import tv.amwa.maj.model.impl.CompositionPackageImpl;
import tv.amwa.maj.model.impl.ConstantValueImpl;
import tv.amwa.maj.model.impl.ContainerDefinitionImpl;
import tv.amwa.maj.model.impl.ControlPointImpl;
import tv.amwa.maj.model.impl.DataDefinitionImpl;
import tv.amwa.maj.model.impl.DataEssenceDescriptorImpl;
import tv.amwa.maj.model.impl.DefinitionObjectImpl;
import tv.amwa.maj.model.impl.DescriptiveClipImpl;
import tv.amwa.maj.model.impl.DescriptiveFrameworkImpl;
import tv.amwa.maj.model.impl.DescriptiveMarkerImpl;
import tv.amwa.maj.model.impl.DescriptiveObjectImpl;
import tv.amwa.maj.model.impl.DictionaryImpl;
import tv.amwa.maj.model.impl.EdgeCodeSegmentImpl;
import tv.amwa.maj.model.impl.EssenceDataImpl;
import tv.amwa.maj.model.impl.EssenceDescriptorImpl;
import tv.amwa.maj.model.impl.EventImpl;
import tv.amwa.maj.model.impl.EventTrackImpl;
import tv.amwa.maj.model.impl.FillerImpl;
import tv.amwa.maj.model.impl.FilmDescriptorImpl;
import tv.amwa.maj.model.impl.GPITriggerImpl;
import tv.amwa.maj.model.impl.HTMLClipImpl;
import tv.amwa.maj.model.impl.HTMLDescriptorImpl;
import tv.amwa.maj.model.impl.IdentificationImpl;
import tv.amwa.maj.model.impl.ImportDescriptorImpl;
import tv.amwa.maj.model.impl.InterchangeObjectImpl;
import tv.amwa.maj.model.impl.InterpolationDefinitionImpl;
import tv.amwa.maj.model.impl.KLVDataDefinitionImpl;
import tv.amwa.maj.model.impl.KLVDataImpl;
import tv.amwa.maj.model.impl.LocatorImpl;
import tv.amwa.maj.model.impl.MPEGVideoDescriptorImpl;
import tv.amwa.maj.model.impl.MaterialPackageImpl;
import tv.amwa.maj.model.impl.NetworkLocatorImpl;
import tv.amwa.maj.model.impl.OperationDefinitionImpl;
import tv.amwa.maj.model.impl.PackageImpl;
import tv.amwa.maj.model.impl.ParameterDefinitionImpl;
import tv.amwa.maj.model.impl.ParameterImpl;
import tv.amwa.maj.model.impl.PhysicalDescriptorImpl;
import tv.amwa.maj.model.impl.PictureDescriptorImpl;
import tv.amwa.maj.model.impl.PluginDefinitionImpl;
import tv.amwa.maj.model.impl.PrefaceImpl;
import tv.amwa.maj.model.impl.PulldownImpl;
import tv.amwa.maj.model.impl.RGBADescriptorImpl;
import tv.amwa.maj.model.impl.RIFFChunkImpl;
import tv.amwa.maj.model.impl.RecordingDescriptorImpl;
import tv.amwa.maj.model.impl.ScopeReferenceImpl;
import tv.amwa.maj.model.impl.SegmentImpl;
import tv.amwa.maj.model.impl.SoundDescriptorImpl;
import tv.amwa.maj.model.impl.SourceClipImpl;
import tv.amwa.maj.model.impl.SourcePackageImpl;
import tv.amwa.maj.model.impl.SourceReferenceSegmentImpl;
import tv.amwa.maj.model.impl.StaticTrackImpl;
import tv.amwa.maj.model.impl.SubDescriptorImpl;
import tv.amwa.maj.model.impl.TIFFDescriptorImpl;
import tv.amwa.maj.model.impl.TaggedValueDefinitionImpl;
import tv.amwa.maj.model.impl.TaggedValueImpl;
import tv.amwa.maj.model.impl.TapeDescriptorImpl;
import tv.amwa.maj.model.impl.TextClipImpl;
import tv.amwa.maj.model.impl.TextLocatorImpl;
import tv.amwa.maj.model.impl.TimecodeSegmentImpl;
import tv.amwa.maj.model.impl.TimecodeStream12MImpl;
import tv.amwa.maj.model.impl.TimecodeStreamImpl;
import tv.amwa.maj.model.impl.TimelineTrackImpl;
import tv.amwa.maj.model.impl.TrackImpl;
import tv.amwa.maj.model.impl.VaryingValueImpl;
import tv.amwa.maj.model.impl.WAVEDescriptorImpl;
import tv.amwa.maj.model.impl.WAVEPCMDescriptorImpl;
import tv.amwa.maj.record.impl.AUIDImpl;

// TODO add support for streams
// TODO investigate why multiple descriptors don't work
// TODO work out what to do about portable objects ... like implement a mix in equivalent

public class JPAGenerator {

	private final static String ORM_NAMESPACE = "http://java.sun.com/xml/ns/persistence/orm";
	// private final static String ORM_PREFIX = "orm";

	/**
	 * <p>Generate an object relational mapping
	 * <a href="http://en.wikipedia.org/wiki/Java_Persistence_API">Java Persistence API 2.0</a>
	 * configuration file (<code>orm.xml</code>) from the given collection of classes and store
	 * it in the given file. Any embeddable objects or other utility class mappings will be
	 * included in the mapping if they are required.</p>
	 *
	 * <p>The mapping file allows the given
	 * {@linkplain MediaEntity media entities} to be persisted to a relational database
	 * using a JPA implementation, such as <a href="http://openjpa.apache.org/">Open JPA</a>
	 * or <a href="https://www.hibernate.org/">Hibernate</a>. By using this method, a developer
	 * only needs to use one set of annotations to turn a class into both:</p>
	 *
	 * <ul>
	 *  <li>a media class that benefits from the facilities of this
	 * media engine;</li>
	 *  <li>a JPA persistent entity, also known as an <em>EJB3 entity bean</em>.</li>
	 * </ul>
	 *
	 * <p>Another benefit of using media annotations rather than JPA annotations is that
	 * this code base can be compiled in Java SE without the need for JPA libraries to
	 * be present.</p>
	 *
	 * @param mediaClassList Collection of classes to create object relational mappings for.
	 * @param fileName Name and path of the object relational mapping file to create.
	 * @return Was an object-relational mapping file generated successfully?
	 *
	 * @throws NullPointerException Cannot generate an object relational mapping if any
	 * of the input values or classes is <code>null</code>.
	 * @throws IllegalArgumentException One or more of the given classes is not
	 * annotated as a media class.
	 */
	public final static boolean generateORM(
			Collection<Class<? extends MediaEntity>> mediaClassList,
			String fileName)
		throws NullPointerException,
			IllegalArgumentException {

		for ( Class<? extends MediaEntity> mediaEntity : mediaClassList )
			if (mediaEntity == null)
				throw new NullPointerException("Cannot generate an object relational mapping when one or more of the classes is a null value.");

		if (fileName == null)
			throw new NullPointerException("The given output filename is null.");

		// Schema name moved to external ... this is part of the system properties and not coupled to the orm file

		DocumentFragment root = XMLBuilder.createDocumentFragment();
		XMLBuilder.appendComment(root, " Automatically generated JPA 2.0 mapping using the MAJAPI JPA Generator - " +
				Forge.now().toString() + " ");

		Element entityMappings = child(root, "entity-mappings");
		attr(entityMappings, "version", "2.0");

		Element persistenceUnitMetadata = child(entityMappings, "persistence-unit-metadata");
		child(persistenceUnitMetadata, "xml-mapping-metadata-complete");
		Element persistenceUnitDefaults = child(persistenceUnitMetadata, "persistence-unit-defaults");
		element(persistenceUnitDefaults, "access", "FIELD");

		XMLBuilder.appendComment(entityMappings, " Generated mappings for all media entities. ");

		List<Class<?>> sortedClasses = new ArrayList<Class<?>>();
		for ( Class<?> mediaClass : mediaClassList ) {
			if (Modifier.isAbstract(mediaClass.getModifiers()))
				sortedClasses.add(0, mediaClass);
			else
				sortedClasses.add(mediaClass);
		}

		for ( Class<?> mediaClass : sortedClasses) {

			ClassDefinition classDefinition = Warehouse.lookForClass(mediaClass);

			XMLBuilder.appendComment(entityMappings, " **************************************************** ");
			XMLBuilder.appendComment(entityMappings, " *** " + classDefinition.getName() + " ");
			XMLBuilder.appendComment(entityMappings, " **************************************************** ");

			Element entity = null;
			if (classDefinition.isConcrete()) {
				entity = child(entityMappings, "entity");
				attr(entity, "name", classDefinition.getName());
				attr(entity, "access", "FIELD");
				attr(entity, "class", classDefinition.getJavaImplementation().getCanonicalName());
				Element table = child(entity, "table");
				attr(table, "name", classDefinition.getName());
				Element inheritance = child(entity, "inheritance");
				attr(inheritance, "strategy", "JOINED");
			}
			else {
				entity = child(entityMappings, "mapped-superclass");
				attr(entity, "access", "FIELD");
				attr(entity, "class", classDefinition.getJavaImplementation().getCanonicalName());
			}

			Element attributes = child(entity, "attributes");

			Set<? extends PropertyDefinition> properties = classDefinition.getPropertyDefinitions();

			// TODO consider whether this should be property access
			if (classDefinition.isRoot()) {
				Element id = child(attributes, "id");
				attr(id, "name", "persistentID");
				Element column = child(id, "column");
				attr(column, "name", "PersistentID");
				child(id, "generated-value");
			}

			List<String> transientList = new ArrayList<String>();
			List<PropertyDefinition> basicList = new ArrayList<PropertyDefinition>();
			List<PropertyDefinition> oneToOneList = new ArrayList<PropertyDefinition>();
			List<PropertyDefinition> oneToManyList = new ArrayList<PropertyDefinition>();
			List<PropertyDefinition> elementCollectionList = new ArrayList<PropertyDefinition>();
			TypeDefinition propertyType = null;

			for ( PropertyDefinition property : properties ) {

				if (property.getAUID().equals(CommonConstants.ObjectClassID)) continue;
				if ((mediaClass.equals(TaggedValueImpl.class)) &&
						(property.getName().equals("PortableObject"))) continue;

				propertyType = property.getTypeDefinition();
				TypeDefinition elementType = null;

				switch (propertyType.getTypeCategory()) {

				case FixedArray:
					elementType = ((TypeDefinitionFixedArray) propertyType).getType();
					switch (elementType.getTypeCategory()) {

					case Record:
						elementCollectionList.add(property);
						break;
					default:
						basicList.add(property);
					}
					break;
				case Set:
					elementType = ((TypeDefinitionSet) propertyType).getElementType();
					switch (elementType.getTypeCategory()) {

					case Record:
						elementCollectionList.add(property);
						break;
					case Int:
						basicList.add(property);
						break;
					case WeakObjRef:
						oneToOneList.add(property);
						break;
					case StrongObjRef:
					default:
						oneToManyList.add(property);
						break;
					}
					break;
				case VariableArray:
					elementType = ((TypeDefinitionVariableArray) propertyType).getType();
					switch (elementType.getTypeCategory()) {

					case Int:
						basicList.add(property);
						break;
					case WeakObjRef:
						oneToOneList.add(property);
						break;
					case StrongObjRef:
					default:
						oneToManyList.add(property);
						break;
					}
					break;
				case WeakObjRef:
				case StrongObjRef:
					oneToOneList.add(property);
					break;
				case Opaque:
				case Indirect:
				case ExtEnum:
				case Record:
					basicList.add(property);
					transientList.add(lowerFirstLetter(property.getName()));
					break;
				case Stream:
					transientList.add(lowerFirstLetter(property.getName()));
					break;
				default:
					basicList.add(property);
					break;
				}
			}

			for ( PropertyDefinition property : basicList ) {

				propertyType = property.getTypeDefinition();

				switch (propertyType.getTypeCategory()) {

				case String: {
					Element basic = child(attributes, "basic");
					if ((mediaClass.equals(NetworkLocatorImpl.class)) ||
							(mediaClass.equals(TextLocatorImpl.class))) {
						attr(basic, "name", lowerFirstLetter(property.getName()) + "Persist");
						attr(basic, "access", "PROPERTY");
					}
					else {
						attr(basic, "name", lowerFirstLetter(property.getName()));
					}
					Element column = child(basic, "column");
					attr(column, "name", property.getName());
					attr(column, "column-definition", "TEXT CHARACTER SET utf8 COLLATE utf8_general_ci");
					child(basic, "lob");
					break;
				}
				case Enum: {
					Element basic = child(attributes, "basic");
					attr(basic, "name", lowerFirstLetter(property.getName()));
					Element column = child(basic, "column");
					attr(column, "name", property.getName());
					if (!propertyType.getName().equals("Boolean"))
						element(basic, "enumerated", "STRING");
					break;
					}
				case ExtEnum: {
					Element basic = child(attributes, "basic");
					attr(basic, "name", lowerFirstLetter(property.getName()) + "String");
					attr(basic, "access", "PROPERTY");
					Element column = child(basic, "column");
					attr(column, "name", property.getName());
					attr(column, "column-definition", AUIDImpl.MYSQL_COLUMN_DEFINITION);
					break;
					}
				case Record: {
					TypeDefinitionRecord recordType = (TypeDefinitionRecord) propertyType;

					Element basic = child(attributes, "basic");
					if (( (mediaClass.equals(PluginDefinitionImpl.class)) &&
							(property.getName().equals("PluginVersion"))) ||
						  ((mediaClass.equals(IdentificationImpl.class)) &&
						    (property.getName().equals("ApplicationVersion")) ) )
						attr(basic, "name", lowerFirstLetter(property.getName()) + "Persist");
					else
						attr(basic, "name", lowerFirstLetter(property.getName()) + "String");
					attr(basic, "access", "PROPERTY");
					Element column = child(basic, "column");
					attr(column, "name", property.getName());
					try {
						String columnDefinition =
							(String) recordType.getImplementation().getField("MYSQL_COLUMN_DEFINITION").get(null);
						if (columnDefinition != null)
							attr(column, "column-definition", columnDefinition);
					}
					catch (Exception e) { }
					break;
					}
				case Opaque:
				case Indirect: {
					Element basic = child(attributes, "basic");
					attr(basic, "name", lowerFirstLetter(property.getName()) + "Persist");
					attr(basic, "access", "PROPERTY");
					Element column = child(basic, "column");
					attr(column, "name", property.getName());
					child(basic, "lob");

					if (mediaClass.equals(TaggedValueImpl.class)) {
						transientList.remove("indirectValue");
						transientList.add("value");
						transientList.add("typeDefinition");
					}
					if (mediaClass.equals(KLVDataImpl.class)) {
						transientList.remove("kLVDataValue");
						transientList.add("klvDataValue");
					}
					break;
				}
				default: {
					Element basic = child(attributes, "basic");
					attr(basic, "name", lowerFirstLetter(property.getName()));
					Element column = child(basic, "column");
					attr(column, "name", property.getName());
					break;
					}
				}
			}

			if (mediaClass.equals(ComponentImpl.class)) {
				Element basic = child(attributes, "basic");
				attr(basic, "name", "lengthPresent");
				Element column = child(basic, "column");
				attr(column, "name", "LengthPresent");
			}

			if (mediaClass.equals(InterchangeObjectImpl.class)) {
				Element basic = child(attributes, "basic");
				attr(basic, "name", "generationTracking");
				Element column = child(basic, "column");
				attr(column, "name", "GenerationTracking");
			}

			for ( PropertyDefinition property : oneToManyList ) {

				propertyType = property.getTypeDefinition();

				switch (propertyType.getTypeCategory()) {

				case VariableArray: {
					TypeDefinitionVariableArray arrayType = (TypeDefinitionVariableArray) propertyType;
					TypeDefinition elementType = arrayType.getType();

					switch (elementType.getTypeCategory()) {

					case StrongObjRef: {
						Element oneToMany = child(attributes, "one-to-many");
						attr(oneToMany, "name", lowerFirstLetter(property.getName()));
						attr(oneToMany, "target-entity",
								((TypeDefinitionStrongObjectReference) elementType)
										.getObjectType().getJavaImplementation().getCanonicalName());
						Element orderColumn = child(oneToMany, "order-column");
						attr(orderColumn, "name", "PersistentIndex");
						Element joinColumn = child(oneToMany, "join-column");
						attr(joinColumn, "name", property.getName());
    					Element cascade = child(oneToMany, "cascade");
    					child(cascade, "cascade-all");
						break;
					}
					default:
						System.err.println("Not handling a variable array of type : " + propertyType.getName());
						break;
					}
					break;
					}
				case Set: {
					TypeDefinitionSet setType = (TypeDefinitionSet) propertyType;
					TypeDefinition elementType = setType.getElementType();

					switch (elementType.getTypeCategory()) {

					case StrongObjRef: {
						Element oneToMany = child(attributes, "one-to-many");
						attr(oneToMany, "name", lowerFirstLetter(property.getName()));
						attr(oneToMany, "target-entity",
								((TypeDefinitionStrongObjectReference) elementType)
										.getObjectType().getJavaImplementation().getCanonicalName());
						Element joinColumn = child(oneToMany, "join-column");
						attr(joinColumn, "name", property.getName());
    					Element cascade = child(oneToMany, "cascade");
    					child(cascade, "cascade-all");
						break;
					}
					default:

						System.err.println("Not handling a set of type : " + propertyType.getName());
						break;

					}
					break;
				}
				case FixedArray:
					System.err.println("Not handling a fixed array type : " + propertyType.getName());
					break;

				default:
					System.err.println("Not handling a one-to-many for type : " + propertyType.getName());
					break;
				}
			}

			for ( PropertyDefinition property : oneToOneList ) {

				propertyType = property.getTypeDefinition();

				switch (propertyType.getTypeCategory()) {

				case StrongObjRef: {
					Element oneToOne = child(attributes, "one-to-one");
					attr(oneToOne, "name", lowerFirstLetter(property.getName()));
					attr(oneToOne, "target-entity",
							((TypeDefinitionStrongObjectReference) propertyType)
									.getObjectType().getJavaImplementation().getCanonicalName());
					Element joinColumn = child(oneToOne, "join-column");
					attr(joinColumn, "name", property.getName());
					Element cascade = child(oneToOne, "cascade");
					child(cascade, "cascade-all");
					break;
					}
				case WeakObjRef: {
					Element oneToOne = child(attributes, "one-to-one");
					attr(oneToOne, "name", lowerFirstLetter(property.getName()));
					attr(oneToOne, "target-entity", WeakReference.class.getCanonicalName());
					Element joinColumn = child(oneToOne, "join-column");
					attr(joinColumn, "name", property.getName());
					Element cascade = child(oneToOne, "cascade");
					child(cascade, "cascade-all");
					break;
					}
				case Set: {
					// Assume this must be a WeakReferenceSet
					Element oneToOne = child(attributes, "one-to-one");
					attr(oneToOne, "name", lowerFirstLetter(property.getName()));
					attr(oneToOne, "target-entity", WeakReferenceSet.class.getCanonicalName());
					Element joinColumn = child(oneToOne, "join-column");
					attr(joinColumn, "name", property.getName());
					Element cascade = child(oneToOne, "cascade");
					child(cascade, "cascade-all");
					break;
					}
				case VariableArray: {
					// Assume this must be a WeakReferenceVector
					Element oneToOne = child(attributes, "one-to-one");
					attr(oneToOne, "name", lowerFirstLetter(property.getName()));
					attr(oneToOne, "target-entity", WeakReferenceVector.class.getCanonicalName());
					Element joinColumn = child(oneToOne, "join-column");
					attr(joinColumn, "name", property.getName());
					Element cascade = child(oneToOne, "cascade");
					child(cascade, "cascade-all");
					break;
					}
				default:
					System.err.println("Not handling a one-to-one for type " + propertyType.getName());
					break;
				}
			}

			for ( PropertyDefinition property : elementCollectionList ) {

				propertyType = property.getTypeDefinition();

				switch (propertyType.getTypeCategory()) {

				case FixedArray: {
					TypeDefinitionFixedArray fixedArrayType = (TypeDefinitionFixedArray) propertyType;
					TypeDefinition elementType = fixedArrayType.getType();

					switch (elementType.getTypeCategory()) {

					case Record: {
						Element elementCollection = child(attributes, "element-collection");
						attr(elementCollection, "access", "PROPERTY");
						attr(elementCollection, "name", lowerFirstLetter(property.getName()) + "StringList");
				        //<order-column name="ArrayIndex"/>
				        Element orderColumn = child(elementCollection, "order-column");
				        attr(orderColumn, "name", "ArrayIndex");
						//<column name="PixelLayout"/>
				        Element column = child(elementCollection, "column");
				        attr(column, "name", property.getName());
				        //<collection-table name="PixelLayout"/>
				        Element collectionTable = child(elementCollection, "collection-table");
				        attr(collectionTable, "name", property.getName());
				        break;
						}
					default:
						System.err.println("Not handling element collection fixed array for type : " + propertyType.getName());
						break;
					}
					break;
					}
				case Set: {
					TypeDefinitionSet setType = (TypeDefinitionSet) propertyType;
					TypeDefinition elementType = setType.getElementType();

					switch (elementType.getTypeCategory()) {

					case Record: {
						Element elementCollection = child(attributes, "element-collection");
						attr(elementCollection, "access", "PROPERTY");
						attr(elementCollection, "name", lowerFirstLetter(property.getName()) + "StringSet");
						//<column name="PixelLayout"/>
				        Element column = child(elementCollection, "column");
				        attr(column, "name", property.getName());
				        //<collection-table name="PixelLayout"/>
				        Element collectionTable = child(elementCollection, "collection-table");
				        attr(collectionTable, "name", property.getName());
				        break;
						}
					}
					break;
					}
				default:
					System.err.println("Not handling element collection for type : " + propertyType.getName());
					break;
				}
			}

			if (mediaClass.equals(InterchangeObjectImpl.class)) {
				transientList.add("persistentIndex");
			}

			for ( String transientItem : transientList) {
				Element transientElement = child(attributes, "transient");
				attr(transientElement, "name", transientItem);
			}
		}

		// Add weak reference support
		XMLBuilder.appendComment(entityMappings, " Entities used internally by MAJ ");
		Element weakReference = child(entityMappings, "entity");
		attr(weakReference, "name", "WeakReference");
		attr(weakReference, "access", "FIELD");
		attr(weakReference, "class", WeakReference.class.getCanonicalName());
		Element weakReferenceTable = child(weakReference, "table");
		attr(weakReferenceTable, "name", "WeakReference");

		Element weakReferenceAttributes = child(weakReference, "attributes");

		Element weakReferenceId = child(weakReferenceAttributes, "id");
		attr(weakReferenceId, "name", "persistentID");
		Element weakReferenceIdColumn = child(weakReferenceId, "column");
		attr(weakReferenceIdColumn, "name", "PersistentID");
		child(weakReferenceId, "generated-value");

		Element weakReferenceTypeName = child(weakReferenceAttributes, "basic");
		attr(weakReferenceTypeName, "name", "canonicalTypeName");
		attr(weakReferenceTypeName, "fetch", "EAGER");
		Element weakReferenceTypeNameColumn = child(weakReferenceTypeName, "column");
		attr(weakReferenceTypeNameColumn, "name", "TypeName");
		attr(weakReferenceTypeNameColumn, "nullable", "false");

		Element weakReferenceIndex = child(weakReferenceAttributes, "basic");
		attr(weakReferenceIndex, "name", "persistentIndex");
		Element weakReferenceIndexColumn = child(weakReferenceIndex, "column");
		attr(weakReferenceIndexColumn, "name", "PersistentIndex");
		attr(weakReferenceIndexColumn, "nullable", "false");

		Element weakReferenceIdent = child(weakReferenceAttributes, "basic");
		attr(weakReferenceIdent, "name", "identifierString");
		attr(weakReferenceIdent, "access", "PROPERTY");
		attr(weakReferenceIdent, "fetch", "EAGER");
		Element weakReferenceIdentColumn = child(weakReferenceIdent, "column");
		attr(weakReferenceIdentColumn, "name", "Identifier");
		attr(weakReferenceIdentColumn, "column-definition", "CHAR(36) CHARACTER SET ascii COLLATE ascii_general_ci");

		Element weakReferenceCachedValue = child(weakReferenceAttributes, "transient");
		attr(weakReferenceCachedValue, "name", "cachedValue");

		Element weakReferenceIdentifier = child(weakReferenceAttributes, "transient");
		attr(weakReferenceIdentifier, "name", "identifier");

		// WeakReferenceVector

		Element weakReferenceVector = child(entityMappings, "entity");
		attr(weakReferenceVector, "access", "FIELD");
		attr(weakReferenceVector, "class", WeakReferenceVector.class.getCanonicalName());
		attr(weakReferenceVector, "name", "WeakReferenceVector");

		Element weakReferenceVectorTable = child(weakReferenceVector, "table");
		attr(weakReferenceVectorTable, "name", "WeakReferenceVector");

		Element weakReferenceVectorAttributes = child(weakReferenceVector, "attributes");

		Element weakReferenceVectorID = child(weakReferenceVectorAttributes, "id");
		attr(weakReferenceVectorID, "name", "persistentID");
		Element weakReferenceVectorIDColumn = child(weakReferenceVectorID, "column");
		attr(weakReferenceVectorIDColumn, "name", "PersistentID");
		child(weakReferenceVectorID, "generated-value");

		Element weakReferenceVectorVector = child(weakReferenceVectorAttributes, "one-to-many");
		attr(weakReferenceVectorVector, "name", "vector");
		attr(weakReferenceVectorVector, "target-entity", WeakReference.class.getCanonicalName());

		Element weakReferenceVectorOrder = child(weakReferenceVectorVector, "order-column");
		attr(weakReferenceVectorOrder, "name", "PersistentIndex");
		Element weakReferenceVectorJoin = child(weakReferenceVectorVector, "join-column");
		attr(weakReferenceVectorJoin, "name", "VectorElements");
		Element weakReferenceVectorCascade = child(weakReferenceVectorVector, "cascade");
		child(weakReferenceVectorCascade, "cascade-all");

		// WeakReferenceSet

		Element weakReferenceSet = child(entityMappings, "entity");
		attr(weakReferenceSet, "access", "FIELD");
		attr(weakReferenceSet, "class", WeakReferenceSet.class.getCanonicalName());
		attr(weakReferenceSet, "name", "WeakReferenceSet");

		Element weakReferenceSetTable = child(weakReferenceSet, "table");
		attr(weakReferenceSetTable, "name", "WeakReferenceSet");

		Element weakReferenceSetAttributes = child(weakReferenceSet, "attributes");

		Element weakReferenceSetID = child(weakReferenceSetAttributes, "id");
		attr(weakReferenceSetID, "name", "persistentID");
		Element weakReferenceSetIDColumn = child(weakReferenceSetID, "column");
		attr(weakReferenceSetIDColumn, "name", "PersistentID");
		child(weakReferenceSetID, "generated-value");

		Element weakReferenceSetSet = child(weakReferenceSetAttributes, "one-to-many");
		attr(weakReferenceSetSet, "name", "set");
		attr(weakReferenceSetSet, "target-entity", WeakReference.class.getCanonicalName());

		Element weakReferenceSetJoin = child(weakReferenceSetSet, "join-column");
		attr(weakReferenceSetJoin, "name", "SetElements");
		Element weakReferenceSetCascade = child(weakReferenceSetSet, "cascade");
		child(weakReferenceSetCascade, "cascade-all");

		FileWriter writer = null;
		try{
			writer = new FileWriter(fileName);
			writer.append(XMLBuilder.transformNodeToString(root));
			writer.flush();
		}
		catch (IOException ioe) {
			return false;
		}
		finally {
			try { writer.close(); } catch (Exception e) { }
		}

		return true;
	}

	private final static Element child(
			Node element,
			String name) {

		return XMLBuilder.createChild(element, ORM_NAMESPACE, null, name);
	}

	private final static void attr(
			Element element,
			String attributeName,
			String attributeValue) {

		XMLBuilder.setAttribute(element, ORM_NAMESPACE, null, attributeName, attributeValue);
	}

	private final static void element(
			Element parent,
			String elementName,
			String elementValue) {

		XMLBuilder.appendElement(parent, ORM_NAMESPACE, null, elementName, elementValue);
	}

	protected static final String lowerFirstLetter(
			String changeMe) {

		if (Character.isUpperCase(changeMe.charAt(0))) {
				StringBuffer replacement = new StringBuffer(changeMe);
				replacement.setCharAt(0, Character.toLowerCase(changeMe.charAt(0)));
				return replacement.toString();
		}

		return changeMe;
	}

	/** <p>List of concrete AAF classes that are interchangeable as they extend
	 * {@link InterchangeObject}.</p> */
	public final static Class<?>[] interchangeable = new Class<?>[] {
//		TransitionImpl.class,
		EdgeCodeSegmentImpl.class,
//		EssenceGroupImpl.class,
		CommentMarkerImpl.class,
		GPITriggerImpl.class,
		DescriptiveMarkerImpl.class,
		FillerImpl.class,
//		NestedScopeImpl.class,
//		OperationGroupImpl.class,
		PulldownImpl.class,
		ScopeReferenceImpl.class,
//		SelectorImpl.class,
//		SequenceImpl.class,
		SourceClipImpl.class,
		DescriptiveClipImpl.class,
		HTMLClipImpl.class,
		TimecodeSegmentImpl.class,
		TimecodeStream12MImpl.class,
//		ContentStorageImpl.class,
		ControlPointImpl.class,
		CodecDefinitionImpl.class,
		ContainerDefinitionImpl.class,
		DataDefinitionImpl.class,
		InterpolationDefinitionImpl.class,
		KLVDataDefinitionImpl.class,
		OperationDefinitionImpl.class,
		ParameterDefinitionImpl.class,
		PluginDefinitionImpl.class,
		TaggedValueDefinitionImpl.class,
		DictionaryImpl.class,
		EssenceDataImpl.class,
		AIFCDescriptorImpl.class,
		DataEssenceDescriptorImpl.class,
		CDCIDescriptorImpl.class,
		MPEGVideoDescriptorImpl.class,
		RGBADescriptorImpl.class,
		HTMLDescriptorImpl.class,
//		MultipleDescriptorImpl.class,
		SoundDescriptorImpl.class,
		WAVEPCMDescriptorImpl.class,
		AES3PCMDescriptorImpl.class,
		TIFFDescriptorImpl.class,
		WAVEDescriptorImpl.class,
		FilmDescriptorImpl.class,
		AuxiliaryDescriptorImpl.class,
		ImportDescriptorImpl.class,
		BWFImportDescriptorImpl.class,
		RecordingDescriptorImpl.class,
		TapeDescriptorImpl.class,
		PrefaceImpl.class,
		IdentificationImpl.class,
		KLVDataImpl.class,
		NetworkLocatorImpl.class,
		TextLocatorImpl.class,
		CompositionPackageImpl.class,
		MaterialPackageImpl.class,
		SourcePackageImpl.class,
		EventTrackImpl.class,
		StaticTrackImpl.class,
		TimelineTrackImpl.class,
		ConstantValueImpl.class,
		VaryingValueImpl.class,
		RIFFChunkImpl.class,
		TaggedValueImpl.class
	};

	/**
	 * <p>List of abstract AAF classes that are part of the interchangeable
	 * object hierarchy, i.e. abstract and extending {@link InterchangeObject}.</p>
	 */
	public final static Class<?>[] abstractInterchangeable = new Class<?>[] {
		InterchangeObjectImpl.class,
		ComponentImpl.class,
		SegmentImpl.class,
		EventImpl.class,
		SourceReferenceSegmentImpl.class,
		TextClipImpl.class,
		TimecodeStreamImpl.class,
		DefinitionObjectImpl.class,
		DescriptiveFrameworkImpl.class,
		DescriptiveObjectImpl.class,
		EssenceDescriptorImpl.class,
		AAFFileDescriptorImpl.class,
		PictureDescriptorImpl.class,
		PhysicalDescriptorImpl.class,
		LocatorImpl.class,
		PackageImpl.class,
		TrackImpl.class,
		ParameterImpl.class,
		SubDescriptorImpl.class
	};

	@SuppressWarnings("unchecked")
	public final static void main(
			String args[])
		throws Exception {

		MediaEngine.initializeAAF();
		AvidFactory.registerAvidExtensions();

		List<Class<? extends MediaEntity>> mediaClassList =
			new ArrayList<Class<? extends MediaEntity>>();
		for (Class<?> interchange : interchangeable )
			mediaClassList.add((Class<? extends MediaEntity>) interchange);
		for (Class<?> abstractInterchange : abstractInterchangeable )
			mediaClassList.add((Class<? extends MediaEntity>) abstractInterchange);

		long startTime = System.currentTimeMillis();
		generateORM(mediaClassList, "/Users/vizigoth2/tools/apache-openjpa-2.0.1/examples/META-INF/orm.xml");
		long endTime = System.currentTimeMillis();
		System.out.println("JPA mapping file generated in " + (endTime - startTime) + ".");
	}

}
