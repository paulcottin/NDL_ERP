package vues;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import model.ERP;
import model.interfaces.Table;
import model.tables.SystemTable;
import utils.ResultSet;

public abstract class VueListeTables extends BorderPane{

	protected ERP erp;
	protected Fenetre fen;
	protected ObservableList<Table> tables;
	private String tableType;
	protected ResultSet data;
	protected Parent gauche, droite, haut, bas;
	
	public VueListeTables(ERP erp, Fenetre fen, String tableType) {
		this.erp = erp;
		this.fen = fen;
		this.tables = FXCollections.observableArrayList();
		for (Table table : erp.getTables()) {
			if (table.getType().get().equals(tableType))
				tables.add(table);
		}
		this.tableType = tableType;
		gauche = new Pane();
		droite = new Pane();
		haut = new Pane();
		bas = new Pane();
		construct();
	}
	
	private void construct() {
		setTop(haut);
		setBottom(bas);
		setCenter(new Liste(fen, tables));
		setLeft(gauche);
		setRight(droite);
	}
}
