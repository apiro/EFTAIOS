package it.polimi.ingsw.cg_38.controller;

import java.util.Scanner;

public class PlayerMain {

	private static PlayerClient player;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PlayerClientInterface player = null;
		System.out.println("WELCOME TO THE GAME: Which interface do you wanna use ? [CLI] [GUI] ");
		Thread.currentThread().setName("Player-MainThread");
		String s = in.nextLine();
		while(s.equals("CLI") || s.equals("GUI")) {
			if(s.equals("CLI")) {
				player = new PlayerClient();
			} else if (s.equals("GUI")) {
				player = new PlayerClientGUI();
			}
		}
		player.run();
	}

}
