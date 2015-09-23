package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Ligne {

	ObservableList<Donnee> data;
	IntegerProperty idLigne;
	
	public Ligne(int idLigne) {
		this.idLigne = new SimpleIntegerProperty(idLigne);
		data = FXCollections.observableArrayList();
		
		
	}
}
