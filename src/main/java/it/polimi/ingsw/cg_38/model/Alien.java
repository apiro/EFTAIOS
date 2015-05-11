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
    public Boolean canMove(Sector sector) {
        // TODO implement here
    	/*se il settore è nei diretti adiacenti del currentSector*/
    	if(this.getCurrentSector().getNeighboringSectors().contains(sector)) {
    		return true;
    	}
    	/*se il settore è nei DOPPI-diretti adiacenti del currentSector*/
    	for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
    		for(Sector sec2: sec.getNeighboringSectors()){
        		if(sec2.equals(sector) && !(sec2.equals(this.getCurrentSector()))) {
        			return true;
        		}
    		}
    	}
    	if(this.getIsPowered()) {
    		for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
        		for(Sector sec2: sec.getNeighboringSectors()) {
        			for(Sector sec3: sec2.getNeighboringSectors()){
    	        		if(sec3.equals(sector) && !(sec3.equals(this.getCurrentSector()))) {
    	        			return true;
    	        		}
        			}
        		}
        	}
    	}
    	return false;
    }

}