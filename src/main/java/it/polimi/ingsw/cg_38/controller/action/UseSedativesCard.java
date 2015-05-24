package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSedat;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;

/**
 * 
 * anche questa perform è void in realtà
 * 
 */
public class UseSedativesCard extends GameAction {

    /**
     * 
     */
    public UseSedativesCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((EventSedat)evt).getToUse());
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
    	model.getActualTurn().setHasDraw(true);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	return new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true);
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
        // TODO implement here
    	if(!this.currentAvatarType(model).equals("Alien") && 
    			model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard())) {
        	return true;
        }
        return false;
    }

}