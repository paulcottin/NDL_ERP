package model.interfaces;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Colonne;
import model.Donnee;
import model.Ligne;
import model.connecteurs.AccessConnector;

public abstract class Table {

	protected int idTable;
	protected String nom, famille;
	protected ObservableList<Ligne> lignes;
	protected ObservableList<Option> options;

	public Table(int idTable) {
		this.idTable = idTable;
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();

		try {
			constructTable();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}

	private void constructTable() throws DefaultException {
		AccessConnector.openTable("donnees");
		
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_table");
			while(cursor.findNextRow(col, idTable)) {
				//TODO: sélectionner l'id maximum + Tant qu'il qu'on a pas ajouter toutes les 
				//données d'une ligne on les recherche et ajoute + incrémenter l'id_ligne jusqu'au max
				int id = cursor.getCurrentRow().getInt("id_ligne");
				AccessConnector.openTable("donnees");
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

}
