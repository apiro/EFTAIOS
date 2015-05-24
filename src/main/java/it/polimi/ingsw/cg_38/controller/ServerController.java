package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.GameController;
import it.polimi.ingsw.cg_38.controller.GameState;
import it.polimi.ingsw.cg_38.controller.PlayerController;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.ParserConfigurationException;

public class ServerController {
	
	private static final String name = "CENTRALSERVER";
	private int socketPortNumber = 33344;
	private int rmiRegistryPortNumber = 2344;
	private String IPAdress = "localhost";
	private ServerSocket serverSocket;
	//mappa che segna il nome del player con il corrispondente gamecontroller per poter trovare il topic di un player facilmente
	private HashMap<String, GameController> topics = new HashMap<String, GameController>();
	
	public HashMap<String, GameController> getTopics() {
		return topics;
	}

	/**
	 * il seguente è un oggetto che gestisce la logica di creazione dei thread, questo oggetto per esempio gestisce la logica di creazione 
	 * di un thread quando una sua creazione è richiesta
	 **/
	private ExecutorService executor;
	private ConcurrentLinkedQueue<NotifyEvent> toDistribute;
	private ConcurrentLinkedQueue<GameEvent> toDispatch;
	
	private final Registry registry;
	private Boolean serverAlive;
	
	/**
	 * è il buffer nel quale vengono inseriti i messaggi provenienti da ogni client, quindi generati da chiamate a metodi RMI
	 * remoti e chiamati dal playerhandler(tramite l'attributo SocketServerInterface).
	 * 
	 * 
	 * client->Rmi->passa messaggi ad un metodo di un oggetto Rmi del server-> questo aggiunge il messaggio al buffer del server
	 * */
	
	public ServerController(int socketPortNumber, int rmiRegistryPortNumber) throws RemoteException {
		this.registry = LocateRegistry.createRegistry(rmiRegistryPortNumber);
		this.socketPortNumber = socketPortNumber;
		this.executor = Executors.newCachedThreadPool();
		this.toDispatch = new ConcurrentLinkedQueue<GameEvent>();
		this.toDistribute = new ConcurrentLinkedQueue<NotifyEvent>();
	}

	public void startServer() throws IOException {
		this.startRMIEnvironment();
		System.out.println("Rmi registry ready on " + rmiRegistryPortNumber);
		this.startSocketEnvironment();
		System.out.println("Server socket ready on " + socketPortNumber);
		System.out.println("Server ready");
		
		while(serverAlive) {
			Event msg = toDispatch.poll();
			if(msg != null) {
				//cerca il topic del giocatore contenuto negli attributi dell'evento e dispaccia l'evento al gamecontroller
				//corrispondente
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
		}
	}
	
	private void startRMIEnvironment() throws RemoteException {
		
		//creo un oggetto i quali metodi potranno essere chiamati remotamente perche estende Remote
		//gli passo il buffer cosi può aggiungere eventi al buffers
		RMIRegistrationInterface registration = new RegistrationView(this.getToDispatch());
		
		//creo lo stub dell'oggetto remotizzabile creato prima
		RMIRegistrationInterface stub = (RMIRegistrationInterface) UnicastRemoteObject.exportObject(registration, 0);
		
		//registra lo stub sul registry con un nome tramite il quale potrà essere cercato
		registry.rebind(name, stub);
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
	    while(serverAlive) {
			Socket socket = serverSocket.accept();
			//la riga dopo crea un thread per la gestione del socket arrivato
			PlayerController view = new PlayerController(new SocketCommunicator(socket, toDispatch, toDistribute));
			//fa partire il thread con la logia del ExecutorService
			executor.submit(view);			
		}
	    executor.shutdown();
	    serverSocket.close();
	}

	public static void main(String[] args) throws IOException {
		ServerController server = new ServerController(33435, 5788);
		server.startServer();
	}
}
