package it.polimi.ingsw.cg_38.controller;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import it.polimi.ingsw.cg_38.controller.event.Event;
import it.polimi.ingsw.cg_38.controller.event.SplashEvent;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Movement;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;

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
	public void renderSplashView(EventNotifyEnvironment evt) {
		this.getOut().println("Loading the map...");
		this.getOut().println("MAPPA DEL GIOCO:\n");
		this.getOut().println("\n|_|A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|");
		for(int i = 0; i < evt.getMap().getHeight() ; i++) {
			this.getOut().print("|" + i + "|");
			for(int j = 0; j < evt.getMap().getWidth() ; j++) {
				this.getOut().print(evt.getMap().getTable().get(i).get(j).getName().substring(0, 1) + "|");
			}
			this.getOut().println("\n|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|");
		}
		this.getStrongOut().println("\nHere are your movements : \n");
		int i = 0;
		for(Movement mv:evt.getGenerator().getAvatar().getMyMovements()) {
			this.getOut().println(i + ")" );
			this.getOut().println(mv.toString() + "   ");
		}
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

	@Override
	public void renderErrorMessage() {
		this.getOut().println("--> ERROR !");
	}
}
