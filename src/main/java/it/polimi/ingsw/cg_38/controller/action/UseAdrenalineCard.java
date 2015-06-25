package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventADRENALINE;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyCardPerformed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

/**
 * 
 * in realta anche questo perform sarebbe void
 * 
 */
public class UseAdrenalineCard extends GameAction {

	private static final long serialVersionUID = 1L;

    public UseAdrenalineCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((ObjectCard)((EventADRENALINE)evt).getToUse()));
    }

    private ObjectCard card;
    
    public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

	@Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.handleRejectedCard(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventRejectCardAlien(model.getActualTurn().getCurrentPlayer()));
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, card.getType()));
    	} else {
    		model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.handleRejectedCard(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventNotifyCardPerformed(model.getActualTurn().getCurrentPlayer()));
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true, card.getType()));	
    	}
    	/*callbackEvent.add(new EventUsedCard(model.getActualTurn().getCurrentPlayer(), ((ObjectCard)card).getType()));*/
    	return callbackEvent;
    }

	@Override
    public Boolean isPossible(GameModel model) {
        if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
        		!model.getActualTurn().getHasUsedObjectCard() &&
            	super.isPossible(model)) {
        	return true;
        }
        return false;
    }
}
