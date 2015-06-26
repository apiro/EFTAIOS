package it.polimi.ingsw.cg_38.controller.action;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTELEPORT;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTeleport;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
/** identifica l'utilizzo della carta teletraporto */
public class UseTeleportCard extends GameAction {

	private static final long serialVersionUID = 1L;

	/** invoca il costruttore della superclasse e setta i dati
	 * 
	 * @param evt evento di gioco che ha generato l'azione
	 */
    public UseTeleportCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((ObjectCard)((EventTELEPORT)evt).getToUse()));
    }

    private ObjectCard card;
    
    public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}

	/** se l'avatar del giocatore che ha generato l'azione e di tipo alien scarto la carta,
	 * altrimenti setto il settore corrente dell'avatar al settore di partenza degli umani e genero
	 * i vari eventi di notifica
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
    	
    	model.getActualTurn().getCurrentPlayer().getAvatar().move(
    			model.getGameMap().searchSectorByName("HumanStartingPoint"), 
    			model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().setNumTurniGiocati(model.getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.handleRejectedCard(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	callbackEvent.add(new EventNotifyTeleport(model.getActualTurn().getCurrentPlayer(), model.getActualTurn().getCurrentPlayer().
    			getAvatar().getCurrentSector().getName()));
    	callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true, card.getType()));
    	return callbackEvent;
    }

	/** verifica se il giocatore possiede la carta
	 * 
	 * @param model gameModel sul quale fare la verifica
	 * @return true se è possibile effettuare l'azione sul model
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
