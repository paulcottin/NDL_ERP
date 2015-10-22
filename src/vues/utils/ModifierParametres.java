package vues.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class ModifierParametres extends Scene{

	private VBox vBox;
	private ObservableList<Pair<Label, TextField>> champs;
	
	public ModifierParametres(Parent root, double width, double height) {
		super(root, width, height);
		champs = FXCollections.observableArrayList();
		vBox = new VBox(5);
		//TODO compléter la fenêtre
	}

}
