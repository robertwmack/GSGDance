import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
@SuppressWarnings("serial")


public class newDancerFrame extends JFrame implements ActionListener {

	dancerInfoPanel info = new dancerInfoPanel();
	dancesPanel dancesPanel = new dancesPanel();
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	JButton submitButton = new JButton("Submit");
	JButton cancelButton = new JButton("Cancel");
	static JFrame mainFrame = GSGDanceMain.home;
	JOptionPane infoFrame = new JOptionPane();
	private static String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private static int danceCount = dances.length;
	
	
	public newDancerFrame(String title) {
		super(title);
		setSize(width, height);
		setLayout(new FlowLayout());
		add(info);
		add(dancesPanel);
		add(submitButton);
		submitButton.addActionListener(this);
		add(cancelButton);
		cancelButton.addActionListener(this);	
	}
	
	@SuppressWarnings("static-access")
	public void submitButtonPressed() {
		Connection c = null;
		Statement stmt = null;
		String name = info.getName();
		String sex = info.getSex();
		String livery = info.getClassChoice();
		int[] dancerAbilities = dancesPanel.getDances();
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:dancing.db");
			c.setAutoCommit(false);
			System.out.println("Opened database successfully for insertion");
			stmt = c.createStatement();
			String sql = "INSERT INTO DANCERS (NAME, SEX, LIVERY";
			for (int i = 0; i < danceCount; i++) {
				sql = sql + ", " + dances[i];
			}	
			sql = sql + ") VALUES ('" +
					 name + "', '" +
					 sex + "', '" +
					 livery + "'";
			for (int i = 0; i < dancerAbilities.length; i++) {
				sql = sql + ", " + dancerAbilities[i];
			}
			sql = sql + ");";
			System.out.println(sql);
			stmt.executeUpdate(sql);
			stmt.close();
			c.commit();
			c.close();
			infoFrame.showMessageDialog(null, name + " Added", "Message Window", JOptionPane.INFORMATION_MESSAGE);
		} catch ( Exception e) {
			System.err.println( e.getClass().getName() + ": newDancerFrame: " + e.getMessage() );
			infoFrame.showMessageDialog(null, "Error in adding " + name, "Message Window", JOptionPane.INFORMATION_MESSAGE);
		}
		info.resetForm();
		dancesPanel.resetForm();
	}

	public void cancelButtonPressed() {
		this.setVisible(false);
		mainFrame.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			submitButtonPressed();
		}
		if(e.getSource() == cancelButton) {
			cancelButtonPressed();
		}
		
	}
}
