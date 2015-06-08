package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketCommunicator implements Communicator {

	//OGNI VOLTA CHE UN CLIENT VUOLE INVIARE EVENTI DI GIOCO DEVE CREARE UN COMMUNICATOR NUOVO
	//CREO IL SOCKET PRIMA, POI CREO UN COMMUNICATOR E INVIO
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
		//client o server inviano un evento
		try {
			this.getOutputStream().writeObject(evt);
			this.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Sending ... : " + evt.toString());
		}
	}
		
	public void initCommunicator() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(this.getSocket().getOutputStream());
		out.flush();
		ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream());
		this.setOutputStream(out);
		this.setInputStream(in);
	}
	
	public SocketCommunicator(Socket socket) {
		/**
		 * è il costruttore per chi deve ricevere un evento SUL socket passato
		 * */
		this.setSocket(socket);
		try {
			this.initCommunicator();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("SOCKET Connection Established !");
	}
	
	public Event recieveEvent() {
		//SERVER SI METTE IN RICEZIONE DI UN GAMEEVENT DAL CLIENT
		
		//la receive invece deve creare un thread per gestire i 
		//dati ricevuti
		
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
