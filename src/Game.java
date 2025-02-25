import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

//My First Java Swing Project: Guess The Number
public class Game {

	private JFrame window;
	private Container con;
	private JPanel titlePanel;	
	private JLabel titleName;
	
	private JPanel buttonPanel;
	private JButton button;
	
	private JPanel textFieldPanel;
	private JTextField textField;
	
	private JPanel hintPanel;
	private JLabel hintLabel;
	
	private JPanel tutorialPanel;
	private JLabel tutorialLabel;
	
	private JMenuBar menuBar;
	private JMenu gameMenu;
	
	private JMenuItem exitGame;
	private JMenuItem restartGame;

	private Random randColor = new Random();
	
	private int randomNumber;
	
	
	private Font buttonFont = new Font("Helvetica", Font.BOLD, 35	);
	
	public Game() {
		//Initialize Program
		initialize();
		//Generate Number to Guess
		generateNumber();
		//Play Music
		playSound("C:\\Users\\GameBox\\eigeneProj\\NumberGuessingGame\\src\\music.wav");
	}
	
	private void initialize() {
		window = new JFrame();
		con = window.getContentPane();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle("Number Guessing Game");
		window.setSize(600, 600);
		window.setLayout(null);
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		con = window.getContentPane();
		con.setBackground(Color.black);
		
		//Create Menu
		menuBar = new JMenuBar();
		gameMenu = new JMenu("Game");
		
		restartGame = new JMenuItem("Restart");
		exitGame = new JMenuItem("Exit");
		
		gameMenu.add(restartGame);
		gameMenu.add(exitGame);
		
		menuBar.add(gameMenu);
		window.setJMenuBar(menuBar);
		
		restartGame.addActionListener(e -> {
			restartGame();
		});
        exitGame.addActionListener(e -> System.exit(0));
		
		//Title
		titlePanel = new JPanel();
		titleName = new JLabel(new ImageIcon(Game.class.getResource("guessthenumberlogo.png")));
		
		titlePanel.setBackground(Color.black);
		titlePanel.setBounds(-5, 0, 600, 210);
		
		con.add(titlePanel);
		titlePanel.add(titleName);	
		
		//Creating Button
		buttonPanel = new JPanel();
		button = new JButton("Guess");
		
		buttonPanel.setBackground(Color.black);
		buttonPanel.setBounds(215, 460, 150, 60);
		
		button.setFont(buttonFont);
		button.setFocusable(false);
		button.addActionListener(a -> {
			String input = textField.getText();
			
			//If invalid Input
			if(input.isEmpty() || input == null || !input.chars().allMatch(Character::isDigit) || Integer.parseInt(input) <= 0 || Integer.parseInt(input) > 50) {
				JOptionPane.showMessageDialog(window, "Error: Invalid Input!");
				textField.setText("");
			}
			//If valid input
			else {
				playSound("C:\\Users\\GameBox\\eigeneProj\\NumberGuessingGame\\src\\buttonsound.wav");
				if(Integer.parseInt(input) == randomNumber) {
					hintLabel.setText("Correct!");
					hintLabel.setForeground(new Color(randColor.nextInt(255),randColor.nextInt(255),randColor.nextInt(255)));
					textField.setVisible(false);
				}
				else if(Integer.parseInt(input) < randomNumber) {
					hintLabel.setForeground(new Color(randColor.nextInt(255),randColor.nextInt(255),randColor.nextInt(255)));
					hintLabel.setText("Higher!");
					textField.setText("");
				}
				else {
					hintLabel.setForeground(new Color(randColor.nextInt(255),randColor.nextInt(255),randColor.nextInt(255)));
					hintLabel.setText("Lower!");
					textField.setText("");
				}
				
			}
		});
		
		
		buttonPanel.add(button);
		con.add(buttonPanel);
		
		//Creating TextField
		textFieldPanel = new JPanel(new BorderLayout());
		textField = new JTextField();
		
		textField.setFont(new Font("Helvetica", Font.PLAIN, 25));
		
		
		textFieldPanel.setBackground(Color.black);
		textFieldPanel.setBounds(235, 395, 110, 60);
		
		textFieldPanel.add(textField, BorderLayout.SOUTH);
		con.add(textFieldPanel);
		
		
		//Creating Tutorial Panel
		tutorialPanel = new JPanel();
		tutorialLabel = new JLabel("Guess A Number Between 1-50");
		
		tutorialPanel.setBackground(Color.black);
		tutorialPanel.setBounds(40,200,500,50);
		tutorialLabel.setForeground(Color.white);
		tutorialLabel.setFont(new Font("Helvetica", Font.BOLD, 30));
		
		tutorialPanel.add(tutorialLabel);
		con.add(tutorialPanel);
		
		
		//Creating Hint Panel
		hintPanel = new JPanel();
		hintLabel = new JLabel("Try it!");
		
		hintPanel.setBackground(Color.black);
		hintPanel.setBounds(140,260,300,130);
		hintPanel.setBorder(new LineBorder(Color.black, 5));
		hintLabel.setForeground(Color.WHITE);
		hintLabel.setFont(new Font("Helvetica", Font.BOLD, 75));
		
		hintPanel.add(hintLabel);
		con.add(hintPanel);
	}
	
	public void show() {
		window.setVisible(true);
	}
	
	private int generateNumber() {
		Random rand = new Random();
		randomNumber = rand.nextInt(50) + 1;
		return randomNumber;
	}
	
	private void restartGame() {
		generateNumber();
		hintLabel.setText("Try it!");
		hintLabel.setForeground(Color.white);
		textField.setText("");
		textField.setVisible(true);
	}
	
	private static void playSound(String soundFilePath) {
        try {
            
            File soundFile = new File(soundFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            //Lower the volume (could add menu button to let the user change the volume)
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-5.0f);
            
            
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }


}
	
	

