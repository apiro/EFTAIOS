package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 * 10 rosse -> 4 icona
 * 10 verdi -> 4 icona
 * 5 bianche -> 0 icona
 * 
 */
public class SectorDeck extends Deck {

    /**
     * 
     */
    public SectorDeck() {
    	this.setSectorDeck(new ArrayList<SectorCard>());
    	this.setRejectedSectorDeck(new ArrayList<SectorCard>());
    	for(int i = 0; i < 5; i++) {
    		if(i<2){
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RandomSectorNoise, true ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RandomSectorNoise, true ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MySectorNoise, true ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MySectorNoise, true ));
    		}
    		else {
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RandomSectorNoise, false ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RandomSectorNoise, false ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MySectorNoise, false ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MySectorNoise, false ));
    		}
    		this.getSectorDeck().add(new SectorCard(SectorCardType.Silence, false ));
    	}
    }

    public void printDeck() {
		for (Card card: this.getSectorDeck()) {
			System.out.println(card.toString());
		}
	}
    
    public void setRejectedSectorDeck(ArrayList<SectorCard> rejectedSectorDeck) {
		this.rejectedSectorDeck = rejectedSectorDeck;
	}

	/**
     * 
     */
    private ArrayList<SectorCard> sectorDeck;

    /**
     * 
     */
    private ArrayList<SectorCard> rejectedSectorDeck;

    /**
     * @return
     */
    public void shuffle() {
        // TODO implement here
    	Collections.shuffle(this.getSectorDeck());
    }

    public ArrayList<SectorCard> getSectorDeck() {
		return sectorDeck;
	}

	public void setSectorDeck(ArrayList<SectorCard> sectorDeck) {
		this.sectorDeck = sectorDeck;
	}

	public ArrayList<SectorCard> getRejectedSectorDeck() {
		return rejectedSectorDeck;
	}

	/**
     * @return
     */
    public Card draw() {
        // TODO implement here
    	Card extracted = this.getSectorDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
    }

    /**
     * @param Card card 
     * @return
     */
    public void eliminateCard(Card card) {
        // TODO implement here
    	this.getSectorDeck().remove(card);
    	this.getRejectedSectorDeck().add((SectorCard) card);
    }

}