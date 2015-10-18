package model.tables;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import utils.BddColonne;
import utils.BddValue;
import utils.WhereCondition;

public class Departement {

	public static final String TABLE_DEPARTEMENT = "departement";
	public static final String ID = "id_dep";
	
	private static final String NOM = "nom";
	private static final String ID_DIRECTEUR = "id_directeur";
	
	BaseDonnee bdd;
	String nom;
	Personne directeur;
	int id;
	
	public Departement(BaseDonnee bdd, String nom, int idDirecteur) throws TableNotFoundException, DefaultException, BadRequestException {
		this.bdd = bdd;
		this.nom = nom;
		this.directeur = new Personne(bdd, idDirecteur);
	}
	
	public Departement(BaseDonnee bdd, int id) throws TableNotFoundException, BadRequestException, DefaultException, ColonneNotfoundException {
		this.bdd = bdd;
		this.id = id;
		
		bdd.select(new BddColonne(TABLE_DEPARTEMENT, NOM), new BddColonne(TABLE_DEPARTEMENT, ID_DIRECTEUR));
		bdd.from(TABLE_DEPARTEMENT);
		bdd.where(new WhereCondition(TABLE_DEPARTEMENT, ID, BaseDonnee.EGAL, id));
		
		for (BddValue value : bdd.execute().get(0)) {
			switch (value.getColonne()) {
			case NOM :
				nom = (String) value.getValue().getValue();
				break;
			case ID_DIRECTEUR :
				int idDir = (int) value.getValue().getValue();
				//TODO La classe Personne définie une table de bd de type personne et non une personne, à revoir
//				directeur = new Personne(bdd, idDir);
				break;
			default:
				throw new ColonneNotfoundException(TABLE_DEPARTEMENT+"."+value.getColonne());
			}
		}
	}
	
	public String toString() {
		return nom;
	}
}
