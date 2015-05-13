package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class HatchDeck extends Deck {

	
	public void printDeck() {
		for (Card card: this.getHatchDeck()) {
			System.out.println(card.toString());
		}
	}

	/**
     * 
     */
    public HatchDeck() {
    	this.setHatchDeck(new ArrayList<HatchCard>());
    	this.setRejectedHatchDeck(new ArrayList<HatchCard>());
    	for(int i = 0; i<3; i++) {
    		this.getHatchDeck().add(new HatchCard(HatchCardType.Red));
    		this.getHatchDeck().add(new HatchCard(HatchCardType.Green));
    	}
    }

    public void setRejectedHatchDeck(ArrayList<HatchCard> rejectedHatchDeck) {
		this.rejectedHatchDeck = rejectedHatchDeck;
	}

	public void setHatchDeck(ArrayList<HatchCard> hatchDeck) {
		this.hatchDeck = hatchDeck;
	}

	/**
     * 
     */
    private ArrayList<HatchCard> hatchDeck;

    /**
     * 
     */
    private ArrayList<HatchCard> rejectedHatchDeck;
    
    /**
     * 
     */
    public ArrayList<HatchCard> getHatchDeck() {
		return hatchDeck;
	}

	public ArrayList<HatchCard> getRejectedHatchDeck() {
		return rejectedHatchDeck;
	}

	/**
     * @return
     */
    public void shuffle() {
        // TODO implement here
    	Collections.shuffle(this.getHatchDeck());
    }

	/**
     * @return
     */
    public Card draw() {
        // TODO implement here
    	Card extracted = this.getHatchDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
    }

    /**
     * @param Card card 
     * @return
     */
    public void eliminateCard(Card card) {
        // TODO implement here
    	this.getHatchDeck().remove(card);
    	this.getRejectedHatchDeck().add((HatchCard) card);
    }
}