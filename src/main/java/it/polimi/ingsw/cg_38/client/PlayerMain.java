package it.polimi.ingsw.cg_38.client;

import it.polimi.ingsw.cg_38.client.cli.PlayerClientCLI;
import it.polimi.ingsw.cg_38.client.gui.PlayerClientGUI;
import it.polimi.ingsw.cg_38.controller.logger.Logger;
import it.polimi.ingsw.cg_38.controller.logger.LoggerCLI;

public class PlayerMain {

	private static PlayerClient player;
	private static Logger logger = new LoggerCLI();

	public static void main(String[] args) {
		
		String choose = logger.showAndRead("Welcome to the game: type the UX you prefer: [CLI] | [GUI]", "");
		
		if(("GUI").equals(choose)) {
			player = new PlayerClientGUI();
		} else if (("CLI").equals(choose)) {
			player = new PlayerClientCLI();
		}
		player.run();
	}
}
