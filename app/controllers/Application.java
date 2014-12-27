package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import models.Card;
import models.Game;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application extends Controller {

   
    /***
     * Start a memory game match.
     * Initial board, set game's configurations
     * 
     * @return Result
     */
    public static Result startGame() {
    	    	
    	// Get params
    	JsonNode jnParams = 
    			request().body().asJson();
    	
    	// Create player's name list
    	List<String> lstPlayersNames = new ArrayList<String>();
    	
    	for (JsonNode jnCurrName : jnParams.get("players")) {
    		lstPlayersNames.add(jnCurrName.get("name").asText());
    	}
    	
    	Game.initialGame(lstPlayersNames,
		    			jnParams.get("numOfCards").asInt(),
		    			jnParams.get("numOfRepeats").asInt());
    	
    	return ok();
    }
    
    /***
     * Flip card
     *  
     * @return Result
     */
    public static Result flipCard() {
    	
    	String strUserName = 
    			request().body().asJson().get("strUserName").asText();
    	
    	// If its the player turn and game is on
    	if (strUserName.equals(Game.getCurrentPlayer().getName()) && Game.isGameOn())
    	{
	    	int nPlayerChosenCard = 
	    			request().body().asJson().get("nChosenCard").asInt();
			
	    	// Flip card
	    	Card crdToReturn = Game.flipCard(nPlayerChosenCard);
	    	    	
	    	// If not null
	    	if (crdToReturn != null){
	    		
	    		// Convert to Json
	    		return ok(new ObjectMapper().convertValue(crdToReturn,  JsonNode.class));
	    		
	    	}
    	}
    	
    	return internalServerError();
    }
    
    /***
     * Flip card
     *  
     * @return Result
     */
    public static Result getFlipResult() {
    	
    	return ok(new ObjectMapper().convertValue(Game.getFlipResult(), JsonNode.class));  
    }
    
    /***
     * Wait till your turn
     *  
     * @return Result
     */
    public static Result getGameStatus() {
    	 
    	if (Game.isGameOn()) {  	
    		 return ok(new ObjectMapper().convertValue(Game.getGameStatus(null), JsonNode.class));
    	}
    	
    	return ok();
    }
}
