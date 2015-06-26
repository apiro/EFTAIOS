package it.polimi.ingsw.cg_38.controller.logger;

/**
 * Questa interfaccia è molto importante per la creazione di un layer di logger, grazie al quale un client GUI e un client
 * CLI possono instanziare lo stesso oggetto che lavora tuttavia in modo differente, nonostante su di esso vengano chiamati
 * gli stessi metodi che sono definiti in questa interfaccia. Quando chiamo print(msg) il loggerCLI stamperà sulla CLI
 * mentre il loggerGUI aggiungerà un messaggio alla JTextArea. Stesso metodo, esecuzione diversa.
 * */
public interface Logger {

	public void print(String message);
	
	public String showAndRead(String toShow, String title);
	
}
