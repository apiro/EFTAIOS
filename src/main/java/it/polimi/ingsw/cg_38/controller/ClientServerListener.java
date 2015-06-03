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
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

public class ClientServerListener extends Observable implements Runnable {
	
	private Client client;
	private Scanner in;

	public ClientServerListener(Client client, Scanner in) {
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
		} else if(((NotifyEvent)event).getType().equals(NotifyEventType.Added)) {
			return;
		} else if(((NotifyEvent)event).getType().equals(NotifyEventType.notifyTurn)) {
			
			if(((EventNotifyTurn)event).getPlayerOfTurn().getName().equals(client.getPlayer().getName())) {
				client.setIsMyTurn(true);
			} else {
				System.out.println("NOT YOUR TURN !");
				client.setIsMyTurn(false);
				return;
			}
			
		}
		if(((NotifyEvent)event).getType().equals(NotifyEventType.Error)) {
			System.out.println("There was an error in processing your previous GameEvent ... RETRY !");
		} else if (((NotifyEvent)event).getType().equals(NotifyEventType.Moved) && client.getIsMyTurn()) {
			client.setPlayer(event.getGenerator());
			if(((EventMoved)event).getMoved().equals("Safe")) {
				System.out.println("You are in a SAFE sector !");
				this.setChanged();
				this.notifyObservers();
			} else if(((EventMoved)event).getMoved().equals("Dangerous")) {
				System.out.println("You are in a DANGEROUS sector ! Type draw or attack :[D] | [A] ?");
				if(in.nextLine().equals("D")) {
					client.getCommunicator().send(new EventDraw(client.getPlayer()));
				} else if (in.nextLine().equals("A")) {
					client.getCommunicator().send(new EventAttack(client.getPlayer(), client.getPlayer().getAvatar().getCurrentSector()));
				}
			} else if(((EventMoved)event).getMoved().equals("Hatch")) {
				System.out.println("You are in a HATCH sector ! Type [D]");
				while(!in.nextLine().equals("D")){}
				client.getCommunicator().send(new EventDraw(client.getPlayer()));
			}
			return;
		} else if (((NotifyEvent) event).getType().equals(NotifyEventType.Drown) && client.getIsMyTurn()) {
			
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
				}
			}
		} else if(client.getIsMyTurn() && !((NotifyEvent)event).getType().equals(NotifyEventType.notifyTurn)){
			client.setPlayer(event.getGenerator());
			this.setChanged();
			this.notifyObservers();
			return;
		}
		this.setChanged();
		this.notifyObservers();
		/*NotifyAction generated = (NotifyAction) NotifyActionCreator.createNotifyAction(event);
		generated.render(userInterface);*/
	}
}
