package model;

import java.awt.Color;


import java.util.ArrayList;
import java.util.List;
import physics.LineSegment;
import physics.Circle;
public class SquareGizmo {

	private int length;
	private int xPos;
	private int yPos;
	private Color colour;
	private int topLCornerX;
	private int topLCornerY;
	private int botRCornerX;
	private int botRCornerY;

	public SquareGizmo(int x, int y){
		xPos = x;
		yPos = y;
		length = 40;
		colour = Color.BLUE;
		topLCornerX = xPos - (length/2);
		topLCornerY = yPos - (length/2);
		botRCornerX = xPos + (length/2);
		botRCornerY = yPos + (length/2);
	}

	public int getLength() {
		return length;
	}


	public int getX() {
		return xPos;
	}


	public int getY() {
		return yPos;
	}


	public Color getColour(){
		return colour;
	}


	public List<LineSegment> getSides(){
		List<LineSegment> sides = new ArrayList<LineSegment>();
		LineSegment tSide = new LineSegment(topLCornerX, topLCornerY, botRCornerX, topLCornerY);
		LineSegment lSide = new LineSegment(topLCornerX, topLCornerY, topLCornerX, botRCornerY);
		LineSegment rSide = new LineSegment(botRCornerX, topLCornerY, botRCornerX, botRCornerY);
		LineSegment bSide = new LineSegment(topLCornerX, botRCornerY, botRCornerX, botRCornerY);
		sides.add(tSide);
		sides.add(lSide);
		sides.add(rSide);
		sides.add(bSide);
		return sides;
	}

	public List<Circle> getCorners(){
		List<Circle> circleList = new ArrayList<Circle>();
		Circle topLeft = new Circle(topLCornerX, topLCornerY, 0);
		Circle topRight = new Circle(topLCornerX + length, topLCornerY,0);
		Circle botLeft = new Circle(topLCornerX, topLCornerY + length,0);
		Circle botRight = new Circle(botRCornerX, botRCornerY,0);
		circleList.add(topLeft);
		circleList.add(topRight);
		circleList.add(botLeft);
		circleList.add(botRight);
		return circleList;


	}


}
