package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
			System.out.println("RMI Connection Established !");
			/*System.out.println(game.isLoginValid("albi"));
			System.out.println(game.isLoginValid("test"));*/
			
			RMIRemoteObjectInterface serverView = game.register();
			RMIRemoteObjectInterface clientView = new ClientView(this.getToProcess());
			
			RMIRemoteObjectDetails clientPersonalView = new RMIRemoteObjectDetails("CLIENTVIEW" + name);
			
			registry.bind(clientPersonalView.getRMI_ID(), clientView);
			
			this.communicator = new RMICommunicator(serverView);
			
			evt = new EventSubscribeRMI(new Player(name), room, map, clientPersonalView.getRMI_ID());
			
		} else if (s.equals("Socket")) {
			Socket socket = new Socket(host, port );
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			System.out.println("SOCKET Connection Established !");
			this.communicator = new SocketCommunicator(socket);
			((SocketCommunicator)this.communicator).setOutputStream(out);
			((SocketCommunicator)this.communicator).setInputStream(in);
			evt = new EventSubscribe(new Player(name), room, map);
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
			NotifyEvent msg = toProcess.poll();
			if(msg != null) {
				System.out.println("Parsing S->C Event... : " + msg.toString());
				System.out.println("Callback event arrived !");
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
	}
	
	public void handleSentNotifyEvent(Event event) {
		//costruisco l'azione S->C corrispondente e la performo.
		System.out.println("Parsing S->C Event... : " + event.toString());
	}*/
	
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException, AlreadyBoundException{
		Scanner in = new Scanner(System.in);
		System.err.println("WELCOME TO THE GAME !\n");
		System.out.println("MAKE YOUR CHOICE! [RMI] [SOCKET]");
		String choose = in.nextLine();
		Client client = new Client(choose);
		//costruzione evento da inviare EventCreator.createEvent(client.in.nextLine())
		client.startClient();
	}
}
