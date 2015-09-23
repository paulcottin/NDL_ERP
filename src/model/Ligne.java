package model;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.connecteurs.AccessConnector;

public class Ligne {

	public static final String INT = "int", BOOLEAN = "boolean", STRING = "string", ACTION = "action";
	
	IntegerProperty idLigne;
	ObservableList<Colonne> colonnes;
	
	public Ligne(int idLigne) {
		this.idLigne = new SimpleIntegerProperty(idLigne);
		this.colonnes = FXCollections.observableArrayList();
		try {
			constructLigne();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
	private void constructLigne() throws DefaultException {
		//On crée la liste de colonnes
		AccessConnector.openTable("lignes");
		try {
			
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_ligne");
			while(cursor.findNextRow(col, idLigne.get())) {
				int id = cursor.getCurrentRow().getInt("id_colonne");
				colonnes.add(new Colonne(id));
				AccessConnector.openTable("lignes");
			}
			
		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion en entier");
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
		AccessConnector.closeTable();
	}

	public int getIdLigne() {
		return idLigne.get();
	}

	public void setIdLigne(int idLigne) {
		this.idLigne.set(idLigne);
	}

	public ObservableList<Colonne> getColonnes() {
		return colonnes;
	}

	public void setColonnes(ObservableList<Colonne> colonnes) {
		this.colonnes = colonnes;
	}
}
