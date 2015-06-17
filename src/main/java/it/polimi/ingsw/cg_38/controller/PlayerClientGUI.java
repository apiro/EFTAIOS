package it.polimi.ingsw.cg_38.controller;

import java.util.concurrent.ConcurrentLinkedQueue;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.gui.GUI;
import it.polimi.ingsw.cg_38.model.Map;

public class PlayerClientGUI implements PlayerClientInterface {

	private ConcurrentLinkedQueue<Event> toSend;
	private ConcurrentLinkedQueue<Event> toProcess;
	/*private EventSubscribe evt;*/
	private Client client;
	private String room;
	private PlayerClientState playerClientState;
	private it.polimi.ingsw.cg_38.model.Player player;
	private String connection;
	private Boolean isMyTurn = false;
	private Boolean clientAlive = true;
	private Boolean isInterfaceBlocked = true;
	private Map map;
	private Thread gameEventSender;
	
	public PlayerClientGUI() {
		this.initPlayer();
	}

	public void initPlayer() {
		/*playerClientState = PlayerClientState.init;
		toProcess = new ConcurrentLinkedQueue<Event>();
		toSend = new ConcurrentLinkedQueue<Event>();
		connection = this.askForTypeOfConnection();
		evt = this.askForEventSubscribe();
		client = Client.clientCreator(connection, toSend, toProcess, evt);
		gameEventSender = new Thread(client, "GameEventSender");
		gameEventSender.start();*/
		GUI gui = new GUI();
		
	}
	/*
	public EventSubscribe askForEventSubscribe() {
		
		EventSubscribe evtSub = null;
		
		return evtSub;
	}*/

	public void run() {
		while(clientAlive) {
			Event msg = toProcess.poll();
			if(msg != null) {
				this.process(msg);
			}
		}
	}
	
	public void process(Event msg) {
		/*System.out.println("----------------------------------------------------------------------\n");
		System.err.println("Recieving " + msg.toString() + " ...\n");
		NotifyAction action = (NotifyAction)NotifyActionCreator.createNotifyAction(msg);
		GameEvent gamEvt = null;
		if(action.isPossible(this)) {
			gamEvt = action.render(this);
			if(gamEvt != null) {
				if(gamEvt instanceof EventContinue) return;
				this.toSend.add(gamEvt);
			} else { 
				if(!isInterfaceBlocked) {
					this.loadInterface();
				} else return;
			}
		} else {
			System.out.println("Error in parsing the notifyEvent ...");
		}*/
	}

	@Override
	public String askForTypeOfConnection() {
		return null;
	}

	@Override
	public EventSubscribe askForEventSubscribe() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
