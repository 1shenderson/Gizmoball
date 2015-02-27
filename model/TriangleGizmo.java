package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;
import physics.Vect;


public class TriangleGizmo {

	private int xPos;
	private int yPos;
	private Color colour;
	private int length;
	private int leftSideX;
	private int rightSideX;
	private int bottomSideY;
	private int topSideY;


	public TriangleGizmo(int x, int y){
		length = 100;
		colour = Color.BLUE;
		xPos = x;
		yPos = y;
		rightSideX = xPos + (length/2);
		leftSideX = xPos - (length/2);
		bottomSideY = yPos + (length/2);
		topSideY = yPos - (length/2);

	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public Color getColour() {
		return colour;
	}

	public int getLength() {
		return length;
	}

	public List<LineSegment> getSides(){
		List<LineSegment> sides = new ArrayList<LineSegment>();
		LineSegment lineAB = new LineSegment(leftSideX, topSideY, rightSideX, bottomSideY);
		LineSegment lineAC = new LineSegment(leftSideX, topSideY, leftSideX, bottomSideY);
		LineSegment lineBC = new LineSegment(leftSideX, bottomSideY, rightSideX, bottomSideY);
		sides.add(lineAB);
		sides.add(lineAC);
		sides.add(lineBC);
		return sides;
	}

	public List<Circle> getCorners(){
		List<Circle> corners = new ArrayList<Circle>();
		Circle topCorner = new Circle(leftSideX, topSideY, 0);
		Circle botLCorner = new Circle(leftSideX, bottomSideY, 0);
		Circle botRCorner = new Circle(rightSideX, bottomSideY, 0);
		corners.add(topCorner);
		corners.add(botLCorner);
		corners.add(botRCorner);
		return corners;
	}


}
