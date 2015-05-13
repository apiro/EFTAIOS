package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class GamesModelTest {
	GamesModel gamesModel;
	ArrayList<GameModel> models ;
	
	@Before
	
	public void init(){
		gamesModel = new GamesModel();
		models = new ArrayList<GameModel>();
		models.add(new GameModel("Galvani"));
		models.add(new GameModel("Fermi"));
		models.add(new GameModel("Galilei"));
	}
	

	@Test
	public void test() {
		gamesModel.setGames(models);
		assertEquals(gamesModel.getGames(), models);
				
	}

}
