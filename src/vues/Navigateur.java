package vues;

import javafx.scene.control.Tab;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Navigateur extends Tab{

	private String url;
	private WebView webView;
	private WebEngine webEngine;
	
	public Navigateur(String titre, String url) {
		super(titre);
		super.setId(titre);
		this.url = url;
		this.webView = new WebView();
		this.webEngine = webView.getEngine();
		this.webEngine.load(url);
		this.setContent(webView);
	}
}
