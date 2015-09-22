package controlleurs;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Quitter implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		Platform.exit();
	}

}
