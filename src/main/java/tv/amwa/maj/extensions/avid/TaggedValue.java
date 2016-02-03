package tv.amwa.maj.extensions.avid;

import java.util.List;

import tv.amwa.maj.exception.PropertyNotPresentException;
import tv.amwa.maj.integer.UInt32;
import tv.amwa.maj.model.InterchangeObject;

public interface TaggedValue 
	extends tv.amwa.maj.model.TaggedValue {

	public List<? extends tv.amwa.maj.model.TaggedValue> getTaggedValueAttributeList()
		throws PropertyNotPresentException;

	public void appendTaggedValueAttributeItem(
			tv.amwa.maj.model.TaggedValue taggedValueAttributeItem)
		throws NullPointerException;

	public void prependTaggedValueAttributeItem(
			tv.amwa.maj.model.TaggedValue taggedValueAttributeItem)
		throws NullPointerException;

	public void insertTaggedValueAttributeItem(
			int index,
			tv.amwa.maj.model.TaggedValue taggedValueAttributeItem)
		throws NullPointerException,
			IndexOutOfBoundsException;

	public int countTaggedValueAttributeList();

	public void clearTaggedValueAttributeList();

	public tv.amwa.maj.model.TaggedValue getTaggedValueAttributeItemAt(
			int index)
		throws IndexOutOfBoundsException;

	public void removeTaggedValueAttributeItemAt(
			int index)
		throws IndexOutOfBoundsException;
	
	public @UInt32 int getPortableObjectClassID()
		throws PropertyNotPresentException;
	
	public void setPortableObjectClassID(
			@UInt32 Integer portableObjectClassID)
		throws IllegalArgumentException;
	
	public InterchangeObject getPortableObject()
		throws PropertyNotPresentException;
	
	public void setPortableObject(
			InterchangeObject portableObject);
}
