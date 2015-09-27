package utils;

import javafx.util.Pair;

public class BddColonne extends Pair<String, String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BddColonne(String nomTable, String nomColonne) {
		super(nomTable, nomColonne);
	}
	
	public String getTableName() {
		return super.getKey();
	}
	
	public String getColonneName() {
		return super.getValue();
	}
}
