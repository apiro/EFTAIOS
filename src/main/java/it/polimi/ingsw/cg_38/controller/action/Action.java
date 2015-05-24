package it.polimi.ingsw.cg_38.controller.action;

import it.polimi.ingsw.cg_38.model.Player;

public class Action {
	
	public Action(Player player) {
    	this.setPlayer(player);
    }
    
    public Player player;

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
