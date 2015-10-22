package vues.controlleurs;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.interfaces.Table;
import vues.Filtre;
import vues.FiltreHelper;

public class FiltreChooser implements EventHandler<ActionEvent>{

	private Filtre f;
	private Table table;
	
	public FiltreChooser(Filtre filtre, Table table) {
		this.f = filtre;
		this.table = table;
	}
	
	@Override
	public void handle(ActionEvent event) {
		Stage stage = new Stage();
		stage.setScene(new FiltreHelper(f, table, new BorderPane(), 400, 500));
		stage.setOnCloseRequest(e -> stage.close());
		stage.show();		
	}

}
