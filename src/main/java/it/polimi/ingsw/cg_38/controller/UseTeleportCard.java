package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 * anche questa perform è void in realta
 * 
 */
public class UseTeleportCard extends GameAction {

    /**
     * 
     */
    public UseTeleportCard(Card card, GameModel gameModel) {
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
    	this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(
    			this.getGameModel().getGameMap().searchSectorByName("HumanStartingPoint"));
    	this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	return true;
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
    	if(!this.currentAvatarType().equals("Alien") && 
    			this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard())) {
        	return true;
        }
        return false;
    }
}