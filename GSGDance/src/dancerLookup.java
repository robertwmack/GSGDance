
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class dancerLookup extends JPanel {
	JLabel lookupLabel = new JLabel("Whom Do You Seek?");
	JLabel nameLabel = new JLabel("Name");
	JComboBox name = new JComboBox();
	JButton submit = new JButton("Find");
	JButton cancel = new JButton("Cancel");
	JButton deleteButton = new JButton("Delete Dancer");
	
	public dancerLookup() 
	{
		
		getNames();
		add(lookupLabel);
		add(nameLabel);
		add(name);
		add(submit);
		add(deleteButton);
		add(cancel);
	}
	
	public void getNames(){
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM DANCERS;" );
			while (rs.next()) {
				name.addItem(rs.getString("NAME"));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	public String getSearchName() {
		return (String) name.getSelectedItem();
	}
}
