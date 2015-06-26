package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/** contiene i test del turno */
public class TurnTest {

	Player player;
	Turn turn;
	
	@Before
	public void init(){
		player = new Player("Alberto");
		turn = new Turn(player);
	}
	
	@Test
	public void test() {
		turn.setHasAttacked(true);
		assertEquals(turn.getHasAttacked(), true);
		turn.setHasDraw(true);
		assertEquals(turn.getHasDraw(), true);
		turn.setHasMoved(true);
		assertEquals(turn.getHasMoved(), true);
		turn.setHasUsedObjectCard(true);
		assertEquals(turn.getHasUsedObjectCard(), true);
	}

}
