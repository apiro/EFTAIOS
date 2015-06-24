package it.polimi.ingsw.cg_38.controller.pubsub;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.action.Subscribe;
import it.polimi.ingsw.cg_38.controller.connection.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Observable;

public class SubscribeController extends Observable implements Runnable {

	private SocketCommunicator socketCommunicator;
	private ServerController server;
	private Logger logger = new LoggerCLI();

	public SubscribeController(SocketCommunicator socketCommunicator, ServerController server) {
		
		this.socketCommunicator = socketCommunicator;
		try {
			this.socketCommunicator.initCommunicator();
		} catch (IOException e) {
			logger.print("Problems with the init of the communicator ...");
		}
		this.server = server;
	}

	@Override
	public void run() {
		EventSubscribe evt = (EventSubscribe) this.socketCommunicator.recieveEvent();
		Action action = GameActionCreator.createGameAction(evt);
		NotifyEvent callbackEvent = null;
		GameController gcFound = null;
		try {
			callbackEvent = ((Subscribe)action).generalEventGenerator(socketCommunicator, server);
		} catch (Exception e) {
			logger.print("General exception in sending the subscribe event ...");
		}
		gcFound = server.getTopics().get(evt.getGenerator().getName());
		gcFound.addEventToTheQueue(callbackEvent);
		try {
			gcFound.sendNotifyEvent();
		} catch (RemoteException e) {
			logger.print("Problems with the RMI connection ...");
		}

		Thread.currentThread().interrupt();
	}
	
}
