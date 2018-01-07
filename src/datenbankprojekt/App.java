package datenbankprojekt;

import java.sql.SQLException;

/**
 * Beleg im Modul Datenbanken WiSe 2017/18
 * Startet das Menie
 * 
 * Ausgefuehrte Aktionen:
 * 1) Ausgabe der Tabelle (Einfache Ausgabe aller Eintraege)
 * 2) Eingabe neuer Datensaetze (Eingabe aller einzelnen Spalten - bis auf die Abhaengigkeiten)
 * 3) Loeschen von Datensaetzen (Einfaches Loeschen ueber Primaerschluessel)
 * 4) Navigieren durch die Tabelle -mittels n (next) und p (previous): jeder Datensatz wird einzeln angezeigt
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException
    {
    	new Controller().starten();
    }
}

