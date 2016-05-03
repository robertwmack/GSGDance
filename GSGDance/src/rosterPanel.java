import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JPanel;


public class rosterPanel extends JPanel{
	GridLayout layout = new GridLayout(0,6);
	String[] names = new String[150];
	
	public rosterPanel() {
		// TODO Auto-generated constructor stub
	}

	private void getNames() {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name FROM DANCERS;" );
			int i = 0;
			while (rs.next()) {
				names[i] = rs.getString("NAME");
				System.out.println(names[i]);
				i++;
			}
			
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}
	
}
