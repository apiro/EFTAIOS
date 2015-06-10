package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.action.InitGameAction;
import it.polimi.ingsw.cg_38.controller.action.Subscribe;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Observable;

public class SubscribeController extends Observable implements Runnable {

	private SocketCommunicator socketCommunicator;
	private ServerController server;

	public SubscribeController(SocketCommunicator socketCommunicator, ServerController server) {
		
		this.socketCommunicator = socketCommunicator;
		this.server = server;
	}

	public void run() {
		EventSubscribe evt = (EventSubscribe) this.socketCommunicator.recieveEvent();
		Action action = GameActionCreator.createGameAction(evt);
		NotifyEvent callbackEvent = null;
		GameController gcFound = null;
		try {
			callbackEvent = ((Subscribe)action).generalEventGenerator(socketCommunicator, server);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gcFound = server.getTopics().get(evt.getGenerator().getName());
		gcFound.addEventToTheQueue(callbackEvent);
		try {
			gcFound.sendNotifyEvent();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		Thread.currentThread().interrupt();
	}
	
}
