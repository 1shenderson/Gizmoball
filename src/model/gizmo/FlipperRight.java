package model.gizmo;

import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperRight extends AbstractFlipper {

	
	public FlipperRight(String gizmoType, String id, int x, int y) {
		super(gizmoType, id, x, y);
		setAngle(360);
		getEndPoint();
	}
	
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