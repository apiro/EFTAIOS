package it.polimi.ingsw.cg_38.controller;
import  it.polimi.ingsw.cg_38.model.*;
import java.util.*;

/**
 * 
 */
public class Move extends GameAction {

    /**
     * 
     */
    public Move(Sector sectorToMove, GameModel gameModel) {
    	super(gameModel);
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
    public String perform() {
        // TODO implement here
    	String toReturn = this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().move(getSectorToMove(), this.getGameModel().getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	this.getGameModel().getActualTurn().getCurrentPlayer().setNumTurniGiocati(this.getGameModel().getActualTurn().getCurrentPlayer().getNumTurniGiocati()+1);
    	this.getGameModel().getActualTurn().setHasMoved(true);
    	return toReturn;
    }

    /**
     * @return
     */
    public Boolean isPossible() {
        if(!this.getGameModel().getActualTurn().getHasMoved() &&
        		this.getGameModel().getActualTurn().getCurrentPlayer().getAvatar().canMove(getSectorToMove())) {
        	return true;
        }
        return false;
    }
}