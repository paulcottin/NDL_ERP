package vues;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.ERP;
import model.tables.Inscription;

public class Fenetre extends Scene{

	private ERP erp;
	private Parent root;
	
	public Fenetre(ERP erp, Parent root, int x, int y) {
		super(root, x, y);
		this.erp = erp;
		this.root = root;
		createWin();
	}
	
	private void createWin() {
		((BorderPane) root).setCenter(new VueInscription((Inscription) erp.getTables().get(0)));
	}
}
