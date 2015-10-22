package vues.inscription;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import model.ERP;
import vues.Fenetre;
import vues.VueListeTables;
import vues.controlleurs.NewFormulaire;

public class ListeInscriptions extends VueListeTables{

	private Button addForm;
	
	public ListeInscriptions(ERP erp, Fenetre fen, String tableType) {
		super(erp, fen, tableType);
		
		construct();
	}
	
	private void construct() {
		addForm = new Button("Créer un formulaire d'inscription");
		addForm.addEventHandler(ActionEvent.ACTION, new NewFormulaire(fen));
	}
}
