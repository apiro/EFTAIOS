package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Subscriber implements Runnable {

	private int portPubSub = 3111;
	private String host = "127.0.0.1";
	private ConcurrentLinkedQueue<Event> queue;
	private Boolean clientAlive;
	private EventSubscribe evt;
	private Communicator communicator;
	
	public Subscriber(EventSubscribe evt, ConcurrentLinkedQueue<Event> queue) {
		this.queue = queue;
		this.evt = evt;
		this.initSubscriber();
	}
	
	public void initSubscriber() {
		Socket socketPubSub;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		try {
			socketPubSub = new Socket(host, portPubSub);
			communicator = new SocketCommunicator(socketPubSub);
			System.out.println("Creating a socket with the PUB/SUB serverSocket !");
			out = new ObjectOutputStream(socketPubSub.getOutputStream());
			out.flush();
			in = new ObjectInputStream(socketPubSub.getInputStream());
			
			out.writeObject(evt);
			out = null;
		} catch (IOException e) {
			System.out.println("Problems with socket connection !");
		}
	}
	
	@Override
	public void run() {
		while(clientAlive) {
			try {
				evt = (EventSubscribe)communicator.recieveEvent();
				queue.add(evt);
				synchronized(queue) {
					queue.notify();
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

}
