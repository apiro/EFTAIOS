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
    	this.setObjectDeck(new ArrayList<ObjectCard>());
    	this.setRejectedObjectDeck(new ArrayList<ObjectCard>());
    	for(int i = 0; i < 2; i++) {
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.Adrenaline));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.Teleport));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.Attack));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.SpotLight));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.Sedatives));
    	}
    	this.getObjectDeck().add(new ObjectCard(ObjectCardType.Sedatives));
    	this.getObjectDeck().add(new ObjectCard(ObjectCardType.Defense));
    }


    public void printDeck() {
		for (Card card: this.getObjectDeck()) {
			System.out.println(card.toString());
		}
	}
    
    
    public void setRejectedObjectDeck(ArrayList<ObjectCard> rejectedObjectDeck) {
		this.rejectedObjectDeck = rejectedObjectDeck;
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
    public void shuffle() {
        // TODO implement here
    	Collections.shuffle(this.getObjectDeck());
    }

    /**
     * @return
     */
    public Card draw() {
        // TODO implement here
    	Card extracted = this.getObjectDeck().get(0);
    	this.getObjectDeck().remove(extracted);
    	this.getRejectedObjectDeck().add((ObjectCard)extracted);
    	/*this.shuffle();*/
        return extracted;
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