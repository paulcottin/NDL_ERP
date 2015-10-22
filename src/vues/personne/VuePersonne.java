package vues.personne;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.ERP;
import model.interfaces.Table;
import model.interfaces.TableType;
import model.tables.Personne;
import vues.Filtre;
import vues.Grille;

public class VuePersonne extends BorderPane{

	ERP erp;
	Table table;
	TabPane onglets;
	Filtre filtre;
	Label label;
	
	public VuePersonne(ERP erp, Table table) {
		this.erp = erp;
		this.table = table;
		init();
	}
	
	public void init() {
		label = new Label(table.getNom().get());
		this.setTop(new Filtre(table, erp));
		this.setCenter(new Grille(table));
	}
	
	
}
