package model;

import java.util.ArrayList;
import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.interfaces.BaseDonnee;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class LigneTest {
	
	private ResultSet data;
	private ObservableList<String> colonneNames;
	private BaseDonnee bdd;
	private int idLigne, idTable;
	private String tableName, idLigneName;
	
	public LigneTest(BaseDonnee bdd, int idTable, ArrayList<BddValue> values) throws TableNotFoundException, BadRequestException, ColonneNotfoundException {
		this.bdd = bdd;
		this.idTable = idTable;
		bdd.select(new BddColonne("tables", "id_table"), 
				new BddColonne("tables", "nom_table"),
				new BddColonne("tables", "id_ligne_name"));
		bdd.from("tables");
		bdd.where(new WhereCondition("tables", "id_table", BaseDonnee.EGAL, idTable));
		ResultSet res =  bdd.execute().get(0);
		this.idTable = (int) res.get("id_table").getValue();
		tableName = (String) res.get("table_name").getValue();
		colonneNames = FXCollections.observableArrayList();
		createLigne(values);
	}
	
	public LigneTest(BaseDonnee bdd, int idTable, ResultSet values) {
		this.bdd = bdd;
		this.idTable = idTable;
		this.data = new ResultSet();
		colonneNames = FXCollections.observableArrayList();
		for (BddValue bddValue : values) {
			if (!colonneNames.contains(bddValue.getColonne()))
				colonneNames.add(bddValue.getColonne());
			data.add(bddValue);
		}
	}
	
	public LigneTest(BaseDonnee bdd, int idTable, int idLigne) throws TableNotFoundException, BadRequestException {
		this.bdd = bdd;
		this.idTable = idTable;
		this.idLigne = idLigne;
		colonneNames = FXCollections.observableArrayList();
		openData();
	}
	
	private void createLigne(ArrayList<BddValue> values) throws TableNotFoundException, BadRequestException, ColonneNotfoundException {
		//Récupérer les noms de colonnes
		for (BddValue v : values) {
			if (!colonneNames.contains(v.getColonne()))
				colonneNames.add(v.getColonne());
		}
		
		//Insertion
		bdd.insert(tableName, values);
		bdd.from(tableName);
		bdd.execute();
		
		//Récupération de l'idLigne
		bdd.select(new BddColonne(tableName, idLigneName));
		bdd.from(tableName);
		ArrayList<ResultSet> res = bdd.execute();
		idLigne = (int) res.get(res.size()-1).get(idLigneName).getValue();
	}
	
	private void openData() throws TableNotFoundException, BadRequestException {
		//TODO : Gérer les colonnes qui ne viennent pas directement de la table
		bdd.selectAll();
		bdd.from(tableName);
		bdd.where(new WhereCondition(tableName, idLigneName, BaseDonnee.EGAL, idLigne));
		for (BddValue res : bdd.execute().get(0)) {
			if (!colonneNames.contains(res.getColonne()))
				colonneNames.add(res.getColonne());
			data.add(res);
		}
		
	}
	
	public String toString() {
		String s = "";
		for (BddValue bddValue : data) {
			s += "\t"+bddValue.getColonne()+" => "+bddValue.getValue().getValue() + "\n";
		}
		return s;
	}

	public ResultSet getData() {
		return data;
	}

	public void setData(ResultSet data) {
		this.data = data;
	}

	public ObservableList<String> getColonneNames() {
		return colonneNames;
	}

	public void setColonneNames(ObservableList<String> colonneNames) {
		this.colonneNames = colonneNames;
	}

	public int getIdTable() {
		return idTable;
	}

	public void setIdTable(int idTable) {
		this.idTable = idTable;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getIdLigneName() {
		return idLigneName;
	}

	public void setIdLigneName(String idLigneName) {
		this.idLigneName = idLigneName;
	}

	public int getIdLigne() {
		return idLigne;
	}

}
