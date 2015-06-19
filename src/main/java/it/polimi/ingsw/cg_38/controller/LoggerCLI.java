package it.polimi.ingsw.cg_38.controller;

import java.util.Scanner;

public class LoggerCLI implements Logger {

	@Override
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public String showAndRead(String toShow) {
		Scanner in = new Scanner(System.in);
		this.print(toShow);
		return in.nextLine();
	}

}
