package exceptions;

public class TableTypeNotFoundException extends NotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TableTypeNotFoundException(String objectName) {
		super("Type de table : "+objectName);
	}

}
