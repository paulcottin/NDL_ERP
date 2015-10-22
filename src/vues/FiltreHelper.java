package vues;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import model.interfaces.Table;

public class FiltreHelper extends Scene{

	Filtre filtre;
	Table table;
	ArrayList<Pair<Label, Control>> champs;
	Parent root;

	public FiltreHelper(Filtre filtre, Table table, Parent root, int x, int y) {
		super(root, x, y);
		this.root = root;
		this.table = table;
		this.filtre = filtre;

		champs = filtre.init(table);
		construct();
	}

	private void construct() {
		//Liste des champs
		VBox flow = new VBox(5);
		flow.setPadding(new Insets(5, 0, 5, 0));

		for (Pair<Label, Control> pair : champs) {
			HBox p = new HBox(5, pair.getKey(), pair.getValue());
			flow.getChildren().add(p);
		}
		((BorderPane) root).setCenter(flow);

		//Bouton recherche
		Button search = new Button("OK");
		
		//TODO listener de recherche + fonction de recherche
		((BorderPane) root).setBottom(search);
	}
}
