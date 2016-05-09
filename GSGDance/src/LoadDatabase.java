import java.sql.*;

public class LoadDatabase 
{
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
					 "(NAME			TEXT," +
					 "SEX			TEXT," +
					 "LIVERY		TEXT," +
					 "HERE           INT," +
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
		stmt.executeUpdate(sql);
		stmt.close();
		System.out.println("Table created successfully");
		} catch ( Exception e ) {
			System.out.println("Table already existed");
		}

	}
}
