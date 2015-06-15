package it.polimi.ingsw.cg_38.controller.action;

import java.io.Serializable;

import it.polimi.ingsw.cg_38.model.Player;

public abstract class Action implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
