package it.polimi.ingsw.cg_38.client;

import it.polimi.ingsw.cg_38.controller.connection.RMIRemoteObjectInterface;
import it.polimi.ingsw.cg_38.controller.event.Event;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Oggetto che viene ritornato se la registrazione non è andata a buon fine
 * */
public class ErrorView extends UnicastRemoteObject implements RMIRemoteObjectInterface{

	private static final long serialVersionUID = 1L;
	
	public ErrorView() throws RemoteException {
		super();
	}

	@Override
	public void trasmitEvent(Event evt) throws RemoteException {
		
	}

	@Override
	public void processEvent(Event evt) throws RemoteException {
		
	}
	
	
}
