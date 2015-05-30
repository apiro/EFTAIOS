package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;

import java.util.*;

/**
 * 
 */
public class UseMySectorNoise extends GameAction {

    /**
     * 
     */
    public UseMySectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /**
     * @return
     */
    public NotifyEvent perform(GameModel model) {
        ArrayList<Player> toDeclare = new ArrayList<Player>();
        toDeclare.add(model.getActualTurn().getCurrentPlayer());
    	return new EventDeclarePosition(model.getActualTurn().getCurrentPlayer(), toDeclare);
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        // TODO implement here
    	return super.isPossible(model);
    }

}