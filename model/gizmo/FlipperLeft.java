package model.gizmo;


import physics.LineSegment;
import physics.Vect;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class FlipperLeft extends AbstractFlipper {


	private int x2;
	private int y2;
	private LineSegment ls;

	public FlipperLeft(int x, int y, String id) {
		super(x, y, id);
		getEndPoint();
		ls = new LineSegment(x,y, x2, y2);
		System.out.println(ls.p1().x());
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
			if (getAngle() < 450){
				setAngle(getAngle() + 20);
				if (getAngle() > 450){
					setAngle(450);
				}
			}
		}
		else {
			setAngle(360);
		}
		getEndPoint();
	} 

}

