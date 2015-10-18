package model.tables;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class Adresse {
	
	public final static String CHAMP_ADRESSE = "id_adresse";
	public final static String TABLE_ADRESSE = "adresses";
	public final static String ID_ADRESSE = "id_adresse";
	
	private final static String RUE = "rue";
	private final static String VILLE = "ville";
	private final static String PAYS = "pays";
	private final static String CEDEX = "cedex";
	private final static String NUMERO = "numero";
	private final static String CODE_POSTAL = "code_postal";
	

	private BaseDonnee bdd;
	private String rue, ville, pays, cedex;
	private int numero, id, codePostal;
	
	
	public Adresse(BaseDonnee bdd) {
		this.bdd = bdd;
		this.rue = "";
		this.ville = "";
		this.pays = "";
		this.cedex = "";
		this.numero = -1;
		this.id = -1;
		this.codePostal = -1;
	}
	
	public Adresse(BaseDonnee bdd, int numero, String rue, String ville, int codePostal, String pays, String cedex) {
		this.bdd = bdd;
		this.rue = rue;
		this.ville = ville;
		this.pays = pays;
		this.cedex = cedex;
		this.numero = numero;
		this.id = -1;
		this.codePostal = codePostal;
		//TODO enregistrement de l'adresse dans la bdd
	}
	
	public Adresse(BaseDonnee bdd, int id) throws BadRequestException, TableNotFoundException, ColonneNotfoundException {
		this.bdd = bdd;
		bdd.selectAll();
		bdd.from(TABLE_ADRESSE);
		bdd.where(new WhereCondition(TABLE_ADRESSE, CHAMP_ADRESSE, BaseDonnee.EGAL, id));
		for (BddValue value : bdd.execute().get(0)) {
			switch (value.getColonne()) {
			case ID_ADRESSE:
				break;
			case NUMERO:
				numero = (int) value.getValue().getValue();
				break;
			case RUE:
				rue = (String) value.getValue().getValue();
				break;
			case VILLE:
				ville = (String) value.getValue().getValue();
				break;
			case PAYS:
				pays = (String) value.getValue().getValue();
				break;
			case CEDEX:
				cedex = (String) value.getValue().getValue();
				break;
			case CODE_POSTAL:
				codePostal = (int) value.getValue().getValue();
				break;
			default:
				throw new ColonneNotfoundException(TABLE_ADRESSE+"."+value.getColonne());
			}
		}
	}
	
	//TODO getter et setter avec update dans la bdd
	
	public String toString() {
		return numero+" "+rue+" "+codePostal+" "+ville+" "+pays+ ((cedex != "" && cedex != null)? " Cedex "+cedex : "");
	}
}
