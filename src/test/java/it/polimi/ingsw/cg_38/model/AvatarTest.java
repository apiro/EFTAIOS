package it.polimi.ingsw.cg_38.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class AvatarTest {

	public static Avatar avatar;
	
	@Before
	public void init(){
		avatar = new Alien();
	}
	
	@Test
	public void test(){
		
		ArrayList<ObjectCard> myArr = avatar.getMyCards();
		System.out.println(myArr);
		assertEquals(, myArr, 0);
	}
	
}
