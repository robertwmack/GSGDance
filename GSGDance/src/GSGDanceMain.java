import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

//main class
public class GSGDanceMain {
	static mainFrame home = new mainFrame();
	private static String[] dances = {
			"Bella",
			"Castellana",
			"Chiranzula",
			"Contrepasso",
			"Gloria",
			"Gracca",
			"Leggiadria",
			"Pavonne",
			"So_Bein_Mi_Chi",
			"Spagnoletta",
			"Spagnoletta_Couples",
			"Villanella",
			"Villanella_Couples"
	};
	
	
	public static void main(String[] args) {
		LoadDatabase.loadDatabase();
		reset();
		home.setVisible(true);
	}
	
	
	public static Object getHome() {
		return home;
	}
	
	public static int getDanceCount() {
		return dances.length;
	}
	
	public static String[] getDanceNames() {
		return dances;
	}
	
	public static void reset() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String sql = "UPDATE DANCERS SET HERE=0;";
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
		} catch ( Exception e) {
		}
		
	}
}
