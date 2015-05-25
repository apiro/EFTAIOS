package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.ActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.action.InitGameAction;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.xml.parsers.ParserConfigurationException;

public class ServerController extends Observable {
	
	/*private static final String name = "CENTRALSERVER";*/
	private int socketPortNumber = 4322;
	/*private int rmiRegistryPortNumber = 1233;*/
	private String IPAdress = "localhost";
	private ServerSocket serverSocket;
	//mappa che segna il nome del player con il corrispondente gamecontroller per poter trovare il topic di un player facilmente
	private HashMap<String, GameController> topics = new HashMap<String, GameController>();
	
	public HashMap<String, GameController> getTopics() {
		return topics;
	}
	
	private Scanner in = new Scanner(System.in);
	private ConcurrentLinkedQueue<NotifyEvent> toDistribute;
	private ConcurrentLinkedQueue<GameEvent> toDispatch;
	
	private Registry registry;
	private Boolean serverAlive = true;
	
	/**
	 * è il buffer nel quale vengono inseriti i messaggi provenienti da ogni client, quindi generati da chiamate a metodi RMI
	 * remoti e chiamati dal playerhandler(tramite l'attributo SocketServerInterface).
	 * 
	 * 
	 * client->Rmi->passa messaggi ad un metodo di un oggetto Rmi del server-> questo aggiunge il messaggio al buffer del server
	 * */
	
	public ServerController(/*int socketPortNumber, int rmiRegistryPortNumber*/) throws RemoteException {
		/*this.socketPortNumber = socketPortNumber;*/
		this.toDispatch = new ConcurrentLinkedQueue<GameEvent>();
		this.toDistribute = new ConcurrentLinkedQueue<NotifyEvent>();
	}
	
	public void closeServer() {
		this.serverAlive = false;
		this.setChanged();
		this.notifyObservers();
	}

	public void startServer() throws ParserConfigurationException, Exception {
		
		this.startRMIEnvironment();
		
		this.startSocketEnvironment();
		
		while(serverAlive) {
			Event msg = toDispatch.poll();
			if(msg != null) {
				//cerca il topic del giocatore contenuto negli attributi dell'evento e dispaccia l'evento al gamecontroller
				//corrispondente
				if(msg instanceof EventSubscribe) {
					Action generatedAction = ActionCreator.createAction((GameEvent)msg);
		    		NotifyEvent callbackEvent = ((InitGameAction)generatedAction).perform(this);
		    		this.getToDistribute().add(callbackEvent);
				}
				(topics.get(msg.getGenerator().getName())).addEventToTheQueue((GameEvent)msg);
			} else {
				try {
					synchronized(toDispatch) {
						toDispatch.wait();
					}
				} catch (InterruptedException e) {
					System.err.println("Cannot wait on the queue!");
				}
			}
			System.out.println("Do you wanna stop the server? Y for yes");
			if(in.nextLine().equals("Y")) {
				this.closeServer();
			}
		}
	}
	
	private void startRMIEnvironment() throws RemoteException, AlreadyBoundException {
		
		this.registry = LocateRegistry.createRegistry(RMIRemoteObjectDetails.RMI_PORT);
		
		//creo un oggetto i quali metodi potranno essere chiamati remotamente perche estende Remote
		//gli passo il buffer cosi può aggiungere eventi al buffers
		RMIRegistrationInterface registration = new RegistrationView(this.getToDispatch());
		
		//registra lo stub sul registry con un nome tramite il quale potrà essere cercato
		registry.bind(RMIRemoteObjectDetails.RMI_ID, registration);
		
		System.out.println("Rmi registry ready on " + RMIRemoteObjectDetails.RMI_PORT);
	}

	public GameController initAndStartANewGame(String map, String room) throws ParserConfigurationException, Exception {

    	GameController generalController = new GameController(map, room);
    	
    	generalController.setState(GameState.STARTING);
    	generalController.waitingForPlayerConnection();
    	/*for(Player pl:generalController.getGameModel().getGamePlayers()) {
    		generalController.getPcs().add(new PlayerController(pl));
    	}*/
    	generalController.assignAvatars();
    	generalController.setFirstTurn();
    	generalController.setState(GameState.RUNNING);
    	generalController.startGame();
    	return generalController;
    }
	
	public ConcurrentLinkedQueue<GameEvent> getToDispatch() {
		return toDispatch;
	}

	public void setToDispatch(ConcurrentLinkedQueue<GameEvent> toDispatch) {
		this.toDispatch = toDispatch;
	}

	private void startSocketEnvironment() throws IOException {
		serverSocket = new ServerSocket(socketPortNumber);
		
	    new SocketConnectionsHandler(this.serverSocket, this.getToDispatch(), this.getToDistribute()).start();
	    
	    System.out.println("Server socket ready on " + socketPortNumber);
		System.out.println("Server ready");
	}

	public ConcurrentLinkedQueue<NotifyEvent> getToDistribute() {
		return toDistribute;
	}

	public static void main(String[] args) throws ParserConfigurationException, Exception {
		ServerController server = new ServerController();
		server.startServer();
	}
}
