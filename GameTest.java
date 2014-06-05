package tetris;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	Game game;
	
	@Before
	public void setUp(){
		game = new Game();
	}

	@Test
	public void testGetLevel() {
		assertEquals(0, game.getLevel());
	}
	
	@Test
	public void testGetNextPiece() {
		
	}

}
