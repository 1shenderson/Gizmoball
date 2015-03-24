package model.gizmo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

/**
 * Contains code that both flippers have in common
 * @author Grzegorz Sebastian Korkosz
 */
public class AbstractFlipper extends AbstractGizmo {

	private List<Gizmo> triggerList = new ArrayList<Gizmo>();
	private double width = 12.5;
	private double length = 37.5;
	private int angle = 0;
	private Color colour = Color.ORANGE;
	private int x;
	private int y;
	private int x2;
	private int y2;
	private int L = 25;
	private ArrayList<Object> gizmoInfo;
	private boolean active = false;
	private int angularVelocity = 1080;


	public AbstractFlipper(String gizmoType, String id, int x, int y) {
		super(gizmoType, id, x, y);
		this.x = (x * L) + 6;
		this.y = (y * L) + 6;
		getEndPoint();
		gizmoInfo = new ArrayList<Object>();
		gizmoInfo.add(gizmoType);
		gizmoInfo.add(id);
		gizmoInfo.add(x);
		gizmoInfo.add(y);
	}

	public void setColour(){
		colour = Color.ORANGE;
	}

	public void getEndPoint(){
		double angleRad = angle * Math.PI / 180;
		double yrad   = y + length * Math.cos(angleRad);
		double xrad   = x + length * Math.sin(angleRad);
		y2 = (int) yrad;
		x2 = (int) xrad;
	}
	
    public int getX(){
    	return x;
    }
	
    public int getY(){
    	return y;
    }

	public int getx2(){
		return x2;
	}

	public int gety2(){
		return y2;
	}

	public double getWidth() {
		return width;
	}

	public double getLength(){
		return length;
	}

	public void rightFlip(){
		x = x + 37;
		x2 = x2 + 37;
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
		return triggerList;
	}
	
	public ArrayList<Object> getFlipperInfo(){
		return gizmoInfo;
	}
	
	public boolean getActive(){
		return active;
	}

	public int getAngularVelocity(){
		return angularVelocity;
	}

	public void setActive(boolean t){
		active = t;
	}
	
	@Override
	public List<LineSegment> getSides() {
		List<LineSegment> lineList = new ArrayList<LineSegment>();
		LineSegment newls = new LineSegment(x+6, y, x2+6,y2);
		lineList.add(newls);
		LineSegment newls2 = new LineSegment(x-6, y, x2-6,y2);
		lineList.add(newls2);
		return lineList;
	}

	@Override
	public List<Circle> getCorners() {
		List<Circle> circleList = new ArrayList<Circle>();
		Circle circle = new Circle(x-6,y,12);
		circleList.add(circle);
		Circle circle2 = new Circle(x2-6,y2,12);
		circleList.add(circle2);
		return circleList;
	}

	@Override
	public int[] getAllXPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getAllYPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeTrigger(Gizmo gizmo) {
		triggerList.remove(gizmo);
	}
}