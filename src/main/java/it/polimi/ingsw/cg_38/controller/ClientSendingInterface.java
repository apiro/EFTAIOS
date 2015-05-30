package it.polimi.ingsw.cg_38.controller;

import java.util.Scanner;

public class ClientSendingInterface extends Thread {
	
	private Communicator communicator;
	private Scanner in = new Scanner(System.in);

	public ClientSendingInterface(Communicator communicator) {
		this.communicator = communicator;
	}
	
	public void splashInterface() {
		System.out.println("1) MOVE - M \n2) ATTACK- A \n1) DRAW - D \n 1) USECARD - U \n ");
		if(in.nextLine().equals("M")) {
		}
	}

	
	
}
