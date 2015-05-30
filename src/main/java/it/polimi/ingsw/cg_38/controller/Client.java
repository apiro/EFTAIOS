package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
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
	private Boolean clientAlive;
	private ClientSendingInterface userCommands;
	
	public ClientSendingInterface getUserCommands() {
		return userCommands;
	}

	public void setUserCommands(ClientSendingInterface userCommands) {
		this.userCommands = userCommands;
	}

	public Boolean getClientAlive() {
		return clientAlive;
	}

	public void setClientAlive(Boolean clientAlive) {
		this.clientAlive = clientAlive;
	}

	private Scanner in = new Scanner(System.in);
	private ConcurrentLinkedQueue<Event> toProcess = new ConcurrentLinkedQueue<Event>();
	private LinkedList<Event> toSend = new LinkedList<Event>();
	private ServerSocket clientSocket;
	private ClientInterface userInterface;

	public Client(String s) throws IOException, NotBoundException {
		System.err.println("WELCOME TO THE GAME !\n");
		userInterface = new CLIView(this);
		System.out.println("INSERT  : \n\t1) YOUR USERNAME: \n\t2) THE ROOM YOU WANT TO ACCESS : \n\t3) THE MAP NAME: ");
		name = in.nextLine();
		room = in.nextLine();
		map = in.nextLine();
		this.player = new Player(name);
		EventSubscribe evt = null;
		
		if(s.equals("RMI")) {
			registry = LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.RMI_PORT);
			
			RMIRegistrationInterface game = null;
			game = (RMIRegistrationInterface) registry.lookup("REGISTRATIONVIEW");
			
			System.out.println("RMI Connection Established !");
			/*System.out.println(game.isLoginValid("albi"));
			System.out.println(game.isLoginValid("test"));*/
			
			RMIRemoteObjectInterface serverView = game.register();
			RMIRemoteObjectInterface clientView = new ClientView(this.getToProcess());
			
			RMIRemoteObjectDetails clientPersonalView = new RMIRemoteObjectDetails("CLIENTVIEW" + name);
			
			try {
				registry.bind(clientPersonalView.getRMI_ID(), clientView);
			} catch (AlreadyBoundException e) {
				System.err.println("The username you choose is already in use. Please change it.");
				Client client = new Client("RMI");
				client.startClient();
			}
			
			this.communicator = new RMICommunicator(serverView);
			
			evt = new EventSubscribeRMI(this.player, room, map, clientPersonalView.getRMI_ID());
			
		} else if (s.equals("Socket")) {
			
			this.startSocketEnvironment();
			System.out.println("Creating a socket with the server !");
			this.communicator = new SocketCommunicator(this.port);
			
			evt = new EventSubscribeSocket(new Player(name), room, map, Client.getClientServerSocketPort());
		} else {
			System.err.println("Communication Protocol not supported. Please choose [RMI] or [Socket], to quit game write [QUIT] !");
			String choose = in.nextLine();
			if(choose.equals("Socket")) {
				 Client.setClientServerSocketPort(Integer.parseInt(in.nextLine()));
			}
			if(choose.equals("QUIT")) {
				System.out.println("Hello !");
			}
			Client client = new Client(choose);
			//costruzione evento da inviare EventCreator.createEvent(client.in.nextLine())
			client.startClient();
		}
		
		this.communicator.send(evt);
	}
	
	private void startSocketEnvironment() throws IOException {
		clientSocket = new ServerSocket(Client.getClientServerSocketPort());
	
	    new SocketConnectionsHandler(this.clientSocket, this.toProcess, this.getClientAlive()).start();
	    
	    System.out.println("Server socket ready on " + Client.getClientServerSocketPort());
		System.out.println("Server ready");
	}
	
	public ConcurrentLinkedQueue<Event> getToProcess() {
		return toProcess;
	}

	public LinkedList<Event> getToSend() {
		return toSend;
	}

	public void startClient() throws RemoteException {
		while(true) {
			Event msg = toProcess.poll();
			if(msg != null) {
				this.handleSentNotifyEvent(msg);
			} /*else {
				try {
					synchronized(toProcess) {
						toProcess.wait();
					}
				} catch (InterruptedException e) {
					System.err.println("Cannot wait on the queue!");
				}
			}*/
		}
	}
	
	public void trasmitGameEvent() throws RemoteException {
		Event evt = this.getToSend().poll();
		communicator.send(evt);
	}
	
	public void handleSentNotifyEvent(Event event) {
		System.out.println("Recieving " + event.toString() + " ...");
		/*NotifyAction generated = (NotifyAction) NotifyActionCreator.createNotifyAction(event);
		generated.render(userInterface);*/
	}
	
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException, AlreadyBoundException{
		Scanner in = new Scanner(System.in);
		System.err.println("WELCOME TO THE GAME !");
		System.out.println("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [SOCKET] : ");
		String choose = in.nextLine();
		if(choose.equals("Socket")) {
			 Client.setClientServerSocketPort(Integer.parseInt(in.nextLine()));
		}
		Client client = new Client(choose);
		client.setUserCommands(new ClientSendingInterface(client.communicator));
		//costruzione evento da inviare EventCreator.createEvent(client.in.nextLine())
		while(true) {
			Event msg = client.toProcess.poll();
			if(msg != null) {
				client.handleSentNotifyEvent(msg);
				client.communicator.send(new EventMove(client.player, this.);
			} /*else {
				try {
					synchronized(client.toProcess) {
						client.toProcess.wait();
					}
				} catch (InterruptedException e) {
					System.err.println("Cannot wait on the queue!");
				}
			}*/
		}
	}
}
