package it.polimi.ingsw.cg_38.model.deck;
import java.util.*;

/** identifica un deck di carte scialuppa */
public class HatchDeck extends Deck {

	/** lista delle carte del mazzo */
    private List<HatchCard> gameHatchDeck;

    /** lista delle carte già pescate */
    private List<HatchCard> rejectedHatchDeck;
    
	/** il costruttore popola con le carte scialuppa il deck che successivamente viene mischiato */
    public HatchDeck() {
    	this.setHatchDeck(new ArrayList<HatchCard>());
    	this.setRejectedHatchDeck(new ArrayList<HatchCard>());
    	for(int i = 0; i<3; i++) {
    		this.getHatchDeck().add(new HatchCard(HatchCardType.RED));
    		this.getHatchDeck().add(new HatchCard(HatchCardType.GREEN));
    	}
    	this.shuffle();
    }

    public void setRejectedHatchDeck(List<HatchCard> rejectedHatchDeck) {
		this.rejectedHatchDeck = rejectedHatchDeck;
	}

	public void setHatchDeck(List<HatchCard> hatchDeck) {
		this.gameHatchDeck = hatchDeck;
	}

    
    public List<HatchCard> getHatchDeck() {
		return gameHatchDeck;
	}

	public List<HatchCard> getRejectedHatchDeck() {
		return rejectedHatchDeck;
	}

	@Override
    public void shuffle() {
    	Collections.shuffle(this.getHatchDeck());
    }
    
	/** Pesca la prima carta del mazzo che successivamente viene rimescolato. Se il deck dal quale 
	 * pescare è vuoto viene ripopolato dalle carte contenute nel rejected deck 
	 * @return la carta pescata*/
	@Override
    public Card draw() {
    	if(this.getHatchDeck().isEmpty()) {
    		this.setHatchDeck(rejectedHatchDeck);
    		this.setRejectedHatchDeck(new ArrayList<HatchCard>());
    	}
    	HatchCard card = null;
    	for(HatchCard c:this.getHatchDeck()) {
    		if(c.getColor().equals(HatchCardType.GREEN)) {
    			card = c;
    		}
    	}
    	return card;
    	/*
    	Card extracted = this.getHatchDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
        */
    }

	/** elimina una carta dal mazzo che viene aggiunta al rejected deck 
	 * @paramcard indica la carta da eliminare*/
	@Override
    public void eliminateCard(Card card) {
    	this.getHatchDeck().remove(card);
    	this.getRejectedHatchDeck().add((HatchCard) card);
    }
}
