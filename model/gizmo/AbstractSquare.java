package model.gizmo;

import physics.Circle;
import physics.LineSegment;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Contains methods all square gizmos will have in common (square and absorber)
 * @author Grzegorz Sebastian Korkosz
 */
public class AbstractSquare extends AbstractGizmo {
    int width;
    int height;
    int topLeftX;
    int topLeftY;
    int botRightX;
    int botRightY;

    public AbstractSquare(String gizmoType, String id, int x, int y) {
        super(gizmoType, id, x, y);
        this.width = width * L;
        this.height = height * L;
        this.topLeftX = x * L;
        this.topLeftY = y * L;
        this.botRightX = topLeftX + (width * L);
        this.botRightY = topLeftY + (height * L);
        color = Color.RED;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getBotRightX() {
        return botRightX;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getBotRightY() {
        return botRightY;
    }

    public ArrayList<LineSegment> getSides(){
        ArrayList<LineSegment> sides = new ArrayList<LineSegment>();
        LineSegment tSide = new LineSegment(topLeftX, topLeftY, botRightX, topLeftY);
        LineSegment lSide = new LineSegment(topLeftX, topLeftY, topLeftX, botRightY);
        LineSegment rSide = new LineSegment(botRightX, topLeftY, botRightX, botRightY);
        LineSegment bSide = new LineSegment(topLeftX, botRightY, botRightX, botRightY);
        sides.add(tSide);
        sides.add(lSide);
        sides.add(rSide);
        sides.add(bSide);
        return sides;
    }

    public ArrayList<Circle> getCorners(){
        ArrayList<Circle> circleList = new ArrayList<Circle>();
        Circle topLeft = new Circle(topLeftX, topLeftY, 0);
        Circle topRight = new Circle(topLeftX + width, topLeftY,0);
        Circle botLeft = new Circle(topLeftX, topLeftY + width,0);
        Circle botRight = new Circle(botRightX, botRightY,0);
        circleList.add(topLeft);
        circleList.add(topRight);
        circleList.add(botLeft);
        circleList.add(botRight);
        return circleList;
    }

	@Override
	public int[] getAllXPos() {
		int[] xPos = {topLeftX, topLeftX, botRightX, botRightX,};
		return xPos;
	}

	@Override
	public int[] getAllYPos() {
		int[] yPos = {topLeftY, botRightY, botRightY, topLeftY};
		return yPos;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
