package model.gizmo;

import java.awt.Color;
import java.util.ArrayList;

import physics.Circle;
import physics.LineSegment;
import model.Ball;
import model.Model;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class Absorber extends AbstractGizmo {
    int width = 1;
    int height = 1;
    int topLeftX;
    int topLeftY;
    int botRightX;
    int botRightY;

    public Absorber(String gizmoType, String id, int x, int y, int x2, int y2) {
        super(gizmoType, id, x, y);
        if (width < 1 || height < 1) {
            throw new IllegalArgumentException((width<0?"width":"height") + " is less than 1 in "
                    + this.getClass() + " constructor");
        }
        this.topLeftX = x * L;
        this.topLeftY = y * L;
        this.botRightX = topLeftX + (x2 * L);
        this.botRightY = topLeftY + (y2 * L);
        color = Color.MAGENTA;
    }

   // @Override
    /**
     * When absorber is triggered, it will spawn a ball in the bottom right corner
     */
   /* public void trigger() {
        if (absorbed) {
            double ballX = botRightX - 0.25 * L; // X position of ball to be spawned by the absorber
            double ballY = botRightY - 0.25 * L; // Y position of ball to be spawned by the absorber
            Ball ball = new Ball("", "", ballX, ballY, velX, velY);
            parent.addBall(ball);
        }
    }*/
    
    	@Override
    	public Color getColour() {
    		return color;
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
    	public String getType() {
    		// TODO Auto-generated method stub
    		return null;
    	}
}
