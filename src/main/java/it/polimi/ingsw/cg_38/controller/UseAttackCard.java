package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 * la perform ritorna true e modifica il modello(in realta è un void ma per seguire l'abstract method ritorno di 
 * default true
 * 
 */
public class UseAttackCard extends GameAction {

	public UseAttackCard(Card card, Sector sector, GameModel gameModel) {
		super(gameModel);
    	this.setCard(card);
    	this.setSectorToAttack(sector);
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
    public Boolean perform() {
        // TODO implement here
    		((Human)this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar()).setCanAttack(true);
    		this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    		GameAction humanAttackAction = new Attack(this.getSectorToAttack(), this.getGameModel());
    		if(humanAttackAction.isPossible()) {
    			humanAttackAction.perform();
    		}
    		this.getGameModel().getActualTurn().setHasUsedObjectCard(true);
    		return true;
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
    	if(!this.currentAvatarType().equals("Alien") ||
    			this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().getMyCards().contains(this.getCard())) {
        	return true;
        }
        return false;
    }
}