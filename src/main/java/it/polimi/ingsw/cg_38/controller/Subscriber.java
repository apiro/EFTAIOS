package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Subscriber implements Runnable {

	private int portPubSub = 3111;
	private String host = "127.0.0.1";
	private ConcurrentLinkedQueue<Event> toProcess;
	private Boolean clientAlive = true;
	private EventSubscribe evt;
	private Communicator communicator;
	
	public Subscriber(EventSubscribe evt, ConcurrentLinkedQueue<Event> toProcess) {
		this.toProcess = toProcess;
		this.evt = evt;
		this.initSubscriber();
	}
	
	public void initSubscriber() {
		Socket socketPubSub;
		/*ObjectOutputStream out = null;
		ObjectInputStream in = null;*/
		
		try {
			socketPubSub = new Socket(host, portPubSub);
			communicator = new SocketCommunicator(socketPubSub);
			System.out.println("Creating a socket with the PUB/SUB serverSocket !");
			((SocketCommunicator)communicator).initCommunicator();
			System.out.println("Sending " + evt.toString() + " ...");
			((SocketCommunicator)communicator).getOutputStream().writeObject(evt);
			((SocketCommunicator)communicator).setOutputStream(null);
		} catch (IOException e) {
			System.out.println("Problems with socket connection ! Check if the server is online ...");
		}
	}
	
	@Override
	public void run() {
		while(clientAlive) {
			try {
				Event notEvt = communicator.recieveEvent();
				if(notEvt != null) {
					toProcess.add(notEvt);
					synchronized(toProcess) {
						toProcess.notify();
					}
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}
