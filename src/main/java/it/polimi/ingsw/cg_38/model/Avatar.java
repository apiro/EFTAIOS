package it.polimi.ingsw.cg_38.model;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public abstract class Avatar implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 
     */
    private ArrayList<ObjectCard> myCards = new ArrayList<ObjectCard>();

    /**
     * 
     */
    private Sector currentSector;

    /*
    @Override
	public String toString() {
    	String code = "name=" + name + "currentSector=" + currentSector
				+ ", isPowered=" + isPowered + ", isAlive=" + isAlive
				+ ", isWinner=" + isWinner + "\n \t ->MyMovements= ";
    	
    	for(Movement mv:this.getMyMovements()) {
    		code = code + (mv.getTurnNumber() + " " + mv.getTargetsector() + " ");
    	}
    	
    	code = code + "\n \t ->MyCards= ";
    	
    	for(Card cd:this.getMyCards()) {
    		code = code + (cd.toString() + " ");
    	}
    	
		return code;
	}*/

    
    
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
    }

    public Boolean hasDefenseCard(){
    	
    	for(int i = 0; i<myCards.size(); i++){
    		if((myCards.get(i).getType()).equals(ObjectCardType.Defense)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    /**
     * @param Deck deck 
     * @return
     */
    public Card draw(Deck deck) {
    	Card drown = deck.draw();
        return drown;
    }

    /**
     * @param Sector sector 
     * @return
     */
    public String move(Sector sector, int number) {
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
    	this.getMyCards().remove(card);
        return card;
    }
    
    /**
     * @param Card card 
     * @return
     */
    public Boolean addCard(Card card) {
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
    
    /**
     * return true se l'attacco va a buon fine false se non va a buon fine
     * */
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
