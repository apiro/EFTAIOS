package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Turn;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketCommunicator implements Communicator {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	
	public ObjectInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ObjectInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ObjectOutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(ObjectOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void send(Event evt) {
		//SERVER INVIA UN NOTIFYEVENT AL CLIENT
		try {
			this.getOutputStream().writeObject(evt);
			this.getOutputStream().flush();
			
			System.out.println("Sending ... : " + evt.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
		
	public SocketCommunicator(Socket socket) throws IOException {
		this.setSocket(socket);
	}
	
	public Event recieveEvent() {
		//SERVER SI METTE IN RICEZIONE DI UN GAMEEVENT DAL CLIENT
		Event evt = null;
		try {
			evt = (Event)this.getInputStream().readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return evt;
	}
	
	public void closeSocket(){
		try {
			this.getOutputStream().close();
			this.getInputStream().close();
			this.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
