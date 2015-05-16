package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Human extends Avatar {

    /**
     * 
     */
    public Human(Name name) {
    	this.setName(name);
    }

    private Boolean canAttack;
    
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