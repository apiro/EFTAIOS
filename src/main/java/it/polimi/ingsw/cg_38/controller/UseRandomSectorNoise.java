package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.model.GameModel;
import it.polimi.ingsw.cg_38.model.Sector;

/**
 * 
 */
public class UseRandomSectorNoise extends GameAction {

    /**
     * 
     */
    public UseRandomSectorNoise(Sector sector, GameModel gameModel) {
    	super(gameModel);
    	this.setToDeclare(sector);
    }

    /**
     * @return
     */
    public Sector perform() {
        // TODO implement here
    	return this.getToDeclare();
    }

    private Sector toDeclare;
    
    public Sector getToDeclare() {
		return toDeclare;
	}

	public void setToDeclare(Sector toDeclare) {
		this.toDeclare = toDeclare;
	}

	/**
     * @return
     */
    public Boolean isPossible() {
        // TODO implement here
        return true;
    }

}