package it.polimi.ingsw.cg_38.controller.action;


import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.map.Sector;

public class UseRandomSectorNoise extends GameAction {

	private static final long serialVersionUID = 1L;

	public UseRandomSectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setToDeclare(((EventNoiseRandSect)evt).getToNoise());
    }
	
	@Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	callbackEvent.add(new EventDeclareNoise(model.getActualTurn().getCurrentPlayer(), this.getToDeclare()));
    	return callbackEvent;
    }

    private Sector toDeclare;
    
    public Sector getToDeclare() {
		return toDeclare;
	}

	public void setToDeclare(Sector toDeclare) {
		this.toDeclare = toDeclare;
	}

	@Override
    public Boolean isPossible(GameModel model) {
        return super.isPossible(model);
    }

}
