package model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.gizmo.CircleGizmo;
import model.gizmo.SquareGizmo;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable {

	private ArrayList<VerticalLine> lines;
	private FileHandling file;
    private static final double friction1 = 0.025; // Per second
    private static final double friction2 = 0.025; // Per L
    private static final double gravity = 625; // TODO Make gravity a 25L variable, not a 625 constant.
	private Walls gws;
	private List<Ball> ballsList;
	private List<CircleGizmo> circlesList;
	private List<SquareGizmo> squaresList;
	private ArrayList<ArrayList<Object>> gizmoList;

	public Model() {

		ballsList = new ArrayList<Ball>();
		circlesList = new ArrayList<CircleGizmo>();
		squaresList = new ArrayList<SquareGizmo>();
		// Ball position (25, 25) in pixels. Ball velocity (100, 100) pixels per tick
        Ball ball = new Ball("GIZMOTYPE", "ID", 475, 475, 0, -625);
        ballsList.add(ball);

		file = new FileHandling();

		// Wall size 500 x 500 pixels
		gws = new Walls(0, 0, 500, 500);

		// Lines added in Main
		lines = new ArrayList<VerticalLine>();
		
		gizmoList = new ArrayList<ArrayList<Object>>();
		
	}
    
	public void moveBall() {
		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
		for (Ball ball: ballsList){
			ball.getExactX();
			if (!ball.stopped()) {
				CollisionDetails cd = timeUntilCollision(ball);
				double tuc = cd.getTuc();
				if (tuc > moveTime) {
					// No collision ...
					ball = movelBallForTime(ball, moveTime);
				} else {
                    System.out.printf("\nCOLLISION DETECTED");
                    debugPrintVelocity("before", ball); // TODO Remove debug
					// We've got a collision in tuc
					ball = movelBallForTime(ball, tuc);
                    debugPrintVelocity("during", ball); // TODO Remove debug
					// Post collision velocity ...
					ball.setVelo(cd.getVelo());
                    debugPrintVelocity("after", ball); // TODO Remove debug
				}
				// Notify observers ... redraw updated view
				this.setChanged();
			}
            this.notifyObservers(); // Will only notify observers if setChanged was called this frame
		}
	}

    /**
     * Debug method
     * Will print the velocity of the ball formatted to be easier to read if a lot of such lines are printed in series.
     * @param stage What to print after "Velocity" and before ":". Can be an empty string, shouldn't be longer than 6 chars.
     * @param ball Ball which you want to print info about
     */
    private void debugPrintVelocity(String stage, Ball ball) {
        stage += ":";
        System.out.printf("\nVelocity %-7s  X: %+-8.2f Y: %+-8.2f", stage, ball.getVelo().x(), ball.getVelo().y());
    }

	private Ball movelBallForTime(Ball ball, double time) {
        // Initiate position variables
		double newX = 0.0;
		double newY = 0.0;
        // Initiate velocity variables
		double xVel = ball.getVelo().x();
		double yVel = ball.getVelo().y();
        // Calculate gravity
        yVel = yVel + (gravity * time);
        // Calculate friction
        xVel = xVel * ((1 - (friction1 * time)) - (Math.abs(xVel) * (friction2 / 25) * time)); // TODO Change 25 constant to variable L
        yVel = yVel * ((1 - (friction1 * time)) - (Math.abs(yVel) * (friction2 / 25) * time)); // TODO Change 25 constant to variable L
        // Find out where the ball should end up next frame
		newX = ball.getExactX() + (xVel * time);
		newY = ball.getExactY() + (yVel * time);
        // Set new velocity values
        ball.setVelo(new Vect(xVel, yVel));
        // Set new position values
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


		// Time to collide with Circles
		for (CircleGizmo circle: circlesList){
			Circle currCircle = circle.getCircle();
			time = Geometry.timeUntilCircleCollision(currCircle, ballCircle, ballVelocity);
			if (time < shortestTime) {
				shortestTime = time;
				newVelo = Geometry.reflectCircle(Vect.ZERO, currBall.getVelo(), currBall.getVelo(), 1.0);
			}
		}

		// Time to collide with squares
		for (SquareGizmo square:squaresList){
			ArrayList<LineSegment> sides = square.getSides();
			ArrayList<Circle> corners = square.getCorners();
			for (LineSegment currSide:sides){
				time = Geometry.timeUntilWallCollision(currSide,ballCircle,ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectWall(currSide, currBall.getVelo(), 1.0);	
				}
			}
			for (Circle corner:corners){
				time = Geometry.timeUntilCircleCollision(corner,ballCircle,ballVelocity);
				if (time < shortestTime) {
					shortestTime = time;
					newVelo = Geometry.reflectCircle(Vect.ZERO, currBall.getVelo(), currBall.getVelo(), 1.0);
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
	
	public void saveBoard(File filed, String fileName){
        file.save(gizmoList, filed, fileName);
    }

    public void loadBoard(File filed){
        ArrayList<ArrayList<Object>> gizmoLoad = file.load(filed);
        for(ArrayList<Object> gizmoInfo : gizmoLoad){
            
            if(gizmoInfo.get(0).equals("Line")){
            	addLine(new VerticalLine((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3), (int)gizmoInfo.get(4)));
            }
            else if(gizmoInfo.get(0).equals("Circle")){
            	addCircle(new CircleGizmo((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3)));
            }
            else if(gizmoInfo.get(0).equals("Ball")){
            	addBall(new Ball((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (double)gizmoInfo.get(2), (double)gizmoInfo.get(3), (double)gizmoInfo.get(4), (double)gizmoInfo.get(5)));
            }
            else{
            	addSquare(new SquareGizmo((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3)));
            }
        }
    }

	public ArrayList<VerticalLine> getLines() {
		return lines;
	}

	public void addLine(VerticalLine l) {
        lines.add(l);
        gizmoList.add(l.getGizmoInfo());
    }
	
	public void addCircle(CircleGizmo c) {
        circlesList.add(c);
        gizmoList.add(c.getCircleInfo());
    }
	
	public void addSquare(SquareGizmo s) {
        squaresList.add(s);
        gizmoList.add(s.getSquareInfo());
    }
	
	public void addBall(Ball b) {
        ballsList.add(b);
        gizmoList.add(b.getBallInfo());
    }

	public void setBallSpeed(int x, int y) {
		for (Ball ball : ballsList){
			ball.setVelo(new Vect(x, y));
		}
	}

	public List<Ball> getBallList(){
		return ballsList;
	}

	public List<CircleGizmo> getCircleList(){
		return circlesList;
	}

	public List<SquareGizmo> getSquareList(){
		return squaresList;
	}
}


