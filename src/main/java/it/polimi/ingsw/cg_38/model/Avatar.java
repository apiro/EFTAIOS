package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public abstract class Avatar {

    /**
     * 
     */
    private ArrayList<ObjectCard> myCards = new ArrayList<ObjectCard>();

    /**
     * 
     */
    private Sector currentSector;

    /**
     * 
     */
    private Name name;

    /**
     * 
     */
    private Boolean isPowered = false;

    /**
     * 
     */
    private LifeState isAlive = LifeState.ALIVE;

    /**
     * 
     */
    private EndState isWinner = EndState.PLAYING;

    /**
     * 
     */
    private ArrayList<Movement> myMovements = new ArrayList<Movement>();

	/**
     * @param Name name
     */
    public Avatar() {
        // TODO implement here
    }

    /**
     * @param Deck deck 
     * @return
     */
    public Card draw(Deck deck) {
        // TODO implement here
    	Card drown = deck.draw();
        return drown;
    }

    /**
     * @param Sector sector 
     * @return
     */
    public String move(Sector sector, int number) {
        // TODO implement here
    	this.setCurrentSector(sector);
    	this.getMyMovements().add(new Movement(this.getCurrentSector(), number));
        return sector.getName();
    }

    public void setCurrentSector(Sector currentSector) {
		this.currentSector = currentSector;
	}

	/**
     * @param Card card 
     * @return
     */
    public Card eliminateFromMyCards(Card card) {
        // TODO implement here
    	this.getMyCards().remove(card);
        return card;
    }
    
    /**
     * @param Card card 
     * @return
     */
    public Boolean addCard(Card card) {
        // TODO implement here
    	if(this.getMyCards().size()<3) {
    		this.getMyCards().add((ObjectCard)card);
    		return true;
    	} else {
    		return false;
    	}
    }

    /**
     * @param Sector sector 
     * @return
     */
    public abstract Boolean canMove(Sector sector);

    /**
     * @param Sector sector 
     * @return
     */
    public void attack(Sector sector) {
        // TODO implement here
    	
    }
    
    public void attacked() {
    	this.setIsAlive(LifeState.DEAD);
    	this.setIsWinner(EndState.LOOSER);
    }
    
    /**
     * getter e setter
     * **/

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Boolean getIsPowered() {
		return isPowered;
	}

	public void setIsPowered(Boolean isPowered) {
		this.isPowered = isPowered;
	}

	public LifeState getIsAlive() {
		return isAlive;
	}

	public void setIsAlive(LifeState isAlive) {
		this.isAlive = isAlive;
	}

	public EndState getIsWinner() {
		return isWinner;
	}

	public void setIsWinner(EndState isWinner) {
		this.isWinner = isWinner;
	}

	public ArrayList<ObjectCard> getMyCards() {
		return myCards;
	}

	public Sector getCurrentSector() {
		return currentSector;
	}

	public ArrayList<Movement> getMyMovements() {
		return myMovements;
	}
}