package model;

import java.io.File;
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

public class Model extends Observable implements Game {

	private ArrayList<VerticalLine> lines;
	private FileHandling file;
    private static int L;
    private static double friction1;
    private static double friction2;
    private static double gravity;
	private Walls gws;
	private List<Ball> ballsList;
	private ArrayList<Gizmo> gizmoList;

	public Model() {
        L = 25; // TODO Assign L through the constructor
        gravity = 25 * L;
        friction1 = 0.025;
        friction2 = 0.025 / L;

		ballsList = new ArrayList<Ball>();
		// Ball position (25, 25) in pixels. Ball velocity (100, 100) pixels per tick
        Ball ball = new Ball("GIZMOTYPE", "ID", 19.75*L, 19.75*L, 0, -30 * L);
        ballsList.add(ball);

		file = new FileHandling();

		// Wall size is 20 by 20 squares (each square is L in width and L in height)
		gws = new Walls(0, 0, (int) (20 * L), (int) (20 * L));

		// Lines added in Main
		lines = new ArrayList<VerticalLine>();

		gizmoList = new ArrayList<Gizmo>();
		//addGizmo(19,19,"Circle","circle");
		addGizmo(19,14,"Square","square");
		//addGizmo(19,19,"Triangle", "triangel1");

        L = 25; // TODO Assign L through the constructor
        gravity = 25 * L;
        friction1 = 0.025;
        friction2 = 0.025 / L;
	}

	public void tick() {
		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
		for (Ball ball: ballsList){
			if (!ball.stopped()) {
				CollisionDetails cd = timeUntilCollision(ball);
				double tuc = cd.getTuc();
				if (tuc > moveTime) {
					// No collision ...
					ball = movelBallForTime(ball, moveTime);
				} else {
                    System.out.printf("\n\nCOLLISION DETECTED");
                    debugPrintVelocity("before", ball.getVelo()); // TODO Remove debug
					// We've got a collision in tuc
					ball = movelBallForTime(ball, tuc);
                    debugPrintVelocity("during", ball.getVelo()); // TODO Remove debug
                    cd = timeUntilCollision(ball); // Update the velocity of the ball, since it changed
					// Post collision velocity ...
					ball.setVelo(cd.getVelo());
                    debugPrintVelocity("after", ball.getVelo()); // TODO Remove debug
				}
				// Ball position changed
				this.setChanged();
			}
            this.notifyObservers(); // Will only notify observers if setChanged was called in this frame
		}
	}

    /**
     * Debug method
     * Will print the velocity formatted to be easier to read if a lot of such lines are printed in series.
     * @param stage What to print after "Velocity" and before ":". Can be an empty string, shouldn't be longer than 6 chars.
     * @param velo Ball which you want to print info about
     */
    private void debugPrintVelocity(String stage, Vect velo) {
        stage += ":";
        System.out.printf("\nVelocity %-7s  X: %+-8.2f Y: %+-8.2f", stage, velo.x(), velo.y());
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
        xVel = xVel * ((1 - (friction1 * time)) - (Math.abs(xVel) * (friction2) * time));
        yVel = yVel * ((1 - (friction1 * time)) - (Math.abs(yVel) * (friction2) * time));
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

        // Time to collide with gizmos
        for (Gizmo gizmo : gizmoList) {
            if (gizmo instanceof CircleBumper) {
                // Gizmo is a circle bumper
                Circle circle = ((CircleBumper) gizmo).getCircle();
                time = Geometry.timeUntilCircleCollision(circle, ballCircle, ballVelocity);
                if (time < shortestTime) {
                	System.out.println("circle hit");
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
            } else if (gizmo instanceof AbstractFlipper) {
                // Gizmo is either a left or a right flipper
                // TODO Add handling of flipper collisions
            } else if (gizmo instanceof Absorber) {
                // Gizmo is an absorber. Gizmos do not collide with absorbers; instead, they get absorbed by them.
                // TODO Add handling of absorber "collisions"
            } else {
                // Gizmo is unrecognized - we have no handling for that gizmo. This should not happen unless we forgot something.
                throw new RuntimeException("Unrecognized gizmo detected in the list of gizmos.");
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

//	public void saveBoard(File filed, String fileName){
//        file.save(gizmoList, filed, fileName);
//    }

//    public void loadBoard(File filed){
//        ArrayList<ArrayList<Object>> gizmoLoad = file.load(filed);
//        for(ArrayList<Object> gizmoInfo : gizmoLoad){
//
//            if(gizmoInfo.get(0).equals("Line")){
//            	addLine(new VerticalLine((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3), (int)gizmoInfo.get(4)));
//            }
//            else if(gizmoInfo.get(0).equals("Circle")){
//            	addCircle(new CircleGizmo((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3)));
//            }
//            else if(gizmoInfo.get(0).equals("Ball")){
//            	addBall(new Ball((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (double)gizmoInfo.get(2), (double)gizmoInfo.get(3), (double)gizmoInfo.get(4), (double)gizmoInfo.get(5)));
//            }
//            else{
//            	addSquare(new SquareGizmo((String) gizmoInfo.get(0), (String)gizmoInfo.get(1), (int)gizmoInfo.get(2), (int)gizmoInfo.get(3)));
//            }
//        }
//    }

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

	public List<Ball> getBallList(){
		return ballsList;
	}

    public List<Gizmo> getGizmoList() {
        return gizmoList;
    }

    @Override
    public void trigger(int keyID) {

    }

    @Override
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
            case "RightFlipper":
                gizmo = new FlipperRight(x, y, gizmoID);
                break;
            case "LeftFlipper":
                gizmo = new FlipperLeft(x, y, gizmoID);
                break;
            case "Absorber":
                throw new IllegalArgumentException("Absorber  needs a width and a height as arguments in addition to the rest.");
            default:
                throw new IllegalArgumentException("Unrecognized gizmo type" + gizmoType + " passed as an argument to addGizmo");
        }
        gizmoList.add(gizmo);
    }

    @Override
    public void addAbsorber(int x, int y, int width, int height, String gizmoID) {
        Gizmo absorber = new Absorber(x, y, width, height, 0, 30*L, this, gizmoID);
        gizmoList.add(absorber);
    }

    @Override
    public void removeGizmo(int x, int y) {
        // TODO Code for removing a gizmo from a location
    }

    @Override
    public void removeGizmo(String gizmoID) {
        // TODO Code for removing a gizmo with a certain ID
    }

    @Override
    public void addTriggerKey(String gizmoID, int keyID) {
        // TODO Code for adding a key as a trigger for a gizmo
    }

    @Override
    public void addTriggerGizmo(String gizmoID, String gizmoTriggerID) {
        // TODO Code for making a gizmo trigger another gizmo
    }

    @Override
    public void removeTriggerKey(String gizmoID, int keyID) {
        // TODO Code for removing a key trigger from a gizmo
    }

    @Override
    public void removeTriggerGizmo(String gizmoID, String gizmoTriggerID) {
        // TODO Code for removing a gizmo trigger from a gizmo
    }

    @Override
    public int[][] getMap() {
        // TODO Code for returning the map. NOTE: I think this is redundant as we don't store gizmos in an array.
        return new int[0][];
    }
}


