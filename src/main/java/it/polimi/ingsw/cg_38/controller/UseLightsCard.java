package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;

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
    public UseLightsCard(Card card, Sector sector, GameModel gameModel) {
    	super(gameModel);
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
    	this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().eliminateFromMyCards(card);
    	this.getGameModel().getActualTurn().setHasUsedObjectCard(true);
    	return players;
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