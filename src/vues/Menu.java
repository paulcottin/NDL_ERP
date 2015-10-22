package vues;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import model.ERP;

public class Menu extends MenuBar{

	private ERP erp;
	private Fenetre fen;
	private javafx.scene.control.Menu fichier;
	private MenuItem parametres;
	
	public Menu(ERP erp, Fenetre fen) {
		super();
		this.erp = erp;
		this.fen = fen;
		
		init();
		construct();
	}
	
	private void init() {
		fichier = new javafx.scene.control.Menu("Fichier");
			parametres = new MenuItem("Paramètres");
		
	}
	
	private void construct() {
		fichier.getItems().add(parametres);
		
		this.getMenus().add(fichier);
	}
 	
	public ERP getErp() {
		return erp;
	}
	public void setErp(ERP erp) {
		this.erp = erp;
	}
	public Fenetre getFen() {
		return fen;
	}
	public void setFen(Fenetre fen) {
		this.fen = fen;
	}
}
