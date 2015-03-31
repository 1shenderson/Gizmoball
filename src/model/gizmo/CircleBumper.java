package model.gizmo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import physics.Circle;
import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class CircleBumper extends AbstractGizmo {
    private int radius;
    private int x;
    private int y;
    public CircleBumper(String gizmoType, String id, int x, int y) {
        super(gizmoType, id, x, y);
        this.radius = L / 2;
        color = Color.GREEN;
        this.x = x * L;
        this.y = y * L;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getCircle() {
        return new Circle(this.getX() * L + getRadius(), this.getY() * L + getRadius(), radius);
    }

	@Override
	public List<LineSegment> getSides() {
		return Collections.emptyList();
	}

	@Override
	public List<Circle> getCorners() {
		List<Circle> circleList = new ArrayList<Circle>();
		Circle circle = getCircle();
		circleList.add(circle);
		return circleList;
	}

    @Override
    public void trigger(){
		if (color == Color.GREEN){
			color = Color.ORANGE;
		}
		else {
			color = Color.GREEN;
		}
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
	public String toString(){
		return "Circle " + getID() + " " + getX() + " " + getY();
	}

    @Override
    public Gizmo moveTo(int x, int y) {
        return new CircleBumper(gizmoType, id, x, y);
    }
}
