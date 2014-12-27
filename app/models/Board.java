package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Board {
	

	int							NUMBER_OF_REPEATS;
	
	private List<Card> 			lstCard 		= new ArrayList<Card>();
	private Map<Integer,Card>	hmTurnedCards	= null;
	
	/***
	 * Return the board size
	 * 
	 * @return The board size
	 */
	public int getBoradSize() {
		
		return (this.lstCard.size());
	}

	/***
	 * Return the turned cards
	 * 
	 * @return
	 */
	public Map<Integer, Card> getTurnedCards() {
		
		return (this.hmTurnedCards);
	}

	/***
	 * Return all the cards as hash map
	 * 
	 * @return
	 */
	public HashMap<Integer, Card> getCardsListAsHashMap() {
		
		HashMap<Integer, Card> hmToReturn = new HashMap<Integer, Card>();
		
		// Run through the cards
		for (int nCardIndex = 0; nCardIndex < getBoradSize(); nCardIndex++)
		{
			hmToReturn.put(nCardIndex, lstCard.get(nCardIndex));
		}
		
		return (hmToReturn);
	}

	/***
	 * Clear the turned cards hash map 
	 */
	public void clearTurnedCards() {
		hmTurnedCards = null;
	}

	/***
	 * Change turned cards by given status
	 * 
	 * @param exposed
	 */
	public void changeTurnedCardsStatus(CardStatus csStatusToChange) {
		
		// Run through turned cards
		for (Card crdCurrentCard : this.hmTurnedCards.values())
		{
			crdCurrentCard.setStatus(csStatusToChange);
		}
	}
	
	/***
	 * Sets a new shuffles board
	 * Set the cards value and shuffle it 
	 * 
	 * @param nNumOfCards
	 * @param nNumOfRepeats
	 */
	public void initialShuffledBoard(int nNumOfCards, int nNumOfRepeats) {
		
		this.NUMBER_OF_REPEATS = nNumOfRepeats;
		
		// If number of card and number of repeats Ok
		if ((nNumOfCards > 1) &&
			(nNumOfRepeats > 1))
		{		
			
			// Initial the board
			this.initialBoardInOrder(nNumOfCards, nNumOfRepeats);
			
			// Shuffle the board
			Collections.shuffle(lstCard);
		}
	}

	/***
	 * Initial the board
	 * Sets the card list w/ it's values and number of repeats
	 * 
	 * @param nNumOfCards
	 * @param nNumOfRepeats
	 */
	private void initialBoardInOrder(int nNumOfCards, int nNumOfRepeats) {

		this.lstCard = new ArrayList<Card>();
		
		// Run through cards optional values
		for (int nCurrCardValue = 1; nCurrCardValue <= nNumOfCards; nCurrCardValue++)
		{
			// Run through number of repeats
			for (int nRepeatIndex = 0; nRepeatIndex < nNumOfRepeats; nRepeatIndex++)
			{
				this.lstCard.add(new Card(nCurrCardValue));
			}
		}		
	}

	/***
	 * Flip Card
	 * Set the card as turned
	 * 
	 * @param nPlayerChosenCard
	 * @return Chosen card
	 */
	public Card flipCardByIndex(int nPlayerChosenCard) {
		
		
		Card crdToReturn = null;
		
		// If first choise
		if (hmTurnedCards == null) {
			
			hmTurnedCards = new HashMap<Integer, Card>();
		}
		
		// Check if turned card full
		if (this.hmTurnedCards.size() < this.NUMBER_OF_REPEATS)
		{
			
			crdToReturn = this.lstCard.get(nPlayerChosenCard);
			
			// If already exposed or turned
			if ((crdToReturn.getStatus() == CardStatus.EXPOSED) || 
				(crdToReturn.getStatus() == CardStatus.TURNED))
			{
				// Set as null
				crdToReturn = null;			
			}
			// OK
			else
			{			
				// Turn card
				crdToReturn.setStatus(CardStatus.TURNED);
				
				// Keep the card as turned
				hmTurnedCards.put(nPlayerChosenCard, crdToReturn);			
			}
		}
		
		return (crdToReturn);
	}

	/**
	 * Check the turned cards status
	 * 
	 * @return
	 */
	public TurnedCardsStatus checkTurnedCardsStatus() {

		TurnedCardsStatus bsToReturn = TurnedCardsStatus.IN_PROGRESS;
		
		// If not first choose
		if (hmTurnedCards != null)
		{
			// If not equals 
			if (!isChosenCardsAreEquals())
			{
				bsToReturn = TurnedCardsStatus.WRONG;
			}
			// If equals
			else
			{
				// If last choose
				if (hmTurnedCards.size() == this.NUMBER_OF_REPEATS)
				{						
					bsToReturn = TurnedCardsStatus.CORRECT;					
				}
			}
		}
		
		return (bsToReturn);
	}
	
	/***
	 * Check if game over
	 * 
	 * Run over the board and check if all cards exposed
	 * 
	 * @return
	 */
	public boolean checkIfGameOver() {
		
		boolean bIsGameOver = true;
		
		// Run Through board
		for (Card crdCurrCard : this.lstCard) {

			// If upside down
			if (crdCurrCard.getStatus() == CardStatus.UPSIDE_DOWN){
				
				// Game not finished
				bIsGameOver = false;
				
				break;
			}
		}
		
		return (bIsGameOver);
	}

	/***
	 * Check if the chosen cards are equals
	 * 
	 * @return
	 */
	private boolean isChosenCardsAreEquals() {
		
		boolean bIsChosenCardsEquals = true;
		
		Iterator<Entry<Integer, Card>> it = hmTurnedCards.entrySet().iterator();
	    
		// Get first card
		Card crdFirstCard = it.next().getValue();
		
		// Run through turned cards while equals
		while (bIsChosenCardsEquals && it.hasNext()) {
			
			// If next equals
	        if (!crdFirstCard.equals((Card)it.next().getValue()))
	        {
	        	bIsChosenCardsEquals = false;
	        }
	    }
		
		return (bIsChosenCardsEquals);
	}
}