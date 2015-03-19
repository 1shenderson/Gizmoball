package model;

import java.util.ArrayList;

import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class VerticalLine {

	private String id;
	private int xpos;
	private int ypos;
	private int width;
	private String gizmoType;
	private LineSegment ls;
	private ArrayList<Object> gizmoInfo;

	public VerticalLine(String id, String gizmoType, int x, int y, int w) {
		gizmoInfo = new ArrayList<Object>();
		this.id = id;
		this.gizmoType = gizmoType;
		xpos = x;
		ypos = y;
		width = w;
		gizmoInfo.add(gizmoType);
		gizmoInfo.add(id);
		gizmoInfo.add(x);
		gizmoInfo.add(y);
		gizmoInfo.add(w);
		
		ls = new LineSegment(x, y, x + w, y);
	}

	public LineSegment getLineSeg() {
		return ls;
	}

	public int getX() {
		return xpos;
	}

	public int getY() {
		return ypos;
	}

	public int getWidth() {
		return width;
	}
	
	public String getGizmoType() {
		return gizmoType;
	}
	
	public String getID() {
		return id;
	}
	
	public ArrayList<Object> getGizmoInfo(){
		return gizmoInfo;
	}

}
