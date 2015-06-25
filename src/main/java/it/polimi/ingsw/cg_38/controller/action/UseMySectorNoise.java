package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import  it.polimi.ingsw.cg_38.model.*;


public class UseMySectorNoise extends GameAction {

	private static final long serialVersionUID = 1L;

	public UseMySectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    }

    /**
     * @return
     */
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	callbackEvent.add(new EventDeclareNoise(model.getActualTurn().getCurrentPlayer() , model.getActualTurn().getCurrentPlayer().getAvatar().getCurrentSector()));
    	return callbackEvent;
    }
    
    @Override
    public Boolean isPossible(GameModel model) {
    	return super.isPossible(model);
    }

}
