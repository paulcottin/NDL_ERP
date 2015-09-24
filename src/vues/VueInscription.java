package vues;

import exceptions.DefaultException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.ERP;
import model.interfaces.Table;
import model.interfaces.TableType;
import model.tables.Inscription;
import vues.listeners.ChangementInscription;

public class VueInscription extends BorderPane{

	ERP erp;
	Inscription inscription;
	ObservableList<Inscription> listInscriptions;
	GaucheInscription gauche;
	TabPane onglets;
	boolean constructed;
	
	public VueInscription(ERP erp) {
		super();
		gauche = new GaucheInscription(erp);
		gauche.addEventHandler(ActionEvent.ACTION, new ChangementInscription(this));
		this.erp = erp;
		this.listInscriptions = FXCollections.observableArrayList();
		for (Table t : erp.getTables())
			if (t.getType().equals(TableType.INSCRIPTION))
				listInscriptions.add((Inscription) t);
		
		this.inscription = listInscriptions.get(1);
		init();
	}
	
	private void init() {
		onglets = new TabPane();
		setCenter(onglets);
		setLeft(gauche);
	}
	
	//TODO : supprimer cette méthode après avoir intégrer l'ouverture des pages web
	public void construct() {
		//Premier onglet de la page web
		WebView webView = new WebView();
		WebEngine engine = webView.getEngine();
//		engine.load(inscription.getUrls().get(0));
		Tab ongletWeb = new Tab("Page web", webView);
	}
	
	private void addOnglet() {
		int index = -1;
		for (int i = 0; i < onglets.getTabs().size(); i++) {
			if (onglets.getTabs().get(i).getText().equals(inscription.getNom()))
				index = i;
		}
		//Si l'onglet existe déjà
		if (index >= 0) {
			onglets.getSelectionModel().clearAndSelect(index);
		}
		//Si l'onglet n'est pas ouvert
		else {
			Grille grille = new Grille(inscription);
			Tab ongletGrille = new Tab(inscription.getNom(), grille);
			onglets.getTabs().add(ongletGrille);
			onglets.getSelectionModel().clearAndSelect(onglets.getTabs().size()-1);
		}
	}

	public Inscription getInscription() {
		return inscription;
	}

	public void setInscription(Inscription inscription) {
		this.inscription = inscription;
		addOnglet();
	}

	public ObservableList<Inscription> getListInscriptions() {
		return listInscriptions;
	}

	public void setListInscriptions(ObservableList<Inscription> listInscriptions) {
		this.listInscriptions = listInscriptions;
	}

	public GaucheInscription getGauche() {
		return gauche;
	}

	public void setGauche(GaucheInscription gauche) {
		this.gauche = gauche;
	}

	public boolean isConstructed() {
		return constructed;
	}

	public void setConstructed(boolean initialized) {
		this.constructed = initialized;
	}
}
