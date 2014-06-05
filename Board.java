package tetris;

import static edu.princeton.cs.introcs.StdOut.*;
import static tetris.Piece.*;

public class Board {

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	private final int numRows = 25;
	private final int numCols = 10;

	int[][] board;
	private Piece piece;

	public Board(Piece piece) {
		this.board = new int[numRows][numCols];
		this.piece = piece;
		fleshPiece();
	}

	public Board() {
		this.board = new int[numRows][numCols];
		this.piece = null;
	}

	public int getRowCol(int r, int c) {
		return board[r][c];
	}

	public Piece getPiece() {
		return piece;
	}

	/**
	 * changes the pieceKind of the piece in the current Live Board to the kind
	 * of the piece passed it
	 */
	public int switchPiece(Piece next) {
		int temp = piece.getPieceKind();

		this.piece.setPieceKind(next.getPieceKind());
//		while(boardIsLegal() == LEFT){
//			move(dead, RIGHT);
//		}
//		while(boardIsLegal() == RIGHT){
//			move(dead, LEFT);
//		}
		return temp;
	}

	/** Checks if the piece has space to move in the direction specified */
	public boolean hasSpaceToMove(Board that, int direction) {
		switch (direction) {

		case DOWN:

			int min = numRows;
			for (int i = 0; i < 4; i++) {
				if (piece.getRow() + piece.getOffsets()[0][i] < min) {
					min = piece.getRow() + piece.getOffsets()[0][i];
				}
			}
			if (min <= 0) {
				// System.out.println("LANDED");
				return false;
			}

			for (int i = 0; i < 4; i++) {
				if (that.getRowCol(piece.getRow() + piece.getOffsets()[0][i]
						- 1, piece.getCol() + piece.getOffsets()[1][i]) != 0) {
					// System.out.println("LANDED");
					return false;
				}
			}
			break;
		case LEFT:
			int leftmost = numCols;

			for (int i = 0; i < 4; i++) {
				// println(leftmost + " , " + (piece.getCol() +
				// piece.offsets[1][i]));

				if (piece.getCol() + piece.getOffsets()[1][i] < leftmost) {
					leftmost = piece.getCol() + piece.getOffsets()[1][i];
				}
			}

			if (leftmost == 0) {
				return false;
			}

			for (int i = 0; i < 4; i++) {
				if (that.getRowCol(piece.getRow() + piece.getOffsets()[0][i],
						piece.getCol() + piece.getOffsets()[1][i] - 1) != 0) {
					return false;
				}
			}
			break;
		case RIGHT:

			int rightmost = 0;

			for (int i = 0; i < 4; i++) {
				if (piece.getCol() + piece.getOffsets()[1][i] > rightmost) {
					rightmost = piece.getCol() + piece.getOffsets()[1][i];
				}
			}

			if (rightmost >= numCols - 1) {
				return false;
			}
			for (int i = 0; i < 4; i++) {
				if (that.getRowCol(piece.getRow() + piece.getOffsets()[0][i],
						piece.getCol() + piece.getOffsets()[1][i] + 1) != 0) {
					return false;
				}
			}
			break;
		}
		return true;
	}

	/** checks if the piece has space to rotate */

	public boolean hasSpaceToRotate(Board dead) {
		Piece tester = new Piece(piece.getRow(), piece.getCol(),
				piece.getPieceKind());
		tester.rotate();
		for (int i = 0; i < 4; i++) {
			if (tester.getRow() + tester.getOffsets()[0][i] < 0) {
				return false;
			} else if (tester.getCol() + tester.getOffsets()[1][i] < 0) {
				return false;
			} else if (tester.getCol() + tester.getOffsets()[1][i] >= numCols - 1) {
				return false;
			}
		}
//		System.out.println(tester.getCol());

		for (int i = 0; i < 4; i++) {
			if (dead.getRowCol(tester.getRow() + tester.getOffsets()[0][i],
					tester.getCol() + tester.getOffsets()[1][i]) != 0) {
				return false;
			}
		}

//		System.out.println("return true");
		return true;

	}

	/** moves the piece down in the board */
	public void move(Board dead, int direction) {
		killPiece();
		if (this.hasSpaceToMove(dead, direction)) {
			piece.move(direction);
		}
		fleshPiece();

	}

	/** rotates the piece in the board */
	public void rotate(Board dead) {
		killPiece();
		if (this.hasSpaceToRotate(dead)) {
			piece.rotate();
		}
		fleshPiece();
	}

	/** tells the board's piece to rotate */
//	private void rotate() {
//		piece.rotate();
//	}

	/** tells the board's piece to drop to the bottom NOT WORKING ALSO NOT USED */
	public void drop(Board dead) {
		killPiece();
		for (int i = 0; i < 20; i++) {
			if (this.hasSpaceToMove(dead, DOWN)) {
				move(dead, DOWN);
			}
		}
		fleshPiece();
	}

	/**
	 * causes all the rows above row i to 'fall' down onto row i, presumably
	 * because it is full
	 */
	public void shiftDown(int i) {
		for (int r = i; r < 19; r++) {
			for (int c = 0; c < numCols; c++) {
				board[r][c] = board[r + 1][c];
			}
		}
	}

	/** checks for, and deletes any full rows */
	public int checkForAndDeleteFullRows() {
		int rowsDeleted = 0;
		boolean rowFull = true;

		for (int r = 0; r < 20; r++) {
			rowFull = true;
			for (int c = 0; c < numCols; c++) {
				if (board[r][c] == 0) {
					rowFull = false;
				}
			}
			if (rowFull) {
				shiftDown(r);
				rowsDeleted++;
				r = -1;
			}
		}
		return rowsDeleted;
	}

	/** checks if the board is full */
	public boolean boardFull() {
		for (int r = 0; r < 20; r++) {
			boolean rowEmpty = true;
			for (int c = 0; c < numCols; c++) {
				if (board[r][c] != 0) {
					rowEmpty = false;
					break;
				}
			}
			if (rowEmpty) {
				return false;
			}
		}
		return true;
	}
	
	public int boardIsLegal(){
		
		for(int i = 0; i < 4; i++){
			if(piece.getOffsets()[0][i] > 9){
				return RIGHT;
			}else if(piece.getOffsets()[0][i] < 0){
				return LEFT;
			}
		}
		return UP;
		
	}

	/** writes the piece into the, presumably dead, board */
	public void lock(Board that) {
		for (int slot = 0; slot < 4; slot++) {
			that.board[this.getPiece().getRow()
					+ getPiece().getOffsets()[0][slot]][getPiece().getCol()
					+ getPiece().getOffsets()[1][slot]] = this.piece
					.getPieceKind();
		}
	}

	/** writes the piece into the live board */
	public void fleshPiece() {

		for (int slot = 0; slot < 4; slot++) {
			board[this.getPiece().getRow() + getPiece().getOffsets()[0][slot]][getPiece()
					.getCol() + getPiece().getOffsets()[1][slot]] = piece
					.getPieceKind();
		}

	}

	/** deletes the piece from the live board, but maintains it */
	public void killPiece() {
		for (int slot = 0; slot < 4; slot++) {
			board[this.getPiece().getRow() + getPiece().getOffsets()[0][slot]][getPiece()
					.getCol() + getPiece().getOffsets()[1][slot]] = 0;
		}
	}

	/** prints a representation of the board to the console */
	public void printBoard() {
		println("__________");
		for (int r = numRows - 1; r >= 0; r--) {
			for (int c = 0; c < numCols; c++) {
				print(this.board[r][c]);
			}
			println();
		}
		println();
	}

	// public boolean isLineFull(int r){
	// for(int i = 0; i< board[r].length; i++){
	// if(board[r][i] == 0){
	// return false;
	// }
	// }return true;
	// }

}
