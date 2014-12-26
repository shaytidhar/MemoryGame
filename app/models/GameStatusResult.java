package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStatusResult {
	List<Player> 		chacngedPlayers 	= null;
	Map<Integer, Card> 	changedCards		= new HashMap<Integer, Card>();
	BoardStatus			boardStatus			= null;
	
	public List<Player> getChacngedPlayers() {
		return chacngedPlayers;
	}
	
	public void setChacngedPlayers(List<Player> lstChacngedPlayers) {
		this.chacngedPlayers = lstChacngedPlayers;
	}
	
	public BoardStatus getBoardStatus() {
		return boardStatus;
	}

	public void setBoardStatus(BoardStatus tsTurnStatus) {
		this.boardStatus = tsTurnStatus;
	}

	public Map<Integer, Card> getChangedCards() {
		return this.changedCards;
	}
	
	public void setUpsideDownChangedCards(Map<Integer, Card> mpChangedCards) {
		
		// Run through changed cards
		for (Integer key : mpChangedCards.keySet()){
		
			// Duplicate card
			Card crdToDisplay = new Card(mpChangedCards.get(key));
			
			crdToDisplay.setValue(null);
			
			// Duplicate
			this.changedCards.put(key, crdToDisplay);
		}
	}
	
	public void setChangedCards(Map<Integer, Card> mpChangedCards) {
		
		// Run through changed cards
		for (Integer key : mpChangedCards.keySet()){
		
			// Duplicate card
			Card crdToDisplay = new Card(mpChangedCards.get(key));
			
			// Duplicate
			this.changedCards.put(key, crdToDisplay);
		}
	}
}
