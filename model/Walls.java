package model;

import java.util.ArrayList;

import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Walls {

	private int xpos1;
	private int ypos1;
	private int ypos2;
	private int xpos2;

	// Walls are the enclosing Rectangle - defined by top left corner and bottom
	// right
	public Walls(int x1, int y1, int x2, int y2) {
		xpos1 = x1; //pos 0
		ypos1 = y1; //pos 0
 		xpos2 = x2; //pos 500
		ypos2 = y2; //pos 500
	}

	public ArrayList<LineSegment> getLineSegments() {
		ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
		LineSegment l1 = new LineSegment(xpos1, ypos1, xpos2, ypos1);// top side
		LineSegment l2 = new LineSegment(xpos1, ypos1, xpos1, ypos2);// left side
		LineSegment l3 = new LineSegment(xpos2, ypos1, xpos2, ypos2);// right side
		LineSegment l4 = new LineSegment(xpos1, ypos2, xpos2, ypos2);//bot side
		ls.add(l1);
		ls.add(l2);
		ls.add(l3);
		ls.add(l4);
		return ls;
	}

}
