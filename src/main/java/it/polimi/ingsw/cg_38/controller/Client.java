package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client {

	private int port = 1233;
	private String host = "127.0.0.1";
	private Communicator communicator;
	private Registry registry;
	private String name = "CENTRALSERVER";
	private Scanner in = new Scanner(System.in);
	private ConcurrentLinkedQueue<NotifyEvent> toProcess = new ConcurrentLinkedQueue<NotifyEvent>();
	private ConcurrentLinkedQueue<GameEvent> toSend = new ConcurrentLinkedQueue<GameEvent>();

	public Client(String s) throws NotBoundException, UnknownHostException, IOException {
		System.out.println("NOME ROOM | TUO NOME NEL GIOCO | NOME MAPPA: ");
		String name = in.nextLine();
		String room = in.nextLine();
		String map = in.nextLine();
		EventSubscribe evt = new EventSubscribe(new Player(name), room, map);
		if(s.equals("RMI")) {
			registry = LocateRegistry.getRegistry(host, port);
			RMIRegistrationInterface game = (RMIRegistrationInterface) registry.lookup(name);
			
			GameView view = game.register(evt);
			this.communicator = new RMICommunicator(view);
		} else if (s.equals("Socket")) {
			Socket socket = new Socket(host, port );
			System.out.println("Connection Established");
			this.communicator = new SocketCommunicator(socket);
		}
		this.communicator.send(evt);
	}
	
	public void startClient() throws RemoteException {
		while(true) {
			Event serverEvent = communicator.recieveEvent();
			this.handleSentNotifyEvent(serverEvent);
		}
	}
	
	public void handleFiredGameEvent(Event evt) throws RemoteException {
		communicator.send(evt);
		Event responseEvent = communicator.recieveEvent();
		this.handleSentNotifyEvent(responseEvent);
	}
	
	public void handleSentNotifyEvent(Event event) {
		//costruisco l'azione S->C corrispondente e la performo.
	}
	
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException{
		System.out.println("MAKE YOUR CHOOSE");
		Client client = new Client("RMI");
		//invio l'evento di subscribe al server
		/*Event evt = null;
		//costruzione evento da inviare EventCreator.createEvent(client.in.nextLine())
		client.handleFiredGameEvent(evt);*/
		client.startClient();
	}
}
