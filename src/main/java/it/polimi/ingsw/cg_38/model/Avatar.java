package it.polimi.ingsw.cg_38.model;

import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.Deck;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;
import java.util.*;

/**
 * 
 */
public abstract class Avatar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** show all avatar's card */
	private ArrayList<ObjectCard> myCards = new ArrayList<ObjectCard>();

	private Sector currentSector;

	/*
	 * @Override public String toString() { String code = "name=" + name +
	 * "currentSector=" + currentSector + ", isPowered=" + isPowered +
	 * ", isAlive=" + isAlive + ", isWinner=" + isWinner +
	 * "\n \t ->MyMovements= ";
	 * 
	 * for(Movement mv:this.getMyMovements()) { code = code +
	 * (mv.getTurnNumber() + " " + mv.getTargetsector() + " "); }
	 * 
	 * code = code + "\n \t ->MyCards= ";
	 * 
	 * for(Card cd:this.getMyCards()) { code = code + (cd.toString() + " "); }
	 * 
	 * return code; }
	 */

	private Name name;

	private Boolean isPowered = false;

	private LifeState isAlive = LifeState.ALIVE;

	private EndState isWinner = EndState.PLAYING;

	/** show all avatar's movements */
	private ArrayList<Movement> myMovements = new ArrayList<Movement>();

	public Avatar() {
	}

	/** check if has defense card */
	public Boolean hasDefenseCard() {

		for (int i = 0; i < myCards.size(); i++) {
			if ((myCards.get(i).getType()).equals(ObjectCardType.DEFENSE)) {
				myCards.remove(i);
				return true;
			}
		}
		return false;
	}

	/** draw a card from deck */
	public Card draw(Deck deck) {
		Card drown = deck.draw();
		return drown;
	}

	/** move avatar in target sector and increases the number of turn */
	public String move(Sector sector, int number) {
		this.setCurrentSector(sector);
		this.getMyMovements()
				.add(new Movement(this.getCurrentSector(), number));
		return sector.getName();
	}

	public void setCurrentSector(Sector currentSector) {
		this.currentSector = currentSector;
	}

	/** delete a card from avatar's cards */
	public Card eliminateFromMyCards(Card card) {
		this.getMyCards().remove(card);
		return card;
	}

	/** add card to avatar's cards */
	public Boolean addCard(Card card) {
		/* if(this.getMyCards().size()<3) { */
		this.getMyCards().add((ObjectCard) card);
		return true;
		/*
		 * return true; } else { return false; }
		 */
	}

	/** check if avatar can move in target sector */
	public abstract Boolean canMove(Sector sector);

	/** change avatar's state when he was attacked */
	public void attacked() {
		this.setIsAlive(LifeState.DEAD);
		this.setIsWinner(EndState.LOOSER);
	}

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
