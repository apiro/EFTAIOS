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
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.AttackCard));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.SpotLight));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.Sedatives));
    	}
    	this.getObjectDeck().add(new ObjectCard(ObjectCardType.Sedatives));
    	this.getObjectDeck().add(new ObjectCard(ObjectCardType.Defense));
    	this.shuffle();
    }


    /*
    public void printDeck() {
		for (Card card: this.getObjectDeck()) {
			System.out.println(card.toString());
		}
	}
	
	*/
    
    
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
    	Collections.shuffle(this.getObjectDeck());
    }

    /**
     * @return
     */
    public Card draw() {
    	/*
    	Card card= null;
    	for(ObjectCard c:this.getObjectDeck()) {
    		if(c.getType().equals(ObjectCardType.AttackCard)) {
    			card = c;
    		}
    	}
    	return card;
    	*/
    	if(this.getObjectDeck().size() == 0) {
    		this.setObjectDeck(rejectedObjectDeck);
    	}
    	Card extracted = this.getObjectDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
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