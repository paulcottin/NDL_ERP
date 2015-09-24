package vues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class ComboBoxListener implements EventHandler<ActionEvent>{

	Parent p;
	
	public ComboBoxListener(Parent p) {
		this.p = p;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
	}

}
