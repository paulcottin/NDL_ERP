package utils;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.util.Pair;

public class BddValue extends Pair<String, ObservableValue<Object>>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BddValue(String colonneName, Object value) {
		super(colonneName, new SimpleObjectProperty<Object>(value));
	}
	
	public String getColonne() {
		return super.getKey();
	}

}
