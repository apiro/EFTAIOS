package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 * anche questa perform è void in realta
 * 
 * 
 * è l'azione di attacco base.attacco sia di umani che di alieni senza disitinzioni.
 *  l'azione attacco per gli alieni viene generata con un'istanza di questa classe, per
 *  gli umani viene generata l'azione useAttackCard(Card card, Sector sector) che genera una azione attacco e la performa
 * 
 */
public class Attack extends GameAction {
	
    /**
     * 
     */
    public Sector sectorToAttack;

    /**
     * @return
     */
    public Boolean perform() { 	
        	for(Player pl:this.getGameModel().getDesiredPlayers(this.getSectorToAttack())) {
        		//nella condizione di if oltre a verificare se l'attacco è andato a buon fine modifico anche il modello
        		//del gicatore attaccato
        		if(pl.getAvatar().attacked()) {
        		//se l'attacco è andato a buon fine sul giocatore i che si trovava nel settore che hai attaccato
        			if(this.currentAvatarType().equals("Alien")) {
        				this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().setIsPowered(true);
        			} else {
        				//se il giocatore che attacca è umano non so cosa succede all'umano che uccide qualcuno,
        				//se diventa potenziato allora non serve questo ultimo ramo if-else e il setIsPowered è 
        				//sia per umani che per alieni
        			}
        		}
        	}
        return false;
    }

    /**
     * @param Sector sector
     */
    public Attack(Sector sector, GameModel gameModel) {
    	super(gameModel);
    	this.setSectorToAttack(sector);
    }

    public Sector getSectorToAttack() {
		return sectorToAttack;
	}

	public void setSectorToAttack(Sector sectorToAttack) {
		this.sectorToAttack = sectorToAttack;
	}
	
	/**
     * @return
     */
    public Boolean isPossible() {
        if(this.currentAvatarType().equals("Alien") &&
        		!this.getGameModel().getActualTurn().getHasAttacked() && 
        		!this.getGameModel().getActualTurn().getHasDraw()) {
        	return true;
        } else if (this.currentAvatarType().equals("Human") && 
        		((Human)this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar()).getCanAttack() && 
        		!this.getGameModel().getActualTurn().getHasAttacked() && 
        		!this.getGameModel().getActualTurn().getHasDraw()) {
        	return true;
        }
        return false;
    }

}