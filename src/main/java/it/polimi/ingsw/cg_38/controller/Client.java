package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {

	private int port = 4322;
	private static int clientServerSocketPort;
	
	public static int getClientServerSocketPort() {
		return clientServerSocketPort;
	}

	public static void setClientServerSocketPort(int clientServerSocketPort) {
		Client.clientServerSocketPort = clientServerSocketPort;
	}

	private String host = "127.0.0.1";
	private Communicator communicator;
	private Registry registry;
	private Player player;
	private String name;
	private String room;
	private String map;
	private Scanner in = new Scanner(System.in);
	private ConcurrentLinkedQueue<Event> toProcess = new ConcurrentLinkedQueue<Event>();
	private ArrayList<GameEvent> toSend = new ArrayList<GameEvent>();
	private ServerSocket clientSocket;

	public Client(String s) throws NotBoundException, UnknownHostException, IOException, AlreadyBoundException {
		System.out.println("NOME ROOM | TUO NOME NEL GIOCO | NOME MAPPA: ");
		name = in.nextLine();
		room = in.nextLine();
		map = in.nextLine();
		this.player = new Player(name);
		EventSubscribe evt = null;
		
		if(s.equals("RMI")) {
			registry = LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.RMI_PORT);
			
			RMIRegistrationInterface game = (RMIRegistrationInterface) registry.lookup("REGISTRATIONVIEW");
			System.out.println("RMI Connection Established !");
			/*System.out.println(game.isLoginValid("albi"));
			System.out.println(game.isLoginValid("test"));*/
			
			RMIRemoteObjectInterface serverView = game.register();
			RMIRemoteObjectInterface clientView = new ClientView(this.getToProcess());
			
			RMIRemoteObjectDetails clientPersonalView = new RMIRemoteObjectDetails("CLIENTVIEW" + name);
			
			registry.bind(clientPersonalView.getRMI_ID(), clientView);
			
			this.communicator = new RMICommunicator(serverView);
			
			evt = new EventSubscribeRMI(this.player, room, map, clientPersonalView.getRMI_ID());
			
		} else if (s.equals("Socket")) {
			
			this.startSocketEnvironment();
			System.out.println("Creating a socket with the server !");
			this.communicator = new SocketCommunicator(this.port);
			
			evt = new EventSubscribeSocket(new Player(name), room, map, Client.getClientServerSocketPort());
		}
		
		this.communicator.send(evt);
	}
	
	private void startSocketEnvironment() throws IOException {
		clientSocket = new ServerSocket(Client.getClientServerSocketPort());
	
	    new SocketConnectionsHandler(this.clientSocket, this.toProcess).start();
	    
	    System.out.println("Server socket ready on " + Client.getClientServerSocketPort());
		System.out.println("Server ready");
	}
	
	public ConcurrentLinkedQueue<Event> getToProcess() {
		return toProcess;
	}

	public ArrayList<GameEvent> getToSend() {
		return toSend;
	}

	public void startClient() throws RemoteException {
		while(true) {
			Event msg = toProcess.poll();
			if(msg != null) {
				this.handleSentNotifyEvent(msg);
			} else {
				try {
					synchronized(toProcess) {
						toProcess.wait();
					}
				} catch (InterruptedException e) {
					System.err.println("Cannot wait on the queue!");
				}
			}
		}
	}
	
	/*public void trasmitGameEvent(Event evt) throws RemoteException {
		communicator.send(evt);
		Event responseEvent = communicator.recieveEvent();
		this.handleSentNotifyEvent(responseEvent);
	}*/
	
	public void handleSentNotifyEvent(Event event) {
		System.out.println("Parsing S->C Event... : " + event.toString());
		System.out.println("Callback event arrived !");
	}
	
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException, AlreadyBoundException{
		Scanner in = new Scanner(System.in);
		System.err.println("WELCOME TO THE GAME !\n");
		System.out.println("MAKE YOUR CHOICE! [RMI] [SOCKET]");
		String choose = in.nextLine();
		if(choose.equals("Socket")) {
			 Client.setClientServerSocketPort(Integer.parseInt(in.nextLine()));
		}
		Client client = new Client(choose);
		//costruzione evento da inviare EventCreator.createEvent(client.in.nextLine())
		client.startClient();
	}
}
