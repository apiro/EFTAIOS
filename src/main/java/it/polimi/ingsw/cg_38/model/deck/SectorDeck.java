package it.polimi.ingsw.cg_38.model.deck;
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
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RANDOMSECTORNOISE, true ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RANDOMSECTORNOISE, true ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MYSECTORNOISE, true ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MYSECTORNOISE, true ));
    		}
    		else {
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RANDOMSECTORNOISE, false ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.RANDOMSECTORNOISE, false ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MYSECTORNOISE, false ));
    			this.getSectorDeck().add(new SectorCard(SectorCardType.MYSECTORNOISE, false ));
    		}
    		this.getSectorDeck().add(new SectorCard(SectorCardType.SILENCE, false ));
    	}
    	this.shuffle();
    }
    
    public void setRejectedSectorDeck(ArrayList<SectorCard> rejectedSectorDeck) {
		this.rejectedSectorDeck = rejectedSectorDeck;
	}

	/**
     * 
     */
    private ArrayList<SectorCard> gameSectorDeck;

    /**
     * 
     */
    private ArrayList<SectorCard> rejectedSectorDeck;

    @Override
    public void shuffle() {
        // TODO implement here
    	Collections.shuffle(this.getSectorDeck());
    }

    public ArrayList<SectorCard> getSectorDeck() {
		return gameSectorDeck;
	}

	public void setSectorDeck(ArrayList<SectorCard> sectorDeck) {
		this.gameSectorDeck = sectorDeck;
	}

	public ArrayList<SectorCard> getRejectedSectorDeck() {
		return rejectedSectorDeck;
	}

	@Override
    public Card draw() {
    	if(this.getSectorDeck().size() == 0) {
    		this.setSectorDeck(rejectedSectorDeck);
    		this.setRejectedSectorDeck(new ArrayList<SectorCard>());
    	}
    	Card extracted = this.getSectorDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
        
    }

    @Override
    public void eliminateCard(Card card) {
        // TODO implement here
    	this.getSectorDeck().remove(card);
    	this.getRejectedSectorDeck().add((SectorCard) card);
    }

}
