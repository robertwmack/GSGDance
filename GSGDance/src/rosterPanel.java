import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class rosterPanel extends JPanel implements ActionListener{
	GridLayout layout = new GridLayout(0,3);
	int dancerCount = makeDancerCount();
	String[] statusArray = {"AM/PM", "AM/-", "-/PM", "-/-"};
	String[] names = new String[dancerCount];
	String[] livery = new String[dancerCount];
	JLabel[] nameLabels = new JLabel[dancerCount];
	JCheckBox[] nameBoxes = new JCheckBox[dancerCount];
	JComboBox[] nameStatus = new JComboBox[dancerCount];
	JLabel nameHeader = new JLabel();
	JLabel attendanceHeader = new JLabel();
	JLabel statusHeader = new JLabel();
	JButton submitButton = new JButton("Calculate Dances");
	JButton resetButton = new JButton("Reset Form");
	JButton cancelButton = new JButton("Cancel");
	
	public rosterPanel() {
		setLayout(layout);
		getNames();
		add(submitButton);
		submitButton.addActionListener(this);
		nameHeader = new JLabel("Name");
		add(nameHeader);
		attendanceHeader = new JLabel("Present");
		add(attendanceHeader);
		statusHeader = new JLabel("Status");
		add(statusHeader);
		for (int i = 0; i < dancerCount; i++) {
			nameLabels[i] = new JLabel(names[i]);
			nameBoxes[i] = new JCheckBox();
			nameStatus[i] = new JComboBox(statusArray);
			add(nameLabels[i]);
			add(nameBoxes[i]);
			add(nameStatus[i]);
			if(livery[i].equals("Royal Livery")){
				nameStatus[i].setSelectedIndex(1);
			} else if (livery[i].equals("Host Household")) {
				nameStatus[i].setSelectedIndex(3);
			}
			System.out.println(nameLabels[i].getText() + " " + livery[i] + " " + nameStatus[i].getSelectedIndex());
		}
	}

	private void getNames() {
		Connection c = null;
		Statement stmt = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT NAME, LIVERY FROM DANCERS;" );
			int i = 0;
			while (rs.next()) {
				names[i] = rs.getString("NAME");
				livery[i] = rs.getString("LIVERY");
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
	private int makeDancerCount() {
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
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ":Test 5: " + e.getMessage() );
			System.exit(0);
		}
		return count;
	}
	
	public int getDancerCount() {
		return dancerCount;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
