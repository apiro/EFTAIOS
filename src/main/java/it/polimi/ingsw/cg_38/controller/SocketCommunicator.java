package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SocketCommunicator implements Communicator {

	private Socket socket;
	private Scanner socketIn;
	private PrintWriter socketOut;
	private ConcurrentLinkedQueue<GameEvent> eventsToProcess;
	private ConcurrentLinkedQueue<NotifyEvent> eventsToSend;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Scanner getSocketIn() {
		return socketIn;
	}

	public void setSocketIn(Scanner socketIn) {
		this.socketIn = socketIn;
	}

	public PrintWriter getSocketOut() {
		return socketOut;
	}

	public void setSocketOut(PrintWriter socketOut) {
		this.socketOut = socketOut;
	}

	public ConcurrentLinkedQueue<GameEvent> getEventsToProcess() {
		return eventsToProcess;
	}

	public void setEventsToProcess(ConcurrentLinkedQueue<GameEvent> eventsToProcess) {
		this.eventsToProcess = eventsToProcess;
	}

	public ConcurrentLinkedQueue<NotifyEvent> getEventsToSend() {
		return eventsToSend;
	}

	public void setEventsToSend(ConcurrentLinkedQueue<NotifyEvent> eventsToSend) {
		this.eventsToSend = eventsToSend;
	}

	public void send(Event evt) {
		//SERVER INVIA UN NOTIFYEVENT AL CLIENT
		socketOut.println(evt.toString());
		socketOut.flush();
	}
		
	public SocketCommunicator(Socket socket, ConcurrentLinkedQueue<GameEvent> toDispatch, ConcurrentLinkedQueue<NotifyEvent> toDistribute) throws IOException {
		this.setSocket(socket);;
		this.setSocketIn(new Scanner(socket.getInputStream()));
		this.setSocketOut(new PrintWriter(socket.getOutputStream()));
		this.setEventsToProcess(toDispatch);
		this.setEventsToSend(toDistribute);
	}
	
	public Event addSubscriber() {
		//SERVER SI METTE IN RICEZIONE DI UN EVENTSUBSCRIBE DAL CLIENT
		return this.recieveEvent();
	}

	public Event recieveEvent() {
		//SERVER SI METTE IN RICEZIONE DI UN GAMEEVENT DAL CLIENT
		Event evt = null;
		String toDecode = this.getSocketIn().next();
		return evt;
	}
}
