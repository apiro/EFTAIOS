package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 */
public class UseSedativesCard extends Action {

    /**
     * 
     */
    public UseSedativesCard(Card card) {
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
    	this.getGameModel().getActualTurn().setHasDraw(true);
    	return true;
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
    	if(!(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien) || this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard())) {
        	return true;
        }
        return false;
    }

}