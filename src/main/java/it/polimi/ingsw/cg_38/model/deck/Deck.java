package it.polimi.ingsw.cg_38.model.deck;

public abstract class Deck {

	public abstract void shuffle();

    public abstract Card draw();

    public abstract void eliminateCard(Card card);
    
}