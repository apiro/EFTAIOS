package it.polimi.ingsw.cg_38.controller;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.SplashEvent;

public class CLIView implements ClientInterface {

	private Scanner in;
	private PrintStream out = System.out;
	private PrintStream strongOut = System.out;
	
	public PrintStream getStrongOut() {
		return strongOut;
	}

	public void setStrongOut(PrintStream strongOut) {
		this.strongOut = strongOut;
	}

	private Client client;
	
	public CLIView(Client client){
		this.setClient(client);
		this.setIn(new Scanner(System.in));
		this.setOut(System.out);
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Scanner getIn() {
		return in;
	}

	public void setIn(Scanner in) {
		this.in = in;
	}

	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out2) {
		this.out = out2;
	}

	@Override
	public void trasmit(Event evt) {
		this.getClient().getToSend().add(evt);
		this.getOut().println("Event Trasmitted !");
	}

	@Override
	public Event askUserCommands() {
		
		return null;
	}

	@Override
	public void renderSplashView() {
		this.getStrongOut().println("WELCOME TO THE GAME !\n");
	}

	@Override
	public void renderEvent(Event evt) {
		this.getOut().println("-----------------------------------------------------------");
		this.getStrongOut().println("Recieved notify event ... " + evt.toString() + "\n");
		this.getStrongOut().println("Callback event arrived !");
		this.getOut().println("-----------------------------------------------------------");
	}

	@Override
	public String askForProtocol() {
		this.getOut().println("CHOOSE THE CONNECTION PROTOCOL: write [RMI] or [SOCKET] : ");
		return this.getIn().nextLine();
	}

	@Override
	public SplashEvent askForSplashEvent() {
		
		return null;
	}

	@Override
	public int askForPort() {
		return Integer.parseInt(this.getIn().nextLine());
	}
}
