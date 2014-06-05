package tetris;

import static edu.princeton.cs.introcs.StdRandom.*;

public class Game {

	private Board liveBoard;
	private Board deadBoard;
	private Board ghostBoard;
	private int level;
	private Piece nextPiece;
	private Piece holdPiece;

	public Piece getHoldPiece() {
		return holdPiece;
	}

	public Board getLiveBoard() {
		return liveBoard;
	}

	public Board getDeadBoard() {
		return deadBoard;
	}

	public void refreshGhostBoard() {
		Piece ghostPiece = new Piece(liveBoard.getPiece().getRow(), liveBoard
				.getPiece().getCol(), liveBoard.getPiece().getPieceKind());
		ghostPiece.copyOffsets(liveBoard.getPiece());
		ghostBoard = new Board(ghostPiece);
		for (int i = 0; i < 25; i++) {
			ghostBoard.move(deadBoard, Piece.DOWN);
		}
	}

	// public void rotateGhostBoard(){
	// ghostBoard = new Board(new Piece(liveBoard.getPiece().getRow(),
	// liveBoard.getPiece().getCol(), liveBoard.getPiece().getPieceKind()));
	// ghostBoard.rotate(deadBoard);
	// }
	//
	// public void moveGhostBoard(int direction){
	// ghostBoard = new Board(new Piece(liveBoard.getPiece().getRow(),
	// liveBoard.getPiece().getCol(), liveBoard.getPiece().getPieceKind()));
	// ghostBoard.move(deadBoard, direction);
	// }

	public Board getGhostBoard() {
		return ghostBoard;
	}

	public void setNextPiece() {
		nextPiece = new Piece(uniform(1, 8));
	}

	public void setNewLiveBoard() {
		liveBoard = new Board(nextPiece);
		setNextPiece();
	}

	public Piece getNextPiece() {
		return nextPiece;
	}

	public int getLevel() {
		return level;
	}

	public Game() {

		nextPiece = new Piece(uniform(1, 8));
		level = 0;
		deadBoard = new Board();
		setNewLiveBoard();
		ghostBoard = new Board(new Piece(liveBoard.getPiece().getRow(),
				liveBoard.getPiece().getCol(), liveBoard.getPiece()
						.getPieceKind()));

	}

	public void incrementLevel(int i) {
		level += i;
	}

	public boolean gameLost() {
		return false;
	}

	public void move(int direction) {
		liveBoard.move(deadBoard, direction);
	}

	public void rotate() {
		liveBoard.rotate(deadBoard);
	}

	public void lock() {
		liveBoard.lock(deadBoard);
	}

	public void holdPiece() {
		if (holdPiece != null) {
			holdPiece.setPieceKind(liveBoard.switchPiece(holdPiece));
		} else {
			holdPiece = new Piece(liveBoard.getPiece().getPieceKind());
			setNewLiveBoard();
		}
	}

}
