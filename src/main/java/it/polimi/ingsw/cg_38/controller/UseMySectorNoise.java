package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 */
public class UseMySectorNoise extends Action {

    /**
     * 
     */
    public UseMySectorNoise() {
    }

    /**
     * @return
     */
    public Sector perform() {
        // TODO implement here
    	return this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector();
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
        return true;
    }

}