package model.interfaces;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.Ligne;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public abstract class VirtualTable extends Table{

	public VirtualTable(BaseDonnee bdd, ArrayList<BddValue> values) {
		super(bdd, values);
	}
	
	public VirtualTable(BaseDonnee bdd, int idTable) {
		super(bdd, idTable);
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
	
	public void createColonnes(ResultSet colonnesNames) throws TableNotFoundException, BadRequestException {
		//TODO
	}
	
	public void open() throws DefaultException, TableNotFoundException, BadRequestException {
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
	
	protected void initTable() throws DefaultException, TableNotFoundException, BadRequestException {
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

}
