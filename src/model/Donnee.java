package model;

import java.util.ArrayList;
import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.interfaces.BaseDonnee;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;

public class Donnee {

	public static final String INT = "int", BOOLEAN = "boolean", STRING = "string", ACTION = "action";

	int id, idLigne, idTable;
	StringProperty value;
	String nomColonne, type;
	boolean visible;
	BaseDonnee bdd;

	public Donnee(BaseDonnee bdd, int idLigne, int idTable) {
		this.bdd = bdd;
		this.idLigne = idLigne;
		this.idTable = idTable;
		nomColonne = null;
		type = null;
		visible = true;
		value = new SimpleStringProperty();
		try {
			createDonnee();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public Donnee(BaseDonnee bdd, int id, int idLigne, int idTable) {
		this.id = id;
		this.bdd = bdd;
		nomColonne = null;
		type = null;
		visible = true;
		value = new SimpleStringProperty();
		try {
			constructLigne();
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	private void constructLigne() throws DefaultException, TableNotFoundException, BadRequestException {
		bdd.select(new BddColonne("donnees", "id_ligne"), 
				new BddColonne("donnees", "id_table"), 
				new BddColonne("donnees", "nom_colonne"), 
				new BddColonne("donnees", "visible"), 
				new BddColonne("donnees", "valeur"), 
				new BddColonne("donnees", "type"));
		bdd.from("donnees");
		bdd.where(new WhereCondition("donnees", "id", BaseDonnee.EGAL, id));

		ResultSet res = bdd.execute().get(0);

		idLigne = (int) res.get("id_ligne").getValue();
		idTable = (int) res.get("id_table").getValue();
		nomColonne  = (String) res.get("nom_colonne").getValue();
		visible = (boolean) res.get("visible").getValue();
		value.set((String) res.get("valeur").getValue());

		String accessType = (String) res.get("type").getValue();
		switch (accessType) {
		case INT:
			this.type = INT;
			break;
		case STRING:
			this.type = STRING;
			break;
		case BOOLEAN:
			this.type = BOOLEAN;
			break;
		case ACTION:
			this.type = ACTION;
			break;

		default:
			throw new DefaultException("Type \""+accessType+"\"inconnu !");
		}

	}

	private void createDonnee() throws DefaultException, BadRequestException, TableNotFoundException {
		ArrayList<BddValue> insert = new ArrayList<BddValue>();
		insert.add(new BddValue("id_table", idTable));
		insert.add(new BddValue("id_ligne", idLigne));
		
		bdd.insert("donnees", insert);
		bdd.execute();
		
		bdd.select(new BddColonne("donnees", "id"));
		bdd.from("donnees");
		ArrayList<ResultSet> res = bdd.execute();
		id = (int) res.get(res.size()-1).get("id").getValue();
	}

	private void update(String colonneName, Object value) throws DefaultException, BadRequestException, TableNotFoundException{
		bdd.update(new BddColonne("donnees", colonneName), value);
		bdd.where(new WhereCondition("donnees", "id", BaseDonnee.EGAL, id));
		bdd.execute();
	}

	public StringProperty getValue() {
		return value;
	}

	public void setValue(StringProperty value) {
		this.value = value;
		try {
			update("valeur", value.get());
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public String getNomColonne() {
		return nomColonne;
	}

	public void setNomColonne(String nomColonne) {
		this.nomColonne = nomColonne;
		try {
			update("nom_colonne", nomColonne);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		try {
			update("type", type);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		try {
			update("visible", visible);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public int getId() {
		return id;
	}

	public int getIdLigne() {
		return idLigne;
	}

	public void setIdLigne(int idLigne) {
		this.idLigne = idLigne;
		try {
			update("id_ligne", idLigne);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

	public int getIdTable() {
		return idTable;
	}

	public void setIdTable(int idTable) {
		this.idTable = idTable;
		try {
			update("id_table", idTable);
		} catch (DefaultException | TableNotFoundException | BadRequestException e) {
			e.printMessage();
		}
	}

}
