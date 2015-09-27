package utils;

import javafx.util.Pair;

public class BddValue extends Pair<String, Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BddValue(String colonneName, Object value) {
		super(colonneName, value);
		// TODO Auto-generated constructor stub
	}
	
	public String getColonne() {
		return super.getKey();
	}

}
