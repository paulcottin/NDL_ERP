package model.connecteurs;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.ConnexionException;
import exceptions.IdentificationException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import net.ucanaccess.jdbc.UcanaccessDriver;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class Access implements BaseDonnee{

	Connection con; 
	String requete, typeOperation;
	ArrayList<Object> dataForPreparedStatement;

	public Access() {
		requete = "";
		typeOperation = "";
		dataForPreparedStatement = new ArrayList<Object>();
	}

	@Override
	public void connect(String user, String mdp) throws IdentificationException, ConnexionException {

	}

	@Override
	public void connect(File file, String mdp) throws IdentificationException, ConnexionException, FileNotFoundException {
		String url = UcanaccessDriver.URL_PREFIX + file.getAbsolutePath()+";newDatabaseVersion=V2003";

		try {
			con =  DriverManager.getConnection(url, "", mdp);
		} catch (SQLException e) {
			new ConnexionException("Impossible de se connecter à la base de donnnées");
		}
	}

	@Override
	public void select(BddColonne... selects) {
		typeOperation = BaseDonnee.SELECT;
		requete = "select ";
		for (BddColonne bddColonne : selects) {
			requete += bddColonne.getColonneName()+ ", ";
		}
		//On enlève la dernière virgule
		requete = requete.substring(0, requete.length()-2)+" ";
	}

	@Override
	public void selectAll() {
		typeOperation = BaseDonnee.SELECT;
		requete = "select * ";
	}

	@Override
	public void from(String... froms) throws TableNotFoundException {
		requete += "from ";
		for (String string : froms) {
			requete += string + ", ";
		}
		//On enlève la dernière virgule
		requete = requete.substring(0, requete.length()-2)+" ";
	}

	@Override
	public void where(WhereCondition... wheres) {
		requete += "where ";
		for (WhereCondition whereCondition : wheres) {
			if (whereCondition.getOperand().equals(BaseDonnee.EGAL))
				requete += whereCondition.getColonneName() + " = " + enveloppementConditions(whereCondition.getValue()) + " AND ";
		}
		//On enlève le dernier AND
		requete = requete.substring(0, requete.length()-" AND ".length())+" ";
	}

	private String enveloppementConditions(Object value) {
		if (value.toString().matches("[0-9]+"))
			return (int) value + "";
		else 
			return "'"+value.toString()+"'";
	}

	@Override
	public void delete(String inTableName) throws TableNotFoundException {
		typeOperation = BaseDonnee.DELETE;
		requete = "DELETE FROM "+inTableName+" ";
	}

	@Override
	public void update(BddColonne tableNameColonneName, Object value) throws TableNotFoundException {
		typeOperation = BaseDonnee.UPDATE;
		requete += "update "+tableNameColonneName.getTableName()+" set "+tableNameColonneName.getColonneName()+ 
				" = " + value.toString()+ " ";
	}

	@Override
	public void insert(String tableName, Collection<BddValue> values) throws TableNotFoundException {
		typeOperation = BaseDonnee.INSERT;
		requete = "INSERT INTO "+tableName +"(";
		for (BddValue bddValue : values) {
			requete += bddValue.getColonne()+", ";
		}
		//On enlève la dernière virgule
		requete = requete.substring(0, requete.length()-2);
		requete += ") VALUES (";
		for (BddValue bddValue : values) {
			requete += enveloppementConditions(bddValue.getValue())+", ";
		}
		//On enlève la dernière virgule
		requete = requete.substring(0, requete.length()-2);
		requete += ")";
	}

	@Override
	public void createPhysicalTable(String tableName, String idLigneName) throws BadRequestException {
		typeOperation = BaseDonnee.CREATE_TABLE;
		requete = "create table "+tableName + "("+idLigneName.toUpperCase()+" INTEGER PRIMARY KEY)";
	}

	@Override
	public void addCol(BddColonne colonne, String typeDonnees) throws BadRequestException {
		typeOperation = BaseDonnee.ADD_COL;
		requete = "alter table "+colonne.getTableName()+" add "+colonne.getColonneName()+" "+typeDonnees;
	}

	@Override
	public void removeCol(String colonneName) throws ColonneNotfoundException {
		// TODO à implémenter quand la fonctionnalité sera développée

	}

	@Override
	public ArrayList<ResultSet> execute() throws BadRequestException {
		System.out.println(requete);
		ArrayList<ResultSet> results = new ArrayList<ResultSet>();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			java.sql.ResultSet res = null;
			if (typeOperation.equals(BaseDonnee.INSERT) || typeOperation.equals(BaseDonnee.CREATE_TABLE)
					|| typeOperation.equals(BaseDonnee.ADD_COL) || typeOperation.equals(BaseDonnee.DELETE))
				stmt.executeUpdate(requete);
			else
				res = stmt.executeQuery(requete);
			if (res != null) {
				while (res.next()) {
					int nbCol = res.getMetaData().getColumnCount();
					ResultSet set = new ResultSet();
					for (int i = 1; i <= nbCol; i++) {
						set.add(new BddValue(res.getMetaData().getColumnName(i), res.getObject(i)));
					}
					results.add(set);
				}
			}
			stmt.close();
		} catch (SQLException e) {
			throw new BadRequestException("Erreur lors de l'éxécution de la requête\n"+requete, e);
		}

		requete = "";
		return results;
	}

	@Override
	public String getStringType() {
		return "TEXT";
	}

	@Override
	public String getIntType() {
		return "INTEGER";
	}

	@Override
	public String getDateType() {
		return "DATE/TIME";
	}

	@Override
	public String getDoubleType() {
		return "DOUBLE";
	}

	@Override
	public String getLargeStringType() {
		return "MEMO";
	}

	@Override
	public String getBooleanType() {
		return "YES/NO";
	}
}
