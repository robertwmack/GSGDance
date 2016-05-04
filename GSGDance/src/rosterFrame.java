import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class rosterFrame extends JFrame implements ActionListener {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	rosterPanel roster = new rosterPanel();
	JButton submitButton = new JButton("Calculate Dances");
	
	
	//constructor that may need to be changed
	public rosterFrame(String title) {
		super(title);
		setSize(width, height);
		setLayout(new FlowLayout());
		add(roster);
		add(submitButton);
		submitButton.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		int count = roster.getDancerCount();
		for (int i = 0; i < count; i++){
			//if statment checking if present
				//check dance ability
					//tally per livery
		}
		
	}

}
