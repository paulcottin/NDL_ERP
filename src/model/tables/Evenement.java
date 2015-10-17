package model.tables;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import model.interfaces.PhysicalTable;
import model.interfaces.TableType;
import utils.BddValue;

public class Evenement extends PhysicalTable{

	public Evenement(BaseDonnee bdd, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, values);
		type = TableType.EVENEMENT;
	}
	
	public Evenement(BaseDonnee bdd, int idTable) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, idTable);
		type = TableType.EVENEMENT;
	}

}
