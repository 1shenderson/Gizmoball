package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.gizmo.*;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable   {


	private Walls gws;
	private ArrayList<Gizmo> gizmoList;
	private ArrayList<Ball> ballsList;
	private ArrayList<VerticalLine> lines;

	public Model() {

		ballsList = new ArrayList<Ball>();
		gizmoList = new ArrayList<Gizmo>();
		lines = new ArrayList<VerticalLine>();
		buildArena();
		// Ball position (25, 25) in pixels. Ball velocity (100, 100) pixels per tick

		// Wall size 500 x 500 pixels
		gws = new Walls(0, 0, 500, 500);

		// Lines added in Main
		lines = new ArrayList<VerticalLine>();
	}

	/*
	 * Test arena for demonstration purposes.
	 * builds a small arena to sample collision.
	 *
	 * Build a arena of gizmos and balls using preset
	 * positions
	 *
	 */
	public void buildArena(){
		Ball ball1 = new Ball(10, 20, 100, 100);
		Ball ball2 = new Ball(250, 250, 100 ,100);
		Ball ball3 = new Ball(400, 400, 100, 100);
		addBall(ball1);
		addBall(ball2);
		addBall(ball3);

		addGizmo(2,8,"Circle","circle");
		addGizmo(3,7,"Circle","circle");
		addGizmo(4,6,"Circle","Circle");
		addGizmo(5,5,"Circle","Circle");
		addGizmo(3,7,"Circle","Circle");
		addGizmo(3,9,"Circle","Circle");
		addGizmo(4,10,"Circle","Circle");
		addGizmo(5,11,"Circle","Circle");
		addGizmo(6,12,"Circle","Circle");
		addGizmo(7,13,"Circle","Circle");
		addGizmo(8,13,"Circle","Circle");
		addGizmo(9,13,"Circle","circle");
		addGizmo(10,13,"Circle","Circle");
		addGizmo(10,13,"Circle","Circle");
		addGizmo(11,13,"Circle","Circle");
		addGizmo(12,13,"Circle","Circle");
		addGizmo(13,12,"Square","square");
		addGizmo(14,11,"Square","Square");
		addGizmo(15,10,"Square","Square");
		addGizmo(16,9,"Square","square");
		addGizmo(17,8,"Square","square");
		addGizmo(16,7,"Square","Square");
		addGizmo(15,6,"Square","Square");
		addGizmo(14,5,"Circle","Circle");
		addGizmo(13,5,"Circle","Circle");
		addGizmo(12,5,"Circle","Circle");
		addGizmo(11,5,"Circle","Circle");
		addGizmo(10,5,"Circle","Circle");
		addGizmo(9,5,"Circle","Circle");
		addGizmo(8,5,"Circle","Circle");
		addGizmo(7,5,"Circle","Circle");
		addGizmo(6,5,"Circle","Circle");

		addGizmo(13,8,"Triangle","triangle");
		addGizmo(7,8,"Triangle","triangle");
		addGizmo(15,17,"Triangle","Triangle");
		addGizmo(13,17,"Triangle","Triangle");
		addGizmo(11,17,"Triangle","Triangle");
		addGizmo(7,15,"Triangle","Triangle");
		addGizmo(5,15,"Triangle","Triangle");
		addGizmo(5,2,"Square","Triangle");
		addGizmo(10,2,"Circle","Triangle");
		addGizmo(15,2,"Square","Triangle");


	}

	public void moveBall() {

		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
		for (int i = 0; i < ballsList.size(); i++){
			Ball currentBall = ballsList.get(i);
			currentBall.getExactX();
			if (currentBall != null && !currentBall.stopped()) {
				CollisionDetails cd = timeUntilCollision(currentBall);
				double tuc = cd.getTuc();
				if (tuc > moveTime) {
					// No collision ...
					currentBall = movelBallForTime(currentBall, moveTime);
				} else {
					// We've got a collision in tuc
					currentBall = movelBallForTime(currentBall, tuc);
					// Post collision velocity ...
					currentBall.setVelo(cd.getVelo());
				}
				// Notify observers ... redraw updated view
				this.setChanged();
				this.notifyObservers();
			}
		}
	}

	private Ball movelBallForTime(Ball ball, double time) {

		double newX = 0.0;
		double newY = 0.0;
		double xVel = ball.getVelo().x();
		double yVel = ball.getVelo().y();
		newX = ball.getExactX() + (xVel * time);
		newY = ball.getExactY() + (yVel * time);
		ball.setExactX(newX);
		ball.setExactY(newY);
		return ball;
	}

	private CollisionDetails timeUntilCollision(Ball currBall) {
		// Find Time Until Collision and also, if there is a collision, the new speed vector.
		// Create a physics.Circle from Ball
		Circle ballCircle = currBall.getCircle();
		Vect ballVelocity = currBall.getVelo();
		Vect newVelo = new Vect(0, 0);

		// Now find shortest time to hit a vertical line or a wall line
		double shortestTime = Double.MAX_VALUE;
		double time = 0.0;

		// Time to collide with 4 walls
		ArrayList<LineSegment> lss = gws.getLineSegments();
		for (LineSegment line : lss) {
			time = Geometry.timeUntilWallCollision(line, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(line, currBall.getVelo(), 1.0);
			}
		}

		/*
		 * loops through the gizmo list, checks the type of gizmo and gets the appropriate arraylists of
		 * Circles and/or lines. After that uses the appropriate collision detection methods for
		 * circles/lines/both.
		 */
		 for (Gizmo gizmo : gizmoList) {
	            if (gizmo instanceof CircleBumper) {
	                // Gizmo is a circle bumper
	                Circle circle = ((CircleBumper) gizmo).getCircle();
	                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
	                if (time < shortestTime) {
	                	shortestTime = time;
	                    newVelo = Geometry.reflectCircle(circle.getCenter(), ballCircle.getCenter(), currBall.getVelo(), 1.0);
	                }

	            } else if (gizmo instanceof TriangleBumper) {
	                // Gizmo is a triangle bumper
	            	TriangleBumper triangle = (TriangleBumper) gizmo;
	            	List<LineSegment> sides = triangle.getSides();
	            	List<Circle> corners = triangle.getCorners();
	            	for (LineSegment side : sides){
	                    time = Geometry.timeUntilWallCollision(side,ballCircle,ballVelocity);
	                    if (time < shortestTime) {
	                        shortestTime = time;
	                        newVelo = Geometry.reflectWall(side, currBall.getVelo(), 1.0);
	                    }
	            	}
	            	for (Circle corner : corners){
	                    time = Geometry.timeUntilCircleCollision(corner,ballCircle,ballVelocity);
	                    if (time < shortestTime) {
	                        shortestTime = time;
	                        newVelo = Geometry.reflectCircle(corner.getCenter(), ballCircle.getCenter(), currBall.getVelo(), 1.0);
	                    }
	            	}

	            } else if (gizmo instanceof SquareBumper) {
	                // Gizmo is a square bumper
	                SquareBumper square = (SquareBumper) gizmo;
	                ArrayList<LineSegment> sides = square.getSides();
	                ArrayList<Circle> corners = square.getCorners();
	                for (LineSegment side : sides){
	                    time = Geometry.timeUntilWallCollision(side,ballCircle,ballVelocity);
	                    if (time < shortestTime) {
	                        shortestTime = time;
	                        newVelo = Geometry.reflectWall(side, currBall.getVelo(), 1.0);
	                    }
	                }
	                for (Circle corner : corners){
	                    time = Geometry.timeUntilCircleCollision(corner,ballCircle,ballVelocity);
	                    if (time < shortestTime) {
	                        shortestTime = time;
	                        newVelo = Geometry.reflectCircle(corner.getCenter(), ballCircle.getCenter(), currBall.getVelo(), 1.0);
	                    }
	                }
	            }
		 }

		// Time to collide with any vertical lines
		for (VerticalLine line : lines) {
			LineSegment ls = line.getLineSeg();
			time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectWall(ls, currBall.getVelo(), 1.0);
			}
		}
		return new CollisionDetails(shortestTime, newVelo);
	}


    public void addGizmo(int x, int y, String gizmoType, String gizmoID) {
        Gizmo gizmo;
        switch (gizmoType) {
            case "Square":
                gizmo = new SquareBumper(x, y, 1, 1, gizmoID);
                break;
            case "Triangle":
                gizmo = new TriangleBumper(x, y, 1, 1, gizmoID);
                break;
            case "Circle":
                gizmo = new CircleBumper(x, y, gizmoID);
                break;
            default:
                throw new IllegalArgumentException("Unrecognized gizmo type" + gizmoType + " passed as an argument to addGizmo");
        }
        gizmoList.add(gizmo);
    }

	public ArrayList<VerticalLine> getLines() {
		return lines;
	}

	public void addLine(VerticalLine l) {
        lines.add(l);
    }

	public void addBall(Ball b) {
        ballsList.add(b);
    }

	public void setBallSpeed(int x, int y) {
		for (Ball ball : ballsList){
			ball.setVelo(new Vect(x, y));
		}
	}

    public List<Gizmo> getGizmoList() {
        return gizmoList;
    }

	public List<Ball> getBallList(){
		return ballsList;
	}


}
