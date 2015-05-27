package it.polimi.ingsw.cg_38.controller.action;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.Communicator;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;

public class SubscribeSocket extends Subscribe {

	private Socket socket;
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public SubscribeSocket(GameEvent evt){
		super((EventSubscribeSocket)evt);
		this.setSocket(((EventSubscribeSocket)evt).getSocket());
	}

	@Override
	public NotifyEvent perform(ServerController server) throws ParserConfigurationException, Exception {

		Communicator c = new SocketCommunicator(this.getSocket());
		
		ObjectOutputStream out = new ObjectOutputStream(this.getSocket().getOutputStream());
		out.flush();
		ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream());
		
		((SocketCommunicator) c).setOutputStream(out);
		((SocketCommunicator) c).setInputStream(in);
		
		return super.generalEventGenerator(c, server);
	}
	
}
