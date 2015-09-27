package exceptions;

public class IdentificationException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IdentificationException() {
		super();
	}
	
	public IdentificationException(String message) {
		super(message);
	}
	
	public IdentificationException(Exception e) {
		super(e);
	}
	
	public IdentificationException(String message, Exception e) {
		super(message, e);
	}

}
