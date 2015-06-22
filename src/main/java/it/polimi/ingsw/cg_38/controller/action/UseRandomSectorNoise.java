package it.polimi.ingsw.cg_38.controller.action;


import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;

public class UseRandomSectorNoise extends GameAction {

	private static final long serialVersionUID = 1L;

	public UseRandomSectorNoise(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setToDeclare(((EventNoiseRandSect)evt).getToNoise());
    }

    /**
     * @return
     */
    public ArrayList<NotifyEvent> perform(GameModel model) {
    	ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
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

	/**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        return super.isPossible(model);
    }

}
