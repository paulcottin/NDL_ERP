package controlleurs;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import model.ERP;
import model.interfaces.Table;
import vues.Filtre;

public class Rechercher implements EventHandler<ActionEvent>{

	private ERP erp;
	private Filtre filtre;
	
	public Rechercher(ERP erp, Filtre filtre) {
		this.erp = erp;
		this.filtre = filtre;
	}
	
	@Override
	public void handle(ActionEvent arg0) {
		//Suppression des champs non vide / null
		Iterator<Pair<Label, Control>> iter = filtre.getChamps().iterator();
		//Ajout des informations dans une liste exploitable par la suite
		ArrayList<Pair<Table, Pair<String, Object>>> conditions = new ArrayList<Pair<Table, Pair<String, Object>>>();
		//TODO améliorer pour des saisies dans une autre fenêtre
		while (iter.hasNext()) {
			Pair<Label, Control> temp = iter.next();
			if (temp.getValue() == null)
				iter.remove();
			else if (temp.getValue() instanceof TextField) {
				if (((TextField) temp.getValue()).getText().equals(""))
					iter.remove();
				else {
					Pair<String, Object> pair = new Pair<String, Object>(temp.getKey().getText(), ((TextField) temp.getValue()).getText());
					//TODO ce n'est pas la bonne table qui est passée en paramètre
					conditions.add(new Pair<Table, Pair<String,Object>>(filtre.getTable(), pair));
				}
			}
		}
		//Recherche
		filtre.getTable().search(filtre.getTable(), conditions);
	}

}
