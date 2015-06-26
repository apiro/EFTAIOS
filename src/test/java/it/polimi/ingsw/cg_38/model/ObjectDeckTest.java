package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg_38.model.deck.ObjectCard;
import it.polimi.ingsw.cg_38.model.deck.ObjectCardType;
import it.polimi.ingsw.cg_38.model.deck.ObjectDeck;
import it.polimi.ingsw.cg_38.model.map.Sector;

import org.junit.Before;
import org.junit.Test;

/** contiene i test del mazzo di carte oggetto */
public class ObjectDeckTest {
	
	ObjectDeck deck;
	ObjectCard objectCard;
	ObjectCard objectCard2;
	ObjectCard objectCard3;
	ObjectCard objectCard4;
	int i;

	@Before
	public void init(){
		deck = new ObjectDeck();		
		objectCard2 = new ObjectCard(ObjectCardType.ADRENALINE);
		objectCard3 = new ObjectCard(ObjectCardType.ATTACKCARD);
		objectCard4 = objectCard2;
		
	}
	@Test
	public void test() {

		i = deck.getObjectDeck().size();
		objectCard = (ObjectCard)deck.draw();
		
		/* verifica il corretto funzionamento del metodo equals delle carte oggetto */
		assertEquals(objectCard.equals(null) , false);
		assertEquals(objectCard.equals(new Sector()) , false);
		assertEquals(objectCard2.hashCode() - ObjectCardType.ADRENALINE.hashCode() , 31);
		assertTrue(objectCard2.equals(objectCard4));
		objectCard4 = null;
		assertTrue(!objectCard2.equals(objectCard4));
		assertTrue(!objectCard2.equals(deck));
		assertTrue(!objectCard2.equals(objectCard3));
		objectCard4 = new ObjectCard(ObjectCardType.ADRENALINE);
		assertTrue(objectCard2.equals(objectCard4));
		
		assertEquals(objectCard4.getType() , ObjectCardType.ADRENALINE);
		assertEquals(objectCard4.toString() , "ObjectCard [type=" + ObjectCardType.ADRENALINE + "]");
		
	}

}
