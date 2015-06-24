package it.polimi.ingsw.cg_38.model.deck;
import java.util.*;

/**
 * 
 */
public class ObjectDeck extends Deck {

    public ObjectDeck() {
    	this.setObjectDeck(new ArrayList<ObjectCard>());
    	this.setRejectedObjectDeck(new ArrayList<ObjectCard>());
    	for(int i = 0; i < 2; i++) {
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.ADRENALINE));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.TELEPORT));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.ATTACKCARD));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.SPOTLIGHT));
    		this.getObjectDeck().add(new ObjectCard(ObjectCardType.SEDATIVES));
    	}
    	this.getObjectDeck().add(new ObjectCard(ObjectCardType.SEDATIVES));
    	this.getObjectDeck().add(new ObjectCard(ObjectCardType.DEFENSE));
    	this.shuffle();
    }


    /*
    public void printDeck() {
		for (Card card: this.getObjectDeck()) {
			System.out.println(card.toString());
		}
	}
	
	*/
    
    
    public void setRejectedObjectDeck(List<ObjectCard> rejectedObjectDeck) {
		this.rejectedObjectDeck = rejectedObjectDeck;
	}

    private List<ObjectCard> objectDeck;

    private List<ObjectCard> rejectedObjectDeck;

    @Override
    public void shuffle() {
    	Collections.shuffle(this.getObjectDeck());
    }
    
    @Override
    public Card draw() {
    	if(this.getObjectDeck().size() == 0) {
    		this.setObjectDeck(rejectedObjectDeck);
    		this.setRejectedObjectDeck(new ArrayList<ObjectCard>());
    	}
    	Card extracted = this.getObjectDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
    }
    
    @Override
    public void eliminateCard(Card card) {
    	this.getObjectDeck().remove(card);
    	this.getRejectedObjectDeck().add((ObjectCard) card);
    }

    public List<ObjectCard> getObjectDeck() {
		return objectDeck;
	}

	public void setObjectDeck(List<ObjectCard> objectDeck) {
		this.objectDeck = objectDeck;
	}

	public List<ObjectCard> getRejectedObjectDeck() {
		return rejectedObjectDeck;
	}
}
