package model.connecteurs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import exceptions.DefaultException;
import exceptions.MyException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class GoogleConnector {

	private String googleID;
	private DriveQuickstart drive;
	private String fileName;
	private ObservableList<ObservableList<Pair<String, String>>> lignes;
 
	public GoogleConnector() {
		lignes = FXCollections.observableArrayList();
	}
	
	public GoogleConnector(String googleID) throws DefaultException {
		this.googleID = googleID;
		lignes = FXCollections.observableArrayList();
	}

	private void waitCSV(){
		boolean pending = true;
		while (pending) {
			if (drive.getFileOutputStream() != null)
				pending = false;
		}
		try {
			drive.getFileOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse le fichier tmp.csv qui contient les donn�es de la feuille Google
	 * @throws MyException
	 */
	public void queryLignes() throws DefaultException {
		BufferedReader br = null;
		Reader in;
		Iterable<CSVRecord> records = null;
		String[] headerTab = null;
		try {
			in = new InputStreamReader(new FileInputStream(new File("tmp.csv")), "utf-8");
			br = new BufferedReader(in);
			String header = br.readLine();
			br.close();
			headerTab = securiseHeader(updateHeader(header));
			in = new InputStreamReader(new FileInputStream(new File("tmp.csv")), "utf-8");
			records = CSVFormat.EXCEL.withHeader(headerTab).parse(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Iterator<CSVRecord> iter = records.iterator();
		int index = 0;
		while (iter.hasNext()) {
			CSVRecord record = iter.next();
			lignes.add(FXCollections.observableArrayList());
			for (int i = 0; i < record.size(); i++) {
				lignes.get(index).add(new Pair<String, String>(headerTab[i], record.get(i)));
			}
			index++;
		}
		System.out.println("list size : "+lignes.size()+" ; index : "+index);
	}

	private String[] updateHeader(String header) {
		String[] old = header.split(",");
		ArrayList<String> l = new ArrayList<String>();
		for (int i = 0; i < old.length; i++) {
			if (!l.contains(old[i]))
				l.add(old[i]);
			else {
				int cpt = 1;
				while (l.contains(old[i]+cpt))
					cpt++;
				l.add(old[i]+cpt);
			}
		}

		String[] tab = new String[old.length];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = l.get(i);
		}
		
		tab = securiseHeader(tab);

		String newHeader = String.join(",", tab);
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("tmp.csv"), "utf-8"));
			String ligne = "";
			ArrayList<String> content = new ArrayList<String>();

			while ((ligne = br.readLine()) != null)
				content.add(ligne);
			br.close();
			content.set(0, newHeader);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tmp.csv"), "utf-8"));
			for (String string : content)
				bw.write(string+"\r\n");
			bw.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tab;
	}
	
	public void connect() throws DefaultException {
		try {
			this.drive = new DriveQuickstart(googleID);
		} catch (IOException e) {
			throw new DefaultException("Erreur de connexion � Google !");
		}
		fileName = drive.getFileName();
		waitCSV();
	}
	
	protected String[] securiseHeader(String[] header) {
		String[] ok = new String[header.length];
		for (int i = 0; i < ok.length; i++) {
			ok[i] = secureString(header[i]);
		}
		return ok;
	}
	
	private String secureString(String string) {
		if (string.length() > 60)
			string = string.substring(0, 60);
		if (string.contains("'"))
			string = string.replace("'", "_");
		if (string.contains("."))
			string = string.replace(".", "");
		while (string.length() > 1 && string.substring(0, 1).equals(" "))
			string = string.substring(1);
		return string;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGoogleID() {
		return googleID;
	}

	public void setGoogleID(String googleID) {
		this.googleID = googleID;
	}

	public ObservableList<ObservableList<Pair<String, String>>> getLignes() {
		return lignes;
	}

	public void setLignes(ObservableList<ObservableList<Pair<String, String>>> lignes) {
		this.lignes = lignes;
	}

}