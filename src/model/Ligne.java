package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
	int idLigne;
	int idTable;
	
	public Ligne(int idTable) throws DefaultException {
		data = FXCollections.observableArrayList();
		colonnesNames = new ArrayList<String>();
		this.idTable = idTable;
		createLigne();
	}
	
	
	public Ligne(int idLigne, int idTable) throws DefaultException {
		this.idLigne = idLigne;
		this.idTable = idTable;
		data = FXCollections.observableArrayList();
		colonnesNames = new ArrayList<String>();
		
		constructLigne();
	}
	
	private void constructLigne() throws DefaultException {
		AccessConnector.openTable("donnees");
		try {
			Cursor cursorLigne = CursorBuilder.createCursor(AccessConnector.table);
			Column colLigne = AccessConnector.table.getColumn("id_ligne");
			while(cursorLigne.findNextRow(colLigne, this.idLigne)) {
				if (!colonnesNames.contains(cursorLigne.getCurrentRow().getString("nom_colonne")))
					colonnesNames.add(cursorLigne.getCurrentRow().getString("nom_colonne"));
				data.add(new Donnee(cursorLigne.getCurrentRow().getInt("id"), idLigne, idTable));
			}
		} catch (IOException e) {
			throw new DefaultException("Erreur lors du parcous de la table \""+AccessConnector.table.getName()+"\"");
		}
	}
	
	private void createLigne() throws DefaultException {
		AccessConnector.openTable("donnees");
		try {
			
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			while (cursor.getNextRow() != null) {
				idLigne = cursor.getCurrentRow().getInt("id_ligne");
			}
			idLigne++;
		} catch (IOException e) {
			throw new DefaultException("Erreur de lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
		
	}
	
	public void add(String nomColonne, String value) throws DefaultException {
		Donnee d = new Donnee(idLigne, idTable);
		d.setNomColonne(nomColonne);
		d.setValue(new SimpleStringProperty(value));
		d.setVisible(true);
		//TODO donner un type de manière dynamique
		d.setType(Donnee.STRING);
		d.setIdTable(idTable);
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


	public int getIdTable() {
		return idTable;
	}


	public void setIdTable(int idTable) {
		this.idTable = idTable;
	}
}
