import javax.swing.*;
import java.util.*;

public class BombSquare extends GameSquare {
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;

	private boolean isRevealed = false;


	public BombSquare(int x, int y, GameBoard board) {
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);

	}

	/**
	 * A class that reveals squares with no bombs around it
	 */
	public void reveal(){
		if (!this.isRevealed()) {
			this.setImage("images/0.png");
			this.setRevealed(true);
		}
	}

	/**
	 * Runs when a GameSquare is clicked, changes image from blank to whatever it should be
	 */
	public void clicked() {

		if (!isRevealed) {


			if (thisSquareHasBomb) {
				this.setImage("images/bomb.png");
			} else {

				String num = String.valueOf(checkSurroundings());
				this.setImage("images/" + num + ".png");
				if (num.equals("0")) {
					revealEmptySpaces();
				}


			}
			setRevealed(true);
		}

	}

	/**
	 * Checks surrounding squares for bombs
	 * @return number of bombs surrounding square clicked
	 */
	public int checkSurroundings(){

		int x = this.xLocation;
		int y = this.yLocation;

		int bombCount = 0;

		for (int i = x-1; i <= x+1; i++){
			for (int j = y-1; j <= y+1; j++){
				if (isInBounds(i,j)){
					if (board.getSquareAt(i,j) instanceof BombSquare){
						if(((BombSquare) board.getSquareAt(i,j)).thisSquareHasBomb){bombCount++;}
					}else {
						BombSquare newSquare = new BombSquare(i, j, board);

						if (newSquare.thisSquareHasBomb) {
							bombCount++;
						}

					}
				}
			}
		}

		return bombCount;

	}

	/**
	 * Uses a recursion to check for empty squares and reveals them
	 */
	public void revealEmptySpaces(){
		if (isRevealed()){
			return;
		}

		for (int i = this.xLocation - 1 ; i <= this.xLocation + 1;i++){
			for (int j = this.yLocation - 1; j <= this.yLocation + 1; j++){
				if (isInBounds(i,j)){
					BombSquare square = (BombSquare) board.getSquareAt(i,j);
					if (!square.isRevealed() && square.isInBounds(square.xLocation,square.yLocation)){
						if (square.checkSurroundings() == 0 && square.isInBounds(square.xLocation,square.yLocation)){
							square.reveal();
							System.out.println(square.isRevealed);
							if(!square.isRevealed && square.isInBounds(square.xLocation,square.yLocation)){
								square.revealEmptySpaces();
							}
						}else{return;}
					}
					}else {break;}

				}
			}
		}

	/**
	 * Checks whether a square has been revealed or not
	 * @return whether a square has been revealed(true) or not(false)
	 */
	public boolean isRevealed(){
			return isRevealed;
		}

	/**
	 * Setter for isRevealed variable
	 * @param r whether a square has been revealed(true) or not(false)
	 */
	public void setRevealed(boolean r){
			isRevealed = r;
		}

	/**
	 * Checks if a square is on the board using x and y coordinates
	 * @param x x-coordinate
	 * @param y y-coordinate
	 * @return Whether coordinate is on board(true) or not(false)
	 */
		private boolean isInBounds(int x, int y){
			return x >= 0 && x < board.getWidth() && y >= 0 && y < board.getHeight();
		}
	}
