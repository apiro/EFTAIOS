package it.polimi.ingsw.cg_38.gameEvent;

import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.GameEventType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;

public class EventAttack extends GameEvent {

	private Sector target;
	
	public Sector getTarget() {
		return target;
	}

	public void setTarget(Sector target) {
		this.target = target;
	}

	public EventAttack(Player generator, Sector toAttack) {
		super(generator);
		super.setType(GameEventType.Attack);
		this.setTarget(toAttack);
	}

}