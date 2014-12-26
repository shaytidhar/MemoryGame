package models;

import java.util.ArrayList;
import java.util.List;

public class Game {
	
	private static int				NUMBER_OF_REPEATS 	= 2;
	private static int				NUMBER_OF_CARDS		= 10;
	
	private static List<Player> 	lstPlayers 			= null;
	private static Board 			brdMemoryGameBoard 	= null;
	private static Player			plrCurrentPlayer	= null;
	private static boolean			bisGameOn			= false;	
	
	/**
	 * Private Ctor (because static)
	 */
	private Game()
	{	
		lstPlayers = new ArrayList<Player>();
		lstPlayers.add(new Player("Shay"));
		lstPlayers.add(new Player("Osher"));
	}
	
	public Player getCurrentPlayer()
	{
		return (plrCurrentPlayer);
	}
	
	public boolean isGameOn()
	{
		return (bisGameOn);
	}
	
	/***
	 * Initial game
	 * 
	 * @return
	 */
	public static GameStatusResult initialGame()
	{		
		return (initialGameInternalUse());
	}
	
	/***
	 * Initial game
	 * 
	 * @return
	 */
	public static GameStatusResult initialGameCustomConfigurations(
										int nNumOfCards,
										int nNumOfRepeats)
	{		
		NUMBER_OF_CARDS = nNumOfCards;
		NUMBER_OF_REPEATS = nNumOfRepeats;
		
		return (initialGameInternalUse());
	}
	
	/***
	 * Initial game
	 * 
	 * @return
	 */
	private static GameStatusResult initialGameInternalUse()
	{
		GameStatusResult gsrResult = new GameStatusResult();    
		brdMemoryGameBoard = new Board();  
		
		// Initial board
		brdMemoryGameBoard.initialShuffledBoard(NUMBER_OF_CARDS, NUMBER_OF_REPEATS);
		
		// Set the changed cards result
		gsrResult.setUpsideDownChangedCards(
				brdMemoryGameBoard.getCardsListAsHashMap());
		
		return (gsrResult);
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
			(nPlayerChosenCard > brdMemoryGameBoard.getBoradSize()))
		{
			// Write error msg
		}
		else
		{
			// Get card value	
			crdCardToFlip = brdMemoryGameBoard.flipCardByIndex(nPlayerChosenCard);
				
			
			// If illegal
			if (crdCardToFlip == null)
			{
				// Write error msg
			}
		}
		
		return (crdCardToFlip);
	}

	/***
	 * Get board state
	 * 
	 * @return 
	 */
	public static GameStatusResult getBoardState() {
		
		GameStatusResult gsrResult = new GameStatusResult(); 
		
		BoardStatus bsBoardStatus = brdMemoryGameBoard.checkStatus();
		
		gsrResult.setBoardStatus(bsBoardStatus);
		
		// If not in progress
		if (bsBoardStatus != BoardStatus.IN_PROGRESS){
		
			// If correct or won 
			if ((bsBoardStatus == BoardStatus.CORRECT) ||
				(bsBoardStatus == BoardStatus.WON)){

				// Set turned cards as exposed 
				brdMemoryGameBoard.changeTurnedCardsStatus(CardStatus.EXPOSED);
				
				// Set cards to change
				gsrResult.setChangedCards(brdMemoryGameBoard.getTurnedCards());
			}
			// If wrong
			else if (bsBoardStatus == BoardStatus.WRONG){
			
				// Flip back the cards 
				brdMemoryGameBoard.changeTurnedCardsStatus(CardStatus.UPSIDE_DOWN);
				
				// Set cards to change
				gsrResult.setUpsideDownChangedCards(brdMemoryGameBoard.getTurnedCards());
			}
			
			// Clear turned cards
			brdMemoryGameBoard.clearTurnedCards();
		}
		
		return (gsrResult);
	}

	/***
	 * Get the board
	 * 
	 * @return
	 */
	public static GameStatusResult getRefreshedBoard() {
		
		GameStatusResult gsrResult = new GameStatusResult(); 				
		
		// Set the changed cards result
		gsrResult.setChangedCards(
				brdMemoryGameBoard.getCardsListAsHashMap());
		
		return (gsrResult);
	}
}
