package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 * in realta anche questo perform sarebbe void
 * 
 */
public class UseAdrenalineCard extends GameAction {

    /**
     * 
     */
    public UseAdrenalineCard(Card card, GameModel gameModel) {
    	super(gameModel);
    	this.setCard(card);
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
    public Boolean perform() {
        // TODO implement here
    	if(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) {
    		return false;
    	} else {
    		this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
    		this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		this.getGameModel().getActualTurn().setHasUsedObjectCard(true);
    		return true;	
    	}
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
        if(!!this.currentAvatarType().equals("Alien") && 
    			this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard())) {
        	return true;
        }
        return false;
    }
}