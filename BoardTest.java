package tetris;

import static org.junit.Assert.*;
//import static edu.princeton.cs.introcs.StdOut.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void testGetRowCol() {
		Board live = new Board(new Piece(Piece.BACKL));
		Board dead = new Board();
		for (int i = 0; i < 5; i++) {
			live.move(dead, Piece.DOWN);
		}
		assertEquals(Piece.BACKL,
				live.getRowCol(Piece.initRow - 5, Piece.initCol));

	}

	@Test
	public void testHasSpaceToMoveDown() {
		Piece piece = new Piece(Piece.LONG);
		Board live = new Board(piece);
		Board dead = new Board();
		live.move(dead, Piece.DOWN);
		assertTrue(live.hasSpaceToMove(dead, Piece.DOWN));
		live.lock(dead);
		piece = new Piece(Piece.LONG);
		live = new Board(piece);

		assertFalse(live.hasSpaceToMove(dead, Piece.DOWN));
	}

	@Test
	public void testHasSpaceToRotate() {
		Board live = new Board(new Piece(Piece.LONG));
		Board dead = new Board();
		live.move(dead, Piece.DOWN);
//		live.move(dead, Piece.DOWN);
		assertTrue(live.hasSpaceToRotate(dead));
		live.lock(dead);
//		dead.printBoard();
		live = new Board(new Piece(Piece.BACKS));
//		live.lock(dead);
//		dead.printBoard();
		assertFalse(live.hasSpaceToRotate(dead));
	}

//	@Test
//	public void testGravity() {
//		fail("not yet written");
//	}
//
//	@Test
//	public void testBoardFull() {
//		Board dead = new Board();
//		Piece block = new Piece(Piece.T);
//		Board live = new Board(block);
//		for(int i = 0; i < 10; i++){
//			while(live.hasSpaceToMove(dead, Piece.DOWN)){
//				live.move(dead, Piece.DOWN);
//			}
//			System.out.println(live.getPiece().getRow());
//			live.lock(dead);
//			live = new Board(block);
//		}
//		dead.printBoard();
//		assertTrue(dead.boardFull());
//	}

	@Test
	public void testRotate() {
		Board live = new Board(new Piece(Piece.T));
		Board dead = new Board();
		live.fleshPiece();
		assertEquals(
				Piece.T,
				live.getRowCol(live.getPiece().getRow() + 1, live.getPiece()
						.getCol()));
		live.rotate(dead);
		assertEquals(Piece.T, live.getRowCol(live.getPiece().getRow(), live.getPiece().getCol()-1));

	}

//	@Test
//	public void testDrop() {
//		Board dead = new Board();
//		Piece block = new Piece(Piece.LONG);
//		Board live = new Board(block);
//		live.drop(dead);
//		assertEquals(0, live.getPiece().getRow());
//		live.lock(dead);
//		live = new Board(block);
//		live.drop(dead);
//		live.lock(dead);
//		dead.printBoard();
//		live = new Board(new Piece(Piece.T));
//		live.drop(dead);
//		live.printBoard();
//		assertEquals(2, live.getPiece().getRow());
//	}

	@Test
	public void testMoveDown() {
		Board live = new Board(new Piece(Piece.BACKS));
		Board dead = new Board();
		live.fleshPiece();
		live.move(dead, Piece.DOWN);
		live.move(dead, Piece.DOWN);
		assertEquals(Piece.BACKS, live.getRowCol(live.getPiece().getRow(), live
				.getPiece().getCol()));
		live.move(dead, Piece.DOWN);
		live.move(dead, Piece.DOWN);
		live.move(dead, Piece.DOWN);
		assertEquals(Piece.BACKS, live.getRowCol(live.getPiece().getRow(), live
				.getPiece().getCol()));
	}

	@Test
	public void testFleshPiece() {
		Board live = new Board(new Piece(Piece.S));
		live.fleshPiece();
		assertEquals(Piece.S, live.getRowCol(live.getPiece().getRow(), live
				.getPiece().getCol()));
	}

	@Test
	public void testKillPiece() {
		Board live = new Board(new Piece(Piece.S));
		live.fleshPiece();
		assertEquals(Piece.S, live.getRowCol(live.getPiece().getRow(), live
				.getPiece().getCol()));
		live.killPiece();
		assertEquals(0, live.getRowCol(live.getPiece().getRow(), live
				.getPiece().getCol()));
	}

}
