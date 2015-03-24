package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import model.gizmo.*;
import physics.Circle;
import physics.Geometry;
import physics.LineSegment;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Model extends Observable implements Board {

	private ArrayList<VerticalLine> lines;
	private FileHandling file;
    public int L;
    private double friction1;
    private double friction2;
    private double gravity;
	private Walls gws;
	private ArrayList<Ball> ballsList;
	private ArrayList<Gizmo> gizmoList;
	private List<ArrayList<Object>> triggerList;
	private Map<String, Integer> rotateMap;

	public Model(int L) {
        this.L = L; // TODO Assign L through the constructor
        gravity = L * L;
        friction1 = 0.025;
        friction2 = 0.025 / L;

		ballsList = new ArrayList<Ball>();

		file = new FileHandling();

		// Wall size is 20 by 20 squares (each square is L in width and L in height)
		gws = new Walls(0, 0, (int) (20 * L), (int) (20 * L));

		// Lines added in Main
		lines = new ArrayList<VerticalLine>();

		gizmoList = new ArrayList<Gizmo>();
		
		triggerList = new ArrayList<ArrayList<Object>>();
		
		rotateMap = new HashMap<String, Integer>();


        L = 25; // TODO Assign L through the constructor
        friction1 = 0.025;
        friction2 = 0.025 / L;
        
	}



	public void tick() {
		double moveTime = 1.0 / 60;
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
        file.save(gizmoList, ballsList, triggerList, rotateMap, fileName);
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
				addTriggerKey((String) gizmoLoad.get(0), (String) gizmoLoad.get(4), (int) gizmoLoad.get(2), (String)gizmoLoad.get(3));         	
        	}
        	else if(gizmoLoad.get(0).equals("Connect")){
        		System.out.println("Recognised Connect for " + (String) gizmoLoad.get(1) + " " + (String) gizmoLoad.get(2));
				addTriggerGizmo((String) gizmoLoad.get(0), (String) gizmoLoad.get(2), (String) gizmoLoad.get(1));
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
        setChanged();
        notifyObservers();
    }

    public void addBall(String id, int x, int y) {
        Ball ball = new Ball("", id, x*L + L/2, y*L + L/2, 0, 0);
        ballsList.add(ball);
        setChanged();
        notifyObservers();
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
                rotateMap.put(gizmoID, 0);
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
        setChanged();
        notifyObservers();
    }
    
    public void rotate(String id){
    	for(Gizmo gizmo: gizmoList){
    		if(gizmo.getID().equals(id)){
    			gizmo.rotateRight();
    			int value = rotateMap.get(id);
    			rotateMap.remove(id);
    			if(value < 3){
    				rotateMap.put(id, value+=1);
    			}
    			else {
    				rotateMap.put(id, 0);
    			}
    			break;
    		}
    	}
    }
    
    @Override
    public void addAbsorber(String gizmoType, String id, int x, int y, int width, int height) {
        Absorber absorber = new Absorber(gizmoType, id, x, y, width, height, this);
        gizmoList.add(absorber);
        setChanged();
        notifyObservers();
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
	public void addTriggerKey(String gizmoType, String gizmoID, int keyID, String keyDirection) {
		ArrayList<Object> triggerKeys = new ArrayList<Object>();
		triggerKeys.add(gizmoType);
		triggerKeys.add(gizmoID);
		triggerKeys.add(keyDirection);
		triggerKeys.add(keyID);
		triggerList.add(triggerKeys);
	}

	@Override
	public void removeTriggerKey(String gizmoID, int keyID, String keyDirection) {
		for(int i = 0; i < triggerList.size() - 1; i++){
			if(triggerList.get(1).equals(keyID)){
				triggerList.remove(i);
				break;
			}
		}
	}

	@Override
	public List<ArrayList<Object>> getTriggerKeys() {
		return triggerList;
	}


	@Override
	public void addTriggerGizmo(String gizmoType, String gizmoID, String gizmoTriggerID) {
		for(Gizmo gizmoTrig: gizmoList){
			if(gizmoTrig.getID().equals(gizmoTriggerID)){
				for(Gizmo gizmo: gizmoList){
					if(gizmo.getID().equals(gizmoID)){
						gizmo.addTrigger(gizmoTrig);;
					}
				}
			}
		}
		ArrayList<Object> triggerConnect = new ArrayList<Object>();
		triggerConnect.add(gizmoType);
		triggerConnect.add(gizmoID);
		triggerConnect.add(gizmoTriggerID);
		triggerList.add(triggerConnect);
	}


	@Override
	public void removeTriggerGizmo(String gizmoID, String gizmoTriggerID) {

		for(Gizmo gizmoTrig: gizmoList){
			if(gizmoTrig.getID().equals(gizmoTriggerID)){
				for(Gizmo gizmo: gizmoList){
					if(gizmo.getID().equals(gizmoID)){
						gizmo.removeTrigger(gizmoTrig);;
					}
				}
			}
		}
	}
	
    @Override
    public int[][] getMap() {
        // TODO Code for returning the map. NOTE: I think this is redundant as we don't store gizmos in an array.
        return new int[0][];
    }
}