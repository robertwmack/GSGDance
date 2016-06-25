import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
	JButton submitButton = new JButton("Calculate Dances");
	JButton resetButton = new JButton("Reset Form");
	JButton cancelButton = new JButton("Cancel");
	private static String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private static int danceCount = dances.length;
	static int[] amDancesGood = new int[danceCount];
	static int[] amDancesOk = new int[danceCount];
	static int[] pmDancesGood = new int[danceCount];
	static int[] pmDancesOk = new int[danceCount];
	

		
	static int[][][][] dancersAbleToDance = new int[2][2][2][13];
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public rosterPanel() {
		setLayout(layout);
		getNames();
		add(submitButton);
		add(resetButton);
		add(cancelButton);
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
		
	// Bella
		// AM is any group of 3
		amDancesGood[0] = (dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0]) / 3;
		amDancesOk[0] = (dancersAbleToDance[0][0][0][0] + dancersAbleToDance[1][0][0][0] + dancersAbleToDance[0][0][1][0] + dancersAbleToDance[1][0][1][0]) / 3;
		
		// PM is any group of 3
		pmDancesGood[0] = (dancersAbleToDance[0][1][0][0] + dancersAbleToDance[1][1][0][0]) / 3;
		pmDancesOk[0] = (dancersAbleToDance[0][1][0][0] + dancersAbleToDance[1][1][0][0] + dancersAbleToDance[0][1][1][0] + dancersAbleToDance[1][1][1][0]) / 3;
		
	//Castellana
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][1] > dancersAbleToDance[1][0][0][1]) {
			amDancesGood[1] = dancersAbleToDance[1][0][0][1];
		} else {
			amDancesGood[1] = dancersAbleToDance[0][0][0][1];
		}
		if ((dancersAbleToDance[0][0][0][1] +  dancersAbleToDance[0][0][1][1]) > (dancersAbleToDance[1][0][0][1]  + dancersAbleToDance[1][0][1][1])) {
			amDancesOk[1] = dancersAbleToDance[1][0][0][1]  + dancersAbleToDance[1][0][1][1];
		} else {
			amDancesOk[1] = dancersAbleToDance[0][0][0][1] +  dancersAbleToDance[0][0][1][1];
		}
		
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][1] > dancersAbleToDance[1][1][0][1]) {
			pmDancesGood[1] = dancersAbleToDance[1][1][0][1];
		} else {
			pmDancesGood[1] = dancersAbleToDance[0][1][0][1];
		}
		if ((dancersAbleToDance[0][1][0][1] +  dancersAbleToDance[0][1][1][1]) > (dancersAbleToDance[1][1][0][1]  + dancersAbleToDance[1][1][1][1])) {
			pmDancesOk[1] = dancersAbleToDance[1][1][0][1]  + dancersAbleToDance[1][1][1][1];
		} else {
			pmDancesOk[1] = dancersAbleToDance[0][1][0][1] +  dancersAbleToDance[0][1][1][1];
		}
		
	//Chiranzula
		// AM is any group of 2
		amDancesGood[2] = (dancersAbleToDance[0][0][0][2] + dancersAbleToDance[1][0][0][2]) / 2;
		amDancesOk[2] = (dancersAbleToDance[0][0][0][2] + dancersAbleToDance[1][0][0][2] + dancersAbleToDance[0][0][1][2] + dancersAbleToDance[1][0][1][2]) / 2;
		
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][2] > dancersAbleToDance[1][1][0][2]) {
			pmDancesGood[2] = dancersAbleToDance[1][1][0][2];
		} else {
			pmDancesGood[2] = dancersAbleToDance[0][1][0][2];
		}
		if ((dancersAbleToDance[0][1][0][2] +  dancersAbleToDance[0][1][1][2]) > (dancersAbleToDance[1][1][0][2]  + dancersAbleToDance[1][1][1][2])) {
			pmDancesOk[2] = dancersAbleToDance[1][1][0][2]  + dancersAbleToDance[1][1][1][2];
		} else {
			pmDancesOk[2] = dancersAbleToDance[0][1][0][2] +  dancersAbleToDance[0][1][1][2];
		}
		
	//Contrepasso
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][3] > dancersAbleToDance[1][0][0][3]) {
			amDancesGood[3] = dancersAbleToDance[1][0][0][3];
		} else {
			amDancesGood[3] = dancersAbleToDance[0][0][0][3];
		}
		if ((dancersAbleToDance[0][0][0][3] +  dancersAbleToDance[0][0][1][3]) > (dancersAbleToDance[1][0][0][3]  + dancersAbleToDance[1][0][1][3])) {
			amDancesOk[3] = dancersAbleToDance[1][0][0][3]  + dancersAbleToDance[1][0][1][3];
		} else {
			amDancesOk[3] = dancersAbleToDance[0][0][0][3] +  dancersAbleToDance[0][0][1][3];
		}
				
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][3] > dancersAbleToDance[1][1][0][3]) {
			pmDancesGood[3] = dancersAbleToDance[1][1][0][3];
		} else {
			pmDancesGood[3] = dancersAbleToDance[0][1][0][3];
		}
		if ((dancersAbleToDance[0][1][0][3] +  dancersAbleToDance[0][1][1][3]) > (dancersAbleToDance[1][1][0][3]  + dancersAbleToDance[1][1][1][3])) {
			pmDancesOk[3] = dancersAbleToDance[1][1][0][3]  + dancersAbleToDance[1][1][1][3];
		} else {
			pmDancesOk[3] = dancersAbleToDance[0][1][0][3] +  dancersAbleToDance[0][1][1][3];
		}
		
		
	//Gloria
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][4] > dancersAbleToDance[1][0][0][4]) {
			amDancesGood[4] = dancersAbleToDance[1][0][0][4];
		} else {
			amDancesGood[4] = dancersAbleToDance[0][0][0][4];
		}
		if ((dancersAbleToDance[0][0][0][4] +  dancersAbleToDance[0][0][1][4]) > (dancersAbleToDance[1][0][0][4]  + dancersAbleToDance[1][0][1][4])) {
			amDancesOk[4] = dancersAbleToDance[1][0][0][4]  + dancersAbleToDance[1][0][1][4];
		} else {
			amDancesOk[4] = dancersAbleToDance[0][0][0][4] +  dancersAbleToDance[0][0][1][4];
		}
				
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][4] > dancersAbleToDance[1][1][0][4]) {
			pmDancesGood[4] = dancersAbleToDance[1][1][0][4];
		} else {
			pmDancesGood[4] = dancersAbleToDance[0][1][0][4];
		}
		if ((dancersAbleToDance[0][1][0][4] +  dancersAbleToDance[0][1][1][4]) > (dancersAbleToDance[1][1][0][4]  + dancersAbleToDance[1][1][1][4])) {
			pmDancesOk[4] = dancersAbleToDance[1][1][0][4]  + dancersAbleToDance[1][1][1][4];
		} else {
			pmDancesOk[4] = dancersAbleToDance[0][1][0][4] +  dancersAbleToDance[0][1][1][4];
		}
		
	//Gracca
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][5] > dancersAbleToDance[1][0][0][5]) {
			amDancesGood[5] = dancersAbleToDance[1][0][0][5];
		} else {
			amDancesGood[5] = dancersAbleToDance[0][0][0][5];
		}
		if ((dancersAbleToDance[0][0][0][5] +  dancersAbleToDance[0][0][1][5]) > (dancersAbleToDance[1][0][0][5]  + dancersAbleToDance[1][0][1][5])) {
			amDancesOk[5] = dancersAbleToDance[1][0][0][5]  + dancersAbleToDance[1][0][1][5];
		} else {
			amDancesOk[5] = dancersAbleToDance[0][0][0][5] +  dancersAbleToDance[0][0][1][5];
		}
				
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][5] > dancersAbleToDance[1][1][0][5]) {
			pmDancesGood[5] = dancersAbleToDance[1][1][0][5];
		} else {
			pmDancesGood[5] = dancersAbleToDance[0][1][0][5];
		}
		if ((dancersAbleToDance[0][1][0][5] +  dancersAbleToDance[0][1][1][5]) > (dancersAbleToDance[1][1][0][5]  + dancersAbleToDance[1][1][1][5])) {
			pmDancesOk[5] = dancersAbleToDance[1][1][0][5]  + dancersAbleToDance[1][1][1][5];
		} else {
			pmDancesOk[5] = dancersAbleToDance[0][1][0][5] +  dancersAbleToDance[0][1][1][5];
		}
		
	//Leggiadria
		// AM is 1 male and 2 females or 1 female and 2 males
		if(dancersAbleToDance[0][0][0][6] > (dancersAbleToDance[1][0][0][6] * 2)) {
			amDancesGood[6] = dancersAbleToDance[1][0][0][6];
		} else if ((dancersAbleToDance[0][0][0][6] * 2) < dancersAbleToDance[1][0][0][6]) {
			amDancesGood[6] = dancersAbleToDance[0][0][0][6];
		} else {
			amDancesGood[6] = (dancersAbleToDance[0][0][0][6] + dancersAbleToDance[1][0][0][6]) / 3;
		}
		if((dancersAbleToDance[0][0][0][6] + dancersAbleToDance[0][0][1][6]) > (dancersAbleToDance[1][0][0][6] + dancersAbleToDance[1][0][1][6])) {
			amDancesOk[6] = (dancersAbleToDance[0][0][0][6] + dancersAbleToDance[0][0][1][6])  / 2;
		} else {
			amDancesOk[6] = (dancersAbleToDance[1][0][0][6] + dancersAbleToDance[1][0][1][6])  / 2;
		}
		
		
		// PM is 1 male and 2 females or 1 female and 2 males but accounts for the loss of 2 men to dance with the Queen
		if((dancersAbleToDance[0][1][0][6] - 2) > (dancersAbleToDance[1][1][0][6] * 2)) {
			pmDancesGood[6] = dancersAbleToDance[1][1][0][6];
		} else if (((dancersAbleToDance[0][1][0][6] - 2) * 2) < dancersAbleToDance[1][1][0][6]) {
			pmDancesGood[6] = (dancersAbleToDance[0][1][0][6] - 2);
		} else {
			pmDancesGood[6] = ((dancersAbleToDance[0][1][0][6] - 2) + dancersAbleToDance[1][1][0][6]) / 3;
		}
		if((dancersAbleToDance[0][1][0][6] + dancersAbleToDance[0][1][1][6] - 2) > (dancersAbleToDance[1][1][0][6] + dancersAbleToDance[1][1][1][6])) {
			pmDancesOk[6] = (dancersAbleToDance[0][1][0][6] + dancersAbleToDance[0][1][1][6] - 2)  / 2;
		} else {
			pmDancesOk[6] = (dancersAbleToDance[1][1][0][6] + dancersAbleToDance[1][1][1][6])  / 2;
		}
		
		
	//Pavonne
		// AM is 1 male and 1 female
		if ((dancersAbleToDance[0][0][0][7] - 1) > dancersAbleToDance[1][0][0][7]) {
			amDancesGood[7] = dancersAbleToDance[1][0][0][7];
		} else {
			amDancesGood[7] = dancersAbleToDance[0][0][0][7] - 1;
		}
		if ((dancersAbleToDance[0][0][0][7] +  dancersAbleToDance[0][0][1][7] - 1) > (dancersAbleToDance[1][0][0][7]  + dancersAbleToDance[1][0][1][7])) {
			amDancesOk[7] = dancersAbleToDance[1][0][0][7]  + dancersAbleToDance[1][0][1][7];
		} else {
			amDancesOk[7] = dancersAbleToDance[0][0][0][7] +  dancersAbleToDance[0][0][1][7] - 1;
		}
				
		// PM is 1 male and 1 female less 1 male to dance with the Queen
		if ((dancersAbleToDance[0][1][0][7] - 1) > dancersAbleToDance[1][1][0][7]) {
			pmDancesGood[7] = dancersAbleToDance[1][1][0][7];
		} else {
			pmDancesGood[7] = dancersAbleToDance[0][1][0][7] - 1;
		}
		if ((dancersAbleToDance[0][1][0][7] +  dancersAbleToDance[0][1][1][7] - 1) > (dancersAbleToDance[1][1][0][7]  + dancersAbleToDance[1][1][1][7])) {
			pmDancesOk[7] = dancersAbleToDance[1][1][0][7]  + dancersAbleToDance[1][1][1][7];
		} else {
			pmDancesOk[7] = dancersAbleToDance[0][1][0][7] +  dancersAbleToDance[0][1][1][7] - 1;
		}
		
	//So Bein Mi Chi
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][8] > dancersAbleToDance[1][0][0][8]) {
			amDancesGood[8] = dancersAbleToDance[1][0][0][8];
		} else {
			amDancesGood[8] = dancersAbleToDance[0][0][0][8];
		}
		if ((dancersAbleToDance[0][0][0][8] +  dancersAbleToDance[0][0][1][8]) > (dancersAbleToDance[1][0][0][8]  + dancersAbleToDance[1][0][1][8])) {
			amDancesOk[8] = dancersAbleToDance[1][0][0][8]  + dancersAbleToDance[1][0][1][8];
		} else {
			amDancesOk[8] = dancersAbleToDance[0][0][0][8] +  dancersAbleToDance[0][0][1][8];
		}
				
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][8] > dancersAbleToDance[1][1][0][8]) {
			pmDancesGood[8] = dancersAbleToDance[1][1][0][8];
		} else {
			pmDancesGood[8] = dancersAbleToDance[0][1][0][8];
		}
		if ((dancersAbleToDance[0][1][0][8] +  dancersAbleToDance[0][1][1][8]) > (dancersAbleToDance[1][1][0][8]  + dancersAbleToDance[1][1][1][8])) {
			pmDancesOk[8] = dancersAbleToDance[1][1][0][8]  + dancersAbleToDance[1][1][1][8];
		} else {
			pmDancesOk[8] = dancersAbleToDance[0][1][0][8] +  dancersAbleToDance[0][1][1][8];
		}
		
	//Spagnoletta
		// AM and PM are just the number available to dance	
		amDancesGood[9] = dancersAbleToDance[0][0][0][9] + dancersAbleToDance[1][0][0][9];
		amDancesOk[9] = dancersAbleToDance[0][0][0][9] + dancersAbleToDance[1][0][0][9] + dancersAbleToDance[0][0][1][9] + dancersAbleToDance[1][0][1][9];
		pmDancesGood[9] = dancersAbleToDance[0][1][0][9] + dancersAbleToDance[1][1][0][9];
		pmDancesOk[9] = dancersAbleToDance[0][1][0][9] + dancersAbleToDance[1][1][0][9] + dancersAbleToDance[0][1][1][9] + dancersAbleToDance[1][1][1][9];
		
	//Spagnoletta Couples
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][10] > dancersAbleToDance[1][0][0][10]) {
			amDancesGood[10] = dancersAbleToDance[1][0][0][10];
		} else {
			amDancesGood[10] = dancersAbleToDance[0][0][0][10];
		}
		if ((dancersAbleToDance[0][0][0][10] +  dancersAbleToDance[0][0][1][10]) > (dancersAbleToDance[1][0][0][10]  + dancersAbleToDance[1][0][1][10])) {
			amDancesOk[10] = dancersAbleToDance[1][0][0][10]  + dancersAbleToDance[1][0][1][10];
		} else {
			amDancesOk[10] = dancersAbleToDance[0][0][0][10] +  dancersAbleToDance[0][0][1][10];
		}
				
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][10] > dancersAbleToDance[1][1][0][10]) {
			pmDancesGood[10] = dancersAbleToDance[1][1][0][10];
		} else {
			pmDancesGood[10] = dancersAbleToDance[0][1][0][10];
		}
		if ((dancersAbleToDance[0][1][0][10] +  dancersAbleToDance[0][1][1][10]) > (dancersAbleToDance[1][1][0][10]  + dancersAbleToDance[1][1][1][10])) {
			pmDancesOk[10] = dancersAbleToDance[1][1][0][10]  + dancersAbleToDance[1][1][1][10];
		} else {
			pmDancesOk[10] = dancersAbleToDance[0][1][0][10] +  dancersAbleToDance[0][1][1][10];
		}
		
	//Villanella
			// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][11] > dancersAbleToDance[1][0][0][11]) {
			amDancesGood[11] = dancersAbleToDance[1][0][0][11];
		} else {
			amDancesGood[11] = dancersAbleToDance[0][0][0][11];
		}
		if ((dancersAbleToDance[0][0][0][11] +  dancersAbleToDance[0][0][1][11]) > (dancersAbleToDance[1][0][0][11]  + dancersAbleToDance[1][0][1][11])) {
			amDancesOk[11] = dancersAbleToDance[1][0][0][11]  + dancersAbleToDance[1][0][1][11];
		} else {
			amDancesOk[11] = dancersAbleToDance[0][0][0][11] +  dancersAbleToDance[0][0][1][11];
		}
				
		// PM is 1 male and 1 female less 1 male to dance with the Queen
		if ((dancersAbleToDance[0][1][0][11] - 1) > dancersAbleToDance[1][1][0][11]) {
			pmDancesGood[11] = dancersAbleToDance[1][1][0][11];
		} else {
			pmDancesGood[11] = dancersAbleToDance[0][1][0][11] - 1;
		}
		if ((dancersAbleToDance[0][1][0][11] +  dancersAbleToDance[0][1][1][11] - 1) > (dancersAbleToDance[1][1][0][11]  + dancersAbleToDance[1][1][1][11])) {
			pmDancesOk[11] = dancersAbleToDance[1][1][0][11]  + dancersAbleToDance[1][1][1][11];
		} else {
			pmDancesOk[11] = dancersAbleToDance[0][1][0][11] +  dancersAbleToDance[0][1][1][11] - 1;
		}
		
	//Villanella Couples
		// AM is 1 male and 1 female
		if (dancersAbleToDance[0][0][0][12] > dancersAbleToDance[1][0][0][12]) {
			amDancesGood[12] = dancersAbleToDance[1][0][0][12];
		} else {
			amDancesGood[12] = dancersAbleToDance[0][0][0][12];
		}
		if ((dancersAbleToDance[0][0][0][12] +  dancersAbleToDance[0][0][1][12]) > (dancersAbleToDance[1][0][0][12]  + dancersAbleToDance[1][0][1][12])) {
			amDancesOk[12] = dancersAbleToDance[1][0][0][12]  + dancersAbleToDance[1][0][1][12];
		} else {
			amDancesOk[12] = dancersAbleToDance[0][0][0][12] +  dancersAbleToDance[0][0][1][12];
		}
				
		// PM is 1 male and 1 female
		if (dancersAbleToDance[0][1][0][12] > dancersAbleToDance[1][1][0][12]) {
			pmDancesGood[12] = dancersAbleToDance[1][1][0][12];
		} else {
			pmDancesGood[12] = dancersAbleToDance[0][1][0][12];
		}
		if ((dancersAbleToDance[0][1][0][12] +  dancersAbleToDance[0][1][1][12]) > (dancersAbleToDance[1][1][0][12]  + dancersAbleToDance[1][1][1][12])) {
			pmDancesOk[12] = dancersAbleToDance[1][1][0][12]  + dancersAbleToDance[1][1][1][12];
		} else {
			pmDancesOk[12] = dancersAbleToDance[0][1][0][12] +  dancersAbleToDance[0][1][1][12];
		}
	}

	public static int[] getAmDancesGood() {
		return amDancesGood;
	}

	public static int[] getAmDancesOk() {
		return amDancesOk;
	}
	
	public static int[] getPmDancesGood() {
		return pmDancesGood;
	}
	
	public static int[] getPmDancesOk() {
		return pmDancesOk;
	}
	
	public void reset() {
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
		for(int i = 0; i < dancerCount; i++) {
			nameBoxes[i].setSelected(false);
		}
	}
}
