package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Sector;

public class UseSilenceCard extends GameAction {

    /**
     * 
     */
    public UseSilenceCard(GameModel gameModel) {
    	super(gameModel);
    }

    /**
     * @return
     */
    public Boolean perform() {
        // TODO implement here
    	return true;
    }

	/**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
        return true;
    }

}