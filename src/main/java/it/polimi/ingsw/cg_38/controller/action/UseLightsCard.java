package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyCardPerformed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.util.*;

/**identifica l'azione di utilizzo della carta luci */
public class UseLightsCard extends GameAction {

	private static final long serialVersionUID = 1L;

    private ObjectCard card;
    
    /** invoca il costruttore della superclasse e setta i vari dati
     * 
     * @param evt evento di gioco che ha generato l'azione
     */
    public UseLightsCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((ObjectCard)((EventSPOTLIGHT)evt).getToUse()));
    	this.setTargetSector(((EventSPOTLIGHT)evt).getTarget());
    }

    public ObjectCard getCard() {
		return card;
	}

	public void setCard(ObjectCard card) {
		this.card = card;
	}
    
	private Sector targetSector;
	
    public Sector getTargetSector() {
		return targetSector;
	}

	public void setTargetSector(Sector targetSector) {
		this.targetSector = targetSector;
	}

	/** se l'avatar del giocatore che ha generato l'azione è di tipo alieno la carta viene scartata, altrimenti
	 * viene generati un evento di notifica di tipo dichiarazione della posizione 
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
    	
    	List<Player> players = new ArrayList<Player>();
    	for(Player pl:model.getDesiredPlayers(this.getTargetSector())) {
    		
    			players.add(pl);
    		
    	}
    	for(Sector sec:model.getGameMap().searchSectorByCoordinates(this.getTargetSector().getRow(), this.getTargetSector().getCol()).getNeighboringSectors()) {
    		for(Player pl:model.getDesiredPlayers(sec)) {
    			
    				players.add(pl);
    			
        	}
    	}
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.handleRejectedCard(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	callbackEvent.add(new EventNotifyCardPerformed(model.getActualTurn().getCurrentPlayer()));
    	callbackEvent.add(new EventDeclarePosition(model.getActualTurn().getCurrentPlayer(), players));
    	callbackEvent.add(new EventCardUsed(model.getActualTurn().getCurrentPlayer(), true, card.getType()));
    	return callbackEvent;
    }

	/** verifica se il giocatore possiede la carta
	 * 
	 * @param model gameModel sul quale fare la verifics
	 * @return ritorna true se è possibile effettuare l'azione sul model
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
