package model.gizmo;


/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperRight extends AbstractFlipper {

	int maxAngle = 360;
	int minAngle = 270;
	
	public FlipperRight(String gizmoType, String id, int x, int y) {
		super(gizmoType, id, x, y);
		setAngle(360);
		getEndPoint();
		rightFlip();
	}

	//	 'active' is toggled by key press and release, 'milliseconds' is how big a time 
	//	slice you want to move by; remember that you will need to be able to move by fractions 
	//	of a tick to correctly deal with collisions (same goes for the ball).  
	//	'time' here is the time in seconds you want to move the flipper for, so a full tick at 50fps would be 20 milliseconds - 0.02s.
	//	 public void step(double time) {
	//	    if active and current angle is less than max angle
	//	         angular velocity = 1080 degrees per second
	//	         angle delta = angular velocity * time
	//	         current angle = MIN(current angle + angle delta, max angle)
	//	         return
	//	    if not active and current angle is greater than min angle
	//	         angular velocity = -1080 degrees per second
	//	         angle delta = angular velocity * time
	//	         current angle = MAX(current angle + angle delta, min angle)
	//	         return
	//	    angular velocity = 0;
	//	 }
	//	 angular velocity, current angle and min and max angles should be global variables, used for 
	//	drawing and collisions, etc.  When doing ball and flipper collisions, you should use the appropriate 
	//	physics methods for ball and rotating line segments/rotating circles.  These methods will require the
	//	current angular velocity (in radians, I think, so you should probably use radian instead of degrees.  
	//	The principle is the same).
	//
	
//	int angleDelta;
//	double time = 0.02;
//	if (getActive() && (getAngle() < maxAngle)){
//		angleDelta = (int) (getAngularVelocity() * time);
//		System.out.println(angleDelta);
//		int currentAngle = Math.min(getAngle() + angleDelta, maxAngle);
//		setAngle(currentAngle);
//	}

		public void trigger(){
			if (getActive()){
				setAngle(360);
				setActive(false);
			}
			else {
				setAngle(270);
				setActive(true);
			}
			getEndPoint();
		}



	public void trigger(boolean t) {
		if (t){
			setActive(true);
			if (getAngle() > 270){
				setAngle(getAngle() - 10);
				if (getAngle() < 270){
					setAngle(270);
				}
			}
		}
		else {
			setActive(false);
			setAngle(360);
		}
		getEndPoint();
	}
	
	@Override
	public String toString(){
		return "RightFlipper " + getID() + " " + (getX() - 43) /25 + " " + getY()/25;
	}

    @Override
    public Gizmo moveTo(int x, int y) {
        return new FlipperRight(gizmoType, id, x, y);
    }
}