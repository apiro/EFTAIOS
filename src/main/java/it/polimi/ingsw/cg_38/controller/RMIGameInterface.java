package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che dichiara i metodi che possono essere invocati dal client 
 * --> tutti i parametri devono essere serializzabili !!!
 * 
 * tutti gli oggetti remoti utilizzati nei communicator per implementare le send per esempio devono rispettare 
 * questo protocollo
 * 
 * */

public interface RMIGameInterface extends Remote {
	
	public NotifyEvent grabEvent() throws RemoteException;

	public void trasmitEvent(GameEvent evt) throws RemoteException;
		
}
