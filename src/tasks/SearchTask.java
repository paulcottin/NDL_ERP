package tasks;

import java.util.ArrayList;
import java.util.Iterator;

import exceptions.BadRequestException;
import exceptions.TableNotFoundException;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.util.Pair;
import model.Ligne;
import model.interfaces.BaseDonnee;
import model.interfaces.Table;
import utils.ResultSet;
import utils.WhereCondition;

public class SearchTask extends Task<Void>{

	private Table table;
	private ObservableList<Ligne> lignes;
	private ArrayList<Pair<Table, Pair<String, Object>>> conditions;
	private WhereCondition[] where;
	private String[] from;

	public SearchTask(Table table, ArrayList<Pair<Table, Pair<String, Object>>> conditions) {
		this.table = table;
		this.lignes = table.getLignes();
		this.conditions = conditions;
	}

	@Override
	protected Void call() throws Exception {
		try {
			search();
		} catch (TableNotFoundException | BadRequestException e) {
			e.printMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void search() throws TableNotFoundException, BadRequestException {
		fromAndWhere(conditions);
		BaseDonnee bdd = table.getBdd();
		bdd.selectAll();
		bdd.from(from);
		bdd.where(where);

		ArrayList<ResultSet> res = bdd.execute();
		//Crée une liste d'id sélectionné
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (ResultSet resultSet : res)
			ids.add((int) resultSet.get(table.getIdLigneName().get()).getValue());
		
		//On enlève des lignes de la table tous les ids que l'on a pas récupéré
		Iterator<Ligne> iter = lignes.iterator();
		while (iter.hasNext()) {
			Ligne l = iter.next();
			if (!ids.contains(l.getIdLigne()))
				iter.remove();
		}
	}

	private void fromAndWhere(ArrayList<Pair<Table, Pair<String, Object>>> conditions) {
		ArrayList<String> f = new ArrayList<String>();
		where = new WhereCondition[conditions.size()];

		for (int i = 0; i < conditions.size(); i++) {
			if (!f.contains(conditions.get(i).getKey().getNom()))
				f.add(conditions.get(i).getKey().getNom().get());
			where[i] = new WhereCondition(conditions.get(i).getKey().getNom().get(), 
					conditions.get(i).getValue().getKey(), 
					BaseDonnee.EGAL, 
					conditions.get(i).getValue().getValue().toString());
		}
		
		from = new String[f.size()];
		for (int i = 0; i < f.size(); i++) {
			from[i] = f.get(i);
		}
	}
}
