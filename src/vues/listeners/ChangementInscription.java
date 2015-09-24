package vues.listeners;

import exceptions.DefaultException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vues.VueInscription;

public class ChangementInscription implements EventHandler<ActionEvent>{

	private VueInscription vue;
	
	public ChangementInscription(VueInscription vue) {
		this.vue = vue;
	}
	
	@Override
	public void handle(ActionEvent event) {
		try {
			if (!vue.getGauche().getInscription().isConstructed()) vue.getGauche().getInscription().construct();
		} catch (DefaultException e) {
			e.printMessage();
		}
		vue.setInscription(vue.getGauche().getInscription());
	}

}
