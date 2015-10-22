package vues.controlleurs;

import exceptions.DefaultException;
import exceptions.TableTypeNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.interfaces.TableType;
import vues.Fenetre;

public class OpenEvenement implements EventHandler<ActionEvent>{

	private Fenetre fen;

	public OpenEvenement(Fenetre fen) {
		this.fen = fen;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			fen.setTableListe(TableType.EVENEMENT);
		} catch (TableTypeNotFoundException e) {
			e.printMessage();
		}
	}

}
