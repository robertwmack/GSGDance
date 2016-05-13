import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class mainFrame extends JFrame implements ActionListener {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	JButton newDancer = new JButton("New Dancer");
	JButton editDancer = new JButton("Edit Dancer");
	JButton calcDance = new JButton("Let's Dance");
	
	public mainFrame()
	{
		super("Saint George Dance Mistress Brain");
		setSize(width, height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new FlowLayout());
		add(newDancer);
		add(editDancer);
		add(calcDance);
		newDancer.addActionListener(this);
		editDancer.addActionListener(this);
		calcDance.addActionListener(this);
	}
	@Override
	//calls frames
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newDancer) {
			this.setVisible(false);
			newDancerFrame newDancer = new newDancerFrame("Add a New Dancer");
			newDancer.setVisible(true);
			newDancer.setDefaultCloseOperation(EXIT_ON_CLOSE);
		} else if(e.getSource() == editDancer) {
			this.setVisible(false);
			editDancerFrame editDancer = new editDancerFrame("Edit Dancer");
			editDancer.setVisible(true);
			editDancer.setDefaultCloseOperation(EXIT_ON_CLOSE);
		} else if(e.getSource() == calcDance) {
			this.setVisible(false);
			rosterFrame roster = new rosterFrame("Let's Dance");
			roster.setVisible(true);
			roster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		
	}
	
	public void returnHome() {
		this.setVisible(true);
	}

}
