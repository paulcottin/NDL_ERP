package model;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.connecteurs.AccessConnector;

public class Donnee {

	public static final String INT = "int", BOOLEAN = "boolean", STRING = "string", ACTION = "action";

	int id, idData;
	StringProperty value;
	String nomColonne, type;
	boolean visible;

	public Donnee(int id) {
		this.id = id;
		nomColonne = null;
		type = null;
		visible = true;
		value = new SimpleStringProperty();
		try {
			constructLigne();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	private void constructLigne() throws DefaultException {
		//On crée la liste des données
		AccessConnector.openTable("donnees");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id");
			while(cursor.findNextRow(col, id)) {
				idData = cursor.getCurrentRow().getInt("id_ligne");
				nomColonne = cursor.getCurrentRow().getString("nom_colonne");
				visible = cursor.getCurrentRow().getBoolean("visible");
				value.set(cursor.getCurrentRow().getString("valeur"));

				String accessType = cursor.getCurrentRow().getString("type");
				switch (accessType) {
				case INT:
					this.type = INT;
					break;
				case STRING:
					this.type = STRING;
					break;
				case BOOLEAN:
					this.type = BOOLEAN;
					break;
				case ACTION:
					this.type = ACTION;
					break;

				default:
					throw new DefaultException("Type \""+accessType+"\"inconnu !");
				}
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

	public String getNomColonne() {
		return nomColonne;
	}

	public void setNomColonne(String nomColonne) {
		this.nomColonne = nomColonne;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
}
