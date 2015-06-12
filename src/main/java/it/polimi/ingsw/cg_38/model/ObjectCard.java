package it.polimi.ingsw.cg_38.model;

/**
 * 
 */
public class ObjectCard extends Card {
	
	private static final long serialVersionUID = 1L;

	/*
	@Override
	public String toString() {
		return "ObjectCard [type=" + type + "]";
	}
	
	*/

	private ObjectCardType type;
	
    /**
     * @param ObjectCardType type 
     */
    public ObjectCard(ObjectCardType type) {
        // TODO implement here
        this.setType(type);
    }

	public ObjectCardType getType() {
		return type;
	}

	public void setType(ObjectCardType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ObjectCard [type=" + type + "]";
	}

}