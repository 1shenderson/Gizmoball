package model.gizmo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import physics.LineSegment;

/**
 * Contains code that both flippers have in common
 * @author Grzegorz Sebastian Korkosz
 */
public class AbstractFlipper extends AbstractGizmo {

	private List<Gizmo> triggerList;
	private int width = 100;
	private int length = 25;
	private int angle = 360;
	private Color colour = Color.GREEN;
	private int x;
	private int y;
	private int x2;
	private int y2;

	private LineSegment ls;

	private ArrayList<Object> gizmoInfo;

	public AbstractFlipper(String gizmoType, String id, int x, int y) {
		super(gizmoType, id, x, y);
		this.x = x * L;
		this.y = y * L;
		getEndPoint();
		gizmoInfo = new ArrayList<Object>();
		gizmoInfo.add(gizmoType);
		gizmoInfo.add(id);
		gizmoInfo.add(x);
		gizmoInfo.add(y);
	}

	public LineSegment getLineSeg() {
		return ls;
	}

	public void setColour(){
		colour = Color.YELLOW;
	}

	public void getEndPoint(){
		double angleRad = angle * Math.PI / 180;
		double yrad   = getY() + length * Math.cos(angleRad);
		double xrad   = getX() + length * Math.sin(angleRad);
		y2 = (int) yrad;
		x2 = (int) xrad;
	}
	
	@Override
    public int getX(){
    	return x;
    }
	
    @Override
    public int getY(){
    	return y;
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
	
	public ArrayList<Object> getFlipperInfo(){
		return gizmoInfo;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}