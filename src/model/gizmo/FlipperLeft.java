package model.gizmo;

import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperLeft extends AbstractFlipper {


	public FlipperLeft(String gizmoType,String id, int x, int y) {
		super(gizmoType, id, x, y);
		getEndPoint();
	}

	public void trigger(boolean t) {
		if (t){
			if (getAngle() < 90){
				setAngle(getAngle() + 36);
				if (getAngle() > 90){
					setAngle(90);
				}
			}
		}
		else {
			setAngle(0);
		}
		getEndPoint();
	} 

}