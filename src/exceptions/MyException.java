package exceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class MyException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String message;
	protected Exception e;
	
	public MyException() {
		super();
		this.message = "";
		this.e = null;
	}
	
	public MyException(String message){
		super(message);
		this.message = message;
	}
	
	public MyException(Exception e) {
		message = e.getMessage() +"\n"+e.getClass()+"\n"+e.getCause();
		this.e = e;
	}
	
	public MyException(String message, Exception e) {
		this.message = message;
		this.e = e;
	}
	
	public void printMessage(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur !");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
		if (e != null) e.printStackTrace();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
