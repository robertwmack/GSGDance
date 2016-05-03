
public class GSGDanceMain {
	static mainFrame home = new mainFrame();

	public static void main(String[] args) {
		LoadDatabase.loadDatabase();
		home.setVisible(true);
	}
	
	public void startProgram() {
		
	}
	
	public Object getHome() {
		return home;
	}

}
