package model.tables;

import java.util.ArrayList;

import org.apache.poi.ddf.EscherColorRef.SysIndexProcedure;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ligne;
import model.interfaces.BaseDonnee;
import model.interfaces.PhysicalTable;
import model.interfaces.TableType;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class Personne extends PhysicalTable{
	
	private static final String DEPARTEMENTS = "id_departement";
	private static final String NOM = "nom";
	private static final String PRENOM = "prenom";
	private static final String TELEPHONE = "telephone";
	private static final String EMAIL = "email";
	private static final String ADRESSE = "id_adresse";
	private static final String STATUT = "id_statut";
	
	private Adresse adresse;
	private Departement departement;
	private Statut statut;
	
	//TODO refactoring pour distinguer les classes qui représentent les tables et celles qui représente une instance

	public Personne(BaseDonnee bdd, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, values);
		type.set(TableType.PERSONNE);
	}
	
	public Personne(BaseDonnee bdd, int idTable) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, idTable);
		type.set(TableType.PERSONNE);
	}
	
	public void habillageDonnees() throws ColonneNotfoundException, TableNotFoundException, BadRequestException, DefaultException {
		for (Ligne ligne : lignes) {
			//On enlève l'id
			if (ligne.getData().isColonne(idLigneName.get())) {
				ligne.getData().remove(idLigneName);
				ligne.getColonneNames().remove(idLigneName);
			}
			//On donne l'adresse
			ObservableValue<Object> value = ligne.getData().get(ADRESSE);
			if (value.getValue() != null) {
				System.out.println(value.getValue());
				adresse = new Adresse(bdd, (int) value.getValue());
				ligne.getData().set(ligne.getData().getIndexOf(ADRESSE), 
						new BddValue(ADRESSE, adresse.toString()));
			}
			//On donne le département
			value = ligne.getData().get(DEPARTEMENTS);
			if (value.getValue() != null) {
				int idDepartements = (int) value.getValue();
				bdd.select(new BddColonne(Departements.TABLE_DEPARTEMENT, Departements.ID_DEPARTEMENT));
				bdd.from(Departements.TABLE_DEPARTEMENT);
				bdd.where(new WhereCondition(Departements.TABLE_DEPARTEMENT, Departements.ID_DEPARTEMENTS, BaseDonnee.EGAL, idDepartements));
				String deps = "";
				for (ResultSet res : bdd.execute()) {
					departement = new Departement(bdd, (int) res.get(Departement.ID).getValue());
					deps += departement.toString() + ", ";
				}
				deps = (deps.length() > 0 ) ? deps = deps.substring(0, deps.length()- ", ".length()) :  "";
				ligne.getData().set(ligne.getData().getIndexOf(DEPARTEMENTS), 
						new BddValue(DEPARTEMENTS, deps));
			}
			//On donne le statut
			value = ligne.getData().get(Statut.ID);
			if (value.getValue() != null) {
				statut = new Statut(bdd, (int) value.getValue());
				ligne.getData().set(ligne.getData().getIndexOf(STATUT), 
						new BddValue(STATUT, statut.getNom()));
			}
		}
	}

}
