package model.gizmo;

import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperRight extends AbstractFlipper {

	private int x;
	private int y;
	private LineSegment ls;

	public FlipperRight(String gizmoType, String id, int x, int y) {
		super(gizmoType, id, x, y);
		this.x = (x * L) + L + 18;
		this.y = (y * L) + 5;

		getEndPoint();
		ls = new LineSegment(this.x, this.y, getx2(), gety2());
	}
	
	@Override
	public LineSegment getLineSeg() {
		return ls;
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void trigger(boolean t) {
		if (t){
			if (getAngle() > 270){
				setAngle(getAngle() - 20);
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