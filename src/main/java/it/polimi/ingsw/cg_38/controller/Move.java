package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 */
public class Move extends Action {

    /**
     * 
     */
    public Move(Sector sectorToMove) {
    	this.setSectorToMove(sectorToMove);
    }

    public Sector getSectorToMove() {
		return sectorToMove;
	}

	public void setSectorToMove(Sector sectorToMove) {
		this.sectorToMove = sectorToMove;
	}

	/**
     * 
     */
    public Sector sectorToMove;

    /**
     * @return
     */
    public Boolean perform() {
        // TODO implement here
    	this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().setCurrentSector(this.getSectorToMove());
    	return true;
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        if(!this.getGameModel().getActualTurn().getHasMoved()) {
        	return true;
        }
        return false;
    }
}