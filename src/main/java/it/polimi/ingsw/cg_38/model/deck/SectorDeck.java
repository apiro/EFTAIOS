package it.polimi.ingsw.cg_38.model.deck;
import java.util.*;

/** identifica un deck di carte settore */
public class SectorDeck extends Deck {

	/** lista delle carte del mazzo */
    private List<SectorCard> gameSectorDeck;

    /** lista delle carte già pescate */
    private List<SectorCard> rejectedSectorDeck;
    
	/** il costrutto popola il deck con le carte settore */
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
    
    public void setRejectedSectorDeck(List<SectorCard> rejectedSectorDeck) {
		this.rejectedSectorDeck = rejectedSectorDeck;
	}


    @Override
    public void shuffle() {
    	Collections.shuffle(this.getSectorDeck());
    }

    public List<SectorCard> getSectorDeck() {
		return gameSectorDeck;
	}

	public void setSectorDeck(List<SectorCard> sectorDeck) {
		this.gameSectorDeck = sectorDeck;
	}

	public List<SectorCard> getRejectedSectorDeck() {
		return rejectedSectorDeck;
	}

	/** Pesca la prima carta del mazzo che successivamente viene rimescolato. Inoltre se il deck dal quale 
	 * pescare è vuoto viene ripopolato dalle carte contenute nel rejected deck 
	 * @return la carta pescata*/
	@Override
    public Card draw() {
    	if(this.getSectorDeck().isEmpty()) {
    		this.setSectorDeck(rejectedSectorDeck);
    		this.setRejectedSectorDeck(new ArrayList<SectorCard>());
    	}
    	Card extracted = this.getSectorDeck().get(0);
    	this.eliminateCard(extracted);
    	this.shuffle();
        return extracted;
        
    }

	/** elimina una carta dal mazzo che viene aggiunta al rejected deck 
	 * @param card indica la carta da eliminare */
    @Override
    public void eliminateCard(Card card) {
    	this.getSectorDeck().remove(card);
    	this.getRejectedSectorDeck().add((SectorCard) card);
    }

}
