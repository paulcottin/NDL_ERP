package controlleurs;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.ERP;

public class CreerFormulaire implements EventHandler<ActionEvent>{

	ERP erp;
	
	public CreerFormulaire(ERP erp) {
		this.erp = erp;
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			erp.createTableFromGoogleSheet();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

}
