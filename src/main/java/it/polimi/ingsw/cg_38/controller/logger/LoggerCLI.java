package it.polimi.ingsw.cg_38.controller.logger;

import java.util.Scanner;

public class LoggerCLI implements Logger {

	private Scanner in = new Scanner(System.in);
	
	@Override
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public String showAndRead(String toShow) {
		this.print(toShow);
		return in.nextLine();
	}
}
