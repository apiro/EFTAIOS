package it.polimi.ingsw.cg_38.notifyAction;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import it.polimi.ingsw.cg_38.client.notifyAction.AddedToGame;
import it.polimi.ingsw.cg_38.client.notifyAction.NotifyActionCreator;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderAliensWin;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderAttackDamage;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderAttacked;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderCardPerformed;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderDrown;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderEnvironment;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderError;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderHatchBlocked;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderHumanWin;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderMoved;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderNoSideEffectCard;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderNoise;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderNotifyTurn;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderRejectCard;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderSpotlight;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderTeleport;
import it.polimi.ingsw.cg_38.client.notifyAction.RenderUseDefenseCard;
import it.polimi.ingsw.cg_38.controller.action.Action;
import it.polimi.ingsw.cg_38.controller.action.Draw;
import it.polimi.ingsw.cg_38.controller.gameEvent.EventDraw;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAddedToGame;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventAttacked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventCardUsed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclareNoise;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDeclarePosition;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventDrown;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventHatchBlocked;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventMoved;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyAliensWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyCardPerformed;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyEnvironment;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyError;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyHumanWin;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTeleport;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventNotifyTurn;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventRejectCardAlien;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventSufferAttack;
import it.polimi.ingsw.cg_38.controller.notifyEvent.EventUseDefense;
import it.polimi.ingsw.cg_38.model.Avatar;
import it.polimi.ingsw.cg_38.model.Human;
import it.polimi.ingsw.cg_38.model.Name;
import it.polimi.ingsw.cg_38.model.Player;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.deck.SectorCardType;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Map;
import it.polimi.ingsw.cg_38.model.map.Sector;

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
	EventNotifyHumanWin humanWin;
	EventSufferAttack sufferAttack;
	EventDraw draw;
	EventUseDefense useDefense;
	EventHatchBlocked hatchBlocked;
	EventRejectCardAlien rejectCard;
	EventNotifyCardPerformed notifyCardPerformed;
	
	Player player;
	Avatar avatar;
	ArrayList<Player> killed;
	ArrayList<String> allTopics;
	Sector sector;	
	Hatch hatch;
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
		hatch = new Hatch();
		
		added = new EventAddedToGame(player , true , true);
		attacked = new EventAttacked(player , true);
		cardUsed = new EventCardUsed(player , true , ObjectCardType.Adrenaline);
		declareNoise = new EventDeclareNoise(player , sector);
		declarePosition = new EventDeclarePosition(player , killed);
		drown = new EventDrown(player , null , card);
		moved = new EventMoved(player , "moved");
		aliensWin = new EventNotifyAliensWin(player , killed , false);
		environment = new EventNotifyEnvironment(killed , map);
		error = new EventNotifyError(player , action);
		turn = new EventNotifyTurn(player);
		sufferAttack = new EventSufferAttack(player , killed);
		useDefense = new EventUseDefense(player , true , ObjectCardType.Defense);
		teleport = new EventNotifyTeleport(player , "moved");
		humanWin = new EventNotifyHumanWin(player , true);
		hatchBlocked = new EventHatchBlocked(player , hatch);
		rejectCard = new EventRejectCardAlien(player);
		notifyCardPerformed = new EventNotifyCardPerformed(player);
		
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
	assertTrue(NotifyActionCreator.createNotifyAction(humanWin) instanceof RenderHumanWin);
	assertTrue(NotifyActionCreator.createNotifyAction(hatchBlocked) instanceof RenderHatchBlocked);
	assertTrue(NotifyActionCreator.createNotifyAction(rejectCard) instanceof RenderRejectCard);
	assertTrue(NotifyActionCreator.createNotifyAction(notifyCardPerformed) instanceof RenderCardPerformed);
	
	}

}
