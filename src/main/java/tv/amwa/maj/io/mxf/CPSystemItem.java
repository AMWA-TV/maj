package tv.amwa.maj.io.mxf;

public interface CPSystemItem 
	extends EssenceElement {

	public abstract UnitType getUnitType();

	public abstract void setUserDate(byte[] userDate);

	public abstract byte[] getUserDate();

	public abstract void setCreationDate(byte[] creationDate);

	public abstract byte[] getCreationDate();

	public abstract void setLabel(UL label);

	public abstract UL getLabel();

	public abstract void setContinuityCount(short continuityCount);

	public abstract short getContinuityCount();

	public abstract void setChannelHandle(short channelHandle);

	public abstract short getChannelHandle();

	public abstract void setType(byte type);

	public abstract byte getType();

	public abstract void setRate(byte rate);

	public abstract byte getRate();

	public abstract void setBitmap(byte bitmap);

	public abstract byte getBitmap();

}
