import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

public class MazeRunner extends JPanel implements KeyListener {
	JFrame frame;
	int c = 1;//1
	int r = 0;//0
	int dir = 2;
	int size=30;
	int shade = 50;
	int stretch = 75;
	Hero hero;
	boolean is3D = false;
	boolean isRunning = true;
	ArrayList<Wall> walls;
	char[][] maze = buildMaze();
	Long startTime;
	Font font = new Font("Arial", Font.BOLD, 30);
	
	public MazeRunner() {
		frame = new JFrame("A-Mazing Program");
		frame.setSize(1000,600); //gives it size
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//cleans up buffer memory of frame, closes app
		frame.setVisible(true); //so you can see
		frame.add(this);
		frame.addKeyListener(this);
		hero = new Hero(new Location(r,c),dir,size,Color.RED);
		startTime = System.currentTimeMillis();
	}
	public long getElapsedTime() {
	    long elapsed=0;
	    if (isRunning) {
	      elapsed = ((System.currentTimeMillis() - startTime) / 1000);
	    } 
	    return elapsed;
	  }
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)g; 
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, frame.getWidth(), frame.getHeight()); 
		g2.setFont(font);
		
		if(!is3D) {
			//draw maze
			g2.setColor(Color.GRAY);
			//char[][] maze = buildMaze();
			int x;
			int y;
			for(int r = 0; r < maze.length; r++) {
				for(int c = 0; c < maze[0].length; c++) {
					x = size + size*c;
					y = size + size*r;
					if(maze[r][c] == ' ') 
						g2.fillRect(x, y, size, size);
					else
						g2.drawRect(x, y, size, size);
				}
			}
			//draw the hero
			
			g2.setColor(hero.getColor());
			g2.fill(hero.getRect());
			
			if(getElapsedTime()<100)
				g2.setColor(Color.WHITE);
			else
				g2.setColor(Color.RED);
			
			g2.drawString("Seconds: " + getElapsedTime(),200,575);
			g2.drawString("Moves: " + hero.getNumberOfMoves(),400,575);
			switch(hero.getDir()) {
			case 0: g2.drawString("North",600,575); break;
			case 1: g2.drawString("East",600,575); break;
			case 2: g2.drawString("South",600,575); break;
			case 3: g2.drawString("West",600,575); break;
			}
		}
		if(is3D)	{
			createWalls();
			for(int i = 0; i < walls.size(); i++) {
				if(walls.size()!=0) {
					g2.setColor(Color.black);
					g2.draw(walls.get(i).getDrawing());
					g2.setPaint(walls.get(i).getPaint());
					g2.fill(walls.get(i).getDrawing());
					
				}
			} 
			
			//tiny map in the corner
			
			g2.setColor(Color.GRAY);
			int x;
			int y;
			for(int r = 0; r < maze.length; r++) {
				for(int c = 0; c < maze[0].length; c++) {
					x = 825 + 10*c;
					y = 600 + 10*r;
					if(maze[r][c] == ' ') {
						if(hero.getLoc().isTravelled(r,c))
							g2.setColor(Color.YELLOW);
						g2.fillRect(x, y, 10, 10);
						g2.setColor(Color.GRAY);
					}
					else
						g2.drawRect(x, y, 10, 10);
				}
			}
			//draw the hero
			g2.setColor(Color.RED);
			
			g2.setColor(hero.getColor());
			g2.fill(hero.getTinyRect());
			
			if(getElapsedTime()<100)
				g2.setColor(Color.WHITE);
			else
				g2.setColor(Color.RED);
			
			g2.drawString("Seconds: " + getElapsedTime(),825,475);
			g2.drawString("Moves: " + hero.getNumberOfMoves(),825,525);
			switch(hero.getDir()) {
			case 0: g2.drawString("North",825,575); break;
			case 1: g2.drawString("East",825,575); break;
			case 2: g2.drawString("South",825,575); break;
			case 3: g2.drawString("West",825,575); break;
			}
		}
		if(hero.getLoc().getR()==13 && hero.getLoc().getC()==39) {
			g2.setColor(Color.GREEN);
			font = new Font("Arial", Font.BOLD, 200);
			g2.drawString("YOU MADE IT!!",400,400);
			g2.setColor(Color.WHITE);
			font = new Font("Arial", Font.BOLD, 30);
			
		}
		
		frame.repaint();
	}

	public static void main(String[] args) {
		MazeRunner app = new MazeRunner();
	}
	 
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		//needs you to push down and let go
		
	}
	public void keyPressed(KeyEvent e) {
		//needs you to press key
		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 32)//space to toggle 3D
			is3D = !is3D;
		if(is3D && e.getKeyCode() == 10)//enter to tag a wall
			hero.getLoc().setTag();
		hero.move(e.getKeyCode(), buildMaze());
	}
	public char[][] buildMaze() {
		File fileName = new File("/Users/krinalmanakiwala/eclipse-workspace/MazeRunner/Maze.txt");
		char[][]maze = new char[15][40];
		try {
			BufferedReader input = new BufferedReader(new FileReader(fileName));
			String text;
			int r = 0;
			while((text = input.readLine()) != null) {
				for(int c = 0; c < text.length(); c++)
					maze[r][c] = text.charAt(c);
				r++;
			}
			
		} catch (IOException e) {
			System.out.println(":(");
		}
		return maze;
	}
	
	 
	public void createWalls() {
		walls = new ArrayList<Wall>();
		int rr = hero.getLoc().getR();
		int cc = hero.getLoc().getC();
		int dir = hero.getDir();
		switch(dir) {
		
			case 0: //up
				
				for(int n = 0; n < 5; n++) {
					try {
						if(maze[rr-n][cc]=='#') {
							walls.add(getFrontWall(n));
							break;
						}
						if(maze[rr-n][cc-1]=='#') { //check left
							walls.add(getFloor(n));
							walls.add(getLeft(n));
							walls.add(getCeiling(n));
						}
						else {
							walls.add(getLeftPath(n));
							walls.add(getCeilingLeft(n));
							walls.add(getFloorLeft(n));
						}
						if(maze[rr-n][cc+1]=='#') { //check right
							walls.add(getRight(n));
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else {
							walls.add(getRightPath(n));
							walls.add(getCeilingRight(n));
							walls.add(getFloorRight(n));
						}
						
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
				
			break;
			
			case 1: //right
				for(int n = 0; n < 5; n++) {
					try {
						if(maze[rr][cc+n]=='#') {
							walls.add(getFrontWall(n));
							break;
						}
						if(maze[rr-1][cc+n]=='#') { 
							walls.add(getLeft(n));
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else {
							walls.add(getLeftPath(n));
							walls.add(getCeilingLeft(n));
							walls.add(getFloorLeft(n));
						}
						if(maze[rr+1][cc+n]=='#') { 
							walls.add(getRight(n));
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else {
							walls.add(getRightPath(n));
							walls.add(getCeilingRight(n));
							walls.add(getFloorRight(n));
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
					
				break;
			
			case 2: //down
				for(int n = 0; n < 5; n++) {
					try {
						if(maze[rr+n][cc]=='#') {
							walls.add(getFrontWall(n));
							break;
						}
						if(maze[rr+n][cc]=='#') {
							walls.add(getFrontWall(n));
						}
						if(maze[rr+n][cc+1]=='#') { 
							if(hero.getLoc().isTagged(rr+n, cc)) {
								Wall theWall = getFloor(n);
								theWall.setType("pink");
								walls.add(theWall);
							}
							else
								walls.add(getFloor(n));
							walls.add(getLeft(n));
							walls.add(getCeiling(n));
						}
						else {
							walls.add(getLeftPath(n));
							walls.add(getCeilingLeft(n));
							walls.add(getFloorLeft(n));
						}
						if(maze[rr+n][cc-1]=='#'){ 
							walls.add(getRight(n));
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else {
							walls.add(getRightPath(n));
							walls.add(getCeilingRight(n));
							walls.add(getFloorRight(n));
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
					
				break;
			
			case 3: //left
				for(int n = 0; n < 5; n++) {
					try {
						if(maze[rr][cc-n]=='#') {
							walls.add(getFrontWall(n));
							break;
						}
						if(maze[rr+1][cc-n]=='#') {
							walls.add(getLeft(n));
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
						}
						else {
							walls.add(getLeftPath(n));
							walls.add(getCeilingLeft(n));
							walls.add(getFloorLeft(n));
						}
						if(maze[rr-1][cc-n]=='#') {
							walls.add(getRight(n));
							walls.add(getCeiling(n));
							walls.add(getFloor(n));
													
						}
						else {
							walls.add(getRightPath(n));
							walls.add(getCeilingRight(n));
							walls.add(getFloorRight(n));
						}
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
					
				break;
			
		}
	}
	public Wall getLeftPath(int n) {
		int[]rows = {50+50*n,50+50*n,700-50*n,700-50*n};
		int[]cols = {100+50*n,50+50*n,50+50*n,100+50*n};
		return new Wall(rows,cols,"leftpath",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getLeft(int n) {
		int[]rows = {50+50*n,0+50*n,750-50*n,700-50*n};
		int[]cols = {100+50*n,50+50*n,50+50*n,100+50*n};
		return new Wall(rows,cols,"left",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getRightPath(int n) {
		int[]rows = {700-50*n,700-50*n,50+50*n,50+50*n};
		int[]cols = {750-50*n,800-50*n,800-50*n,750-50*n};
		return new Wall(rows,cols,"rightpath",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getRight(int n) {
		int[]rows = {700-50*n,750-50*n,0+50*n,50+50*n};
		int[]cols = {750-50*n,800-50*n,800-50*n,750-50*n};
		return new Wall(rows,cols,"right",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getCeiling(int n) {
		int[]rows = {50*n,50*n,50+50*n,50+50*n};
		int[]cols = {50+50*n,800-50*n,750-50*n,100+50*n};
		return new Wall(rows,cols,"ceiling",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getCeilingLeft(int n) {
		int[]rows = {50*n,50*n,50+50*n,50+50*n};
		int[]cols = {50+50*n,800-50*n,800-50*n,50+50*n};
		return new Wall(rows,cols,"ceilingleft",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getCeilingRight(int n) {
		int[]rows = {50*n,50*n,50+50*n,50+50*n};
		int[]cols = {50+50*n,800-50*n,800-50*n,100+50*n};
		return new Wall(rows,cols,"ceilingright",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getFloor(int n) {
		int[]rows = {750-50*n,750-50*n,700-50*n,700-50*n};
		int[]cols = {50+50*n,800-50*n,750-50*n,100+50*n};
		return new Wall(rows,cols,"floor",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getFloorLeft(int n) {
		int[]rows = {750-50*n,750-50*n,700-50*n,700-50*n};
		int[]cols = {50+50*n,800-50*n,750-50*n,50+50*n};
		return new Wall(rows,cols,"floorleft",new Color(255-shade*n,255-shade*n,255-shade*n),size);
	}
	public Wall getFloorRight(int n) {
		int[]rows = {750-50*n,750-50*n,700-50*n,700-50*n};
		int[]cols = {50+50*n,800-50*n,800-50*n,100+50*n};
		return new Wall(rows,cols,"floorright",new Color(255-shade*n,255-shade*n,255-shade*n),size); 
	}
	public Wall getFrontWall(int n) {
		int[]rows = {50*n,750-50*n,750-50*n,50*n};
		int[]cols = {50+50*n,50+50*n,800-50*n,800-50*n};
		return new Wall(rows,cols,"front",new Color(255-shade*(n-1),255-shade*(n-1),255-shade*(n-1)),size);
	}
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//needs you to release key
	}

}
