package datenbankprojekt;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Verwaltet die Benutzereingaben bezueglich der auszufuehrenden Operationen.
 * Beleg im Modul Datenbanken WiSe 2017/18
 *
 */
public class Controller {
	
	Scanner in = new Scanner(System.in);
	
	/*
	 * Menue-Runner Methode
	 */
	public void starten() throws SQLException {
		JDBC_Connection dbcon = new JDBC_Connection();
		GerichtVerwaltung db = new GerichtVerwaltung(dbcon, in);
		while(true) {
			try {
				info1();
				int wahl1 = auswahl();
				
				switch(wahl1) {
				case 1:	db.showTable();
						break;
				case 2: db.insert();
						break;
				case 3: db.delete();
						break;
				case 4: db.navigate();
						break;
				case 5: dbcon.close_database(); 
						System.exit(0);
				default: break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
    }
    
	/*
	 * Hauptmenue
	 */
    private static void info1() {
    	System.out.println("Willkommen in der Gerichte-Datenbank! Was moechten Sie tun?");
    	System.out.println("(1) Ausgabe der Tabelle");
    	System.out.println("(2) Eingabe neuer Datensaetze");
    	System.out.println("(3) Loeschen von Datensaetzen");
    	System.out.println("(4) Navigation durch die Tabelle");
    	System.out.println("(5) Beenden des Programms");
    	System.out.println("");
    }
    
    /*
     * Verwaltet die Auswahl
     */
    private int auswahl() {
    	Scanner scan = new Scanner(System.in);
    	int wahl = 1;
    	boolean ok = false;
    	while(ok == false) {
    		wahl = scan.nextInt();
    		if (wahl < 1 || wahl > 5) {
    			ok = false;
    			System.out.println("Falsche Eingabe! Bitte waehlen Sie eine Zahl zwischen 1 und 5!");
    	    		System.out.println("");
    		}
    		else
    			ok = true;
    	}
    	return wahl;
    }
 
}
