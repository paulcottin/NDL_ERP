package model.tables;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.interfaces.BaseDonnee;
import model.interfaces.PhysicalTable;
import model.interfaces.TableType;
import model.interfaces.VirtualTable;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class Inscription extends PhysicalTable {

	ObservableList<String> urls;
	
	public Inscription(BaseDonnee bdd, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, values);
		type.set(TableType.INSCRIPTION);
		urls = FXCollections.observableArrayList();
	}
	
	public Inscription(BaseDonnee bdd, int idTable) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, idTable);
		type.set(TableType.INSCRIPTION);
		urls = FXCollections.observableArrayList();
		try {
			constructInscription();
		} catch (DefaultException | TableNotFoundException | BadRequestException | ColonneNotfoundException e) {
			e.printMessage();
		}
	}
	
	private void constructInscription() throws DefaultException, BadRequestException, TableNotFoundException, ColonneNotfoundException{
		bdd.select(new BddColonne("pagesWeb", "url"));
		bdd.from("pagesWeb");
		bdd.where(new WhereCondition("pagesWeb", "id_table", BaseDonnee.EGAL, idTable));
		
		for (ResultSet map : bdd.execute())
			urls.add((String) map.get("url").getValue());
	}
	
	public String toString() {
		return nom.get();
	}

	public ObservableList<String> getUrls() {
		return urls;
	}

	public void setUrls(ObservableList<String> urls) {
		this.urls = urls;
	}

	@Override
	protected void habillageDonnees() throws ColonneNotfoundException, TableNotFoundException, BadRequestException, DefaultException {
		
	}

}
