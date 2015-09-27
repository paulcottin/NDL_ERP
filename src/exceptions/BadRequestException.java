package exceptions;

public class BadRequestException extends MyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BadRequestException() {
		super();
	}
	
	public BadRequestException(String message) {
		super(message);
	}

}
