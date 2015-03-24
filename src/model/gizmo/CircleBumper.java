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
    private String gizmoType;
    public CircleBumper(String gizmoType, String id, int x, int y) {
        super(gizmoType, id, x, y);
        this.radius = L / 2;
        this.gizmoType = gizmoType;
        color = Color.GREEN;
        this.x = x * L;
        this.y = y * L;
    }

    @Override
    public int getX(){
    	return x;
    }
    @Override
    public int getY(){
    	return y;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getCircle() {
        return new Circle(this.getX() * L, this.getY() * L, radius);
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
	public String getType() {
		return gizmoType;
	}

	@Override
	public int getx2() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int gety2() {
		// TODO Auto-generated method stub
		return 0;
	}
}
