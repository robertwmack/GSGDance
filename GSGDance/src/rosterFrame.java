import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class rosterFrame extends JFrame implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth() / 2;
	int height = (int) screenSize.getHeight();
	rosterPanel roster = new rosterPanel();
	JScrollPane scrollpane = new JScrollPane();
	static JFrame mainFrame = GSGDanceMain.home;
	
	public rosterFrame(String title) {
		setSize(width, height);
		roster = new rosterPanel();
		JScrollPane scrollpane = new JScrollPane(roster);
		setLayout(new BorderLayout());
		add(scrollpane, BorderLayout.CENTER);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setVisible(true);
		roster.submitButton.addActionListener(this);
		roster.cancelButton.addActionListener(this);
		roster.resetButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == roster.submitButton) {
			rosterPanel.updateAttendance();
			rosterPanel.setDancers();
			setVisible(false);
			reportFrame report = new reportFrame("Report");
			report.setVisible(true);
		} else if(e.getSource() == roster.cancelButton) {
			this.setVisible(false);
			mainFrame.setVisible(true);
		} else if(e.getSource() == roster.resetButton) {
			roster.reset();
		}
		
	}
}
