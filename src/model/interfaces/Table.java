package model.interfaces;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Colonne;
import model.Ligne;
import model.connecteurs.AccessConnector;

public abstract class Table {

	protected int idTable;
	protected String nom, famille;
	protected ObservableList<Colonne> colonnes;
	protected ObservableList<Ligne> lignes;
	protected ObservableList<Option> options;

	public Table(int idTable) {
		this.idTable = idTable;
		colonnes = FXCollections.observableArrayList();
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();

		try {
			constructTable();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	private void constructTable() throws DefaultException {
		AccessConnector.openTable("colonnes");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_table");
			while(cursor.findNextRow(col, idTable)) {
				int id = cursor.getCurrentRow().getInt("id_colonne");
				colonnes.add(new Colonne(id));
				AccessConnector.openTable("colonnes");
			}

		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion en entier");
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
		
		AccessConnector.closeTable();
		AccessConnector.openTable("lignes");
		
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_table");
			while(cursor.findNextRow(col, idTable)) {
				int id = cursor.getCurrentRow().getInt("id_ligne");
				lignes.add(new Ligne(id));
				AccessConnector.openTable("lignes");
			}

		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion en entier");
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
		
		AccessConnector.closeTable();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		this.famille = famille;
	}

	public ObservableList<Colonne> getColonnes() {
		return colonnes;
	}

	public void setColonnes(ObservableList<Colonne> colonnes) {
		this.colonnes = colonnes;
	}

	public ObservableList<Ligne> getLignes() {
		return lignes;
	}

	public void setLignes(ObservableList<Ligne> lignes) {
		this.lignes = lignes;
	}

	public ObservableList<Option> getOptions() {
		return options;
	}

	public void setOptions(ObservableList<Option> options) {
		this.options = options;
	}

}
