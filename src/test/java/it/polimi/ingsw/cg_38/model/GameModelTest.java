package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameModelTest {

	GameModel model1;
	GameModel model2;
	GameModel model3;
	Player player11;
	Player player21;
	Player player31;
	Player player12;
	Player player22;
	Player player32;
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
		model1.getGamePlayers().add(new Player("Anna"));
		model1.getGamePlayers().add(new Player("Alberto"));
		model2.getGamePlayers().add(new Player("Anna"));
		model2.getGamePlayers().add(new Player("Alberto"));
		model3.getGamePlayers().add(new Player("Anna"));
		model3.getGamePlayers().add(new Player("Alberto"));
		player11 = model1.getGamePlayers().get(0);
		player21 = model2.getGamePlayers().get(0);
		player31 = model3.getGamePlayers().get(0);
		player12 = model1.getGamePlayers().get(1);
		player22 = model2.getGamePlayers().get(1);
		player32 = model3.getGamePlayers().get(1);
		model1.setActualTurn(new Turn(player11));
		model2.setActualTurn(new Turn(player21));
		model3.setActualTurn(new Turn(player31));
	}
	
	@Test
	public void test() {
		assertEquals(model1.getNextPlayer(), player12);
		assertEquals(model2.getNextPlayer(), player22);
		assertEquals(model3.getNextPlayer(), player32);
	}

}
