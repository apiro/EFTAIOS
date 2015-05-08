package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class SectorDeck extends Deck {

    /**
     * 
     */
    public SectorDeck() {
    	
    }

    /**
     * 
     */
    private ArrayList<SectorCard> sectorDeck;

    /**
     * 
     */
    private ArrayList<SectorCard> rejectedSectorDeck;

    /**
     * @return
     */
    public void shuffle() {
        // TODO implement here
    	Collections.shuffle(this.getSectorDeck());
    }

    public ArrayList<SectorCard> getSectorDeck() {
		return sectorDeck;
	}

	public void setSectorDeck(ArrayList<SectorCard> sectorDeck) {
		this.sectorDeck = sectorDeck;
	}

	public ArrayList<SectorCard> getRejectedSectorDeck() {
		return rejectedSectorDeck;
	}

	/**
     * @return
     */
    public Card draw() {
        // TODO implement here
    	Card estracted = this.getSectorDeck().get(0);
    	this.getSectorDeck().remove(estracted);
    	this.shuffle();
        return estracted;
    }

    /**
     * @param Card card 
     * @return
     */
    public void eliminateCard(Card card) {
        // TODO implement here
    	this.getSectorDeck().remove(card);
    	this.getRejectedSectorDeck().add((SectorCard) card);
    }

}