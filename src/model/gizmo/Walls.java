package model.gizmo;

import java.util.ArrayList;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Walls extends AbstractGizmo{

	private int xpos1;
	private int ypos1;
	private int ypos2;
	private int xpos2;

	// Walls are the enclosing Rectangle - defined by top left corner and bottom
	// right
	public Walls(int x1, int y1, int x2, int y2) {
		super("Walls", "OuterWalls", x1, y1);
		xpos1 = x1;
		ypos1 = y1;
		xpos2 = x2;
		ypos2 = y2;
	}

	@Override
	public List<LineSegment> getSides() {
		ArrayList<LineSegment> ls = new ArrayList<LineSegment>();
		LineSegment l1 = new LineSegment(xpos1, ypos1, xpos2, ypos1);
		LineSegment l2 = new LineSegment(xpos1, ypos1, xpos1, ypos2);
		LineSegment l3 = new LineSegment(xpos2, ypos1, xpos2, ypos2);
		LineSegment l4 = new LineSegment(xpos1, ypos2, xpos2, ypos2);
		ls.add(l1);
		ls.add(l2);
		ls.add(l3);
		ls.add(l4);
		return ls;
	}

	@Override
	public List<Circle> getCorners() {
		// TODO Auto-generated method stub
		return null;
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

}
