package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.gameEvent.EventAdren;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventLights;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.gameEvent.EventSedat;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeRMI;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

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
	private Map map;
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	private String room;
	private Boolean clientAlive = false;
	private ClientSendingInterface userCommands;
	private Boolean isMyTurn;
	
	public Boolean getIsMyTurn() {
		return isMyTurn;
	}

	public void setIsMyTurn(Boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

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
		userInterface = new CLIView(this);
		System.out.println("INSERT  : \n\t1) YOUR USERNAME: \n\t2) THE ROOM YOU WANT TO ACCESS : \n\t3) THE MAP NAME: ");
		String name = in.nextLine();
		room = in.nextLine();
		String map = in.nextLine();
		this.player = new Player(name);
		EventSubscribe evt = null;
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Connecting with the server... ");
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
				return;
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
		System.err.println("Recieving " + event.toString() + " ...\n");
		if(((NotifyEvent)event).getType().equals(NotifyEventType.notifyTurn)) {
			this.setIsMyTurn(true);
		} else if(((NotifyEvent)event).getType().equals(NotifyEventType.environment)) {
			this.setClientAlive(true);
			this.setMap(((EventNotifyEnvironment)event).getMap());
		}
		/*NotifyAction generated = (NotifyAction) NotifyActionCreator.createNotifyAction(event);
		generated.render(userInterface);*/
	}
	
	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException, AlreadyBoundException{
		Scanner in = new Scanner(System.in);
		System.err.println("WELCOME TO THE GAME !\n");
		System.out.println("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [SOCKET] : ");
		String choose = in.nextLine();
		if(choose.equals("Socket")) {
			 Client.setClientServerSocketPort(Integer.parseInt(in.nextLine()));
		}
		Client client = new Client(choose);
		client.setUserCommands(new ClientSendingInterface(client.communicator));
		
		ClientServerListener serverEventsAccepter = new ClientServerListener(client);
		serverEventsAccepter.start();
		//qui chiamo lo startClient che propone al giocatore gli eventi che può generare, una volta generato l'evento lo invia al server e si pone in attesa di una risposta
		//quando questa arriva ripropone le scelte cosi via.
		
		while(!client.clientAlive) {
			
		}
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Starting Game : Loading the map...");
		System.out.println("GAME MAP:\n");
		System.out.println("\n|_|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|");
		for(int i = 0; i < client.getMap().getHeight() ; i++) {
			System.out.print("|" + i + "|");
			for(int j = 0; j < client.getMap().getWidth() ; j++) {
				System.out.print(client.getMap().getTable().get(i).get(j).getName().substring(0, 1) + "|");
			}
			System.out.println("\n|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
		}
		
		while(client.getIsMyTurn()) {

			System.out.println("\nHere are your movements : \n");
			int i = 0;
			for(Movement mv:client.getPlayer().getAvatar().getMyMovements()) {
				System.out.println(i + ")" );
				System.out.println(mv.toString() + "   ");
			}
			System.out.println("----------------------------------------------------------------------");
			System.out.println("Inserisci il tipo di azione da compiere: \n");
    		System.out.println("\t 1) MOVE - M\n");
    		System.out.println("\t 2) DRAW - D\n");
    		System.out.println("\t 3) ATTACK - A\n");
    		System.out.println("\t 4) USE CARD - U\n");
    		System.out.println("\t 5) FINISH TURN - F\n");
    		System.out.println("----------------------------------------------------------------------");
    		String command = in.nextLine();
    		
    		if(command.equals("M")) {
    			
    			Sector toMove = client.askForMoveCoordinates(in);
    			client.communicator.send(new EventMove(client.getPlayer(), toMove));
    			
    		} else if (command.equals("D")) {
    			
    			client.communicator.send(new EventDraw(client.getPlayer()));
    			
    		} else if (command.equals("A")) {
    			
    			Sector toMove = client.askForMoveCoordinates(in);
    			client.communicator.send(new EventAttack(client.getPlayer(), toMove));
    			
    		} else if (command.equals("U")) {
    			
    			System.out.println("----------------------------------------------------------------------");
    			System.out.println("Which one ? type the number ... ");
    			int j = 1;
    			for(ObjectCard card:client.getPlayer().getAvatar().getMyCards()) {
    				System.out.println(j + ")" + card.getType() + "\n");
    				j++;
    			}
    			int cardSelected = Integer.parseInt(in.nextLine());
    			
    			if(client.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Adrenaline)) {
    				
    				client.communicator.send(new EventAdren(client.getPlayer(), client.getPlayer().getAvatar().getMyCards().get(cardSelected)));
    				
    			} else if(client.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Attack)) {
    				
    				Sector toMove = client.askForMoveCoordinates(in);
    				client.communicator.send(new EventAttackCard(client.getPlayer(), client.getPlayer().getAvatar().getMyCards().get(cardSelected), toMove));
    				
    			} else if(client.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Defense)) {
    				
    				System.out.println("---> You can't use defense card !");
    				
    			} else if(client.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Sedatives)) {
    				
    				client.communicator.send(new EventSedat(client.getPlayer(), client.getPlayer().getAvatar().getMyCards().get(cardSelected)));
    				
    			} else if(client.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SpotLight)) {
    				
    				Sector toMove = client.askForMoveCoordinates(in);
    				client.communicator.send(new EventLights(client.getPlayer(), toMove, client.getPlayer().getAvatar().getMyCards().get(cardSelected)));
    				
    			} else if (client.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Teleport)) {
    				
    				client.communicator.send(new EventTeleport(client.getPlayer(), client.getPlayer().getAvatar().getMyCards().get(cardSelected)));
    				
    			}
				
			} else if (command.equals("F")) {
    			client.communicator.send(new EventFinishTurn(client.getPlayer()));
        		System.out.println("Turno terminato !\n");
    		} else {
    			System.out.println("ERROR IN TYPING ... RETRY !\n");
    		}
		}
		
	}

	public Sector askForMoveCoordinates(Scanner in) {
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Inserire la riga :");
    	int userRow = Integer.parseInt(in.nextLine());
    	System.out.println("Inserire la colonna :");
    	int userCol = Integer.parseInt(in.nextLine());
    	Sector toMove = this.getMap().searchSectorByCoordinates(userRow, userCol);
    	System.out.println("----------------------------------------------------------------------");
    	return toMove;
	}
	
	public Player getPlayer() {
		return player;
	}
}
