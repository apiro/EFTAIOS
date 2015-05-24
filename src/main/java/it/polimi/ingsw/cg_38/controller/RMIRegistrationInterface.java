package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.rmi.Remote;

/**
 * interfaccia che dichiara i metodi per la registrazione al server
 * */
public interface RMIRegistrationInterface extends Remote {

	public GameView register(EventSubscribe evt);
}
