package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.map.Sector;

/**
 * 
 * la perform ritorna true e modifica il modello(in realta è un void ma per seguire l'abstract method ritorno di 
 * default true
 * 
 */
public class UseAttackCard extends GameAction {

	private static final long serialVersionUID = 1L;
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
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	
    	if(this.currentAvatarType(model).equals("Alien")){
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.handleRejectedCard(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventRejectCardAlien(model.getActualTurn().getCurrentPlayer()));
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, card.getType()));
    		return callbackEvent;
    	}
    	
    	((Human)model.getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.handleRejectedCard(card);
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