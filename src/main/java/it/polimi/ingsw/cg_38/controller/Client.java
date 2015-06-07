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
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Client implements Observer {

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
	private Boolean isMyTurn = false;
	
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
		this.setPlayer(new Player(name));
		EventSubscribe evt = null;
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Connecting with the server... ");
		if(s.equals("RMI")) {
			
			//NON INVIERO' PIU EVENTI DI SUBSCRIBE !
			
			System.setProperty("java.rmi.server.hostname",this.host);
			registry = LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.RMI_PORT);
			
			RMIRegistrationInterface game = null;
			game = (RMIRegistrationInterface) registry.lookup("REGISTRATIONVIEW");
			
			System.out.println("RMI Connection Established !");
			/*System.out.println(game.isLoginValid("albi"));
			System.out.println(game.isLoginValid("test"));*/
			
			System.out.println("Trying to REGISTER to the choosen Topic ! Wait ...");
			//prima che la partita parta sul server tutti i client che si vogliono connetter sono fermi qui 
			
			evt = new EventSubscribe(this.getPlayer(), room, map);
			RMIRemoteObjectInterface clientView = new ClientView(this.getToProcess());
			RMIRemoteObjectInterface serverView = game.register(clientView, evt);
			
			RMIRemoteObjectDetails clientPersonalView = new RMIRemoteObjectDetails("CLIENTVIEW" + name);
			
			try {
				registry.bind(clientPersonalView.getRMI_ID(), clientView);
			} catch (AlreadyBoundException e) {
				System.err.println("The username you choose is already in use. Please change it.");
				Client client = new Client("RMI"); 
				
			}
			
			this.communicator = new RMICommunicator(serverView);
			
			
		} else if (s.equals("Socket")) {
			
			/*this.startSocketEnvironment();*/
			System.out.println("Creating a socket with the server !");
			this.communicator = new SocketCommunicator(this.port);
			
			/*evt = new EventSubscribeSocket(this.getPlayer(), room, map, Client.getClientServerSocketPort());*/
		} else {
			System.err.println("Communication Protocol not supported. Please choose [RMI] or [Socket], to quit game write [QUIT] !");
			Client client = Client.initClient();
		}
		
		/*this.communicator.send(evt);*/
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
	
	public void trasmitGameEvent() throws RemoteException {
		Event evt = this.getToSend().poll();
		communicator.send(evt);
	}
	
	public static Client initClient() throws IOException, NotBoundException {
		Scanner in = new Scanner(System.in);
		System.err.println("WELCOME TO THE GAME !\n");
		System.out.println("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [Socket] : ");
		String choose = in.nextLine();
		if(choose.equals("Socket")) {
			 Client.setClientServerSocketPort(Integer.parseInt(in.nextLine()));
		}
		Client client = new Client(choose);
		client.setUserCommands(new ClientSendingInterface(client.communicator));
		
		ClientServerListener serverEventsAccepter = new ClientServerListener(client, in);
		Thread t = new Thread(serverEventsAccepter, "ServerEventsAccepterThread");
		t.start();
		
		return client;
	}
	
	public Communicator getCommunicator() {
		return communicator;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public static void main(String[] args) throws UnknownHostException, NotBoundException, IOException, AlreadyBoundException{
		Client client = Client.initClient();
		Thread.currentThread().setName("Client-" + client.getPlayer().getName() + "-MainThread");
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

	@Override
	public void update(Observable o, Object arg) {
		//è arrivato l'evento di inizio turno ! 
		System.out.println("Starting Game : Loading the map ...");
		System.out.println("GAME MAP:\n");
		System.out.println("\n|_|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|");
		for(int i = 0; i < this.getMap().getHeight() ; i++) {
			System.out.print("|" + i + "|");
			for(int j = 0; j < this.getMap().getWidth() ; j++) {
				if((this.getMap().getTable().get(i).get(j).getCol() == this.getPlayer().getAvatar().getCurrentSector().getCol()) &&
						((this.getMap().getTable().get(i).get(j).getRow() == this.getPlayer().getAvatar().getCurrentSector().getRow()))) {
					System.err.print(this.getMap().getTable().get(i).get(j).getName().substring(0, 1) + "|");
				} else {
					System.out.print(this.getMap().getTable().get(i).get(j).getName().substring(0, 1) + "|");
				}
			}
			System.out.println("\n|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
		}
		System.out.println("\nHere are your movements : \n");
		int i = 0;
		for(Movement mv:this.getPlayer().getAvatar().getMyMovements()) {
			System.out.println(i + ")" );
			i++;
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
		
		try {
			if(command.equals("M")) {
				
				Sector toMove = this.askForMoveCoordinates(in);
				this.communicator.send(new EventMove(this.getPlayer(), toMove));
				
			} else if (command.equals("D")) {
				
				this.communicator.send(new EventDraw(this.getPlayer()));
				
			} else if (command.equals("A")) {
				
				Sector toMove = this.askForMoveCoordinates(in);
				this.communicator.send(new EventAttack(this.getPlayer(), toMove));
				
			} else if (command.equals("U")) {
				
				System.out.println("----------------------------------------------------------------------");
				System.out.println("Which one ? type the number ... ");
				int j = 1;
				for(ObjectCard card:this.getPlayer().getAvatar().getMyCards()) {
					System.out.println(j + ")" + card.getType() + "\n");
					j++;
				}
				int cardSelected = Integer.parseInt(in.nextLine());
				
				try {
					
					if(this.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Adrenaline)) {
						
						this.communicator.send(new EventAdren(this.getPlayer(), this.getPlayer().getAvatar().getMyCards().get(cardSelected)));
						
					} else if(this.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Attack)) {
						
						Sector toMove = this.askForMoveCoordinates(in);
						this.communicator.send(new EventAttackCard(this.getPlayer(), this.getPlayer().getAvatar().getMyCards().get(cardSelected), toMove));
						
					} else if(this.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Defense)) {
						
						System.out.println("---> You can't use defense card !");
						
					} else if(this.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Sedatives)) {
						
						this.communicator.send(new EventSedat(this.getPlayer(), this.getPlayer().getAvatar().getMyCards().get(cardSelected)));
						
					} else if(this.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SpotLight)) {
						
						Sector toMove = this.askForMoveCoordinates(in);
						this.communicator.send(new EventLights(this.getPlayer(), toMove, this.getPlayer().getAvatar().getMyCards().get(cardSelected)));
						
					} else if (this.getPlayer().getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Teleport)) {
						
						this.communicator.send(new EventTeleport(this.getPlayer(), this.getPlayer().getAvatar().getMyCards().get(cardSelected)));
						
					}
					
				} catch(IndexOutOfBoundsException e) {
					System.out.println("NO CARDS, RETRY !");
				}
				
				
			} else if (command.equals("F")) {
				this.communicator.send(new EventFinishTurn(this.getPlayer()));
			} else {
				System.out.println("ERROR IN TYPING ... RETRY !\n");
				this.update(o, arg);
			} 
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
