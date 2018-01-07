package datenbankprojekt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

/**
 * Verwaltet Gerichte in der Datenbank anhand der im Aufgabeblatt gefragten
 * Operationen.
 * 
 * Beleg im Modul Datenbanken WiSe 2017/18
 *
 */

/*
 * Orientierung: Foliensatz JDBC - S. 5, Bruecke zwischen relationalem Modell
 * und Objektorientierung
 */

public class GerichtVerwaltung {
	// Connection attribut
	private JDBC_Connection dbcon;

	// User inputs
	private Scanner in;

	private Random r = new Random();

	private List<GerichtEntitaet> gerichte = new ArrayList<GerichtEntitaet>();

	/**
	 * Default-Konstruktor
	 */
	public GerichtVerwaltung() {
	}

	/**
	 * Parameter-Konstruktor
	 * 
	 * @param db_connection
	 * @param scanner
	 */
	public GerichtVerwaltung(JDBC_Connection dbcon, Scanner in) {
		System.out.println("Ihr Gerichte-Management wird nun gestartet...");
		this.dbcon = dbcon;
		this.in = in;
	}

	// Aufgabe 6-1
	/**
	 * Zeigt die Inhalte der Tabelle Gericht
	 * 
	 * @throws SQLException
	 */
	public void showTable() throws SQLException {
		ResultSet rs = dbcon.executeQuery("SELECT * FROM gericht");
		while (rs.next()) {
			// speichert die Gericht-Entitaeten
			GerichtEntitaet b = new GerichtEntitaet(rs);
			// Gibt Gericht-Entitaeten am Bildschirm aus
			System.out.println(b.gerichtAlsString());
		}
	}

	// Aufgabe 6-2
	/**
	 * Fuehrt eine INSERT-Operation durch
	 * 
	 * @throws SQLException
	 */
	public void insert() throws SQLException {
		String name = "";
		String farbe = "";
		int stueck = -1;
		boolean ok = false;

		// Name des Gerichts eingeben
		System.out.println("Geben Sie den Namen des Gerichts ein: ");
		while (!ok) {
			try {
				Scanner s1 = new Scanner(System.in);
				name += s1.nextLine();
				System.out.println(name);
				//name += System.lineSeparator();
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println("Falsche Eingabe! Das Programm wird beendet.");
				System.exit(0);
			}
		}
		ok = false;

		// Tellerfarbe des Gerichts auswaehlen
		System.out.println("Geben Sie die Tellerfarbe des Gerichts ein.");
		System.out.println("Waehlen Sie die Farbe blau, gelb, orange oder rot aus.");
		System.out.println("Bitte achten sie auf die Kleinschreibung bei der Farbenwahl: ");
		while (!ok) {
			try {
				Scanner s2 = new Scanner(System.in);
				String pruefen = s2.next();
				if (pruefen.equals("blau") || pruefen.equals("gelb") || pruefen.equals("orange")
						|| pruefen.equals("rot")) {
					farbe = pruefen;
					//farbe = "rot";
					System.out.println(farbe);
					
					ok = true;
				} else {
					System.out.println("Falsche Eingabe! Das Programm wird beendet.");
					System.exit(0);
				}
			} catch (InputMismatchException e) {
				System.out.println("Falsche Eingabe! Das Programm wird beendet.");
				System.exit(0);
			}
						
		}
		ok = false;

		// Stueckanzahl des Gerichts
		System.out.println("Geben Sie die Stueckanzahl des Inhalts des Gerichts ein: ");
		while (!ok) {
			try {
				Scanner s3 = new Scanner(System.in);
				stueck = s3.nextInt();
				System.out.println(stueck);
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println("Falsche Eingabe! Das Programm wird beendet.");
				System.exit(0);
			}
		}
		ok = false;
		
		String query = String.format("INSERT INTO gericht(name, farbe, stueck) VALUES('%s', '%s', %d);", name, farbe, stueck);
		//String query = "INSERT INTO gericht(name, farbe, stueck) VALUES("+name+","+farbe+","+stueck+");";
		
		/*
		 * INSERT INTO gericht(name, farbe, stueck)
		   VALUES ('Inari Nigiri', 'blau', 2);
		 */
		dbcon.executeUpdate(query);

		System.out.println("Das Einfuegen war erfolgreich!");
	}

	// Aufgabe 6-3
	/**
	 * Fuehrt eine DELETE-Operation durch.
	 * 
	 * @throws SQLException
	 */
	public void delete() throws SQLException {
		System.out.println(
				"Welchen Datensatz wollen Sie entfernen? Geben Sie den Schluessel des zu loeschenden Datensatzes an.");
		showTable();
		String wahl = null;
		while (true) {
			try {
				wahl = in.nextLine();
				System.out.println(wahl);
				break;
			} catch (InputMismatchException e) {
				System.out.println("Falsche Eingabe!");
				System.exit(0);
			}
		}

		//gerichte = alleGerichte();
		boolean vorhanden = true;
		/*for (GerichtEntitaet b : gerichte) {
			if (b.getName().equals(wahl)) {
				vorhanden = true;
				break;
			}
		}*/

		if (vorhanden) {
			String query = "DELETE FROM gericht WHERE name='" +wahl+"';";
			System.out.println(query);
			int deleteState = dbcon.executeUpdate(query);
			// muss komplett geloescht werden, da sonst die aus der Datenbank geloeschten
			// Buecher noch drinnen sind
			if (deleteState == 0)
				System.out.println("Das Loeschen ist fehlgeschlagen!");
			else
				System.out.println("Der Datensatz wurde erfolgreich geloescht!");
		} else
			System.out.println("Der Datensatz ist nicht enthalten und kann somit nicht geloescht werden!");

	}

	// Aufgabe 6-4
	/**
	 * Navigiert durch die Tabelle. p - Previous n - Next alle anderen Taste beenden
	 * die Navigation
	 * 
	 * Die Navigation ist kreislaeufig eingebaut
	 * 
	 * @throws SQLException
	 */
	public void navigate() throws SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// Siehe Folien (JDBC) + Oracle Doku +
			// https://www.tutorialspoint.com/jdbc/navigate-result-sets.htm
			stmt = dbcon.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery("SELECT * FROM gericht");
		} catch (SQLException e) {
			System.out.println("Ein Fehler ist aufgetreten!");
			e.printStackTrace();
		}

		System.out.println(
				"Navigieren Sie durch die Tabelle Gericht mit " + "den Tasten p (rueckwaerts) und n (vorwaerts).");
		System.out.print("");
		boolean ok = true;
		while (ok) {
			GerichtEntitaet gericht;
			System.out.println("Ihre Wahl: ");
			switch (in.next().toLowerCase()) {
			case "p":
				if (rs.isFirst() || rs.isBeforeFirst())
					rs.last();
				else
					rs.previous();
				gericht = new GerichtEntitaet(rs);
				System.out.println(gericht.gerichtAlsString());
				break;
			case "n":
				if (rs.isLast() || rs.isAfterLast())
					rs.first();
				else
					rs.next();
				gericht = new GerichtEntitaet(rs);
				System.out.println(gericht.gerichtAlsString());
				break;
			default:
				ok = false;
				break;
			}
		}
	}

	/*
	 * Folgende Methoden untestuetzen die Programmausfaehrung
	 */

	/**
	 * Speichert alle Verlage in einer Liste als Zeichenketten
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected List<String> alleTellerfarben() throws SQLException {
		Statement stmt = dbcon.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM tellerfarbe");
		List<String> verlage = new ArrayList<>();
		while (rs.next()) {
			String verlag = "| " + rs.getString("verlag_nr") + " | " + rs.getString("name") + " |";
			verlage.add(verlag);
		}
		return verlage;
	}

	protected void ausgabeListe(List<String> l, String table) {
		for (String ausgabe : l) {
			System.out.println(table + "	" + ausgabe);
		}
	}

	/**
	 * Speichert alle Gerichte in der DB als ResultSet (Gericht-Entitaeten)
	 * 
	 * @return Liste der Gerichte
	 * @throws SQLException
	 */
	protected List<GerichtEntitaet> alleGerichte() throws SQLException {
		// Liste muss erneuert werden, sonst koennten geloeschte Datensaetze noch drin
		// sein
		gerichte.clear();
		ResultSet rs = dbcon.executeQuery("SELECT * FROM gericht");

		while (rs.next()) {
			GerichtEntitaet gericht = new GerichtEntitaet(rs);
			gerichte.add(gericht);
		}
		return gerichte;
	}

}
