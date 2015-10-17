package vues.personne;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.ERP;
import vues.Filtre;

public class VuePersonne extends BorderPane{

	ERP erp;
	TabPane onglets;
	Filtre filtre;
	
	public VuePersonne(ERP erp) {
		this.erp = erp;
	}
}
