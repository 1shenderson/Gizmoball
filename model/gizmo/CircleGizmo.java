package model.gizmo;


import java.awt.Color;
import physics.Circle;

public class CircleGizmo {

	private int xPos;
	private int yPos;
	private int radius;
	private Color colour;

	public CircleGizmo(int x, int y){
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

}