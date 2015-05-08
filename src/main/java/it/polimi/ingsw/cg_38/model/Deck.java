package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public interface Deck {

	public void shuffle();

    /**
     * @return
     */
    public Card draw();

    /**
     * @param Card card 
     * @return
     */
    public Card eliminateCard(Card card);

    /**
     * @param Card card 
     * @return
     */
    public void addCard(Card card);

    /**
     * @param Card[] toAdd 
     * @return
     */
    public void setSectorDeck(Card[] toAdd);
	
}