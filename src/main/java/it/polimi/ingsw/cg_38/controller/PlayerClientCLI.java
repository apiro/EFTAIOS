package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventAdrenaline;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventSpotLight;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.gameEvent.EventSedatives;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.gameEvent.EventContinue;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.notifyAction.NotifyAction;
import it.polimi.ingsw.cg_38.notifyAction.NotifyActionCreator;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerClientCLI implements PlayerClient {

	private ConcurrentLinkedQueue<Event> toSend;
	private ConcurrentLinkedQueue<Event> toProcess;
	private EventSubscribe evt;
	private Client client;
	private Scanner in = new Scanner(System.in);
	private String room;
	private PlayerClientState playerClientState;
	private it.polimi.ingsw.cg_38.model.Player player;
	private String connection;
	private Boolean isMyTurn = false;
	private Boolean clientAlive = true;
	private Boolean isInterfaceBlocked = true;
	private Map map;
	private Thread gameEventSender;
	private Logger logger = new LoggerCLI();
	
	public PlayerClientCLI() {
		this.init();
	}

	public void init() {
		playerClientState = PlayerClientState.init;
		toProcess = new ConcurrentLinkedQueue<Event>();
		toSend = new ConcurrentLinkedQueue<Event>();
		connection = this.askForTypeOfConnection();
		evt = this.askForEventSubscribe();
		this.startSender();
	}

	public void startSender() {
		client = Client.clientCreator(connection, toSend, toProcess, evt);
		gameEventSender = new Thread(client, "GameEventSender");
		gameEventSender.start();
	}

	public String askForTypeOfConnection() {
		logger.print("WELCOME TO THE GAME !\n");
		logger.print("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [Socket] : ");
		
		return in.nextLine();
	}

	public EventSubscribe askForEventSubscribe() {
		logger.print("INSERT  : \n\t1) YOUR USERNAME: \n\t2) THE ROOM YOU WANT TO ACCESS : \n\t3) THE MAP NAME: ");
		String name = in.nextLine();
		room = in.nextLine();
		String map = in.nextLine();
		this.player = new Player(name);
		EventSubscribe evtSub = new EventSubscribe(player, room, map);
		logger.print("----------------------------------------------------------------------");
		logger.print("Connecting with the server... ");
		playerClientState = PlayerClientState.connecting;
		return evtSub;
	}
	
	public PlayerClientState getPlayerClientState() {
		return playerClientState;
	}

	public void setPlayerClientState(PlayerClientState playerClientState) {
		this.playerClientState = playerClientState;
	}

	public it.polimi.ingsw.cg_38.model.Player getPlayer() {
		return player;
	}
	
	public void setPlayer(it.polimi.ingsw.cg_38.model.Player player) {
		this.player = player;
	}

	public Boolean getIsMyTurn() {
		return isMyTurn;
	}

	public void setIsMyTurn(Boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}
	
	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}
	
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
		/*System.out.println("\t 2) DRAW - D\n");
		System.out.println("\t 3) ATTACK - A\n");*/
		logger.print("\t 2) USE CARD - U\n");
		logger.print("\t 3) FINISH TURN - F\n");
		logger.print("----------------------------------------------------------------------");
		String command = in.nextLine();
		
			if(command.equals("M")) {
				
				Sector toMove = this.askForMoveCoordinates();
				this.toSend.add(new EventMove(player, toMove));
				
			} else if (command.equals("U")) {
				
				/*if(player.getAvatar() instanceof Human) {*/
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
					
					this.useCard(cardSelected);
				/*} else {
					System.out.println("You are an Alien and you can't use Cards !");
				}*/
				
			} else if (command.equals("F")) {
				this.toSend.add(new EventFinishTurn(player));
			} else {
				logger.print("ERROR IN TYPING ... RETRY !\n");
				this.loadInterface();
			}
	}

	private void useCard(int cardSelected) {
		try {
			
			if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Adrenaline)) {
				
				this.toSend.add(new EventAdrenaline(player, player.getAvatar().getMyCards().get(cardSelected)));
				
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.AttackCard)) {
				
				this.toSend.add(new EventAttackCard(player, player.getAvatar().getMyCards().get(cardSelected)));
				
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Defense)) {
				
				logger.print("---> You can't use defense card !");
				this.getPlayer().getAvatar().getMyCards().remove(cardSelected);
				this.loadInterface();
				
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Sedatives)) {
				
				this.toSend.add(new EventSedatives(player, player.getAvatar().getMyCards().get(cardSelected)));
				
			} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SpotLight)) {
				
				Sector toMove = this.askForMoveCoordinates();
				this.toSend.add(new EventSpotLight(player, toMove, player.getAvatar().getMyCards().get(cardSelected)));
				
			} else if (player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Teleport)) {
				
				this.toSend.add(new EventTeleport(player, player.getAvatar().getMyCards().get(cardSelected)));
				
			}
			
		} catch(IndexOutOfBoundsException e) {
			logger.print("The requested card doesn't exist !");
			this.loadInterface();
		}
	}

	public Sector askForMoveCoordinates() {
		Scanner in = new Scanner(System.in);
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

	public void run() {
		while(clientAlive) {
			Event msg = toProcess.poll();
			if(msg != null) {
				this.process(msg);
			}
		}
	}
	
	public void process(Event msg) {
		logger.print("----------------------------------------------------------------------\n");
		logger.print("Recieving " + msg.toString() + " ...\n");
		NotifyAction action = (NotifyAction)NotifyActionCreator.createNotifyAction(msg);
		GameEvent gamEvt = null;
		if(action.isPossible(this)) {
			gamEvt = action.render(this);
			if(gamEvt != null) {
				if(gamEvt instanceof EventContinue) return;
				this.toSend.add(gamEvt);
			} else { 
				if(!isInterfaceBlocked/* && toProcess.size() == 0*/) {
					this.loadInterface();
				} else return;
			}
		} else {
			logger.print("Error in parsing the notifyEvent ...");
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

	public void setIsInterfaceBlocked(Boolean isInterfaceBlocked) {
		this.isInterfaceBlocked = isInterfaceBlocked;
	}

	public void closeClient() {
		this.gameEventSender.interrupt();
	}

	public Logger getLogger() {
		return logger;
	}

	@Override
	public void updateCards() {
		
		int i = 3;
		if(player.getAvatar().getMyCards().size() == 4) {
			logger.print("You have drown the 4th card !Choose the card you wanna use !");
			int cardSelected = Integer.parseInt(in.nextLine());
			this.useCard(cardSelected);
		}
	}

	@Override
	public void updateMovements() {
		
	}

	@Override
	public void setClientAlive(Boolean b) {
		this.clientAlive = b;
	}
	
}
