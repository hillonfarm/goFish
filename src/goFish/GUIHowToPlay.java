package goFish;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GUIHowToPlay extends JFrame{
	private JTextArea taRules;
	private JScrollPane scrollRules;
	private Color blue, black;
	private JLabel rulesLabel;
	private String rulesText;
			
	public GUIHowToPlay() {
		blue = new Color(0, 162, 255);
		black = new Color(0, 0, 0);
		
		setSize(402, 263);
		setTitle("Learn To Play");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);

		getContentPane().setLayout(null);
		getContentPane().setBackground(black);

		rulesLabel = new JLabel("How To Play:");
		rulesLabel.setBounds(10, 11, 212, 75);
		rulesLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
		rulesLabel.setForeground(blue);

		rulesText = "     In the beginning of the game it's your turn, which is indicated by the yellow " +
				"border around your cards. At any point in the game you will be able to tell whose turn it" +
				" is by the yellow border. First remove any pairs from your hand by clicking on the cards. " +
				"Then click the button to advance until it is your turn again. You may then continue with normal game " +
				"play and ask a player for a card. All you have to do is click on a card and click another player's hand. " +
				"You can continue to do so until you are told 'Go Fish!' Once told so you must click the deck and then advance the turns." +
				" If you ever run out of cards just click the deck for a refill on your turn. The game is over " +
				"when all the pairs have been removed and the player with the most pairs at the end of the game wins! Good Luck!";
		
		getContentPane().add(rulesLabel);

		taRules = new JTextArea();
		taRules.setWrapStyleWord(true);
		taRules.setLineWrap(true);
		taRules.setEditable(false);
		taRules.setMargin(new Insets(5, 10, 10, 10));
		taRules.setText(rulesText);
		taRules.setCaretPosition(taRules.getDocument().getLength() / 5);

		scrollRules = new JScrollPane(taRules);
		scrollRules.setBounds(10, 72, 376, 152);
		scrollRules
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollRules);

	}

}