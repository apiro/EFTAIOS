package it.polimi.ingsw.cg_38.controller;

import java.rmi.RemoteException;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;


/**
 * per come è pensato qui si pensa che questo communicator sia l'interfaccia di comunicazione del client con il server
 * 
 * pero se passo, invece di come faccio ora in cui invio un oggetto remoto creato dal server, un oggetto creato dal client allora 
 * questo puo diventare anche una interfaccia di comunicazionbe del server con un dato client determinato dall'oggetto che quqndo
 * si registra deve cedere. quando si registra un client genera un evento di subscribe in cui c'è anche un campo per l'oggetto
 * remoto che il client crea per permettere al server di aggiungere eventi alla coda di eventi del client
 * */
public class RMICommunicator implements Communicator {

	private RMIGameInterface remoteView;
	
	public RMICommunicator(RMIGameInterface remoteView) {
		this.remoteView = remoteView;
	}
	
	public void send(Event evt) throws RemoteException {
		//CHI CHIAMA QUESTO METODO TRASMETTE UN EVENTO ALL'OGGETTO REMOTO
		remoteView.trasmitEvent((GameEvent)evt);
	}

	public Event addSubscriber() {
		//SERVER SI METTE IN RICEZIONE DI UN EVENTSUBSCRIBE DAL CLIENT
		return null;
	}

	public Event recieveEvent() throws RemoteException {
		//CHI CHIAMA QUESTO METODO SI PONE IN ATTESA DI RICEVERE UN EVENTO DALL'OGGETTO REMOTO E LO METTE IN evt
		NotifyEvent evt = remoteView.grabEvent();
		return evt;
	}

}
