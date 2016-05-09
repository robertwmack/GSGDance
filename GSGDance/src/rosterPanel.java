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
	String[] statusArray = {"AM_PM", "AM", "PM", ""};
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
	
	final int DANCE_COUNT = GSGDanceMain.getDanceCount();
	
	int[] amMenYes = new int[DANCE_COUNT];
	int[] amMenMaybe = new int[DANCE_COUNT];
	int[] amWomenYes = new int[DANCE_COUNT];
	int[] amWomenMaybe = new int[DANCE_COUNT];
	int[] pmMenYes = new int[DANCE_COUNT];
	int[] pmMenMaybe = new int[DANCE_COUNT];
	int[] pmWomenYes = new int[DANCE_COUNT];
	int[] pmWomenMaybe = new int[DANCE_COUNT];
	
	
	
	public rosterPanel() {
		setLayout(layout);
		getNames();
		add(submitButton);
		submitButton.addActionListener(this);
		add(resetButton);
		resetButton.addActionListener(this);
		add(cancelButton);
		cancelButton.addActionListener(this);
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
			ResultSet rs = stmt.executeQuery("SELECT NAME, LIVERY FROM DANCERS ORDER BY NAME;" );
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
			System.err.println(e.getClass().getName() + ":Counting Records Error: " + e.getMessage() );
			System.exit(0);
		}
		return count;
	}
	
	public int getDancerCount() {
		return dancerCount;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			updateAttendance();
			setDancers();
			//display dances to perform
		}
	}
	
	private void updateAttendance() {
		Connection c = null;
		Statement stmt = null;
		String dancerName;
		int presence;
		String available;
		
		for(int i = 0; i < dancerCount; i++) {
			dancerName = nameLabels[i].getText();
			if (nameBoxes[i].isSelected() == true) {
				presence = 1;
			} else {
				presence = 0;
			}
			available = statusArray[nameStatus[i].getSelectedIndex()];
			try {
				Class.forName("org.sqlite.JDBC");
				System.out.println("Stage 1");
				c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
				System.out.println("Stage 1");
				c.setAutoCommit(false);
				System.out.println("Stage 1");
				stmt = c.createStatement();
				System.out.println("Stage 1");
				String q = "UPDATE DANCERS SET HERE=" + presence + ", STATUS='" + available + "' WHERE NAME ='" + dancerName + "';";
				System.out.println(q);
				stmt.executeUpdate(q);
				stmt.close();
				c.close();
			} catch (Exception e) {
					System.err.println(e.getClass().getName() + ":Updating for attendance error: " + e.getMessage() );
					System.exit(0);
			}
		}
	}
	
	private void setDancers() {
		amMenYes
		/*
		 * SELECT {BELLA, CASTELLANA, CHIRANZULA, CONTREPASSO, GLORIA, GRACCA, LEGGIADRIA, PAVONNE, SOBEIN, SPAGNOLETTA, SPAGNOLETTAC, VILLANELLA, VILLANELLAP WHERE HERE=1 AND SEX='MALE' AND (STATUS="AM_PM OR STATUS="AM");	
		 */
		amMenMaybe
		amWomenYes
		amWomenMaybe
		pmMenYes
		pmMenMaybe
		pmWomenYes
		pmWomenMaybe
	}
	
}
