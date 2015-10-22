package vues.controlleurs;

import exceptions.TableNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.interfaces.Table;
import vues.Fenetre;

public class ListeTableControlleur implements EventHandler<MouseEvent>{

	private Fenetre fen;
	
	public ListeTableControlleur(Fenetre fen) {
		this.fen = fen;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handle(MouseEvent event) {
		if (event.getClickCount() > 1) {
			TableView<Table> vue = (TableView<Table>) event.getSource();
			int id = vue.getSelectionModel().getSelectedItem().getIdTable();
			try {
				fen.setTable(id);
			} catch (TableNotFoundException e) {
				e.printMessage();
			}
		}
	}

}
