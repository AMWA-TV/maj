# Media Authoring with Java API (MAJ)

The MAJ API (pronounced *madge*) is a pure Java API for creating, reading, manipulating and writing MXF (SMPTE ST 377), [AAF](http://www.amwa.tv/projects/MS-01.shtml) structured storage and Reg-XML (SMPTE ST 2001) files. MXF files are commonly used as a container for professional media file formats and AAF is supported by a number of professional video editing packages. MXF and Reg-XML are used as part of the [Interoperable Mastering Format](http://www.imfforum.com/IMF_Forum/index.html) suite of specifications.

As well as supporting the published metadata dictionaries for AAF and MXF, MAJ includes mechanisms that support extension namespaces and the auto-generation of Java code from Reg-XML meta-dictionaries.

This is a stable version of the API that is in use in production environments. Contributions to improve the API are welcome, particularly to ensure that the code remains up-to-date with evolving specifications and metadata dictionaries.

## Building MAJ

The MAJ API is built from source using [Apache Maven](http://maven.apache.org/). The output of the build process is a jar file `maj-1.0.0.jar` that can be installed locally or placed on the classpath of other projects.

To build the jar:

    mvn package

The jar file is created in the `target` folder. You may wish to install MAJ locally with:

    mvn install

To rebuild the [Javadoc API documentation](./apidocs/):

    mvn javadoc:javadoc

HTML documentation is built in the `apidocs` folder.

## Using MAJ

The MAJ API has a number of different starting points, depending on how you intend to use it. These include:

* [Working with AAF data with the provided data model](#manipulating-aaf-data).
* [Reading data from or writing to an MXF file](#mxf-files).
* [Reading data from or writing to an AAF file](#aaf-files).
* [Reading and writing Reg-XML documents and extensions](#reg-xml-files).
* [Media-specialist datatypes, such as timecode](#media-specialist-data-types).
* [Dealing with extension metadata](#dealing-with-extension-metadata).

These topics are covered below.

### Manipulating AAF data

An application can be written using the AAF data model from scratch without the need to read or write files. One difference between MAJ and the AAF SDK is that you can write code that uses classes of the AAF model without the need to contain them within a virtual file at runtime. For more details, see the documentation of the [industry package](./apidocs/tv/amwa/maj/industry/package-summary.html).

The starting point is to initialize the local Java virtual machine so that it supports processing the AAF data model with [`MediaEngine.initializeAAF()`](./src/main/java/tv/amwa/maj/industry/MediaEngine.java#L169). You can then start creating objects of the AAF data model, including *packages*, *tracks*, *sequences* and *source clips*, using the `make...` *forge*, for example:

```java
Forge.make(Class, Object...)
```

Every class in MAJ provides a registered XML representation as its `toString()` output, which in turn is created by `MediaEngine.toString()`. This makes debugging fairly easy as you can query a value in the debugger and see a human-readable XML format.

To help you get started, here is an [`AMWADemoClass`](./src/main/java/tv/amwa/maj/example/AMWADemoClass.java) code example:

```java
package tv.amwa.maj.example;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.model.*;

public class AMWADemoClass
  implements tv.amwa.maj.constant.CommonConstants {

  public static void main(String[] args) throws Exception {

    MediaEngine.initializeAAF(); // Required to initialize AAF specified classes

    MaterialPackage amwaPackage = Forge.makeByName(
      AAF_XML_NAMESPACE, "MaterialPackage",
      "PackageID", Forge.randomUMID(), // Randomly generated
      "Name", "AMWADemoPackage",
      "PackageLastModified", Forge.now(),
      "CreationTime", Forge.now());

    Sequence amwaVideoSequence = Forge.makeByName(
      AAF_XML_NAMESPACE, "Sequence",
      "ComponentDataDefinition", "Picture");

    amwaVideoSequence.appendComponentObject(
      Forge.make(
        SourceClip.class,
        "ComponentDataDefinition", "Picture",
        "ComponentLength", 60l,
        "SourcePackageID", "urn:smpte:umid:060c2b34.02051101.01001000.13000000.11ee08d4.040311d4.8e3d0090.27dfca7c",
        "SourceTrackID", 1,
        "StartPosition", 10l));

    TimelineTrack amwaVideoTrack = Forge.make(
      TimelineTrack.class,
      "TrackID", 1,
      "TrackSegment", amwaVideoSequence,
      "EditRate", "25/1",
      "Origin", 0l);

    amwaVideoTrack.setTrackName("AMWA VIDEO TRACK");

    amwaPackage.appendPackageTrack(amwaVideoTrack);
    amwaPackage.appendPackageUserComment("company", "portability 4 media");

    System.out.println(amwaPackage.toString());
  }
}
```

For a more complex example, see the source for the [composition example](./src/main/java/tv/amwa/maj/example/CompositionExample.java).

### MXF files

Material eXchange Format (MXF) files, also known as AAF-KLV files, consist of a sequence of partitions. Partitions contain a partition header and may contain metadata, index tables and/or essence data. Support for reading and writing MXF files is provided in package [`tv.amwa.maj.io.mxf`](./src/main/java/tv/amwa/maj/io/mxf).

To register all MXF data types with MAJ, start by calling [`MXFBuilder.registerMXF()`](./src/main/java/tv/amwa/maj/io/mxf/MXFBuilder.java#L101). Alternatively, use static methods in the [`MXFFactory`](./src/main/java/tv/amwa/maj/io/mxf/MXFFactory.java) class that call this method for you.

MXF files can be processed as [complete static entities](#static) (files) or as [streams](#streaming), as described in the following sections.

#### Static

MXF files contain one or more partitions. The first step in reading an MXF file is to build an in memory cache of the structure of those partitions. To do this:

```java
import tv.amwa.maj.industry.MediaEngine;
import tv.amwa.maj.io.mxf.MXFFactory;
import tv.amwa.maj.io.mxf.MXFFile;

...

  MXFFile mxfFile = MXFFactory.readPartitions("filename.mxf");
```

All MXF files contain a *header partition*. Most also contain a *footer partition*. To access these:

```java
import tv.amwa.maj.io.mxf.HeaderPartition;
import tv.amwa.maj.io.mxf.FooterPartition;

...

  HeaderPartition header = mxfFile.getHeaderPartition();
  FooterPartition footer = mxfFile.getFooterPartition();
```

Partitions can contain header metadata and this is split into a primer pack and a preface. The metadata can be read into memory from file using the `readHeaderMetadata()` method.

If a footer partition is present in an MXF file and it contains header metadata, this version is often the best source for metadata about the file as it was written once the rest of the file is complete. If the footer partition is not present or does not contain header metadata, read the header partition's header metadata.

```java
import tv.amwa.maj.model.Preface;
import tv.amwa.maj.io.mxf.HeaderMetadata;

...

  HeaderMetadata headerMD = null;
  if ((footer != null) && (footer.hasHeaderMetadata()))
    headerMD = footer.readHeaderMetadata();
  else
    headerMD = header.readHeaderMetadata();

  Preface preface = headerMD.getPreface();

```

Methods from the preface interface can be used to interrogate what is in the MXF file, or you can call `toString()` on the preface to get an XML representation.

Due to the wide variety of different styles of MXF file, writing MXF files is best achieved using the streaming API.

#### Streaming

MXF files can be very large, especially when the contain video data. The best approach for efficiently reading and writing MXF files is to use the streaming API. This requires some knowledge of the structure of both an MXF file - the expected order of partitions and local sets - and the nature of the container in use - for example an interleaved MXF OP1a file containing video and audio vs a mono-essence OP-Atom file with only audio.

The streaming API takes the form a static methods in class [`tv.amwa.maj.io.mxf.MXFStream`](./src/main/java/tv/amwa/maj/io/mxf/MXFStream.java). The methods provide a means to read one or more KLV (key-length-value) item(s) as *MXF units* from a `java.io.InputStream` and write the same structures back to a `java.io.OutputStream`. The MXF units are:

* partition packs (open, closed, complete, incomplete);
* header metadata, from header or footer partitions;
* essence elements (content package, generic container);
* index table segments;
* random index packs.

To read the next MXF unit in the stream, call `MXFStream.readNextUnit(*stream*, *sizeLimit*)`, where *stream* is a Java input stream to read from and *sizeLimit* is the maximum number of bytes to read before finding the next key. Introspect the type of the `MXFUnit` value returned using `instanceof` methods or Java reflection APIs.

Reading through all the units in an MXF stream or file in a linear fashion using `readNextUnit()` is a valid and efficient strategy to dump or play an MXF file. For other use cases, such as to read a single frame of video, partial access to a clip or to extract specific items of metadata from a random access MXF file, the streaming API offers directed access to specific types. For example. here is an example of a strategy for extracting a single frame (`desiredFrame`) from a closed complete MXF file:

1. Read the random index pack with `MXFStream.readRandomIndexPack(*stream*, *size*)`, where stream is an input      stream and *size* is the length of the stream. The stream is closed by this operation.
  ```java
    RandomIndexPack rip = MXFStream.readRandomIndexPack(stream, streamLength);
  ```

2. Create a new input stream looking at the same data. Use the partition offsets to read index table segments and partition packs (for header body offset properties and pack sizes) from the stream, skipping over essence and metadata, until the index offset of the required frame is found.
  ```java
    List<PartitionPack> packs = new List<PartitionPack>();
    List<IndexTableSegment> index = new List<IndexTableSegment>();
    long readBytes = 0;
    for ( RandomIndexItem item : rip.getPartitionIndex() ) {
      MXFStream.skipForwards(stream, item.getByteOffset() - readBytes);
      PartitionPack pack = MXFStream.readPartitionPack(stream);
      packs.add(pack);
      readBytes += 20 + pack.getEncodedSize();
      if (pack.getIndexSID() > 0) { // Assuming no mixed header and index partitions
        IndexTableSegment segment = MXFStream.readIndexTableSegment(stream);
        index.add(segment);
        readBytes += 20 + segment.getEncodedSize();
        if (segment.getIndexStartPosition() > desiredFrame) break;
      }    
    }
  ```

3. Use the index table segments and body offsets properties to locate the required frame as a total byte offset in the file, jump to that offset and read the essence element.
  ```java
    IndexTableSegment indexOfFrame;
    for ( IndexTableSegment its : index ) {
      if ((desiredFrame >= its.getIndexStartPosition()) &&
          (desiredFrame < its.getIndexStartPosition() + its.getIndexDuration())) {
        indexOfFrame = its; break;  
      }
    }
    int desiredIndex = desiredFrame - indexOfFrame.getIndexStartPosition();
    long bodyByteOffset = indexOfFrame.getIndexEntryArray()[desiredIndex];
    PartitionPack framePack;
    for ( PartitionPack pp : packs ) {
      if (pp.getBodyOffset() > bodyByteOffset) break;
      framePack = pp;
    }
    MXFStream.skipForward(stream, framePack.getThisPartition() + 20 +
        framePack.getEncodedSize() +
        (bodyByteOffset - framePack.getBodyOffset())); // Assumes reset stream
    EssenceElement frameData = MXFStream.readEssenceElement(stream);
  ```

Writing MXF data to a file uses the write methods of the `MXFStream` class with `java.io.OutputStream`. The user of the API has to work out the relative sizes of partitions and set partition pack metadata sizes etc.. For example:

```java
  PartitionPack essencePack = Forge.make(HeaderOpenIncompletePartitionPack.class);  
  essencePack.setFooterPartition(0l);
  essencePack.setThisPartition(0l);
  essencePack.setPreviousPartition(0l);
	essencePack.setKagSize(1);
	essencePack.setMajorVersion((short) 1);
	essencePack.setMinorVersion((short) 3);
	essencePack.setOperationalPattern(essenceComponentPreface.getOperationalPattern());
	essencePack.addEssenceContainer(fileDescriptor.getContainerFormat().getAUID());
  long essenceBlockSize = bufferSize < 65536 ? 65546 : bufferSize; // Always at least one block, allowing 64k
	ByteArrayOutputStream essenceHeaderBytes = new ByteArrayOutputStream(essenceBlockSize);
	essencePack.setHeaderByteCount(essenceBlockSize - essencePack.getEncodedSize - 20);
	MXFStream.writePartitionPack(essenceHeaderBytes, essencePack);
	MXFStream.writeHeaderMetadata(essenceHeaderBytes, essenceComponentPreface);
  MXFStream.writeFill(essenceHeaderBytes, essenceBlockSize - essenceHeaderBytes.size());
```

When writing header metadata, MAJ will take care of writing primer packs and making appropriate stream-local reference between classes.

### AAF files

Advanced Authoring Format files, also known as AAF-SS or AAF structured storage files, store AAF structured data in a Microsoft structured storage container. To read and write these files, MAJ uses the [Apache POI library](http://poi.apache.org/download.html).

Support for reading and writing AAF files is provided in package [`tv.amwa.maj.io.aaf`](./src/main/java/tv/amwa/maj/io/aaf). MAJ provides a helper class [`AAFFactory`](./src/main/java/tv/amwa/maj/io/aaf/AAFFactory.java) as a basis for reading and writing AAF files. To read a preface from an AAF file, such as those generated by Avid, use the `readPreface()` method. For example:

```java
import tv.amwa.maj.io.aaf.AAFFactory;
import tv.amwa.maj.iface.Preface;
import tv.amwa.maj.extensions.avid.AvidFactory;
...

AvidFactory.registerAvidExtensions(); // refisters AAF and common Avid extensions
Preface fromAAF = AAFFactory.readPreface("filename.aaf");
```

Some warning messages will be printed if extensions are unknown. These can be ignored unless the extension data is important to your application.

MAJ supports writing metadata-only AAF files, files that do not contain any essence data. AAF is commonly used as a metadata-only representation so this limitation means MAJ is still useful for most use cases. To write an existing preface to an AAF file, make sure AAF and suitable extensions are registered (as for reading) and use the `writePreface()` method.

```java
import tv.amwa.maj.io.AAFFactory;
import tv.amwa.maj.iface.Preface;
...

Preface prefaceToWrite = ...;
AAFFactory.writePreface(prefaceToWrite, "filename.aaf");
```

MAJ will create a dynamic meta dictionary and, if the preface does not contain a valid dictionary already, add in all the required definitions to make the file valid.

### Reg-XML files

AAF XML files are also known as *Registered Data XML* (Reg-XML) files (SMPTE standards ST 2001-1 and ST 2001-2). MAJ uses this format for the return value of `toString()` methods almost everywhere, so it is easy to get to learn this format by just working with MAJ. When you use a debugger and hover over a variable that is a MAJ type, you will see the same XML format. Useful for RESTful and web service interfaces, Reg-XML provides an easier to analyse and process representation of AAF/MXF data stored in an AAF-based repository.

Support for reading and writing XML files is provided in package [`tv.amwa.maj.io.xml`](./src/main/java/tv/amwa/maj/io/xml). MAJ provides a helper class [`XMLBuilder`](./src/main/java/tv/amwa/maj/io/xml/XMLBuilder.java) as a starting point with static methods for reading and writing AAF fragments to and from XML. To convert a single object and any of its contained strong referenced objects to XML, use `toXML()` methods.

```java
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.iface.MaterialPackage;
...

MaterialPackage material = ...;
String packageAsXML = XMLBuilder.toXML(material);
```

Any objects of classes that implement the [`XMLSerializable`](./src/main/java/tv/amwa/maj/io/xml/XMLSerializable.java) or [`MetadataObject`](./src/main/java/tv/amwa/maj/industry/MetadataObject.java) interfaces can be serialized to XML fragments.

To read the XML representation of an object in XML and create an instance in memory, use either the `createFromXML()` or `createFromXMLString()` methods.

```java
import tv.amwa.maj.io.xml.XMLBuilder;
import tv.amwa.maj.iface.MaterialPackage;
...

MaterialPackage material =
        (MaterialPackage) XMLBulder.createFromXMLString(packageAsXML);

```

Complete XML files have a root `&lt;AAF&gt;` root element. To read a preface from an XML file, register all the required data types (e.g. with `MediaEngine.initializeAAF()`) and then use the `readPreface()` method.

```java
import tv.amwa.maj.io.xml.XMLFactory;
import tv.amwa.maj.model.Preface;
....

Preface preface = XMLFactory.readPreface("input_file.xml");
```

Catch IO exceptions (`java.io.IOException`) to find out about any problems parsing the XML.

To write a complete Reg-XML file, use the `writePreface()` static method.

```java
import tv.amwa.maj.io.xml.XMLFactory;
....

XMLFactory.writePreface(preface, "output_file.xml");
```

The preface will be automatically updated with a correct dictionary and any extensions classes will be added to the output.

### Media-specialist data types

MAJ has implementations of some media-specialist data types that may be useful in other applications independently from whether they use AAF data. These can be found in the [`tv.amwa.maj.record`](./src/main/java/tv/amwa/maj/record) package and include:

* [`TimecodeValue`](./src/main/java/tv/amwa/maj/record/TimecodeValue.java) - representation of SMPTE ST 12 timecode values, including static methods for parsing, converting to real time, drop frame calculations and timecode maths.
* [`AUID`](./src/main/java/tv/amwa/maj/record/AUID.java) - AAF unique identifier that is either a SMPTE Universal Label or a UUID. Includes methods for generating values.
* [`PackageID`](./src/main/java/tv/amwa/maj/record/PackageID.java) - Representation, generation and manipulation of 32-byte SMPTE UMID values.
* [`DateStruct`](./src/main/java/tv/amwa/maj/record/DateStruct.java) and [`TimeStruct`](./src/main/java/tv/amwa/maj/record/TimeStruct.java) - AAF and MXF data and time representations with conversions to and from Java equivalent values.

### Dealing with extension metadata

The MAJ API uses reflection to establish the data model it is working with. The data model must be loaded and extended at runtime. The data model is expressed by annotating Java classes with the additional information required to serialize the class to the various supported formats, such as SMPTE Universal Label identifiers, XML names with namespaces etc.. MAJ reads these annotations at runtime to be able to process AAF data. This approach was chosen to combine the efficiency of compiled Java classes with the ability to extend the data model on the fly.

The core mechanics of MAJ are found in the [`tv.amwa.maj.industry`](./src/main/java/tv/amwa/maj/industry) package, which includes:

* A [`MediaEngine`](./src/main/java/tv/amwa/maj/industry/MediaEngine.java) provides the core functions of the generic [`MetadataObject`](./src/main/java/tv/amwa/maj/industry/MetadataObject.java), such as hash code generation and equality testing.
* A [`Forge`](./src/main/java/tv/amwa/maj/industry/Forge.java) that can be used to make new instances of metadata objects as well as values of the core data types, including `TimecodeValue` and `AUID` (AAF unique identifier - either a SMPTE Universal Label or a UUID).
* A [`Warehouse`](./src/main/java/tv/amwa/maj/industry/Warehouse.java) where AAF definitions (container, codec, operation etc.) are kept alongside details of the current meta-dictionary that represents the current data model (registered AAF classes, properties and types).
* A toolkit for constructing extension data types with minimal amounts of boilerplate, including annotations and generic types for AAF *sets* and *vectors*.

Dictionary constants and enumerations that are registered with the Warehouse can be found in package [`tv.amwa.maj.constant`](./src/main/java/tv/amwa/maj/constant). These were chosen to match those used with the AAF SDK. Further useful idenfiers can be found in interface [`RP224`](./src/main/java/tv/amwa/maj/constant/RP224.java). For example, to register an ANC data mapping:

```java
import tv.amwa.maj.model.ConstainerDefinition
import tv.amwa.maj.industry.Forge
import tv.amwa.maj.constant.RP224
import tv.amwa.maj.industry.Warehouse

...

  ContainerDefinition ancDataMapping = Forge.make(ContainerDefinition.class,
    "AUID", RP224.MXFGC_Generic_VBI_Data_Mapping_Undefined_Payload,
    "Name", "MXFGC_Generic_VBI_Data_Mapping_Undefined_Payload",
    "Description", "Identifier for the MXF-GC frame wrapped Generic VBI data mapping with an undefined payload.");
  Warehouse.register(ancDataMapping);
```

#### Vendor-specific metadata

Vendors of products that use AAF sometimes emit extensions metadata as a matter of course. AAF is designed for this, with structured storage files, some MXF files and Reg-XML files all containing details of these extensions in the dictionary and meta-dictionary. MAJ has pre-built support for extensions data types found in files generated and read by Avid and Quantel (now Snell Advanced Media) products. Specifically:

* Avid extensions, found in package [`tv.amwa.maj.extenions.avid`](./src/main/java/tv/amwa/maj/extensions/avid) and registered with [`AvidFactory.registerAvidExtensions()`](./src/main/java/tv/amwa/maj/extensions/avid/AvidFactory.java).
* Quantel extensions, found in package [`tv.amwa.maj.extensions.quantel`](./src/main/java/tv/amwa/maj/extensions/quantel) and registered with [`QFactory.initialize()`](./src/main/java/tv/amwa/maj/extensions/quantel/QFactory.java).

It is not recommended to mix different vendor's extensions in the same file.

Support for SMPTE ST 436 that is in common use for the carriage of ancillary data in MXF files is also implemented as an extension:

* SMPTE ST 436 extensions in package [`tv.amwa.maj.extensions.st436`](./src/main/java/tv/amwa/maj/extensions/st436) and registered with [`ST436Factory.initialize()`](./src/main/java/tv/amwa/maj/extensions/st436/ST436Factory.java).

#### Auto-generation

Utilities are provided to automatically generated files to and from different representations of AAF data models, including Java classes that can be used as extensions. These include:

* [`MetaDictionaryGenerator`](./src/main/java/tv/amwa/maj/io/xml/MetaDictionaryGenerator.java) in package `tv.amwa.maj.io.xml` can be used to generate a SMPTE 2001-1 meta-dictionary from a given list of classes. Pass in the AAF base classes and you get the model published as SMPTE 2001-2.
* [`XSDGenerator`](./src/main/java/tv/amwa/maj/io/xml/XSDGenerator.java), also in package `tv.amwa.maj.io.xml` is used to generate an XML Schema (XSD) that can be used to validate a Reg-XML document.
* [`AutoGeneration`](./src/main/java/tv/amwa/maj/util/AutoGeneration.java) in package `tv.amwa.maj.util` can take a meta-dictionary in the format specified for Reg-XML and create a set of Java classes that can be registered with MAJ.
* [`TestGeneration`](./src/main/java/tv/amwa/maj/util/TestGeneration.java) takes a meta-dictionary file and creates a set of JUnit tests for classes that represent that meta-dictionary.
* [`RP224ToJava`](./src/main/java/tv/amwa/maj/util/RP224ToJava.java) converts a CSV file representation of SMPTE RP 224 metadata registry into Java static values that helps with the process of registering with the warehouse.

## License

The MAJ API is released under an Apache 2 license. Please see the [LICENSE](./LICENSE) file for more details.

An older and now deprecated version of this API was previously published on SourceForge (http://sourceforge.net/projects/majapi/) under the legacy AAF Public Source SDK license.

## Author

The MAJ API was written by Richard Cartwright, with the help and support of Guillaume Belrose and Fang Ren. To contact the author,
please raise an issue. The software is provided AS IS with no warranty whatsoever.

This is an approved project of the [Advanced Media Workflow Assocation (AMWA)](http://www.amwa.tv/).  Contributions via fork and
pull request are welcome according to the [IPR policy of the association](http://www.amwa.tv/about/policies/AMWA_IPR_Policy_V3.0.pdf).
