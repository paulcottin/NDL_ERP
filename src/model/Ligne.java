package model;

import java.io.IOException;
import java.util.ArrayList;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.connecteurs.AccessConnector;

public class Ligne {

	ObservableList<Donnee> data;
	ArrayList<String> colonnesNames;
	IntegerProperty idLigne;
	
	public Ligne(int idLigne) throws DefaultException {
		this.idLigne = new SimpleIntegerProperty(idLigne);
		data = FXCollections.observableArrayList();
		colonnesNames = new ArrayList<String>();
		
		constructLigne();
	}
	
	private void constructLigne() throws DefaultException {
		AccessConnector.openTable("donnees");
		try {
			Cursor cursorLigne = CursorBuilder.createCursor(AccessConnector.table);
			Column colLigne = AccessConnector.table.getColumn("id_ligne");
			while(cursorLigne.findNextRow(colLigne, this.idLigne.get())) {
				if (!colonnesNames.contains(cursorLigne.getCurrentRow().getString("nom_colonne")))
					colonnesNames.add(cursorLigne.getCurrentRow().getString("nom_colonne"));
				data.add(new Donnee(cursorLigne.getCurrentRow().getInt("id")));
			}
		} catch (IOException e) {
			throw new DefaultException("Erreur lors du parcous de la table \""+AccessConnector.table.getName()+"\"");
		}
	}
	
	public StringProperty getDonneeValue(String colonneName) {
		for (Donnee donnee : data) {
			if (donnee.getNomColonne().equals(colonneName))
				return donnee.getValue();
		}
		return new SimpleStringProperty();
	}

	public ObservableList<Donnee> getData() {
		return data;
	}

	public void setData(ObservableList<Donnee> data) {
		this.data = data;
	}

	public ArrayList<String> getColonnesNames() {
		return colonnesNames;
	}

	public void setColonnesNames(ArrayList<String> colonnesNames) {
		this.colonnesNames = colonnesNames;
	}
}
