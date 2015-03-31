package model.gizmo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class TriangleBumper extends AbstractGizmo {

	private int x;
	private int y;
	private points topLeft;
	private points topRight;
	private points botLeft;
	private points botRight;
	private points cornerABC;
	private points cornerBCA;
	private points cornerCAB;
	private int height;
	private int width;
	private String gizmoType;

	public TriangleBumper(String gizmoType, String id, int x, int y) {
        super(gizmoType, id, x, y);
        color = Color.BLUE;
        this.height = L;
        this.width = L;
        this.x = x * L;
        this.y = y * L;
        this.gizmoType = gizmoType;
        topLeft = new points(this.x, this.y);
        topRight = new points(this.x + width, this.y);
        botLeft = new points(this.x, this.y + this.height);
        botRight = new points(this.x + this.width, this.y + this.height);
        cornerABC = topLeft;
        cornerBCA = topRight;
        cornerCAB = botLeft;

    }

	private class points{
		private int pointX;
		private int pointY;
		public points(int x, int y){
			pointX = x;
			pointY = y;
		}

		public int getPointX(){
			return pointX;
		}

		public int getPointY(){
			return pointY;
		}
	}

    public int[] getAllXPos(){
    	int[] xCoordinates = {cornerABC.getPointX(), cornerBCA.getPointX(), cornerCAB.getPointX()};
    	return xCoordinates;
    }

    public int[] getAllYPos(){
    	int[] yCoordinates = {cornerABC.getPointY(), cornerBCA.getPointY(), cornerCAB.getPointY()};
    	return yCoordinates;
    }

    //TODO make a better rotate method
    @Override
    public void rotateLeft(){
    	if (cornerABC.equals(topLeft)){
    		cornerABC = botLeft;
    		cornerCAB = botRight;
    		cornerBCA = topLeft;
    	} else if (cornerABC.equals(topRight)){
    		cornerABC = topLeft;
            cornerBCA = topRight;
            cornerCAB = botLeft;
    	} else if (cornerABC.equals(botRight)){
    		cornerABC = topRight;
            cornerBCA = botRight;
            cornerCAB = topLeft;
    	} else {
    		cornerABC = botRight;
            cornerBCA = botLeft;
            cornerCAB = topRight;
    	}
    }

    @Override
    public void rotateRight(){
    	if (cornerABC.equals(topLeft)){
    		cornerABC = topRight;
    		cornerCAB = topLeft;
    		cornerBCA = botRight;
    	} else if (cornerABC.equals(topRight)){
    		cornerABC = botRight;
    		cornerCAB = topRight;
    		cornerBCA = botLeft;
    	} else if (cornerABC.equals(botRight)){
    		cornerABC = botLeft;
    		cornerCAB = botRight;
    		cornerBCA = topLeft;
    	} else {
    		cornerABC = topLeft;
            cornerBCA = topRight;
            cornerCAB = botLeft;
    	}
    }

	public List<LineSegment> getSides(){
		List<LineSegment> sides = new ArrayList<LineSegment>();
		LineSegment lineAB = new LineSegment(cornerCAB.getPointX(), cornerCAB.getPointY(), cornerABC.getPointX(), cornerABC.getPointY());
		LineSegment lineBC = new LineSegment(cornerABC.getPointX(), cornerABC.getPointY(), cornerBCA.getPointX(), cornerBCA.getPointY());
		LineSegment lineAC = new LineSegment(cornerCAB.getPointX(), cornerCAB.getPointY(), cornerBCA.getPointX(), cornerBCA.getPointY());
		sides.add(lineAB);
		sides.add(lineAC);
		sides.add(lineBC);
		return sides;
	}

	public List<Circle> getCorners(){
		List<Circle> corners = new ArrayList<Circle>();
		Circle circleABC = new Circle(cornerABC.getPointX(), cornerABC.getPointY(), 0);
		Circle circleCAB = new Circle(cornerCAB.getPointX(), cornerCAB.getPointY(), 0);
		Circle circleBCA = new Circle(cornerBCA.getPointX(), cornerBCA.getPointY(), 0);
		corners.add(circleABC);
		corners.add(circleCAB);
		corners.add(circleBCA);
		return corners;
	}

	@Override
	public String getType() {
		return gizmoType;
	}
	
	@Override
    public void trigger(){
		if (color == Color.BLUE){
			color = Color.CYAN;
		}
		else {
			color = Color.BLUE;
		}
    }
	
	@Override
	public String toString(){
		return "Triangle " + getID() + " " + getX()/25 + " " + getY()/25;
	}

    @Override
    public Gizmo moveTo(int x, int y) {
        return new TriangleBumper(gizmoType, id, x, y);
    }


}

