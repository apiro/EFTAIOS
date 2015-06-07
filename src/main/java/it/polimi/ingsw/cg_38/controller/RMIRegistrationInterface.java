package it.polimi.ingsw.cg_38.controller;


import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che dichiara i metodi per la registrazione al server
 * */
public interface RMIRegistrationInterface extends Remote {

	public boolean isLoginValid(String username) throws RemoteException;

	public RMIRemoteObjectInterface register(RMIRemoteObjectInterface clientView, EventSubscribe evt) throws RemoteException;
}
