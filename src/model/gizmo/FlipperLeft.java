package model.gizmo;


/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperLeft extends AbstractFlipper {

	int maxAngle = 360;
	int minAngle = 270;

	public FlipperLeft(String gizmoType,String id, int x, int y) {
		super(gizmoType, id, x, y);
		getEndPoint();
	}

	
	public void trigger(){
		if (getActive()){
			if (getAngle() < 90){
				setAngle(getAngle() + 15);
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
	
	public void trigger(boolean t) {
		if (t){
			setActive(true);
			if (getAngle() < 90){
				setAngle(getAngle() + 15);
				if (getAngle() > 90){
					setAngle(90);
				}
			}
		}
		else {
			setActive(false);
			setAngle(0);
		}
		getEndPoint();
	} 

}