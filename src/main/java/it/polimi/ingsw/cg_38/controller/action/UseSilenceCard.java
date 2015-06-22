package it.polimi.ingsw.cg_38.controller.action;

import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.model.GameModel;

public class UseSilenceCard extends GameAction {

	private static final long serialVersionUID = 1L;

    public UseSilenceCard(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /**
     * @return
     */
    public ArrayList<NotifyEvent> perform(GameModel model) {
    	ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
        callbackEvent.add(new EventDeclareNoise(model.getActualTurn().getCurrentPlayer(), null));
        return callbackEvent;
    }

	/**
     * @return
     */
    public Boolean isPossible(GameModel model) {
         return super.isPossible(model);
    }

}