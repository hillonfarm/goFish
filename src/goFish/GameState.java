package goFish;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState implements GameConstants{
	private GUIGameBoard mainBoard;
	private Person playerList[];
	private HashMap<String, Integer> pairsPlayed;
	private HashMap<String, ArrayList<Integer>> knownCards;
	private Deck deck;
	private boolean goFish, removedPair, goAgain;
	private int turn, difficultyLevel;
	
	public GameState(GUIGameBoard dis, int difficulty){
		mainBoard = dis;
		difficultyLevel = difficulty;
		deck = new Deck();
		turn = PLAYER0TURN;
		goFish = false;
		removedPair = false;
		goAgain = true;
		
		//Map tracks pairs of ranks played.
		pairsPlayed = new HashMap<String, Integer>();
		for(int i = 0; i < allRanks.length; i++)
			pairsPlayed.put(allRanks[i].substring(0, 1), 0);
		
		//tracks the cards a player has in their hand that must be asked for.
		knownCards = new HashMap<String, ArrayList<Integer>>();
		for(int i = 0; i < allRanks.length; i++)
			knownCards.put(allRanks[i].substring(0, 1), new ArrayList<Integer>());
		
		playerList = new Person[4];
		playerList[0] = new Person();
		playerList[0].setPanelNumber(PLAYER0TURN);
		
		//Determine difficulty
		if(difficultyLevel == 1){
			for(int i = 1; i < playerList.length; i++){
				playerList[i] = new AIeasy();
				playerList[i].setPanelNumber(i);
			}
		} else {
			for(int i = 1; i < playerList.length; i++){
				playerList[i] = new AIhard();
				playerList[i].setPanelNumber(i);
			}
		}
	}
	
	public void setTurn(int turnNumber)     {turn = turnNumber;}
	public Deck getDeck()	{return deck;}
	public boolean isPairRemoved()	{return removedPair;}
	public Person[] getPlayers()	{return playerList;}
	public boolean isGoFish()	{return goFish;}
	public int getTurnNumber()	{return turn;}

	//Assign Cards
	public void dealCards() {
		for(int players = 1; players < playerList.length; players++){
			for(int i = 0; i < STARTING_CARDS; i++)	{
				Card temp = deck.drawFromDeck();
				playerList[players].draw(temp);
			}
		}
		
		for(int i = 0; i < STARTING_CARDS; i++){
			//Flip cards
			Card temp = deck.drawFromDeck();
			temp.flipCard();
			playerList[0].draw(temp);
		}
		
		mainBoard.drawGame(playerList);
	}

	public void checkUserPair(String firstCard, String secondCard){
		if (firstCard.substring(0, 1).equals(secondCard.substring(0, 1))){
			playerList[0].removePair(firstCard, secondCard);
			
			String pair = firstCard.substring(0, firstCard.indexOf(" "));
			mainBoard.addTextToLog("You have removed a pair of " + pair + "'s!");

			incrementMapPair(firstCard);
			updateKnownPairs(firstCard, turn);
			
			//Skip Turn
			if(isHandEmpty(0) && deck.isDeckEmpty())	{mainBoard.setButtonClickable(true);}
			
			mainBoard.drawGame(playerList);
		}
	}

	//sets button
	public void processAIFirstTurn() {
		//increment turn
		incrementTurn();

		//Remove matches
		ArrayList<String> pairsFound = playerList[turn].autoRemovePairs();
		for (int i = 0; i < pairsFound.size(); i++) {
			mainBoard.addTextToLog("Player " + turn + " has removed a pair of " + pairsFound.get(i) + "'s!");
			incrementMapPair(pairsFound.get(i));
		}
		
		mainBoard.drawGame(playerList);

		//button text
		if (turn == PLAYER3TURN)
			mainBoard.setButtonText("Your Turn!");
		else
			mainBoard.setButtonText("Player " + (turn + 1) + "'s First Turn");
	}
	
	//Increase turn count
	public void incrementTurn(){
		if(turn+1 > PLAYER3TURN)
			setTurn(PLAYER0TURN);
		else
			turn++;
	}

	//Remove card
	public void checkOpponentHandForMatch(String cardSelectedName, int playerAsked) {		
		//if yes
		if(playerList[playerAsked].contains(cardSelectedName)){
			//remove card
			playerList[turn].removeCard(cardSelectedName);
			int numPairs = pairsPlayed.get(cardSelectedName.substring(0, 1));
			numPairs++;
			
			pairsPlayed.put(cardSelectedName.substring(0, 1), numPairs);
			updateKnownPairs(cardSelectedName, playerAsked);
			
			playerList[turn].incrementMatches();
			removedPair = true;
		
			if(turn == PLAYER0TURN)
				mainBoard.addTextToLog(cardSelectedName + " has been removed from your hand");
			else
				mainBoard.addTextToLog(cardSelectedName + " has been removed from Player " + turn + "'s hand");
		}else{

			goAgain = false;

			if(!deck.isDeckEmpty()){
				goFish = true;
				mainBoard.displayGoFish(goFish);
			}
			else{
				mainBoard.addTextToLog("The deck is empty and your turn is now over, please advance to Player 1's turn.");
				mainBoard.setButtonClickable(true);
			}
	

			ArrayList<Integer> players = knownCards.get(cardSelectedName.substring(0, 1));
			if(!players.contains(turn))	    {players.add(turn);}
			knownCards.put(cardSelectedName.substring(0, 1), players);
			

			if(turn != PLAYER0TURN){
				drawFromDeck(turn);
				
				ArrayList<String> pairsFound = playerList[turn].autoRemovePairs();
				for(int i = 0; i < pairsFound.size(); i++){
					mainBoard.addTextToLog("Player " + turn + " has removed a pair of " + pairsFound.get(i) + "'s!");
					incrementMapPair(pairsFound.get(i));
					updateKnownPairs(pairsFound.get(i), turn);
				}
			}
		}
		

		if(isHandEmpty(turn))	{mainBoard.setButtonClickable(true);}
		
		mainBoard.drawGame(playerList);
	}
	

	public void drawFromDeck(int player){

		if(deck.isDeckEmpty()){
			mainBoard.displayDeckEmpty(true);
			mainBoard.drawGame(playerList);
			return;
		}


		Card temp = deck.drawFromDeck();
		if(player == PLAYER0TURN)	{temp.flipCard();}
		playerList[player].draw(temp);
		

		if(turn == PLAYER0TURN)
			mainBoard.addTextToLog("You drew a(n) " + temp.toString());
		else
			mainBoard.addTextToLog("Player " + turn + " drew a card from the deck.");
		

		goFish = false;
		if(player == PLAYER0TURN)	{mainBoard.displayGoFish(goFish);}


		if(deck.isDeckEmpty())	{mainBoard.displayDeckEmpty(true);}
		

		mainBoard.drawGame(playerList);
	}
	

	public void updateButtonText(){
		if(turn == PLAYER3TURN){
			mainBoard.setButtonText("Your Turn!");
			removedPair = false;
		} else {
			mainBoard.setButtonText("Player " + (turn+1) + "'s Turn");
		}
	}


	public boolean isAllOtherHandsEmpty(int player){
		boolean allHandsEmpty = true;
		for(int i = 0; i < playerList.length; i++){
			if(i != player && !isHandEmpty(i))	{allHandsEmpty = false;}
		}
		
		return allHandsEmpty;
	}
	

	public void processAITurn() {

		incrementTurn();
		

		if(isAllOtherHandsEmpty(turn)){
			drawFromDeck(turn);
			updateButtonText();
			return;
		}
		

		if(isHandEmpty(turn)){
			for(int i = 0; i < REFILL_CARDS; i++)
				drawFromDeck(turn);
			
			ArrayList<String> pairsFound = playerList[turn].autoRemovePairs();
			for(int i = 0; i < pairsFound.size(); i++){
				
				mainBoard.addTextToLog("Player " + turn + " has removed a pair of " + pairsFound.get(i) + "'s!");
				incrementMapPair(pairsFound.get(i));
				updateKnownPairs(pairsFound.get(i), turn);
			}
			
			updateButtonText();
			return;
		}
		

		goAgain = true;
		while(goAgain){
			if(isHandEmpty(turn)){
				goAgain = false;
				break;
			}

			int cardNumber = 0, playerNumber = 0;

			if(playerList[turn] instanceof AIeasy){
				AIeasy tempRobot = (AIeasy) playerList[turn];
				cardNumber = tempRobot.getCardIndexToAsk();
				playerNumber = tempRobot.getPlayerIndexToAsk(playerList.length);
				
				while(playerNumber == turn || isHandEmpty(playerNumber)){
		      		playerNumber = tempRobot.getPlayerIndexToAsk(playerList.length);
		      	}
			} else if(playerList[turn] instanceof AIhard) {
				AIhard tempRobot = (AIhard) playerList[turn];
				tempRobot.setScores(playerList, turn, pairsPlayed, knownCards);
				cardNumber = tempRobot.getCardIndex();
				playerNumber = tempRobot.getPlayerToAsk();
			} else {
				System.out.println("Static casting went wrong");
			}
			
	      	Card card = playerList[turn].getCard(cardNumber);
	      	String playerCard = card.toString();
	      	
	      	if(playerNumber == PLAYER0TURN)
	      		mainBoard.addTextToLog(" *** Player " + turn + " is looking for a(n) " + playerCard + " and is asking you");
	      	else
	      		mainBoard.addTextToLog(" *** Player " + turn + " is looking for a(n) " + playerCard + " and is asking Player " + playerNumber);
	      	
	      	checkOpponentHandForMatch(playerCard, playerNumber);
		}
		updateButtonText();
		
		if(turn == PLAYER3TURN){
			if(isHandEmpty(0) && deck.isDeckEmpty()){
				mainBoard.setButtonClickable(true);
			}
		}
	}

	public boolean isHandEmpty(int player) {
		if(playerList[player].getHandSize() == 0)
			return true;
		return false;
	}

	public boolean isGameOver() {
		for(int i = 0; i < playerList.length; i++){
			if(!isHandEmpty(i))
				return false;
		}

		if(deck.isDeckEmpty())
			return true;

		return true;
	}

	
	public int getWinner() {
		int player = 0;
		for(int i = 1; i < playerList.length; i++){
			if(playerList[i].getMatches() > playerList[player].getMatches())
				player = i;
		}
		
		for(int j = 0; j < playerList.length; j++){
			if(j!= player && playerList[j].getMatches() == playerList[player].getMatches())
				return -1;
		}
		
		return player;
	}
	
	public void updateKnownPairs(String cardName, int playerAsked){
		
		ArrayList<Integer> players = knownCards.get(cardName.substring(0, 1));
		if(players.contains(turn))
			players.remove((Object) turn);
		if(playerAsked != turn && players.contains(playerAsked)){
			players.remove((Object) playerAsked);
		}
		
		knownCards.put(cardName, players);
	}
	
	public void incrementMapPair(String cardName){
		int numPairs = pairsPlayed.get(cardName.substring(0, 1));
		numPairs++;
		pairsPlayed.put(cardName.substring(0, 1), numPairs);
	}
	
	public void printPairMap(){
		System.out.println("New Map \n ------------------ \n");
		for(int p = 0; p < allRanks.length; p++)
			System.out.println(""+ allRanks[p] + " : " + pairsPlayed.get(allRanks[p].substring(0,1)));
	}
	
	public void printKnownCardsMap(){
		System.out.println("Known Cards \n ------------------ \n");
		for(int p = 0; p < allRanks.length; p++){
			System.out.print("\n"+ allRanks[p] + " :");
			ArrayList<Integer> players = knownCards.get(allRanks[p].substring(0,1));
			for(int i = 0; i < players.size(); i++){
				System.out.print(" " + players.get(i));
			}
		}
		System.out.println();
	}
	
}
