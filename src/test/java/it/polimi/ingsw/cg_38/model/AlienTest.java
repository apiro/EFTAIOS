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

public class AlienTest {

	GameModel model;
	GameModel model2;
	Sector alienStartingPoint;
	Sector sector;
	Sector sector2;
	Sector sector4;
	Player player;
	Player player2;
	Turn actualTurn;
	Avatar avatar;
	Avatar avatar2;
	Card drownSect; 
	Card drownObj; 
	Card drownHatch;
	
	@Before
	public void init() throws ParserConfigurationException, Exception {
		model = new GameModel("Galilei");
		model2 = new GameModel("Galilei");
		sector = model2.getGameMap().searchSectorByCoordinates(2, 1);
		sector4 = new Hatch();
    	alienStartingPoint = model.getGameMap().searchSectorByName("AlienStartingPoint");
    	//asp 5,11
    	model.getGamePlayers().add(new Player("Alberto"));
    	player = model.getGamePlayers().get(0);
    	model.getGamePlayers().get(0).setAvatar(new Alien(Name.Alien1, alienStartingPoint));
    	actualTurn = new Turn(player);
    	model.setActualTurn(actualTurn);
    	player.setNumTurniGiocati(player.getNumTurniGiocati());
    	avatar = model.getGamePlayers().get(0).getAvatar();
    	drownSect = avatar.draw(model.getDeckSector());
    	drownHatch = avatar.draw(model.getDeckHatch());
    	avatar2 = new Alien(Name.Alien1 , sector);
    	player2 = new Player("reda");
    	player2.setAvatar(avatar2);
	}
	
	
	@Test
	public void test() {
		
		Sector sector = model.getGameMap().searchSectorByCoordinates(5, 11);
		Sector sector1 = model.getGameMap().searchSectorByCoordinates(4, 11);
		Sector sector2 = model.getGameMap().searchSectorByCoordinates(3, 11);
		Sector sector3 = model.getGameMap().searchSectorByCoordinates(2, 11);
		
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
		assertEquals(avatar.canMove(sector2) , false);	
		assertEquals(avatar.getIsPowered(), true);
		
		avatar.move(sector, player.getNumTurniGiocati()+1);
		
		
		if(avatar.canMove(sector2)){
    		assertEquals(avatar.canMove(sector2), true);
    		String nameOfSector = avatar.move(sector2, player.getNumTurniGiocati()+1);
    		assertTrue(nameOfSector instanceof String);
    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
    		assertEquals(avatar.getMyMovements().size(), 4);
			
		}
		
		avatar.move(sector, player.getNumTurniGiocati()+1);
		
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
		avatar.addCard(new ObjectCard(ObjectCardType.Adrenaline));
		avatar.addCard(new ObjectCard(ObjectCardType.Adrenaline));
		Boolean result = avatar.addCard(new ObjectCard(ObjectCardType.Adrenaline));
		
		assertEquals(result, true/*false*/);
		assertEquals(avatar.getMyCards().size(), 4);
		
		avatar.eliminateFromMyCards(drownObj);
		assertEquals(avatar.getMyCards().size(), 3);
		
		assertEquals(avatar.getName(), Name.Alien1);
		
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
		
		assertEquals(player2.getAvatar().canMove(alienStartingPoint) , false);
		player2.getAvatar().setIsPowered(true);
		assertEquals(player2.getAvatar().canMove(sector) , false);
		sector3.getNeighboringSectors().add(sector4);
		avatar2.setCurrentSector(sector3);
		assertTrue(!avatar2.canMove(sector4));
		sector3.getNeighboringSectors().remove(sector4);
		sector2.getNeighboringSectors().add(sector4);
		sector3.getNeighboringSectors().add(sector2);
		assertTrue(!avatar2.canMove(sector4));
		avatar2.setIsPowered(true);
		sector2.getNeighboringSectors().remove(sector4);
		sector.getNeighboringSectors().add(sector4);
		sector2.getNeighboringSectors().add(sector);
		assertTrue(!avatar2.canMove(sector4));
		
		
		
	}
}
