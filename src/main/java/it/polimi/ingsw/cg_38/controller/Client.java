package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.IOException;
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
	private String host = "127.0.0.1";
	private Communicator communicator;
	private Registry registry;
	private Player player;
	private String name;
	private String room;
	private String map;
	private Scanner in = new Scanner(System.in);
	private ConcurrentLinkedQueue<NotifyEvent> toProcess = new ConcurrentLinkedQueue<NotifyEvent>();
	private ArrayList<GameEvent> toSend = new ArrayList<GameEvent>();

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
			System.out.println(game.isLoginValid("albi"));
			System.out.println(game.isLoginValid("test"));
			
			RMIRemoteObjectInterface serverView = game.register();
			RMIRemoteObjectInterface clientView = new ClientView(this.getToProcess());
			
			registry.bind("CLIENTVIEW1", clientView);
			
			this.communicator = new RMICommunicator(serverView);
			
			evt = new EventSubscribeRMI(new Player(name), room, map, "CLIENTVIEW1");
			
		} else if (s.equals("Socket")) {
			Socket socket = new Socket(host, port );
			System.out.println("Connection Established");
			this.communicator = new SocketCommunicator(socket);
			evt = new EventSubscribeSocket(new Player(name), room, map, socket);
		}
		
		this.communicator.send(evt);
	}
	
	public ConcurrentLinkedQueue<NotifyEvent> getToProcess() {
		return toProcess;
	}

	public ArrayList<GameEvent> getToSend() {
		return toSend;
	}

	public void startClient() throws RemoteException {
		while(true) {
			Event serverEvent = communicator.recieveEvent();
			this.handleSentNotifyEvent(serverEvent);
		}
	}
	
	public void trasmitGameEvent(Event evt) throws RemoteException {
		communicator.send(evt);
		Event responseEvent = communicator.recieveEvent();
		this.handleSentNotifyEvent(responseEvent);
	}
	
	public void handleSentNotifyEvent(Event event) {
		//costruisco l'azione S->C corrispondente e la performo.
	}
	
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException{
		Scanner in = new Scanner(System.in);
		System.out.println("MAKE YOUR CHOOSE");
		String choose = in.nextLine();
		Client client = new Client(choose);
		//invio l'evento di subscribe al server
		/*Event evt = null;
		//costruzione evento da inviare EventCreator.createEvent(client.in.nextLine())
		client.handleFiredGameEvent(evt);*/
		client.startClient();
	}
}
