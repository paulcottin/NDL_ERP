package vues;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import model.ERP;
import model.tables.Inscription;

public class Fenetre extends Scene{

	private ERP erp;
	private Parent root, center, left, right, bottom;
	
	public Fenetre(ERP erp, Parent root, int x, int y) {
		super(root, x, y);
		this.erp = erp;
		this.root = root;
		createWin();
	}
	
	private void createWin() {	
		
		// grille centrale
		center = new VueInscription(erp);
		((BorderPane) root).setLeft(left);
		((BorderPane) root).setCenter(center);
	}
}
