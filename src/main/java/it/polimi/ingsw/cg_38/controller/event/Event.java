package it.polimi.ingsw.cg_38.controller.event;

import java.io.Serializable;

import it.polimi.ingsw.cg_38.model.Player;

public class Event implements Serializable {

	private Player generator;
	
	public Event(Player generator) {
		this.setGenerator(generator);
	}

	public Player getGenerator() {
		return generator;
	}

	public void setGenerator(Player generator) {
		this.generator = generator;
	}
}
