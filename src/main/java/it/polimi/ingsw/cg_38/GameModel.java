package it.polimi.ingsw.cg_38;
import java.util.*;

/**
 * 
 */
public class GameModel {

    /**
     * 
     */
    public GameModel() {
    }

    /**
     * 
     */
    private ArrayList<Player> gamePlayers;

    /**
     * 
     */
    private Map gameMap;

    /**
     * 
     */
    private SectorDeck deckSector;

    /**
     * 
     */
    private HatchDeck deckHatch;

    /**
     * 
     */
    private ObjectDeck deckObject;

    /**
     * 
     */
    private Turn actualTurn;

    /**
     * @return
     */
    public Player getNextPlayer() {
        // TODO implement here
        return null;
    }

    /**
     * @param Player currentPlayer 
     * @return
     */
    public void setTurn(Player currentPlayer) {
        // TODO implement here
    }

    /**
     * @return
     */
    public Turn getTurn() {
        // TODO implement here
        return null;
    }

}