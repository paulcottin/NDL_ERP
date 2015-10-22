package model.interfaces;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.Ligne;
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
	
	protected void createTable() throws DefaultException, TableNotFoundException, BadRequestException, ColonneNotfoundException {
		//creation dans la table tables
		bdd.insert("tables", initialValues);
		bdd.execute();
		
		bdd.select(new BddColonne("tables", "id_table"));
		bdd.from("tables");
		ArrayList<ResultSet> res = bdd.execute();
		idTable = (int) res.get(res.size()-1).get("id_table").getValue();
		
		//Creation en vrai
		bdd.createPhysicalTable(nom.get(), idLigneName.get());
		bdd.execute();
	}
	
	public void createColonnes(ResultSet resultSet) throws TableNotFoundException, BadRequestException {
		for (BddValue bddValue : resultSet) {
			//TODO : comment fixer le type de données
			bdd.addCol(new BddColonne(nom.get(), bddValue.getColonne()), bdd.getStringType());
			bdd.execute();
		}
		
		
	}
	
	public void open() throws DefaultException, TableNotFoundException, BadRequestException, ColonneNotfoundException {
		lignes.clear();
		//Ouverture de la table et chargement des résultats
		bdd.selectAll();
		bdd.from(nom.get());
		
		for (ResultSet res : bdd.execute()) {
			lignes.add(new Ligne(bdd, idTable, res));
		}
//		habillageDonnees();
	}
	
	protected abstract void habillageDonnees() throws ColonneNotfoundException, TableNotFoundException, BadRequestException, DefaultException;
	
	protected void initTable() throws DefaultException, TableNotFoundException, BadRequestException, ColonneNotfoundException {
		//Chargement des infos de base sur la table
		bdd.select(new BddColonne("tables", "nom_table"), 
				new BddColonne("tables", "famille"), 
				new BddColonne("tables", "type"), 
				new BddColonne("tables", "id_ligne_name"));
		bdd.from("tables");
		bdd.where(new WhereCondition("tables", "id_table", BaseDonnee.EGAL, idTable));
		
		ResultSet res = bdd.execute().get(0);
		nom.set((String) res.get("nom_table").getValue());
		famille.set((String) res.get("famille").getValue());
		type.set((String) res.get("type").getValue());
		idLigneName.set((String) res.get("id_ligne_name").getValue());
		open();
		habillageDonnees();
	}

}
