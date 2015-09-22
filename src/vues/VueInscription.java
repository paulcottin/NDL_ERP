package vues;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.tables.Inscription;

public class VueInscription extends Parent{

	Inscription inscription;
	
	public VueInscription(Inscription inscription) {
		super();
		this.inscription = inscription;
		createVue();
	}
	
	private void createVue() {
		//Premier onglet de la page web
		WebView webView = new WebView();
		WebEngine engine = webView.getEngine();
		engine.load(inscription.getUrls().get(0));
		Tab ongletWeb = new Tab("Page web", webView);
		TabPane onglets = new TabPane();
		
		//Ajout de la table
		
		
		//Ajout des onglets
		onglets.getTabs().add(ongletWeb);
		getChildren().add(onglets);
		
	}
}
