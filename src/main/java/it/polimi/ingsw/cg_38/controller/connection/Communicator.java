package it.polimi.ingsw.cg_38.controller.connection;

import java.io.IOException;
import java.rmi.RemoteException;

import it.polimi.ingsw.cg_38.controller.event.Event;

/**
 * Metodi che il clienthandler chiama per comunicare con il client
 * 
 * 
 * 
 * ---> sono implementati dalle classi che implementano Communicator in diverso modo a seconda della modalita di 
 * 		comunicazione scelta. SocketCommunicator implementerà la send scrivendo sul canale di "out" il messaggio e
 * 		RMICommunicator invece implementerà la send del messaggio in base alle modialità della comunicazione RMI.
 * @throws RemoteException 
 * @throws IOException 
 **/
public interface Communicator {
	
	void send(Event evt) throws RemoteException, IOException;
	
	Event recieveEvent() throws RemoteException, IOException;
	
	void closeCommunicator() throws RemoteException;
}
