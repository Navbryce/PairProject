import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;


public class ZombieInvasion extends Game{
	private JPanel mainPanel = this;
	private JLayeredPane layerPane;
	private JLabel scopeLabel;
	private ArrayList <JLabel> zombieLabels = new ArrayList();
	private ArrayList <Velocity> zombieVelocities = new ArrayList();
	private ArrayList <JLabel> bloodLabels = new ArrayList();
	private ArrayList <Double> bloodAlpha = new ArrayList();
	private ArrayList <Weapon> weaponList = new ArrayList();
	private JLabel weaponLabel=new JLabel();
	private JLabel reloadText;
	private JLabel ammoLabel=new JLabel();
	private int timerCounter=0;
	private Point shootingLocation=null;
	private Weapon currentWeapon;
	private int currentWeaponId=-1;
	final int timerInterval=5;
	private double automaticFire = -1;
	private int reloading = -1;
	private Timer timer;

	public ZombieInvasion(int difficulty){
		generateWeaponList();
		equipWeapon(weaponList.get(0), 0);

		
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
		mainPanel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if(currentWeapon.getFireType()==Weapon.normalWeapon){
					shootingLocation = new Point(e.getX(), e.getY());
				}else{
					automaticFire=timerCounter;
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				automaticFire=-1;

			}
			
		});

		BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImage, new Point(0, 0), "blank");
		this.setCursor(blankCursor);
		
		for(int elementCounter=0; elementCounter<(difficulty+1)*15; elementCounter++){
			spawnZombie();
		}
		
		ActionListener timerListener = new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				timerCounter++;
				double timerIntervalInSec = (double)timerInterval/1000.0;
				double second = timerIntervalInSec * timerCounter; 
				
				decayBlood();
				if(automaticFire!=-1 && ((timerCounter-automaticFire)%currentWeapon.getDelay()==0 || timerCounter-automaticFire<2)){ //%5 adds a delay
					shootingLocation = mainPanel.getMousePosition();

				}
				if(reloading>0){
					reloading--; //Reloading is set to the reload delay of the weapon
				}else if(reloading==0){
					shootingLocation=null;
					automaticFire=-1;
					currentWeapon.reload();
					reloadText.setVisible(false);
					reloading=-1;
					setAmmoLabel(currentWeapon.getBulletsLeftInClip(), currentWeapon.getMagazineSize());
					reloadText.setText("You need to reload. Press R.");
					reloadText.setSize(reloadText.getPreferredSize());
					reloadText.setLocation((int)(layerPane.getWidth()/2.0- reloadText.getWidth()/2.0), (int)(layerPane.getHeight()/2.0 - reloadText.getHeight()));


				}
				if(currentWeapon.needToReload()){
					if(!reloadText.isVisible()){
						reloadText.setVisible(true);
					}
				}else if(shootingLocation!=null){
					shoot((int)shootingLocation.getX(), (int)shootingLocation.getY());
					try {
						currentWeapon.fire();
						setAmmoLabel(currentWeapon.getBulletsLeftInClip(), currentWeapon.getMagazineSize());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("Tried to shoot an ureloaded weapon.");
					}
					
					shootingLocation=null;
				}
				
				
				moveZombies(timerIntervalInSec);
				mainPanel.repaint();
				
			}
			
		};
		//Weapon Panel GUI
		JPanel weaponPanel = new JPanel();
		weaponPanel.setSize(400,200);
		weaponPanel.setOpaque(false);
		weaponPanel.setPreferredSize(new Dimension(400, 200));
		weaponPanel.setLayout(new GridBagLayout());
		weaponPanel.setLocation(layerPane.getWidth()-weaponPanel.getWidth(), layerPane.getHeight()-weaponPanel.getHeight());
		weaponPanel.add(weaponLabel);
		GridBagConstraints ammoLabelConstraints = new GridBagConstraints();
		ammoLabelConstraints.gridy=1;
		ammoLabel.setFont(new Font("Lucida Console", Font.BOLD, 45));
		ammoLabel.setForeground(Color.WHITE);
		weaponPanel.add(ammoLabel, ammoLabelConstraints);
		
		layerPane.add(weaponPanel, new Integer(10));
		
		//Reload GUI
		reloadText = new JLabel("You need to reload. Press R.");
		reloadText.setForeground(Color.yellow);
		reloadText.setFont(new Font("Lucida Console", Font.BOLD, 50));
		reloadText.setSize(reloadText.getPreferredSize());
		reloadText.setLocation((int)(layerPane.getWidth()/2.0- reloadText.getWidth()/2.0), (int)(layerPane.getHeight()/2.0 - reloadText.getHeight()));
		reloadText.setVisible(false);
		layerPane.add(reloadText, new Integer(20));
		
		//Reload Mechanics
		AbstractAction reloadAction = new AbstractAction(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(currentWeapon.getBulletsLeftInClip()!=currentWeapon.getMagazineSize() && reloading==-1){
					reloading=currentWeapon.getReloadDelay();
					reloadText.setText("Reloading...");
					reloadText.setVisible(true);
					reloadText.setSize(reloadText.getPreferredSize());
					reloadText.setLocation((int)(layerPane.getWidth()/2.0- reloadText.getWidth()/2.0), (int)(layerPane.getHeight()/2.0 - reloadText.getHeight()));
				}


				
			}
			
		};
		//Key Bindings
        this.getInputMap().put(KeyStroke.getKeyStroke("R"), "reloadWeapon");
        this.getActionMap().put("reloadWeapon", reloadAction);
        
        setWeaponBindings();
        
        
		
		timer = new Timer(timerInterval, timerListener);
		
		layerPane.repaint();
		layerPane.setVisible(true);



		

		
	}
	@Override
	public boolean run() {
		// TODO Auto-generated method stub
		timer.start();
		return true;
	}
	public static void main(String args[]){
		JFrame frame = new JFrame("Zombie Invasion Test"); //REMOVE FRAME BEFORE COMPLEETION
		ZombieInvasion game = new ZombieInvasion(0);
		frame.add(game);
		frame.setVisible(true);
		game.run();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	}
	public void spawnZombie(){
		int maxVelocity=10;
		int minVelocity=0;
		ParsedImageIcon zombie = new ParsedImageIcon(".\\ZombieInvasion\\zombie.gif");
		JLabel zombieLabel = new JLabel(zombie);
		zombieLabel.setSize(zombie.getIconWidth(), zombie.getIconHeight());
		
		Random randomNumber = new Random();
		int x = randomNumber.nextInt(layerPane.getWidth()-zombie.getIconWidth());
		int y = randomNumber.nextInt(layerPane.getHeight()-zombie.getIconHeight());
		
		zombieLabel.setLocation(x, y);
		
		Velocity velocity = new Velocity();
		
		zombieLabels.add(zombieLabel);
		zombieVelocities.add(velocity);

		
		layerPane.add(zombieLabel, new Integer(2));
		
	}
	public void moveZombies(double timerIntervalInSec){
		final double metersToPixel=20;
		for(int zombieCounter=0; zombieCounter<zombieLabels.size(); zombieCounter++){
			JLabel zombieLabel = zombieLabels.get(zombieCounter);
			Velocity zombieVelocity = zombieVelocities.get(zombieCounter);
			
			int currentX = zombieLabel.getX();
			int currentY = zombieLabel.getY();
			
			int velocityX = zombieVelocity.getVelocityX();
			int velocityY = zombieVelocity.getVelocityY();
			
			int newX = (int)(((double)velocityX)*timerIntervalInSec*metersToPixel) + currentX;
			int newY = (int)(((double)velocityY)* timerIntervalInSec * metersToPixel) + currentY;
			
			if(newX<=0 || newX>getWidth()-zombieLabel.getWidth()){
				velocityX = velocityX*-1;
				zombieVelocity.setVelocityX(velocityX);
				newX = (int)(((double)velocityX)*timerIntervalInSec*metersToPixel) + currentX;
			}
			if(newY<0 || newY>getHeight()-zombieLabel.getHeight()){
				velocityY = velocityY*-1;
				zombieVelocity.setVelocityY(velocityY);
				newY = (int)(((double)velocityY)* timerIntervalInSec * metersToPixel) + currentY;

			}
			zombieLabel.setLocation(newX, newY);
		}
	}
	
	public void shoot(int x, int y){
		for(int zombieCounter=0; zombieCounter<zombieLabels.size(); zombieCounter++){
			JLabel zombieLabel = zombieLabels.get(zombieCounter);
			int zombieX = zombieLabel.getX();
			int zombieY = zombieLabel.getY();
			int zombieWidth = zombieLabel.getWidth();
			int zombieHeight = zombieLabel.getHeight();
			
			if(x>zombieX && x<zombieX+zombieWidth){
				if(y>zombieY && y<zombieY+zombieHeight){
					zombieLabels.remove(zombieLabel);
					layerPane.remove(zombieLabel);
					spawnBlood(zombieLabel);
					zombieVelocities.remove(zombieCounter);
					zombieCounter--;
					mainPanel.repaint();
				}
			}
		}
		
	}
	public void spawnBlood(JLabel zombieLabel){
		int zombieX = zombieLabel.getX();
		int zombieY = zombieLabel.getY();
		int zombieWidth = zombieLabel.getWidth();
		int zombieHeight = zombieLabel.getHeight();
		int bloodWidth;
		int bloodHeight;
		int bloodType = new Random().nextInt(2);
		ParsedImageIcon bloodImage;
		if(bloodType==0){
			bloodImage = new ParsedImageIcon(".\\ZombieInvasion\\blood0.png");
		}else{
			bloodImage = new ParsedImageIcon(".\\ZombieInvasion\\blood1.png");

		}
		JLabel blood = new JLabel(bloodImage);
		bloodWidth = bloodImage.getIconWidth();
		bloodHeight = bloodImage.getIconHeight();
		blood.setSize(bloodWidth, bloodHeight);
		
		int zombieMidX = zombieX + zombieWidth/2; //Gets center
		int zombieMidY = zombieY + zombieHeight/2; //Gets center
		
		int bloodX = zombieMidX - (bloodWidth/2); //Places blood on center
		int bloodY = zombieMidY - (bloodHeight/2);
		
		blood.setLocation(bloodX, bloodY);
		layerPane.add(blood, new Integer(1));
		bloodLabels.add(blood);
		bloodAlpha.add(255.0);
		blood.setForeground(new Color(255, 255, 255, 255));
		
	}
	public void decayBlood(){
		double bloodDecayFactor=.2*timerInterval;
		
		for(int bloodCounter=0; bloodCounter<bloodLabels.size(); bloodCounter++){
			JLabel bloodLabel = bloodLabels.get(bloodCounter);
			double currentAlpha = bloodAlpha.get(bloodCounter);
			
			double newAlpha = currentAlpha - bloodDecayFactor;
			
			if(newAlpha<=0){ //If the bloodlabel will no longer be visible
				bloodLabels.remove(bloodCounter);
				bloodAlpha.remove(bloodCounter);
				layerPane.remove(bloodLabel);
			}else{
				bloodAlpha.set(bloodCounter, newAlpha);
				((ParsedImageIcon)bloodLabel.getIcon()).setAlpha((int)newAlpha);
			}
			
		}
	}
	public void generateWeaponList(){
		weaponList.add(new Weapon(5, Weapon.normalWeapon, 50, 5, new ParsedImageIcon(".\\ZombieInvasion\\sniper.png")));
		weaponList.add(new Weapon(32, Weapon.automaticWeapon, 25, 100, new ParsedImageIcon(".\\ZombieInvasion\\ak47.png")));
		weaponList.add(new Weapon(10, Weapon.normalWeapon, 25, 200, new ParsedImageIcon(".\\ZombieInvasion\\shotgun.png")));

	}
	public void setAmmoLabel(int bulletsLeft, int magazineSize){
		ammoLabel.setText(bulletsLeft  + "/" + magazineSize);
	}
	public void setWeaponLabel(ParsedImageIcon weaponImage){
		weaponLabel.setIcon(weaponImage);
	}
	public void equipWeapon(Weapon weapon, int weaponId){
		//Reset reload and fire (In case weapon was reloading during weapon switch)
		resetReloadAndFire();
		
		if(currentWeaponId!=-1 && currentWeapon!=null){
			weaponList.get(currentWeaponId).setBulletsLeftInClip(currentWeapon.getBulletsLeftInClip());
		}
		//If the new weapon needs to be reloaded
		if(weapon.getBulletsLeftInClip()==0){
			reloadText.setVisible(true);
		}
		Weapon newWeapon =weapon.replicate();
		currentWeapon=newWeapon;
		currentWeaponId=weaponId;
		setAmmoLabel(newWeapon.getBulletsLeftInClip(), newWeapon.getMagazineSize());
		setWeaponLabel(newWeapon.getImage());
		
	}
	public void setWeaponBindings(){
		for(int weaponCounter=0; weaponCounter<weaponList.size(); weaponCounter++){
			final int weaponIndex=weaponCounter;
			AbstractAction equipWeaponAction = new AbstractAction(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					equipWeapon(weaponList.get(weaponIndex), weaponIndex);
					
				}
				
			};
			this.getInputMap().put(KeyStroke.getKeyStroke("" + (weaponCounter+1)), "equipWeapon" + weaponIndex);
	        this.getActionMap().put("equipWeapon" + weaponIndex, equipWeaponAction);
		}

		
	}
	public void resetReloadAndFire(){
		shootingLocation=null;
		automaticFire=-1;
		reloading=-1;
		
		//Because equipweaon is called (which calls this method) is called at the very start of the game, before the reload label is created
		if(reloadText!=null){
			reloadText.setVisible(false);
			reloadText.setText("You need to reload. Press R.");
			reloadText.setSize(reloadText.getPreferredSize());
			reloadText.setLocation((int)(layerPane.getWidth()/2.0- reloadText.getWidth()/2.0), (int)(layerPane.getHeight()/2.0 - reloadText.getHeight()));	
		}

	}
	

}
