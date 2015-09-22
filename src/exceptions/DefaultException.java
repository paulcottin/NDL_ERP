package exceptions;

public class DefaultException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DefaultException(){
		super();
	}
	
	public DefaultException(String message) {
		super(message);
	}
	
	public DefaultException(Exception e){
		super(e);
	}
	
	public DefaultException(String message, Exception e) {
		super(message, e);
	}
	
	public void printMessage() {
		displayMessage();
	}

}
