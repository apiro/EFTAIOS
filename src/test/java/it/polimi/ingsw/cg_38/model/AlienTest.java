package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class AlienTest {

	GameModel model;
	Sector alienStartingPoint;
	Player player;
	Turn actualTurn;
	Avatar avatar;
	Card drownSect; 
	Card drownObj; 
	
	@Before
	public void init() throws InterruptedException {
		model = new GameModel("Galilei");
    	alienStartingPoint = model.getGameMap().searchSectorByName("AlienStartingPoint");
    	//asp 5,11
    	model.getGamePlayers().add(new Player("Alberto"));
    	player = model.getGamePlayers().get(0);
    	Thread.sleep(3000);
    	model.getGamePlayers().get(0).setAvatar(new Alien(Name.Alien1));
    	actualTurn = new Turn(player);
    	model.setActualTurn(actualTurn);
    	player.setNumTurniGiocati(player.getNumTurniGiocati());
    	avatar = model.getGamePlayers().get(0).getAvatar();
    	avatar.setCurrentSector(alienStartingPoint);
    	System.out.println(avatar.getCurrentSector().toString());
    	drownSect = avatar.draw(model.getDeckSector());
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
		avatar.setIsPowered(true);
		
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
		
		
				
		
		if(((SectorCard) drownSect).getHasObjectIcon() == true) {
			drownObj = model.getDeckObject().draw();
		}
		
		avatar.addCard(drownObj);
		avatar.addCard(new ObjectCard(ObjectCardType.Adrenaline));
		avatar.addCard(new ObjectCard(ObjectCardType.Adrenaline));
		Boolean result = avatar.addCard(new ObjectCard(ObjectCardType.Adrenaline));
		
		assertEquals(result, false);
		assertEquals(avatar.getMyCards().size(), 3);
		
		avatar.eliminateFromMyCards(drownObj);
		assertEquals(avatar.getMyCards().size(), 2);
		
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
		
		
	}
}
