package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.model.Player;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketCommunicator implements Communicator {

	private Socket socket;
	private Scanner socketIn;
	private PrintWriter socketOut;
	
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

	public void send(Event evt) {
		//SERVER INVIA UN NOTIFYEVENT AL CLIENT
		socketOut.println(evt.toString());
		socketOut.flush();
	}
		
	public SocketCommunicator(Socket socket) throws IOException {
		this.setSocket(socket);;
		this.setSocketIn(new Scanner(socket.getInputStream()));
		this.setSocketOut(new PrintWriter(socket.getOutputStream()));
	}
	
	public Event addSubscriber() {
		//SERVER SI METTE IN RICEZIONE DI UN EVENTSUBSCRIBE DAL CLIENT
		return this.recieveEvent();
	}

	public Event recieveEvent() {
		//SERVER SI METTE IN RICEZIONE DI UN GAMEEVENT DAL CLIENT
		Event evt = new Event(new Player("test"));
		String toDecode = this.getSocketIn().next();
		System.out.println(toDecode);
		return evt;
	}
}
