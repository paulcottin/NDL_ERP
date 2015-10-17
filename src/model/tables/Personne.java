package model.tables;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import model.interfaces.PhysicalTable;
import model.interfaces.TableType;
import utils.BddValue;

public class Personne extends PhysicalTable{

	public Personne(BaseDonnee bdd, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, values);
		type = TableType.PERSONNE;
	}
	
	public Personne(BaseDonnee bdd, int idTable) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, idTable);
		type = TableType.PERSONNE;
	}

}
