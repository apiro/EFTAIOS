package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Alien extends Avatar {

    /**
     * 
     */
    public Alien(Name name) {
    	this.setName(name);
    }

    /**
     * @param Sector sector 
     * @return 
     */
    public Boolean canMove(Sector sector, Sector currentSector) {
        // TODO implement here
    	/*se il settore è nei diretti adiacenti del currentSector*/
    	if(currentSector.getNeighboringSectors().contains(sector)) {
    		return true;
    	}
    	/*se il settore è nei DOPPI-diretti adiacenti del currentSector*/
    	for(Sector sec: currentSector.getNeighboringSectors()){
    		if(sec.equals(sector)) {
    			return true;
    		}
    	}
    	if(this.getIsPowered()) {
    		for(Sector sec: currentSector.getNeighboringSectors()){
        		for(Sector sec2: sec.getNeighboringSectors()) {
        			if(sec2.equals(sector)) {
        				return true;
        			}
        		}
        	}
    	}
    	return false;
    }

}