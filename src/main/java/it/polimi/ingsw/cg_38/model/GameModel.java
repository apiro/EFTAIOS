package it.polimi.ingsw.cg_38.model;
import java.util.*;

/**
 * 
 */
public class GameModel {

    /**
     * 
     */
    public GameModel(String map) {
    	this.init(map);
    }

    /**
     * 
     */
    public void init(String map){
    	this.setGameMap(MapCreator.createMap(map));
    	this.setDeckObject(DeckCreator.createDeck("ObjectDeck"));
    	this.setDeckHatch(DeckCreator.createDeck("HatchDeck"));
    	this.setDeckSector(DeckCreator.createDeck("SectorDeck"));
    	this.setGamePlayers(new ArrayList<Player>());
    }
    
    public ArrayList<Player> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(ArrayList<Player> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	public Deck getDeckSector() {
		return deckSector;
	}

	public void setDeckSector(Deck deckSector) {
		this.deckSector = deckSector;
	}

	public Deck getDeckHatch() {
		return deckHatch;
	}

	public void setDeckHatch(Deck deckHatch) {
		this.deckHatch = deckHatch;
	}

	public Deck getDeckObject() {
		return deckObject;
	}

	public void setDeckObject(Deck deckObject) {
		this.deckObject = deckObject;
	}

	public Map getGameMap() {
		return gameMap;
	}

	public void setGameMap(Map gameMap) {
		this.gameMap = gameMap;
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
    private Deck deckSector;

    /**
     * 
     */
    private Deck deckHatch;

    /**
     * 
     */
    private Deck deckObject;

    /**
     * 
     */
    private Turn actualTurn = null;

    /**
     * @return
     */
    public Player getNextPlayer() {
        // TODO implement here
    	int indexOfCurrent = 0;
    	for(Player current: this.getGamePlayers()){
	    	 if(current.equals(actualTurn.getCurrentPlayer())) {
	    		  
	    	 }
    	}
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