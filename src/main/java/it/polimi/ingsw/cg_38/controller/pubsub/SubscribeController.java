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
import java.util.Observable;

/**
 * è un thread che ha lo scopo di ricevere l'unico evento che il client può inviare su questo socket, vale a dire
 * l'evento di sottoscrizione iniziale ad un certo topic, una volta ricevuto e processato l'evento questo thread termina.
 * */
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
	/**
	 * metodo che pone il thread in attesa della ricezione dell'evento di subscribe
	 * */
	@Override
	public void run() {
		EventSubscribe evt = (EventSubscribe) this.socketCommunicator.recieveEvent();
		Action action = GameActionCreator.createGameAction(evt);
		NotifyEvent callbackEvent = null;
		GameController gcFound = null;
		try {
			callbackEvent = ((Subscribe)action).generalEventGenerator(socketCommunicator, server);
		} catch (Exception e) {
			e.printStackTrace();
			logger.print("General exception in sending the subscribe event ...");
		}
		gcFound = server.getTopics().get(evt.getGenerator().getName());
		gcFound.addEventToTheQueue(callbackEvent);
		try {
			gcFound.sendNotifyEvent();
		} catch (IOException e) {
			logger.print("A client is probably disconnected ...");
		}

		Thread.currentThread().interrupt();
	}
	
}
