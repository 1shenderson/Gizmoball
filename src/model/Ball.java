package model;

import java.awt.Color;

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
	private double startingXVelocity;
	private double startingYVelocity;
	private Color colour;
    private boolean ignoreAbsorber;
    private String id;

	private boolean stopped;

	// x, y coordinates and x,y velocity
	public Ball(String id, double x, double y, double xv, double yv) {
		xpos = x; // Centre coordinates
		ypos = y;
		this.id = id;
		startingXVelocity = xv;
		startingYVelocity = yv;
		colour = Color.BLUE;
		velocity = new Vect(xv, yv);
		radius = 7;
		stopped = false;
        ignoreAbsorber = false;
	}
	
	public String getID(){
		return id;
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
		return new Circle(xpos , ypos , radius);
	}

    public void setIgnoreAbsorber(boolean b) {
        this.ignoreAbsorber = b;
    }

    public boolean ignoreAbsorber() {
        return this.ignoreAbsorber;
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
	
	public double getXVelocity(){
		return startingXVelocity;
	}
	
	public double getYVelocity(){
		return startingYVelocity;
	}

	@Override
	public String toString(){
		return "Ball " + getID() + " " + getExactX()/25 + " " + getExactY()/25 + " " + getXVelocity() + " " + getYVelocity();
	}
}
