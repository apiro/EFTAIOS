package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class ObjectDeck extends Deck {

    /**
     * 
     */
    public ObjectDeck() {
    }

    /**
     * 
     */
    private ArrayList<ObjectCard> objectDeck;

    /**
     * 
     */
    private ArrayList<ObjectCard> rejectedObjectDeck;

    /**
     * 
     */
    public void ObjectDeck() {
        // TODO implement here
    }

    /**
     *
     */
    public void shuffle() {
        // TODO implement here
    	Collections.shuffle(this.getObjectDeck());
    }

    /**
     * @return
     */
    public Card draw() {
        // TODO implement here
    	Card estracted = this.getObjectDeck().get(0);
    	this.getObjectDeck().remove(estracted);
    	this.shuffle();
        return estracted;
    }

    /**
     * @param Card card 
     */
    public void eliminateCard(Card card) {
    	this.getObjectDeck().remove(card);
    	this.getRejectedObjectDeck().add((ObjectCard) card);
    }

    public ArrayList<ObjectCard> getObjectDeck() {
		return objectDeck;
	}

	public void setObjectDeck(ArrayList<ObjectCard> objectDeck) {
		this.objectDeck = objectDeck;
	}

	public ArrayList<ObjectCard> getRejectedObjectDeck() {
		return rejectedObjectDeck;
	}
	

}