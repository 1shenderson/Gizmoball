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
}
