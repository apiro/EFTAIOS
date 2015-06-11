package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.GameActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.controller.action.InitGameAction;
import it.polimi.ingsw.cg_38.controller.action.GameAction;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerLooser;
import it.polimi.ingsw.cg_38.gameEvent.EventPlayerWinner;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotYourTurn;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class ServerController extends Observable {
	
	private int socketPortNumber = 4322;
	private int portPublisherSubscriber = 3111;
	private String IPAdress = "localhost";
	private ServerSocket serverSocketPubSub;
	private ServerSocket serverSocketClientServer;
	//mappa che segna il nome del player con il corrispondente gamecontroller per poter trovare il topic di un player facilmente
	private HashMap<String, GameController> topics = new HashMap<String, GameController>();
	
	public HashMap<String, GameController> getTopics() {
		return topics;
	}
	
	private Scanner in = new Scanner(System.in);
	private ConcurrentLinkedQueue<Event> toDispatch;
	
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
		
		System.err.println("Starting the Server !");
		
		this.startRMIEnvironment();
		
		this.startSocketEnvironment();
		
		while(serverAlive) {
			Event msg = toDispatch.poll();
			NotifyEvent callbackEvent = null;
			if(msg != null) {
				System.err.println("Game Event arrived !\n");
				System.out.println("Parsing Event... : " + msg.toString());
				GameController gcFound = null;
				Action generatedAction = GameActionCreator.createGameAction(msg);
				gcFound = topics.get(msg.getGenerator().getName());
				if( msg.getGenerator().getName().equals(gcFound.getGameModel().getActualTurn().getCurrentPlayer().getName())) {
					//se l'evento viene dal giocatore del turno corrente
					callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
				} else {
					//se l'evento non viene dal gicatore del turno (qualcuno ha inviato un evento fuori turno)
					if(msg instanceof EventPlayerLooser || msg instanceof EventPlayerWinner) {
						callbackEvent = gcFound.performUserCommands((GameAction)generatedAction);
					} else {
						callbackEvent = new EventNotYourTurn(msg.getGenerator());
					}
				}
				gcFound.addEventToTheQueue(callbackEvent);
				this.setChanged();
				this.notifyObservers(gcFound.getTopic());
				if(callbackEvent.getType().equals(NotifyEventType.Added)) {
					if(((EventAddedToGame)callbackEvent).getAdded() == false ) {
						gcFound.getSubscribers().remove(msg.getGenerator().getName());
						this.getTopics().remove(msg.getGenerator().getName());
						this.deleteObserver(gcFound);
					}
				}
				System.err.println("Event parsed !");
				System.out.println("---------------------------------------------------------------------\n");
			} 
		}
	}
	
	private void startRMIEnvironment() throws RemoteException, AlreadyBoundException {
		
		RMIRemoteObjectDetails serverView = new RMIRemoteObjectDetails("REGISTRATIONVIEW");
		
		System.setProperty("java.rmi.server.hostname",this.IPAdress);
		
		this.registry = LocateRegistry.createRegistry(RMIRemoteObjectDetails.getRMI_PORT());
		
		//creo un oggetto i quali metodi potranno essere chiamati remotamente perche estende Remote
		//gli passo il buffer cosi può aggiungere eventi al buffers
		RMIRegistrationInterface registration = new RegistrationView(this);
		
		//registra lo stub sul registry con un nome tramite il quale potrà essere cercato
		registry.bind(serverView.getRMI_ID(), registration);
		
		System.out.println("Rmi registry ready on " + RMIRemoteObjectDetails.getRMI_PORT());
	}

	public GameController initAndStartANewGame(String map, String topic) throws ParserConfigurationException, Exception {

    	GameController generalController = new GameController(map, topic);
    	return generalController;
    }
	
	public ConcurrentLinkedQueue<Event> getToDispatch() {
		return toDispatch;
	}

	public void setToDispatch(ConcurrentLinkedQueue<Event> toDispatch) {
		this.toDispatch = toDispatch;
	}

	private void startSocketEnvironment() throws IOException {
		
		this.serverSocketClientServer = new ServerSocket(socketPortNumber);
		this.serverSocketPubSub = new ServerSocket(portPublisherSubscriber);
	
	    Thread t1 = new SocketConnectionsHandler(this.serverSocketClientServer, this.getToDispatch(), this.serverAlive, this.getTopics());
	    Thread t2 = new PubSubConnectionsHandler(this.serverSocketPubSub, this.serverAlive, this);
	    t1.setName("ServerSocketClientServer");
	    t2.setName("ServerSocketPubSub");
	    t1.start();
	    System.out.println("ServerSocket Client/Server ready on " + socketPortNumber);
	    t2.start();
	    System.out.println("ServerSocket Pub/Sub ready on " + socketPortNumber);
	   
		System.out.println("Server ready");
	}

	public static void main(String[] args) throws ParserConfigurationException, Exception {
		Thread.currentThread().setName("ApplicationMainThread");
		ServerController server = new ServerController();
		server.startServer();
	}
}
