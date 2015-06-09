package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSocketCommunicator extends SocketCommunicator {

	public ServerSocketCommunicator(Socket socket) {
		super(socket);
		try {
			this.initCommunicator();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initCommunicator() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(this.getSocket().getOutputStream());
		out.flush();
		ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream());
		this.setOutputStream(out);
		this.setInputStream(in);
	}
	
	@Override
	public void send(Event evt) {
		try {
			this.getOutputStream().writeObject(evt);
			this.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Sending ... : " + evt.toString());
		}
	}

}
