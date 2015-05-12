package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class ObjectCard extends Card {

	@Override
	public String toString() {
		return "ObjectCard [type=" + type + "]";
	}

	private ObjectCardType type;
	
    /**
     * @param ObjectCardType type 
     */
    public ObjectCard(ObjectCardType type) {
        // TODO implement here
        this.setColor(type);
    }

	public ObjectCardType getColor() {
		return type;
	}

	public void setColor(ObjectCardType color) {
		this.type = color;
	}

}