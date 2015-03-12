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
    public CircleBumper(int x, int y, String id) {
        super(x , y , id);
        this.radius = L / 2;
        color = Color.GREEN;
        this.x = x * L + this.radius;
        this.y = y * L + this.radius;
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
        return new Circle(this.getX(), this.getY(), radius);
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
}
