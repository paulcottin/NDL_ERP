package vues;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;
import model.Ligne;
import model.interfaces.Table;
import utils.BddValue;
import utils.ResultSet;
import vues.controlleurs.ListeTableControlleur;

public class Liste extends AnchorPane {

	private Fenetre fen;
	private	ObservableList<Table> tables;
	private TableView<Table> tableView;
	private ObservableList<String> colonnes;
	
	public Liste(Fenetre fen, ObservableList<Table> tables) {
		this.fen = fen;
		this.tables = tables;
		tableView = new TableView<>();
		colonnes = FXCollections.observableArrayList();

		//Ajout des colonnes
		colonnes.addAll("Nom", "Type");
		TableColumn<Table, String> nomCol = new TableColumn<Table, String>("Nom");
        nomCol.setCellValueFactory(cellData -> cellData.getValue().getNom());
        TableColumn<Table, String> typeCol = new TableColumn<Table, String>("Type");
        typeCol.setCellValueFactory(cellData -> cellData.getValue().getType());
        tableView.getColumns().add(nomCol);
        tableView.getColumns().add(typeCol);
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, new ListeTableControlleur(fen));
		
		
		//Et des données
		tableView.setItems(tables);
		getChildren().add(tableView);
	
	}
}
