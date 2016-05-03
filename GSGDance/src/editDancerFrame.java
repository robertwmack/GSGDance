import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
public class editDancerFrame extends JFrame implements ActionListener {
	int NUMDANCES = 13;
	dancerInfoPanel editInfo = new dancerInfoPanel();
	dancesPanel editDances = new dancesPanel();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();


	dancerLookup lookup = new dancerLookup();
	dancerInfoPanel info = new dancerInfoPanel();
	dancesPanel dances = new dancesPanel();
	String searchName = new String();
	String name;
	String sex;
	String livery;
	int[] danceAbilities = new int[NUMDANCES];
	JFrame mainFrame = GSGDanceMain.home;
	JOptionPane infoFrame = new JOptionPane();
	JButton editDancerButton = new JButton("Commit Changes");
	JButton cancelEditButton = new JButton("Return to Menu");
	private String[] danceNames = {
			"BELLA", "CASTELLANA", "CHIRANZULA", "CONTREPASSO", "GLORIA", "GRACCA", "LEGGIADRIA", "PAVONNE", "SOBEIN", "SPAGNOLETTA", "SPAGNOLETTAC",
			"VILLANELLA", "VILLANELLAP"};
	
	
	public editDancerFrame(String title) {
		super(title);
		setSize(width, height);
		setLayout(new FlowLayout());
		add(lookup);
		lookup.submit.addActionListener(this);
		lookup.deleteButton.addActionListener(this);
		lookup.cancel.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if(a.getSource() == lookup.submit) {
			Connection c = null;
			Statement stmt = null;
			searchName = lookup.getSearchName();
			
			try {
				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
				c.setAutoCommit(false);
				stmt = c.createStatement();
				String q = "SELECT * FROM DANCERS WHERE NAME = '" + searchName +"';";
				ResultSet rs = stmt.executeQuery(q );
				name = rs.getString("name");
				sex = rs.getString("sex");
				livery = rs.getString("livery");
				for (int i = 0; i < NUMDANCES; i++) {
					danceAbilities[i] = rs.getInt(i + 4);
					System.out.println(danceAbilities[i]);
				}
				for (int i = 0; i < danceAbilities.length; i++) {
					switch(danceAbilities[i]) {
					case 0:
						dances.setDances((i * 4) + 0);
						break;
					case 1:
						dances.setDances((i * 4) + 1);
						break;
					case 2:
						dances.setDances((i * 4) + 2);
						break;
					case 3:
						dances.setDances((i * 4) + 3);
					}
				}
				lookup.setVisible(false);
				add(info);
				add(dances);
				info.setName(name);
				info.setSexChoice(sex);
				info.setClassChoice(livery);
				rs.close();
				stmt.close();
				c.close();
				add(editDancerButton);
				add(cancelEditButton);
				editDancerButton.addActionListener(this);
				cancelEditButton.addActionListener(this);
			} catch (Exception e) {
				System.err.println(e.getClass().getName() + ": " + e.getMessage() );
				System.exit(0);
			}
		} else if (a.getSource() == lookup.deleteButton) {
			searchName = lookup.getSearchName();
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + searchName, "WARNING", JOptionPane.YES_NO_CANCEL_OPTION) == JOptionPane.YES_OPTION) {
				Connection c = null;
				Statement stmt = null;
				try {
					Class.forName("org.sqlite.JDBC");
					c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
					c.setAutoCommit(false);
					stmt = c.createStatement();
					String sql = "DELETE from DANCERS where NAME='" + searchName + "';"; 
					System.out.println(sql);
					stmt.executeUpdate(sql);
					c.commit();
					stmt.close();
					c.close();
					infoFrame.showMessageDialog(null, searchName + " deleted.", "Message Window", JOptionPane.INFORMATION_MESSAGE);
				}  catch (Exception e) {
					System.out.println(e.getClass().getName() + ": " + e.getMessage() );
					infoFrame.showMessageDialog(null, name + " could not be deleted at this time.", "Message Window", JOptionPane.INFORMATION_MESSAGE);
				}
				lookup.name.removeAllItems();
				lookup.getNames();
				lookup.revalidate();
			}
		} else if (a.getSource() == lookup.cancel) {
			cancelButtonPressed();
		} else if (a.getSource() == editDancerButton) {
			makeChanges();
		}else if (a.getSource() == cancelEditButton) {
			cancelButtonPressed();
		}
	}
	
	public void makeChanges() {
		Connection c = null;
		Statement stmt = null;
		String name = info.getName();
		String sex = info.getSex();
		String livery = info.getClassChoice();
		int[] danceAbilities = new int[dances.danceGroups.length];
		danceAbilities = dances.getDances();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully for changes");
			stmt = c.createStatement();
			String sql = "UPDATE DANCERS SET " +
					"NAME = '" + name + "', " +
					"SEX = '" + sex + "', " +
					"LIVERY = '" + livery + "' ";
			for (int i = 0; i < danceAbilities.length; i++) {
				sql = sql + ", " + danceNames[i] + " = " + danceAbilities[i];
			}
			sql = sql + " where NAME = '" + searchName + "';";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			infoFrame.showMessageDialog(null, name + " Added", "Message Window", JOptionPane.INFORMATION_MESSAGE);
		} catch ( Exception e) {
			System.err.println( e.getClass().getName() + ": 2-" + e.getMessage() );
			infoFrame.showMessageDialog(null, "Error in adding " + name, "Message Window", JOptionPane.INFORMATION_MESSAGE);
		}
		cancelButtonPressed();
	}

	private void cancelButtonPressed() {
		this.setVisible(false);
		mainFrame.setVisible(true);
	}
}
