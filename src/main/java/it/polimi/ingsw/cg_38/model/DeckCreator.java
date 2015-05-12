package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class DeckCreator {

    /**
     * 
     */
    public DeckCreator() {
    }

    /**
     * @param String nameDeck
     */
    public static Deck createDeck(String nameDeck) {
        // TODO implement here
    	Deck deck;
    	if(nameDeck == "SectorDeck") {
    		deck = new SectorDeck();
    	} else if(nameDeck == "HatchDeck") {
    		deck = new HatchDeck();
    	} else if(nameDeck == "ObjectDeck") {
    		deck = new ObjectDeck();
    	} else {
    		deck = null;
    	}
		return deck;
    }
}