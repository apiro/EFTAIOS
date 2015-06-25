package it.polimi.ingsw.cg_38.model;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;

/**
 * 
 */
public class Alien extends Avatar implements Serializable {

	private static final long serialVersionUID = 1L;

    public Alien(Name name, Sector sector) {
    	this.setCurrentSector(sector);
    	this.setName(name);
    }

    /** check if has defenseCard */
    @Override
	public Boolean hasDefenseCard() {
		return false;
	}

	/** check if avatar can move in target sector */
    @Override
    public Boolean canMove(Sector sector) {
    	if(("AlienStartingPoint").equals(sector.getName()) || ("Hatch").equals(sector.getName())) {
    		return false;
    	}
    	if(this.getCurrentSector().getNeighboringSectors().contains(sector)) {

        	if(("Hatch").equals(sector.getName())) {
        		return false;
        	}
    		return true;
    	}
    	for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
    		for(Sector sec2: sec.getNeighboringSectors()){
        		if(sec2.equals(sector) && !(sec2.equals(this.getCurrentSector()))) {

        	    	if(("Hatch").equals(sector.getName())) {
        	    		return false;
        	    	}
        			return true;
        		}
    		}
    	}
    	if(this.getIsPowered()) {
    		for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
        		for(Sector sec2: sec.getNeighboringSectors()) {
        			for(Sector sec3: sec2.getNeighboringSectors()){
    	        		if(sec3.equals(sector) && !(sec3.equals(this.getCurrentSector()))) {

    	        	    	if(("Hatch").equals(sector.getName())) {
    	        	    		return false;
    	        	    	}
    	        			return true;
    	        		}
        			}
        		}
        	}
    	}
    	return false;
    }

}