import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class rosterFrame extends JFrame implements ActionListener{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	rosterPanel roster = new rosterPanel();
	JButton submitButton = new JButton("Calculate Dances");
	JButton resetButton = new JButton("Reset Form");
	JButton cancelButton = new JButton("Cancel");
	
	public rosterFrame(String title) {
		setSize(width, height);
		JScrollPane scrollpane = new JScrollPane(roster);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		getContentPane().add(submitButton);
		submitButton.addActionListener(this);
		getContentPane().add(resetButton);
		resetButton.addActionListener(this);
		getContentPane().add(cancelButton);
		cancelButton.addActionListener(this);
		getContentPane().add(scrollpane);
		roster.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == submitButton) {
			rosterPanel.updateAttendance();
			rosterPanel.setDancers();
			reportPanel rPanel = new reportPanel();
			rPanel.setVisible(true);
		}
	}
	
}
