package models;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	
	private static int	NUMBER_OF_REPEATS 	= 2;
	private static int	NUMBER_OF_CARDS		= 3;
	private static int	NUMBER_OF_PLAYERS	= 2;
	
	private static 	Map<Integer, Player>	hmPlayers 			= null;
	private static 	Board 					brdMemoryGameBoard 	= null;
	private static	int						nCurrentPlayer		= 0;
	private static 	GameStatus				gsGameStatus		= null;	
	
	/**
	 * Private Ctor (because static)
	 */
	private Game()
	{	
		
	}
	
	public static Player getCurrentPlayer()
	{
		return (hmPlayers.get(nCurrentPlayer));
	}
	
	/***
	 * Move to the next player
	 */
	private static void nextPlayer()
	{
		nCurrentPlayer++;
		
		// If last player
		if (nCurrentPlayer == NUMBER_OF_PLAYERS)
		{
			nCurrentPlayer = 0;
		}
	}
	
	public static boolean isGameOn()
	{
		return (gsGameStatus != null);
	}
			
	/***
	 * Initial game
	 * 
	 * @return
	 */
	public static void initialGame(List<String> lstPlayers,
									int nNumOfCards,
									int nNumOfRepeats)
	{		
		// Set configurations
		NUMBER_OF_CARDS = nNumOfCards;
		NUMBER_OF_REPEATS = nNumOfRepeats;
		NUMBER_OF_PLAYERS = lstPlayers.size();
		
		// Sets players
		hmPlayers = new HashMap<Integer, Player>();
		nCurrentPlayer = 0;
		
		for (String strCurrPlayerName : lstPlayers) {
			
			// id = size of map
			int id = hmPlayers.size();
			
			// Add Player
			hmPlayers.put(id, new Player(id, strCurrPlayerName));		
		}
		
		Game.brdMemoryGameBoard = new Board();  
		Game.gsGameStatus = GameStatus.START;
		
		// Initial board
		Game.brdMemoryGameBoard.initialShuffledBoard(NUMBER_OF_CARDS, NUMBER_OF_REPEATS);
	}
	
	/***
	 * Flip card
	 * 
	 * @return The card value
	 */
	public static Card flipCard(int nPlayerChosenCard) {
		
		Card crdCardToFlip = null;
		
		// If illegal
		if ((nPlayerChosenCard < 0) ||
			(nPlayerChosenCard > Game.brdMemoryGameBoard.getBoradSize()))
		{
			// Write error msg
		}
		else
		{
			// Get card value	
			crdCardToFlip = Game.brdMemoryGameBoard.flipCardByIndex(nPlayerChosenCard);
				
			
			// If illegal
			if (crdCardToFlip == null)
			{
				// Write error msg
			}
		}
		
		return (crdCardToFlip);
	}

	/***
	 * Get the flip result
	 * 
	 * @return 
	 */
	public static GameStatusResult getFlipResult() {
				
		TurnedCardsStatus tsTurnedCardsStatus = Game.brdMemoryGameBoard.checkTurnedCardsStatus();
		Game.gsGameStatus = GameStatus.IN_GAME;
		Map<Integer, Card> hmTurnedCardsToRemember = new HashMap<Integer, Card>();
				
		// If not in progress (player correct \ wrong)
		if (tsTurnedCardsStatus != TurnedCardsStatus.IN_PROGRESS) {
					
			// If correct
			if (tsTurnedCardsStatus == TurnedCardsStatus.CORRECT) {

				// Set turned cards as exposed 
				Game.brdMemoryGameBoard.changeTurnedCardsStatus(CardStatus.EXPOSED);
								
				// Increase points
				getCurrentPlayer().increaseScore();
								
				// If game over
				if (Game.brdMemoryGameBoard.checkIfGameOver()) {
					
					// Set game status
					Game.gsGameStatus = GameStatus.OVER;
					
					// Set the winner
					setWinnerPlayer();
				}
			}
			// If wrong
			else if (tsTurnedCardsStatus == TurnedCardsStatus.WRONG) {
							
				// Flip back the cards 
				Game.brdMemoryGameBoard.changeTurnedCardsStatus(CardStatus.UPSIDE_DOWN);
												
				// Set next player
				nextPlayer();
			}			

			// Save turned cards before clear
			hmTurnedCardsToRemember = Game.brdMemoryGameBoard.getTurnedCards();
			
			// Clear turned cards
			Game.brdMemoryGameBoard.clearTurnedCards();
		}
				
		return (getGameStatus(hmTurnedCardsToRemember));
	}

	/***
	 * Check which player got the biggest score
	 * 
	 * @return
	 */
	private static void setWinnerPlayer() {
		
		Game.nCurrentPlayer = (Collections.max(Game.hmPlayers.values(),  new Comparator<Player>() {

			@Override
			public int compare(Player arg0, Player arg1) {
				
				return arg0.getScore() - arg1.getScore();
			}
	    })).getId();
	}

	/***
	 * Get the board
	 * 
	 * @return
	 */
	public static GameStatusResult getGameStatus(Map<Integer, Card> hmTurnedCards) {
		
		GameStatusResult gsrResult = new GameStatusResult(); 				
		
		Map<Integer, Card> hmCardsToReutrn = null;
		
		// If partial 
		if (hmTurnedCards != null) {
			
			// Get turned cards
			hmCardsToReutrn = hmTurnedCards;
		}
		// If complete
		else {
			
			// Get all cards
			hmCardsToReutrn = Game.brdMemoryGameBoard.getCardsListAsHashMap();
		}
		
		// Set the changed cards result
		gsrResult.setChangedCardsForDisplay(hmCardsToReutrn);
		
		// Set current user
		gsrResult.setCurrentPlayer(getCurrentPlayer());
		
		// Set players
		gsrResult.setChacngedPlayers(Game.hmPlayers);
		
		// Set game status
		gsrResult.setGameStatus(Game.gsGameStatus);
		
		return (gsrResult);
	}
}
