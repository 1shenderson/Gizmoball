package model.gizmo;
import java.awt.Color;
import java.util.List;

import physics.LineSegment;
import physics.Vect;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperRight extends AbstractGizmo {

	// flipper is a co ord, angle and length
	// use trig to work out next point

	private int width;
	private int length;
	private int angle;
	private Color colour;
	private boolean active;
	private int x2;
	private int y2;
	
	private LineSegment ls;

	public FlipperRight(int x, int y, String id, int w, int l, int a) {
		super(x, y, id);

		width = w;
		length = l;
		angle = a;
		active = false;
		colour = Color.RED;
		getEndPoint();
		ls = new LineSegment(getX(), getY(), x2, y2);
	}
	
	
	@Override
	public Vect getVect() {
		// TODO Auto-generated method stub
		return null;
	}

	public LineSegment getLineSeg() {
		return ls;
	}
	
	public void setColour(){
		if (colour == Color.RED){
			colour = Color.BLUE;
		}
		else {
			colour = Color.RED;
		}
	}
	
	public void getEndPoint(){
		double angleRad = angle * Math.PI / 180;
		double yrad   = getY() + length * Math.cos(angleRad);
		double xrad   = getX() + length * Math.sin(angleRad);
		y2 = (int) yrad;
		x2 = (int) xrad;
		if (active){
			active = false;
		}
		else {
			active = true;
		}
	}
	
	public void rotateFlipper(){
//		if (active && angle != 90){
//			angle = angle -5;
//		}
//		else if (!active && angle != 180){	
//			angle = angle +5;
//	
//		}
		if (angle == 180){
			angle = 90;
		}
		else if (angle == 90){
			angle = 180;
		}
		getEndPoint();
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
	
	public boolean getActive(){
		return active;
	}
	
	public Color getColour(){
		return colour;
	}

	@Override
	public List<Gizmo> getTriggers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void trigger() {
		// TODO Auto-generated method stub
		
	}

}

