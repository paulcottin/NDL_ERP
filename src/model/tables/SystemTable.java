package model.tables;

import java.util.ArrayList;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.DefaultException;
import exceptions.TableNotFoundException;
import model.interfaces.BaseDonnee;
import model.interfaces.PhysicalTable;
import utils.BddValue;

public class SystemTable extends PhysicalTable{

	public SystemTable(BaseDonnee bdd, ArrayList<BddValue> values) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, values);
	}
	
	public SystemTable (BaseDonnee bdd, int id) throws TableNotFoundException, DefaultException, BadRequestException {
		super(bdd, id);
	}

	@Override
	protected void habillageDonnees() throws ColonneNotfoundException, TableNotFoundException, BadRequestException, DefaultException {
		
	}

}
