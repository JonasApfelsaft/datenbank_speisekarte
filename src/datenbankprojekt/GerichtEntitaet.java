package datenbankprojekt;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Buchentitaet - Stellt einen Buch-Datensatz (ResultSet) dar.
 * 
 *
 */
public class GerichtEntitaet {

	// ResultSet aus buch - wird nach einer Abfrage erzeugt
	private ResultSet res;

	/**
	 * Parameter-Konstruktor
	 * 
	 * @param res
	 *            ResultSet das ueber eine Anfrage geliefert wurde
	 */
	public GerichtEntitaet(ResultSet res) {
		super();
		this.res = res;
	}

	/**
	 * Liest Name des Gerichts aus
	 * 
	 * @return name
	 * @throws SQLException
	 */
	public String getName() throws SQLException {
		return res.getString("name");
	}

	/**
	 * Liest Tellerfarbe aus
	 * 
	 * @return Tellerfarbe
	 * @throws SQLException
	 */
	public String getTellerfarbe() throws SQLException {
		return res.getString("farbe");
	}

	/**
	 * Liest Stueckanzahl aus
	 * 
	 * @return Stueck
	 * @throws SQLException
	 */
	public int getStueck() throws SQLException {
		return res.getInt("stueck");

	}

	/**
	 * Liefert Datensatz als String zurueck
	 * @return Gericht als String
	 */
	public String gerichtAlsString() throws SQLException{
		return "Gericht[name=" + getName() + ", tellerfarbe=" + getTellerfarbe() + ", stueck=" + getStueck()
				+ "]" + System.lineSeparator();

	}
}
