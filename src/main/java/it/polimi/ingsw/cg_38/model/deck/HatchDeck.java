package it.polimi.ingsw.cg_38.model.deck;
import java.util.*;

/**
 * 
 */
public class HatchDeck extends Deck {

    public HatchDeck() {
    	this.setHatchDeck(new ArrayList<HatchCard>());
    	this.setRejectedHatchDeck(new ArrayList<HatchCard>());
    	for(int i = 0; i<3; i++) {
    		this.getHatchDeck().add(new HatchCard(HatchCardType.RED));
    		this.getHatchDeck().add(new HatchCard(HatchCardType.GREEN));
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

	@Override
    public void shuffle() {
    	Collections.shuffle(this.getHatchDeck());
    }
    
	@Override
    public Card draw() {
    	if(this.getHatchDeck().size() == 0) {
    		this.setHatchDeck(rejectedHatchDeck);
    		this.setRejectedHatchDeck(new ArrayList<HatchCard>());
    	}
    	HatchCard card = null;
    	for(HatchCard c:this.getHatchDeck()) {
    		if(c.getColor().equals(HatchCardType.GREEN)) {
    			card = c;
    		}
    	}
    	return card;
    	/*
    	Card extracted = this.getHatchDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
        */
    }

	@Override
    public void eliminateCard(Card card) {
    	this.getHatchDeck().remove(card);
    	this.getRejectedHatchDeck().add((HatchCard) card);
    }
}
