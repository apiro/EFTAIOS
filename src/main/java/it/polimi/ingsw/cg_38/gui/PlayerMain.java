package it.polimi.ingsw.cg_38.gui;

import it.polimi.ingsw.cg_38.controller.Logger;
import it.polimi.ingsw.cg_38.controller.LoggerCLI;
import it.polimi.ingsw.cg_38.controller.PlayerClient;
import it.polimi.ingsw.cg_38.controller.PlayerClientCLI;

import java.util.Scanner;

import javax.swing.SwingUtilities;

public class PlayerMain {

	private static PlayerClient player;
	private static Logger logger = new LoggerCLI();

	public static void main(String[] args) {
		
		String choose = logger.showAndRead("Welcome to the game: type the UX you prefer: [CLI] | [GUI]");
		
		if(choose.equals("GUI")) {
			player = new PlayerClientGUI();
		} else if (choose.equals("CLI")) {
			player = new PlayerClientCLI();
		}
		player.run();
		
		/*SwingUtilities.invokeLater(new Runnable()
		{
			public void run ()
			{
				GUI game = new GUI();
				
			}
		});*/
	}
	
}
