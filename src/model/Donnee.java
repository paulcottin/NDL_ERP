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

public class Donnee {

	public static final String INT = "int", BOOLEAN = "boolean", STRING = "string", ACTION = "action";

	IntegerProperty id, idData;
	StringProperty nomColonne, type, value;
	BooleanProperty visible;

	public Donnee(int idLigne) {
		id = new SimpleIntegerProperty(idLigne);
		this.idData = new SimpleIntegerProperty();
		nomColonne = new SimpleStringProperty();
		type = new SimpleStringProperty();
		visible = new SimpleBooleanProperty();
		value = new SimpleStringProperty();
		try {
			constructLigne();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	private void constructLigne() throws DefaultException {
		//On crée la liste des lignes
		AccessConnector.openTable("lignes");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id");
			while(cursor.findNextRow(col, id.get())) {
				idData.set(cursor.getCurrentRow().getInt("id_ligne"));
				nomColonne.set(cursor.getCurrentRow().getString("nom_colonne"));
				visible.set(cursor.getCurrentRow().getBoolean("visible"));
				value.set(cursor.getCurrentRow().getString("valeur"));

				String accessType = cursor.getCurrentRow().getString("type");
				switch (accessType) {
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

	public int getIdLigne() {
		return idData.get();
	}

	public void setIdLigne(int idLigne) {
		this.idData.set(idLigne);
	}

	public StringProperty getNomColonne() {
		return nomColonne;
	}

	public void setNomColonne(StringProperty nomColonne) {
		this.nomColonne = nomColonne;
	}

	public StringProperty getTypeColonne() {
		return type;
	}

	public void setTypeColonne(StringProperty typeColonne) {
		this.type = typeColonne;
	}

	public StringProperty getValue() {
		return value;
	}

	public void setValue(StringProperty value) {
		this.value = value;
	}

	public BooleanProperty getVisible() {
		return visible;
	}

	public void setVisible(BooleanProperty visible) {
		this.visible = visible;
	}

	public void setIdLigne(IntegerProperty idLigne) {
		this.idData = idLigne;
	}
}
