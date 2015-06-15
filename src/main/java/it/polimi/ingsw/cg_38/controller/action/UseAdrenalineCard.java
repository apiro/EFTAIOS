package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAdren;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;

/**
 * 
 * in realta anche questo perform sarebbe void
 * 
 */
public class UseAdrenalineCard extends GameAction {

	private static final long serialVersionUID = 1L;

    public UseAdrenalineCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((EventAdren)evt).getToUse());
    }

    private Card card;
    
    public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	/**
     * @return
     */
    public ArrayList<NotifyEvent> perform(GameModel model) {
    	ArrayList<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false));
    	} else {
    		model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true));	
    	}
    	/*callbackEvent.add(new EventUsedCard(model.getActualTurn().getCurrentPlayer(), ((ObjectCard)card).getType()));*/
    	return callbackEvent;
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        if(!this.currentAvatarType(model).equals("Alien")) {
        	if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
            		super.isPossible(model)) {
        		return true;
        	}
        } else {
        	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
        }
        return false;
    }
}