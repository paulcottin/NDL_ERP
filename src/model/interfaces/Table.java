package model.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import exceptions.DefaultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ligne;
import model.connecteurs.AccessConnector;

public abstract class Table {

	protected int idTable;
	protected String nom, famille, type;
	protected ObservableList<Ligne> lignes;
	protected ObservableList<Option> options;
	protected boolean constructed;

	public Table() {
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();
		constructed = false;
		try {
			createTable();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public Table(int idTable) {
		this.idTable = idTable;
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();
		constructed = false;
		try {
			initTable();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	private void initTable() throws DefaultException {
		AccessConnector.openTable("tables");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_table");
			cursor.findFirstRow(col, idTable);
			nom = cursor.getCurrentRow().getString("nom_table");
			famille = cursor.getCurrentRow().getString("famille");
			type = cursor.getCurrentRow().getString("type");
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \"tables\"");
		} finally {
			AccessConnector.closeTable();
		}

	}

	public void construct() throws DefaultException {
		AccessConnector.openTable("donnees");

		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_table");
			ArrayList<Integer> ids = new ArrayList<Integer>();
			while(cursor.findNextRow(col, idTable)) {
				int idLigne = cursor.getCurrentRow().getInt("id_ligne");
				if (!ids.contains(idLigne))
					ids.add(idLigne);
			}
			for (Integer integer : ids) 
				lignes.add(new Ligne(integer, idTable));

		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion en entier");
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}

		AccessConnector.closeTable();
		constructed = true;
	}

	protected void createTable() throws DefaultException {
		AccessConnector.openTable("tables");
		try {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			AccessConnector.table.addRowFromMap(map);
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			while (cursor.getNextRow() != null) {
				idTable = cursor.getCurrentRow().getInt("id_table");
			}
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
	}

	protected void update(String colonneName, String value) throws DefaultException {
		AccessConnector.openTable("tables");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column idCol = AccessConnector.table.getColumn("id_table");
			Column col = AccessConnector.table.getColumn(colonneName);
			cursor.findFirstRow(idCol, idTable);
			cursor.setCurrentRowValue(col, value);
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		} finally {
			AccessConnector.closeTable();
		}

	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		try {
			this.nom = nom;
			update("nom_table", nom);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		try {
			this.famille = famille;
			update("famille", famille);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public ObservableList<Option> getOptions() {
		return options;
	}

	public void setOptions(ObservableList<Option> options) {
		this.options = options;
	}

	public ObservableList<Ligne> getLignes() {
		return lignes;
	}

	public void setLignes(ObservableList<Ligne> lignes) {
		this.lignes = lignes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		try {
			this.type = type;
			update("type", type);
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	public boolean isConstructed() {
		return constructed;
	}

	public void setConstructed(boolean constructed) {
		this.constructed = constructed;
	}

	public int getIdTable() {
		return idTable;
	}

}
