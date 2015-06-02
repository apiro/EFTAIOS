package it.polimi.ingsw.cg_38.model;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public class Human extends Avatar implements Serializable {

    /**
     * 
     */
    public Human(Name name, Sector sector) {
    	this.setName(name);
    	this.setCurrentSector(sector);
    }

    private Boolean canAttack = false;
    
    public Boolean getCanAttack() {
		return canAttack;
	}

	public void setCanAttack(Boolean canAttack) {
		this.canAttack = canAttack;
	}

	/**
     * @param Sector sector 
     * @return
     */
    public Boolean canMove(Sector sector) {
        // TODO implement here
    	if(sector.getName().equals("HumanStartingPoint")) {
    		return false;
    	}
    	if(this.getCurrentSector().getNeighboringSectors().contains(sector)) {
    		return true;
    	} else if(this.getIsPowered()) {
        	for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
        		for(Sector sec2: sec.getNeighboringSectors()){
	        		if(sec2.equals(sector) && !(sec2.equals(this.getCurrentSector()))) {
	        			return true;
	        		}
        		}
        	}
    	}
        return false;
    }
}