import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Window {
	public JFrame frame;
	public Snake snake;
	private static Timer timer;
	private JPanel mainPanel;
	private JButton snakeButton, hurdlerButton, zombieButton;
	private JLabel title, desc;
	
	public Window() {
		frame = new JFrame();
		mainPanel = new JPanel();
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setBackground(Color.white);

		GridBagConstraints gbc = new GridBagConstraints();

		title = new JLabel("Minute to Win It Games");
		title.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		title.setFont(new Font("Courier", Font.PLAIN, 30));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 2;
		mainPanel.add(title, gbc);
		
		desc = new JLabel("Survive for 60 seconds in each minigame");
		desc.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		desc.setFont(new Font("Courier", Font.PLAIN, 20));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 2;
		mainPanel.add(desc, gbc);
		
		snakeButton = new JButton("Play Snake");
		snakeButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runSnake();			
				snake.requestDefaultFocus();
				//snake.requestFocus();
				//snake.requestFocusInWindow();
			}
			
		});
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		mainPanel.add(snakeButton,gbc);
		
		hurdlerButton = new JButton("Play Hurdler");
		hurdlerButton.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				runSnake();			
				snake.requestDefaultFocus();
				//snake.requestFocus();
				//snake.requestFocusInWindow();
			}
			
		});
		gbc.gridx = 1;
		gbc.gridy = 2;
		mainPanel.add(hurdlerButton, gbc);
		
		
		frame.setTitle("Minute to Win It");
		frame.setContentPane(mainPanel);
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(1200,720));
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void runSnake() {
		snake = new Snake(0);
		//snake.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		//snake.setLayout(new GridBagLayout());
		frame.setContentPane(snake);
		
		snake.repaint();
		frame.pack();
		snake.run();
		
		
		/*
		timer = new Timer(100, snake);
		timer.setInitialDelay(500);
		timer.start(); 
		*/
	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}
	
}
