import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.*;

public class dancesPanel extends JPanel
{

	
	private String[] dances = {
			"Bella",
			"Castellana",
			"Chiranzula",
			"Contrepasso",
			"Gloria",
			"Gracca",
			"Leggiadria",
			"Pavonne",
			"So Bein Mi Chi",
			"Spagnoletta",
			"Spagnoletta Couples",
			"Villanella",
			"Villanella Couples"
	};
	private String[] abilities = {
			"Can't Do",
			"Don't Know",
			"Needs Assistance",
			"Able To Perform"
	};
	ButtonGroup[] danceGroups = new ButtonGroup[dances.length];
	private int buttonCounter =0;
	JRadioButton[] buttons = new JRadioButton[(dances.length) * (abilities.length)];
	JLabel[] danceLabels = new JLabel[dances.length];
	GridLayout layout = new GridLayout(0,6);
	JCheckBox[] boxes = new JCheckBox[dances.length];
	
	
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
