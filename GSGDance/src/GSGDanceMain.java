//main class
public class GSGDanceMain {
	static mainFrame home = new mainFrame();
	private static String[] dances = {
			"Bella",
			"Castellana",
			"Chiranzula",
			"Contrepasso",
			"Gloria",
			"Gracca",
			"Leggiadria",
			"Pavonne",
			"So_Bein_Mi_Chi",
			"Spagnoletta",
			"Spagnoletta_Couples",
			"Villanella",
			"Villanella_Couples"
	};
	
	
	public static void main(String[] args) {
		LoadDatabase.loadDatabase();
		home.setVisible(true);
	}
	
	
	public Object getHome() {
		return home;
	}
	
	public static int getDanceCount() {
		return dances.length;
	}
	
	public static String[] getDanceNames() {
		return dances;
	}
}
