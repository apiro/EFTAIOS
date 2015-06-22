package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;

public class RenderHumanWin extends NotifyAction {

	private static final long serialVersionUID = 1L;

	public RenderHumanWin(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLogger().print("PLAYER " + super.player.getName() + " HAS LEFT THE SPACESHIP ! ");
		if(client.getPlayer().getName().equals(super.player.getName())) {
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				client.getLogger().print("Interrupted Exception !");
			}
			client.setIsInterfaceBlocked(true);
		} else {
			client.setIsInterfaceBlocked(false);
			if(((EventNotifyHumanWin)evt).getAreThereOtherHumans()) {
				client.getLogger().print("There are other human in the map...Continue the game !");
			} else {
				client.getLogger().print("There aren't human in the map... !");
				client.getLogger().print("Aliens loose !");
				client.setIsInterfaceBlocked(true);
				client.setClientAlive(false);
			}
		}
		return null;
	}
}
