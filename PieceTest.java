package tetris;

import static org.junit.Assert.*;
import static tetris.Piece.*;

import org.junit.Test;

public class PieceTest {

	@Test
	public void testConstructor() {
		Piece block = new Piece(LONG);
		assertEquals(0, block.getOffsets()[0][1]);
		assertEquals(-1, block.getOffsets()[1][3]);
		block = new Piece(T);
		assertEquals(-1, block.getOffsets()[1][1]);
		block = new Piece(BACKL);
		assertEquals(-1, block.getOffsets()[1][1]);
	}

	@Test
	public void testRotate() {
		Piece block = new Piece(BACKS);
		assertEquals(1, block.getOffsets()[1][1]);
		block.rotate();
		assertEquals(0, block.getOffsets()[1][1]);
		block.rotate();
		assertEquals(-1, block.getOffsets()[1][1]);
	}

	@Test
	public void testMove() {
		Piece block = new Piece(S);
		assertEquals(Piece.initRow, block.getRow());
		block.move(DOWN);
		assertEquals(initRow - 1, block.getRow());
		block.move(LEFT);
		assertEquals(initCol - 1, block.getCol());
		block.move(RIGHT);
		block.move(RIGHT);
		assertEquals(initCol + 1, block.getCol());
	}

}
