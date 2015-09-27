package model.tables;

import java.io.IOException;
import java.util.HashMap;

import com.healthmarketscience.jackcess.Column;
import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.CursorBuilder;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import model.connecteurs.AccessConnector;
import model.interfaces.BaseDonnee;
import model.interfaces.Table;
import utils.BddColonne;
import utils.ResultSet;
import utils.Trio;
import utils.WhereCondition;

public class Inscription extends Table{

	ObservableList<String> urls;
	
	public Inscription(BaseDonnee bdd) {
		super(bdd);
		urls = FXCollections.observableArrayList();
	}
	
	public Inscription(BaseDonnee bdd, int idTable) {
		super(bdd, idTable);
		urls = FXCollections.observableArrayList();
		try {
			constructInscription();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}
	
	private void constructInscription() throws DefaultException, BadRequestException, TableNotFoundException{
		bdd.select(new BddColonne("pagesWeb", "url"));
		bdd.from("pagesWeb");
		bdd.where(new WhereCondition("pagesWeb", "id_table", BaseDonnee.EGAL, idTable));
		
		for (ResultSet map : bdd.execute())
			urls.add((String) map.get("url"));
	}
	
	public String toString() {
		return nom;
	}

	public ObservableList<String> getUrls() {
		return urls;
	}

	public void setUrls(ObservableList<String> urls) {
		this.urls = urls;
	}

}
