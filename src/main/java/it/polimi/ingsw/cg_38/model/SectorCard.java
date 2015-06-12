package it.polimi.ingsw.cg_38.model;

/**
 * 
 */
public class SectorCard extends Card {
	
	private static final long serialVersionUID = 1L;

    @Override
	public String toString() {
		return "SectorCard [hasObjectIcon=" + hasObjectIcon + ", type=" + type
				+ "]";
	}

	private Boolean hasObjectIcon;
    private SectorCardType type;

    /**
     * @param SectorCardType type Boolean hasIcon
     * @return
     */
    public SectorCard(SectorCardType type, Boolean hasIcon) {
        // TODO implement here
    	this.setType(type);
    	if(hasIcon == true) {
    		this.setHasObjectIcon(true);
    	} else this.setHasObjectIcon(false);
    }
    
    /*
    @Override
	public String toString() {
		return "SectorCard [hasObjectIcon=" + hasObjectIcon + ", type=" + type
				+ "]";
	}
	
	*/

	/**
     * getter e setter 
     * **/
    public Boolean getHasObjectIcon() {
		return hasObjectIcon;
	}

	public void setHasObjectIcon(Boolean hasObjectIcon) {
		this.hasObjectIcon = hasObjectIcon;
	}

	public SectorCardType getType() {
		return type;
	}
	
	public void setType(SectorCardType type) {
		this.type = type;
	}
}