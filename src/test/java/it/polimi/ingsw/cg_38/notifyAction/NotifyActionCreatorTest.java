package it.polimi.ingsw.cg_38.notifyAction;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.Draw;
import it.polimi.ingsw.cg_38.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Card;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Map;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.ObjectCard;
import it.polimi.ingsw.cg_38.model.ObjectCardType;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.Sector;
import it.polimi.ingsw.cg_38.model.SectorCard;
import it.polimi.ingsw.cg_38.model.SectorCardType;
import it.polimi.ingsw.cg_38.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.notifyEvent.EventFinishedTurn;
import it.polimi.ingsw.cg_38.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotYourTurn;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyLoose;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyPlayerState;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTeleport;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTopics;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.notifyEvent.EventNotifyWin;
import it.polimi.ingsw.cg_38.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.notifyEvent.EventUseDefense;

import org.junit.Before;
import org.junit.Test;

public class NotifyActionCreatorTest {

	NotifyActionCreator notifyActionCreator;
	
	EventAddedToGame added;
	EventAttacked attacked;
	EventCardUsed cardUsed;
	EventDeclareNoise declareNoise;
	EventDeclarePosition declarePosition;
	EventDrown drown;
	EventMoved moved;
	EventNotifyAliensWin aliensWin;
	EventNotifyEnvironment environment;
	EventNotifyError error;
	EventNotifyTurn turn;
	EventNotifyTeleport teleport;
	EventSufferAttack sufferAttack;
	EventDraw draw;
	EventUseDefense useDefense;
	
	Player player;
	Avatar avatar;
	ArrayList<Player> killed;
	ArrayList<String> allTopics;
	Sector sector;	
	Card card;
	Map map;
	Action action;
	
	@Before
	public void init() throws ParserConfigurationException, Exception{
		
		notifyActionCreator = new NotifyActionCreator();
		avatar = new Human(Name.Human1 , sector);
		player = new Player("reda");
		player.setAvatar(avatar);
		draw = new EventDraw(player);
		action = new Draw(draw);
		map = new Map("Galvani");
		card = new SectorCard(SectorCardType.RandomSectorNoise , false);
		killed = new ArrayList<Player>();
		killed.add(player);
		
		added = new EventAddedToGame(player , true , true);
		attacked = new EventAttacked(player , true);
		cardUsed = new EventCardUsed(player , true , ObjectCardType.Adrenaline);
		declareNoise = new EventDeclareNoise(player , sector);
		declarePosition = new EventDeclarePosition(player , killed);
		drown = new EventDrown(player , null , card);
		moved = new EventMoved(player , "moved");
		aliensWin = new EventNotifyAliensWin(player , killed);
		environment = new EventNotifyEnvironment(killed , map);
		error = new EventNotifyError(player , action);
		turn = new EventNotifyTurn(player);
		sufferAttack = new EventSufferAttack(player , killed);
		useDefense = new EventUseDefense(player , true , ObjectCardType.Defense);
		teleport = new EventNotifyTeleport(player , "moved");
		
	}

	@Test
	public void test() {
	
	assertTrue(NotifyActionCreator.createNotifyAction(added) instanceof AddedToGame);
	assertTrue(NotifyActionCreator.createNotifyAction(attacked) instanceof RenderAttacked);
	assertTrue(NotifyActionCreator.createNotifyAction(cardUsed) instanceof RenderNoSideEffectCard);
	assertTrue(NotifyActionCreator.createNotifyAction(declareNoise) instanceof RenderNoise);
	assertTrue(NotifyActionCreator.createNotifyAction(declarePosition) instanceof RenderSpotlight);
	assertTrue(NotifyActionCreator.createNotifyAction(drown) instanceof RenderDrown);
	assertTrue(NotifyActionCreator.createNotifyAction(moved) instanceof RenderMoved);
	assertTrue(NotifyActionCreator.createNotifyAction(aliensWin) instanceof RenderAliensWin);
	assertTrue(NotifyActionCreator.createNotifyAction(environment) instanceof RenderEnvironment);
	assertTrue(NotifyActionCreator.createNotifyAction(error) instanceof RenderError);
	assertTrue(NotifyActionCreator.createNotifyAction(turn) instanceof RenderNotifyTurn);
	assertTrue(NotifyActionCreator.createNotifyAction(sufferAttack) instanceof RenderAttackDamage);
	assertTrue(NotifyActionCreator.createNotifyAction(useDefense) instanceof RenderUseDefenseCard);
	assertTrue(NotifyActionCreator.createNotifyAction(teleport) instanceof RenderTeleport);
		
	}

}