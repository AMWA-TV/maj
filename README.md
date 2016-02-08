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

The starting point is to initialize the local Java virtual machine so that it supports processing the AAF data model with `MediaEngine.initializeAAF()`. You can then start creating objects of the AAF data model, including *packages*, *tracks*, *sequences* and *source clips*, using the `make...` *forge* forge, for example:

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

#### Static

_To follow_.

#### Streaming

_To follow_.

### AAF files

_To follow_.

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

## Author

The MAJ API was written by Richard Cartwright, with the help and support of Guillaume Belrose and Fang Ren. To contact the author,
please raise an issue. The software is provided AS IS with no warranty whatsoever.

This is an approved project of the [Advanced Media Workflow Assocation (AMWA)](http://www.amwa.tv/).  Contributions via fork and
pull request are welcome according to the [IPR policy of the association](http://www.amwa.tv/about/policies/AMWA_IPR_Policy_V3.0.pdf).
