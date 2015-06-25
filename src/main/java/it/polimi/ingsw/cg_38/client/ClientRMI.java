package it.polimi.ingsw.cg_38.client;

import it.polimi.ingsw.cg_38.controller.connection.RMICommunicator;
import it.polimi.ingsw.cg_38.controller.connection.RMIRegistrationInterface;
import it.polimi.ingsw.cg_38.controller.connection.RMIRemoteObjectDetails;
import it.polimi.ingsw.cg_38.controller.connection.RMIRemoteObjectInterface;
import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventSubscribe;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Queue;

public class ClientRMI extends Client implements Runnable {

	/**
	 * QUESTO OGGETTO INVIA AL SERVER I MESSAGGI CHE TROVA NELLA SUA CODA.
	 * */
	
	private Registry registry;
	private EventSubscribe evt;
	private Boolean clientAlive = true;
	private Queue<Event> toSend;
	private Queue<Event> toProcess;
	private Logger logger = new LoggerCLI();

	public ClientRMI(Queue<Event> toSend, Queue<Event> toProcess, EventSubscribe evt) {
		this.evt = evt;
		this.toSend = toSend;
		this.toProcess = toProcess;
		try {
			this.initClientRMI();
		} catch (RemoteException e) {
			logger.print("Problems with the RMI connection ! Check if the server is online ...");
		}
	}
	
	private void initClientRMI() throws RemoteException {

		System.setProperty("java.rmi.server.hostname",this.host);
		registry = LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.RMI_PORT);
		
		RMIRegistrationInterface game = null;
		try {
			game = (RMIRegistrationInterface) registry.lookup("REGISTRATIONVIEW");
		} catch (NotBoundException e) {
			logger.print("Can't reach the remote object registration view ...");
		}
		
		logger.print("RMI Connection Established !");
		

		RMIRemoteObjectInterface clientView = new ClientView(toProcess);
		RMIRemoteObjectInterface serverView = game.register(clientView, evt);
		
		communicator = new RMICommunicator(serverView);
	}

	@Override
	public void run() {
		while(clientAlive) {
			synchronized(toSend) {
				Event msg = toSend.poll();
				if(msg != null) {
					try {
						communicator.send(msg);
					} catch (IOException e) {
						logger.print("A client is probably disconnected ...");
					}
				}
			}
		}
		try {
			communicator.closeCommunicator();
		} catch (RemoteException e) {
			logger.print("Can't close the communicator ... ");
		}
	}
}
