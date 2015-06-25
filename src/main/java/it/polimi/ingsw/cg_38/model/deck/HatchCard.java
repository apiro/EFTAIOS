package it.polimi.ingsw.cg_38.model.deck;

/** identifica una carta di tipo scialuppa */
public class HatchCard extends Card {
	
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "HatchCard [color=" + color + "]";
	}

	/** indica il colore della carta scialuppa */
	private HatchCardType color;

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