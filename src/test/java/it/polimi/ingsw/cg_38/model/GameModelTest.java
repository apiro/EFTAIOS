package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameModelTest {

	GameModel model1;
	GameModel model2;
	GameModel model3;
	GameModel model4;
	Player player11;
	Player player21;
	Player player31;
	Player player12;
	Player player22;
	Player player32;
	Player player18;
	Turn turn1;
	Turn turn2;
	Turn turn3;
	Turn turn8;
	int i;
	
	/*Deck deckSector11;
	Deck deckObject12;
	Deck deckHatch13;
	Deck deckSector21;
	Deck deckObject22;
	Deck deckHatch23;
	Deck deckSector31;
	Deck deckObject32;
	Deck deckHatch33;*/
	
	
	@Before
	public void init() {
		model1 = new GameModel("Galilei");
		model2 = new GameModel("Galvani");
		model3 = new GameModel("Fermi");
		model4 = new GameModel("Galilei");
		model1.getGamePlayers().add(new Player("Anna"));
		model1.getGamePlayers().add(new Player("Alberto"));
		model2.getGamePlayers().add(new Player("Anna"));
		model2.getGamePlayers().add(new Player("Alberto"));
		model3.getGamePlayers().add(new Player("Anna"));
		model3.getGamePlayers().add(new Player("Alberto"));
		model1.getGamePlayers().add(new Player("Marco"));
		model1.getGamePlayers().add(new Player("Frank"));
		model1.getGamePlayers().add(new Player("Mario"));
		model1.getGamePlayers().add(new Player("Andrea"));
		model1.getGamePlayers().add(new Player("Ermenegilda"));
		model1.getGamePlayers().add(new Player("Candeloro"));
		player11 = model1.getGamePlayers().get(0);
		player21 = model2.getGamePlayers().get(0);
		player31 = model3.getGamePlayers().get(0);
		player12 = model1.getGamePlayers().get(1);
		player22 = model2.getGamePlayers().get(1);
		player32 = model3.getGamePlayers().get(1);
		player18 = model1.getGamePlayers().get(7);
		turn1 = new Turn(player11);
		turn2 = new Turn(player21);
		turn3 = new Turn(player31);
		turn8 = new Turn(player18);
		model1.setActualTurn(turn1);
		model2.setActualTurn(turn2);
		model3.setActualTurn(turn3);
	}
	
	
	
	@Test
	public void test() {
		
		assertEquals(model4.getNextPlayer(), null);
		
		assertEquals(model1.getActualTurn(), turn1);
		assertEquals(model2.getActualTurn(), turn2);
		assertEquals(model3.getActualTurn(), turn3);
		
		
		
		assertEquals(model1.getNextPlayer(), player12);
		assertEquals(model2.getNextPlayer(), player22);
		assertEquals(model3.getNextPlayer(), player32);
		
		model1.setActualTurn(turn8);
		
		assertEquals(model1.getNextPlayer(), player11);
		
		assertEquals(player11.getName(), "Anna");
		
		i = player11.getNumTurniGiocati();
		player11.finishTurn();
		assertEquals(player11.getNumTurniGiocati(), i+1);
		
	}

}
