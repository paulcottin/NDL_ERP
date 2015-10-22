package vues;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import model.ERP;
import vues.controlleurs.OpenEvenement;
import vues.controlleurs.OpenInscription;
import vues.controlleurs.OpenPersonne;

public class BarreTache extends ToolBar {

	private ERP erp;
	private Fenetre fen;
	
	private Button inscription, personne, evenement;
	
	public BarreTache(ERP erp, Fenetre fen) {
		super();
		this.erp = erp;
		this.fen = fen;
		init();
		construct();
	}
	
	private void init() {
		inscription = new Button("Inscriptions");
		inscription.addEventHandler(ActionEvent.ACTION, new OpenInscription(fen));
		personne = new Button("Personnes");
		personne.addEventHandler(ActionEvent.ACTION, new OpenPersonne(fen));
		evenement = new Button("Evénements");
		evenement.addEventHandler(ActionEvent.ACTION, new OpenEvenement(fen));
	}
	
	private void construct() {
		this.getItems().addAll(personne, inscription, evenement);
	}
	
}
