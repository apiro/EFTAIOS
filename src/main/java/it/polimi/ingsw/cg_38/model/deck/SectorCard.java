package it.polimi.ingsw.cg_38.model.deck;

public class SectorCard extends Card {
	
	private static final long serialVersionUID = 1L;

    /** indica se la carta ha l'icona*/
	private Boolean hasObjectIcon;
	
	/** indica il tipo della carta settore */
    private SectorCardType type;
    
    @Override
	public String toString() {
		return "SectorCard [hasObjectIcon=" + hasObjectIcon + ", type=" + type
				+ "]";
	}

    public SectorCard(SectorCardType type, Boolean hasIcon) {
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