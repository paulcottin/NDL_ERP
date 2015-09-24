package model;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

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
import model.interfaces.TableType;
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
		
		try {
			initTables();
		} catch (DefaultException e) {
			e.printMessage();
		}
	}
	
	private void initTables() throws DefaultException {
		AccessConnector.openTable("tables");
		try {
			Cursor cursor = CursorBuilder.createCursor(AccessConnector.table);
			while (cursor.getNextRow() != null) {
				//TODO : Instanciation automatique en fonction du type de table
				if (cursor.getCurrentRow().getString("type").equals(TableType.INSCRIPTION))
					tables.add(new Inscription(cursor.getCurrentRow().getInt("id_table")));
			}
		} catch (IOException e) {
			throw new DefaultException("Erreur de lecture de la table \""+AccessConnector.table.getName()+"\"");
		}
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
