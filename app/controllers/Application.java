package controllers;

import models.Card;
import models.Game;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    /***
     * Start a memory game match.
     * Initial board, set game's configurations
     * 
     * @return Result
     */
    public static Result startGame() {
    	    	
    	return ok(new ObjectMapper().convertValue(Game.initialGame(), JsonNode.class));
    }
    
    /***
     * Flip card
     *  
     * @return Result
     */
    public static Result flipCard() {
    	
    	int nPlayerChosenCard = 
    			request().body().asJson().get("nChosenCard").asInt();
		
    	// Flip card
    	Card crdToReturn = Game.flipCard(nPlayerChosenCard);
    	    	
    	// If not null
    	if (crdToReturn != null){
    		
    		// Convert to Json
    		return ok(new ObjectMapper().convertValue(crdToReturn,  JsonNode.class));
    	}
    	else
    	{
    		return internalServerError();
    	}       
    }
    
    /***
     * Flip card
     *  
     * @return Result
     */
    public static Result getBoardState() {
    			
    	return ok(new ObjectMapper().convertValue(Game.getBoardState(), JsonNode.class));  
    }
    
    /***
     * Wait till your turn
     *  
     * @return Result
     */
    public static Result getRefreshedBoard() {
    			    	
    	return ok(new ObjectMapper().convertValue(Game.getRefreshedBoard(), JsonNode.class));  
    }
}
