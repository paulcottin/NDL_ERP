package vues;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.Colonne;
import model.Donnee;
import model.Ligne;
import model.interfaces.Table;

public class Grille extends Parent{

	Table table;
	TableView<Donnee> tableView;
	ObservableList<TableColumn<Donnee, String>> colonnes;


	public Grille(Table table) {
		this.table = table;
		tableView = new TableView<>();
		colonnes = FXCollections.observableArrayList();

		//Ajout des colonnes
		ArrayList<String> cols = new ArrayList<String>();
		Iterator<Ligne> iter = table.getLignes().iterator();
		while(iter.hasNext()) {
			Ligne l = iter.next();
			//TODO : Corriger le changement ligne -> donnee
//			if (!cols.contains(l.getNomColonne().get())){
//				cols.add(l.getNomColonne().get());
//				TableColumn<Donnee, String> col = new TableColumn<Donnee, String>();
//				col.setText(l.getNomColonne().get());
//				tableView.getColumns().add(col);
//				System.out.println("add col : "+l.getNomColonne().get()+" ; "+l.getValue().get());
//			}
		}
		
		//Ajout des donnees
		 ObservableList<ObservableList<Donnee>> data = FXCollections.observableArrayList(); 
		 
//		 for(List<String> dataList : data) {
//		     ObservableList<String> row = FXCollections.observableArrayList();
//		    for( String rowData : dataList) {
//		      row.add(rowData); 
//		  }
//		   cvsData.add(row); // add each row to cvsData
//		}
		 
		

//		tableView.getItems().addAll(table.getLignes());

		//		for (Ligne ligne : table.getLignes()) {
		//			tableView.setRowFactory(new Callback<TableView<Ligne>, TableRow<Ligne>>() {
		//				
		//				@Override
		//				public TableRow<Ligne> call(TableView<Ligne> param) {
		//					// TODO Auto-generated method stub
		//					return null;
		//				}
		//			});
		//		}


		//		tableView.getColumns().setAll(colonnes);
//		System.out.println("table colonnes : "+tableView.getColumns().size());
//		getChildren().add(tableView);
	}



}
