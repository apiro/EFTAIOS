package it.polimi.ingsw.cg_38.client.notifyAction;

import it.polimi.ingsw.cg_38.client.PlayerClient;
import it.polimi.ingsw.cg_38.controller.event.GameEvent;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyChatMessage;

public class RenderChatMessage extends NotifyAction {

	private static final long serialVersionUID = 1L;
	private String message;

	public RenderChatMessage(NotifyEvent evt) {
		super(evt.getGenerator(), evt);
		this.message = ((EventNotifyChatMessage)evt).getMessage();
	}

	@Override
	public Boolean isPossible(PlayerClient client) {
		return super.check(client);
	}

	@Override
	public GameEvent render(PlayerClient client) {
		client.getLoggerChat().print(super.getPlayer().getName() + " says: " + message );
		return null;
	}
}
