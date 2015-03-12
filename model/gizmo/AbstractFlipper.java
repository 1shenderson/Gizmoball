package model.gizmo;

import java.util.List;

import physics.Circle;
import physics.LineSegment;

/**
 * Contains code that both flippers have in common
 * @author Grzegorz Sebastian Korkosz
 */
public class AbstractFlipper extends AbstractGizmo {
    public AbstractFlipper(int x, int y, String id) {
        super(x, y, id);
    }

	@Override
	public List<LineSegment> getSides() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Circle> getCorners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getAllXPos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getAllYPos() {
		// TODO Auto-generated method stub
		return null;
	}
}
