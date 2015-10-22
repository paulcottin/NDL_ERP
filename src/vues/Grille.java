package vues;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.ColonneNotfoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Ligne;
import model.interfaces.Table;

public class Grille extends AnchorPane{

	Table table;
	TableView<Ligne> tableView;
	ObservableList<TableColumn<Ligne, Object>> colonnes;


	public Grille(Table table) {
		this.table = table;
		tableView = new TableView<>();
		colonnes = FXCollections.observableArrayList();

		//Ajout des colonnes
		ArrayList<String> cols = new ArrayList<String>();
		Iterator<Ligne> iter = table.getLignes().iterator();
		while(iter.hasNext()) {
			Ligne l = iter.next();
			for (String colonneName : l.getColonneNames()) {
				if (!cols.contains(colonneName)) {
					cols.add(colonneName);
					TableColumn<Ligne, Object> lastNameCol = new TableColumn<Ligne, Object>(colonneName);
			        lastNameCol.setCellValueFactory(cellData -> cellData.getValue().getData().get(colonneName));
			        tableView.getColumns().add(lastNameCol);
				}
			}
		}
		
		//Et des données
		tableView.setItems(table.getLignes());
		getChildren().add(tableView);
//		setTopAnchor(tableView, (double) 0);
	}



}
