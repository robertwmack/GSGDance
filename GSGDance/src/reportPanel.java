import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class reportPanel extends JPanel {
	JLabel[] reportLabels = new JLabel[(danceCount + 3) * 3];
	private static String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private static int danceCount = dances.length;
	private static int[] amDancesGood = rosterPanel.getAmDancesGood();
	private static int[] amDancesOk = rosterPanel.getAmDancesOk();
	private static int[] pmDancesGood = rosterPanel.getPmDancesGood();
	private static int[] pmDancesOk = rosterPanel.getPmDancesOk();
	public reportPanel() {
		setLayout(new GridLayout(14,3));
		reportLabels[0] = new JLabel("Dances");
		add(reportLabels[0]);
		reportLabels[1] = new JLabel("AM Dancers");
		add(reportLabels[1]);
		reportLabels[2] = new JLabel("PM Dancers");
		add(reportLabels[2]);
		for (int i = 1; i < (danceCount + 1); i++) {
			reportLabels[(3 * i)] = new JLabel(dances[i - 1]);
			add(reportLabels[(3 * i)]);
			reportLabels[(3 * i) + 1] = new JLabel(amDancesGood[i - 1] + " - " + amDancesOk[i - 1]);
			add(reportLabels[(3 * i) + 1]);
			reportLabels[(3 * i) + 2] = new JLabel(pmDancesGood[i - 1] + " - " + pmDancesOk[i - 1]);
			add(reportLabels[(3 * i) + 2]);
		}
	}
}
