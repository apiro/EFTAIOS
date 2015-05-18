package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 */
public class ActionCreator {

    /**
     * 
     */
    public ActionCreator() {
    }

    /**
     * @param String type
     */
    public static GameAction createAction(String type, Sector sector, Card card, GameModel gameModel) {
    	// TODO implement here
    	GameAction action = null;
    	if(type == "Attack") {
    		action = new Attack(sector, gameModel);
    	} else if (type == "Draw") {
    		action = new Draw(gameModel);
    	} else if (type == "Move") {
    		action = new Move(sector, gameModel);
    	}  else if (type == "UseAdrenalineCard") {
    		action = new UseAdrenalineCard(card, gameModel);
    	} else if (type == "UseAttackCard") {
    		action = new UseAttackCard(card, sector, gameModel);
    	} else if (type == "UseLightsCard") {
    		action = new UseLightsCard(card, sector, gameModel);
    	} else if (type == "UseSedativesCard") {
    		action = new UseSedativesCard(card, gameModel);
    	} else if (type == "UseRandomSectorNoise") {
    		action = new UseRandomSectorNoise(sector, gameModel);
    	} else if (type == "UseMySectorNoise") {
    		action = new UseMySectorNoise(gameModel);
    	} else if (type == "UseSilenceCard") {
    		action = new UseSilenceCard(gameModel);
    	}
		return action;
    }
}