import java.sql.*;

public class LoadDatabase 
{
	
	private static String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private static int danceCount = dances.length;
	
	public static void loadDatabase()
	{
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		try {
		stmt = c.createStatement();
		String sql = "CREATE TABLE DANCERS " +
					 "(NAME TEXT, " +
					 "SEX TEXT, " +
					 "LIVERY TEXT, " +
					 "HERE TINYINT, " +
					 "STATUS TINYINT";
		for (int i = 0; i < danceCount; i++) {
			sql = sql + ", " + dances[i] + " TINYINT";
		}
		sql = sql + ")";
		
		stmt.executeUpdate(sql);
		stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": LoadDatabase-" + e.getMessage() );
		}

	}
}
