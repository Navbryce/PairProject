
public class Window {

	private static void runGUI() {
		Snake snake = new Snake();		
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				runGUI();
			}
		});
	}
	
}
