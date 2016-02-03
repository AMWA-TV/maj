package tv.amwa.maj.io.mxf;

import java.io.IOException;

public class MAJMXFStreamException 
	extends IOException {

	private static final long serialVersionUID = -5589973472662055243L;
	
	public MAJMXFStreamException() {
		
		super();
	}
	
	public MAJMXFStreamException(
			String message) {
		
		super(message);
	}
	
	public MAJMXFStreamException(
			String message,
			Throwable cause) {
		
		super(message, cause);
	}
}
