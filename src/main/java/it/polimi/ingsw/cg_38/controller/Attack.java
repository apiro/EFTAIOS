package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 */
public class Attack extends Action {
	
    /**
     * 
     */
    public Sector sectorToAttack;

    /**
     * @return
     */
    public Boolean perform() { 	
        	for(Player pl:this.getGameModel().getDesiredPlayers(this.getSectorToAttack())) {
        		if(!pl.getAvatar().hasDefenseCard()) {
        			return pl.getAvatar().attacked();
        		}
        	}
        return false;
    }

    /**
     * @param Sector sector
     */
    public Attack(Sector sector) {
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
        if(this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar() instanceof Alien || !this.getGameModel().getActualTurn().getHasAttacked()) {
        	return true;
        }
        return false;
    }

}