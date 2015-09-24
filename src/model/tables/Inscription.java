package model.tables;

import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.DefaultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.connecteurs.AccessConnector;
import model.interfaces.Table;

public class Inscription extends Table{

	ObservableList<String> urls;
	
	public Inscription(int idTable) {
		super(idTable);
		urls = FXCollections.observableArrayList();
		try {
			constructInscription();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
	private void constructInscription() throws DefaultException{
		AccessConnector.openTable("pagesWeb");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			Column col = AccessConnector.table.getColumn("id_table");
			while(cursor.findNextRow(col, idTable)) {
				String u = cursor.getCurrentRow().getString("url");
				urls.add(u);
			}

		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion", e);
		} catch (IOException e) {
			throw new DefaultException("Erreur lors de la lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
	}
	
	public String toString() {
		return nom;
	}

	public ObservableList<String> getUrls() {
		return urls;
	}

	public void setUrls(ObservableList<String> urls) {
		this.urls = urls;
	}

}
