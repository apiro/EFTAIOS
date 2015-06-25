package it.polimi.ingsw.cg_38.controller.logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class LoggerGUI implements Logger {

	private JTextArea text;
	private JFrame frame;

	public LoggerGUI(JTextArea text, JFrame frame) {
		this.text = text;
		this.frame = frame;
	}
	
	@Override
	public void print(String message) {
		text.append(message + "\n");
	}

	@Override
	public String showAndRead(String toShow, String title) {
		return JOptionPane.showInputDialog(
		           frame,
		            toShow,
		            title,
		            JOptionPane.INFORMATION_MESSAGE);
	}

}
