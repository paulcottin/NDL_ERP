package exceptions;

public class TableNotFoundException extends NotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TableNotFoundException(String objectName) {
		super("Table "+objectName);
	}

}
