package it.polimi.ingsw.cg_38.controller.connection;

import it.polimi.ingsw.cg_38.controller.event.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che dichiara i metodi che possono essere invocati dal client 
 * */

public interface RMIRemoteObjectInterface extends Remote {
	

	public void trasmitEvent(Event evt) throws RemoteException;
	
	public void processEvent(Event evt) throws RemoteException;
		
}
