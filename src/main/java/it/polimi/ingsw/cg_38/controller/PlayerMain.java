package it.polimi.ingsw.cg_38.controller;

public class PlayerMain {

	private static PlayerClient player;

	public static void main(String[] args) {
		Thread.currentThread().setName("Player-MainThread");
		player = new PlayerClient();
		player.run();
	}

}