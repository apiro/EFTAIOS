package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class HatchCard extends Card {

	private HatchCardType color;
	
    /**
     * 
     */
    public HatchCard(HatchCardType type) {
    	this.setColor(type);
    }

	public HatchCardType getColor() {
		return color;
	}

	public void setColor(HatchCardType color) {
		this.color = color;
	}


}