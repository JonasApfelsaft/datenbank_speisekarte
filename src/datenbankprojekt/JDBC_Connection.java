package datenbankprojekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
/**
 * Stellt eine JDBC_Connection dar (hier ist sie nach unserem Account personalisieren)
 * Beleg im Modul Datenbanken WiSe 2017/18
 *
 * In Anlehnung zur Klasse JDBC_Connection aus Dozentenfoliensatz
 */
public class JDBC_Connection {
	private Connection dbcon;
	private Statement stmt;
	
	//finale Attribute, da sich unsere Verbindung nicht aendert
	//Eventuell kann man sie ueber Konstruktor uebergeben
	private final String db_url = "jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0558239__db_sp2";	
	private final String username = "_s0558239__db_sp2_generic";
	private final String password = "speisekarte";
	
	/**
	 * Konstruktor zum Aufbau einer Verbindung zum DB-Server
	 * @throws SQLException 
	 */
	public JDBC_Connection() throws SQLException {
		try{
			//startet Verbindung
			dbcon = DriverManager.getConnection(db_url, username, password);
			//bereitet ein Statement vor
			stmt = dbcon.createStatement();
			System.out.println("Verbunden auf " + db_url);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}


	/**
	 * Schickt SELECT-Anfragen an die Datenbank.
	 * @param SELECT-Query
	 * @return ResultSet (Datensatz), wenn Anfrage korrekt, sonst null
	 */
	 public ResultSet executeQuery(String query){
		try {
			return stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Bei der Anfrageverarbeitung ist ein Fehler aufgetreten: "+e.toString());
		}
		return null;
	}
	 /**
	  * Schickt INSERT-Anfragen an die Datenbank.
	  * @param update (hier: insert)
	  * @return Anzahl der geaenderten Datensaetze
	  */
	public int executeUpdate(String update){
		 try{
			 return stmt.executeUpdate(update);
		 } catch(SQLException e){
			 System.out.println("Bei der Anfrageverarbeitung ist ein Fehler aufgetreten: " + e.toString());
			 return -1;
		 }
		 
	 }
	 
	 /**
	  * Schliesst die Verbindung zur DB.
	  */
	 public void close_database(){
		 try{
			 stmt.close();
			 dbcon.close();
		 } catch(Exception e){
			 e.printStackTrace();
		 }
		 System.out.println("Verbindung zur Datenbank geschlossen.");
	 }
	 
	 /**
	  * Getter fuer Connection
	  * @return
	  */
	 public Connection getConnection(){
		 return this.dbcon;
	 }
	 
	 /**
	  * Getter fuer das Statement
	  * @return
	  */
	 public Statement getStatement(){
		 return this.stmt;
	 }
	 
	 /**
	  * Erzeugt Statements
	  * @return
	  * @throws SQLException
	  */
	 public Statement createStatement() throws SQLException{
		 //siehe Oracle-Doku!
		 stmt = dbcon.createStatement();
		 return stmt;
	 }
	
	 /**
	  * Erzeugt eine Connection mit den angegebenen Parametern (Zeiger)
	  * Dient zur Navigation in der DB
	  *  @return das Statement
	  * @throws SQLException falls Parameter falsch oder Verbindung unmoeglich
	  * 
	  */
	 public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException{
		 //siehe Oracle-Doku!
		 stmt = dbcon.createStatement(resultSetType, resultSetConcurrency);
		 return stmt;
	 }
}
