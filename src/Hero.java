import java.awt.Color;
import java.awt.Rectangle;

public class Hero {
	Location loc;
	int dir;
	int size;
	Color color;
	int numberofmoves=0;
	public Hero(Location l, int dir, int size, Color c) {
		this.loc=l;
		this.dir=dir;
		this.size = size;
		this.color=c;
	}
	public Color getColor() {
		return this.color;
	}
	public Location getLoc() {
		return this.loc;
	}
	public int getDir() {
		return this.dir;
	}
	public void move(int key, char[][]maze) {
		int r = getLoc().getR();
		int c = getLoc().getC();
		//forward
		if(key == 38) {
			numberofmoves++;
			//0 = up, 1 = right, 2 = down, 3 = left
			if(dir == 0)
				if(r>0 && maze[r-1][c] == ' ')
					getLoc().setR(-1); 
			if(dir == 1)
				if(c<maze[0].length -1 && maze[r][c+1] == ' ')
					getLoc().setC(1); 
			if(dir == 2)
				if(r<maze.length-1 && maze[r+1][c] == ' ')
					getLoc().setR(1);
			if(dir == 3)
				if(c>0 &&maze[r][c-1] == ' ')
					getLoc().setC(-1);
			
		}
		//turn right
		if(key == 39) {
			numberofmoves++;
			dir++;
			if(dir>3)
				dir=0;
		}
		//turn left
		if(key == 37) {
			numberofmoves++;
			dir--;
			if(dir<0)
				dir=3;
		}
		
	}
	public Rectangle getRect() {
		int r = getLoc().getR();
		int c = getLoc().getC();
		return new Rectangle(c*size + size, r*size+size, size,size);
	}
	public Rectangle getTinyRect() {
		int r = getLoc().getR();
		int c = getLoc().getC();
		return new Rectangle(c*10 + 825, r*10+600, 10,10);
	}
	public int getNumberOfMoves() {
		return numberofmoves;
	}
	

}

