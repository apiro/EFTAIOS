package it.polimi.ingsw.cg_38.controller.logger;

import java.util.Scanner;

/**
 * Questa è l'implementazione del logger secondo l'ambiente CLI
 * */
public class LoggerCLI implements Logger {

	private Scanner in = new Scanner(System.in);
	
	/**
	 *@param message: messaggio stampato in CLI
	 * */
	@Override
	public void print(String message) {
		System.out.println(message);
	}

	/**
	 *@param toShow: messaggio stampato in CLI
	 *@param title: parametro opzionale utilizzato in GUI
	 * */
	@Override
	public String showAndRead(String toShow, String title) {
		this.print(toShow);
		return in.nextLine();
	}
}
