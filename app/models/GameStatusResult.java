package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStatusResult {
	List<Player> 		lstChacngedPlayers 	= null;
	Map<Integer, Card> 	mapChangedCards		= new HashMap<Integer, Card>();;
	
	public List<Player> getLstChacngedPlayers() {
		return lstChacngedPlayers;
	}
	
	public void setLstChacngedPlayers(List<Player> lstChacngedPlayers) {
		this.lstChacngedPlayers = lstChacngedPlayers;
	}
	
	public Map<Integer, Card> getCrdChangedCards() {
		return mapChangedCards;
	}
	
	public void setChangedCardsAllCards(List<Card> lstAllCards) {
		
		// Run through the given cards
		for (int nCardIndex = 0; nCardIndex < lstAllCards.size(); nCardIndex++)
		{
			this.mapChangedCards.put(nCardIndex, lstAllCards.get(nCardIndex));
		}
	}
	
	public void setChangedCard(Card crdChangedCard, int nCardIndex) {
		
		this.mapChangedCards.put(nCardIndex, crdChangedCard);
	}
}
