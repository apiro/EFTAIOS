package it.polimi.ingsw.cg_38.controller.connection;

import java.rmi.RemoteException;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;


/**
 * Implementazione RMI del Communicator. implementa i metodi di trasmissione con le regole di comunicazione di RMI
 * */
public class RMICommunicator implements Communicator {

	private RMIRemoteObjectInterface remoteView;
	private Logger logger = new LoggerCLI();
	
	public RMICommunicator(RMIRemoteObjectInterface remoteView) {
		this.remoteView = remoteView;
		/*System.out.println("Creating a new RMI communicator !");*/
	}
	
	@Override
	public void send(Event evt) throws RemoteException {
		//CHI CHIAMA QUESTO METODO TRASMETTE UN EVENTO ALL'OGGETTO REMOTO
		remoteView.trasmitEvent(evt);
		logger.print("Sending ... : " + evt.toString());
	}

	@Override
	public Event recieveEvent() throws RemoteException {
		//CHI CHIAMA QUESTO METODO SI PONE IN ATTESA DI RICEVERE UN EVENTO DALL'OGGETTO REMOTO E LO METTE IN evt
		NotifyEvent evt = null;/*remoteView.grabEvent();*/
		return evt;
	}

	@Override
	public void closeCommunicator() throws RemoteException {
		remoteView = null;
	}

}
