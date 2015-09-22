package model;

import java.io.File;

import exceptions.DefaultException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.connecteurs.AccessConnector;
import model.interfaces.Table;
import model.tables.Inscription;
import vues.Fenetre;

public class ERP extends Application{

	ObservableList<Table> tables;
	
	public ERP() {
		this.tables = FXCollections.observableArrayList();
		AccessConnector.databaseFile = new File("C:\\Users\\polob\\Documents\\bdd_erp.accdb");
		try {
			AccessConnector.connect();
		} catch (DefaultException e) {
			e.printMessage();
		}
		
		tables.add(new Inscription(1));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Fenetre(this, new BorderPane(),1200,600);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<Table> getTables() {
		return tables;
	}

	public void setTables(ObservableList<Table> tables) {
		this.tables = tables;
	}

}
