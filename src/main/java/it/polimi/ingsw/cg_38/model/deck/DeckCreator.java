package it.polimi.ingsw.cg_38.model.deck;

public class DeckCreator {

	/** create deck based to the required type */
    public static Deck createDeck(String nameDeck) {
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
}