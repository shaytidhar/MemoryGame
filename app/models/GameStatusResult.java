package models;

import java.util.HashMap;
import java.util.Map;

public class GameStatusResult {
	Map<Integer, Player>	changedPlayers 		= new HashMap<Integer, Player>();
	Map<Integer, Card> 		changedCards		= new HashMap<Integer, Card>();
	GameStatus				gameStatus			= null;
	Player					currentPlayer		= null;
	
	public Map<Integer, Player> getChangedPlayers() {
		return changedPlayers;
	}
	
	public void setChacngedPlayers(Map<Integer, Player> hmChacngedPlayers) {
		
		this.changedPlayers = hmChacngedPlayers;	
	}
	
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;		
	}

	public Map<Integer, Card> getChangedCards() {
		return this.changedCards;
	}
	
	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public void setChangedCardsForDisplay(Map<Integer, Card> mpChangedCards) {
		
		// Run through changed cards
		for (Integer key : mpChangedCards.keySet()){
		
			// Duplicate card
			Card crdToDisplay = new Card(mpChangedCards.get(key));
			
			// If upside down card
			if (crdToDisplay.getStatus() == CardStatus.UPSIDE_DOWN)
			{
				// Set value as null
				crdToDisplay.setValue(null);
			}
						
			// Duplicate
			this.changedCards.put(key, crdToDisplay);
		}
	}
}
