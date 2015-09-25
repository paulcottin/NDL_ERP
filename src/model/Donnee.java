package model;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.connecteurs.AccessConnector;

public class Donnee {

	public static final String INT = "int", BOOLEAN = "boolean", STRING = "string", ACTION = "action";

	int id, idLigne, idTable;
	StringProperty value;
	String nomColonne, type;
	boolean visible;

	public Donnee(int idLigne, int idTable) {
		this.idLigne = idLigne;
		this.idTable = idTable;
		nomColonne = null;
		type = null;
		visible = true;
		value = new SimpleStringProperty();
		try {
			createDonnee();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
	public Donnee(int id, int idLigne, int idTable) {
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
				idLigne = cursor.getCurrentRow().getInt("id_ligne");
				idTable = cursor.getCurrentRow().getInt("id_table");
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
	
	private void createDonnee() throws DefaultException {
		AccessConnector.openTable("donnees");
		
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		try {
			AccessConnector.table.addRowFromMap(map);
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			while (cursor.getNextRow() != null) {
				id = cursor.getCurrentRow().getInt("id");
			}
		} catch (IOException e) {
			throw new DefaultException("Erreur de lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
	}
	
	private void update(String colonneName, Object value) throws DefaultException{
		System.out.println(idTable+", update "+colonneName+" with "+value.toString());
		AccessConnector.openTable("donnees");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column idCol = AccessConnector.table.getColumn("id_table");
			Column col = AccessConnector.table.getColumn(colonneName);
			System.out.println("colonne name : "+col.getName()+"; value : "+value.toString());
			cursor.findFirstRow(idCol, idTable);
			//Ne trouves pas la row avec cette idTable car elle n'existe pas dans la table donnees 
			cursor.setCurrentRowValue(col, value);
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		} finally {
			AccessConnector.closeTable();
		}
	}

	public StringProperty getValue() {
		return value;
	}

	public void setValue(StringProperty value) {
		this.value = value;
		try {
			update("valeur", value.get());
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public String getNomColonne() {
		return nomColonne;
	}

	public void setNomColonne(String nomColonne) {
		this.nomColonne = nomColonne;
		try {
			update("nom_colonne", nomColonne);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		try {
			update("type", type);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		try {
			update("visible", visible);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public int getId() {
		return id;
	}

	public int getIdLigne() {
		return idLigne;
	}

	public void setIdLigne(int idLigne) {
		this.idLigne = idLigne;
		try {
			update("id_ligne", idLigne);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public int getIdTable() {
		return idTable;
	}

	public void setIdTable(int idTable) {
		this.idTable = idTable;
		try {
			update("id_table", idTable);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
}
