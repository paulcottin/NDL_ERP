package vues.controlleurs;


import exceptions.DefaultException;
import exceptions.TableTypeNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.interfaces.TableType;
import vues.Fenetre;

public class OpenInscription implements EventHandler<ActionEvent>{

	private Fenetre fen;

	public OpenInscription(Fenetre fen) {
		this.fen = fen;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			fen.setTableListe(TableType.INSCRIPTION);
		} catch (TableTypeNotFoundException e) {
			e.printMessage();
		}
	}

}
