package goFish;

import java.util.Random;

public class AIeasy extends Person{
	private Random randomGenerator;
	
	public AIeasy(){
		randomGenerator = new Random();
	}
	
	public int getPlayerIndexToAsk(int players){
		return randomGenerator.nextInt(players);
	}
	
	public int getCardIndexToAsk(){
		return randomGenerator.nextInt(getHandSize());
	}
}
