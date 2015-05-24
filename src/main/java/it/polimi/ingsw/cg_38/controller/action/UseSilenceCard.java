package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;

public class UseSilenceCard extends GameAction {

    /**
     * 
     */
    public UseSilenceCard(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /**
     * @return
     */
    public NotifyEvent perform(GameModel model) {
        return new EventDeclarePosition(model.getActualTurn().getCurrentPlayer(), null);
    }

	/**
     * @return
     */
    public Boolean isPossible(GameModel model) {
         return true;
    }

}