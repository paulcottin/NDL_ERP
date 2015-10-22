package model.interfaces;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import model.Ligne;
import tasks.SearchTask;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public abstract class Table {

	protected int idTable;
	protected StringProperty nom, famille, type, idLigneName;
	protected ObservableList<Ligne> lignes;
	protected ObservableList<Option> options;
	protected ArrayList<BddValue> initialValues;
	protected boolean constructed;
	protected BaseDonnee bdd;

	public Table(BaseDonnee bdd, ArrayList<BddValue> values) {
		this.bdd = bdd;
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();
		this.initialValues = values;
		for (BddValue bddValue : initialValues) {
			if (bddValue.getColonne().equals("nom_table"))
				nom = new SimpleStringProperty((String) bddValue.getValue().getValue());
			else if (bddValue.getColonne().equals("famille"))
				famille = new SimpleStringProperty((String) bddValue.getValue().getValue());
			else if (bddValue.getColonne().equals("type"))
				type = new SimpleStringProperty((String) bddValue.getValue().getValue());
			else if (bddValue.getColonne().equals("id_ligne_name"))
				idLigneName = new SimpleStringProperty((String) bddValue.getValue().getValue());
		}
		constructed = false;
		try {
			createTable();
		} catch (DefaultException | TableNotFoundException | BadRequestException | ColonneNotfoundException e) {
			e.printMessage();
		}
	}

	public Table(BaseDonnee accessConnector, int idTable) {
		this.bdd = accessConnector;
		this.idTable = idTable;
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();
		nom = new SimpleStringProperty();
		famille = new SimpleStringProperty();
		idLigneName = new SimpleStringProperty();
		type = new SimpleStringProperty();
		constructed = false;
		try {
			initTable();
		} catch (DefaultException | TableNotFoundException | BadRequestException | ColonneNotfoundException e) {
			e.printMessage();
			e.printStackTrace();
		}
	}
	
	public void search(Table table, ArrayList<Pair<Table, Pair<String, Object>>> conditions) {
		SearchTask search = new SearchTask(this, conditions);
		search.run();
	}

	protected abstract void initTable() throws DefaultException, TableNotFoundException, BadRequestException, ColonneNotfoundException;
	public abstract void open() throws DefaultException, TableNotFoundException, BadRequestException, ColonneNotfoundException; 
	protected abstract void createTable() throws DefaultException, TableNotFoundException, BadRequestException, ColonneNotfoundException;
	public abstract void createColonnes(ResultSet resultSet) throws TableNotFoundException, BadRequestException;
	

	protected void update(String colonneName, String value) throws DefaultException, BadRequestException, TableNotFoundException {
		bdd.update(new BddColonne("tables", colonneName), value);
		bdd.where(new WhereCondition("tables", "id_table", BaseDonnee.EGAL, idTable));
		bdd.execute();
	}

	public StringProperty getNom() {
		return nom;
	}

	public void setNom(String nom) {
		try {
			this.nom.set(nom);
			update("nom_table", nom);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public StringProperty getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		try {
			this.famille.set(famille);
			update("famille", famille);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public ObservableList<Option> getOptions() {
		return options;
	}

	public void setOptions(ObservableList<Option> options) {
		this.options = options;
	}

	public StringProperty getType() {
		return type;
	}

	public void setType(String type) {
		try {
			this.type.set(type);
			update("type", type);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public boolean isConstructed() {
		return constructed;
	}

	public void setConstructed(boolean constructed) {
		this.constructed = constructed;
	}

	public int getIdTable() {
		return idTable;
	}

	public StringProperty getIdLigneName() {
		return idLigneName;
	}

	public void setIdLigneName(String idLigneName) {
		this.idLigneName.set(idLigneName);
	}

	public ObservableList<Ligne> getLignes() {
		return lignes;
	}

	public void setLignes(ObservableList<Ligne> lignesTest) {
		this.lignes = lignesTest;
	}

	public BaseDonnee getBdd() {
		return bdd;
	}

}
