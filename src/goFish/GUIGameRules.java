package goFish;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class GUIGameRules extends JFrame {
	private JTextArea taRules;
	private JScrollPane scrollRules;
	private Color blue, white;
	private JLabel rulesLabel;
	private String rulesText;
		
	public GUIGameRules(){
		blue = new Color(0, 162, 255);
		white = new Color(255, 255, 255);
		
		setSize(402, 263);
		setTitle("Game Rules");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setVisible(true);
				
		getContentPane().setLayout(null);
		getContentPane().setBackground(blue);
		
		rulesLabel = new JLabel("Game Rules:");
		rulesLabel.setBounds(10, 11, 212, 75);
		rulesLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
		rulesLabel.setForeground(white);

		rulesText = "Cards are dealt at the dealers discretion. The cards that are left after each player is "
				+ "dealt theirs is put in a pile face down in the centre of the table. This deck is then drawn from during "
				+ "the game and is often referred to as the pool or ocean. Players take turns moving clockwise, with the objective "
				+ "being to get four cards matching the same rank (amount), when all four cards are obtained these are referred to as "
				+ "books. Each player attempts to make a book by asking other players for the cards they need, or obtain the cards "
				+ "they need by drawing from the pool or ocean. Each player MUST have at least one card of the same rank they are asking "
				+ "the other player for. If a player asks another for a rank and the player being asked does not have any cards of the "
				+ "rank, they reply \"Go Fish\" at which point the person asking must draw a card from the pool or ocean and their turn "
				+ "ends. If the player being asked does however have a card or cards of the same rank, they must hand over ALL of the cards "
				+ "of that rank they are holding.";
		


		getContentPane().add(rulesLabel);
		
		taRules = new JTextArea();
		taRules.setWrapStyleWord(true);
		taRules.setLineWrap(true);
		taRules.setEditable(false);
		taRules.setMargin(new Insets(5,10,10,10));
		taRules.setText(rulesText);
		taRules.setCaretPosition(taRules.getDocument().getLength()/5);
		
		scrollRules = new JScrollPane(taRules);
		scrollRules.setBounds(10, 72, 376, 152);
		scrollRules.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollRules);
		        
	}
	

	
}
