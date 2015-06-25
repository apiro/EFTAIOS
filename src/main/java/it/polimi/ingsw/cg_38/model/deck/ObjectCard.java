package it.polimi.ingsw.cg_38.model.deck;

/** identifica una carta oggetto */
public class ObjectCard extends Card {

	private static final long serialVersionUID = 1L;
	
	/** indica il tipo della carta oggetto */
	private ObjectCardType type;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/** stabilice l'uguaglianza tra due oggetti
	 * @param obj indica l'oggetto da eguagliare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectCard other = (ObjectCard) obj;
		if (type != other.type)
			return false;
		return true;
	}

	/*
	@Override
	public String toString() {
		return "ObjectCard [type=" + type + "]";
	}
	
	*/

    public ObjectCard(ObjectCardType type) {
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