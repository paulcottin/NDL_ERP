package vues;

import controlleurs.CreerFormulaire;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import model.ERP;
import model.tables.Inscription;
import vues.inscription.VueInscription;
import vues.personne.VuePersonne;

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
		//Bouton temporaire
		Button b = new Button("Ajout form");
		b.addEventHandler(ActionEvent.ACTION, new CreerFormulaire(erp));
		
		
		// grille centrale
//		center = new VueInscription(erp);
		center = new VuePersonne(erp);
		((BorderPane) root).setLeft(left);
		((BorderPane) root).setCenter(center);
		((BorderPane) root).setBottom(b);
	}
}
