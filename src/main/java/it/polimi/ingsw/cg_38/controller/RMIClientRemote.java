package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

import java.rmi.Remote;

/**
 * interfaccia che dichiara i metodi che il client offre al server
 * */
public interface RMIClientRemote extends Remote {
	
	public void handleNotifyEvent(NotifyEvent evt);
	
}
