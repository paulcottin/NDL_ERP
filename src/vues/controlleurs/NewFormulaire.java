package vues.controlleurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vues.Fenetre;
import vues.Navigateur;

public class NewFormulaire implements EventHandler<ActionEvent>{

	//TODO finir le passement de paramètre avec le bon id de dossier need ERP
	public static final String URL = "https://docs.google.com/forms/create?usp=drive_web&folder=0AIPWzpehgPhaUk9PVA";
	
	private Fenetre fen;
	
	public NewFormulaire(Fenetre fen) {
		this.fen = fen;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		fen.getCenter().getTabs().add(new Navigateur("Nouvelle inscription", URL));
	}

}
