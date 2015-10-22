package vues;

import java.util.ArrayList;

import controlleurs.InitialiseRecherche;
import controlleurs.Rechercher;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.ERP;
import model.Ligne;
import model.interfaces.Table;
import model.tables.SystemTable;
import vues.controlleurs.FiltreChooser;

public class Filtre extends BorderPane{

	ERP erp;
	Table table;
	FlowPane flow;
	Button search, init;
	//Control permet d'instancier à la fois des boutons et des input text
	ArrayList<Pair<Label, Control>> champs;
	ObservableList<SystemTable> systemTables;

	public Filtre(Table table, ERP erp) {
		this.table = table;
		this.erp = erp;
		this.systemTables = erp.getSystemTables();
		this.champs = init(table);

		construct();
	}

	public ArrayList<Pair<Label, Control>> init(Table table) {
		ArrayList<Pair<Label, Control>> temp = new ArrayList<Pair<Label, Control>>();
		for (String string : table.getLignes().get(0).getColonneNames()) {
			if (!string.equals(table.getIdLigneName())) {
				Label l = null;
				Control c = null;
				if (string.startsWith("id_")) {
					l = new Label();
					c = new Button(string);
					c.addEventHandler(ActionEvent.ACTION, new FiltreChooser(this, getTableWhereIDIs(string)));
				} else {
					l = new Label(string);
					c = new TextField();
				}
				temp.add(new Pair<Label, Control>(l, c));
			}
		}
		return temp;
	}

	public void construct() {
		//Liste des champs
		flow = new FlowPane();
		flow.setPadding(new Insets(5, 0, 5, 0));
		flow.setVgap(4);
		flow.setHgap(4);
		flow.setPrefWrapLength(this.heightProperty().get()-150);

		for (Pair<Label, Control> pair : champs) {
			HBox p = new HBox(5, pair.getKey(), pair.getValue());
			flow.getChildren().add(p);
		}
		this.setCenter(flow);

		//Boutons
		//Bouton recherche
		search = new Button("Rechercher");
		search.addEventHandler(ActionEvent.ACTION, new Rechercher(erp, this));
		search.setPrefWidth(120);
		search.setPrefHeight(70);
		//Bouton réinitialisation
		init = new Button("Réinitialiser");
		init.addEventHandler(ActionEvent.ACTION, new InitialiseRecherche(table));
		init.setPrefWidth(120);
		init.setPrefHeight(70);
		//Panel
		VBox v = new VBox(5);
		v.getChildren().addAll(search, init);
		this.setRight(v);
	}

	private ObservableList<String> getTableData(String id) throws TableNotFoundException {
		//Récupère la table
		Table t = getTableWhereIDIs(id);
		if (t == null) throw new TableNotFoundException(id);
		//Récupère les données
		ObservableList<String> data = FXCollections.observableArrayList();
		for (Ligne ligne: t.getLignes()) {
			data.add(ligne.getData().get(1).getValue().getValue().toString());
		}
		return data;
	}

	private Table getTableWhereIDIs(String idName) {
		for (Table table : systemTables) {
			if (table.getIdLigneName().equals(idName))
				return table;
		}
		return null;
	}

	public ArrayList<Pair<Label, Control>> getChamps() {
		return champs;
	}

	public void setChamps(ArrayList<Pair<Label, Control>> champs) {
		this.champs = champs;
	}

	public ObservableList<SystemTable> getSystemTables() {
		return systemTables;
	}

	public void setSystemTables(ObservableList<SystemTable> systemTables) {
		this.systemTables = systemTables;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
}
