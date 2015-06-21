package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class HatchDeck extends Deck {

    public HatchDeck() {
    	this.setHatchDeck(new ArrayList<HatchCard>());
    	this.setRejectedHatchDeck(new ArrayList<HatchCard>());
    	for(int i = 0; i<3; i++) {
    		this.getHatchDeck().add(new HatchCard(HatchCardType.Red));
    		this.getHatchDeck().add(new HatchCard(HatchCardType.Green));
    	}
    	this.shuffle();
    }

    public void setRejectedHatchDeck(ArrayList<HatchCard> rejectedHatchDeck) {
		this.rejectedHatchDeck = rejectedHatchDeck;
	}

	public void setHatchDeck(ArrayList<HatchCard> hatchDeck) {
		this.hatchDeck = hatchDeck;
	}

    private ArrayList<HatchCard> hatchDeck;

    private ArrayList<HatchCard> rejectedHatchDeck;
    
    public ArrayList<HatchCard> getHatchDeck() {
		return hatchDeck;
	}

	public ArrayList<HatchCard> getRejectedHatchDeck() {
		return rejectedHatchDeck;
	}

    public void shuffle() {
    	Collections.shuffle(this.getHatchDeck());
    }
    
    public Card draw() {
    	if(this.getHatchDeck().size() == 0) {
    		this.setHatchDeck(rejectedHatchDeck);
    	}
    	Card extracted = this.getHatchDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
    }

    public void eliminateCard(Card card) {
    	this.getHatchDeck().remove(card);
    	this.getRejectedHatchDeck().add((HatchCard) card);
    }
}