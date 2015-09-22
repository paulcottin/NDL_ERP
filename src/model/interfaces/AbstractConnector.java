package model.interfaces;

import java.util.ArrayList;
import exceptions.MyException;

public abstract class AbstractConnector {
	
	protected ArrayList<ArrayList<String>> lignes;
	
	public AbstractConnector() throws MyException {
		this.lignes = new ArrayList<ArrayList<String>>();
	}

	public abstract void queryLignes() throws MyException;
	public abstract void connect() throws MyException;
	
}
