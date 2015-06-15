package it.polimi.ingsw.cg_38.controller.action;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventLights;
import  it.polimi.ingsw.cg_38.model.*;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;

import java.util.*;

/**
 * 
 * la perform ritorna l'arraylist dei giocatori che devono dichiarare la loro posizione(nel player che ritorna 
 * c'è il currentSector che deve essere mandato a tutti i giocatori)
 * 
 */
public class UseLightsCard extends GameAction {

    /**
     * 
     */
    public UseLightsCard(GameEvent evt) {
    	super(evt.getGenerator());
    	this.setCard(((EventLights)evt).getCard());
    	this.setTargetSector(((EventLights)evt).getTarget());
    }

    private Card card;
    
    public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
    
	private Sector targetSector;
	
    public Sector getTargetSector() {
		return targetSector;
	}

	public void setTargetSector(Sector targetSector) {
		this.targetSector = targetSector;
	}

	/**
     * @return
     */
    public NotifyEvent perform(GameModel model) {
    	ArrayList<Player> players = new ArrayList<Player>();
    	for(Player pl:model.getDesiredPlayers(this.getTargetSector())) {
    		
    			players.add(pl);
    		
    	}
    	for(Sector sec:model.getGameMap().searchSectorByCoordinates(this.getTargetSector().getRow(), this.getTargetSector().getCol()).getNeighboringSectors()) {
    		for(Player pl:model.getDesiredPlayers(sec)) {
    			
    				players.add(pl);
    			
        	}
    	}
    	model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	model.getActualTurn().setHasUsedObjectCard(true);
    	return new EventDeclarePosition(model.getActualTurn().getCurrentPlayer(), players);
    }

    /**
     * @return
     */
    public Boolean isPossible(GameModel model) {
    	if(!this.currentAvatarType(model).equals("Alien")){
    		if(model.getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard()) && 
        			super.isPossible(model))
        			return true;
    	}
    	else{
    		model.getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	}
        return false;
    }

}