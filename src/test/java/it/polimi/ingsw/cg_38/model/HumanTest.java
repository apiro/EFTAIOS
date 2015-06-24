package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.Card;
import it.polimi.ingsw.cg_38.model.deck.HatchCard;
import it.polimi.ingsw.cg_38.model.deck.HatchCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.deck.SectorCard;
import it.polimi.ingsw.cg_38.model.map.Hatch;
import it.polimi.ingsw.cg_38.model.map.Sector;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

public class HumanTest {

	GameModel model;
	Sector humanStartingPoint;
	Sector sector;
	Player player;
	Player player2;
	Turn actualTurn;
	Avatar avatar;
	Avatar avatar2;
	Card drownSect; 
	Card drownObj; 
	Card drownHatch;
	Card card1;
	
	/**
	 * setCurrentSector(sector) su avatar non setta il settore!
	 * */
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		model = new GameModel("Galilei");
    	humanStartingPoint = model.getGameMap().searchSectorByName("HumanStartingPoint");
    	
    	//asp 5,11
    	model.getGamePlayers().add(new Player("Alberto"));
    	player = model.getGamePlayers().get(0);
    	player2 = new Player("Alberto");
    	model.getGamePlayers().get(0).setAvatar(new Human(Name.Human1, humanStartingPoint));
    	actualTurn = new Turn(player);
    	model.setActualTurn(actualTurn);
    	player.setNumTurniGiocati(player.getNumTurniGiocati());
    	avatar = model.getGamePlayers().get(0).getAvatar();
    	card1 = new ObjectCard(ObjectCardType.DEFENSE);
    	sector = model.getGameMap().searchSectorByCoordinates(2, 1);
    	avatar2 = new Human(Name.Human1 , sector);
    	player2.setAvatar(avatar2);
    	drownSect = avatar.draw(model.getDeckSector());
    	drownHatch = avatar.draw(model.getDeckHatch());
	}
	
	
	@Test
	public void test() {
		
		Sector sector = model.getGameMap().searchSectorByCoordinates(7, 11);
		Sector sector1 = model.getGameMap().searchSectorByCoordinates(8, 11);
		Sector sector2 = model.getGameMap().searchSectorByCoordinates(9, 11);
		Sector sector3 = model.getGameMap().searchSectorByCoordinates(10, 11);
		Hatch sector4 = (Hatch)model.getGameMap().searchSectorByCoordinates(1, 1);
		Sector sector5 = model.getGameMap().searchSectorByCoordinates(1, 2);
		
		if(avatar.canMove(sector1)){
			assertEquals(avatar.canMove(sector1), true);
			String nameOfSector = avatar.move(sector1, player.getNumTurniGiocati()+1);
    		assertTrue(nameOfSector instanceof String);
    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
    		assertEquals(avatar.getMyMovements().size(), 1);
		}
		
		if(avatar.canMove(sector2)){
			assertEquals(avatar.canMove(sector2), true);
			String nameOfSector = avatar.move(sector2, player.getNumTurniGiocati()+1);
    		assertTrue(nameOfSector instanceof String);
    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
    		assertEquals(avatar.getMyMovements().size(), 2);
			
		}
		assertEquals(avatar.canMove(sector2) , false);
		avatar.setIsPowered(true);
		
		assertEquals(avatar.getIsPowered(), true);
		assertTrue(!avatar.canMove(sector2));
		
		avatar.move(sector, player.getNumTurniGiocati()+1);
		
		
		if(avatar.canMove(sector2)){
    		assertEquals(avatar.canMove(sector2), true);
    		String nameOfSector = avatar.move(sector2, player.getNumTurniGiocati()+1);
    		assertTrue(nameOfSector instanceof String);
    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
    		assertEquals(avatar.getMyMovements().size(), 4);
			
		}
		
		avatar.move(sector, player.getNumTurniGiocati()+1);
		assertEquals(avatar.canMove(sector) , false);
		
		if(avatar.canMove(sector3)){
    		assertEquals(avatar.canMove(sector3), true);
    		String nameOfSector = avatar.move(sector3, player.getNumTurniGiocati()+1);
    		assertTrue(nameOfSector instanceof String);
    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
    		assertEquals(avatar.getMyMovements().size(), 6);
			
		}
		
		assertTrue(avatar.getMyMovements().get(0).toString() instanceof String);
		assertTrue(new Integer(avatar.getMyMovements().get(0).getTurnNumber()) instanceof Integer);
		assertTrue(avatar.getMyMovements().get(0).getTargetsector() instanceof Sector);
		
		assertTrue(((HatchCard) drownHatch).getColor() instanceof HatchCardType);
				
		
		if(((SectorCard) drownSect).getHasObjectIcon() == true) {
			drownObj = model.getDeckObject().draw();
		}
		
		avatar.addCard(drownObj);
		avatar.addCard(new ObjectCard(ObjectCardType.ADRENALINE));
		avatar.addCard(new ObjectCard(ObjectCardType.ADRENALINE));
		Boolean result = avatar.addCard(new ObjectCard(ObjectCardType.ADRENALINE));
		
		assertEquals(result, true);
		assertEquals(avatar.getMyCards().size(), 4);
		
		avatar.eliminateFromMyCards(drownObj);
		assertEquals(avatar.getMyCards().size(), 3);
		
		assertEquals(avatar.getName(), Name.Human1);
		
		avatar.setIsAlive(LifeState.ALIVE);
		assertEquals(avatar.getIsAlive(), LifeState.ALIVE);
		
		avatar.setIsAlive(LifeState.DEAD);
		assertEquals(avatar.getIsAlive(), LifeState.DEAD);
		
		avatar.setIsWinner(EndState.LOOSER);
		assertEquals(avatar.getIsWinner(), EndState.LOOSER);
		
		avatar.setIsWinner(EndState.PLAYING);
		assertEquals(avatar.getIsWinner(), EndState.PLAYING);
		
		avatar.setIsWinner(EndState.WINNER);
		assertEquals(avatar.getIsWinner(), EndState.WINNER);
		
		avatar.attacked();
		assertEquals(avatar.getIsWinner(), EndState.LOOSER);
		assertEquals(avatar.getIsAlive(), LifeState.DEAD);

		assertEquals(player2.getAvatar().hasDefenseCard() , false);
    	player2.getAvatar().addCard(card1);
		assertEquals(player2.getAvatar().hasDefenseCard() , true);
		assertEquals(player2.getAvatar().canMove(humanStartingPoint) , false);
		assertEquals(player2.getAvatar().canMove(sector) , false);
		player2.getAvatar().setIsPowered(true);
		assertEquals(player2.getAvatar().canMove(sector) , false);
		
		assertEquals(avatar.hasDefenseCard() , false);
		player2.getAvatar().setCurrentSector(sector5);
		sector4.setIsOpen(true);
		assertEquals(sector4.getIsOpen() , true);
		assertTrue(player2.getAvatar().canMove(sector4));
		sector4.setIsOpen(false);
		assertTrue(!player2.getAvatar().canMove(sector4));
		sector5 = model.getGameMap().searchSectorByCoordinates(1, 3);
		player2.getAvatar().setCurrentSector(sector5);
		assertTrue(!player2.getAvatar().canMove(sector4));
		sector4.setIsOpen(true);
		assertTrue(player2.getAvatar().canMove(sector4));
	
		}

}
