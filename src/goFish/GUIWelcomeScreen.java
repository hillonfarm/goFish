package goFish;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;


@SuppressWarnings("serial")
public class GUIWelcomeScreen extends JFrame implements ActionListener{
	private JButton rulesButton, startButton, btnHowToPlay;
	private Color blue, black; 
	private Border loweredbevel;
	private JLabel greetings, gameTitle /*fishLabel, fish2Label*/;
	private JTextArea creatorLabel;
	private JMenuBar mbBar;
	private JMenu mnMenu;
	private JMenuItem mnitmEasy, mnitmHard;

	
	public GUIWelcomeScreen(){
		blue = new Color(0, 162, 255);
		black = new Color(0, 0, 0);
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		
		setSize(600, 500);
		setVisible(true);
		setBackground(blue);
		setTitle("Welcome to Go Fish!");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		greetings = new JLabel("Welcome To");
		greetings.setHorizontalAlignment(SwingConstants.CENTER);
		greetings.setBounds(0, 123, 597, 43);
		greetings.setFont(new Font("Verdana", Font.PLAIN, 44));
		
		gameTitle = new JLabel("Go Fish!");
		gameTitle.setHorizontalAlignment(SwingConstants.CENTER);
		gameTitle.setBounds(0, 177, 597, 111);
		gameTitle.setFont(new Font("Jokerman", Font.BOLD, 99));
		
		creatorLabel = new JTextArea();
		creatorLabel.setEditable(false);
		creatorLabel.setWrapStyleWord(true);
		creatorLabel.setBounds(0, 367, 380, 34);
		creatorLabel.setDisabledTextColor(black);
		creatorLabel.setBorder(loweredbevel);
		creatorLabel.setAlignmentX(CENTER_ALIGNMENT);
		creatorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		creatorLabel.setText("Created by: Hilton Bailey, Lonwabo Sandi, Siphesande Mnqonywa");
		creatorLabel.setLineWrap(true);
		creatorLabel.setEnabled(false);
		creatorLabel.setBackground(blue);
		
		rulesButton = new JButton("Game Rules");
		rulesButton.setBounds(29, 312, 160, 27);
		rulesButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		rulesButton.setActionCommand("rules");
		rulesButton.setMnemonic(KeyEvent.VK_G);
		rulesButton.setToolTipText("The game rules can be found here.");
		rulesButton.addActionListener(this);
		
		startButton = new JButton("Start");
		startButton.setBounds(406, 312, 160, 27);
		startButton.setFont(new Font("Verdana", Font.PLAIN, 15));
		startButton.setActionCommand("start");		
		startButton.setMnemonic(KeyEvent.VK_S);	
		startButton.setToolTipText("Click to begin the game!");
		startButton.addActionListener(this);
		
		btnHowToPlay = new JButton("How To Play");
		btnHowToPlay.setBounds(215, 312, 160, 27);
		btnHowToPlay.setFont(new Font("Verdana", Font.PLAIN, 15));
		btnHowToPlay.setActionCommand("howTo");		
		btnHowToPlay.setMnemonic(KeyEvent.VK_H);	
		btnHowToPlay.setToolTipText("Find out how to play.");
		btnHowToPlay.addActionListener(this);
		
		mbBar = new JMenuBar();
		
		mnMenu = new JMenu("Choose Difficulty");
		mnMenu.setForeground(Color.BLACK);
		mnMenu.setBounds(10, 44, 170, 40);
		mbBar.add(mnMenu);
		
		mnitmEasy = new JRadioButtonMenuItem("Easy", true);
		mnMenu.add(mnitmEasy);
		mnitmEasy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				mnitmEasy.setSelected(true);
				mnitmHard.setSelected(false);
			}}
		);
		
		mnitmHard = new JRadioButtonMenuItem("Hard");
		mnMenu.add(mnitmHard);
		mnitmHard.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event){
				mnitmHard.setSelected(true);
				mnitmEasy.setSelected(false);
			}}
		);
		setJMenuBar(mbBar);
		
		getContentPane().setBackground(blue);
		getContentPane().setLayout(null);
		getContentPane().add(greetings);
		getContentPane().add(gameTitle);
		getContentPane().add(rulesButton);
		getContentPane().add(startButton);
		getContentPane().add(btnHowToPlay);
		getContentPane().add(creatorLabel);	
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
	    if((event.getActionCommand()).equals("rules")) {
	    	new GUIGameRules();
	    }
	    else if((event.getActionCommand()).equals("start")) {
	    	dispose();
	    	
	    	GUIGameBoard d = new GUIGameBoard();
	    	
	    	int difficulty = 0;
	    	
	    	if(mnitmEasy.isSelected()) {difficulty = 1;}
	    	else if(mnitmHard.isSelected()) {difficulty = 2;}
	    	
	    	GameState gs = new GameState(d, difficulty);
	    	d.setGameState(gs);
	    	gs.dealCards();
	    	
	    	d.setVisible(true);
	    }
	    else if((event.getActionCommand()).equals("howTo"))  {new GUIHowToPlay();}
	}
}
