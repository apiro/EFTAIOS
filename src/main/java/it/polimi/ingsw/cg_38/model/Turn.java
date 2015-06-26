package it.polimi.ingsw.cg_38.model;

/** rappresenta un turno del gioco */
public class Turn {

    public Turn(Player currentPlayer) {
    	this.setCurrentPlayer(currentPlayer);
    }

    /** contiene il giocatore che sta effettuando il turno */
    private Player currentPlayer;

    /** indica se il giocatore ha già mosso durante il turno */
    private Boolean hasMoved = false;
    
    /** indica se il giocatore ha già utilizzato una carta oggetto durante il turno */
    private Boolean hasUsedObjectCard = false;
    
    /** indica se il giocatore ha già attaccato durante il turno */
    private Boolean hasAttacked = false;

    /** indica se il giocatore ha già pescato durante il turno */
    private Boolean hasDraw = false;

    public Boolean getHasMoved() {
		return hasMoved;
	}

	public void setHasMoved(Boolean hasMoved) {
		this.hasMoved = hasMoved;
	}

	public Boolean getHasUsedObjectCard() {
		return hasUsedObjectCard;
	}

	public void setHasUsedObjectCard(Boolean hasUsedObjectCard) {
		this.hasUsedObjectCard = hasUsedObjectCard;
	}

	public void setHasAttacked(Boolean hasAttacked) {
		this.hasAttacked = hasAttacked;
	}

	public void setHasDraw(Boolean hasDraw) {
		this.hasDraw = hasDraw;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Boolean getHasAttacked() {
		return hasAttacked;
	}

	public Boolean getHasDraw() {
		return hasDraw;
	}
}
