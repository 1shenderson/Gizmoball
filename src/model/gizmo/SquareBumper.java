package model.gizmo;

import java.awt.Color;

/**
 * @author Grzegorz Sebastian Korkosz
 */
public class SquareBumper extends AbstractSquare {
    public SquareBumper(String gizmoType, String id, int x, int y) {
        super(gizmoType, id, x, y);
        color = Color.RED;
    }
    
    @Override
    public void trigger(){
		if (color == Color.RED){
			color = Color.GREEN;
		}
		else {
			color = Color.RED;
		}
    }
    
    @Override
	public String toString(){
		return "Square " + getID() + " " + getX() + " " + getY();
	}

    @Override
    public Gizmo moveTo(int x, int y) {
        return new SquareBumper(gizmoType, id, x, y);
    }
}
