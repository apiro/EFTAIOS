package it.polimi.ingsw.cg_38.gui;

import it.polimi.ingsw.cg_38.controller.PlayerClient;

import java.util.Scanner;

import javax.swing.SwingUtilities;

public class PlayerMain {

	private static PlayerClient player;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run ()
			{
				GUI game = new GUI();
				
			}
		});
	}

}
