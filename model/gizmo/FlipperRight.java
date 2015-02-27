package model.gizmo;


import physics.LineSegment;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperRight extends AbstractFlipper {

	private int x2;
	private int y2;
	private LineSegment ls;

	public FlipperRight(int x, int y, String id) {
		super(x, y, id);
		getEndPoint();
		ls = new LineSegment(getX(), getY(), x2, y2);
	}
	
	@Override
	public LineSegment getLineSeg() {
		return ls;
	}

	public void getEndPoint(){
		double angleRad = getAngle() * Math.PI / 180;
		double yrad   = getY() + getLength() * Math.cos(angleRad);
		double xrad   = getX() + getLength() * Math.sin(angleRad);
		y2 = (int) yrad;
		x2 = (int) xrad;

	}

	public int getx2(){
		return x2;
	}

	public int gety2(){
		return y2;
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

