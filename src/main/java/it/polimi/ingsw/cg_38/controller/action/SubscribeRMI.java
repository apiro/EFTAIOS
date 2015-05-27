package it.polimi.ingsw.cg_38.controller.action;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.Communicator;
import it.polimi.ingsw.cg_38.controller.RMICommunicator;
import it.polimi.ingsw.cg_38.controller.RMIRemoteObjectDetails;
import it.polimi.ingsw.cg_38.controller.RMIRemoteObjectInterface;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;

import javax.xml.parsers.ParserConfigurationException;

public class SubscribeRMI extends Subscribe{
	
	private String RMI_ID;
	
	public SubscribeRMI(GameEvent evt){
		super((EventSubscribeRMI)evt);
		this.setRMI_ID(((EventSubscribeRMI)evt).getRMI_ID());
	}
	
	public String getRMI_ID() {
		return RMI_ID;
	}

	public void setRMI_ID(String rMI_ID) {
		RMI_ID = rMI_ID;
	}

	@Override
	public NotifyEvent perform(ServerController server) throws ParserConfigurationException, Exception {
		
		Registry registry =  LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.getRMI_PORT());
		RMIRemoteObjectInterface clientView = (RMIRemoteObjectInterface) registry.lookup(this.getRMI_ID()); 
		Communicator c = new RMICommunicator(clientView);
		return super.generalEventGenerator(c, server);
	}


}
