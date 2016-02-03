package tv.amwa.maj.io.mxf;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import tv.amwa.maj.io.mxf.impl.IndexEntryImpl;

/**
 * <p>Represents a flag used in an {@linkplain IndexEntryImpl index entry} to indicate the nature 
 * of the indexed edit unit.</p>
 * 
 * @author <a href="mailto:richard@portability4media.com">Richard Cartwright</a>
 *
 */
@Documented
@Target(value={ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface EditUnitFlag {
	
}
