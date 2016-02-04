package tv.amwa.maj.extensions.quantel;

import tv.amwa.maj.industry.Forge;
import tv.amwa.maj.industry.MetadataObject;
import tv.amwa.maj.industry.Warehouse;
import tv.amwa.maj.model.ParameterDefinition;

/**
 * <p>Factory for all types and classes of symbolespace <code>http://www.quantel.com/genQ/extensions</code>.
 * Contains methods to initialize the MAJ media engine and warehouses with the types
 * of this symbol space.</p>
 *
 * <p>Quantel extension metadata</p>
 *
 *
 *
 * @see TypeDefinitions
 * @see tv.amwa.maj.industry.MediaEngine
 * @see tv.amwa.maj.industry.Warehouse
 */
public class QFactory{
    
    private static boolean initialized = false;

    /**
     * <p>List of all the implementing classes defined for this symbol space.</p>
     *
     * @see #initialize()
     */
    public final static Class<?>[] CLASSES = new Class<?>[] {
            tv.amwa.maj.extensions.quantel.impl.PolyFileDescriptorImpl.class };

    /**
     * <p>Initialize all the types of this symbol space and make them available through
     * the MAJ {@linkplain tv.amwa.maj.industry.MediaEngine media engine}. The media engine
     * and associated APIs can then make, serialize and deserialize values to all supported
     * XML formats, binary formats and persistance providers.</p>
     *
     * @see tv.amwa.maj.industry.MediaEngine#initializeAAF()
     * @see #CLASSES
     */
    public final static void initialize() {
        
        if (initialized) return;

        // Register all of the symbolspace classes
        for ( Class<?> extensionClass : CLASSES )
            Warehouse.lookForClass(extensionClass);

        // Register all of the initial extendible enumeration values
        // Register all of the extension type definitions
        Warehouse.registerTypes(TypeDefinitions.class, QConstants.XML_NAMESPACE, QConstants.XML_PREFIX);
        
        for ( ParameterDefinition pd : QParameterConstants.allParameters ) 
        	Warehouse.register(pd);

        initialized = true;

    }

    /**
     * <p>Create a new instance of a class defined in this symbol space from its name
     * and initial property values.</p>
     *
     * @param className Name of the class in this symbol space to create.
     * @param properties List of property identifier and value pairs to use to make a
     * value of the required type.
     * @return Newly created value of the named class.
     *
     * @throws NullPointerException Cannot create a new instance from a <code>null</code>
     * name or <code>null</code> property specifications.
     * @throws IllegalArgumentException Unable to use the given properties to create a
     * value of the name class or the named class does not exist.
     *
     * @see tv.amwa.maj.industry.Forge#makeByName(String, String, Object...)
     */
    @SuppressWarnings("unchecked")
    public final static <T extends MetadataObject> T make(
            String className,
            Object... properties)
        throws NullPointerException,
            IllegalArgumentException {

        return (T) Forge.makeByName(QConstants.XML_NAMESPACE, className, properties);
    }

}
