import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class rosterFrame extends JFrame{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	rosterPanel roster = new rosterPanel();
	FlowLayout layout = new FlowLayout();
	
	
	public rosterFrame(String title) {
		setSize(width, height);
		setLayout(layout);
		add(roster);
		System.out.println("Added roster panel");
		roster.setVisible(true);
	}
}
