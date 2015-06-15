package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;

/**
 * 
 * la perform ritorna true e modifica il modello(in realta è un void ma per seguire l'abstract method ritorno di 
 * default true
 * 
 */
public class UseAttackCard extends GameAction {

	public UseAttackCard(GameEvent evt) {
		super(evt.getGenerator());
    	this.setCard(((EventAttackCard)evt).getToUse());
    	this.setSectorToAttack(((EventAttackCard)evt).getTarget());
    }

    public Sector getSectorToAttack() {
		return sectorToAttack;
	}

	public void setSectorToAttack(Sector sectorToAttack) {
		this.sectorToAttack = sectorToAttack;
	}

	private Card card;
    private Sector sectorToAttack;
    
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
    	((Human)model.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	GameAction humanAttackAction = new Attack(new EventAttack(model.getActualTurn().getCurrentPlayer(), this.getSectorToAttack()));
    	
    	if(humanAttackAction.isPossible(model)) {
    		callbackEvent = humanAttackAction.perform(model);
    	}
    	model.getActualTurn().setHasUsedObjectCard(true);
    	callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true));
    	return callbackEvent;
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