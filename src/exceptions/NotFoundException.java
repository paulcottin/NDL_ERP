package exceptions;

public abstract class NotFoundException extends MyException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String objectName) {
		super();
		message = objectName+" non trouvé !";
	}

	
}

