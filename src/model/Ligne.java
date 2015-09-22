package model;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.connecteurs.AccessConnector;

public class Ligne {

	public static final String INT = "int", BOOLEAN = "boolean", STRING = "string", ACTION = "action";
	
	IntegerProperty idLigne, idCol;
	StringProperty type, value;
	
	public Ligne(int idLigne) {
		this.idLigne = new SimpleIntegerProperty(idLigne);
		this.idCol = new SimpleIntegerProperty();
		this.type = new SimpleStringProperty();
		this.value = new SimpleStringProperty();
		try {
			constructLigne();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
	private void constructLigne() throws DefaultException {
		//On récupère le type de la valeur
		AccessConnector.openTable("lignes");
		try {
			//On récupère l'id de la colonne
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_ligne");
			cursor.findFirstRow(col, idLigne.get());
			idCol.set(cursor.getCurrentRow().getInt("id_colonne"));
			value.set(cursor.getCurrentRow().getString("valeur"));
			
			//On récupère le type
			AccessConnector.closeTable();
			AccessConnector.openTable("colonnes");
			cursor = CursorBuilder.createCursor(AccessConnector.table);
			col = AccessConnector.table.getColumn("id_colonne");
			cursor.findFirstRow(col, idCol.get());
			String type = cursor.getCurrentRow().getString("type");
			
			switch (type) {
			case INT:
				this.type.set(INT);
				break;
			case STRING:
				this.type.set(STRING);
				break;
			case BOOLEAN:
				this.type.set(BOOLEAN);
				break;
			case ACTION:
				this.type.set(ACTION);
				break;

			default:
				break;
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

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public String getValue() {
		return value.get();
	}

	public void setValue(String value) {
		this.value.set(value);
	}
}
