package it.polimi.ingsw.cg_38.model.deck;

/** classe utilizzata per la creazione di un deck specifico */
public class DeckCreator {

	/** crea un mazzo in base al nome ricevuto come parametro 
	 * @param nameDeck indica il nome del deck da creare
	 * @return deck creato*/
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