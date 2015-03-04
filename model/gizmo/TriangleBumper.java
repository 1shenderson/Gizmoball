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

	//All possible values of x and y for a triangle
	private int x;
	private int y;
	private int bottomY;
	private int rightX;


    public TriangleBumper(int x, int y, int height, int width, String id) {
        super(x, y, id);
        color = Color.BLUE;
        height = height * L;
        width = width * L;
        this.x = x * L;
        this.y = y * L;
        bottomY = this.y + height;
        rightX = this.x + width;
    }

    @Override
    public int getX(){
    	return x;
    }

    public int getY(){
    	return y;
    }

    public int[] getAllXPos(){
    	int[] xCoordinates = {x,x,rightX};
    	return xCoordinates;
    }

    public int[] getAllYPos(){
    	int[] yCoordinates = {y, bottomY, bottomY};
    	return yCoordinates;
    }

	public List<LineSegment> getSides(){
		List<LineSegment> sides = new ArrayList<LineSegment>();
		LineSegment lineAB = new LineSegment(x, y, x, bottomY);
		LineSegment lineBC = new LineSegment(x, bottomY, rightX, bottomY);
		LineSegment lineAC = new LineSegment(x, y, rightX, bottomY);
		sides.add(lineAB);
		sides.add(lineAC);
		sides.add(lineBC);
		return sides;
	}

	public List<Circle> getCorners(){
		List<Circle> corners = new ArrayList<Circle>();
		Circle topCorner = new Circle(x, y, 0);
		Circle botLCorner = new Circle(x, bottomY, 0);
		Circle botRCorner = new Circle(rightX, bottomY, 0);
		corners.add(topCorner);
		corners.add(botLCorner);
		corners.add(botRCorner);
		return corners;
	}


}