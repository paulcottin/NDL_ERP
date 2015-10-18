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
	TabPane onglets;
	Filtre filtre;
	Label label;
	ObservableList<Personne> liste;
	
	public VuePersonne(ERP erp) {
		this.erp = erp;
		liste = FXCollections.observableArrayList();
		for (Table t : erp.getTables()) {
			if (t.getType().equals(TableType.PERSONNE))
				liste.add((Personne) t);
		}
		init();
	}
	
	public void init() {
		
		label = new Label(liste.get(0).getNom());
		this.setTop(label);
		this.setCenter(new Grille(liste.get(0)));
	}
	
	
}
