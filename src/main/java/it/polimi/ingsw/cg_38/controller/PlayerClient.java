package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

public interface PlayerClient {
	
	public void init();
	
	public void run();
	
	public void process(Event msg);
	
	public void startSender();
	
	public Logger getLogger();
	
	public Player getPlayer();
	
	public void setPlayer(Player player);
	
	public PlayerClientState getPlayerClientState();
	
	public void setPlayerClientState(PlayerClientState playerClientState);
	
	public void closeClient();
	
	public void setMap(Map map);
	
	public void setIsInterfaceBlocked(Boolean isInterfaceBlocked);
	
	public Sector askForMoveCoordinates();
	
	public Map getMap();
	
	public void updateCards();
	
	public void setIsMyTurn(Boolean b);
	
	public void updateMovements();
	
	public void setClientAlive(Boolean b);
}
