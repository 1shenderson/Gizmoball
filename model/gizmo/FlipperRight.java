package model.gizmo;

import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperRight extends AbstractFlipper {

	private int x;
	private int y;
	private int L = 25;
	private LineSegment ls;

	public FlipperRight(String gizmoType, String id, int x, int y) {
		super(gizmoType, id, x, y);
		this.x = (x * L) + L;
		this.y = (y * L);
		setAngle(360);
		getEndPoint();
		ls = new LineSegment(this.x, this.y, getx2(), gety2());
	}
	
	@Override
	public LineSegment getLineSeg() {
		return ls;
	}
	
	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	// 160 presses for 5 secs
	// 30 presses per sec
	// 1080 degrees per sec
	// 36 degree's per press
	
	public void trigger(boolean t) {
		if (t){
			if (getAngle() > 270){
				setAngle(getAngle() - 36);
				if (getAngle() < 270){
					setAngle(270);
				}
			}
		}
		else {
			setAngle(360);
		}
		getEndPoint();
	}
}