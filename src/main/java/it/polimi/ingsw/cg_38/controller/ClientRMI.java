package it.polimi.ingsw.cg_38.controller;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.gameEvent.EventSubscribe;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClientRMI extends Client implements Runnable {

	/**
	 * QUESTO OGGETTO INVIA AL SERVER I MESSAGGI CHE TROVA NELLA SUA CODA.
	 * */
	
	private Registry registry;
	private EventSubscribe evt;
	private Boolean clientAlive = true;
	private ConcurrentLinkedQueue<Event> toSend;
	private ConcurrentLinkedQueue<Event> toProcess;

	public ClientRMI(ConcurrentLinkedQueue<Event> toSend, ConcurrentLinkedQueue<Event> toProcess, EventSubscribe evt) {
		this.evt = evt;
		this.toSend = toSend;
		this.toProcess = toProcess;
		try {
			this.initClientRMI();
		} catch (RemoteException e) {
			System.out.println("Problems with the RMI connection ! Check if the server is online ...");
		}
	}
	
	private void initClientRMI() throws RemoteException {

		System.setProperty("java.rmi.server.hostname",this.host);
		registry = LocateRegistry.getRegistry("localhost", RMIRemoteObjectDetails.RMI_PORT);
		
		RMIRegistrationInterface game = null;
		try {
			game = (RMIRegistrationInterface) registry.lookup("REGISTRATIONVIEW");
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("RMI Connection Established !");
		/*System.out.println(game.isLoginValid("albi"));
		System.out.println(game.isLoginValid("test"));*/
		

		RMIRemoteObjectInterface clientView = new ClientView(toProcess);
		RMIRemoteObjectInterface serverView = game.register(clientView, evt);
		
		communicator = new RMICommunicator(serverView);
	}

	@Override
	public void run() {
		while(clientAlive) {
			Event msg = toSend.poll();
			if(msg != null) {
				try {
					communicator.send(msg);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			communicator.closeCommunicator();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}