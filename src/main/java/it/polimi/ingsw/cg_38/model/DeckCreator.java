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
    	Deck deck = null;
    	if(nameDeck == "SectorDeck") {
    		deck = new SectorDeck();
    	} else if(nameDeck == "HatchDeck") {
    		deck = new HatchDeck();
    	} else if(nameDeck == "ObjectDeck") {
    		deck = new ObjectDeck();
    	}
		return deck;
    }
    
    public static void main( String[] args )
    {
    	Deck gameDeck = DeckCreator.createDeck("HatchDeck");
    	((HatchDeck)gameDeck).printDeck();
    	System.out.println("\n");
    	System.out.println(gameDeck.draw());
    	gameDeck.shuffle();
    	System.out.println("\n");
    	((HatchDeck)gameDeck).printDeck();
    	System.out.println("\n");
    	System.out.println(gameDeck.draw());
    	gameDeck.shuffle();
    }

}