package it.polimi.ingsw.cg_38.model.map;

/**
 * 
 */
public class Hatch extends Sector {
	
	private static final long serialVersionUID = 1L;

	/** show if the sectors opened*/
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