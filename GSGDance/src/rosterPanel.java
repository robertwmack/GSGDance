import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class rosterPanel extends JPanel{
	GridLayout layout = new GridLayout(0,6);
	int dancerCount = getDancerCount();
	String[] names = new String[dancerCount];
	JLabel[] nameLabels = new JLabel[dancerCount];
	JCheckBox[] nameBoxes = new JCheckBox[dancerCount];
	JButton submitButton = new JButton();
	
	public rosterPanel() {
		//still not getting these to display properly
		System.out.println(dancerCount);
		setLayout(layout);
		getNames();
		for (int i = 0; i < dancerCount; i++){
			String workingName = names[i];
			JLabel workingLabel = new JLabel();
			add(workingLabel);
			workingLabel.setName(workingName);	
		}
		add(submitButton);
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
	
	
	//used to get the number of dancers
	private int getDancerCount() {
		int count = 0;
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM DANCERS;" );
			while(rs.next()) {
				count++;
			}
			System.out.println("The returned value is " + count);
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ":Test 5: " + e.getMessage() );
			System.exit(0);
		}
		return count;
	}
}
