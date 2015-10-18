package model.tables;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.LigneTest;
import model.interfaces.BaseDonnee;
import model.interfaces.PhysicalTable;
import model.interfaces.TableType;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class Personne extends PhysicalTable{
	
	private static final String DEPARTEMENTS = "id_departements";
	private static final String NOM = "nom";
	private static final String PRENOM = "prenom";
	private static final String TELEPHONE = "telephone";
	private static final String EMAIL = "email";
	private static final String ADRESSE = "id_adresse";
	private static final String STATUT = "id_statut";
	
	private String nom, prenom, portable, telephone, email;
	private Adresse adresse;
	private Departement departement;
	private ObservableList<Statut> statuts;

	public Personne(BaseDonnee bdd, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, values);
		type = TableType.PERSONNE;
//		adresses = FXCollections.observableArrayList();
//		departements = FXCollections.observableArrayList();
		statuts = FXCollections.observableArrayList();
	}
	
	public Personne(BaseDonnee bdd, int idTable) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, idTable);
		type = TableType.PERSONNE;
//		adresses = FXCollections.observableArrayList();
//		departements = FXCollections.observableArrayList();
		statuts = FXCollections.observableArrayList();
		
		
		
		try {
			habillageDonnees();
		} catch (ColonneNotfoundException e) {
			e.printMessage();
		}
	}
	
	private void habillageDonnees() throws ColonneNotfoundException, TableNotFoundException, BadRequestException, DefaultException {
		for (LigneTest ligne : lignesTest) {
			//On enlève l'id
			ligne.getData().remove(idLigneName);
			ligne.getColonneNames().remove(idLigneName);
			//On donne l'adresse
			adresse = new Adresse(bdd, (int) ligne.getData().get(ADRESSE).getValue());
			ligne.getData().set(ligne.getData().getIndexOf(ADRESSE), 
					new BddValue(ADRESSE, adresse.toString()));
			//On donne le département
			//TODO continuer !
//			int idDepartements = (int) ligne.getData().get(DEPARTEMENTS).getValue();
//			bdd.select(new BddColonne(Departements.TABLE_DEPARTEMENT, Departements.ID_DEPARTEMENT));
//			bdd.from(Departements.TABLE_DEPARTEMENT);
//			bdd.where(new WhereCondition(Departements.TABLE_DEPARTEMENT, Departements.ID_DEPARTEMENTS, BaseDonnee.EGAL, idDepartements));
//			String deps = "";
//			for (ResultSet res : bdd.execute()) {
//				departement = new Departement(bdd, (int) res.get(Departement.ID).getValue());
//				deps += departement.toString() + ", ";
//			}
//			deps = (deps.length() > 0 ) ? deps = deps.substring(0, deps.length()- ", ".length()) :  "";
//			ligne.getData().set(ligne.getData().getIndexOf(DEPARTEMENTS), 
//					new BddValue(DEPARTEMENTS, deps));
			//On donne le statut
		}
	}

}
