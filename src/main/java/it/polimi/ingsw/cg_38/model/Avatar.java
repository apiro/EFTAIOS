package it.polimi.ingsw.cg_38.model;

import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.Deck;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.io.Serializable;
import java.util.*;

/** rappresenta un generico avatar */
public abstract class Avatar implements Serializable {

	private static final long serialVersionUID = 1L;

	/** contiene tutte le carte oggetto possedute dal avatar */
	private List<ObjectCard> myCards = new ArrayList<ObjectCard>();

	/** indica il settore sul quale si trova l'avatar */
	private Sector currentSector;

	private Name name;

	/** indica se l'avatar è potenziato */
	private Boolean isPowered = false;

	/** contiene lo stato di vita dell' avatar */
	private LifeState isAlive = LifeState.ALIVE;

	/** contiene lo stato di fine dell' avatar */
	private EndState isWinner = EndState.PLAYING;

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


	/** contiene tutti i movimenti già effettuati dall'avatar */
	private List<Movement> myMovements = new ArrayList<Movement>();

	public Avatar() {
	}

	/**verifica se il giocatore possiede la carta oggetto difesa
	 * @return ritorna true se il giocatore possiede una carta difesa */
	public Boolean hasDefenseCard() {

		for (int i = 0; i < myCards.size(); i++) {
			if ((myCards.get(i).getType()).equals(ObjectCardType.DEFENSE)) {
				myCards.remove(i);
				return true;
			}
		}
		return false;
	}

	/** pesca una carta dal mazzo 
	 * @param deck indica il deck dal quale pescare 
	 * @return ritorna la carta pescata */
	public Card draw(Deck deck) {
		Card drown = deck.draw();
		return drown;
	}

	/** muove l'avatar in un dato settore ed incrementa il numero di turni giocati
	 * @param sector indica il settore sul quale si vuole effettuare il movimento
	 * @param number indica il numero turni già giocatori dal giocatore
	 * @return ritorna il nome del settore sul quale l'avatar è stato mosso */
	public String move(Sector sector, int number) {
		this.setCurrentSector(sector);
		this.getMyMovements()
				.add(new Movement(this.getCurrentSector(), number));
		return sector.getName();
	}

	public void setCurrentSector(Sector currentSector) {
		this.currentSector = currentSector;
	}

	/** elimina una carta dalla lista di carte oggetto possedute 
	 * @param card carta da eliminare
	 * @return ritorna la carta eiliminata*/
	public Card eliminateFromMyCards(Card card) {
		this.getMyCards().remove(card);
		return card;
	}

	/** aggiunge una carta alla lista di carte oggetto dell'avatar
	 * @param card carta da aggiungere
	 * @return ritorna true se la carta è stata aggiunta*/
	public Boolean addCard(Card card) {
		/* if(this.getMyCards().size()<3) { */
		this.getMyCards().add((ObjectCard) card);
		return true;
		/*
		 * return true; } else { return false; }
		 */
	}

	/** verifica se l'avatar può muoversi sul dato settore
	 * @param settore sul quale il giocatore vuole spostarsi
	 * @return ritorna true se è possibile effettuare il movimento*/
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
