package controlleurs;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.interfaces.Table;

public class InitialiseRecherche implements EventHandler<ActionEvent>{

	Table table;
	
	public InitialiseRecherche(Table table) {
		this.table = table;
	}
	
	
	@Override
	public void handle(ActionEvent arg0) {
		try {
			table.open();
		} catch (TableNotFoundException | ColonneNotfoundException | DefaultException | BadRequestException e) {
			e.printMessage();
		}
	}

}
