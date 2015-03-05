package model;

import java.awt.Color;
import java.util.ArrayList;

import physics.Circle;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Ball {

	private Vect velocity;
	private double radius;
	private double xpos;
	private double ypos;
	private Color colour;
	private ArrayList<Object> gizmoInfo;

	private boolean stopped;

	// x, y coordinates and x,y velocity
	public Ball(String gizmoType, String id, double x, double y, double xv, double yv) {
		xpos = x * 25; // Centre coordinates
		ypos = y * 25;
		colour = Color.BLUE;
		velocity = new Vect(xv, yv);
		radius = 7; // TODO Change constant 7 to quarter of L as per specification
		stopped = false;
		
		gizmoInfo = new ArrayList<Object>();
		gizmoInfo.add(gizmoType);
		gizmoInfo.add(id);
		gizmoInfo.add(x);
		gizmoInfo.add(y);
	}

	public Vect getVelo() {
		return velocity;
	}

	public void setVelo(Vect v) {
		velocity = v;
	}

	public double getRadius() {
		return radius;
	}

	public Circle getCircle() {
		return new Circle(xpos, ypos, radius);

	}

	// Ball specific methods that deal with double precision.
	public double getExactX() {
		return xpos;
	}

	public double getExactY() {
		return ypos;
	}

	public void setExactX(double x) {
		xpos = x;
	}

	public void setExactY(double y) {
		ypos = y;
	}

	public void stop() {
		stopped = true;
	}

	public void start() {
		stopped = false;
	}

	public boolean stopped() {
		return stopped;
	}

	public Color getColour() {
		return colour;
	}
	
	public ArrayList<Object> getBallInfo(){
		return gizmoInfo;
	}

}
