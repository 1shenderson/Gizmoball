package model.gizmo;

import java.awt.Color;
import java.util.List;

import physics.LineSegment;

/**
 * Contains code that both flippers have in common
 * @author Grzegorz Sebastian Korkosz
 */
public class AbstractFlipper extends AbstractGizmo {

	private int width = 12;
	private double length = 37.5;
	private int angle = 0;
	private Color colour = Color.RED;;
	private int x2;
	private int y2;

	private LineSegment ls;

	public AbstractFlipper(String type,String id, int x, int y) {
		super(type,id, x, y);
	}

	public LineSegment getLineSeg() {
		return ls;
	}

	public void setColour(){
		colour = Color.RED;
	}

	public void getEndPoint(){
		double angleRad = angle * Math.PI / 180;
		double yrad   = getY() + length * Math.cos(angleRad);
		double xrad   = getX() + length * Math.sin(angleRad);
		y2 = (int) yrad;
		x2 = (int) xrad;
		LineSegment newls = new LineSegment(getX(), getY(), x2,y2);
		ls = newls;
	}

	public int getx2(){
		return x2;
	}

	public int gety2(){
		return y2;
	}

	public int getWidth() {
		return width;
	}

	public double getLength(){
		return length;
	}

	public int getAngle(){
		return angle;
	}
	
	public void setAngle(int a){
		angle = a;
	}

	public Color getColour(){
		return colour;
	}

	public void trigger(boolean t) {
	} 

	public List<Gizmo> getTriggers() {
		List<Gizmo> triggerList = null;
		return triggerList;
	}

}