package it.polimi.ingsw.cg_38.controller.action;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.Communicator;
import it.polimi.ingsw.cg_38.controller.ServerController;
import it.polimi.ingsw.cg_38.controller.SocketCommunicator;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribeSocket;

public class SubscribeSocket extends Subscribe {

	private String HOST = "localhost";
	private int PORT;
	
	public int getPORT() {
		return PORT;
	}

	public void setPORT(int PORT) {
		this.PORT = PORT;
	}

	public SubscribeSocket(GameEvent evt){
		super((EventSubscribeSocket)evt);
		this.setPORT(((EventSubscribeSocket)evt).getPORT());
	}

	public String getHOST() {
		return HOST;
	}

	@Override
	public NotifyEvent perform(ServerController server) throws ParserConfigurationException, Exception {

		Communicator c = new SocketCommunicator(this.getPORT());
		
		/*System.out.println("Creating a socket with the client !");
		Socket socket = new Socket(this.getHOST(), this.getPORT());
		
		System.out.println("SOCKET Connection Established !");
		Communicator c = new SocketCommunicator(socket);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());;
		
		((SocketCommunicator) c).setOutputStream(out);
		((SocketCommunicator) c).setInputStream(in);*/
		
		return super.generalEventGenerator(c, server);
	}
	
}
