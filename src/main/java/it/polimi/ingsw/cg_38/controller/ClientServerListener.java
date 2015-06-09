package it.polimi.ingsw.cg_38.controller;

import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.action.Draw;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.gameEvent.EventAttack;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseMySect;
import it.polimi.ingsw.cg_38.gameEvent.EventNoiseRandSect;
import it.polimi.ingsw.cg_38.model.HatchCard;
import it.polimi.ingsw.cg_38.model.HatchCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

public class ClientServerListener extends Observable implements Runnable {
	
	private ClientOld client;
	private Scanner in;

	public ClientServerListener(ClientOld client, Scanner in) {
		this.client = client;
		this.in = in;
		this.addObserver(client);
	}
	
	public void run() {
		while(true) {
			Event msg = client.getToProcess().poll();
			if(msg != null) {
				try {
					this.handleSentNotifyEvent(msg);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void handleSentNotifyEvent(Event event) throws RemoteException {
		System.err.println("Recieving " + event.toString() + " ...\n");
		/*NOTIFYACTION RELATED TO EVENTNOTIFYENVIRONMENT*/
		if(((NotifyEvent)event).getType().equals(NotifyEventType.environment)) {
			for(Player pl:((EventNotifyEnvironment)event).getMappingPlayerAvatar()) {
				if(pl.getName().equals(client.getPlayer().getName())) {
					client.setPlayer(pl);
					System.out.println("The server assigned you : " + client.getPlayer().getAvatar().getName() + "...");
				}
			}
			client.setMap(((EventNotifyEnvironment)event).getMap());
			System.out.println("----------------------------------------------------------------------");
			System.out.println("Starting Game : Waiting for the first turn message ...");
			return;
		} 
		/*NOTIFYACTION RELATED TO EVENTADDEDTOGAME*/
		else if(((NotifyEvent)event).getType().equals(NotifyEventType.Added)) {
			return;
		} 
		/*NOTIFYACTION RELATED TO EVENTNOTIFYTURN*/
		else if(((NotifyEvent)event).getType().equals(NotifyEventType.notifyTurn)) {
			
			if(((EventNotifyTurn)event).getPlayerOfTurn().getName().equals(client.getPlayer().getName())) {
				client.setIsMyTurn(true);
				this.setChanged();
				this.notifyObservers();
			} else {
				System.out.println("NOT YOUR TURN !");
				client.setIsMyTurn(false);
				return;
			}
			
		}
		/*NOTIFYACTION RELATED TO EVENTNOTIFYERROR*/
		if(((NotifyEvent)event).getType().equals(NotifyEventType.Error)) {
			System.out.println("There was an error in processing your previous GameEvent ... RETRY !");
			this.setChanged();
			this.notifyObservers();
		} 
		/*NOTIFYACTION RELATED TO EVENTMOVED*/
		else if (((NotifyEvent)event).getType().equals(NotifyEventType.Moved) && client.getIsMyTurn()) {
			client.setPlayer(event.getGenerator());
			if(((EventMoved)event).getMoved().equals("Safe")) {
				System.out.println("You are in a SAFE sector !");
				this.setChanged();
				this.notifyObservers();
			} else if(((EventMoved)event).getMoved().equals("Dangerous")) {
				System.out.println("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
				String com = in.nextLine();
				while(!com.equals("D") && !com.equals("A")) {
					System.out.println("Command not valid retry !");
					System.out.println("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
					com = in.nextLine();
				}
				
				if(com.equals("D")) {
					client.getCommunicator().send(new EventDraw(client.getPlayer()));
				} else if (com.equals("A")) {
					client.getCommunicator().send(new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector()));
				}
			} else if(((EventMoved)event).getMoved().equals("Hatch")) {
				System.out.println("You are in a HATCH sector ! Type [D]");
				while(!in.nextLine().equals("D")){}
				client.getCommunicator().send(new EventDraw(client.getPlayer()));
			}
			return;
		} 
		/*NOTIFYACTION RELATED TO EVENTDROWN*/
		else if (((NotifyEvent) event).getType().equals(NotifyEventType.Drown) && client.getIsMyTurn()) {
			
			client.setPlayer(event.getGenerator());
			if(((EventDrown)event).getDrown() instanceof SectorCard) {
				SectorCard card = ((SectorCard)((EventDrown)event).getDrown());
				if(card.getType().equals(SectorCardType.MySectorNoise)) {
					client.getCommunicator().send(new EventNoiseMySect(client.getPlayer()));
					return;
				} else if (card.getType().equals(SectorCardType.RandomSectorNoise)) {
					client.getCommunicator().send(new EventNoiseRandSect(client.getPlayer(), client.askForMoveCoordinates(in)));
					return;
				} else if (card.getType().equals(SectorCardType.Silence)) {
					this.setChanged();
					this.notifyObservers();
				}
			} else if (((EventDrown)event).getDrown() instanceof HatchCard) {
				HatchCard card = ((HatchCard)((EventDrown)event).getDrown());
				if(card.getColor().equals(HatchCardType.Green)) {
					//se sono umano
					System.out.println("WINNER");
					//send event of "player generator wins"
					//se sono alieno niente
				} else if(card.getColor().equals(HatchCardType.Red)) {
					//niente
					this.setChanged();
					this.notifyObservers();
				}
			}
		}
		/*NOTIFYACTION RELATED TO ALL OTHER EVENTATTACKED*/
		else if (((NotifyEvent)event).getType().equals(NotifyEventType.Attacked) && client.getIsMyTurn()) {
			client.setPlayer(event.getGenerator());
			for(Player pl:((EventAttacked)event).getKilled()){
				if(pl.getName().equals(client.getPlayer().getName())) {
					System.out.println("AN ALIEN KILLED YOU ! YOU LOOSE !");
					return;
				}
			}
			if(((EventAttacked)event).getGenerator().getName().equals(client.getPlayer().getName())) {
				System.out.println("You killed an Human ! You are powered!");
			}
		}
		/*NOTIFYACTION RELATED TO ALL OTHER NOTIFYEVENT*/
		else if(client.getIsMyTurn() && !((NotifyEvent)event).getType().equals(NotifyEventType.notifyTurn)){
			client.setPlayer(event.getGenerator());
			this.setChanged();
			this.notifyObservers();
			return;
		}
		/*NotifyAction generated = (NotifyAction) NotifyActionCreator.createNotifyAction(event);
		generated.render(userInterface);*/
	}
}
