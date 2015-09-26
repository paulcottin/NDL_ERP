package model.connecteurs;

import java.io.File;
import java.io.IOException;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;

import exceptions.DefaultException;
import exceptions.MyException;

public class AccessConnector {
	
	public static File databaseFile;
	public static Database db;
	public static Table table;

	public AccessConnector(File databaseFile) {
		AccessConnector.databaseFile = databaseFile;
		db = null;
		table = null;
	}

	public void queryLignes() throws MyException {
		
	}

	public static void connect() throws DefaultException {
		try {
			db = DatabaseBuilder.open(databaseFile);
		} catch (IOException e) {
			throw new DefaultException("Le fichier de connexion est introuvable ! \n"
					+ "(Il est peut être ouvert dans Access, dans ce cas, il faut fermer le programme Access)");
		}
		if (db == null) throw new DefaultException("Impossible de se connecter à la base de données !");	
	}
	
	public static void openTable(String tableName) throws DefaultException {
		try {
			table = db.getTable(tableName);
		} catch (IOException e) {
			throw new DefaultException("Impossible d'ouvrir la table \""+tableName+"\"");
		}
		if (table == null) throw new DefaultException("L'ouverture de la table \""+tableName+"\" a échoué !");
	}
	
	public static void closeTable() {
		table = null;
	}

}
