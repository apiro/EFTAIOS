package it.polimi.ingsw.cg_38.model.deck;
import java.util.*;

/** identifica un mazzo di carte oggetto */
public class ObjectDeck extends Deck {

	/** lista delle carte del mazzo */
    private List<ObjectCard> gameObjectDeck;

    /** lista delle carte già pescate */
    private List<ObjectCard> rejectedObjectDeck;
    
	/** il costruttore popola con le carte oggetto il deck che successivamente viene mischiato*/
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


    @Override
    public void shuffle() {
    	Collections.shuffle(this.getObjectDeck());
    }
    
	/** Pesca la prima carta del mazzo che successivamente viene rimescolato. Inoltre se il deck dal quale 
	 * pescare è vuoto viene ripopolato dalle carte contenute nel rejected deck 
	 * @return la carta pescata*/
    @Override
    public Card draw() {
    	if(this.getObjectDeck().isEmpty()) {
    		this.setObjectDeck(rejectedObjectDeck);
    		this.setRejectedObjectDeck(new ArrayList<ObjectCard>());
    	}
    	Card extracted = this.getObjectDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
    }
    
	/** elimina una carta dal mazzo che viene aggiunta al rejected deck 
	 * @param card carta da eliminare */
    @Override
    public void eliminateCard(Card card) {
    	this.getObjectDeck().remove(card);
    	this.getRejectedObjectDeck().add((ObjectCard) card);
    }

    public List<ObjectCard> getObjectDeck() {
		return gameObjectDeck;
	}

	public void setObjectDeck(List<ObjectCard> objectDeck) {
		this.gameObjectDeck = objectDeck;
	}

	public List<ObjectCard> getRejectedObjectDeck() {
		return rejectedObjectDeck;
	}
}
