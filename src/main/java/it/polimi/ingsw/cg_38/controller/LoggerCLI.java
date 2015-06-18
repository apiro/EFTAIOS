package it.polimi.ingsw.cg_38.controller;

public class LoggerCLI implements Logger {

	@Override
	public void print(String message) {
		System.out.println(message);
	}

}
