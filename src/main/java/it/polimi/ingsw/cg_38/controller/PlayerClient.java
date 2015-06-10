package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventAdren;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventAttackCard;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.gameEvent.EventLights;
import it.polimi.ingsw.cg_38.gameEvent.EventMove;
import it.polimi.ingsw.cg_38.gameEvent.EventSedat;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gameEvent.EventTeleport;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerClient {

	private ConcurrentLinkedQueue<Event> toSend;
	private ConcurrentLinkedQueue<Event> toProcess;
	private EventSubscribe evt;
	private Client client;
	private Scanner in = new Scanner(System.in);
	private String room;
	private it.polimi.ingsw.cg_38.model.Player player;
	private String connection;
	private boolean clientAlive = true;
	private Map map;

	public PlayerClient() {
		this.initPlayer();
	}

	public void initPlayer() {
		toProcess = new ConcurrentLinkedQueue<Event>();
		toSend = new ConcurrentLinkedQueue<Event>();
		connection = this.askForTypeOfConnection();
		evt = this.askForEventSubscribe();
		
		client = Client.clientCreator(connection, toSend, toProcess, evt);
		Thread t = new Thread(client, "GameEventSender");
		t.start();
	}

	public String askForTypeOfConnection() {
		System.err.println("WELCOME TO THE GAME !\n");
		System.out.println("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [Socket] : ");
		
		return in.nextLine();
	}

	public EventSubscribe askForEventSubscribe() {
		System.out.println("INSERT  : \n\t1) YOUR USERNAME: \n\t2) THE ROOM YOU WANT TO ACCESS : \n\t3) THE MAP NAME: ");
		String name = in.nextLine();
		room = in.nextLine();
		String map = in.nextLine();
		this.player = new Player(name);
		EventSubscribe evtSub = new EventSubscribe(player, room, map);
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Connecting with the server... ");
		return evtSub;
	}
	
	public void run() {
		while(clientAlive) {
			Event msg = toProcess.poll();
			if(msg != null) {
				this.process(msg);
				/*this.loadInterface();*/
			}
		}
	}

	public void loadInterface() {
		System.out.println("Starting Game : Loading the map ...");
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
		System.out.println("\nHere are your movements : \n");
		int i = 0;
		for(Movement mv:player.getAvatar().getMyMovements()) {
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
		
			if(command.equals("M")) {
				
				Sector toMove = this.askForMoveCoordinates(in);
				this.toSend.add(new EventMove(player, toMove));
				
			} else if (command.equals("D")) {
				
				this.toSend.add(new EventDraw(player));
				
			} else if (command.equals("A")) {
				
				Sector toMove = this.askForMoveCoordinates(in);
				this.toSend.add(new EventAttack(player, toMove));
				
			} else if (command.equals("U")) {
				
				System.out.println("----------------------------------------------------------------------");
				System.out.println("Which one ? type the number ... ");
				int j = 1;
				for(ObjectCard card:player.getAvatar().getMyCards()) {
					System.out.println(j + ")" + card.getType() + "\n");
					j++;
				}
				int cardSelected = Integer.parseInt(in.nextLine());
				
				try {
					
					if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Adrenaline)) {
						
						this.toSend.add(new EventAdren(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Attack)) {
						
						Sector toMove = this.askForMoveCoordinates(in);
						this.toSend.add(new EventAttackCard(player, player.getAvatar().getMyCards().get(cardSelected), toMove));
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Defense)) {
						
						System.out.println("---> You can't use defense card !");
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Sedatives)) {
						
						this.toSend.add(new EventSedat(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if(player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.SpotLight)) {
						
						Sector toMove = this.askForMoveCoordinates(in);
						this.toSend.add(new EventLights(player, toMove, player.getAvatar().getMyCards().get(cardSelected)));
						
					} else if (player.getAvatar().getMyCards().get(cardSelected).getType().equals(ObjectCardType.Teleport)) {
						
						this.toSend.add(new EventTeleport(player, player.getAvatar().getMyCards().get(cardSelected)));
						
					}
					
				} catch(IndexOutOfBoundsException e) {
					System.out.println("NO CARDS, RETRY !");
				}
				
				
			} else if (command.equals("F")) {
				this.toSend.add(new EventFinishTurn(player));
			} else {
				System.out.println("ERROR IN TYPING ... RETRY !\n");
				this.loadInterface();
			}
	}

	public Sector askForMoveCoordinates(Scanner in) {
		System.out.println("----------------------------------------------------------------------");
		System.out.println("Inserire la riga :");
    	int userRow = Integer.parseInt(in.nextLine());
    	System.out.println("Inserire la colonna :");
    	int userCol = Integer.parseInt(in.nextLine());
    	Sector toMove = map.searchSectorByCoordinates(userRow, userCol);
    	System.out.println("----------------------------------------------------------------------");
    	return toMove;
	}

	public void process(Event msg) {
		if(msg instanceof EventAddedToGame || msg instanceof EventNotifyEnvironment) {
			System.out.println(msg);
		} else {
			this.loadInterface();
		}
	}
	
}
