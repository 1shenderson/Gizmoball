package model.gizmo;


import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperLeft extends AbstractFlipper {


	private int x;
	private int y;
	private int L = 25;
	private LineSegment ls;

	public FlipperLeft(String gizmoType,String id, int x, int y) {
		super(gizmoType, id, x, y);
		this.x = (x * L);
		this.y = (y * L);
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