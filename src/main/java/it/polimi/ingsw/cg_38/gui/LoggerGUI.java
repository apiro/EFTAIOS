package it.polimi.ingsw.cg_38.gui;

import javax.swing.JTextArea;

import it.polimi.ingsw.cg_38.controller.Logger;

public class LoggerGUI implements Logger {

	private JTextArea text;

	public LoggerGUI(JTextArea text) {
		this.text = text;
	}
	
	@Override
	public void print(String message) {
		text.append(message);
	}

}
