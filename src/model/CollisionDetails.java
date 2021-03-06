package model;

import model.gizmo.Gizmo;
import physics.Vect;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public  class CollisionDetails {
	private double tuc;
	private Vect velo;
    private Gizmo gizmo; // Gizmo we will collide with

	public CollisionDetails(double t, Vect v) {
		tuc = t;
		velo = v;
	}

    public CollisionDetails(double t, Vect v, Gizmo gizmo) {
        this(t, v);
        this.gizmo = gizmo;
    }

	public double getTuc() {
		return tuc;
	}
	
	public Vect getVelo() {
		return velo;
	}

    public Gizmo getGizmo() {
        return gizmo;
    }

}
