package vues;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Colonne;
import model.Ligne;
import model.interfaces.Table;

public class Grille {
	
	Table table;
	TableView<Ligne> tableView;
	ObservableList<TableColumn<Ligne, String>> colonnes;
	
	public Grille(Table table) {
		this.table = table;
		tableView = new TableView<>();
		colonnes = FXCollections.observableArrayList();
		
		//On liste toutes les colonnes existantes		
		Iterator<Colonne> iter = table.getColonnes().iterator();
		while (iter.hasNext()) {
			Colonne c = iter.next();
			TableColumn<Ligne, String> col = new TableColumn<Ligne, String>();
			col.setCellValueFactory(cellData -> c.getValue());
			System.out.println("ajout de la colonne "+c.getNom().get());
		}
	}
	
	

}
