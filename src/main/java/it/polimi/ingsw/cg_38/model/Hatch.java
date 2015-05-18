package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Hatch extends Sector {

    /**
     * 
     */
	
	private Boolean isOpen = true;
	
    public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Hatch() {
    	super();
    	this.name = "Hatch";
    }
    
}