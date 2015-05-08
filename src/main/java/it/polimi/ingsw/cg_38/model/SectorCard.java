package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class SectorCard extends Card {

    /**
     * costruttore
     */
    public SectorCard(SectorCardType type) {
    	this.setType(type);
    }

	/**
     * attributi
     */
    private Boolean hasObjectIcon;
    private SectorCardType type;

    /**
     * @param SectorCardType type 
     * @return
     */
    public SectorCard SectorCard(SectorCardType type) {
        // TODO implement here
        return null;
    }
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