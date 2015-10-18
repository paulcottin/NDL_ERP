package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.BadRequestException;
import exceptions.ColonneNotfoundException;
import exceptions.ConnexionException;
import exceptions.DefaultException;
import exceptions.IdentificationException;
import exceptions.TableNotFoundException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.connecteurs.Access;
import model.connecteurs.GoogleConnector;
import model.interfaces.BaseDonnee;
import model.interfaces.Table;
import model.interfaces.TableType;
import model.tables.Evenement;
import model.tables.Inscription;
import model.tables.Personne;
import utils.BddColonne;
import utils.BddValue;
import utils.ResultSet;
import utils.WhereCondition;
import vues.Fenetre;

public class ERP extends Application{

	ObservableList<Table> tables;
	GoogleConnector google;
	BaseDonnee bdd;
	int lastTableID;

	public ERP() throws DefaultException {
		this.tables = FXCollections.observableArrayList();
		bdd = new Access();
		google = new GoogleConnector();
		try {
			bdd.connect(new File("C:\\Users\\polob\\Documents\\bdd_erp.accdb"), "");
		} catch (IdentificationException | ConnexionException e1) {
			e1.printMessage();
		} catch (FileNotFoundException e) {
			throw new DefaultException("Fichier de connexion à la base de donnée non trouvé !");
		}

		try {
			initTables();
		} catch (DefaultException | TableNotFoundException | BadRequestException | ColonneNotfoundException e) {
			e.printMessage();
			e.printStackTrace();
		}
	}

	private void initTables() throws DefaultException, BadRequestException, TableNotFoundException, ColonneNotfoundException {
		bdd.select(new BddColonne("tables", "id_table"), new BddColonne("tables", "type"));
		bdd.from("tables");
//		bdd.where(new WhereCondition("tables", "type", BaseDonnee.EGAL, TableType.INSCRIPTION));
		for (ResultSet map: bdd.execute()) {
			if(map.get("type").getValue().equals(TableType.INSCRIPTION))
				tables.add(new Inscription(bdd, (int) map.get("id_table").getValue()));
			else if (map.get("type").getValue().equals(TableType.PERSONNE))
				tables.add(new Personne(bdd, (int) map.get("id_table").getValue()));
			else if (map.get("type").getValue().equals(TableType.EVENEMENT))
				tables.add(new Evenement(bdd, (int) map.get("id_table").getValue()));
			else 
				throw new DefaultException("Type de table ("+map.get("type").getValue()+") incorrect !");
		}
	}

	public void createTableFromGoogleSheet() throws DefaultException, TableNotFoundException, BadRequestException {
		//Demande de l'URL et du nom de la table
		Dialog<Pair<String, String>> bddDialog = new Dialog<>();
		bddDialog.setTitle("Connexion à la base de donnée");
		bddDialog.setHeaderText("Informations de connexion");

		ButtonType loginButtonType = new ButtonType("Valider", ButtonData.OK_DONE);
		bddDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		TextField urlField = new TextField();
		TextField nomTable = new TextField();
		grid.add(new Label("URL complète de la feuille de réponse du formulaire:"), 0, 0);
		grid.add(urlField, 1, 0);
		grid.add(new Label("Nom de la nouvelle table dans la base de donnée:"), 0, 1);
		grid.add(nomTable, 1, 1);

		bddDialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> urlField.requestFocus());

		bddDialog.setResultConverter(dialogButton -> {
			if (dialogButton == loginButtonType) {
				return new Pair<>(urlField.getText(), nomTable.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = bddDialog.showAndWait();

		if(!result.isPresent()) throw new DefaultException();

		String url = result.get().getKey();
		String nom = result.get().getValue().replace(" ", "_");

		//Récupération de l'id à partir de l'URL
		//Partie du tab (=split(/)) la plus longue
		String[] tab = url.split("/");
		int max = 0, index = 0;
		for (int i = 0; i < tab.length; i++) {
			if (tab[i].length() > max) {
				max = tab[i].length();
				index = i;
			}
		}
		google.setGoogleID(tab[index]);
		
		ArrayList<BddValue> values = new ArrayList<BddValue>();
		values.add(new BddValue("nom_table", nom));
		values.add(new BddValue("famille", "inscription"));
		values.add(new BddValue("type", "test"));
		values.add(new BddValue("id_ligne_name", "id_ligne"));
		Table ins = new Inscription(bdd, values);
		google.connect();
		try {
			google.queryLignes();
		} catch (DefaultException e) {
			e.printMessage();
		}
		
		//Création des colonnes
		System.out.println("nombres de lignes : "+google.getLignes().size());
		if (google.getLignes().size() == 0) throw new BadRequestException("Impossible de créer une colonne null");
		ins.createColonnes(google.getLignes().get(0));
//		
//		for (ObservableList<Pair<String, String>> list : google.getLignes()) {
//			LigneTest l = new LigneTest(bdd, ins.getIdTable(), values);
//			for (Pair<String, String> pair : list) {
//				l.add(pair.getKey(), pair.getValue());
//			}
//		}
//		tables.add(ins);
		
		System.out.println("done");
//			f.createTable();
//			progressBar = f.getProgressBar();
//			forms.add(f);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Fenetre(this, new BorderPane(),1200,600);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public ObservableList<Table> getTables() {
		return tables;
	}

	public void setTables(ObservableList<Table> tables) {
		this.tables = tables;
	}

}
