import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;


public class ZombieInvasion extends Game{
	private JFrame frame;
	private JPanel mainPanel = this;
	private JLayeredPane layerPane;
	private JLabel scopeLabel;
	private ArrayList <JLabel> zombieLabels = new ArrayList();
	public ZombieInvasion(){
		frame = new JFrame("Zombie Invasion Test"); //REMOVE FRAME BEFORE COMPLEETION
		
		layerPane = new JLayeredPane();
		layerPane.setSize(1200, 720);
		layerPane.setPreferredSize(layerPane.getSize());
		
		ParsedImageIcon background = new ParsedImageIcon(".\\ZombieInvasion\\background.jpg");
		JLabel backgroundLabel = new JLabel(background);
		backgroundLabel.setSize(background.getIconWidth(), background.getIconHeight());
		
		layerPane.add(backgroundLabel, new Integer(0));
		
		this.add(layerPane);
		this.setLayout(new GridLayout(0, 1, 0, 0));
		this.setPreferredSize(layerPane.getSize());
		
		ParsedImageIcon scope = new ParsedImageIcon(".\\ZombieInvasion\\scope.png");
		scopeLabel = new JLabel(scope);
		scopeLabel.setSize(scope.getIconWidth(), scope.getIconHeight());
		layerPane.add(scopeLabel, new Integer(300));
		mainPanel.addMouseMotionListener(new MouseAdapter(){
		    public void mouseMoved(MouseEvent mouseEvent) {
		        int xTranslation = scopeLabel.getWidth()/2;
		        int yTranslation = scopeLabel.getHeight()/2;
		        
		        Point panelLocation = mainPanel.getLocationOnScreen();
		        Point mouseLocation = mouseEvent.getLocationOnScreen();
		        
		        int x = (int)(mouseLocation.getX() - panelLocation.getX() - xTranslation);
		        int y = (int)(mouseLocation.getY() - panelLocation.getY() - yTranslation);
		        scopeLabel.setLocation(x, y);
		      }
		    public void mouseDragged(MouseEvent mouseEvent) {
		        int xTranslation = scopeLabel.getWidth()/2;
		        int yTranslation = scopeLabel.getHeight()/2;
		        
		        Point panelLocation = mainPanel.getLocationOnScreen();
		        Point mouseLocation = mouseEvent.getLocationOnScreen();
		        
		        int x = (int)(mouseLocation.getX() - panelLocation.getX() - xTranslation);
		        int y = (int)(mouseLocation.getY() - panelLocation.getY() - yTranslation);
		        scopeLabel.setLocation(x, y);
		      }
			
		});
		BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImage, new Point(0, 0), "blank");
		this.setCursor(blankCursor);
		
		for(int elementCounter=0; elementCounter<100; elementCounter++){
			spawnZombie();
		}
		
		layerPane.repaint();
		frame.add(this);
		layerPane.setVisible(true);
		frame.setVisible(true);
		frame.pack();

		

		
	}
	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String args[]){
		new ZombieInvasion();
	}
	public void spawnZombie(){
		ParsedImageIcon zombie = new ParsedImageIcon(".\\ZombieInvasion\\zombie.gif");
		JLabel zombieLabel = new JLabel(zombie);
		zombieLabel.setSize(zombie.getIconWidth(), zombie.getIconHeight());
		
		Random randomNumber = new Random();
		int x = randomNumber.nextInt(layerPane.getWidth()-zombie.getIconWidth());
		int y = randomNumber.nextInt(layerPane.getHeight()-zombie.getIconHeight());
		
		zombieLabel.setLocation(x, y);
		
		layerPane.add(zombieLabel, new Integer(1));
		
		zombieLabels.add(zombieLabel);
	}

}
