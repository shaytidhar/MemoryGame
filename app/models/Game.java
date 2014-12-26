package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	static List<Player> 	lstPlayers 			= new ArrayList<Player>();
	static Board 			brdMemoryGameBoard 	= new Board();
	
	public static GameStatusResult initialGame()
	{
		GameStatusResult gsrResult = new GameStatusResult(); 
		
		// Initial board
		brdMemoryGameBoard.initialShuffledBoard(10, 2);
		
		// Set the changed cards result
		gsrResult.setChangedCardsAllCards(brdMemoryGameBoard.getAllCardsForDisplay());
		
		// Initial players
		initialPlayers();
		
		// Set the players result
		gsrResult.setLstChacngedPlayers(lstPlayers);
		
		return (gsrResult);
	}

	/**
	 * Initial the players
	 * Sets theirs names and scores
	 */
	private static void initialPlayers() {
		lstPlayers.add(new Player("1"));		
		lstPlayers.add(new Player("2"));
	}

	/***
	 * Play a game turn
	 * @return
	 */
	public static GameStatusResult playTurn(int nPlayerChosenCard) {
		
		GameStatusResult gsrResult = new GameStatusResult(); 
		
		// If illegal
		if ((nPlayerChosenCard <= 0) ||
			(nPlayerChosenCard > brdMemoryGameBoard.getBoradSize()))
		{
			// Write error msg
		}
		else
		{
			// Get card value
			Card crdChosenCard = brdMemoryGameBoard.getCardByIndex(nPlayerChosenCard);
				
			// If already exposed or turned
			if ((crdChosenCard.getStatus() == CardStatus.EXPOSED) || 
				(crdChosenCard.getStatus() == CardStatus.TURNED))
			{
				// Write error msg
			}
			// If legal
			else
			{
		
				// If the first chose
					
					// Turn card
					crdChosenCard.setStatus(CardStatus.TURNED);
				
					// Set card on the result
					gsrResult.setChangedCard(crdChosenCard, nPlayerChosenCard);
				
				// If not the first chose
				
					// If equals 
				
						// Append point
				
						// Set on result
				
						// Set the cards
				
						// Sets the cards on result
				
					// Not equals
			
					// Append turn
			
					//
			}
		}
		
		return (gsrResult);
	}
}
