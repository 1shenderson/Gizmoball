package model.gizmo;

import physics.Vect;

import java.util.List;

public interface Gizmo {

	public Vect getVect();

	public int getX();

	public int getY();

    public String getID();

	public List<Gizmo> getTriggers();

    public void rotateLeft();

    public void rotateRight();

    public void addTrigger(Gizmo gizmo);

	public void trigger();

    public void sendTrigger();
}
