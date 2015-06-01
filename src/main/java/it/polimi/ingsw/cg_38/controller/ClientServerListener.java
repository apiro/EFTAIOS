package it.polimi.ingsw.cg_38.controller;

import java.rmi.RemoteException;
import java.util.Observable;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;

public class ClientServerListener extends Observable implements Runnable {
	
	private Client client;

	public ClientServerListener(Client client) {
		this.client = client;
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
		} else if(((NotifyEvent)event).getType().equals(NotifyEventType.Added)) {
			
		} else if(((NotifyEvent)event).getType().equals(NotifyEventType.notifyTurn)) {
			
			if(((EventNotifyTurn)event).getPlayerOfTurn().getName().equals(client.getPlayer().getName())) {
				client.setIsMyTurn(true);
				this.setChanged();
				this.notifyObservers();
			} else {
				System.out.println("NOT MY TURN !");
				client.setIsMyTurn(false);
			}
			
		}  else if(client.getIsMyTurn()){
			this.setChanged();
			this.notifyObservers();
		} else if(((NotifyEvent)event).getType().equals(NotifyEventType.Error)) {
			System.out.println("There was an error in processing your previous GameEvent ... RETRY !");
			this.setChanged();
			this.notifyObservers();
		}
		/*NotifyAction generated = (NotifyAction) NotifyActionCreator.createNotifyAction(event);
		generated.render(userInterface);*/
	}
}
