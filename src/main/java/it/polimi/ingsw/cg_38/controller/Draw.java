package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 */
public class Draw extends Action {

    /**
     * 
     */
    public Draw() {
    }

    /**
     * @return
     */
    public Card perform() {
        // TODO implement here
    	if(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Dangerous ) {
   			return this.getGameModel().getDeckSector().draw();
   		} else if (this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Hatch ) {
   			return this.getGameModel().getDeckHatch().draw();
   		} else {
    		return null;
    	}
    }

    /**
     * @return
     */
    public Boolean isPossible() {
       
    	// TODO implement here
    	if(!this.getGameModel().getActualTurn().getHasAttacked() || !(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector() instanceof Safe) || !this.getGameModel().getActualTurn().getHasDraw()) {
    		return true;
    	}
    	return false;
    }
    

}