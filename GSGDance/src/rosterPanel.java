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


@SuppressWarnings("serial")
public class rosterPanel extends JPanel {
	GridLayout layout = new GridLayout(0,3);
	static int dancerCount = makeDancerCount();
	static String[] statusArray = {"AM_PM", "AM", "PM", ""};
	String[] names = new String[dancerCount];
	String[] livery = new String[dancerCount];
	static JLabel[] nameLabels = new JLabel[dancerCount];
	static JCheckBox[] nameBoxes = new JCheckBox[dancerCount];
	@SuppressWarnings("rawtypes")
	static
	JComboBox[] nameStatus = new JComboBox[dancerCount];
	JLabel nameHeader = new JLabel();
	JLabel attendanceHeader = new JLabel();
	JLabel statusHeader = new JLabel();

	private static String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private static int danceCount = dances.length;
	static int[] amDancesGood = new int[danceCount];
	static int[] amDancesOk = new int[danceCount];
	
	

		
	static int[][][][] dancersAbleToDance = new int[2][2][2][13];
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public rosterPanel() {
		setLayout(layout);
		getNames();
		
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
	private static int makeDancerCount() {
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
	
	static void updateAttendance() {
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
				c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String q = "UPDATE DANCERS SET HERE=" + presence + ", STATUS='" + available + "' WHERE NAME ='" + dancerName + "';";
				stmt.executeUpdate(q);
				c.commit();
				stmt.close();
				c.close();
			} catch (Exception e) {
					System.err.println(e.getClass().getName() + ":Updating for attendance error: " + e.getMessage() );
					System.exit(0);
			}
		}
	}
	
	static void setDancers() {
		String[] sex = {"'Male'", "'Female'"};
		String[] timeOfDay = {"'%AM%'", "'%PM%'"};
		int[] strength = {3, 2};
		
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			stmt = c.createStatement();
			for (int x = 0; x < sex.length; x++) {
				for (int y = 0; y < timeOfDay.length; y++) {
					for (int z = 0; z < strength.length; z++) {
						for (int i = 0; i < danceCount; i++) {
							String q = "SELECT COUNT(*) FROM DANCERS WHERE " + dances[i] + "=" + strength[z] +
									" AND SEX=" + sex[x] + " AND HERE=1 AND STATUS LIKE " + timeOfDay[y] + ";";
							System.out.println(q);
							ResultSet rs = stmt.executeQuery(q);
							dancersAbleToDance[x][y][z][i] = rs.getInt(1);
							System.out.println(dancersAbleToDance[x][y][z][i]);
							rs.close();
						}
					}
				}
			}
			
			stmt.close();
			c.commit();
			c.close();
		} catch ( Exception e) {
			System.err.println( e.getClass().getName() + ": Error Finding Dancers to Dance: " + e.getMessage() );
		}
		
		//this will have to be changed for next version
		
		amDancesGood[0] = (dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0]) / 3;
		amDancesOk[0] = (dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0] + dancersAbleToDance[0][0][1][0] + dancersAbleToDance[1][0][1][0]) / 3;
		
		amDancesGood[1] = (dancersAbleToDance[0][0][0][1] + dancersAbleToDance[1][0][0][1]) / 2;
		amDancesOk[1] = (dancersAbleToDance[0][0][0][1] + dancersAbleToDance[1][0][0][1] + dancersAbleToDance[0][0][1][1] + dancersAbleToDance[1][0][1][1]) / 2;
		
		amDancesGood[2] = (dancersAbleToDance[0][0][0][2] + dancersAbleToDance[1][0][0][2]) / 2;
		amDancesOk[2] = (dancersAbleToDance[0][0][0][2] + dancersAbleToDance[1][0][0][2] + dancersAbleToDance[0][0][1][2] + dancersAbleToDance[1][0][1][2]) / 2;
		
		amDancesGood[3] = (dancersAbleToDance[0][0][0][3] + dancersAbleToDance[1][0][0][3]) / 2;
		amDancesOk[3] = (dancersAbleToDance[0][0][0][3] + dancersAbleToDance[1][0][0][3] + dancersAbleToDance[0][0][1][3] + dancersAbleToDance[1][0][1][3]) / 2;
		
		amDancesGood[4] = (dancersAbleToDance[0][0][0][4] + dancersAbleToDance[1][0][0][4]) / 3;
		amDancesOk[4] = (dancersAbleToDance[0][0][0][4] + dancersAbleToDance[1][0][0][4] + dancersAbleToDance[0][0][1][4] + dancersAbleToDance[1][0][1][4]) / 2;
		
		amDancesGood[5] = 0; //(dancersAbleToDance[0][0][0][5] + dancersAbleToDance[1][0][0][5]) / 2;
		amDancesOk[5] = 0; //(dancersAbleToDance[0][0][0][5] + dancersAbleToDance[1][0][0][5] + dancersAbleToDance[0][0][1][5] + dancersAbleToDance[1][0][1][5]) / 2;
		
		if(dancersAbleToDance[0][0][0][6] > dancersAbleToDance[1][0][0][6]) {
			amDancesGood[6] = dancersAbleToDance[0][0][0][6]  / 2;
		} else {
			amDancesGood[6] = dancersAbleToDance[1][0][0][6]  / 2;
		}
		
		if((dancersAbleToDance[0][0][0][6] + dancersAbleToDance[0][0][1][6]) > (dancersAbleToDance[1][0][0][6] + dancersAbleToDance[1][0][1][6])) {
			amDancesOk[6] = (dancersAbleToDance[0][0][0][6] + dancersAbleToDance[0][0][1][6])  / 2;
		} else {
			amDancesOk[6] = (dancersAbleToDance[1][0][0][6] + dancersAbleToDance[1][0][1][6])  / 2;
		}
		
		amDancesGood[7] = (dancersAbleToDance[0][0][0][7] + dancersAbleToDance[1][0][0][7]) / 2;
		amDancesOk[7] = (dancersAbleToDance[0][0][0][7] + dancersAbleToDance[1][0][0][7] + dancersAbleToDance[0][0][1][7] + dancersAbleToDance[1][0][1][7]) / 2;
		
		amDancesGood[8] = (dancersAbleToDance[0][0][0][8] + dancersAbleToDance[1][0][0][8]) / 2;
		amDancesOk[8] = (dancersAbleToDance[0][0][0][8] + dancersAbleToDance[1][0][0][8] + dancersAbleToDance[0][0][1][8] + dancersAbleToDance[1][0][1][8]) / 2;
		
		amDancesGood[9] = dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0];
		amDancesOk[9] = dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0] + dancersAbleToDance[0][0][1][0] + dancersAbleToDance[1][0][1][0];
		
		amDancesGood[10] = (dancersAbleToDance[0][0][0][10] + dancersAbleToDance[1][0][0][10]) / 2;
		amDancesOk[10] = (dancersAbleToDance[0][0][0][10] + dancersAbleToDance[1][0][0][10] + dancersAbleToDance[0][0][1][10] + dancersAbleToDance[1][0][1][10]) / 2;
		
		amDancesGood[11] = 0; //(dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0]) / 3;
		amDancesOk[11] = 0; //(dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0] + dancersAbleToDance[0][0][1][0] + dancersAbleToDance[1][0][1][0]) / 3;
		
		amDancesGood[12] = (dancersAbleToDance[0][0][0][12] + dancersAbleToDance[1][0][0][12]) / 2;
		amDancesOk[12] = (dancersAbleToDance[0][0][0][12] + dancersAbleToDance[1][0][0][12] + dancersAbleToDance[0][0][1][12] + dancersAbleToDance[1][0][1][12]) / 2;
			
	}

	public static int[] getAmDancesGood() {
		return amDancesGood;
	}

	public static int[] getAmDancesOk() {
		return amDancesOk;
	}
	
}
