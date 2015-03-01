package model.gizmo;

import physics.Circle;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class CircleBumper extends AbstractGizmo {
    private int radius;
    public CircleBumper(int x, int y, String id) {
        super(x, y, id);
        this.radius = L / 2;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getCircle() {
        return new Circle(this.getX(), this.getY(), radius);
    }
}
