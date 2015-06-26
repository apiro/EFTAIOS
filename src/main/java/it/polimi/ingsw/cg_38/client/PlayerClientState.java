package it.polimi.ingsw.cg_38.client;

/**
 * Stati in cui si può trovare il client durante il gioco
 * */
public enum PlayerClientState {
	INIT,
	CONNECTING,
	CONNECTED,
	PLAYING,
	ISTURN,
	WINNER,
	LOOSER
}
