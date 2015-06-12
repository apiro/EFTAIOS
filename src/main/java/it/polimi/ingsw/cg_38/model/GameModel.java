package it.polimi.ingsw.cg_38.model;
import it.polimi.ingsw.cg_38.controller.GameState;

import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 
 */
public class GameModel {

    /**
     * @throws Exception 
     * @throws ParserConfigurationException 
     * 
     */
    public GameModel(String map) throws ParserConfigurationException, Exception {
    	this.init(map);
    }

    /**
     * @throws Exception 
     * @throws ParserConfigurationException 
     * 
     */
    public void init(String map) throws ParserConfigurationException, Exception{
    	this.setGameMap(MapCreator.createMap(map));
    	this.setDeckObject(DeckCreator.createDeck("ObjectDeck"));
    	this.setDeckHatch(DeckCreator.createDeck("HatchDeck"));
    	this.setDeckSector(DeckCreator.createDeck("SectorDeck"));
    	this.setGamePlayers(new ArrayList<Player>());
    }
    
    public ArrayList<Player> getDesiredPlayers(Sector sectorToSearch){
    	ArrayList<Player> selected = new ArrayList<Player>();
    	for(Player pl:this.getGamePlayers()) {
    		if(pl.getAvatar().getCurrentSector().equals(sectorToSearch)) {
    			selected.add(pl);
    		}
    	}
    	return selected;
    }
    
    public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	private GameState gameState = GameState.INIT;
    
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
    	for(int i = 0; i < this.getGamePlayers().size(); i++){
	    	 if(this.getGamePlayers().get(i).equals(this.getActualTurn().getCurrentPlayer())) {
	    		 for(int j = i+1; j != i; ){
	    		  if(j<=this.getGamePlayers().size()-1 ){
	    			  if(this.getGamePlayers().get(j).getAvatar().getIsAlive() == LifeState.ALIVE)
	    				  return this.getGamePlayers().get(j);
	    			  else
	    				  j++;
	    		  	} 
	    		  else j=0;
	    		 }
	    		 
	    	 } 
    	}
    	return null;
    }

	public Turn getActualTurn() {
		return actualTurn;
	}

	public void setActualTurn(Turn actualTurn) {
		this.actualTurn = actualTurn;
	}

    
}