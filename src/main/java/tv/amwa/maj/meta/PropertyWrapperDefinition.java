package tv.amwa.maj.meta;

/**
 * <p>Specifies a globally unique alias for a property that was originally defined to be used in another 
 * class. This allows the property to be used unambiguously in more than one class.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
public interface PropertyWrapperDefinition 
	extends PropertyDefinition {

	/**
	 * <p>Returns the original definition of the reused property.<p>
	 * 
	 * @return Original definition of the reused property.
	 */
	public PropertyDefinition getOriginalProperty();
	
	/**
	 * <p>Sets the original definition of the reused property.</p>
	 * 
	 * @param propertyDefinition Original definition of the reused property.
	 * 
	 * @throws NullPointerException Cannot set the original property definition using a <code>null</code>
	 * value.
	 * @throws IllegalArgumentException The given property definition is not compatible 
	 * with this property, for example it has the same identification or is for a different
	 * type.
	 */
	public void setOriginalProperty(
			PropertyDefinition propertyDefinition)
		throws NullPointerException,
			IllegalArgumentException;
	
	/**
	 * <p>Create a cloned copy of this property wrapper definition.</p>
	 *
	 * @return Cloned copy of this property wrapper definition.
	 */
	public PropertyWrapperDefinition clone();
}
