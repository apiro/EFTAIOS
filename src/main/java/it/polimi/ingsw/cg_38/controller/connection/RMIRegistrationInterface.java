package it.polimi.ingsw.cg_38.controller.connection;


import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che dichiara i metodi per la registrazione al server
 * */
public interface RMIRegistrationInterface extends Remote {

	public RMIRemoteObjectInterface register(RMIRemoteObjectInterface clientView, EventSubscribe subEvent) throws RemoteException;

	public boolean isLoginValid(String username) throws RemoteException;
	
	public void trasmitEventToPublisher(Event evt) throws RemoteException;
}
