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
    public static int L;
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
        //Ball ball = new Ball("GIZMOTYPE", "ID", 19.50*L, 18.75*L, 0, -30 * L);
       // ballsList.add(ball);

		file = new FileHandling();

		// Wall size is 20 by 20 squares (each square is L in width and L in height)
		gws = new Walls(0, 0, (int) (20 * L), (int) (20 * L));

		// Lines added in Main
		lines = new ArrayList<VerticalLine>();

		gizmoList = new ArrayList<Gizmo>();
		//buildArena();

        L = 25; // TODO Assign L through the constructor
        friction1 = 0.025;
        friction2 = 0.025 / L;
        
//        buildArena();
	}

		public void buildArena(){
//			Ball ball1 = new Ball("ball","ball",250, 250, 100 ,100);
//			Ball ball2 = new Ball("ball","ball",10, 20, 100, 100);
//			Ball ball3 = new Ball("ball","ball",400, 400, 100, 100);
//			Ball ball = new Ball("ball","ball", 17 * L, 1 * L, -100, 100);
//
//			addBall(ball);
//			addBall(ball1);
//			addBall(ball2);
//			addBall(ball3);
//
//			addGizmo(2,8,"Circle","circle");
//			addGizmo(3,7,"Circle","circle");
//			addGizmo(4,6,"Circle","Circle");
//			addGizmo(5,5,"Circle","Circle");
//			addGizmo(3,7,"Circle","Circle");
//			addGizmo(3,9,"Circle","Circle");
//			addGizmo(4,10,"Circle","Circle");
//			addGizmo(5,11,"Circle","Circle");
//			addGizmo(6,12,"Circle","Circle");
//			addGizmo(7,13,"Circle","Circle");
//			addGizmo(8,13,"Circle","Circle");
//			addGizmo(9,13,"Circle","circle");
//			addGizmo(10,13,"Circle","Circle");
//			addGizmo(10,13,"Circle","Circle");
//			addGizmo(11,13,"Circle","Circle");
//			addGizmo(12,13,"Circle","Circle");
//			addGizmo(13,12,"Square","square");
//			addGizmo(14,11,"Square","Square");
//			addGizmo(15,10,"Square","Square");
//			addGizmo(16,9,"Square","square");
//			addGizmo(17,8,"Square","square");
//			addGizmo(16,7,"Square","Square");
//			addGizmo(15,6,"Square","Square");
//			addGizmo(14,5,"Circle","Circle");
//			addGizmo(13,5,"Circle","Circle");
//			addGizmo(12,5,"Circle","Circle");
//			addGizmo(11,5,"Circle","Circle");
//			addGizmo(10,5,"Circle","Circle");
//			addGizmo(9,5,"Circle","Circle");
//			addGizmo(8,5,"Circle","Circle");
//			addGizmo(7,5,"Circle","Circle");
//			addGizmo(6,5,"Circle","Circle");
//
//			addGizmo(13,8,"Triangle","triangle");
//			addGizmo(7,8,"Triangle","triangle");
//			addGizmo(15,17,"Triangle","Triangle");
//			addGizmo(13,17,"Triangle","Triangle");
//			addGizmo(11,17,"Triangle","Triangle");
//			addGizmo(7,15,"Triangle","Triangle");
//			addGizmo(5,15,"Triangle","Triangle");
//			addGizmo(5,2,"Square","Triangle");
//			addGizmo(10,2,"Circle","Triangle");
//			addGizmo(15,2,"Square","Triangle");


	}

	public void tick() {
		double moveTime = 0.05; // 0.05 = 20 times per second as per Gizmoball
		for (int i = 0; i < ballsList.size(); i++){
            Ball ball = ballsList.get(i);
			if (!ball.stopped()) {
				CollisionDetails cd = timeUntilCollision(ball);
				double tuc = cd.getTuc();
				if (tuc > moveTime) {
					// No collision ...
                    if (ball.ignoreAbsorber()) {
                        ball.setIgnoreAbsorber(false);
                    }
					ball = movelBallForTime(ball, moveTime);
				} else {
//                    System.out.printf("\n\nCOLLISION DETECTED");    // TODO Remove debug
//                    debugPrintVelocity("before", ball.getVelo());   // TODO Remove debug
					// We've got a collision in tuc
					ball = movelBallForTime(ball, tuc);
//                    debugPrintVelocity("during", ball.getVelo());   // TODO Remove debug
                    cd = timeUntilCollision(ball); // Update the velocity of the ball, since it changed
					// Post collision velocity ...
                    if (cd.getGizmo() != null) {
                        // Ball is going to bounce off a gizmo. Here, we can have different cases for different gizmos
                        Gizmo gizmo = cd.getGizmo();
                        if (gizmo instanceof Absorber) {
                            // Going to collide with an absorber
                            if (!ball.ignoreAbsorber()) {
                                // Ball is going to be absorbed by the absorber
                                ballsList.remove(i); i--;
                                Absorber absorber = (Absorber) gizmo;
                                absorber.absorb();
                            }
                        }
                        // Now trigger all gizmos this gizmo is supposed to trigger.
                        gizmo.sendTrigger();
                    }
					ball.setVelo(cd.getVelo());
//                    debugPrintVelocity("after", ball.getVelo());    // TODO Remove debug
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
        Gizmo target = null; // Gizmo we will collide with

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

        // Time to collide with any vertical lines
        for (VerticalLine line : lines) {
            LineSegment ls = line.getLineSeg();
            time = Geometry.timeUntilWallCollision(ls, ballCircle, ballVelocity);
            if (time < shortestTime) {
                shortestTime = time;
                newVelo = Geometry.reflectWall(ls, currBall.getVelo(), 1.0);
            }
        }

        // Time to collide with gizmos
        for (Gizmo gizmo : gizmoList) {
        	List<LineSegment> sides = gizmo.getSides();
        	List<Circle> corners = gizmo.getCorners();
        	for (Circle corner : corners){
                time = Geometry.timeUntilCircleCollision(corner,ballCircle,ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectCircle(corner.getCenter(), ballCircle.getCenter(), currBall.getVelo(), 1.0);
                    target = gizmo;
                }
        	}
        	for (LineSegment side : sides){
                time = Geometry.timeUntilWallCollision(side,ballCircle,ballVelocity);
                if (time < shortestTime) {
                    shortestTime = time;
                    newVelo = Geometry.reflectWall(side, currBall.getVelo(), 1.0);
                    target = gizmo;
                }
        	}
        }

/*
        //ball to ball collision
        for (Ball ball: ballsList){
        	if (!currBall.equals(ball)){
        		time = Geometry.timeUntilBallBallCollision(ballCircle, ballVelocity, ball.getCircle(), ball.getVelo());
        		if (time < shortestTime){
        			shortestTime = time;
                    newVelo = Geometry.reflectBalls(ballCircle.getCenter(), 100.0, ballVelocity, ball.getCircle().getCenter(), 100.0, ball.getVelo());
        		}
        	}
        }
*/

        if (target == null) {
            return new CollisionDetails(shortestTime, newVelo);
        } else {
            return new CollisionDetails(shortestTime, newVelo, target);
        }

	}
	
	public void saveBoard(String fileName){
        file.save(gizmoList, fileName);
    }

    public void loadBoard(File filed){
    	ArrayList<ArrayList<Object>> gizmoInfo = file.load(filed);
        for(int i = 0; i < gizmoInfo.size(); i++){
        	ArrayList<Object> gizmoLoad = gizmoInfo.get(i);
        	if(gizmoLoad.get(0).equals("Ball")){
        		addBall(new Ball((String) gizmoLoad.get(0), (String) gizmoLoad.get(1), (double) gizmoLoad.get(2), (double) gizmoLoad.get(3), (double) gizmoLoad.get(4), (double) gizmoLoad.get(5)));
        	}
        	else if(gizmoLoad.get(0).equals("Rotate")){
        		rotate((String) gizmoLoad.get(1));
        	}
        	else if(gizmoLoad.get(0).equals("Absorber")){
        		addAbsorber((String) gizmoLoad.get(0), (String) gizmoLoad.get(1), (int) gizmoLoad.get(2), (int) gizmoLoad.get(3), (int) gizmoLoad.get(4), (int) gizmoLoad.get(5));
        	}
        	else if(gizmoLoad.get(0).equals("KeyConnect")){
        		System.out.println("Recognised KeyConnect for " + (String) gizmoLoad.get(1) + " " + (int) gizmoLoad.get(2) + " " + (String) gizmoLoad.get(3) + " " + (String) gizmoLoad.get(4));
        	}
        	else if(gizmoLoad.get(0).equals("Connect")){
        		System.out.println("Recognised Connect for " + (String) gizmoLoad.get(1) + " " + (String) gizmoLoad.get(2));
        	}
        	else {
        		addGizmo((String) gizmoLoad.get(0), (String) gizmoLoad.get(1), (int) gizmoLoad.get(2), (int) gizmoLoad.get(3));
        	}
        }
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
    public void addGizmo(String gizmoType, String gizmoID, int x, int y) {
        Gizmo gizmo;
        switch (gizmoType) {
            case "Square":
                gizmo = new SquareBumper(gizmoType, gizmoID, x, y);
                break;
            case "Triangle":
                gizmo = new TriangleBumper(gizmoType, gizmoID, x, y);
                break;
            case "Circle":
                gizmo = new CircleBumper(gizmoType, gizmoID, x, y);
                break;
            case "RightFlipper":
                gizmo = new FlipperRight(gizmoType, gizmoID, x, y);
                break;
            case "LeftFlipper":
                gizmo = new FlipperLeft(gizmoType, gizmoID, x, y);
                break;
            case "Absorber":
                throw new IllegalArgumentException("Absorber  needs a width and a height as arguments in addition to the rest.");
            default:
                throw new IllegalArgumentException("Unrecognized gizmo type" + gizmoType + " passed as an argument to addGizmo");
        }
        gizmoList.add(gizmo);
    }
    
    public void rotate(String id){
    	for(Gizmo gizmo: gizmoList){
    		if(gizmo.getID().equals(id)){
    			gizmo.rotateRight();
    		}
    	}
    }
    
    @Override
    public void addAbsorber(String gizmoType, String id, int x, int y, int width, int height) {
        Absorber absorber = new Absorber(gizmoType, id, x, y, width, height, this);
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


