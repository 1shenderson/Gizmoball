package model.gizmo;


import java.awt.Color;
import java.util.ArrayList;

import physics.Circle;

public class CircleGizmo {

	private int xPos;
	private int yPos;
	private int radius;
	private Color colour;
	private ArrayList<Object> gizmoInfo;

	public CircleGizmo(String gizmoType, String id, int x, int y){
		gizmoInfo = new ArrayList<Object>();
		gizmoInfo.add(gizmoType);
		gizmoInfo.add(id);
		gizmoInfo.add(x);
		gizmoInfo.add(y);
		
		xPos = x;
		yPos = y;
		radius = 10;
		colour = Color.GREEN;
	}

	public Circle getCircle(){
		return new Circle(xPos, yPos, radius);
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	public int getRadius() {
		return radius;
	}

	public Color getColour() {
		return colour;
	}
	
	public ArrayList<Object> getCircleInfo(){
		return gizmoInfo;
	}

}