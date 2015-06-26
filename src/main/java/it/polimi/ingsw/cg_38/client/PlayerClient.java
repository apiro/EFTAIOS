package it.polimi.ingsw.cg_38.client;

/**
 * Interfaccia dei metodi che il PlayerClient deve implementare
 * */
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Sector;

public interface PlayerClient {
	
	public void init();
	
	public void run();
	
	public void process(Event msg);
	
	public void startSender();
	
	public Logger getLogger();
	
	public Logger getLoggerChat();
	
	public Player getPlayer();
	
	public Boolean getIsMyTurn();
	
	public void setPlayer(Player player);
	
	public PlayerClientState getPlayerClientState();
	
	public void setPlayerClientState(PlayerClientState playerClientState);
	
	public void closeClient();
	
	public void paintHatch(Boolean bool, Sector sec);
	
	public void setMap(Map map);
	
	public void setIsInterfaceBlocked(Boolean isInterfaceBlocked);
	
	public Sector askForMoveCoordinates();
	
	public Map getMap();
	
	public void updateCards();
	
	public void setIsMyTurn(Boolean b);
	
	public void updateMovements();
	
	public void setClientAlive(Boolean b);
}
