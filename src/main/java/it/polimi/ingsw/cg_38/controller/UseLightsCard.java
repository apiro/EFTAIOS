package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

import java.util.*;

/**
 * 
 */
public class UseLightsCard extends Action {

    /**
     * 
     */
    public UseLightsCard(Card card, Sector sector) {
    	this.setCard(card);
    	this.setTargetSector(sector);
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
    public ArrayList<Player> perform() {
        // TODO implement here
    	ArrayList<Player> players = new ArrayList<Player>();
    	for(Player pl:this.getGameModel().getDesiredPlayers(this.getTargetSector())) {
    		players.add(pl);
    	}
    	for(Sector sec:this.getGameModel().getGameMap().searchSectorByCoordinates(this.getTargetSector().getRow(), this.getTargetSector().getCol()).getNeighboringSectors()) {
    		for(Player pl:this.getGameModel().getDesiredPlayers(sec)) {
        		players.add(pl);
        	}
    	}
    	return players;
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