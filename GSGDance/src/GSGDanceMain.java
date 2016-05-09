import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//main class
public class GSGDanceMain {
	static mainFrame home = new mainFrame();

	public static void main(String[] args) {
		LoadDatabase.loadDatabase();
		home.setVisible(true);
		//databaseCorrections();
	}
	
	
	public Object getHome() {
		return home;
	}
	
	//use this to add columns in the database
/*	public static void databaseCorrections() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			String q = "ALTER TABLE dancers RENAME TO TempDancers;";
			String r = "CREATE TABLE DANCERS " +
					"(NAME			TEXT," +
					 "SEX			TEXT," +
					 "LIVERY		TEXT," +
					 "HERE			 INT," +
					 "BELLA			TEXT," +
					 "CASTELLANA	TEXT," +
					 "CHIRANZULA	TEXT," +
					 "CONTREPASSO	TEXT," +
					 "GLORIA		TEXT," +
					 "GRACCA		TEXT," +
					 "LEGGIADRIA	TEXT," +
					 "PAVONNE		TEXT," +
					 "SOBEIN		TEXT," +
					 "SPAGNOLETTA	TEXT," +
					 "SPAGNOLETTAC	TEXT," +
					 "VILLANELLA	TEXT," +
					 "VILLANELLAP	TEXT," +
					 "STATUS		TEXT)";
			String s = "INSERT INTO DANCERS (NAME, SEX, LIVERY, BELLA, CASTELLANA, CHIRANZULA, CONTREPASSO, GLORIA, GRACCA, LEDDIADRIA, PAVONNE, SOBEIN " +
					 	"SPAGNOLETTA, SPAGNOLETTAC, VILLANELLA, VILLANELLAP) SELECT NAME, SEX, LIVERY, BELLA, CASTELLANA, CHIRANZULA, CONTREPASSO, GLORIA, GRACCA, LEDDIADRIA, PAVONNE, SOBEIN " +
					 	"SPAGNOLETTA, SPAGNOLETTAC, VILLANELLA, VILLANELLAP FROM TempDancers;";
		
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
*/
}
