import javax.swing.*;
import java.util.*;

public class BombSquare extends GameSquare
{
	private boolean thisSquareHasBomb = false;
	public static final int MINE_PROBABILITY = 10;

	public BombSquare(int x, int y, GameBoard board)
	{
		super(x, y, "images/blank.png", board);

		Random r = new Random();
		thisSquareHasBomb = (r.nextInt(MINE_PROBABILITY) == 0);
	}

	public void clicked()
	{
		if (thisSquareHasBomb){
			this.setImage("images/bomb.png");
		} else if (checkSurroundings(this.xLocation+1,this.yLocation)){
			this.setImage("images/1.png");
		} else{
			this.setImage("images/blank.png");
		}
	}

	public boolean checkSurroundings(int x, int y){
		BombSquare square = new BombSquare(x,y,board);
		return square.checkSquare();
	}

	public boolean checkSquare(){
		return thisSquareHasBomb;
	}




}
