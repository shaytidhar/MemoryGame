package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
	
	List<Card> lstCard = new ArrayList<Card>();
	
	/***
	 * Sets a new shuffles board
	 * Set the cards value and shuffle it 
	 * 
	 * @param nNumOfCards
	 * @param nNumOfRepeats
	 */
	public void initialShuffledBoard(int nNumOfCards, int nNumOfRepeats) {
		
		// If number of card and number of repeats Ok
		if ((nNumOfCards > 2) &&
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
	 * Return all the cards
	 * If status is upside down doesnt get the value
	 * 
	 * @return
	 */
	public List<Card> getAllCardsForDisplay() {
		
		List<Card> lstCardForDisplay = new ArrayList<Card>();

		// Run through cards
		for (Card crdCurrentCard : this.lstCard)
		{
			Card crdCardToDisplay = new Card(crdCurrentCard);
			
			// If upside down
			if (crdCardToDisplay.getStatus() == CardStatus.UPSIDE_DOWN)
			{
				// Set value as null
				crdCardToDisplay.setValue(null);
			}

			lstCardForDisplay.add(crdCardToDisplay);
		}
		
		return (lstCardForDisplay);
	}

	/***
	 * Return the board size
	 * 
	 * @return The board size
	 */
	public int getBoradSize() {
		
		return (this.lstCard.size());
	}

	/***
	 * Return Card from the board by given index
	 * 
	 * @param nPlayerChosenCard
	 * @return Chosen card
	 */
	public Card getCardByIndex(int nPlayerChosenCard) {

		return (lstCard.get(nPlayerChosenCard));
	}
}
