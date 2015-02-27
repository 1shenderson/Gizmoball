package model.gizmo;

import java.awt.Color;
import java.util.List;

import physics.LineSegment;

/**
 * Contains code that both flippers have in common
 * @author Grzegorz Sebastian Korkosz
 */
public class AbstractFlipper extends AbstractGizmo {

	private List<Gizmo> triggerList;
	private int width = 50;
	private int length = 100;
	private int angle = 360;
	private Color colour = Color.RED;;
	private int x2;
	private int y2;

	private LineSegment ls;

	public AbstractFlipper(int x, int y, String id) {
		super(x, y, id);
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

	public int getLength(){
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
