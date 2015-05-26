package it.polimi.ingsw.cg_38.controller.action;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import it.polimi.ingsw.cg_38.controller.Communicator;
import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.RMICommunicator;
import it.polimi.ingsw.cg_38.controller.RMIRegistrationInterface;
import it.polimi.ingsw.cg_38.controller.RMIRemoteObjectDetails;
import it.polimi.ingsw.cg_38.controller.RMIRemoteObjectInterface;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;

import javax.xml.parsers.ParserConfigurationException;

public class SubscribeRMI extends Subscribe{
	
	private String RMI_ID;
	
	public SubscribeRMI(EventSubscribeRMI evt){
		super(evt);
		this.setRMI_ID(evt.getRMI_ID());
	}
	
	
	
	
	public String getRMI_ID() {
		return RMI_ID;
	}




	public void setRMI_ID(String rMI_ID) {
		RMI_ID = rMI_ID;
	}




	@Override
	public NotifyEvent perform(ServerController server) throws ParserConfigurationException, Exception {
		
		for(GameController gc:server.getTopics().values()) {
			if(this.isPossible(gc)) {
				Registry registry =  LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.getRMI_PORT());
				RMIRemoteObjectInterface clientView = (RMIRemoteObjectInterface) registry.lookup(this.getRMI_ID()); 
				Communicator c = new RMICommunicator(clientView);
				//aggiungere comunicator alla mappa dei comunicator nel game controller 
				gc.getGameModel().getGamePlayers().add(super.getPlayer());
				server.getTopics().put(super.getPlayer().getName(), gc);
				return new EventAddedToGame(super.getPlayer(), true);
			} else {
				return new EventAddedToGame(super.getPlayer(), false);
			}
		}
		
		GameController newGc = server.initAndStartANewGame(this.getTypeMap(), this.getTopic());
		server.addObserver(newGc);
		server.getTopics().put(super.getPlayer().getName(), newGc);
		return new EventAddedToGame(super.getPlayer(), true);
	}

	@Override
	public Boolean isPossible(GameController gc) {
		if(gc.getTopic().equals(this.getTopic()) && gc.getGameModel().getGamePlayers().size()<8 &&
				gc.getCanAcceptOtherPlayers()) {
			return true;
		}
		else return false;
	}


}
