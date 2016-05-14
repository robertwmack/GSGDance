import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class reportFrame extends JFrame implements ActionListener {
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int) screenSize.getWidth();
	int height = (int) screenSize.getHeight();
	reportPanel report = new reportPanel();
	JButton doneButton = new JButton("Done");
	static JFrame mainFrame = GSGDanceMain.home;

	public reportFrame(String title){
		super(title);
		setSize(width, height);
		setLayout(new FlowLayout());
		add(report);
		doneButton.addActionListener(this);
		add(doneButton);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		mainFrame.setVisible(true);
	}

}
