# Media Authoring with Java API (MAJ)

The MAJ API is a pure Java API for creating, reading, manipulating and writing MXF (SMPTE 0377), [AAF](http://www.amwa.tv/projects/MS-01.shtml) and Reg-XML (SMPTE 2001) files. MXF files are commonly used are a container for professional media file formats and AAF is supported by a number of professional video editing packages. MXF and Reg-XML are used as part of the [Interoperable Mastering Format](http://www.imfforum.com/IMF_Forum/index.html) suite of specifications.

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

The MAJ API has a number of different starting points, depending on how you intend to use it. These may included:

* Writing an AAF file from scratch based on an existing internal data structure.
* Reading data from or writing to an MXF file.
* Reading data from or writing to an AAF file.
* Dumping the contents of a file.
* Streaming MXF data in or out of the API.
* Adding a library to support metadata extensions.

These topics are covered below.

### Manipulating AAF data

An application can be written using the AAF data model from scratch without the need to read or write files. One difference between MAJ and the AAF SDK is that you can write code that uses classes of the AAF model without the need to contain them within a virtual file at runtime. For more details, see the documentation of the [industry package](./apidocs/tv/amwa/maj/industry/package-summary.html).

The starting point is to initialize the local Java virtual machine so that it supports processing the AAF data model with `MediaEngine.initializeAAF()`. You can then start creating objects of the AAF data model, including *packages*, *tracks*, *sequences* and *source clips*, using the `make...` *forge*, for example:

```java
Forge.make(Class, Object...)
```

Every class in MAJ provides a registered XML representation as its `toString()` output, which in turn is created by `MediaEngine.toString()`. This makes debugging fairly easy as you can query a value in the debugger and see a human-readable XML format.

To help you get started, here is an `AMWADemoClass` code example:

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

For a more complex example, see the source for the [composition example](./src/main/tv/amwa/maj/example/CompositionExample.java).

### MXF files

Material eXchange Format (MXF) files, also known as AAF-KLV files, consist of sequence of partitions. Partitions contain a partition header and may contain metadata, index tables and/or essence data. Support for reading and writing MXF files is provided in package `tv.amwa.maj.io.mxf`.

To register all MXF data types with MAJ, start by calling `MXFBuilder.registerMXF()`. Alternatively, use static methods in the `MXFFactory` class that call this method for you.

MXF files can be processed as static entities of are streams, as described in the following sections.

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

MXF files can be very large, especially when the contain video data. The best approach for the management of  efficiently reading and writing MXF files is to use the streaming API. This requires some knowledge of the structure of both an MXF file - the expected order of partitions and local sets - and the nature of the container in use - for example an interleaved MXF OP1a file containing video and audio vs a mono-essence OP-Atom file with only audio.

The streaming API takes the form a static methods in class `tv.amwa.maj.io.mxf.MXFStream`. The methods provide a means to read one or more KLV (key-length-value) item(s) as *MXF units* from a `java.io.InputStream` and write the same structures back to a `java.io.OutputStream`. The MXF units are:

* partition packs (open, closed, complete, incomplete);
* header metadata, from header or footer partitions;
* essence elements (content package, generic container);
* index table segments;
* random index packs.

To read the next MXF unit in the stream, call `MXFStream.readNextUnit(*stream*, *sizeLimit*)`, where *stream* as a Java input stream to read from and *sizeLimit* is the maximum number of bytes to read before finding the next key. Introspect the type of the `MXFUnit` value returned.

Reading through all the units in an MXF stream or file in a linear fashion using `readNextUnit()` is a valid and efficient strategy to dump or play an MXF file. For other use cases, such as to read a single frame of video, partial access or a clip or to extract specific items of metadata from a random access MXF file, the streaming API offers directed access to specific types. For example. here is an example of a strategy for extracting a single frame (`desiredFrame`) from a closed complete MXF file:

1. Read the random index pack with `MXFStream.readRandomIndexPack(*stream*, *size*)`, where stream is an input      stream and *size* is the length of the stream. The stream is closed by this operation.
  ```java
    RandomIndexPack rip = MXFStream.readRandomIndexPack(stream, streamLength);
  ```

2. Create a new input stream looking at the same data. Use the partition offsets to read index table segments and partition packs (for header body offset properties and pack sizes) from the stream, skipping over essence and metadata, until a the index offset of the required frame is found.
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

Support for reading and writing AAF files is provided in package `tv.amwa.maj.io.aaf`. MAJ provides a
helper class `AAFFactory` as a basis for reading and writing AAF files. To read a preface from an AAF file, such as those generated by Avid, use the `readPreface()`</code></a> method. For example:</p>

```java
import tv.amwa.maj.io.aaf.AAFFactory;
import tv.amwa.maj.iface.Preface;
import tv.amwa.maj.extensions.avid.AvidFactory;
...

AvidFactory.registerAvidExtensions(); // refisters AAF and common Avid extensions
Preface fromAAF = AAFFactory.readPreface("filename.aaf");
```

Some warning messages will be printed if extensions are unknown. These can be ignored unless the extension data is important to your application.

MAJ supports writing metadata-only AAF files, files that do not contain any essence data. AAF is commonly used as a metadata-only representation so this limitation means MAJ is still useful for most use cases. To write an existing preface to an AAF file, make sure AAF and suitable extensions are registered (as for reading) and use the `writePreface()` method.</p>

```java
import tv.amwa.maj.io.AAFFactory;
import tv.amwa.maj.iface.Preface;
...

Preface prefaceToWrite = ...;
AAFFactory.writePreface(prefaceToWrite, "filename.aaf");
```

MAJ will create a dynamic meta dictionary and, if the preface does not contain a valid dictionary already, add in all the required definitions to make the file valid. 

### Reg-XML files

_To follow_.


### Media-specialist data types.

### Dealing with extension metadata

#### Vendor specific metadata

_To follow_.

#### Generating schemas.

_To follow_.


## License

The MAJ API is released under an Apache 2 license. Please see the [LICENSE](./LICENSE) file for more details.

An older and now deprecated version of this API was previously published on SourceForge (http://sourceforge.net/projects/majapi/) under the now legacy AAF Public Source SDK license.

## Author

The MAJ API was written by Richard Cartwright, with the help and support of Guillaume Belrose and Fang Ren. To contact the author,
please raise an issue. The software is provided AS IS with no warranty whatsoever.

This is an approved project of the [Advanced Media Workflow Assocation (AMWA)](http://www.amwa.tv/).  Contributions via fork and
pull request are welcome according to the [IPR policy of the association](http://www.amwa.tv/about/policies/AMWA_IPR_Policy_V3.0.pdf).
