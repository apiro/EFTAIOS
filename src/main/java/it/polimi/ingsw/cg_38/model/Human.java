package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Human extends Avatar {

    /**
     * 
     */
    public Human() {
    }

    /**
     * @param Sector sector 
     * @return
     */
    public Boolean canMove(Sector sector, Sector currentSector) {
        // TODO implement here
    	if(currentSector.getNeighboringSectors().contains(sector)) {
    		return true;
    	} else if(this.getIsPowered()) {
        	for(Sector sec: currentSector.getNeighboringSectors()){
        		if(sec.equals(sector)) {
        			return true;
        		}
        	}
    	}
        return false;
    }

    /**
     * @param String name
     */
    public Avatar createHuman(String name) {
        // TODO implement here
    	return null;
    }

}