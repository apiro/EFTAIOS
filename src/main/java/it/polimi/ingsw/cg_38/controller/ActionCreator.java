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
    public Action createAction(String type, Sector sector) {
    	// TODO implement here
    	Action action = null;
    	if(type == "Attack") {
    		action = new Attack(sector);
    	} else if (type == "Draw") {
    		action = new Draw();
    	} else if (type == "Move") {
    		action = new Move(sector);
    	}  else if (type == "UseAdrenalineCard") {
    		action = new UseAdrenalineCard();
    	} else if (type == "UseAttackCard") {
    		action = new UseAttackCard(sector);
    	} else if (type == "UseLightsCard") {
    		action = new UseLightsCard();
    	} else if (type == "UseSedativesCard") {
    		action = new UseSedativesCard();
    	}
		return action;
    }

}