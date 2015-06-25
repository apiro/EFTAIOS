package it.polimi.ingsw.cg_38.model;

import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.Deck;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;
import java.util.*;

public abstract class Avatar implements Serializable {

	private static final long serialVersionUID = 1L;

	/** contiene tutte le carte oggetto possedute dal avatar */
	private List<ObjectCard> myCards = new ArrayList<ObjectCard>();

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

	/** contiene tutti i movimenti già effettuati dall'avatar */
	private List<Movement> myMovements = new ArrayList<Movement>();

	public Avatar() {
	}

	/**verifica se il giocatore possiede la carta oggetto difesa */
	public Boolean hasDefenseCard() {

		for (int i = 0; i < myCards.size(); i++) {
			if ((myCards.get(i).getType()).equals(ObjectCardType.DEFENSE)) {
				myCards.remove(i);
				return true;
			}
		}
		return false;
	}

	/** pesca una carta dal mazzo */
	public Card draw(Deck deck) {
		Card drown = deck.draw();
		return drown;
	}

	/** muove l'avatar nel settore passato come parametro ed incrementa il numero di turni giocati */
	public String move(Sector sector, int number) {
		this.setCurrentSector(sector);
		this.getMyMovements()
				.add(new Movement(this.getCurrentSector(), number));
		return sector.getName();
	}

	public void setCurrentSector(Sector currentSector) {
		this.currentSector = currentSector;
	}

	/** elimina una carta dalla lista di carte oggetto possedute */
	public Card eliminateFromMyCards(Card card) {
		this.getMyCards().remove(card);
		return card;
	}

	/** aggiunge una carta alla lista di carte oggetto controllando che non ne possegga già 3 */
	public Boolean addCard(Card card) {
		/* if(this.getMyCards().size()<3) { */
		this.getMyCards().add((ObjectCard) card);
		return true;
		/*
		 * return true; } else { return false; }
		 */
	}

	/** verifica se l'avatar può muoversi nel settore passato come parametro */
	public abstract Boolean canMove(Sector sector);

	/** cambia lo stato dell'avatar quando esso viene attaccato */
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

	public List<ObjectCard> getMyCards() {
		return myCards;
	}

	public Sector getCurrentSector() {
		return currentSector;
	}

	public List<Movement> getMyMovements() {
		return myMovements;
	}
}
