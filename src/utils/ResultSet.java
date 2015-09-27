package utils;

import java.util.ArrayList;

public class ResultSet extends ArrayList<BddValue>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResultSet() {
		super();
	}
	
	public Object get(String colonneName) {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).getColonne().equals(colonneName))
				return this.get(i).getValue();
		}
		return null;
	}

}
