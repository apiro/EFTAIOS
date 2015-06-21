package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAdrenaline;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.notifyEvent.EventRejectCard;

/**
 * 
 * la perform ritorna true e modifica il modello(in realta è un void ma per seguire l'abstract method ritorno di 
 * default true
 * 
 */
public class UseAttackCard extends GameAction {

	private Player generator;

	public UseAttackCard(GameEvent evt) {
		super(evt.getGenerator());
		this.generator = evt.getGenerator();
		this.setCard(((ObjectCard)((EventAttackCard)evt).getToUse()));
    	this.setSectorToAttack(((EventAttackCard)evt).getTarget());
    }

    public Sector getSectorToAttack() {
		return sectorToAttack;
	}

	public void setSectorToAttack(Sector sectorToAttack) {
		this.sectorToAttack = sectorToAttack;
	}

	private ObjectCard card;
    private Sector sectorToAttack;
    
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
    	
    	((Human)model.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	GameAction humanAttackAction = new Attack(new EventAttack(generator, this.getSectorToAttack()));
    	
    	if(humanAttackAction.isPossible(model)) {
    		callbackEvent = humanAttackAction.perform(model);
    	}
    	model.getActualTurn().setHasUsedObjectCard(true);
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