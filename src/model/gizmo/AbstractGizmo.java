package model.gizmo;

import physics.Vect;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractGizmo contains implementations of methods that all gizmos will have completely in common.
 * Some will have to be overriden in the solid implementations of gizmos (like the trigger() method in the Flipper
 * class) to add necessary functionality. Otherwise, anything that implements this class technically doesn't have to
 * override anything.
 *
 * @author Grzegorz Sebastian Korkosz
 */
public abstract class AbstractGizmo implements Gizmo {
    int L;
    Color color;
    String id;
     String gizmoType;
    private Vect vector;
    private List<Gizmo> triggerList;
    int xPos;
    int yPos;

    public AbstractGizmo(String gizmoType, String id, int x, int y) {
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException((x < 0 ? "x" : "y") + " is less than 0 in "
                    + this.getClass() + " constructor");
        }
        this.L = 25; // TODO Change from constant 25 to a variable in constructor
        this.vector = new Vect(x, y);
        this.triggerList = new ArrayList<Gizmo>();
        this.id = id;
        this.gizmoType = gizmoType;
        this.xPos = x;
        this.yPos = y;
    }

    @Override
    public Vect getVect() {
        return vector;
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public List<Gizmo> getTriggers() {
        return triggerList;
    }

    @Override
    public String getID() {
        return id;
    }
    
    @Override
    public String getType() {
        return gizmoType;
    }


    @Override
    public Color getColour() {
        return color;
    }

    @Override
    public void rotateLeft() {
        // Do nothing by default, override if gizmo can be rotated.
    }

    @Override
    public void rotateRight() {
        // Do nothing by default, override if gizmo can be rotated.
    }

    @Override
    public void addTrigger(Gizmo gizmo) {
        triggerList.add(gizmo);
    }
    
	@Override
	public void removeTrigger(Gizmo gizmo) {
		triggerList.remove(gizmo);
	}

    @Override
    public void trigger() {
        // Do nothing by default, override if gizmo can be triggered.
    }

    
    public void trigger(boolean t) {
        // Do nothing by default, override if gizmo can be triggered.
    }
	
    @Override
    public void sendTrigger() {
        for (Gizmo gizmo: triggerList) {
            gizmo.trigger();
        }
    }
}
