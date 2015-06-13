package it.polimi.ingsw.cg_38.controller.action;
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

    /**
     * 
     */
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
    public NotifyEvent perform(GameModel model) {
        // TODO implement here
    	if(model.getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
    		return new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false);
    	} else {
    		model.getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		return new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true);	
    	}
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        if(!this.currentAvatarType(model).equals("Alien") && 
        		model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
        		super.isPossible(model)) {
        	return true;
        }
        model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
        return false;
    }
}