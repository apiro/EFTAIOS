package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyClosingTopic;
import it.polimi.ingsw.cg_38.controller.pubsub.PubSubConnectionsHandler;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.controller.connection.RMIRegistrationInterface;
import it.polimi.ingsw.cg_38.controller.connection.RMIRemoteObjectDetails;
import it.polimi.ingsw.cg_38.controller.connection.RegistrationView;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class ServerController extends Observable {
	
	private int socketPortNumber = 4322;
	private int portPublisherSubscriber = 3111;
	private String IPAdress = "localhost";
	private ServerSocket serverSocketPubSub;
	private ServerSocket serverSocketClientServer;
	//mappa che segna il nome del player con il corrispondente gamecontroller per poter trovare il topic di un player facilmente
	private Map<String, GameController> topics = new HashMap<String, GameController>();
	private Logger logger = new LoggerCLI();
	
	public Map<String, GameController> getTopics() {
		return topics;
	}
	
	private Queue<Event> toDispatch;
	
	private Registry registry;
	private Boolean serverAlive = true;
	
	/**
	 * è il buffer nel quale vengono inseriti i messaggi provenienti da ogni client, quindi generati da chiamate a metodi RMI
	 * remoti e chiamati dal playerhandler(tramite l'attributo SocketServerInterface).
	 * 
	 * 
	 * client->Rmi->passa messaggi ad un metodo di un oggetto Rmi del server-> questo aggiunge il messaggio al buffer del server
	 * */
	
	public ServerController() throws RemoteException {
		this.toDispatch = new ConcurrentLinkedQueue<Event>();
	}
	
	public void closeServer() {
		//closing the server and deallocate all the objects
		this.serverAlive = false;
		this.setToDispatch(null);
		this.serverSocketClientServer = null;
		this.serverSocketPubSub = null;
		this.registry = null;
		this.topics = null;
	}

	public void startServer() throws ParserConfigurationException, Exception {
		
		logger.print("Starting the Server !");
		
		this.startRMIEnvironment();
		
		this.startSocketEnvironment();
		
		while(serverAlive) {
			Event msg = toDispatch.poll();
			List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
			if(msg != null) {
				logger.print("Game Event arrived !\n");
				logger.print("Parsing Event... : " + msg.toString());
				GameController gcFound = null;
				Action generatedAction = GameActionCreator.createGameAction(msg);
				gcFound = topics.get(msg.getGenerator().getName());
				if(gcFound == null) break;
				if( msg.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName()) ||
						msg instanceof EventChat) {
					//se l'evento viene dal giocatore del turno corrente
					callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
				} else {
					//se l'evento non viene dal gicatore del turno (qualcuno ha inviato un evento fuori turno)
					/*if(msg instanceof EventPlayerLooser || msg instanceof EventPlayerWinner) {
						callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
					} else {
						NotifyEvent callbackError = new EventNotYourTurn(msg.getGenerator());
						callbackEvent.add(callbackError);
					}*/
				}
				Event eR= null;
				for(NotifyEvent e:callbackEvent) {
					if(e instanceof EventNotifyClosingTopic) {
						synchronized(gcFound) {
							this.removeTopic(gcFound);
						}
						eR = e;
					} else {
						synchronized(gcFound) {
							gcFound.addEventToTheQueue(e);
							this.setChanged();
							this.notifyObservers(gcFound.getTopic());
						}
					}
				}
				if(eR != null) callbackEvent.remove(eR);
				
				for(NotifyEvent e:callbackEvent) {
					if(e.getType().equals(NotifyEventType.ADDED)) {
						if(((EventAddedToGame)e).getAdded() == false ) {
							gcFound.getSubscribers().remove(msg.getGenerator().getName());
							this.getTopics().remove(msg.getGenerator().getName());
							this.deleteObserver(gcFound);
						}
					}
				}
				
				logger.print("Event parsed !");
				logger.print("---------------------------------------------------------------------\n");
			} 
		}
	}
	
	public void removeTopic(GameController gcFound) {
		List<String> toRemove = new ArrayList<String>();
		for(String topic:topics.keySet()) {
			if(topics.get(topic).getTopic().equals(gcFound.getTopic())) {
				toRemove.add(topic);
			}
		}
		for(String s:toRemove) {
			topics.remove(s);
			logger.print("---------------------------------------------------------------------\n");
			logger.print(s + " removed from topic " + gcFound.getTopic() + " !");
			logger.print("---------------------------------------------------------------------\n");
		}
		logger.print("CLOSING: " + gcFound.getTopic());
		logger.print("---------------------------------------------------------------------\n");
	}

	private void startRMIEnvironment() throws RemoteException, AlreadyBoundException {
		
		RMIRemoteObjectDetails serverView = new RMIRemoteObjectDetails("REGISTRATIONVIEW");
		
		System.setProperty("java.rmi.server.hostname",this.IPAdress);
		
		try{
			this.registry = LocateRegistry.createRegistry(RMIRemoteObjectDetails.getRMI_PORT());
			//creo un oggetto i quali metodi potranno essere chiamati remotamente perche estende Remote
			//gli passo il buffer cosi può aggiungere eventi al buffers
			RMIRegistrationInterface registration = new RegistrationView(this);
			
			//registra lo stub sul registry con un nome tramite il quale potrà essere cercato
			registry.bind(serverView.getRMI_ID(), registration);
			logger.print("Rmi registry ready on " + RMIRemoteObjectDetails.getRMI_PORT());
		} catch(ExportException e) {
			logger.print("The port you have choosen to instantiate an RMI registry is already bounded...\nClose the running server on this port or change port !"); 
		}
	}

	public GameController initAndStartANewGame(String map, String topic) throws ParserConfigurationException, Exception {

    	GameController generalController = new GameController(map, topic);
    	return generalController;
    }
	
	public Queue<Event> getToDispatch() {
		return toDispatch;
	}

	public void setToDispatch(Queue<Event> toDispatch) {
		this.toDispatch = toDispatch;
	}

	private void startSocketEnvironment() throws IOException {
		try {
			this.serverSocketClientServer = new ServerSocket(socketPortNumber);
			this.serverSocketPubSub = new ServerSocket(portPublisherSubscriber);
		
		    Thread t1 = new SocketConnectionsHandler(this.serverSocketClientServer, this.getToDispatch(), this.serverAlive, this.getTopics());
		    Thread t2 = new PubSubConnectionsHandler(this.serverSocketPubSub, this.serverAlive, this);
		    t1.setName("ServerSocketClientServer");
		    t2.setName("ServerSocketPubSub");
		    t1.start();
		    logger.print("ServerSocket Client/Server ready on " + socketPortNumber);
		    t2.start();
		    logger.print("ServerSocket Pub/Sub ready on " + socketPortNumber);
		   
		    logger.print("Server ready");
		} catch(BindException e) {
			logger.print("The socket address and the server socket port are already in use !");
		}
	}

	public static void main(String[] args) throws ParserConfigurationException, Exception {
		Thread.currentThread().setName("ApplicationMainThread");
		ServerController server = new ServerController();
		server.startServer();
	}
}
