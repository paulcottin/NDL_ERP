package vues.listeners;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vues.inscription.VueInscription;

public class ChangementInscription implements EventHandler<ActionEvent>{

	private VueInscription vue;
	
	public ChangementInscription(VueInscription vue) {
		this.vue = vue;
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			if (!vue.getGauche().getInscription().isConstructed()) vue.getGauche().getInscription().open();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
		vue.setInscription(vue.getGauche().getInscription());
	}

}
