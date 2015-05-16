package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.model.Sector;

/**
 * 
 */
public class UseRandomSectorNoise extends Action {

    /**
     * 
     */
    public UseRandomSectorNoise(Sector sector) {
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