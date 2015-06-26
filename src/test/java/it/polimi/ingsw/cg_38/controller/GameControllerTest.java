package it.polimi.ingsw.cg_38.controller;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

import it.polimi.ingsw.cg_38.controller.action.Draw;
import it.polimi.ingsw.cg_38.controller.action.FinishTurn;
import it.polimi.ingsw.cg_38.controller.event.NotifyEvent;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventFinishTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.model.Alien;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.map.Dangerous;
import it.polimi.ingsw.cg_38.model.map.Fermi;

/** contiene i test del gameController */
public class GameControllerTest {
	
	GameController gc;
	String type;
	String topic;
	
	Player player1;
	Player player2;
	Player player3;
	
	List<Player> add;
	List<NotifyEvent> notifyEvents;
	
	FinishTurn finishTurn;
	Draw draw;
	EventFinishTurn evtFinishTurn;
	EventDraw evtDraw;
	
	Dangerous sector1;
	
	int count1;
	int count2;
	
	
	
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		
		type = "Fermi";
		topic = "room1";
		
		player1 = new Player("Robben");
		player2 = new Player("Diffi");
		player3 = new Player("Berlu");
		
		sector1 = new Dangerous();
		
		add = new ArrayList<Player>();
		add.add(player1);
		add.add(player2);
		add.add(player3);

		gc = new GameController(type , topic);
		gc.setCanAcceptOtherPlayers(true);
		gc.getGameModel().setGamePlayers(add);
		
		
	}


	@Test
	public void test() throws RemoteException {
		
		assertEquals(gc.getTopic() , topic);
		assertTrue(gc.getGameModel().getGameMap() instanceof Fermi);
		assertTrue(gc.getBuffer().isEmpty());
		assertTrue(gc.getCanAcceptOtherPlayers());
		gc.assignAvatars();
		for(int i=0; i<gc.getGameModel().getGamePlayers().size(); i++){
			gc.getGameModel().getGamePlayers().get(i).getAvatar().setCurrentSector(sector1);
			if(gc.getGameModel().getGamePlayers().get(i).getAvatar() instanceof Alien)
				count1++;
			else
				count2++;
		}
		
		/* verifica che il numero di alieni in gioco sia maggiore o uguale del numero di 
		 * umani in gioco */
		assertTrue(count1 >= count2);
		
		/* verifica che il giocatore che deve effettuare il primo turno è il primo della lista dei giocatori
		 * del model */
		gc.setFirstTurn();
		assertEquals(gc.getGameModel().getActualTurn().getCurrentPlayer() , gc.getGameModel().getGamePlayers().get(0));

		evtFinishTurn = new EventFinishTurn(gc.getGameModel().getActualTurn().getCurrentPlayer());
		evtDraw = new EventDraw(gc.getGameModel().getActualTurn().getCurrentPlayer());
		finishTurn = new FinishTurn(evtFinishTurn);
		draw = new Draw(evtDraw);
		
		notifyEvents = gc.performUserCommands(finishTurn);
		assertEquals(((EventNotifyTurn)notifyEvents.get(0)).getPlayerOfTurn()    , gc.getGameModel().getActualTurn().getCurrentPlayer());
		
		/* verifico che il giocatore non può effettuare l'azione di pescaggio in quanto non ha ancora
		 * mosso durante il proprio turno */
		notifyEvents = gc.performUserCommands(draw);
		assertTrue(notifyEvents.get(0) instanceof EventNotifyError);
		
		gc.getGameModel().getActualTurn().setHasMoved(true);
		gc.getGameModel().setGameState(GameState.RUNNING);
		notifyEvents = gc.performUserCommands(draw);
		assertTrue(notifyEvents.get(0) instanceof EventDrown);
		
		gc.closeGame();
			
	}

}
