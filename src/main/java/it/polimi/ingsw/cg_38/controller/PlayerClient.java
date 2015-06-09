package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.model.Player;

import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PlayerClient {

	private ConcurrentLinkedQueue<Event> toProcess;
	private EventSubscribe evt;
	private Scanner in = new Scanner(System.in);
	private String room;
	private it.polimi.ingsw.cg_38.model.Player player;

	public PlayerClient() {
		this.initPlayer();
	}

	private void initPlayer() {
		evt = this.askForEventSubscribe();
		new Subscriber(evt , toProcess);
	}

	private EventSubscribe askForEventSubscribe() {
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
	
}
