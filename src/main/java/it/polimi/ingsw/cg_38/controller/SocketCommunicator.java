package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketCommunicator implements Communicator {

	private int port;
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

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
		Socket socket;
		try {
			socket = new Socket("localhost", port);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			this.setOutputStream(out);
			this.setInputStream(in);
			
			System.out.println("SOCKET Connection Established !");
			
			this.getOutputStream().writeObject(evt);
			this.getOutputStream().flush();
			
			System.out.println("Sending ... : " + evt.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
		
	public SocketCommunicator(int port) {
		/**
		 * è il costruttore per chi deve inviare un evento creando un socket alla porta passata
		 * */
		//creo il communicator con una porta: se la porta che passo è quella del server allora
		//il communicator sarà utilizzato dal client, se la porta che passo è di un client allora
		//il communicator sarà utilizzato dal server per inviare eventi di notifica.
		this.setPort(port);
	}
	
	public SocketCommunicator(Socket socket) {
		/**
		 * è il costruttore per chi deve ricevere un evento SUL socket passato
		 * */
		this.setSocket(socket);
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
