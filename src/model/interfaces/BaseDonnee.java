package model.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.ConnexionException;
import exceptions.IdentificationException;
import exceptions.TableNotFoundException;
import javafx.util.Pair;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.Trio;
import utils.WhereCondition;
/**
 * Les différentes méthodes servent à donner les paramères d'une requête exécutée ensuite par la
 * méthode execute(); qui renvoie les résultats (ou une liste vide si la requête n'en fournie pas)
 * @author polob
 *
 */
public interface BaseDonnee {
	
	//Operations pour le WHERE
	public static final String EGAL = "=";
	public static final String INFERIEUR = "<";
	public static final String INFERIEUR_EGAL = "<=";
	public static final String SUPERIEUR = ">";
	public static final String SUPERIEUR_EGAL = ">=";
	public static final String DIFERRENT  = "!=";
	public static final String STRING_COMMENCE_PAR = "*=";
	public static final String STRING_FINI_PAR = "=*";
	public static final String STRING_CONTIENT = "*=*";
	
	//Types d'opérations : SELECT, UPDATE, DELETE...
	public static final String SELECT = "select";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String INSERT = "insert";
	public static final String ADD_COL = "add_col";
	public static final String REMOVE_COL = "remove_col";

	public void connect(String user, String mdp) throws IdentificationException, ConnexionException;
	public void connect(File file, String mdp) throws IdentificationException, ConnexionException, FileNotFoundException;
	
	public void select(BddColonne... selects);
	public void from(String... froms) throws TableNotFoundException;
	public void where(WhereCondition... wheres);
	
	public void delete(String inTableName) throws TableNotFoundException;
	public void update(BddColonne tableNameColonneName, Object value) throws TableNotFoundException;
	public void insert(String tableName, Collection<BddValue> values) throws TableNotFoundException;
	public void addCol(String colonneName, String typeDonnees);
	public void removeCol(String colonneName) throws ColonneNotfoundException;
	
	public ArrayList<ResultSet> execute() throws BadRequestException;
}
