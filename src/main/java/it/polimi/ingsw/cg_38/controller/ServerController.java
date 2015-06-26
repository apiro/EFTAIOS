package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
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

/** si occupa di rendere disponibile il server ad accettare connessioni e di ricevere gli eventi 
 * di gioco inviati dal giocatori e generare, di conseguenza, azioni che  poi vengono passate 
 * al relativo game controller */
public class ServerController extends Observable {
	
	private int socketPortNumber = 4322;
	
	private int portPublisherSubscriber = 3111;
	
	private String IPAdress = "localhost";
	
	private ServerSocket serverSocketPubSub;
	
	private ServerSocket serverSocketClientServer;
	
	/**mappa che assegna il nome del player al corrispondente gamecontroller per 
	 * poter trovare il topic a cu è sottoscritto un giocatore */
	private Map<String, GameController> topics = new HashMap<String, GameController>();
	
	private Logger logger = new LoggerCLI();
	
	public Map<String, GameController> getTopics() {
		return topics;
	}
	
	/** coda degli eventi di gioco ricevuti dai giocatori */
	private Queue<Event> toDispatch;
	
	private Registry registry;
	
	/** true se il server è attivo */
	private Boolean serverAlive = true;
	
	/** il costruttore inizializza la coda di eventi di gioco */
	public ServerController() throws RemoteException {
		this.toDispatch = new ConcurrentLinkedQueue<Event>();
	}
	
	/** chiude il server annullando tutte le connessioni ed ilimina il topic associato */
	public void closeServer() {
		//closing the server and deallocate all the objects
		this.serverAlive = false;
		this.setToDispatch(null);
		this.serverSocketClientServer = null;
		this.serverSocketPubSub = null;
		this.registry = null;
		this.topics = null;
	}

	/** starta il server che si mette in ascolto sulla coda di eventi di gioco per generare azioni
	 * che poi verranno performare dal controllore del gioco
	 * 
	 * @throws ParserConfigurationException
	 * @throws Exception
	 */
	public void startServer() throws ParserConfigurationException, Exception {
		
		logger.print("Starting the Server !");
		
		this.startRMIEnvironment();
		
		this.startSocketEnvironment();
		
		while(serverAlive) {
			Event msg = toDispatch.poll();
			List<NotifyEvent> callbackEvent = new ArrayList<NotifyEvent>();
			if(msg != null) {
				
				logger.print("---------------------------------------------------------------------\n");
				logger.print("Game Event arrived !\n");
				logger.print("Parsing Event... : " + msg.toString());
				
				GameController gcFound = null;
				Action generatedAction = GameActionCreator.createGameAction(msg);
				gcFound = topics.get(msg.getGenerator().getName());
				if(gcFound == null) break;
				if( msg.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName()) ||
						msg instanceof EventChat) {
					callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
					if(callbackEvent == null) break;
				} else {
					break;
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
				
				logger.print("Event parsed !");
				logger.print("---------------------------------------------------------------------\n");
			} 
		}
	}
	
	/** rimuove un topic dal server */
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

	/** setta tutte le variabili necessarie per la connessione di tipo RMI */
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

	/** inizializza e starta una nuova partita
	 * 
	 * @param map nome della mappa della partita
	 * @param topic nome del topic da creare
	 * @return ritorna il gameController associato alla nuova partita
	 * @throws ParserConfigurationException
	 * @throws Exception
	 */
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

	/** setta tutte le variabili necessarie per la connessione via Socket */
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
