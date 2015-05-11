package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class Turn {

    /**
     * 
     */
    public Turn() {
    }

    /**
     * 
     */
    private Player currentPlayer;

    /**
     * 
     */
    private Boolean hasMoved;

    /**
     * 
     */
    private Boolean hasUsedObjectCard;

    /**
     * 
     */
    private Boolean hasAttacked;

    /**
     * 
     */
    private Boolean hasDraw;




    /**
     * @param Boolean flag 
     * @return
     */
    public void setHasMoved(Boolean flag) {
        // TODO implement here
    }

    /**
     * @param Boolean flag 
     * @return
     */
    public void setHasUsedObjectCard(Boolean flag) {
        // TODO implement here
    }

    /**
     * @param Boolean flag 
     * @return
     */
    public void setHasAttacked(Boolean flag) {
        // TODO implement here
    }

    /**
     * @param Boolean flag 
     * @return
     */
    public void setHasDraw(Boolean flag) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Boolean getHasMoved() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Boolean getHasUsedObjectCard() {
        // TODO implement here
        return null;
    }

    public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
     * @return
     */
    public Boolean getHasAttacked() {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public Boolean getHasDraw() {
        // TODO implement here
        return null;
    }

}