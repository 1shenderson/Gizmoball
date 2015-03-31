package model.gizmo;

import physics.Circle;
import physics.LineSegment;
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
    int botLeftX;
    int botLeftY;
    int botRightX;
    int botRightY;
    int topRightX;
    int topRightY;
    String gizmoType;
    private int x;
    private int y;

    public AbstractSquare(String gizmoType, String id, int x, int y) {
        super(gizmoType, id, x, y);
        this.width = 1;
        this.height = 1;
        setPoints(x, y);
        this.x = x * L;
        this.y = y * L;
        this.gizmoType = gizmoType;
    }
    
    void setPoints(int startX, int startY) {
    	this.topLeftX = startX * L;
        this.topLeftY = startY * L;
        this.botLeftX = topLeftX;
        this.botLeftY = topLeftY + (height * L);
        this.topRightX = topLeftX + (width * L);
        this.topRightY = topLeftY;
        this.botRightX = topLeftX + (width * L);
        this.botRightY = topLeftY + (height * L);
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
        LineSegment tSide = new LineSegment(topLeftX, topLeftY, topRightX, topRightY);
        LineSegment lSide = new LineSegment(topLeftX, topLeftY, botLeftX, botLeftY);
        LineSegment rSide = new LineSegment(topRightX, topRightY, botRightX, botRightY);
        LineSegment bSide = new LineSegment(botLeftX, botLeftY, botRightX, botRightY);
        sides.add(tSide);
        sides.add(lSide);
        sides.add(rSide);
        sides.add(bSide);
        return sides;
    }

    public ArrayList<Circle> getCorners(){
        ArrayList<Circle> circleList = new ArrayList<Circle>();
        Circle topLeft = new Circle(topLeftX, topLeftY, 0);
        Circle topRight = new Circle(topRightX, topRightY,0);
        Circle botLeft = new Circle(topLeftX, botLeftY,0);
        Circle botRight = new Circle(botRightX, botRightY,0);
        circleList.add(topLeft);
        circleList.add(topRight);
        circleList.add(botLeft);
        circleList.add(botRight);
        return circleList;
    }

	@Override
	public int[] getAllXPos() {
		int[] xPos = {topLeftX, topRightX, botRightX, botLeftX,};
		return xPos;
	}

	@Override
	public int[] getAllYPos() {
		int[] yPos = {topLeftY, topRightY, botRightY, botLeftY};
		return yPos;
	}

    public Gizmo moveTo(int x, int y) {
        return new AbstractSquare(gizmoType, id, x, y);
    }
}
