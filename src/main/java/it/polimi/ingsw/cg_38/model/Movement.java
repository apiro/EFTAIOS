package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Movement {

	@Override
	public String toString() {
		return "Movement [turnNumber=" + turnNumber + ", targetsector="
				+ targetsector + ", riga=" + targetsector.getRow() + ", colonna=" + targetsector.getCol() + "]";
	}

	/**
     * @param Sector sector  
     * @param int number
     */
    public Movement(Sector sector , int number) {
        this.setTargetsector(sector);
        this.setTurnNumber(number);
    }

    /**
     * 
     */
    public int turnNumber;

    /**
     * 
     */
    public Sector targetsector;

    /**
     * getter e setter
     */
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