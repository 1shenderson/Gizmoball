package model.gizmo;

import physics.Circle;
import physics.Vect;
import physics.LineSegment;

import java.awt.*;
import java.util.List;

public interface Gizmo {

	public Vect getVect();

	public int getX();

	public int getY();

    public String getID();

	public List<Gizmo> getTriggers();

    public Color getColour();

    public void rotateLeft();

    public void rotateRight();

    public void addTrigger(Gizmo gizmo);
    
    public void removeTrigger(Gizmo gizmo);

	public void trigger();

    public void sendTrigger();

    public List<LineSegment> getSides();

    public List<Circle> getCorners();

    public int[] getAllXPos();

    public int[] getAllYPos();

	public String getType();
	
	public void trigger(boolean b);
}
