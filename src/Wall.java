import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Polygon;
import java.awt.Rectangle;

public class Wall {
	
	int[] rows;
	int[] cols;
	Color color;
	String type;
	int size;
	
	public Wall(int[] rows, int[] cols, String type,Color color, int size) {
		this.rows = rows;
		this.cols = cols;
		this.color = color;
		this.type=type;
		this.size = size;
		
	}
	
	public Polygon getDrawing() {
		//return new Rectangle(cols[0],rows[0],cols[1]-cols[0],rows[2]-rows[1]);
		return new Polygon(cols,rows,4);
	}
	public Color getColor() {
		return color;
	}
	public void setType(String t) {
		type = t;
	}
	public GradientPaint getPaint() {
		int endR=color.getRed()-50;
		int endG = color.getGreen()-50;
		int endB = color.getBlue()-50;
		if(type.equals("pink")) {
			System.out.println("pinked");
			return new GradientPaint(cols[1],rows[0],new Color(65,255,48), cols[0],rows[1],new Color(65,254,48));
		}
		else{
			
			if(endR==0)
				endR = 0;
			if(endG==0)
				endR = 0;
			if(endB==0)
				endR = 0;
			switch(type) {
				case "left":
				case "right":
					return new GradientPaint(cols[1],rows[0],color, cols[0],rows[0],new Color(endR, endG, endB));
				case "leftpath":
				case "rightpath":
					return new GradientPaint(cols[0],rows[0],color, cols[1],rows[0],new Color(endR, endG, endB));
				case "ceiling":
				case "floor":
					return new GradientPaint(cols[0],rows[0],color, cols[0],rows[2],new Color(endR, endG, endB));
				case "ceilingright":
				case "ceilingleft":
				case "floorright":
				case "floorleft":
					return new GradientPaint(cols[0],rows[0],color, cols[0],rows[2],new Color(endR, endG, endB));
					
			}
		}
		return new GradientPaint(cols[1],rows[0],color, cols[0],rows[0],new Color(endR, endG, endB));
	}
}
