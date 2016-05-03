import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class rosterFrame extends JFrame{
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	rosterPanel roster = new rosterPanel();
	
	public rosterFrame(String title) {
		super(title);
		setSize(width, height);
		setLayout(new FlowLayout());
		add(roster);
	}

}
