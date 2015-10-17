package model.interfaces;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.LigneTest;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public abstract class PhysicalTable extends Table {

	
	public PhysicalTable(BaseDonnee accessConnector, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(accessConnector, values);
	}
	
	public PhysicalTable(BaseDonnee bdd, int idTable) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, idTable);
	}
	
	protected void createTable() throws DefaultException, TableNotFoundException, BadRequestException {
		//creation dans la table tables
		bdd.insert("tables", initialValues);
		bdd.execute();
		
		bdd.select(new BddColonne("tables", "id_table"));
		bdd.from("tables");
		ArrayList<ResultSet> res = bdd.execute();
		idTable = (int) res.get(res.size()-1).get("id_table");
		
		//Creation en vrai
		bdd.createPhysicalTable(nom, idLigneName);
		bdd.execute();
	}
	
	public void createColonnes(ResultSet resultSet) throws TableNotFoundException, BadRequestException {
		System.out.println("create colonne ("+nom+"): \n"+resultSet.toString());
		for (BddValue bddValue : resultSet) {
			//TODO : comment fixer le type de données
			bdd.addCol(new BddColonne(nom, bddValue.getColonne()), bdd.getStringType());
			bdd.execute();
		}
		
		
	}
	
	public void open() throws DefaultException, TableNotFoundException, BadRequestException {
		//Ouverture de la table et chargement des résultats
		bdd.selectAll();
		bdd.from(nom);
		
		for (ResultSet res : bdd.execute()) {
			lignesTest.add(new LigneTest(bdd, idTable, res));
		}
	}
	
	protected void initTable() throws DefaultException, TableNotFoundException, BadRequestException {
		//Chargement des infos de base sur la table
		bdd.select(new BddColonne("tables", "nom_table"), 
				new BddColonne("tables", "famille"), 
				new BddColonne("tables", "type"), 
				new BddColonne("tables", "id_ligne_name"));
		bdd.from("tables");
		bdd.where(new WhereCondition("tables", "id_table", BaseDonnee.EGAL, idTable));
		
		ResultSet res = bdd.execute().get(0);
		nom = (String) res.get("nom_table");
		famille = (String) res.get("famille");
		type = (String) res.get("type");
		idLigneName = (String) res.get("id_ligne_name");
	}

}
