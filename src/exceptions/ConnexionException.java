package exceptions;

public class ConnexionException extends MyException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConnexionException() {
		super();
	}
	
	public ConnexionException(String message) {
		super(message);
	}
	
	public ConnexionException(Exception e) {
		super(e);
	}
	
	public ConnexionException(String message, Exception e) {
		super(message, e);
	}

}
