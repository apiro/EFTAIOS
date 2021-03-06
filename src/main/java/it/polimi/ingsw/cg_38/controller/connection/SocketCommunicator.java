package it.polimi.ingsw.cg_38.controller.connection;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Implementazione Socket del Communicator. implementa i metodi di trasmissione con le regole di comunicazione di Socket
 * */
public class SocketCommunicator implements Communicator {

	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Logger logger = new LoggerCLI();
	
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

	@Override
	public void send(Event evt) throws IOException {
		this.getOutputStream().writeObject(evt);
		this.getOutputStream().reset();
		logger.print("Sending ... : " + evt.toString());
		this.getOutputStream().flush();
	}
		
	public void initCommunicator() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(this.getSocket().getOutputStream());
		out.flush();
		ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream());
		this.setOutputStream(out);
		this.setInputStream(in);
	}
	
	public SocketCommunicator(Socket socket) {

		this.setSocket(socket);
	}
	
	@Override
	public Event recieveEvent() {
		Event evt = null;
		try {
			evt = (Event)this.getInputStream().readObject();
		} catch (ClassNotFoundException e) {
			logger.print("Event class not found in socket parsing object from output stream ...");
		} catch (IOException e) {
			logger.print("Disconnected ... ");
			
		}
		return evt;
	}
	
	@Override
	public void closeCommunicator(){
		try {
			this.getOutputStream().close();
			this.getInputStream().close();
			this.getSocket().close();
		} catch (IOException e) {
			logger.print("Can't close the socket ...");
		}
		
	}
}
