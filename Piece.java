package tetris;

public class Piece {

	public static final int initCol = 5;
	public static final int initRow = 20;
	
	private int pieceKind;
	
	private int row;
	private int col;

	public static final int SQUARE = 7;
	public static final int T = 1;
	public static final int L = 2;
	public static final int BACKL = 3;
	public static final int S = 4;
	public static final int BACKS = 5;
	public static final int LONG = 6;

	public static final int LEFT = -1;
	public static final int RIGHT = 1;
	public static final int DOWN = 0;
	public static final int UP = 2;

	public static int[][] rOffsets = { {}, // 0 square
			{ 0, 0, 0, 1 }, // 1 t-block
			{ 0, 0, 0, 1 }, // 2 l-block
			{ 0, 0, 0, 1 }, // 3 backwards-l-block
			{ 0, 0, 1, 1 }, // 4 s-block
			{ 0, 0, 1, 1 }, // 5 backwards s-block
			{ 0, 0, 0, 0 },// 6 line-block
			{ 0, 0, 1, 1 } }; // 7 square

	public static int[][] cOffsets = { {}, // 0
			{ 0, -1, 1, 0 }, // 1
			{ 0, -1, 1, 1 }, // 2
			{ 0, -1, 1, -1 }, // 3
			{ 0, -1, 0, 1 }, // 4
			{ 0, 1, 0, -1 }, // 5
			{ 0, 1, 2, -1 }, // 6
			{ 0, 1, 0, 1 }}; //7

	private int offsets[][] = new int[2][4]; // offsets[r0/c1][squarenumber]

	public Piece(int pieceKind) {
		this.pieceKind = pieceKind;
		row = initRow;
		col = initCol;
		for (int i = 0; i < 4; i++) {
			offsets[0][i] = rOffsets[pieceKind][i];
			offsets[1][i] = cOffsets[pieceKind][i];
		}
	}

	public Piece(int r, int c, int pieceKind) {
		this.pieceKind = pieceKind;
		row = r;
		col = c;
		for (int i = 0; i < 4; i++) {
			offsets[0][i] = rOffsets[pieceKind][i];
			offsets[1][i] = cOffsets[pieceKind][i];
		}
	}
	
	public void copyOffsets(Piece that){
		for(int i = 0;i < 4; i++){
			this.offsets[0][i] = that.offsets[0][i];
			this.offsets[1][i] = that.offsets[1][i];
		}
	}
	
	/** sets the kind of the piece, without changing its position */
	public void setPieceKind(int pieceKind){
		this.pieceKind = pieceKind;
		for (int i = 0; i < 4; i++) {
			offsets[0][i] = rOffsets[pieceKind][i];
			offsets[1][i] = cOffsets[pieceKind][i];
		}
	}
	
	
	
	/** rotates the piece #ABSTRACT */
	public void rotate() {
		if(this.pieceKind == SQUARE){
			return;
		}
		int temp;
		for (int i = 0; i < 4; i++) {
			temp = getOffsets()[0][i];
			offsets[0][i] = getOffsets()[1][i];
			offsets[1][i] = -temp;
		}

	}

	/** moves the piece in the specified direction #ABSTRACT */
	public void move(int direction) {
		switch (direction) {
		case -1:
			col--;
			break;
		case 0:
			row--;
			break;
		case 1:
			col++;
			break;
		case 2:
			row++;
		}
	}
	
	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}
	
	public int getPieceKind(){
		return pieceKind;
	}

	public int[][] getOffsets() {
		return offsets;
	}

	// public boolean canDrop() {
	//
	// boolean isBottom[] = new boolean[4];
	// int colMin[] = new int[4];
	// for (int i = 0; i < 4; i++) {
	// for(int j = 0; j< 4; i++){
	//
	// }
	// }
	// }

}
