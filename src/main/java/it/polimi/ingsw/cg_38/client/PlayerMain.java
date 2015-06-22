package it.polimi.ingsw.cg_38.client;

public class PlayerMain {

	private static PlayerClientCLI player;

	public static void main(String[] args) {
		Thread.currentThread().setName("Player-MainThread");
		player = new PlayerClientCLI();
		player.run();
	}

}