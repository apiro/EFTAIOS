package it.polimi.ingsw.cg_38.controller;

import java.rmi.RemoteException;

import it.polimi.ingsw.cg_38.controller.event.Event;

public interface Communicator {

	/**
	 * metodi che il clienthandler chiama per comunicare con il client
	 * 
	 * 
	 * 
	 * ---> sono implementati dalle classi che implementano ServerCommunicator in diverso modo a seconda della modalita di 
	 * 		comunicazione scelta. SocketServerInterface implementerà la send scrivendo sul canale di "out" il messaggio e
	 * 		RMIServerInterface invece implementerà la send del messaggio in base alle modialità della comunicazione RMI.
	 * @throws RemoteException 
	 **/
	
	void send(Event evt) throws RemoteException;
	
	public Event addSubscriber();
	
	Event recieveEvent() throws RemoteException;
}
