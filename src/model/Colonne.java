package model;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.connecteurs.AccessConnector;

public class Colonne {

	IntegerProperty idCol;
	StringProperty nom, type, value;
	BooleanProperty visible;
	
	public Colonne(int idColonne) {
		this.idCol = new SimpleIntegerProperty(idColonne);
		nom = new SimpleStringProperty();
		type = new SimpleStringProperty();
		visible = new SimpleBooleanProperty();
		value = new SimpleStringProperty();
		try {
			createColonne();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
	private void createColonne() throws DefaultException {
		AccessConnector.openTable("colonnes");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_colonne");
			while(cursor.findNextRow(col, idCol.get())) {
				visible.set(cursor.getCurrentRow().getBoolean("visible"));
				nom.set(cursor.getCurrentRow().getString("nom"));
				type.set(cursor.getCurrentRow().getString("type"));
				value.set(cursor.getCurrentRow().getString("value"));
			}

		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion en entier");
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
		
		AccessConnector.closeTable();
	}

	public StringProperty getValue() {
		return value;
	}

	public void setValue(StringProperty value) {
		this.value = value;
	}

	public StringProperty getNom() {
		return nom;
	}

	public void setNom(StringProperty nom) {
		this.nom = nom;
	}

	public StringProperty getType() {
		return type;
	}

	public void setType(StringProperty type) {
		this.type = type;
	}

	public BooleanProperty getVisible() {
		return visible;
	}

	public void setVisible(BooleanProperty visible) {
		this.visible = visible;
	}
}
