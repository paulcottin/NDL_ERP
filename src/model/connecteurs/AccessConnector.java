package model.connecteurs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import exceptions.BadRequestException;
import exceptions.ConnexionException;
import exceptions.IdentificationException;
import exceptions.TableNotFoundException;
import javafx.util.Pair;
import model.interfaces.BaseDonnee;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.Trio;
import utils.WhereCondition;

public class AccessConnector implements BaseDonnee{

	private File databaseFile;
	private Database db;
	private Table table;
	private String mdp;
	private ArrayList<BddColonne> tablesColonnes;
	private ArrayList<BddValue> colonnesValues;
	private ArrayList<Table> fromTables;
	private ArrayList<WhereCondition> whereConditions;
	private String typeOperation;
	private BddColonne tableColonne;
	private BddValue aRow;
	private Object value;
	private String tableName;

	public AccessConnector() {
		tablesColonnes = new ArrayList<BddColonne>();
		fromTables = new ArrayList<Table>();
		whereConditions = new ArrayList<WhereCondition>();
		colonnesValues = new ArrayList<BddValue>();
	}

	@Override
	public void connect(String user, String mdp) throws IdentificationException, ConnexionException {
		throw new ConnexionException("Impossible de se connecter avec ce mode (user/password)");
	}

	@Override
	public void connect(File file, String mdp) throws IdentificationException, ConnexionException, FileNotFoundException {
		databaseFile = file;
		this.mdp = mdp;
		try {
			db = DatabaseBuilder.open(file);
		} catch (IOException e) {
			throw new ConnexionException("Le fichier de connexion est introuvable ! \n"
					+ "(Il est peut être ouvert dans Access, dans ce cas, il faut fermer le programme Access)");
		}
		if (db == null) throw new ConnexionException("Impossible de se connecter à la base de données !");

	}

	@Override
	public void select(BddColonne... selects) {
		typeOperation = BaseDonnee.SELECT;
		tablesColonnes.clear();
		for (BddColonne colonne : selects) {
			tablesColonnes.add(colonne);
		}
	}

	@Override
	public void from(String... froms) throws TableNotFoundException {
		fromTables.clear();
		for (String string : froms) {
			try {
				if (!db.getTableNames().contains(string))
					throw new TableNotFoundException(string);
				fromTables.add(db.getTable(string));
			} catch (IOException e) {
				throw new TableNotFoundException(string);
			}
		}
	}

	@Override
	public void where(WhereCondition... wheres) {
		whereConditions.clear();
		for (WhereCondition trio : wheres) {
			whereConditions.add(trio);
		}
	}

	@Override
	public void delete(String inTableName) throws TableNotFoundException {
		typeOperation = BaseDonnee.DELETE;
		try {
			table = db.getTable(inTableName);
			setTableName(inTableName);
		} catch (IOException e) {
			throw new TableNotFoundException(inTableName);
		}
	}

	@Override
	public void update(BddColonne tableNameColonneName, Object value) throws TableNotFoundException {
		typeOperation = BaseDonnee.UPDATE;
		tableColonne = tableNameColonneName;
		this.value = value;
		try {
			if (!db.getTableNames().contains(tableNameColonneName.getTableName()))
				throw new TableNotFoundException("Table "+tableNameColonneName.getTableName());
			this.table = db.getTable(tableNameColonneName.getTableName());
			this.setTableName(tableNameColonneName.getTableName());
		} catch (IOException e) {
			throw new TableNotFoundException(tableNameColonneName.getKey());
		}
	}

	@Override
	public void insert(String tableName, Collection<BddValue> values) throws TableNotFoundException {
		typeOperation = BaseDonnee.INSERT;
		try {
			table = db.getTable(tableName);
			this.setTableName(tableName);
		} catch (IOException e) {
			throw new TableNotFoundException(tableName);
		}
		for (BddValue v : values) {
			colonnesValues.add(v);
		}
	}

	@Override
	public ArrayList<ResultSet> execute() throws BadRequestException {
		ArrayList<ResultSet> result = new ArrayList<ResultSet>();
		/** 
		 * SELECT  
		 **/
		if (typeOperation.equals(BaseDonnee.SELECT)) {
			//Remplissage de la liste de résultats
			System.out.println("nombre de résultats : "+selectHelper().size());
			for (Row row : selectHelper()) {
				ResultSet map = new ResultSet();
				for (BddColonne colonne : tablesColonnes) 
					map.add(new BddValue(colonne.getColonneName(), row.get(colonne.getColonneName())));
				result.add(map);
			}
		}
		/**
		 * UPDATE
		 */
		else if (typeOperation.equals(BaseDonnee.UPDATE)) {
			//Application des changements
			for (Row row : selectByWhere()) {
				System.out.println("replace "+tableColonne.getColonneName()+" by "+value.toString());
				try {
					row.replace(tableColonne.getColonneName(), value);
					db.getTable(tableColonne.getTableName()).updateRow(row);
				} catch (IOException e) {
					throw new BadRequestException("Mise à jour de la valeur \""+tableColonne.getValue()+"\" impossible !");
				}
			}
		}

		/**
		 * INSERT
		 */
		else if (typeOperation.equals(BaseDonnee.INSERT)) {
			try {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				for (BddValue pair : colonnesValues) {
					map.put(pair.getColonne(), pair.getValue());
				}
				//TODO : Le problème vient de l'insertion via une map, ça ne doit pas être correct, 
				//regarder la doc
				table.addRowFromMap(map);
			} catch (IOException e) {
				throw new BadRequestException("Erreur lors de l'insertion de valeurs dans la table \""+table.getName()+"\"");
			}
		}
		/**
		 * DELETE
		 */
		else if (typeOperation.equals(BaseDonnee.DELETE)) {
			try {
				for (Row r : selectByWhere()) {
					table.deleteRow(r);
				}

			} catch (IOException e) {
				throw new BadRequestException("Erreur lors de la suppression d'une ligne dans la table \""+aRow.getKey()+"\"");
			}
		}
		//Remise à zéro des composantes de la requête
		colonnesValues.clear();
		tablesColonnes.clear();
		fromTables.clear();
		whereConditions.clear();
		typeOperation = null;
		value = null;
		return result;
	}

	private ArrayList<Row> selectHelper() throws BadRequestException {
		ArrayList<Column> cols = new ArrayList<Column>();

		//création des colonnes à sélectionner
		for (Pair<String,String> pair : tablesColonnes) 
			for (Table table : fromTables)  {
				if (table.getName().equals(pair.getKey()))
					cols.add(table.getColumn(pair.getValue()));
			}
		
		//différentes sélection du Where
		ArrayList<Row> rowsSelected = new ArrayList<Row>();

		//Si il n'y a pas de condition, on met toutes les lignes
		if (whereConditions.size() == 0) {
			try {
				for (Table table : fromTables) {
					Cursor c = CursorBuilder.createCursor(table);
					while (c.getNextRow() != null) {
						rowsSelected.add(c.getCurrentRow());
					}
				}
			} catch (IOException e) {
				throw new BadRequestException("Erreur dans le traitement de la requête");
			}
		}

		try {
			for (WhereCondition trio : whereConditions) {
				Cursor c = CursorBuilder.createCursor(db.getTable(trio.getTableName()));
				Column col = db.getTable(trio.getTableName()).getColumn(trio.getColonneName());
				while (c.findNextRow(col, trio.getValue())) {
					rowsSelected.add(c.getCurrentRow());					
				}
			}
		} catch (IOException e) {
			throw new BadRequestException("Erreur dans le traitement de la requête");
		}

		//récupération des rows qui correspondent au bon nombre de conditions
		ArrayList<Row> rowAfterAnd = new ArrayList<Row>();		
		for (Row row : rowsSelected) {
			int nbApparition = 0;
			for (Row row1 : rowsSelected) {
				if (row.equals(row1))
					nbApparition++;
			}
			if (whereConditions.size() > 0) {
				if (nbApparition == whereConditions.size() && !rowAfterAnd.contains(row))
					rowAfterAnd.add(row);
			}
			else {
				if (!rowAfterAnd.contains(row))
					rowAfterAnd.add(row);
			}
		}

		return rowAfterAnd;
	}

	private ArrayList<Row> selectByWhere() throws BadRequestException {
		//différentes sélection du Where
		ArrayList<Row> rowsSelected = new ArrayList<Row>();

		try {
			for (Trio<String,String,String,Object> trio : whereConditions) {
				Cursor c = CursorBuilder.createCursor(db.getTable(trio.getTableName()));
				Column col = db.getTable(trio.getTableName()).getColumn(trio.getColonneName());
				while (c.findNextRow(col, trio.getValue())) {
					rowsSelected.add(c.getCurrentRow());					
				}
			}
		} catch (IOException e) {
			throw new BadRequestException("Erreur dans le traitement de la requête");
		}

		//récupération des rows qui correspondent au bon nombre de conditions
		ArrayList<Row> rowAfterAnd = new ArrayList<Row>();
		for (Row row : rowsSelected) {
			int nbApparition = 0;
			for (Row row1 : rowsSelected) {
				if (row.equals(row1))
					nbApparition++;
			}
			if (nbApparition == whereConditions.size() && !rowAfterAnd.contains(row))
				rowAfterAnd.add(row);
		}

		return rowAfterAnd;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
