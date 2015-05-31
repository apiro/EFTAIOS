package it.polimi.ingsw.cg_38.controller;


import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * interfaccia che dichiara i metodi per la registrazione al server
 * */
public interface RMIRegistrationInterface extends Remote {

	public RMIRemoteObjectInterface register() throws RemoteException;

	public boolean isLoginValid(String username) throws RemoteException;
}