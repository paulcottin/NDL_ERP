package vues.inscription;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import model.ERP;
import model.interfaces.Table;
import model.interfaces.TableType;
import model.tables.Inscription;

public class GaucheInscription extends Parent{

	ERP erp;
	ComboBox<Inscription> choixInscription;
	Inscription inscription;
	
	public GaucheInscription(ERP erp) {
		super();
		this.erp = erp;
		this.inscription = null;
		construct();
	}
	
	private void construct() {
		ObservableList<Inscription> list = FXCollections.observableArrayList();
		for (Table table : erp.getTables())
			if (table.getType().equals(TableType.INSCRIPTION)) {
				list.add((Inscription) table);
			}
		choixInscription = new ComboBox<Inscription>(list);
		choixInscription.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				inscription = choixInscription.getValue();
			}
		});
		getChildren().add(choixInscription);
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
	}
	
}
