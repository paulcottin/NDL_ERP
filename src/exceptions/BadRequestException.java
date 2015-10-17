package exceptions;

import java.sql.SQLException;

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

	public BadRequestException(String string, Exception e) {
		super(string, e);
	}

}
