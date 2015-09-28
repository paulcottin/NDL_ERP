package model.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import model.Ligne;
import model.connecteurs.AccessConnector;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.Trio;
import utils.WhereCondition;

public abstract class Table {

	protected int idTable;
	protected String nom, famille, type;
	protected ObservableList<Ligne> lignes;
	protected ObservableList<Option> options;
	protected boolean constructed;
	protected BaseDonnee bdd;

	public Table(BaseDonnee accessConnector) {
		this.bdd = accessConnector;
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();
		constructed = false;
		try {
			createTable();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public Table(BaseDonnee accessConnector, int idTable) {
		this.bdd = accessConnector;
		this.idTable = idTable;
		lignes = FXCollections.observableArrayList();
		options = FXCollections.observableArrayList();
		constructed = false;
		try {
			initTable();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	private void initTable() throws DefaultException, TableNotFoundException, BadRequestException {
		bdd.select(new BddColonne("tables", "nom_table"), 
				new BddColonne("tables", "famille"), 
				new BddColonne("tables", "type"));
		bdd.from("tables");
		bdd.where(new WhereCondition("tables", "id_table", BaseDonnee.EGAL, idTable));

		ResultSet res = bdd.execute().get(0);
		nom = (String) res.get("nom_table");
		famille = (String) res.get("famille");
		type = (String) res.get("type"); 
	}

	public void construct() throws DefaultException, TableNotFoundException, BadRequestException {
		try {
			bdd.select(new BddColonne("donnees", "id_ligne"));
			bdd.from("donnees");
			bdd.where(new WhereCondition("donnees", "id_table", BaseDonnee.EGAL, idTable));

			ArrayList<Integer> ids = new ArrayList<Integer>();
			for (ResultSet m : bdd.execute()) 
				if (!ids.contains(m.get("id_ligne")))
					ids.add((Integer) m.get("id_ligne"));

			for (Integer integer : ids) 
				lignes.add(new Ligne(bdd, integer, idTable));

		} catch (ClassCastException e) {
			throw new DefaultException("Erreur de conversion en entier");
		}
		constructed = true;
	}

	protected void createTable() throws DefaultException, TableNotFoundException, BadRequestException {
		bdd.insert("tables", new ArrayList<BddValue>());
		bdd.execute();

		bdd.select(new BddColonne("tables", "id_table"));
		bdd.from("tables");

		int max = 0;
		for (ResultSet map : bdd.execute()) {
			if ((int) map.get("id_table") > max)
				max = (int) map.get("id_table");
		}
		idTable = max;	
	}

	protected void update(String colonneName, String value) throws DefaultException, BadRequestException, TableNotFoundException {
		bdd.update(new BddColonne("tables", colonneName), value);
		bdd.where(new WhereCondition("tables", "id_table", BaseDonnee.EGAL, idTable));
		bdd.execute();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		try {
			this.nom = nom;
			update("nom_table", nom);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public String getFamille() {
		return famille;
	}

	public void setFamille(String famille) {
		try {
			this.famille = famille;
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

	public ObservableList<Ligne> getLignes() {
		return lignes;
	}

	public void setLignes(ObservableList<Ligne> lignes) {
		this.lignes = lignes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		try {
			this.type = type;
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

}
