package model.tables;

import exceptions.BadRequestException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import utils.BddColonne;
import utils.WhereCondition;

public class Statut {

	public static final String TABLE_STATUT = "statut";
	public static final String ID = "id_statut";
	
	private static final String NOM = "nom";
	
	private BaseDonnee bdd;
	private int id;
	private String nom;
	
	public Statut(BaseDonnee bdd, int id) throws TableNotFoundException, BadRequestException {
		this.bdd = bdd;
		this.id = id;
		
		bdd.select(new BddColonne(TABLE_STATUT, NOM));
		bdd.from(TABLE_STATUT);
		bdd.where(new WhereCondition(TABLE_STATUT, ID, BaseDonnee.EGAL, id));
		nom = (String) bdd.execute().get(0).get(NOM).getValue();
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
}
