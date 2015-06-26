package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSEDATIVES;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyCardPerformed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;

/** identifica l'azione di utilizzo della carta sedativo */
public class UseSedativesCard extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
    public UseSedativesCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((ObjectCard)((EventSEDATIVES)evt).getToUse()));
    }

    private ObjectCard card;
    
    public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

	/** modifica lo stato del turno attuale settando a true la variabile setHasUsedObjectCard
	 * 
	 * @param model gameModel sul quale performare l'azione
	 * @return lista di eventi di notifica generati
	 */
	@Override
    public List<NotifyEvent> perform(GameModel model) {
    	List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
    	if(("Alien").equals(this.currentAvatarType(model))){
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		model.handleRejectedCard(card);
    		model.getActualTurn().setHasUsedObjectCard(true);
    		callbackEvent.add(new EventRejectCardAlien(model.getActualTurn().getCurrentPlayer()));
    		callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), false, card.getType()));
    		return callbackEvent;
    	}
    	model.getActualTurn().setHasDraw(true);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.handleRejectedCard(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	callbackEvent.add(new EventNotifyCardPerformed(model.getActualTurn().getCurrentPlayer()));
    	callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true, card.getType()));
    	return callbackEvent;
    }

	/** verifica se il giocatore possiede la carta e se il gioco è in esecuzione
	 * 
	 * @param model gameModel sul quale fare la verifica
	 * @return true se è possibile performare l'azione sul gioco
	 */
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
