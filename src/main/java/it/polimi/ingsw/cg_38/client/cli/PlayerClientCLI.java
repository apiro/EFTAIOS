package it.polimi.ingsw.cg_38.client.cli;

import it.polimi.ingsw.cg_38.client.Client;
import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.client.PlayerClientState;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyAction;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyActionCreator;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventADRENALINE;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventATTACKCARD;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventChat;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRejectCard;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventRetired;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSEDATIVES;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSPOTLIGHT;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventTELEPORT;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Sector;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Classe principale del Client connesso con CLI
 * */
public class PlayerClientCLI implements PlayerClient {

	/**
	 * coda di messaggi da inviare generati dalla gui
	 * */
	private Queue<Event> toSend;
	/**
	 * coda di messaggi da processare inviati dal server
	 * */
	private Queue<Event> toProcess;
	private EventSubscribe evt;
	/**
	 * Oggetto Runnable che serve per lanciare il thread di invio messaggi al server
	 * */
	private Client client;
	private Scanner in = new Scanner(System.in);
	private String room;
	private PlayerClientState playerClientState;
	private it.polimi.ingsw.cg_38.model.Player player;
	private String connection;
	private Boolean isMyTurn = false;
	private Boolean isInterfaceBlocked = true;
	private Map map;
	/**
	 * Thread che invia i messaggi al server. Indifferentemente ClientRMI o ClientSocket perche il loro funzionamento
	 * è mascherato dall'interfaccia comune Client
	 * */
	private Thread gameEventSender;
	/**
	 * Logger assegnato alla cli
	 * */
	private Logger logger = new LoggerCLI();
	private Logger loggerChat = new LoggerCLI();
	private Boolean clientAlive = true;
	
	public PlayerClientCLI(String connection , EventSubscribe evt){
		
		this.connection = connection;
		this.evt = evt;
		
		}
	
	public PlayerClientCLI() {
		this.init();
		this.run();
	}

	/**
	 * Inizializza i campi del client
	 * */
	@Override
	public void init() {
		playerClientState = PlayerClientState.INIT;
		toProcess = new ConcurrentLinkedQueue<Event>();
		toSend = new ConcurrentLinkedQueue<Event>();
		connection = this.askForTypeOfConnection();
		evt = this.askForEventSubscribe();
		this.startSender();
	}

	/**
	 * Metodo che fa partire il thread di invio messaggi di gioco al server
	 * */
	@Override
	public void startSender() {
		client = Client.clientCreator(connection, toSend, toProcess, evt);
		gameEventSender = new Thread(client, "GameEventSender");
		gameEventSender.start();
	}
	
	/**
	 * Richiede al user le coordinate della mossa o del rumore
	 * */
	public String askForTypeOfConnection() {
		logger.print("WELCOME TO THE GAME !\n");
		logger.print("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [Socket] : ");
		
		return in.nextLine();
	}

	/**
	 * Richiede allo user informazioni per generare l'evendo di subscribe
	 * */
	public EventSubscribe askForEventSubscribe() {
		logger.print("INSERT  : \n\t1) YOUR USERNAME: \n\t2) THE ROOM YOU WANT TO ACCESS : \n\t3) THE MAP NAME: ");
		logger.print("if you enter in an existing room type smth random");
		String name = in.nextLine();
		room = in.nextLine();
		String map = in.nextLine();
		this.player = new Player(name);
		EventSubscribe evtSub = new EventSubscribe(player, room, map);
		logger.print("----------------------------------------------------------------------");
		logger.print("Connecting with the server... ");
		playerClientState = PlayerClientState.CONNECTING;
		return evtSub;
	}
	
	/**
	 * Usato quando si vuole chiedere allo user che carta scartare a quando ha pescato la quarta
	 * */
	public String askForUseCardOrRejectCard() {
		String choice = logger.showAndRead("Do you wanna Use [U] or Reject [R] the selected Card ?", "");
		
		while(!("U").equals(choice) && !("R").equals(choice)) {
			choice = logger.showAndRead("Do you wanna Use [U] or Reject [R] the selected Card ?", "");
		}
		
		return choice;
	}
	
	@Override
	public PlayerClientState getPlayerClientState() {
		return playerClientState;
	}

	@Override
	public void setPlayerClientState(PlayerClientState playerClientState) {
		this.playerClientState = playerClientState;
	}

	@Override
	public it.polimi.ingsw.cg_38.model.Player getPlayer() {
		return player;
	}
	
	@Override
	public void setPlayer(it.polimi.ingsw.cg_38.model.Player player) {
		this.player = player;
	}

	public Boolean getIsMyTurn() {
		return isMyTurn;
	}

	@Override
	public void setIsMyTurn(Boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}
	
	@Override
	public Map getMap() {
		return map;
	}

	@Override
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Metodo importante che ricarica l'interfaccia stampando sulla CLI l'interfaccia di gioco.
	 * Questo metodo viene chiamato ogni volta che la gestione di un evento è terminata( per eventi che hanno token
	 * si aspetta che il token svanisca e poi si ricarica l'interfaccia) ed ha il compito di acquisire eventi
	 * di gioco dall giocatore.
	 * */
	public void loadInterface() {
		System.out.println("Loading the map ...");
		System.out.println("GAME MAP:\n");
		System.out.println("\n|_|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|");
		for(int i = 0; i < map.getHeight() ; i++) {
			System.out.print("|" + i + "|");
			for(int j = 0; j < map.getWidth() ; j++) {
				if((map.getTable().get(i).get(j).getCol() == player.getAvatar().getCurrentSector().getCol()) &&
						((map.getTable().get(i).get(j).getRow() == player.getAvatar().getCurrentSector().getRow()))) {
					System.err.print(map.getTable().get(i).get(j).getName().substring(0, 1) + "|");
				} else {
					System.out.print(map.getTable().get(i).get(j).getName().substring(0, 1) + "|");
				}
			}
			System.out.println("\n|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
		}
		logger.print("\nHere are your movements : \n");
		int i = 0;
		for(Movement mv:player.getAvatar().getMyMovements()) {
			logger.print(i + ")" );
			i++;
			logger.print(mv.toString() + "   ");
		}
		logger.print("----------------------------------------------------------------------");
		logger.print("Inserisci il tipo di azione da compiere: \n");
		logger.print("\t 1) MOVE - M\n");
		logger.print("\t 2) SEND CHAT MESSAGE - C\n");
		logger.print("\t 3) USE CARD - U\n");
		logger.print("\t 4) FINISH TURN - F\n");
		logger.print("\t 5) CLOSE CLIENT - CC\n");
		logger.print("----------------------------------------------------------------------");
		String command = in.nextLine();
		
			if(("M").equals(command)) {
				
				Sector toMove = this.askForMoveCoordinates();
				synchronized(this.toSend) {
					this.toSend.add(new EventMove(player, toMove));
				}
				
			} else if (("CC").equals(command)) {
				synchronized(this.toSend) {
					this.toSend.add(new EventRetired(player));
				}
			} else if (("C").equals(command)) {
				String chat = logger.showAndRead("Type the message ...", "");
				synchronized(this.toSend) {
					this.toSend.add(new EventChat(player, chat));
				}
				
			} else if (("U").equals(command)) {
				logger.print("----------------------------------------------------------------------");
				logger.print("Which one? type the number ...");
				int j = 1;
				for(ObjectCard card:player.getAvatar().getMyCards()) {
					logger.print(j + ")" + card.getType() + "\n");
					j++;
				}
				int cardSelected = 0;
				try {
					cardSelected = Integer.parseInt(in.nextLine())-1;
				} catch(NumberFormatException e) {
					logger.print("You typed a word not a number !");
					this.loadInterface();
				}
				if(("R").equals(this.askForUseCardOrRejectCard()) && cardSelected<player.getAvatar().getMyCards().size()) {
					this.toSend.add(new EventRejectCard(player, player.getAvatar().getMyCards().get(cardSelected)));
					player.getAvatar().getMyCards().remove(cardSelected);
				} else {
					this.useCard(cardSelected);
				}
			} else if (("F").equals(command)) {
				synchronized(this.toSend) {
					this.toSend.add(new EventFinishTurn(player));
				}
			} else {
				logger.print("ERROR IN TYPING ... RETRY !\n");
				this.loadInterface();
			}
	}

	private void useCard(int cardSelected) {
		try {
			
			if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.ADRENALINE)) {
				synchronized(this.toSend) {
					this.toSend.add(new EventADRENALINE(player, player.getAvatar().getMyCards().get(cardSelected)));
				}
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.ATTACKCARD)) {
				synchronized(this.toSend) {
					this.toSend.add(new EventATTACKCARD(player, player.getAvatar().getMyCards().get(cardSelected)));
				}
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.DEFENSE)) {
				
				logger.print("---> You can't use defense card !");
				this.getPlayer().getAvatar().getMyCards().remove(cardSelected);
				this.loadInterface();
				
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SEDATIVES)) {
				synchronized(this.toSend) {
					this.toSend.add(new EventSEDATIVES(player, player.getAvatar().getMyCards().get(cardSelected)));
				}
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SPOTLIGHT)) {
				
				Sector toMove = this.askForMoveCoordinates();
				synchronized(this.toSend) {
					this.toSend.add(new EventSPOTLIGHT(player, toMove, player.getAvatar().getMyCards().get(cardSelected)));
				}
			} else if (player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.TELEPORT)) {
				synchronized(this.toSend) {
					this.toSend.add(new EventTELEPORT(player, player.getAvatar().getMyCards().get(cardSelected)));
				}
			}
			player.getAvatar().getMyCards().remove(cardSelected);
			
		} catch(IndexOutOfBoundsException e) {
			logger.print("The requested card doesn't exist !");
			this.loadInterface();
		}
	}
	
	@Override
	public Sector askForMoveCoordinates() {
		try {
			logger.print("----------------------------------------------------------------------");
			logger.print("Inserire la riga :");
	    	int userRow = Integer.parseInt(in.nextLine());
	    	logger.print("Inserire la colonna :");
	    	int userCol = Integer.parseInt(in.nextLine());
	    	Sector toMove = map.searchSectorByCoordinates(userRow, userCol);
	    	logger.print("----------------------------------------------------------------------");
	    	return toMove;
		} catch (NumberFormatException e) {
			logger.print("Invalid input retry !");
			return askForMoveCoordinates();
		}
	}

	/**
	 * Metodo che prende un evento dalla coda ogni ciclo di while e lo processa. Dopo di che aggiorna le carte del
	 * giocatore per avere sempre una situazione aggiornata.
	 * */
	@Override
	public void run() {
		while(clientAlive) {
			Event msg = toProcess.poll();
			if(msg != null) {
				this.process(msg);
			}
		}
		logger.print("GoodBye !");
		Thread.currentThread().interrupt();
	}
	
	/**
	 * Metodo del client che processa l'evento che gli viene passato, ne genera l'azione di notifica e renderizza l'effetto 
	 * sull' interfaccia.
	 * @param msg evento da processare
	 * */
	@Override
	public void process(Event msg) {
		logger.print("----------------------------------------------------------------------\n");
		logger.print("Recieving " + msg.toString() + " ...\n");
		NotifyAction action = (NotifyAction)NotifyActionCreator.createNotifyAction(msg);
		GameEvent gamEvt = null;
		if(action.isPossible(this)) {
			gamEvt = action.render(this);
			if(gamEvt != null) {
				if(gamEvt instanceof EventContinue) 
					return;
				this.toSend.add(gamEvt);
			} else { 
				if(!isInterfaceBlocked/* && toProcess.size() == 0*/) {
					this.loadInterface();
				} else return;
			}
		} else {
			logger.print("Error in parsing the notifyEvent, you are DEAD or OUT OF TURN ...");
		}
	}

	public static void main(String[] args) {
		PlayerClientCLI player;
		Thread.currentThread().setName("Player-MainThread");
		player = new PlayerClientCLI();
		player.run();
	}
	
	public Boolean getIsInterfaceBlocked() {
		return isInterfaceBlocked;
	}

	@Override
	public void setIsInterfaceBlocked(Boolean isInterfaceBlocked) {
		this.isInterfaceBlocked = isInterfaceBlocked;
	}

	/**
	 * Termina il processo Client dopo 15 sec dalla sua chiamata
	 * */
	@Override
	public void closeClient() {
		logger.print("CLOSING CLIENT TERMINAL ...");
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			logger.print("Problems with interrupting process ...");
		}
		System.exit(0);
	}
	
	@Override
	public Logger getLogger() {
		return logger;
	}

	/**
	 * Richiama il metodo updateCards() della gui, in un ambiente protetto dato da SwingUtilities che permette di 
	 * lavorare dentro l'EDT
	 * */
	@Override
	public void updateCards() {
		
		if(player.getAvatar().getMyCards().size() == 4) {
			logger.print("You have drown the 4th card !Choose the card you wanna use !");
			int cardSelected = Integer.parseInt(in.nextLine());
			this.useCard(cardSelected);
		}
	}

	@Override
	public void setClientAlive(Boolean b) {
		this.clientAlive = b;
	}

	/**
	 * Richiama il metodo colora hatch della gui, in un ambiente protetto dato da SwingUtilities che permette di 
	 * lavorare dentro l'EDT
	 * */
	@Override
	public void paintHatch(Boolean bool, Sector sec) {
		
	}

	/**
	 * Richiama il metodo updateMovements() della gui, in un ambiente protetto dato da SwingUtilities che permette di 
	 * lavorare dentro l'EDT
	 * */
	@Override
	public void updateMovements() {
		
	}

	
	@Override public Logger getLoggerChat() {
		return loggerChat;
	}
}
