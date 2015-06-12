package it.polimi.ingsw.cg_38.model;

/**
 * 
 */
public abstract class Deck {

	/**
     *
     */
	public abstract void shuffle();

    /**
     * costructor
     */
    public abstract Card draw();

    /**
     * @param Card card 
     */
    public abstract void eliminateCard(Card card);
    
    /*
    public void printDeck(ArrayList<Card> toPrint) {
    	for (Card card: toPrint) {
			System.out.println(card.toString());
		}
    };
    */

}