import javax.swing.*;
import java.util.*;

public class BombSquare extends GameSquare {
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;



	private int bombCount;

	public BombSquare(int x, int y, GameBoard board) {
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);

	}

	public void clicked() {

		if (thisSquareHasBomb) {
			this.setImage("images/bomb.png");
		}else{

			String num = String.valueOf(checkSurroundings());
			this.setImage("images/"+num+".png");
			revealEmptySpaces();


		}

	}

	public int checkSurroundings(){

		int x = this.xLocation;
		int y = this.yLocation;

		int bombCount = 0;

		for (int i = x-1; i <= x+1; i++){
			for (int j = y-1; j <= y+1; j++){
				if (i >= 0 && i < board.getWidth() && j >= 0 && j < board.getHeight()){
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
	public void revealEmptySpaces(){
		for (int i = this.xLocation - 1 ; i <= this.xLocation + 1;i++){
			for (int j = this.yLocation - 1; j <= this.yLocation + 1; j++){
				if (i >= 0 && i < board.getWidth() && j >= 0 && j < board.getHeight()){
					board.getSquareAt(i,j).clicked();
					}
				}
			}
		}
	}

//make new variable that if its blank/false we run revealEmptySpaces
