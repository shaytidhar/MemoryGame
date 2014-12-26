package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import models.Game;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

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
    	    	
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	try {
			String json = ow.writeValueAsString(Game.initialGame());
			
			return ok(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return internalServerError("התרחשה שגיאה");
		}        
    }
    
    /***
     * Play turn.
     *  
     * @return Result
     */
    public static Result playTurn() {
    	    	
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	try {
    		
    		int nPlayerChosenCard = request().body().asJson().get("nChosenCard").asInt();
    		
			String json = ow.writeValueAsString(Game.playTurn(nPlayerChosenCard));
			
			return ok(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return internalServerError("התרחשה שגיאה");
		}        
    }
}
