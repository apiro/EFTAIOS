package it.polimi.ingsw.cg_38.model;

import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;

public class Movement implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Movement [ riga=" + targetsector.getRow() + ", colonna=" + targetsector.getCol() + "]";
	}

    public Movement(Sector sector , int number) {
        this.setTargetsector(sector);
        this.setTurnNumber(number);
    }

    public int turnNumber;

    public Sector targetsector;

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public Sector getTargetsector() {
		return targetsector;
	}

	public void setTargetsector(Sector targetsector) {
		this.targetsector = targetsector;
	}

}