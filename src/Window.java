import javax.swing.JFrame;


public class Window {
	public JFrame frame;
	public Window() {
		frame = new JFrame();
	}
	
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
