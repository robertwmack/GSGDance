import java.awt.GridLayout;
import javax.swing.*;

@SuppressWarnings("serial")
public class dancesPanel extends JPanel
{
	private String[] dances = GSGDanceMain.getDanceNames(); //gets the names of the dances
	private int danceCount = dances.length; 
	private String[] abilities = {
			"Can't Do",
			"Don't Know",
			"Needs Assistance",
			"Able To Perform"
	};
	ButtonGroup[] danceGroups = new ButtonGroup[danceCount];
	private int buttonCounter =0;
	JRadioButton[] buttons = new JRadioButton[(danceCount) * (abilities.length)];
	JLabel[] danceLabels = new JLabel[danceCount];
	GridLayout layout = new GridLayout(0,6);
	JCheckBox[] boxes = new JCheckBox[danceCount];
	
	
	public dancesPanel()
	{
		setLayout(layout);
		for (int i = 0; i < danceGroups.length; i++) {
			danceGroups[i] = new ButtonGroup();
			danceLabels[i] = new JLabel(dances[i]);
			add(danceLabels[i]);
			for (int x = 0; x < abilities.length; x++) {
				buttons[buttonCounter] = new JRadioButton(abilities[x]);
				if (x == 1) {
					buttons[buttonCounter].setSelected(true);
				}
				danceGroups[i].add(buttons[buttonCounter]);
				add(buttons[buttonCounter]);
				buttonCounter++;
			}
			boxes[i] = new JCheckBox("Teach Me");
			boxes[i].setEnabled(false);
			add(boxes[i]);		}
	}
	
	public int[] getDances(){
		int[] danceAbilities = new int[dances.length];
		buttonCounter = 0;
		for (int i = 0; i < danceGroups.length; i++) {//for each dance
			for (int x = 0; x < abilities.length; x++) { //for each ability level
				if (buttons[buttonCounter].isSelected() == true) {//this line needs work
					danceAbilities[i] = x;
				}
				buttonCounter++;
			}
		}
		return danceAbilities;
	}

	public void setDances(int n) {
		buttons[n].setSelected(true);
	}
	
	public void resetForm() {
		buttonCounter = 0;
		for (int i = 0; i < danceGroups.length; i++) {
			for (int x = 0; x < abilities.length; x++) {
				if (x == 1) {
					buttons[buttonCounter].setSelected(true);
				}
				buttonCounter++;
			}
		}
	}
	
}
