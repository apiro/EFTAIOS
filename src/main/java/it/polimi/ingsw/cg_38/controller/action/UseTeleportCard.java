package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;

/**
 * 
 * anche questa perform è void in realta
 * 
 */
public class UseTeleportCard extends GameAction {

    /**
     * 
     */
    public UseTeleportCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((EventTeleport)evt).getToUse());
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
    	model.getActualTurn().getCurrentPlayer().getAvatar().move(
    			model.getGameMap().searchSectorByName("HumanStartingPoint"), 
    			model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().setNumTurniGiocati(model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	return new EventMoved(model.getActualTurn().getCurrentPlayer(), model.getActualTurn().getCurrentPlayer().
    			getAvatar().getCurrentSector().getName());
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
    	if(!this.currentAvatarType(model).equals("Alien")){
    		if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
        			super.isPossible(model)){
    			return true;
    		}
    	}else{
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	}
        
        return false;
    }
}