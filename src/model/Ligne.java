package model;

import java.util.ArrayList;
import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.interfaces.BaseDonnee;
import utils.BddColonne;
import utils.ResultSet;
import utils.WhereCondition;

public class Ligne {

	ObservableList<Donnee> data;
	ArrayList<String> colonnesNames;
	BaseDonnee bdd;
	int idLigne;
	int idTable;
	
	public Ligne(BaseDonnee bdd, int idTable) throws DefaultException, TableNotFoundException, BadRequestException {
		this.bdd = bdd;
		data = FXCollections.observableArrayList();
		colonnesNames = new ArrayList<String>();
		this.idTable = idTable;
		createLigne();
	}
	
	
	public Ligne(BaseDonnee bdd, int idLigne, int idTable) throws DefaultException, TableNotFoundException, BadRequestException {
		this.bdd = bdd;
		this.idLigne = idLigne;
		this.idTable = idTable;
		data = FXCollections.observableArrayList();
		colonnesNames = new ArrayList<String>();
		
		constructLigne();
	}
	
	private void constructLigne() throws DefaultException, BadRequestException, TableNotFoundException {
		bdd.select(new BddColonne("donnees", "id"),
				new BddColonne("donnees", "nom_colonne"));
		bdd.from("donnees");
		bdd.where(new WhereCondition("donnees", "id_ligne", BaseDonnee.EGAL, idLigne));
		
		for (ResultSet map: bdd.execute()) {
			if (!colonnesNames.contains((String) map.get("nom_colonne")))
				colonnesNames.add((String) map.get("nom_colonne"));
			data.add(new Donnee(bdd, (int) map.get("id"), idLigne, idTable));
		}
	}
	
	private void createLigne() throws DefaultException, BadRequestException, TableNotFoundException {
		bdd.select(new BddColonne("donnees", "id_ligne"));
		bdd.from("donnees");
		
		ArrayList<ResultSet> res = bdd.execute();
		if ((int) res.size() > 0)
			idLigne = (int) res.get(res.size()-1).get("id_ligne");
		else
			idLigne = 0;
		idLigne++;		
	}
	
	public void add(String nomColonne, String value) throws DefaultException {
		Donnee d = new Donnee(bdd, idLigne, idTable);
		d.setNomColonne(nomColonne);
		d.setValue(new SimpleStringProperty(value));
		d.setVisible(true);
		//TODO donner un type de manière dynamique
		d.setType(Donnee.STRING);
		d.setIdTable(idTable);
	}
	
	public StringProperty getDonneeValue(String colonneName) {
		for (Donnee donnee : data) {
			if (donnee.getNomColonne().equals(colonneName))
				return donnee.getValue();
		}
		return new SimpleStringProperty();
	}

	public ObservableList<Donnee> getData() {
		return data;
	}

	public void setData(ObservableList<Donnee> data) {
		this.data = data;
	}

	public ArrayList<String> getColonnesNames() {
		return colonnesNames;
	}

	public void setColonnesNames(ArrayList<String> colonnesNames) {
		this.colonnesNames = colonnesNames;
	}


	public int getIdTable() {
		return idTable;
	}


	public void setIdTable(int idTable) {
		this.idTable = idTable;
	}
}
