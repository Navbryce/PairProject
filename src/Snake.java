import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.*;


public class Snake extends Game {
	private JFrame frame;
	private JPanel mainPanel;
	private JGridPanel grid;
	private JLabel score;
	private ArrayList<Coordinate> snake;
	private int cellSide = 50;
	
	public Snake() {
		frame = new JFrame();
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel score = new JLabel("score");
		gbc.gridx = 0;
		gbc.gridy = 0;
		mainPanel.add(score, gbc);
		
		grid = new JGridPanel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		grid.setPreferredSize(new Dimension(500, 500));
		mainPanel.add(grid, gbc);
		
		generateFood();
		
		frame.setTitle("Snake");
		frame.setContentPane(mainPanel);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public boolean run() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void generateFood() {
		//check where snake is
		int xVal = 0, yVal = 0;
		
		boolean clean = false;
		while(!clean) {
			xVal = (int)(Math.random()*10);
			yVal = (int)(Math.random()*10);
			
			//if(snake.doesnotinclude x,y) {
				clean = true;
			//}
		}
		System.out.println(new Coordinate(xVal, yVal));
		grid.makeFood(new Coordinate(xVal, yVal));
	}

	private class JGridPanel extends JPanel {
		private Coordinate food;
		public JGridPanel(){}
		 
		//FIX: SET GRID TO 10X10 
		public void paint(Graphics g) {			
			Graphics2D g2 = (Graphics2D) g;
			super.paintComponent(g2);
					
			int cellHeight = getHeight()/cellSide;
			int cellWidth = getWidth()/cellSide;
					
			for(int i = 0; i <= getHeight(); i+=cellHeight) { //horizontal
				g2.setColor(Color.black);
				g2.draw(new Line2D.Double(0, i, getWidth(), i));
			}
					
			for(int j = 0; j <= getWidth(); j += cellWidth) { //vertical
				g2.setColor(Color.black);
				g2.draw(new Line2D.Double(j, 0, j, getHeight()));

			}
			
			if(food != null) {
				g2.setColor(Color.blue);
				g2.draw(new Rectangle(food.getX()*cellSide, food.getY()*cellSide, cellSide, cellSide));
			}
		}
		
		public void makeFood(Coordinate coord) {
			food = coord;
		}
			
	}
	
	

}
