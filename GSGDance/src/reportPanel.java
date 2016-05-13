import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class reportPanel {
	JLabel[] reportLabels = new JLabel[(danceCount + 3) * 3];
	private static String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private static int danceCount = dances.length;
	private static int[] amDancesGood = rosterPanel.getAmDancesGood();
	private static int[] amDancesOk = rosterPanel.getAmDancesOk();
	public reportPanel() {
		JPanel reportPanel = new JPanel();
		reportPanel.setLayout(new GridLayout(14,3));
		reportLabels[0] = new JLabel("Dances");
		reportPanel.add(reportLabels[0]);
		reportLabels[1] = new JLabel("AM Dancers");
		reportPanel.add(reportLabels[1]);
		reportLabels[2] = new JLabel("PM Dancers");
		reportPanel.add(reportLabels[2]);
		for (int i = 1; i < (danceCount + 1); i++) {
			reportLabels[(3 * i)] = new JLabel(dances[i - 1]);
			reportPanel.add(reportLabels[(3 * i)]);
			reportLabels[(3 * i) + 1] = new JLabel(amDancesGood[i - 1] + " " + amDancesOk[i - 1]);
			reportPanel.add(reportLabels[(3 * i) + 1]);
			//set to 0s for Janesville
			reportLabels[(3 * i) + 2] = new JLabel("not ready");
			reportPanel.add(reportLabels[(3 * i) + 2]);
		}
	}
	public void setVisible(boolean b) {
		this.setVisible(b);
		
	}

}
