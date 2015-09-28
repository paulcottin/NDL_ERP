package exceptions;

public class ColonneNotfoundException extends NotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ColonneNotfoundException(String objectName) {
		super(objectName);
	}

}
