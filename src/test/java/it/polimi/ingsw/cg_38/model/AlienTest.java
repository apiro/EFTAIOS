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
	Card drown; 
	
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
    	drown = avatar.draw(model.getDeckSector());
	}
	
	
	@Test
	public void test() {
		Sector sector = model.getGameMap().searchSectorByCoordinates(3,9);
		if(avatar.canMove(sector)) {
    		avatar.move(sector, player.getNumTurniGiocati()+1);
    		player.setNumTurniGiocati(player.getNumTurniGiocati()+1);
    	}
		assertEquals(avatar.canMove(sector), false);
		assertEquals(avatar.getMyMovements().size(), 0);
	}
}
