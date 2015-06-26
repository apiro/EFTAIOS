package it.polimi.ingsw.cg_38.model;

public class Turn {

    public Turn(Player currentPlayer) {
    	this.setCurrentPlayer(currentPlayer);
    }

    private Player currentPlayer;

    private Boolean hasMoved = false;

    private Boolean hasUsedObjectCard = false;

    private Boolean hasAttacked = false;

    private Boolean hasDraw = false;
    
    private Boolean yetValid = true;

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

	public Boolean getYetValid() {
		return yetValid;
	}

	public void setYetValid(Boolean yetValid) {
		this.yetValid = yetValid;
	}


}