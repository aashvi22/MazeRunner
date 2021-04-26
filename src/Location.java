import java.util.ArrayList;

public class Location {
	int r=0;
	int c=0;
	boolean[][]travelled=new boolean[15][40];
	boolean[][]tagged=new boolean[15][40];
	public Location(int row, int col) {
		r = row;
		c = col;
	}
	public int getR() {
		return r;
	}
	public int getC() {
		return c;
	}
	public Location getLoc() {
		return this;
	}
	public void setR(int a) {
		r += a;
		travelled[r][c]=true;
	}
	public void setC(int a) {
		c += a;
		travelled[r][c]=true;
	}
	public void setTag() {
		tagged[r][c]=true;
	}
	public boolean isTravelled(int enterR, int enterC) {
		try{if(travelled[enterR][enterC]==true)
			return true;
		}
		catch(NullPointerException e) {
			return false;
		}
		return false;
	}
	public boolean isTagged(int enterR, int enterC) {
		try{if(tagged[enterR][enterC]==true)
			return true;
		}
		catch(NullPointerException e) {
			return false;
		}
		return false;
	}
}
