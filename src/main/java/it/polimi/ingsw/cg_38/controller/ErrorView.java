package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ErrorView extends UnicastRemoteObject implements RMIRemoteObjectInterface{

	private static final long serialVersionUID = 1L;
	
	protected ErrorView() throws RemoteException {
		super();
	}

	@Override
	public void trasmitEvent(Event evt) throws RemoteException {
		
	}

	@Override
	public void processEvent(Event evt) throws RemoteException {
		
	}
	
	
}
