package it.polimi.ingsw.cg_38.model;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.Deck;
import it.polimi.ingsw.cg_38.model.deck.DeckCreator;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchDeck;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectDeck;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorDeck;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.MapCreator;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.util.*;

import javax.xml.parsers.ParserConfigurationException;

public class GameModel {

    public GameModel(String map) throws ParserConfigurationException, Exception {
    	this.init(map);
    }

    /** inizializza tutte le variabili neccesarie per il gioco */
    public void init(String map) throws ParserConfigurationException, Exception{
    	this.setGameMap(MapCreator.createMap(map));
    	this.setDeckObject(DeckCreator.createDeck("ObjectDeck"));
    	this.setDeckHatch(DeckCreator.createDeck("HatchDeck"));
    	this.setDeckSector(DeckCreator.createDeck("SectorDeck"));
    	this.setGamePlayers(new ArrayList<Player>());
    }
    
    /** ritorna tutti i giocatori che si trovano sul settore passato come parametro */
    public List<Player> getDesiredPlayers(Sector sectorToSearch){
    	List<Player> selected = new ArrayList<Player>();
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
    
    public List<Player> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(List<Player> gamePlayers) {
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

    private List<Player> gamePlayers;

    private Map gameMap;

    private Deck deckSector;

    private Deck deckHatch;

    private Deck deckObject;

    private Turn actualTurn = null;

    /** ritorna il prossimo giocatore che dovrà effettura il suo turno. Ovviamente il giocatore deve essere
     * ancora vivo e non deve aver vinto o perso */   
    public Player getNextPlayer() {

    	for(int i = 0; i < this.getGamePlayers().size(); i++){
	    	 if(this.getGamePlayers().get(i).equals(this.getActualTurn().getCurrentPlayer())) {
	    		 for(int j = i+1; j != i; ){
	    		  if(j<=this.getGamePlayers().size()-1 ){
	    			  if((this.getGamePlayers().get(j).getAvatar().getIsAlive().equals(LifeState.ALIVE)) &&
	    			  (this.getGamePlayers().get(j).getAvatar().getIsWinner().equals(EndState.PLAYING)))
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

	/** controlla se ci sono ancora umani in gioco */
	public Boolean areThereOtherHumans() {
		for(Player pl:this.gamePlayers) {
			if(pl.getAvatar() instanceof Human &&
					(pl.getAvatar().getIsWinner().equals(EndState.PLAYING)) &&
					(pl.getAvatar().getIsAlive().equals(LifeState.ALIVE))) {
				return true;
			}
		}
		return false;
	}
	
	public void setActualTurn(Turn actualTurn) {
		this.actualTurn = actualTurn;
	}

	/** aggiunge la carta passata come parametro al corrispettivo rejected deck */
	public void handleRejectedCard(Card eliminateFromMyCards) {
		if(eliminateFromMyCards instanceof ObjectCard) {
			((ObjectDeck)this.getDeckObject()).getRejectedObjectDeck().add((ObjectCard) eliminateFromMyCards);
		} else if(eliminateFromMyCards instanceof SectorCard) {
			((SectorDeck)this.getDeckSector()).getRejectedSectorDeck().add((SectorCard) eliminateFromMyCards);
		} else if(eliminateFromMyCards instanceof HatchCard) {
			((HatchDeck)this.getDeckHatch()).getRejectedHatchDeck().add((HatchCard) eliminateFromMyCards);
		}
	}

    
}
