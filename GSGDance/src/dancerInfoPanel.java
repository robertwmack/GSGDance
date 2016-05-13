import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class dancerInfoPanel extends JPanel {
	private JTextField name = new JTextField(15);
	private String[] sexArray = {"Female", "Male"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox sexChoice = new JComboBox(sexArray);
	private String[] classArray = {"Noble", "Royal Livery", "Host Household"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox classChoice = new JComboBox(classArray);
	private JLabel nameLabel = new JLabel("Name");
	private JLabel sexChoiceLabel = new JLabel("Sex");
	private JLabel classChoiceLabel = new JLabel("Class");
	GridLayout layout = new GridLayout(0,2);
	
	public dancerInfoPanel() {
		add(nameLabel);
		add(name);
		add(sexChoiceLabel);
		add(sexChoice);
		add(classChoiceLabel);
		add(classChoice);
	}

	
	public String getName() {
		return name.getText();
	}


	public void setName(String name) {
		this.name.setText(name);
	}


	public void setSexChoice(String sex) {
		sexChoice.setSelectedItem(sex);
	}

	public void setClassChoice(String livery) {
		classChoice.setSelectedItem(livery);
	}
	
	public String getSex() {
		return sexArray[sexChoice.getSelectedIndex()];
	}
	
	public String getClassChoice() {
		return classArray[classChoice.getSelectedIndex()];
	}
	
	public void resetForm() {
		setName("");
		setSexChoice(sexArray[0]);
		setClassChoice(classArray[0]);
	}
}
