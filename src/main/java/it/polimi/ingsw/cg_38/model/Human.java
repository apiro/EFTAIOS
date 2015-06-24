package it.polimi.ingsw.cg_38.model;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;

public class Human extends Avatar implements Serializable {

	private static final long serialVersionUID = 1L;

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

	/** check if human can move in target sector */
    public Boolean canMove(Sector sector) {
        // TODO implement here
    	if(sector.getName().equals("HumanStartingPoint")) {
    		return false;
    	}
    	if(this.getCurrentSector().getNeighboringSectors().contains(sector)) {

        	if(sector.getName().equals("Hatch")) {
        		return ((Hatch)sector).getIsOpen();
        	}
    		return true;
    	} else if(this.getIsPowered()) {
        	for(Sector sec: this.getCurrentSector().getNeighboringSectors()){
        		for(Sector sec2: sec.getNeighboringSectors()){
	        		if(sec2.equals(sector) && !(sec2.equals(this.getCurrentSector()))) {

	        	    	if(sector.getName().equals("Hatch")) {
	        	    		return ((Hatch)sector).getIsOpen();
	        	    	}
	        			return true;
	        		}
        		}
        	}
    	}
        return false;
    }
}