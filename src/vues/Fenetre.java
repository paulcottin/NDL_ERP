package vues;

import controlleurs.CreerFormulaire;
import exceptions.TableNotFoundException;
import exceptions.TableTypeNotFoundException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import model.ERP;
import model.interfaces.Table;
import model.interfaces.TableType;
import vues.evenement.ListeEvenement;
import vues.inscription.ListeInscriptions;
import vues.personne.ListePersonne;
import vues.personne.VuePersonne;

public class Fenetre extends Scene{

	private ERP erp;
	private Parent root, left, right, bottom;
	private TabPane center;
	
	public Fenetre(ERP erp, Parent root, int x, int y) {
		super(root, x, y);
		this.erp = erp;
		this.root = root;
		createWin();
	}
	
	private void createWin() {	
		//Bouton temporaire
		Button b = new Button("Ajout form");
		b.addEventHandler(ActionEvent.ACTION, new CreerFormulaire(erp));
		
		//Menu
		BorderPane top = new BorderPane();
		Menu menu = new Menu(erp, this);
		BarreTache taches = new BarreTache(erp, this);
		top.setCenter(taches);
		top.setTop(menu);
		
		// grille centrale
		center = new TabPane();
		
		((BorderPane) root).setLeft(left);
		((BorderPane) root).setCenter(center);
		((BorderPane) root).setBottom(b);
		((BorderPane) root).setTop(top);
	}
	
	public void setTableListe(String tableType) throws TableTypeNotFoundException{
		BorderPane liste = new BorderPane();
		switch (tableType) {
		case TableType.PERSONNE:
			liste = new ListePersonne(erp, this, tableType);
			break;
		case TableType.INSCRIPTION:
			liste = new ListeInscriptions(erp, this, tableType);
			break;
		case TableType.EVENEMENT:
			liste = new ListeEvenement(erp, this, tableType);
			break;
		default:
			throw new TableTypeNotFoundException(tableType);
		}
		Tab t = new Tab(tableType, liste);
		t.setId(tableType);
		int tabOpen = isTabOpen(tableType);
		if (tabOpen >= 0)
			center.getSelectionModel().select(center.getTabs().get(tabOpen));
		else
			center.getTabs().add(t);
	}
	
	public void setTable(int idTable) throws TableNotFoundException {
		int index = -1;
		for (int i = 0; i < erp.getTables().size(); i++) {
			if (erp.getTables().get(i).getIdTable() == idTable) 
				index = i;
		}
		if (index == -1) throw new TableNotFoundException("table d'id "+idTable);
		
		Table t = erp.getTables().get(index);
		Tab tab = new Tab(t.getNom().get(), new VuePersonne(erp, t));
		tab.setId(String.valueOf(t.getIdTable()));
		int tabOpen = isTabOpen(t);
		if (tabOpen >= 0)
			center.getSelectionModel().select(center.getTabs().get(tabOpen));
		else {
			center.getTabs().add(tab);
			center.getSelectionModel().select(center.getTabs().size()-1);
		}
	}
	
	private int isTabOpen(Table table) {
		ObservableList<Tab> l = center.getTabs();
		for (int i = 0; i < l.size(); i++)
			if (l.get(i).getId().equals(String.valueOf(table.getIdTable())))
				return i;
		return -1;
	}
	
	private int isTabOpen(String tableType) {
		ObservableList<Tab> l = center.getTabs();
		for (int i = 0; i < l.size(); i++)
			if (l.get(i).getId().equals(tableType))
				return i;
		return -1;
	}

	public TabPane getCenter() {
		return center;
	}

	public void setCenter(TabPane center) {
		this.center = center;
	}
}
