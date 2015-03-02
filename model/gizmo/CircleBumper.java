package model.gizmo;

import java.awt.Color;

import physics.Circle;

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
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
