package model.gizmo;

import java.awt.Color;


import java.util.ArrayList;
import physics.LineSegment;
import physics.Circle;
public class SquareGizmo {

	private int width;
	private int xPos;
	private int yPos;
	private int topLCornerX;
	private int topLCornerY;
	private int botRCornerX;
	private int botRCornerY;

	private Color colour;

	public SquareGizmo(int x, int y){
		xPos = x;
		yPos = y;
		width = 20;
		topLCornerX = xPos - (width/2);
		topLCornerY = yPos - (width/2);
		botRCornerX = xPos + (width/2);
		botRCornerY = yPos + (width/2);
		colour = Color.BLUE;

	}

	public int getWidth() {
		return width;
	}


	public int getxPos() {
		return xPos;
	}


	public int getyPos() {
		return yPos;
	}

	public int getTLeftX(){
		return topLCornerX;
	}

	public int getTLeftY(){
		return topLCornerY;
	}


	public Color getColour(){
		return colour;
	}


	public ArrayList<LineSegment> getSides(){
		ArrayList<LineSegment> sides = new ArrayList<LineSegment>();
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

	public ArrayList<Circle> getCorners(){
		ArrayList<Circle> circleList = new ArrayList<Circle>();
		Circle topLeft = new Circle(topLCornerX, topLCornerY, 0);
		Circle topRight = new Circle(topLCornerX + width, topLCornerY,0);
		Circle botLeft = new Circle(topLCornerX, topLCornerY + width,0);
		Circle botRight = new Circle(botRCornerX, botRCornerY,0);
		circleList.add(topLeft);
		circleList.add(topRight);
		circleList.add(botLeft);
		circleList.add(botRight);
		return circleList;


	}


}