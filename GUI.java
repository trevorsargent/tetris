package tetris;

import static edu.princeton.cs.introcs.StdDraw.*;
import static tetris.Piece.*;
import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.Out;

public class GUI {

	private class Player implements Comparable<Object> {
		String name;
		int score;

		public Player() {
			name = "";
			score = 0;
		}

		@Override
		public int compareTo(Object that) {
			Player a = (Player) that;
			if (a.score > this.score) {
				return 1;
			} else if (a.score < this.score) {
				return -1;
			}
			return 0;

		}
	}

	private class Click {
		private double x;
		private double y;

		public Click() {
			while (!mousePressed()) {
			}
			while (mousePressed()) {
				x = mouseX();
				y = mouseY();
			}
		}

		public int getButton() {
			int i;
			if (x < 8 && x > 3) {
				for (i = 0; i < 5; i++) {
					if (y > 11.65 - 1.5 * i && y < 12.45 - 1.5 * i) {
						return i;
					}
				}
			}
			return 5;
		}
	}

	Game game;
	int numPlayers;
	Player[] playerList;
	Player currentPlayer;

	public GUI() {
		setCanvasSize(525, 645);
		setXscale(-3.75, 13.75);
		setYscale(-0.25, 20.5);

	}

	public static void main(String args[]) {
		new GUI().run();
	}

	public void run() {
		while (true) {
			readInScores();
			Arrays.sort(playerList);
			drawWelcomeTitle();
			drawPlayerButtons();

			Click click;
			selectPlayer();
			show();

			boolean cont = false;
			do {
				game = new Game();
				playGame(game);
				click = new Click();
				cont = cont(click, cont);

			} while (cont);

			Arrays.sort(playerList);
			writeOutScores();
			clear(BLACK);
			drawWelcomeTitle();
			setFont();
			text(5, 10, "Thanks for Playing!");
			show(1000);
		}

	}

	public boolean cont(Click click, boolean cont) {
		if (click.x > 3 && click.x < 8) {
			if (click.y > 9.65 && click.y < 10.45) {
				cont = true;
			} else if (click.y > 8.15 && click.y < 8.95) {
				cont = false;
			}
		}
		return cont;
	}

	public void selectPlayer() {
		Click click = new Click();
		int button = click.getButton();
		if (button > 0 && button < 5) {
			currentPlayer = playerList[button];
		} else if (button == 5) {
			currentPlayer = new Player();
			setPenColor();
			filledRectangle(5, 2, 4, .7);
			setPenColor(WHITE);
			setPenRadius(.005);
			rectangle(5, 2.05, 4, .7);
			text(5, .5, "Please enter your initials");
			String textBeingEntered = "";
			for (int i = 0; i < 3; i++) {
				while (!hasNextKeyTyped()) {
				}
				setPenColor();
				filledRectangle(5, 2.05, 3, .55);
				char a1 = nextKeyTyped();
				if (a1 > 'Z') {
					a1 -= ('a' - 'A');
				}
				textBeingEntered += a1;
				setPenColor(WHITE);
				text(5, 2, textBeingEntered);
			}
			currentPlayer.name = textBeingEntered;

		}
	}

	public void drawWelcomeTitle() {
		clear(BLACK);
		setPenColor(WHITE);
		setFont(new Font(null, Font.PLAIN, 80));
		text(5, 15, "TETRIS");
	}

	public void drawPlayerButtons() {

		setFont();
		for (int i = 0; i < numPlayers; i++) {
			setPenColor(DARK_GRAY);
			filledRectangle(5, 12.05 - 1.5 * i, 3, .4);
			setPenColor(WHITE);
			setPenRadius(.005);
			rectangle(5, 12.05 - 1.5 * i, 3, .4);
			text(4, 12 - 1.5 * i, playerList[i].name);
			text(6, 12 - 1.5 * i, "" + playerList[i].score);

		}
		setPenColor(DARK_GRAY);
		filledRectangle(5, 12.05 - 1.5 * 5, 3, .4);
		setPenColor(WHITE);
		setPenRadius(.005);
		rectangle(5, 4.55, 3, .4);
		text(5, 4.5, "New Player");
		show();
	}

	public void readInScores() {

		In in = new In("highScores.txt");
		numPlayers = Integer.parseInt(in.readLine());
		playerList = new Player[numPlayers];
		for (int i = 0; i < numPlayers; i++) {
			playerList[i] = new Player();
		}
		for (int i = 0; i < numPlayers; i++) {
			if (in.hasNextLine()) {
				playerList[i].name = in.readLine();
				playerList[i].score = Integer.parseInt(in.readLine());
			}
		}
	}

	public void writeOutScores() {
		boolean found = false;
		if (numPlayers > 0) {
			for (int i = 0; i < 5 && i < numPlayers; i++) {
				if (currentPlayer.name.equals(playerList[i].name)) {
					if (currentPlayer.score > playerList[i].score) {
						playerList[i].score = currentPlayer.score;
						found = true;
					}
					break;
				}
			}
			if (!found) {
				if (numPlayers < 5) {
					Player[] tempArray = new Player[numPlayers + 1];
					for (int i = 0; i < numPlayers; i++) {
						tempArray[i] = playerList[i];
					}
					playerList = tempArray;
					playerList[numPlayers] = currentPlayer;
					numPlayers++;
				} else if (currentPlayer.score > playerList[4].score) {
					playerList[4] = currentPlayer;
				}

			}
		}

		Out out = new Out("highScores.txt");
		out.println(numPlayers);
		for (int i = 0; i < numPlayers && i < 5; i++) {
			out.println(playerList[i].name);
			out.println(playerList[i].score);
		}
	}

	public void playGame(Game game) {
		for(int i = 3; i > 0; i--){
			draw();
			setFont(new Font(null, Font.PLAIN, 200));
			text(5, 10, "" + i);
			show(1000);
		}
		while (true) {
			game.move(DOWN);
			draw();
			for (int i = 0; i < 80 - game.getLevel(); i++) {
				getInput();
				draw();
				show(5);
			}
			if (!game.getLiveBoard().hasSpaceToMove(game.getDeadBoard(), DOWN)) {
				game.lock();
				game.incrementLevel(game.getDeadBoard()
						.checkForAndDeleteFullRows());
				game.setNewLiveBoard();
			}
			if (game.getDeadBoard().boardFull()) {
				break;
			}
		}
		if (game.getLevel() > currentPlayer.score) {
			currentPlayer.score = game.getLevel();
		}
		drawLoss();
	}

	public void drawLoss() {
		setPenColor();
		filledRectangle(5, 10, 3.5, 2.5);
		setPenColor(WHITE);
		setPenRadius(.005);
		rectangle(5, 10, 3.5, 2.5);
		text(5, 12, "You Lost :( Sorry");
		text(5, 11, "Your Score Was: " + game.getLevel());
		setPenColor(DARK_GRAY);
		filledRectangle(5, 10.05, 3, .4);
		filledRectangle(5, 8.55, 3, .4);
		setPenColor(WHITE);
		setPenRadius(.005);
		rectangle(5, 10, 3, .4);
		rectangle(5, 8.5, 3, .4);
		text(5, 10, "Play Again!");
		text(5, 8.5, "Quit. I'm Done");

		show();
	}

	public void draw(Board board) {
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < board.getNumCols(); c++) {
				if (board.getRowCol(r, c) != 0) {

					switch (board.getRowCol(r, c)) {
					case 1:
						setPenColor(RED);
						break;
					case 2:
						setPenColor(ORANGE);
						break;
					case 3:
						setPenColor(YELLOW);
						break;
					case 4:
						setPenColor(GREEN);
						break;
					case 5:
						setPenColor(BLUE);
						break;
					case 6:
						setPenColor(MAGENTA);
						break;
					case 7:
						setPenColor(new Color(200, 0, 255));
						break;
					}
					filledSquare(c + .5, r + .5, .5);
				}
			}
		}
		show(0);
	}

	public void drawGhostBoard() {
		game.refreshGhostBoard();
		for (int r = 0; r < 20; r++) {
			for (int c = 0; c < game.getGhostBoard().getNumCols(); c++) {
				if (game.getGhostBoard().getRowCol(r, c) != 0) {
					setPenColor(DARK_GRAY);
					filledSquare(c + .5, r + .5, .5);
				}
			}
		}
	}

	public void getInput() {
		if (hasNextKeyTyped()) {
			switch (nextKeyTyped()) {
			case 'a':
				// System.out.println("LEFT");
				game.getLiveBoard().move(game.getDeadBoard(), LEFT);
				break;
			case 'd':
				game.getLiveBoard().move(game.getDeadBoard(), RIGHT);
				break;
			case 's':
				game.getLiveBoard().move(game.getDeadBoard(), DOWN);
				break;
			case 'w':
				game.getLiveBoard().rotate(game.getDeadBoard());
				break;
			case ' ':
				while (game.getLiveBoard().hasSpaceToMove(game.getDeadBoard(),
						DOWN)) {
					game.getLiveBoard().move(game.getDeadBoard(), DOWN);
					draw();
					show(10);
				}
				break;
			case 'x':
				game.holdPiece();
			}
			draw();
		}
	}

	public void draw() {
		setFont();
		clear(BLACK);
		drawNextPiece();
		drawHoldPiece();
		drawBoardBackground();
		drawDashedGrid();
		drawGhostBoard();
		draw(game.getDeadBoard());
		draw(game.getLiveBoard());
		show(0);

	}

	public void drawBoardBackground() {
		setPenColor(BLACK);
		filledRectangle(5, 10, 5, 10);
		setPenColor(WHITE);
		// text(12.5, 11, "Next Piece");
		text(5, 20.5, ":TETRIS:");

		text(-2.5, 16.5, "Left: A");
		text(-2.5, 15.5, "Right: D");
		text(-2.5, 14.5, "Rotate W");
		text(-2.5, 13.5, "Down: S");
		text(-2.5, 12.5, "Drop: SPACE");
		text(-2.5, 11.5, "Hold: X");
		text(12.5, 19.5, "SCORE: " + game.getLevel());
		text(12, 14, "Next Piece");
		text(12, 10, "Held Piece");

	}

	public void drawDashedGrid() {
		setPenColor(DARK_GRAY);
		for (int i = 1; i < 10; i++) {
			line(i, 0, i, 20);
		}
		for (int i = 1; i < 20; i++) {
			line(0, i, 10, i);
		}
	}

	public void drawNextPiece() {
		double r, c;
		for (int i = 0; i < 4; i++) {
			r = 15 + game.getNextPiece().getOffsets()[0][i];
			c = 11.5 + game.getNextPiece().getOffsets()[1][i];
			switch (game.getNextPiece().getPieceKind()) {
			case 1:
				setPenColor(RED);
				break;
			case 2:
				setPenColor(ORANGE);
				break;
			case 3:
				setPenColor(YELLOW);
				break;
			case 4:
				setPenColor(GREEN);
				break;
			case 5:
				setPenColor(BLUE);
				break;
			case 6:
				setPenColor(MAGENTA);
				break;
			case 7:
				setPenColor(new Color(200, 0, 255));
				break;
			}
			filledSquare(c + .5, r + .5, .5);
		}
	}

	public void drawHoldPiece() {
		double r, c;
		if (game.getHoldPiece() != null) {
			for (int i = 0; i < 4; i++) {
				r = 11 + game.getHoldPiece().getOffsets()[0][i];
				c = 11.5 + game.getHoldPiece().getOffsets()[1][i];
				switch (game.getHoldPiece().getPieceKind()) {
				case 1:
					setPenColor(RED);
					break;
				case 2:
					setPenColor(ORANGE);
					break;
				case 3:
					setPenColor(YELLOW);
					break;
				case 4:
					setPenColor(GREEN);
					break;
				case 5:
					setPenColor(BLUE);
					break;
				case 6:
					setPenColor(MAGENTA);
					break;
				case 7:
					setPenColor(new Color(200, 0, 255));
					break;
				}
				filledSquare(c + .5, r + .5, .5);
			}
		}
	}

}
