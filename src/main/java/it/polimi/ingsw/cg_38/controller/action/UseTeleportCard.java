package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTeleport;
import it.polimi.ingsw.cg_38.notifyEvent.EventRejectCard;

public class UseTeleportCard extends GameAction {

	private static final long serialVersionUID = 1L;

    public UseTeleportCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((ObjectCard)((EventTeleport)evt).getToUse()));
    }

    private ObjectCard card;
    
    public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

	/**
     * @return
     */
    public ArrayList<NotifyEvent> perform(GameModel model) {
    	ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	
    	if(this.currentAvatarType(model).equals("Alien")){
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventRejectCard(model.getActualTurn().getCurrentPlayer()));
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, card.getType()));
    		return callbackEvent;
    	}
    	
    	model.getActualTurn().getCurrentPlayer().getAvatar().move(
    			model.getGameMap().searchSectorByName("HumanStartingPoint"), 
    			model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().setNumTurniGiocati(model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	callbackEvent.add(new EventNotifyTeleport(model.getActualTurn().getCurrentPlayer(), model.getActualTurn().getCurrentPlayer().
    			getAvatar().getCurrentSector().getName()));
    	callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true, card.getType()));
    	return callbackEvent;
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
	    if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
	    		!model.getActualTurn().getHasUsedObjectCard() &&
	    		super.isPossible(model)) {
	    	return true;
	    }
	     return false;
	}
}
