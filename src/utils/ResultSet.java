package utils;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.ColonneNotfoundException;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableListBase;

public class ResultSet extends ArrayList<BddValue>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResultSet() {
		super();
	}
	
	public ObservableValue<Object> get(String colonneName) {
		try {
			return this.get(getIndexOf(colonneName)).getValue();
		} catch (ColonneNotfoundException e) {
			e.printMessage();
		}
		return null;
	}
	
	public void remove(String colonneName) throws ColonneNotfoundException{
		this.remove(getIndexOf(colonneName));
	}
	
	public int getIndexOf(String colonneName) throws ColonneNotfoundException {
		int index = -1;
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i).getColonne().equals(colonneName))
				index = i;
		}
		if (index == -1) throw new ColonneNotfoundException(colonneName);
		return index;
	}
	
	public boolean isColonne(String colonneName) {
		for (BddValue value : this) {
			if (value.getColonne().equals(colonneName))
				return true;
		}
		return false;
	}
}
