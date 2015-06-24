package it.polimi.ingsw.cg_38.model;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;

/**
 * 
 */
public class Alien extends Avatar implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    public Alien(Name name, Sector sector) {
    	this.setCurrentSector(sector);
    	this.setName(name);
    }

    @Override
	public Boolean hasDefenseCard() {
		return false;
	}

	/**
     * @param Sector sector 
     * @return 
     */
    public Boolean canMove(Sector sector) {
    	if(sector.getName().equals("AlienStartingPoint") || sector.getName().equals("Hatch")) {
    		return false;
    	}
    	if(this.getCurrentSector().getNeighboringSectors().contains(sector)) {

        	if(sector.getName().equals("Hatch")) {
        		return false;
        	}
    		return true;
    	}
    	for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
    		for(Sector sec2: sec.getNeighboringSectors()){
        		if(sec2.equals(sector) && !(sec2.equals(this.getCurrentSector()))) {

        	    	if(sector.getName().equals("Hatch")) {
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

    	        	    	if(sector.getName().equals("Hatch")) {
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