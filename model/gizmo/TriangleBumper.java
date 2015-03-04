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
	private int bottomY;
	private int rightX;
	private int topCornerX;
	private int topCornerY;
	private int leftCornerX;
	private int leftCornerY;
	private int rightCornerX;
	private int rightCornerY;
	private int height;
	private int width;


    public TriangleBumper(int x, int y, int height, int width, String id) {
        super(x, y, id);
        color = Color.BLUE;
        this.height = height * L;
        this.width = width * L;
        this.x = x * L;
        this.y = y * L;
        bottomY = this.y + this.height;
        rightX = this.x + this.width;
        setTopCorner(this.x, this.y);
        setLeftCorner(topCornerX, topCornerY + this.height);
        setRightCorner(topCornerX + this.width, topCornerY);
    }

    @Override
    public int getX(){
    	return x;
    }

    public int getY(){
    	return y;
    }

    public void setTopCorner(int pointX, int pointY){
    	topCornerX = pointX;
    	topCornerY = pointY;
    }

    public void setLeftCorner(int pointX, int pointY){
    	leftCornerX = pointX;
    	leftCornerY = pointY;
    }

    public void setRightCorner(int pointX, int pointY){
    	rightCornerX = pointX;
    	rightCornerY = pointY;
    }


    public int[] getAllXPos(){
    	int[] xCoordinates = {topCornerX, rightCornerX, leftCornerX};
    	return xCoordinates;
    }

    public int[] getAllYPos(){
    	int[] yCoordinates = {topCornerY, rightCornerY, leftCornerY};
    	return yCoordinates;
    }

    //TODO make a better rotate method
    @Override
    public void rotateLeft(){
    	if (topCornerX == rightX && topCornerY == y){
    		setTopCorner(topCornerX - width, topCornerY);
    		setLeftCorner(topCornerX, topCornerY + height);
    		setRightCorner(topCornerX + width, topCornerY);
    	} else if (topCornerX == x && topCornerY == y){
    		setTopCorner(topCornerX, topCornerY + height);
    		setLeftCorner(topCornerX + width, topCornerY);
    		setRightCorner(topCornerX, topCornerY - height);
    	} else if (topCornerX == x && topCornerY == bottomY){
    		setTopCorner(topCornerX + width, topCornerY);
    		setLeftCorner(topCornerX, topCornerY - height);
    		setRightCorner(topCornerX - width, topCornerY);
    	} else {
    		setTopCorner(topCornerX, topCornerY - height);
    		setLeftCorner(topCornerX - width, topCornerY);
    		setRightCorner(topCornerX, topCornerY + height);
    	}
    }

    @Override
    public void rotateRight(){
    	if (topCornerX == rightX && topCornerY == y){
    		setTopCorner(topCornerX, topCornerY + height);
    		setLeftCorner(topCornerX, topCornerY - height);
    		setRightCorner(topCornerX - width, topCornerY);
    	} else if (topCornerX == x && topCornerY == y){
    		setTopCorner(topCornerX + width, topCornerY);
    		setLeftCorner(topCornerX - width, topCornerY);
    		setRightCorner(topCornerX, topCornerY + height);
    	} else if (topCornerX == x && topCornerY == bottomY){
    		setTopCorner(topCornerX, topCornerY - height);
    		setLeftCorner(topCornerX, topCornerY + height);
    		setRightCorner(topCornerX + width, topCornerY);
    	} else {
    		setTopCorner(topCornerX - width, topCornerY);
    		setLeftCorner(topCornerX + width, topCornerY);
    		setRightCorner(topCornerX, topCornerY - height);
    	}
    }

	public List<LineSegment> getSides(){
		List<LineSegment> sides = new ArrayList<LineSegment>();
		LineSegment lineAB = new LineSegment(topCornerX, topCornerY, leftCornerX , leftCornerY);
		LineSegment lineBC = new LineSegment(leftCornerX, leftCornerY, rightCornerX, rightCornerY);
		LineSegment lineAC = new LineSegment(rightCornerX, rightCornerY, topCornerX, topCornerY);
		sides.add(lineAB);
		sides.add(lineAC);
		sides.add(lineBC);
		return sides;
	}

	public List<Circle> getCorners(){
		List<Circle> corners = new ArrayList<Circle>();
		Circle topLCorner = new Circle(x, y, 0);
		Circle topRCorner = new Circle(rightX, y, 0);
		Circle botRCorner = new Circle(rightX, bottomY, 0);
		corners.add(topLCorner);
		corners.add(topRCorner);
		corners.add(botRCorner);
		return corners;
	}


}
